package com.rockthejvm

object ObjectOrientation extends App {   // extends App = java equivalent: public static void main(String[] args)

  // class and instance
  class Animal {
    // define fields
    val age: Int = 0
    //define methods
    def eat(): Unit = println("I'm eating")
  }

  val anAnimal = new Animal

  // inheritance
  class Dog(val name: String) extends Animal // Consructor definition
  val aDog = new Dog("Lassie")

  // Constructor arguments are NOT fields: Need to put a val before the constructor argument
  aDog.name

  // subtype polymorphism
  val aDeclaredAnimal: Animal = new Dog("Hachi")
  aDeclaredAnimal.eat() // The most derived method will be called at runtime

  // Abstract class
  abstract class WalkingAnimal {
    val hasLegs = true // by default public, can be restricted by adding private/protected
    def walk(): Unit
  }

  // "interface" = ultimate abstract type
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  trait Philosopher {
    def ?!(thought: String): Unit // ?! is a valid method name (and used in practice)
  }

  // single-class inheritance, multi-trait "mixing"
  class Crocodile extends Animal with Carnivore  with Philosopher {
    override def eat(animal: Animal): Unit = println("I'm eating you, animal!")
    override def ?!(thought: String): Unit = println(s"I was thinking: $thought")
  }

  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog //infix notation = object method argument, only available for methods with ONE argument
  aCroc ?! "What if we could fly?"

  // Operators in Scala is actually methods
  val basicMath = 1 + 2
  val anotherBasicMath = 1.+(2) // equivalent

  // Anonymous classes
  val dinosaur = new Carnivore {
    override def eat(animal: Animal): Unit = println("I am a dinosaur so I can eat pretty much anything")
  }

  /*
    What you tell the compiler:
    class Carnivore_Anonymous_43432 extends Carnivore {
       override def eat(animal: Animal): Unit = println("I am a dinosaur so I can eat pretty much anything")
   }

   val dinosaur = new Carnivore_Anonymous_43432
   */

  // Singleton object
  object MySingleton { // the only instance of the MySingleton type
    val mySpecialValue = 32535
    def mySpecialMethod(): Int = 3232
    def apply(x: Int): Int = x + 1
  }

  MySingleton.mySpecialMethod()
  MySingleton.apply(65)
  MySingleton(65) // equivalent to MySingleton.apply(65)

  object Animal { // companions - companion object
    // companions can access each other's private fields/methods
    // singleton Animal and instances of Animal are different things
    val canLiveIndefinitely = false
  }

  val animalsCanLiveForever = Animal.canLiveIndefinitely // "static" fields/methods

  /*
    case classes = lightweight data structures with some boilerplate
      - sensible equals and hash code
      - serialization
      - companion with apply
      - pattern matching
   */
  case class Person(name: String, age: Int)
  // may be constructed without new
  val bob = new Person("Bob", 54) // Person.apply("Bob", 54)

  // exceptions
  try {
    // code that can throw
    val x: String = null
    x.length
  } catch { // catch(Exception e) {...} in Java
    case e: Exception => "Some faulty error message"
  } finally {
    // Execute some code no matter what
  }

  // generics
  abstract class MyList[T] {
    def head: T
    def tail: MyList[T]
  }

  // using a generic with a concrete type
  val aList: List[Int] = List(1,2,3) // List.apply(1,2,3)
  val first = aList.head // int
  val rest = aList.tail
  val aStringList = List("hello", "Scala")
  val firstString = aStringList.head // string

  // Point #1: in Scala we usually operate with IMMUTABLE values/objects
  // Any modification to an object must return ANOTHER object
  /*
    Benefits:
    1) works miracles in multithreaded/distributed env
    2) helps making sense of the code ("reasoning about")
   */
  val reversedList = aList.reverse // returns a NEW list

  // Point #2: Scala is closest to the OOP ideal

}

