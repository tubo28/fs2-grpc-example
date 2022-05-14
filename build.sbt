ThisBuild / scalaVersion := "2.13.8"
ThisBuild / organization := "com.example"

lazy val root = project.in(file("."))
  .settings(commonConfigs)
  .aggregate(server, client)

lazy val client = (project in file("client"))
  .settings(commonConfigs)
  .dependsOn(protobuf)

lazy val server = (project in file("server"))
  .settings(commonConfigs)
  .dependsOn(protobuf)

lazy val protobuf = (project in file("protobuf"))
  .enablePlugins(Fs2Grpc)
  .settings(
    libraryDependencies += "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
  )

lazy val commonConfigs = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % "2.7.0",
    "org.typelevel" %% "cats-effect" % "3.3.11",
    "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
    "io.grpc" % "grpc-services" % scalapb.compiler.Version.grpcJavaVersion,
  ),
  run / fork := true,
)
