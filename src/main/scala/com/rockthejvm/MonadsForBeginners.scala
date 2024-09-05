package com.rockthejvm

import com.rockthejvm.MonadsForBeginners.numbers

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

object MonadsForBeginners {

  case class SafeValue[+T](private val internalValue: T) { // "constructor" = pure, or unit
    def get: T = synchronized {
      // does something interesting
      internalValue
    }

    def flatMap[S](transformer: T => SafeValue[S]): SafeValue[S] = synchronized { // bind, or flatMap
      transformer(internalValue)
    }
  }

  // "external" API
  def gimmeSafeValue[T](value: T): SafeValue[T] = SafeValue(value)

  val safeString: SafeValue[String] = gimmeSafeValue("Scala is awesome")
  // extract
  val string = safeString.get
  // transform
  val upperString = string.toUpperCase()
  // wrap
  val upperSafeString = SafeValue(upperString)
  // ETW: Extract, transform, wrap

  // Compressed ETW:
  val upperSafeString2 = safeString.flatMap(s => SafeValue(s.toUpperCase()))

  // Examples:

  // Example 1: Census
  case class Person(firstName: String, lastName: String) {
    assert(firstName != null && lastName != null)
  }
  // census API
  // Traditional Java style null check
  def getPerson(firstName: String, lastName: String): Person =
    if (firstName != null) {
      if (lastName != null) {
        Person(firstName, lastName)
      } else {
        null
      }
    } else {
      null
    }

  def getPersonBetter(firstName: String, lastName: String): Option[Person] =
    Option(firstName).flatMap { fName =>
      Option(lastName).flatMap { lName =>
        Option(Person(fName, lName))
      }
    }

  def getPersonFor(firstName: String, lastName: String): Option[Person] = for {
    fName <- Option(firstName)
    lName <- Option(lastName)
  } yield Person(fName, lName)

  // Example 2: Asynchronous fetches
  case class User(id: String)
  case class Product(sku: String, price: Double)

  def getUser(url: String): Future[User] = Future {
    User("Daniel") // sample implementation
  }

  def getLastOrder(userId: String): Future[Product] = Future {
    Product("123-456", 99.99) // sample
  }

  val danielsUrl = "my.store.com/users/daniel"

  // ETW pattern version:
  getUser(danielsUrl).onComplete {
    case Success(User(id)) =>
      val lastOrder = getLastOrder(id)
      lastOrder.onComplete( {
        case Success(Product(sku, p)) =>
          val vatIncludedPrice = p * 1.19
          // pass it on - send Daniel an email
      })
  }

  // flatMap version of the code above:
  val vatInclPrice = getUser(danielsUrl)
    .flatMap(user => getLastOrder(user.id))
    .map(_.price * 1.19)

  // For comprehension version of the code above:
  val vatInclPriceFor = for {
    user <- getUser(danielsUrl)
    product <- getLastOrder(user.id)
  } yield product.price * 1.19

  // Example 3: Double for-loops
  val numbers = List(1,2,3)
  val chars = List('a', 'b', 'c')

  // flatMaps
  val checkerboard = numbers.flatMap(number => chars.map(char => (number, char)))

  // For comprehension
  val checkerboard2 = for {
    number <- numbers
    char <- chars
  } yield (number, char)

  // Monad properties

  // Property 1
  def twoConsecutive(x: Int) = List(x, x + 1)
  twoConsecutive(3) // List(3,4)
  List(3).flatMap(twoConsecutive) // = List(3, 4)
  // Monad(x).flatMap(f) == f(x)

  // Property 2
  List(1,2,3).flatMap(x => List(x)) // List(1,2,3)
  // Monad(v).flatMap(x => Monad(x)) Useless, returns Monad(v)

  // Property 3 - ETW-ETW
  val incrementer = (x: Int) => List(x, x + 1)
  val doubler = (x: Int) => List(x, 2 * x)

  def main(args: Array[String]): Unit = {
    println(numbers.flatMap(incrementer).flatMap(doubler))
    println(numbers.flatMap(incrementer).flatMap(doubler) == numbers.flatMap(x => incrementer(x).flatMap(doubler))
    )
    // List(1, 2, 2, 4,   2, 4, 3, 6,   3, 6, 4, 8)
    /**
     List(
      incrementer(1).flatMap(doubler) -- 1,2,2,4
      incrementer(2).flatMap(doubler) -- 2,4,3,6
      incrementer(3).flatMap(doubler) -- 3,6,4,8
     )

     Monad(v).flatMap(f).flatMap(g) == Monad(v).flatMap(x => f(x).flatMap(g))
     The left side applies f on all elements and then g on all elements
     On the right side f.flatMap(g) applied on every elements individually, then combining them all together.
     */
  }
}
