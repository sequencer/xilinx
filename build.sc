import mill._
import mill.scalalib._
import mill.scalalib.publish._
import mill.scalalib.scalafmt._

object xilinx extends ScalaModule with PublishModule with ScalafmtModule {
  def scalaVersion = "2.12.10"

  override def ivyDeps = Agg(
    ivy"edu.berkeley.cs::chisel3:latest.integration",
  )

  def publishVersion = "0.1.0"

  def pomSettings = PomSettings(
    description = "Xilinx Macros",
    organization = "me.sequencer",
    url = "https://github.com/sequencer/xilinx",
    licenses = Seq(License.`BSD-3-Clause`),
    versionControl = VersionControl.github("sequencer", "xilinx"),
    developers = Seq(
      Developer("sequencer", "Jiuyang Liu", "https://github.com/sequencer")
    )
  )
}
