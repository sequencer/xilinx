package xilinx.config

import chisel3._
import chisel3.experimental._

/**
  * Every I/O block contains a programmable absolute delay element called IDELAYE2. The
  * IDELAYE2 can be connected to an input register/ISERDESE2 or driven directly into FPGA logic.
  * The IDELAYE2 is a 31-tap, wraparound, delay element with a calibrated tap resolution. Refer to
  * the 7 series FPGA Data Sheet for delay values. The IDELAYE2 allows incoming signals to be
  * delayed on an individual basis. The tap delay resolution is varied by selecting an IDELAYCTRL
  * reference clock from the range specified in the 7 series FPGA Data Sheet.
  **/
class IDELAYE2(CINVCTRL_SEL: Boolean = false,
               DELAY_SRC: String = "IDATAIN",
               HIGH_PERFORMANCE_MODE: Boolean = false,
               IDELAY_TYPE: String = "FIXED",
               IDELAY_VALUE: Int = 0,
               PIPE_SEL: Boolean = false,
               REFCLK_FREQUENCY: Double = 200.0,
               SIGNAL_PATTERN: String = "DATA") extends ExtModule {
  /** TODO: a XilinxPrimitive class for wrapper parseBoolParam and automaticly [[params]] generation. */
  def parseBoolParam(param: Boolean) = if (param) "TRUE" else "FLASE"

  override val params: Map[String, Param] = Map(
    "CINVCTRL_SEL" -> parseBoolParam(CINVCTRL_SEL),
    "DELAY_SRC" -> DELAY_SRC,
    "HIGH_PERFORMANCE_MODE" -> parseBoolParam(HIGH_PERFORMANCE_MODE),
    "IDELAY_TYPE" -> IDELAY_TYPE,
    "IDELAY_VALUE" -> IDELAY_VALUE,
    "PIPE_SEL" -> parseBoolParam(PIPE_SEL),
    "REFCLK_FREQUENCY" -> REFCLK_FREQUENCY,
    "SIGNAL_PATTERN" -> SIGNAL_PATTERN
  )

  require(Seq("IDATAIN", "DATAIN").contains(DELAY_SRC))
  require(Seq("FIXED", "VARIABLE", "VAR_LOAD", "VAR_LOAD_PIPE").contains(IDELAY_TYPE))
  require(0 to 31 contains (IDELAY_VALUE))
  require((REFCLK_FREQUENCY >= 190 & REFCLK_FREQUENCY <= 210) | (REFCLK_FREQUENCY >= 290 & REFCLK_FREQUENCY <= 310))
  require(Seq("DATA", "CLOCK").contains(SIGNAL_PATTERN))

  val C = IO(Input(Bool()))
  val CE = IO(Input(Bool()))
  val CINVCTRL = IO(Input(Bool()))
  val CNTVALUEIN = IO(Input(UInt(5.W)))
  val CNTVALUEOUT = IO(Output(UInt(5.W)))
  val DATAIN = IO(Input(Bool()))
  val DATAOUT = IO(Output(Bool()))
  val IDATAIN = IO(Input(Bool()))
  val INC = IO(Input(Bool()))
  val LD = IO(Input(Bool()))
  val LDPIPEEN = IO(Input(Bool()))
  val REGRST = IO(Input(Bool()))
}