package com.example

import cats.effect.{IO, SyncIO}
import munit.CatsEffectSuite

class HelloWorldSuite extends CatsEffectSuite {
  test("test hello world says hi") {
    HelloWorld.say().map(assertEquals(_, "16!"))
  }
}
