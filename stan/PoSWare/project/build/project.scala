import sbt._
class Project(info: ProjectInfo) extends DefaultProject(info)
      with IdeaProject {
  val h2 = "com.h2database" % "h2" % "1.2.127"
  val squeryl = "org.squeryl" % "squeryl_2.8.1" % "0.9.4-RC6"
  override def libraryDependencies = Set(
    h2,
    squeryl
  ) ++ super.libraryDependencies
}