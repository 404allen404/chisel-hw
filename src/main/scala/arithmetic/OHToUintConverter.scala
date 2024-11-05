package arithmetic

import chisel3._
import chisel3.util._

class OHToUintConverter(val width: Int) extends Module {
  val io = IO(new Bundle{
    val in  = Input(UInt(width.W))
    val out = Output(UInt(log2Ceil(width).W))
  })

  io.out := OHToUInt(io.in)

}

object OHToUintConverterMain extends App {
  val width = if (args.length > 0) args(0).toInt else 8
  emitVerilog(
    new OHToUintConverter(width),
    Array(
      "--target-dir", "generated/OHToUintConverter"
    )
  )
}