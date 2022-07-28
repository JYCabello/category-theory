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

//case class TurboDieselInjection[R, F[_], +A](value: R => F[A])(using flatMap: FlatMap[F]) {
//  def map[B] (f: A => B) : TurboDieselInjection[R, F, B] =
//    TurboDieselInjection(deps => value(deps).map(f))
//
//  def flatMap[B] (f: A => TurboDieselInjection[R, F, B]): TurboDieselInjection[R, F, B] =
//    val x: R => IO[Either[Int, B]] =
//      (deps: R) =>
//        value(deps).flatMap(either =>
//          either.fold(
//            e => IO.delay(Left(e)),
//            s => f(s).value(deps)
//          )
//        )
//
//    TurboDieselInjection(x)
//}
case class TurboDieselInjection[R, F[_], A](value: R => F[A])(using fm: FlatMap[F]) {
  def map[B] (f: A => B) : TurboDieselInjection[R, F, B] =
    TurboDieselInjection(deps => value(deps).map(f))

  def flatMap[B] (f: A => TurboDieselInjection[R, F, B]): TurboDieselInjection[R, F, B] =
    TurboDieselInjection((deps: R) => value(deps).flatMap((a: A) => f(a).value(deps)))
}
