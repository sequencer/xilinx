package xilinx.primitive.clock

import chisel3._
import chisel3.experimental._

/** Primitive: Global Clock Buffer with Clock Enable
  *
  * This design element is a global clock buffer with a single gated input. Its O output is "0" when
  * clock enable (CE) is Low (inactive). When clock enable (CE) is High, the I input is transferred to
  * the O output.
  **/
class BUFGCE extends ExtModule {
  /** Clock output. */
  val O = IO(Output(Bool()))
  /** Clock buffer active-High enable. */
  val CE = IO(Input(Bool()))
  /** Clock input. */
  val I = IO(Input(Bool()))
}