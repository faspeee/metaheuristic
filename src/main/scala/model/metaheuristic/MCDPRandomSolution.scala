package model.metaheuristic

import model.util.CommonMethod

trait MCDPRandomSolution{
  def createRandomSolution():Unit
  def getMachine:Array[Array[Int]]
  def getPart:Array[Array[Int]]
}
object MCDPRandomSolution {
  def apply(MCDPModel: Option[MCDPModel]): MCDPRandomSolution = new MCDPRandomSolutionImp(MCDPModel)
  private class MCDPRandomSolutionImp(mCDPModel: Option[MCDPModel]) extends MCDPRandomSolution{
    var machineCell: List[Int] = List[Int]()
    val mCDPModelA: MCDPModel =  CommonMethod.getMcpdModel(mCDPModel)
    val machineCellArray: Array[Array[Int]] = Array.ofDim[Int](mCDPModelA.m,mCDPModelA.c)
    val partCellArray: Array[Array[Int]] = Array.ofDim[Int](mCDPModelA.p,mCDPModelA.c)
    override def createRandomSolution(): Unit = {
      iterateM()
      iteraMachineCellFinal(machineCell)
      iteratePartCell()
      iterateFinal()
    }

    @scala.annotation.tailrec
    private def iterateM(p:Int=0): Unit = mCDPModel match {
      case Some(value) if p<value.m=>
        val N = (Math.random * (value.c - 1 + 1) + 1).toInt
        val l = iteraMachineCell(machineCell,N)
        if(l<value.mMax) {
          machineCell=machineCell:+N
          iterateM(p+1)
        }else
          iterateM(p)
      case None =>
    }

    @scala.annotation.tailrec
    private def iteraMachineCell(machineCell:List[Int], N:Int, l:Int=0):Int= machineCell match {
      case ::(head, next) if head == N => iteraMachineCell(next,N,l+1)
      case ::(_, next)  =>iteraMachineCell(next,N,l)
      case Nil =>l
    }

    def iteraMachineCellFinal(machineCell:List[Int]): Unit =
      machineCell.zipWithIndex.foreach(x=>machineCellArray(x._2)(x._1)=1)

    def iteratePartCell(): Unit =
      0 to mCDPModelA.p foreach(j=>0 to mCDPModelA.c foreach(k=>partCellArray(j)(k)=0))

    def iterateFinal(): Unit ={
      0 to mCDPModelA.p foreach(j=>{
        val maxIndex = CommonMethod.iteraCellCount(tempPartAndCellCount(j).zipWithIndex)
        partCellArray(j)(maxIndex)=1
      })
    }
    def tempPartAndCellCount(j:Int): List[Int] =
      (0 to mCDPModelA.c).toList map(k=>
        (0 to mCDPModelA.m).toList.map(i=> machineCellArray(i)(k) * mCDPModelA.matrix.get(i.toString.concat(k.toString).toInt).max)
          .sum)



    override def getMachine: Array[Array[Int]] = machineCellArray

    override def getPart: Array[Array[Int]] = partCellArray
  }
}
