package model.metaheuristic

import scala.collection.immutable.ArraySeq

trait SecondFitness {
  def secondFitnessCalculusI(MCDPModel: MCDPModel,partCell:ArraySeq[ArraySeq[Int]],machineCell:ArraySeq[ArraySeq[Int]]):Int
  def secondFitnessCalculusD(MCDPModel: MCDPModel,partCell:ArraySeq[ArraySeq[Int]],machineCell:ArraySeq[ArraySeq[Int]]):Int
}
object SecondFitness extends SecondFitness{
  implicit class ConvertOptionToInt(option: Option[Int]){
    def result():Int=option match {
      case Some(value) => value
      case None =>0
    }
  }

  override def secondFitnessCalculusI(MCDPModel: MCDPModel, partCell: ArraySeq[ArraySeq[Int]], machineCell: ArraySeq[ArraySeq[Int]]): Int = {
    (0 to MCDPModel.c).toList.flatMap(first=> (0 to MCDPModel.m/2).toList.map(second=>calculusFitness(MCDPModel,second,first,partCell,machineCell))).sum
  }
  private def calculusFitness(MCDPModel: MCDPModel,second:Int,first:Int,partCell: ArraySeq[ArraySeq[Int]], machineCell: ArraySeq[ArraySeq[Int]]):Int=
    (0 to MCDPModel.p).toList.map(third=>
      MCDPModel.matrix.get(third.toString.concat(second.toString).toInt).result() *
        partCell.lift(third).flatMap(ele=>ele.lift(first)).result() *
        (1 -  machineCell.lift(second).flatMap(ele=>ele.lift(first)).result())).sum


  override def secondFitnessCalculusD(MCDPModel: MCDPModel, partCell: ArraySeq[ArraySeq[Int]], machineCell: ArraySeq[ArraySeq[Int]]): Int = {
    (0 to MCDPModel.c).toList.flatMap(first=> (MCDPModel.m/2 to MCDPModel.m).toList.map(second=> calculusFitness(MCDPModel,second,first,partCell,machineCell))).sum
  }
}