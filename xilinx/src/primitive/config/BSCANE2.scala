package xilinx.primitive.config

import chisel3._
import chisel3.experimental._

/** The BSCANE2 primitive allows access between the internal FPGA logic and the JTAG
  * boundary scan logic controller. This allows for communication between the internal running
  * design and the dedicated JTAG test access port (TAP) pins of the FPGA. The [[BSCANE2]]
  * primitive must be instantiated to gain internal access to the JTAG pins. The BSCANE2
  * primitive is not needed for normal JTAG operations that use direct access from the JTAG
  * pins to the TAP controller. The [[BSCANE2]] is automatically added to a design when using the
  * Vivado Logic Analyzer, or when using indirect flash programming in the Vivado Device
  * Programmer.
  *
  * 7 series FPGAs and UltraScale of BSCANE2 primitive are identical.
  *
  * @param JTAG_CHAIN Value for USER command.
  */
class BSCANE2(JTAG_CHAIN: Double = 1) extends ExtModule(Map("JTAG_CHAIN" -> JTAG_CHAIN)) {
  require(1 to 4 contains JTAG_CHAIN)
  /** Asserted when TAP controller is in Capture-DR state */
  val CAPTURE = IO(Output(Bool()))
  /** Gated [[TCK]] output. When [[SEL]] is asserted, [[DRCK]] toggles when [[CAPTURE]] or [[SHIFT]] are asserted. */
  val DRCK = IO(Output(Bool()))
  /** Asserted when TAP controller is in Test-Logic-Reset state. */
  val RESET = IO(Output(Bool()))
  /** Asserted when TAP controller is in Run-Test/Idle state. */
  val RUNTEST = IO(Output(Bool()))
  /** Asserted when the USER instruction (USER1 – USER4) that corresponds to
    * the BSCANE2 instance's JTAG_CHAIN attribute (1–4) is loaded as
    * the active instruction in the JTAG Instruction register.
    */
  val SEL = IO(Output(Bool()))
  /** Asserted when TAP controller is in Shift-DR state. */
  val SHIFT = IO(Output(Bool()))
  /** Test Clock. Primitive output from external TAP pin to FPGA internal logic. */
  val TCK = IO(Output(Clock()))
  /** Test Data Input. Primitive output from external TAP pin to FPGA internal logic. */
  val TDI = IO(Output(Bool()))
  /** Test Data Output. Primitive input from User internal scan register to
    * a flip-flop that registers the primitive input on the falling edge of [[TCK]].
    * The output of the flip-flop is forwarded to the external TAP TDO pin.
    */
  val TDO = IO(Input(Bool()))
  /** Test Mode Select. Primitive output from external TAP pin to FPGA internal logic. */
  val TMS = IO(Output(Bool()))
  /** Asserted when TAP controller is in Update state. Internal logic should update from
    * the internal scan register on the rising edge of the [[UPDATE]] signal.
    */
  val UPDATE = IO(Output(Bool()))
}

object BSCANE2 {
  def apply(jtagChain: Int) = Module(new BSCANE2(jtagChain.toDouble))
}