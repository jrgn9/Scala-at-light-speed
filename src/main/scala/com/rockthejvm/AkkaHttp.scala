package com.rockthejvm

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

object AkkaHttp {

  implicit val system = ActorSystem() // Akka actors
  implicit val materializer = ActorMaterializer() // Akka Streams
  import system.dispatcher // "Thread pool"

  val source =
    """
      |JSON.stringify({
      |    title: 'foo',
      |    body: 'bar',
      |    userId: 1,
      |})
      |""".stripMargin

  val request = HttpRequest(
    method = HttpMethods.POST,
    uri = "https://jsonplaceholder.typicode.com/posts",
    entity = HttpEntity(
      ContentTypes.`application/json`, s"$source" // the actual data you want to send
    )
  )

  def sendRequest(): Future[String] = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)
    val entityFuture: Future[HttpEntity.Strict] = responseFuture.flatMap(response => response.entity.toStrict(2.seconds))
    entityFuture.map(entity => entity.data.utf8String)
  }

  def main(args: Array[String]): Unit = {
    sendRequest().foreach(println)
  }
}
