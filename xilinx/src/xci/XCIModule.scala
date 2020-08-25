package xilinx.xci

import chisel3.BlackBox

import scala.xml.{Elem, XML}

abstract class XCIModule extends BlackBox {
  def xci: Elem

  def xciString = {
    val writer = new java.io.StringWriter
    XML.write(writer, xci, "utf-8", xmlDecl = true, null).toString
    writer.toString
  }
}