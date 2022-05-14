package example.client

import cats.effect.{IO, IOApp}
import cats.implicits._

import scala.concurrent.duration._

object Main extends IOApp.Simple {

  override def run: IO[Unit] =
    GreeterClient.resource().use { client =>
      Seq.range(1, 10).map { i =>
        val name = s"Alice$i"
        for {
          _ <- IO.println(s"send $name")
          _ <- IO.sleep(1.second)
          reply <- client.sayHello(name)
          _ <- IO.println(s"received $reply")
        } yield ()
      }.sequence_
    }

}
