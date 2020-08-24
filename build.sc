import mill._
import mill.scalalib._
import mill.scalalib.publish._
import mill.scalalib.scalafmt._

val defaultVersions = Map(
  "chisel3" -> "3.3.2",
)

def getVersion(dep: String, org: String = "edu.berkeley.cs") = {
  val version = sys.env.getOrElse(dep + "Version", defaultVersions(dep))
  ivy"$org::$dep:$version"
}

object xilinx extends xilinx

class xilinx extends ScalaModule with PublishModule with ScalafmtModule {
  def scalaVersion = "2.12.12"

  def chisel3Module: Option[PublishModule] = None

  def chisel3IvyDeps = if(chisel3Module.isEmpty) Agg(
    getVersion("chisel3")
  ) else Agg.empty[Dep]

  def moduleDeps = super.moduleDeps ++ chisel3Module 

  def ivyDeps = super.ivyDeps() ++ chisel3IvyDeps

  def publishVersion = "0.1.0"

  def artifactName = "xilinx"

  def pomSettings = PomSettings(
    description = artifactName(),
    organization = "me.sequencer",
    url = "https://github.com/sequencer/xilinx",
    licenses = Seq(License.`BSD-3-Clause`),
    versionControl = VersionControl.github("sequencer", "xilinx"),
    developers = Seq(
      Developer("sequencer", "Jiuyang Liu", "https://github.com/sequencer")
    )
  )
}
