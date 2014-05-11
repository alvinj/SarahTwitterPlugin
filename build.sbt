name := "Twitter"

version := "0.1"

scalaVersion := "2.10.3"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

// Akka needs to be in sync with whatever Sarah2 uses
libraryDependencies ++= Seq("com.thoughtworks.paranamer" % "paranamer" % "2.4.1",
                            "com.typesafe.akka" %% "akka-actor" % "2.3.2")


