package com.example

import cats._
import cats.effect._
import cats.implicits._
import cats.syntax._

implicit class CustomCatsSyntax[A](value: A) {
  implicit def io : IO[A] = IO.delay(value)
  implicit def someIO : IO[Option[A]] = IO.delay(value.some)
}

implicit class ListSyntax(value: List[Int]) {
  def add: Int = value.foldLeft(Monoid[Int].empty)(_ |+| _)
}

object HelloWorld {
  def someAsyncBanana: IO[Option[String]] = "banana".someIO
  def say(): IO[String] = IO.delay(List(1,2,3).add.toString)
}

