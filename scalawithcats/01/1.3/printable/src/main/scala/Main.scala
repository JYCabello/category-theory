import cats.Show
import cats.instances.int._
import cats.instances.string._
import cats.syntax.show._

final case class Cat(name: String, age: Int, color: String)
object Cat {
  implicit val showableCat: Show[Cat] =
    (c: Cat) => s"${c.name.show} is a ${c.age.show} year-old ${c.color.show} cat."
}

trait Printable[A] {
  def format(a: A) : String
}
import PrintableSyntax._
implicit object PrintableInstances {
  implicit val printableString: Printable[String] = (a: String) => a
  implicit val printableInt: Printable[Int] = (a: Int) => a.toString
  implicit val printableCat: Printable[Cat] =
    (c: Cat) => s"${c.name.format} is a ${c.age.format} year-old ${c.color.format} cat."
}


object Printable {
  def format[A](a: A)(implicit p: Printable[A]): String = p.format(a)
  def print[A](a: A)(implicit p: Printable[A]): Unit = println(p.format(a))
}

object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def format(implicit w: Printable[A]): String = Printable.format(value)
    def print(implicit w: Printable[A]): Unit = Printable.print(value)
  }
}

import PrintableInstances._

@main def hello(): Unit =
  println("Hello world!")
  println(msg)
  7.print
  Cat("Garfield", 5, "orange").show
  Cat("Garfield", 5, "orange").print

def msg = "I was compiled by Scala 3. :)"
