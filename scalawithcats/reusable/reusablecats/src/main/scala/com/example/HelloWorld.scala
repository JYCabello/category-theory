package com.example

import cats.effect._
import cats.syntax.all._

implicit class CustomCatsSyntax[A](value: A) {
  implicit def io : IO[A] = IO.delay(value)
  implicit def someIO : IO[Option[A]] = IO.delay(value.some)
}

object HelloWorld {
  def someBanana: Option[String] = "banana".some
  def someAsyncBanana: IO[Option[String]] = "banana".someIO
  def say(): IO[String] = "Hello Cats!".io
}
