package arithmetic

import chisel3._
import chisel3.util._

class Adder(val n:Int) extends Module {
  val io = IO(new Bundle {
    val A    = Input(UInt(n.W))
    val B    = Input(UInt(n.W))
    val Cin  = Input(UInt(1.W))
    val Sum  = Output(UInt(n.W))
    val Cout = Output(UInt(1.W))
  })

  val FAs   = Array.fill(n)(Module(new FullAdder()).io)
  val carry = Wire(Vec(n+1, UInt(1.W)))
  val sum   = Wire(Vec(n, UInt(1.W)))

  carry(0) := io.Cin

  for (i <- 0 until n) {
    FAs(i).a   := io.A(i)
    FAs(i).b   := io.B(i)
    FAs(i).cin := carry(i)
    carry(i+1) := FAs(i).cout
    sum(i)     := FAs(i).sum
  }

  io.Sum  := sum.asUInt
  io.Cout := carry(n)

}

object AdderMain extends App {
  val n = if (args.length > 0) args(0).toInt else 8
  emitVerilog(
    new Adder(n),
    Array(
      "--target-dir", "generated/Adder"
    )
  )
}
