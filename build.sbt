name := """PlayScalaSample"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, CodegenPlugin)

scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  guice,
  "com.google.inject" % "guice" % "5.0.1",
  "com.google.inject.extensions" % "guice-assistedinject" % "5.0.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.3",
  "org.postgresql" % "postgresql" % "42.2.23"
)

routesGenerator := InjectedRoutesGenerator

wartremoverErrors ++= Seq()
wartremoverWarnings ++= Warts.unsafe ++ Warts.all
wartremoverExcluded ++= (Compile / routes).value
wartremoverExcluded += (Compile / baseDirectory).value / "target"

Compile / sourceGenerators += slickCodegen
slickCodegenDatabaseUrl := "jdbc:postgresql://localhost:5432/mydb"
slickCodegenDatabaseUser := "postgres"
slickCodegenDatabasePassword := "postgres"
slickCodegenDriver := slick.jdbc.PostgresProfile
slickCodegenJdbcDriver := "org.postgresql.Driver"
slickCodegenOutputPackage := "entities"
slickCodegenExcludedTables := Seq("schema_version")

// Adds additional packages into Twirl
// TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
