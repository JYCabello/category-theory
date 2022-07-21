def tuplesEqual[A, B](t1: (A, B), t2: (A, B)) : Boolean =
  val (t1a, t1b) = t1
  val (t2a, t2b) = t2
  t1a == t2a && t1b == t2b

@main def hello(): Unit =
  println("I compare tuples in a language that has tuple equality already")
