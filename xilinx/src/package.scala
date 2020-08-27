import chisel3.experimental.ChiselAnnotation
import firrtl.annotations.Annotation

package object xilinx {
  implicit class XilinxBoolean(param: Boolean) {
    def str = if (param) "TRUE" else "FALSE"
  }
  def markDebug(data: chisel3.Data) = {
    chisel3.experimental.requireIsHardware(data)
    chisel3.experimental.annotate(
      new ChiselAnnotation {
        override def toFirrtl: Annotation = firrtl.AttributeAnnotation(data.toTarget, """mark_debug = "yes"""")
      }
    )
    data.toTarget
  }
}