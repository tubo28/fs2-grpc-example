package example.server

import cats.effect.{IO, Resource, ResourceApp}
import fs2.grpc.syntax.all._
import io.grpc.examples.helloworld.helloworld.GreeterFs2Grpc
import io.grpc.netty.NettyServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService

object Main extends ResourceApp.Forever {

  override def run(args: List[String]): Resource[IO, Unit] =
    for {
      service <- GreeterFs2Grpc.bindServiceResource(new GreeterServiceImpl)
      server <- NettyServerBuilder.forPort(9999).addService(service)
        .addService(ProtoReflectionService.newInstance())
        .resource[IO]
      _ <- Resource.eval(IO.println("server start"))
    } yield server.start()

}
