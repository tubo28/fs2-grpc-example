package example.client

import cats.effect.{IO, Resource}
import fs2.grpc.syntax.all._
import io.grpc.Metadata
import io.grpc.examples.helloworld.helloworld.{GreeterFs2Grpc, HelloRequest}
import io.grpc.netty.NettyChannelBuilder

class GreeterClient(client: GreeterFs2Grpc[IO, Metadata]) {

  def sayHello(name: String): IO[String] =
    client
      .sayHello(HelloRequest.of(name), new Metadata())
      .map(_.message)

}

object GreeterClient {

  def resource(): Resource[IO, GreeterClient] =
    for {
      channel <- NettyChannelBuilder
        .forTarget("localhost:9999")
        .usePlaintext()
        .resource[IO]
      client <- GreeterFs2Grpc.stubResource[IO](channel)
    } yield new GreeterClient(client)

}
