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
  *
  * @param CINVCTRL_SEL          Enables the CINVCTRL_SEL pin to dynamically switch the polarity of the C pin.
  * @param DELAY_SRC             Select the delay source input to the IDELAYE2.
  *                              "IDATAIN": IDELAYE2 chain input is IDATAIN.
  *                              "DATAIN": IDELAYE2 chain input is DATAIN.
  * @param HIGH_PERFORMANCE_MODE When true, this attribute reduces the output jitter.
  *                              When false, power consumption is reduced.
  *                              The difference in power consumption is quantified in the Xilinx Power Estimator tool.
  * @param IDELAY_TYPE           Sets the type of tap delay line.
  *                              "FIXED": Sets a static delay value.
  *                              "VARIABLE": Dynamically adjust (increment/decrement) delay value.
  *                              "VAR_LOAD": Dynamically loads tap values.
  *                              "VAR_LOAD_PIPE": Pipelined dynamically loadable tap values.
  * @param PIPE_SEL              Specifies the fixed number of delay taps in fixed mode or the initial starting number of taps in "VARIABLE" mode (input path).
  *                              When IDELAY_TYPE is set to "VAR_LOAD" or "VAR_LOAD_PIPE" mode, this value is ignored.
  * @param REFCLK_FREQUENCY      Sets the tap value (in MHz) used by the timing analyzer for static timing analysis and functional/timing simulation.
  *                              The frequency of REFCLK must be within the given datasheet range to guarantee the tap-delay value and performance.
  * @param SIGNAL_PATTERN        Causes the timing analyzer to account for the appropriate amount of delay-chain jitter in the data or clock path.
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

  /** All control inputs to IDELAYE2 primitive (RST, CE, and INC) are synchronous to the clock input (C).
    * A clock must be connected to this port when IDELAYE2 is configured in "VARIABLE", "VAR_LOAD" or "VAR_LOAD_PIPE" mode.
    * [[C]] can be locally inverted, and must be supplied by a global or regional clock buffer.
    * This clock should be connected to the same clock in the SelectIO logic resources (when using ISERDESE2 and OSERDESE2,
    * [[C]] is connected to CLKDIV).
    * */
  val C = IO(Input(Bool()))
  /** Active-High enable for increment/decrement function. */
  val CE = IO(Input(Bool()))
  /** The [[CINVCTRL]] pin is used for dynamically switching the polarity of [[C]] pin.
    * This is for use in applications when glitches are not an issue.
    * When switching the polarity, do not use the [[IDELAYE2]] control pins for two clock cycles.
    * */
  val CINVCTRL = IO(Input(Bool()))
  /** Counter value from FPGA logic for dynamically loadable tap value input.
    * */
  val CNTVALUEIN = IO(Input(UInt(5.W)))
  /** The CNTVALUEOUT pins are used for reporting the dynamically switching value of the delay element.
    * [[CNTVALUEOUT]] is only available when [[IDELAYE2]] is in
    * "VAR_LOAD" or "VAR_LOAD_PIPE" mode.
    * */
  val CNTVALUEOUT = IO(Output(UInt(5.W)))
  /** The DATAIN input is directly driven by the FPGA logic providing a logic accessible delay line.
    * The data is driven back into the FPGA logic through the [[DATAOUT]] port with a delay set by the [[IDELAY_VALUE]].
    * [[DATAIN]] can be locally inverted. The data cannot be driven to an I/O.
    * */
  val DATAIN = IO(Input(Bool()))
  /** Delayed data from either the [[IDATAIN]] or [[DATAIN]] input paths. [[DATAOUT]] connects to an [[ISERDESE2]],
    * input register or FPGA logic.
    * */
  val DATAOUT = IO(Output(Bool()))
  /** The IDATAIN input is driven by its associated I/O. The data
    * can be driven to either an ISERDESE2 or input register
    * block, directly into the FPGA logic, or to both through the
    * DATAOUT port with a delay set by the IDELAY_VALUE.
    * */
  val IDATAIN = IO(Input(Bool()))
  /** Selects whether tap delay numbers will be incremented or decremented.
    * INC = 1 increments when CE is high. INC=0 decrements.
    * */
  val INC = IO(Input(Bool()))
  /** In “VARIABLE” mode, loads the value set by the IDELAY_VALUE attribute.
    * The default value is zero.
    * In “VAR_LOAD” mode, loads the value of CNTVALUEIN.
    * The value present at CNTVALUEIN[4:0] will be the new tap value.
    * In “VAR_LOAD_PIPE” mode, loads the value currently in the pipeline register.
    * The value present in the pipeline register will be the new tap value.
    * */
  val LD = IO(Input(Bool()))
  /** When High, loads the pipeline register with the value currently on the CNTVALUEIN pins.
    * */
  val LDPIPEEN = IO(Input(Bool()))
  /** When high, resets the pipeline register to all zeros. Only used in "VAR_LOAD_PIPE" mode.
    * */
  val REGRST = IO(Input(Bool()))
}