import mill._
import scalalib._
import scalafmt._

val defaultScalaVersion = "2.13.12"

def defaultVersions = Map(
  "chisel"        -> ivy"org.chipsalliance::chisel:6.5.0",
  "chisel-plugin" -> ivy"org.chipsalliance:::chisel-plugin:6.5.0",
  "chiseltest"    -> ivy"edu.berkeley.cs::chiseltest:6.0.0"
)

trait HasChisel extends SbtModule {
  def chiselIvy: Option[Dep] = Some(defaultVersions("chisel"))
  def chiselPluginIvy: Option[Dep] = Some(defaultVersions("chisel-plugin"))

  override def scalaVersion = defaultScalaVersion
  override def scalacOptions = super.scalacOptions() ++ 
    Agg(
      "-language:reflectiveCalls",
      "-Ytasty-reader"
    )

  override def ivyDeps = super.ivyDeps() ++ Agg(chiselIvy.get)
  override def scalacPluginIvyDeps = super.scalacPluginIvyDeps() ++ Agg(chiselPluginIvy.get) 
}

object `chisel-hw` extends ScalaModule with HasChisel with ScalafmtModule {

  override def millSourcePath = os.pwd

  override def ivyDeps = super.ivyDeps() ++ 
    Agg(
      defaultVersions("chiseltest")
    )

  override def scalacOptions = super.scalacOptions() ++ 
    Agg(
      "-deprecation",
      "-feature"
    )

  object test extends SbtModuleTests with TestModule.ScalaTest {
    override def ivyDeps = super.ivyDeps() ++ 
      Agg(
        defaultVersions("chiseltest")
      )

    override def scalacOptions = super.scalacOptions() ++ 
      Agg(
        "-deprecation",
        "-feature"
      )
  }

}