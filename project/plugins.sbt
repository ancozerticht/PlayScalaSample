addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.8")
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8-scaffold" % "0.13.1")
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.4.16")
addSbtPlugin("com.github.tototoshi" % "sbt-slick-codegen" % "1.4.0")

libraryDependencies += "org.postgresql" % "postgresql" % "42.2.23"
