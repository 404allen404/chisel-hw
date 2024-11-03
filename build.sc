import mill._
import scalalib._
import scalafmt._

val defaultScalaVersion = "2.13.15"

def defaultVersions = Map(
  "chisel"        -> ivy"org.chipsalliance::chisel:6.5.0",
  "chisel-plugin" -> ivy"org.chipsalliance:::chisel-plugin:6.5.0"
)

