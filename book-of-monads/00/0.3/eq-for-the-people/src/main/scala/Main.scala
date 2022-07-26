trait Eq[A] {
  def eq(x: A, y: A): Boolean
}

def notEq[A](x: A, y: A)(implicit eq: Eq[A]): Boolean = !eq.eq(x, y)

implicit def tupleEq[A, B](implicit eqa: Eq[A], eqb: Eq[B]): Eq[(A, B)] =
  (x: (A, B), y: (A, B)) =>
    val (x1, x2) = x
    val (y1, y2) = y
    eqa.eq(x1, y1) && eqb.eq(x2, y2)

implicit def intEq: Eq[Int] = (x, y) => x == y

implicit class EqOps[A](x: A) {
  def equalTo(y: A)(implicit eq: Eq[A]): Boolean = eq.eq(x, y)
  def notEqualTo(y: A)(implicit eq: Eq[A]): Boolean = !eq.eq(x, y)
}

@main def hello(): Unit =
  if ((1, 2).equalTo((1, 2)))
  then println("Equal")
  else println("Not equal")
