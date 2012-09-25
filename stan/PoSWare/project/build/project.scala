import sbt._
class Project(info: ProjectInfo) extends DefaultProject(info)
      with IdeaProject {
  val mysql = "mysql" % "mysql-connector-java" % "5.1.15"
  val squeryl = "org.squeryl" % "squeryl_2.8.1" % "0.9.4-RC6"
  override def libraryDependencies = Set(
    mysql,
    squeryl
  ) ++ super.libraryDependencies
}