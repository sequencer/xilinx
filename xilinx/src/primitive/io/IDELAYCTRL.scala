package xilinx
package io

import chisel3._
import chisel3.experimental._

/**
  * At least one of these design elements must be instantiated when using IDELAYE2 or ODELAYE2.
  * The IDELAYCTRL module provides a reference clock input that allows internal circuitry to derive
  * a voltage bias, independent of PVT (process, voltage, and temperature) variations, to define
  * precise delay tap values for the associated IDELAYE2 and ODELAYE2 components. Use the
  * IODELAY_GROUP attribute when instantiating this component to distinguish which
  * IDELAYCTRL is associated with which IDELAYE2 and ODELAYE2.
  **/
class IDELAYCTRL extends ExtModule {
  /** The ready ([[RDY]]) signal indicates when the IDELAYE2 and ODELAYE2 modules in the specific region are calibrated.
    * The RDY signal is deasserted if [[REFCLK]] is held High or Low for one clock period or more.
    * If [[RDY]] is deasserted Low, the [[IDELAYCTRL]] module must be reset. If not needed, [[RDY]] to be unconnected/ignored.
    */
  val RDY = IO(Output(Bool()))
  /** Time reference to [[IDELAYCTRL]] to calibrate all IDELAYE2 and ODELAYE2 modules in the same region.
    * [[REFCLK]] can be supplied directly from a user-supplied source
    * or the MMCME2/PLLE2 and must be routed on a global clock buffer.
    * */
  val REFCLK = IO(Input(Clock()))
  /** Active-High asynchronous reset. To ensure proper IDELAYE2 and ODELAYE2 operation,
    * IDELAYCTRL must be reset after configuration and the REFCLK signal is stable.
    * A reset pulse width Tidelayctrl_rpw is required.
    */
  val RST = IO(Input(Bool()))
}