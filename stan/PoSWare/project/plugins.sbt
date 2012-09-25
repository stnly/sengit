
addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.1.0")

libraryDependencies  ++=  Seq(
  "org.squeryl" %% "squeryl" % "0.9.5-2",
  "com.h2database" % "h2" % "1.2.127"
)