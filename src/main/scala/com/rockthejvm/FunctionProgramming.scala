package com.rockthejvm

object FunctionProgramming extends App {

  // Scala is OOP
  class Person(name: String) {
    def apply(age: Int) = println(s"I have ages $age years")
  }

  val bob = new Person("Bob")
  bob.apply(43)
  bob(43) // INVOKING bob as a function === bob.apply(43)

  /*
    Scala runs on the JVM (designed for OOP)
    Functional programming:
      - compose functions
      - pass functions as args
      - return functions as results

      Conclusion: FunctionX = Function1, Function2, ... Function22
   */

  val simpleIncrementer = new Function1[Int, Int] {
    override def apply(arg: Int): Int = arg + 1
  }

  simpleIncrementer.apply(23) // 24
  simpleIncrementer(23) // equivalent to simpleIncrementer.apply(23)
  // defined a function!

  // ALL SCALA FUNCTIONS ARE INSTANCES OF THESE FUNCTION_X TYPES

  // Function with 2 arguments and a String return type
  val stringConcatenator = new Function2[String, String, String] {
    override def apply(arg1: String, arg2: String): String = arg1 + arg2
  }

  stringConcatenator("I love", " Scala") // Returns "I love Scala"

  // syntac sugars - code to replace heavy boilerplate code
  val doubler: Function1[Int, Int] = (x: Int) => 2 * x
  doubler(4) // 8

  /*
    Equivalent to the much longer:

    new Function1[Int, Int] {
      override def apply(x: Int) = 2 * x
    }
   */

  val doubler2: Int => Int = (x: Int) => 2 * x // Can use Int => Int instead

  /*
  Equivalent to the much longer:

  val doubler2: Function1[Int, Int] = new Function1[Int, Int] {
    override def apply(x: Int) = 2 * x
  }
 */

  // Can omit the type all together because the compiler can infer it
  val doubler3 = (x: Int) => 2 * x

  // Higher-order functions: Take functions as args/return functions as result
  val aMappedList: List[Int] = List(1,2,3).map(x => x + 1) // HOF
  println(aMappedList)

  val aFlatMappedList = List(1,2,3).flatMap(x => List(x, 2 * x)) // flatmap concatenates multiple smaller lists into one
  println(aFlatMappedList)

  val anotherFlatMappedList = List(1,2,3).flatMap { x =>
    List(x, 2 * x)
  } // Alternative syntax of the previous function

  val aFilteredList = List(1,2,3,4,5).filter(x => x <= 3) // Filters out all ints smaller than or equals to three
  println(aFilteredList)

  val anotherFilteredList = List(1,2,3,4,5).filter(_ <= 3) // equivalent to x => x <= 3

  // all pairs between the numbers 1,2,3 and the letters 'a', 'b', 'c'
  val allPairs = List(1,2,3).flatMap(number => List('a', 'b', 'c').map(letter => s"$number-$letter"))
  println(allPairs)

  // for comprehensions: A more human readable chaining of HOF
  var alternativePairs = for {
    number <- List(1,2,3)
    letter <- List('a', 'b', 'c')
  } yield s"$number-$letter"
  // equivalent to the allPairs chaining of map/flatmap

  /**
   * Collections
   */

  // lists
  val aList = List(1,2,3,4,5)
  val firstElement = aList.head
  val rest = aList.tail
  val aPrependedList = 0 :: aList // List(0,1,2,3,4,5)
  val anExtendedList = 0 +: aList :+ 6 // List(0,1,2,3,4,5,6)

  // sequences
  val aSequence: Seq[Int] = Seq(1,2,3) // Seq.apply(1,2,3)
  val accessedElement = aSequence.apply(1) // the element at index 1: 2

  // vectors: Fast Seq implementation
  val aVector = Vector(1,2,3,4,5)

  // sets = no duplicates
  val aSet = Set(1,2,3,4,1,2,3) // Set(1,2,3,4)
  val setHas5 = aSet.contains(5) // false
  val anAddedSet = aSet + 5 // Set(1,2,3,4,5)
  val aRemovedSet = aSet - 3 // Set(1,2,4)

  // ranges
  val aRange = 1 to 1000
  val twoByTwo = aRange.map(x => 2 * x).toList // List(2,4,6,8...,2000)

  // tuples = groups of values under the same value
  val aTuple = ("Bon Jovi", "Rock", 1982)

  // maps
  val aPhonebook: Map[String, Int] = Map(
    ("Daniel", 653738),
    "Jane" -> 393930 // equivalent to ("Jane",393930)
  )
}
