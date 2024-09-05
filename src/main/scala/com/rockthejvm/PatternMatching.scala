package com.rockthejvm

object PatternMatching extends App {

  // switch expression
  val anInteger = 55
  val order = anInteger match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => anInteger + "th" // underscore is everything else. It is a wildcard
  }

  // Pattern match is an EXPRESSION
  println(order)

  case class Person(nane: String, age: Int)
  val bob = Person("Bob", 43) // Person.apply("Bob", 43)

  // Match based on structure
  // Case classes decomposition
  val personGreeting = bob match {
    case Person(n, a) => s"Hi, my name is $n and I am $a years old."
    case _ => "Something else"
  }
  println(personGreeting)

  // deconstructing tuples
  val aTuple = ("Bon Jovi", "Rock")
  val bandDesc = aTuple match {
    case (band, genre) => s"$band belongs to the genre $genre"
    case _ => "I don't know what you are talking about"
  }

  // decomposing lists
  val aList = List(1,2,3)
  val listDesc = aList match {
    case List(_, 2, _) => "List containing 2 on its second position"
    case _ => "Unkown list"
  }
  // if PM doesn't match anything, it will throw a MatchError. Therefore, use _
  // PM will try all cases in sequence
}
