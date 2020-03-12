package xilinx
package io

import chisel3._
import chisel3.experimental._

/** The ISERDESE2 in 7 series FPGAs is a dedicated serial-to-parallel converter with specific
  * clocking and logic features designed to facilitate the implementation of high-speed source-
  * synchronous applications. The ISERDESE2 avoids the additional timing complexities encountered
  * when designing deserializers in the FPGA fabric. ISERDESE2 features include:
  *
  * 1. Dedicated Deserializer/Serial-to-Parallel Converter, which enables high-speed data transfer
  * without requiring the FPGA fabric to match the input data frequency. This converter supports
  * both single data rate (SDR) and double data rate (DDR) modes. In SDR mode, the serial-to-
  * parallel converter creates a 2-, 3-, 4-, 5-, 6-, 7-, or 8-bit wide parallel word. In DDR mode, the
  * serial-to-parallel converter creates a 4-, 6-, 8-, 10-, or 14-bit-wide parallel word.
  *
  * 2. Bitslip Submodule, which lets designers reorder the sequence of the parallel data stream going
  * into the FPGA fabric. This can be used for training source-synchronous interfaces that include
  * a training pattern.
  *
  * 3. Dedicated Support for Strobe-based Memory Interfaces, including the OCLK input pin, to
  * handle the strobe-to-FPGA clock domain crossover entirely within the ISERDESE2 block. This
  * allows for higher performance and a simplified implementation.
  *
  * 4. Dedicated Support for Networking Interfaces
  *
  * 5. Dedicated Support for Memory Interfaces
  */

class ISERDESE2(JTAG_CHAIN: String = "DDR",
                DATA_WIDTH: Double = 4,
                DYN_CLKDIV_INV_EN: Boolean = false,
                DYN_CLK_INV_EN: Boolean = false,
                INIT_Q1: Int = 0,
                INIT_Q2: Int = 0,
                INIT_Q3: Int = 0,
                INIT_Q4: Int = 0,
                INTERFACE_TYPE: String = "MEMORY",
                IOBDELAY: String = "NONE",
                NUM_CE: Double = 2,
                OFB_USED: Boolean = false,
                SERDES_MODE: String = "MASTER",
                SRVAL_Q1: Int = 0,
                SRVAL_Q2: Int = 0,
                SRVAL_Q3: Int = 0,
                SRVAL_Q4: Int = 0,
               ) extends ExtModule {

}
