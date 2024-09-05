package com.rockthejvm

import scala.concurrent.Future
import scala.util.Try

object ScalaIntroduction {
  //
  // 1 - Immutability and recursion
  //
  val meaningOfLife = 40 + 2 // Cannot be changed - immutable

  // In other languages, one would make a for loop like this:
  // for (int i = 0; i < 10; 1++) println("hello scala"
  // In Scala this is often solved with recursion instead:
  def loop(i: Int = 0): Unit = {
    println(i)
    if (i < 10)
      loop(i + 1)
  }
  // repetition => recursion

  //
  // 2 - Expressions vs instructions
  //
  // In other languages we tend to think in instructions to do. In Scala everything is an expression
  val anIfStatement = if (42 < 100) "Scala" else "Something not nice" // evaluated to "Scala"

  /**
   * Example of a function which concatenate a string over and over. This is a typical instructional way to do it:
   * String concatenaeN(int n, String s) {
   *  String result = "";
   *  for (int = 0; i < n; i++) result += s;
   *  return result;
   */
  // In Scala:
  def concatenateN(n: Int, s: String): String =
    if (n <= 0) ""
    else s + concatenateN(n-1, s)

  //
  // 3 - Object Oriented Programming
  //
  // Classes, fields, methods, constructors, instances, "interfaces" (traits), abstract classes, inheritance
  // Overloading, overriding, "polymorphism"
  // case classes (records in Java)

  trait Human {
    def statement(): String
  }

  case class Person(name: String, favLanguage: String) extends Human {
    def statement(): String = s"Hi, I'm $name and I love $favLanguage"
  }

  val daniel: Human = new Person("Daniel", "Scala")

  //
  // 4 - Pattern matching
  //
  val danielSays = daniel match {
    case Person(name, lang) => s"$name likes $lang"
  }

  //
  // 5 - Functional programming - Functions as values
  //
  val aFunction = new Function1[Int, Int] {
    override def apply(x: Int) = x + 1
  }
  val three = aFunction(2)
  val aFunction_v2 = (x: Int) => x + 1 // Same as aFunction

  //
  // 6 - Collections
  //
  val aList = List(1,2,3)
  val incrementedList = aList.map(x => x + 1) // [2,3,4]
  val transformedList = aList.flatMap(x => List(x, x + 1)) // [1,2,2,3,3,4]
  val evenList = aList.filter(_ % 2 == 0) // [2]
  val chessboard = for { // For comprehension - NOT a for loop => map and flatMap chained
    num <- List(1,2,3)
    char <- List('a', 'b', 'c')
  } yield s"$num-$char"

  //
  // 7 - Abstract data structures - Option, Try
  //
  // map, flatMap, for, filter, orElse
  val anOption: Option[Int] = Option(42) // Option => "mini collections" with > 1 element
  val aTransformedOption = anOption.map(_ + 1) // Returns increment or just empty if anOption is null. No need for null check
  val aTry: Try[Int] = Try(throw new RuntimeException) // Abstracting an exception
  val aTransformedTry = aTry.map(_ * 10) // Multiplies by 10 or returns an abstracted exception (still valid value)

  //
  // 8 - Monads - Chainable computations
  //
  // Read more about this

  //
  // 9 - Futures
  //
  // map, flatMap, for, recover, onComplete
  import scala.concurrent.ExecutionContext.Implicits.global // Import of multi thread
  val aFuture: Future[Int] = Future {
    // long running code
    Thread.sleep(1000)
    42
  }
  val aTransofmredFuture = aFuture.map(_ * 100)

  //
  // 10 - Contextual abstractions (implicits)
  //
  // This is for Scala 3, not relevant for me yet

  // Scala main method
  def main(args: Array[String]): Unit = {
    println("Hello, Scala!")
  }
}
