package memory

import chisel3._
import chisel3.util._

class SRAMWrapper(val memSize: Int, 
                  val dataWidth: Int,
                  val numReadPorts: Int,
                  val numWritePorts: Int,
                  val numReadWritePorts: Int)
extends Module {

  val io = IO(
    new SRAMInterface(memSize, Vec(dataWidth / 8, UInt(8.W)),
                      numReadPorts, numWritePorts,
                      numReadWritePorts, true)
  )

  io :<>= SRAM.masked(memSize, Vec(dataWidth / 8, UInt(8.W)),
                      numReadPorts, numWritePorts, numReadWritePorts)

}

object SRAMWrapperMain extends App {
  val memSize = if (args.length > 0) args(0).toInt else 131072
  val dataWidth = if (args.length > 1) args(1).toInt else 64
  val numReadPorts = if (args.length > 2) args(2).toInt else 1
  val numWritePorts = if (args.length > 3) args(3).toInt else 1
  val numReadWritePorts = if (args.length > 4) args(4).toInt else 0

  emitVerilog(
    new SRAMWrapper(memSize, dataWidth, numReadPorts, numWritePorts, numReadWritePorts),
    Array(
      "--target-dir", "generated/SRAMWrapper",
      "--help"
    )
  )
}