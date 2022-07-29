package com.example

import cats._
import cats.effect._
import cats.implicits._
import cats.syntax._
import cats.instances._
import cats.syntax.functor._

implicit class CustomCatsSyntax[A](value: A) {
  implicit def io : IO[A] = IO.delay(value)
  implicit def someIO : IO[Option[A]] = IO.delay(value.some)
}

implicit class ListSyntax(value: List[Int]) {
  def add: Int = value.foldLeft(Monoid[Int].empty)(_ |+| _)
}

object HelloWorld {
  def someAsyncBanana: IO[Option[String]] = "banana".someIO
  def say(): IO[String] = IO.delay(func4(7))
}

val func1 = (a: Int) => a + 1
val func2 = (a: Int) => a * 2
val func3 = (a: Int) => s"$a!"
val func4: Int => String = func1.map(func2).map(func3)


case class TDIKinds[R, F[_], A](value: R => F[A])(using fm: FlatMap[F]) {
  def map[B] (f: A => B) : TDIKinds[R, F, B] =
    TDIKinds(deps => value(deps).map(f))

  def flatMap[B] (f: A => TDIKinds[R, F, B]): TDIKinds[R, F, B] =
    TDIKinds((deps: R) => value(deps).flatMap((a: A) => f(a).value(deps)))
}

def rightForEven(n: Int): Either[String, Int] = if n % 2 === 0 then Right(n) else Left(n.toString)

val meh: Either[String, Int] =
  for {
    a <- rightForEven(2)
    b <- rightForEven(4)
    c <- rightForEven(8)
    result = a + b + c
  } yield result

import cats.instances.all._
import cats.instances.either._

type EitherString[A] = Either[String, A]
type Effectful[A] = IO[EitherString[A]]

extension [A](e: Either[String, A])
  def eff:Effectful[A] = e.io

implicit val flatMapEitherString: FlatMap[Effectful] =
  new FlatMap[Effectful] {
    def flatMap[A, B](e: Effectful[A])(f: A => Effectful[B]): Effectful[B] =
      e.flatMap((e: Either[String, A]) => e.fold[Effectful[B]](str => Left(str).io, a => f(a)))

    def tailRecM[A, B](a: A)(f: A => Effectful[Either[A, B]]): Effectful[B] =
      f(a).flatMap {
        case Left(s) => IO.delay(Left(s))
        case Right(eab) =>
          eab match {
            case Right(b) => IO.delay(Right(b))
            case Left(a1) => tailRecM(a1)(f)
          }
      }

    def map[A, B](e: Effectful[A])(f: A => B): Effectful[B] =
      e.flatMap {
        case Left(s) => IO.delay(Left(s))
        case Right(a) => IO.delay(Right(f(a)))
      }
  }

val turboDependencyInjection: TDIKinds[String, Effectful, Int] =
  for {
    a <- TDIKinds((x: String) => rightForEven(2).eff)
    b <- TDIKinds((x: String) => rightForEven(4).eff)
  } yield a + b

