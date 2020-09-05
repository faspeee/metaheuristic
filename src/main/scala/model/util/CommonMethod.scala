package model.util

import model.metaheuristic.MCDPModel

import scala.util.Random

object CommonMethod {
  def getMcpdModel(mCDPModel: Option[MCDPModel]): MCDPModel =
    mCDPModel.toList.foldLeft(MCDPModel(0,0,0,0,Map.empty))((_, y)=>y)

  @scala.annotation.tailrec
  def iteraCellCount(cellCount:List[(Int,Int)], maxIndex:Int=0):Int= cellCount match {
    case first::second::next if second._1>first._1 => iteraCellCount(second::next,second._2)
    case Nil =>maxIndex
  }
  def calculusDRandDS(mCDPModelA:MCDPModel):(Int,Int)={
    val rm  = Random
    val DR = rm.nextInt(1 - 0 + 1) + 1
    (DR,rm.nextInt(mCDPModelA.m  - DR + 1) + 1)
  }
}
