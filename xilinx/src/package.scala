package object xilinx {
  implicit class XilinxBoolean(param: Boolean) {
    def str = if (param) "TRUE" else "FALSE"
  }
}