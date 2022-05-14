package example.server

import cats.effect.IO
import io.grpc.Metadata
import io.grpc.examples.helloworld.helloworld.{GreeterFs2Grpc, HelloReply, HelloRequest}

class GreeterServiceImpl extends GreeterFs2Grpc[IO, Metadata] {

  override def sayHello(request: HelloRequest, ctx: Metadata): IO[HelloReply] =
    IO.pure(HelloReply.of(s"Hello, ${request.name}")) <* IO.println(s"processed ${request.name}")

}
