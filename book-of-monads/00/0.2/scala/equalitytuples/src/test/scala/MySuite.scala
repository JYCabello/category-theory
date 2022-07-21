// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  test("tuple equality works") {
    assertEquals(true, tuplesEqual((1, "banana"), (1, "banana")))
  }
}
