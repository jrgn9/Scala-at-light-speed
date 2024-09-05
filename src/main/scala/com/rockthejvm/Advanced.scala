package com.rockthejvm

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

object Advanced extends App {

  // ALL OF THESE "COLLECTIONS" ARE MONADS

  /**
   Lazy evaluation: An expression is not evaluated before it is first used
    */
  lazy val aLazyValue = 2
  lazy val lazyValueWithSideEffect = {
    println("I am so very lazy!")
    43
  }

  val eagerValue = lazyValueWithSideEffect + 1 // This prints the message because lazyValueWithSideEffect is first used
  // Useful in infinite collections

  /**
   "Pseudo-collections": Option, Try
    */
  def methodWhichCanReturnNull(): String = "hello, Scala"
  /*  How to guard against null in other languages:
    if (methodWhichCanReturnNull() == null) {
    //defensive code against null
  }*/

  // Null defense in Scala:
  val anOption = Option(methodWhichCanReturnNull()) // Some("hello, Scala")
  // Option = "collection" which contains at most one value> Some(value) or None

  val stringProcessing = anOption match {
    case Some(string) => s"I have obtained a valid string: $string"
    case None => "I obtained nothing"
  }
  // map, flatMap, filter

  def methodWhichCanThrowException(): String = throw new RuntimeException
  /*
  How you would typically safeguard against exceptions in Java:
  try {
    methodWhichCanThrowException()
  } catch {
    case e: Exception => "defend against this evil exception"
  }*/

  // In Scala:
  val aTry = Try(methodWhichCanThrowException())
  // A Try = "collection" with either a value if the code went well, or an exception if the code threw one

  val anotherStringProcessing = aTry match {
    case Success(validValue) => s"I have obtained a valid string: $validValue"
    case Failure(ex) => s"I have obtained an exception: $ex"
  }
  // map, flatMap, filter

  /**
   * Evaluate something on another thread
   * (asynchronous programming)
   * Added import: import scala.concurrent.ExecutionContext.Implicits.global
   */
  val aFuture = Future {
    println("Loading...")
    Thread.sleep(1000)
    println("I have computed a value.")
    67
  }

  Thread.sleep(2000) // To let the Future have time to finnish

  // Future is a "collection" which contains a value when it's evaluated
  // Future is composable with map, flatMap and filter

  /**
   * Implicits basics
   */
  // #1: Implicit arguments
  def aMethodWithImplicitArgs(implicit arg: Int) = arg + 1
  implicit val dummyImplicit = 46
  println(aMethodWithImplicitArgs) // aMethodWithImplicitsArgs(myImplicitInt)

  // #2: Implicit conversions
  implicit class MyRichInteger(n: Int) {
    def isEven() = n % 2 == 0
  }

  println(23.isEven()) // The compiler runs: new MyRichInteger(23).isEven()
  // USE THIS CAREFULLY
}
