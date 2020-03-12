package xilinx
package config

import chisel3._
import chisel3.experimental._

/**
  * This design element allows access to and from internal logic by the JTAG Boundary Scan logic
  * controller. This allows for communication between the internal running design and the dedicated
  * JTAG pins of the FPGA. Each instance of this design element will handle one JTAG USER
  * instruction (USER1 through USER4) as set with the [[JTAG_CHAIN]] attribute.
  *
  * To handle all four USER instructions, instantiate four of these elements, and set the JTAG_CHAIN
  * attribute appropriately.
  *
  * For specific information on boundary scan for an architecture, see the Configuration User Guide
  * for the specific device.
  *
  * @param JTAG_CHAIN Value for USER command.
  **/
class BSCANE2(JTAG_CHAIN: Double = 1) extends ExtModule(Map("JTAG_CHAIN" -> JTAG_CHAIN)) {
  require(1 to 4 contains JTAG_CHAIN)
  /** [[CAPTURE]] output from TAP controller. */
  val CAPTURE = IO(Output(Bool()))
  /** Gated [[TCK]] output. When [[SEL]] is asserted, [[DRCK]] toggles when [[CAPTURE]] or [[SHIFT]] are asserted. */
  val DRCK = IO(Output(Bool()))
  /** Reset output for TAP controller. */
  val RESET = IO(Output(Bool()))
  /** Output asserted when TAP controller is in Run Test/Idle state. */
  val RUNTEST = IO(Output(Bool()))
  /** USER instruction active output. */
  val SEL = IO(Output(Bool()))
  /** [[SHIFT]] output from TAP controller. */
  val SHIFT = IO(Output(Bool()))
  /** Test Clock output. Fabric connection to TAP Clock pin. */
  val TCK = IO(Output(Bool()))
  /** Test Data Input ([[TDI]]) output from TAP controller. */
  val TDI = IO(Output(Bool()))
  /** Test Data Output ([[TDO]]) input for USER function. */
  val TDO = IO(Input(Bool()))
  /** Test Mode Select output. Fabric connection to TAP. */
  val TMS = IO(Output(Bool()))
  /** [[UPDATE]] output from TAP controller. */
  val UPDATE = IO(Output(Bool()))
}
