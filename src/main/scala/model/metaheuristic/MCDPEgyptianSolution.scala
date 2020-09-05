package model.metaheuristic

import model.util.CommonMethod

import scala.collection.immutable.ArraySeq
import scala.util.Random

trait MCDPEgyptianSolution{
  def pebblesTossing():Unit
  def rollingTwigs():Unit
  def rollingTwigs2():Unit
  def changeAngle():Unit
  def changeAngle2():Unit
}
object MCDPEgyptianSolution {
  def apply(mCDPModel: Option[MCDPModel]): MCDPEgyptianSolution = new MCDPEgyptianSolutionImp(mCDPModel)
  private class MCDPEgyptianSolutionImp(mCDPModel: Option[MCDPModel]) extends MCDPEgyptianSolution{
    val mCDPModelA: MCDPModel = CommonMethod.getMcpdModel(mCDPModel)
    var machineCellArray: ArraySeq[ArraySeq[Int]] =ArraySeq[ArraySeq[Int]]()
    var partCellArray: ArraySeq[ArraySeq[Int]] = ArraySeq[ArraySeq[Int]]()
    var machineCell: List[Int] = List[Int]()
    override def pebblesTossing(): Unit = {
      val cell: List[Int] = List[Int]() //creo Array de celdas

      // Random machine
      val rm  = Random
      val hitPoint: Int = rm.nextInt(mCDPModelA.m - 1 + 1) + 1
      val aleatorioParaPT: Int = mCDPModelA.m  - hitPoint
      val pt: Int = rm.nextInt(aleatorioParaPT - 0 + 1) + 0

    }
    private def addCell(pt:Int,rm:Random):List[Int]={
      for{
        _ <- 0 to pt
      }yield rm.nextInt(mCDPModelA.c-1+1)+1
    }.toList

    private def removeAndAddCell(pt:Int,hitPoint:Int,cell:List[Int]):List[Int]= {
      @scala.annotation.tailrec
      def _removeAndAddCell(hitPoint:Int, machineCell:List[Int], index:Int=0):List[Int] = index match {
        case x if x<pt => _removeAndAddCell(hitPoint+1,machineCell.updated(hitPoint-1,cell(x)),x+1)
        case _ => machineCell
      }
      _removeAndAddCell(hitPoint,machineCell)
    }
    private def changeMachineCell(): ArraySeq[ArraySeq[Int]] ={
      @scala.annotation.tailrec
      def _changeMachineCell(machineCell:ArraySeq[(ArraySeq[Int],Int)], machineCellUpdate:ArraySeq[ArraySeq[Int]]=ArraySeq.empty):ArraySeq[ArraySeq[Int]]= machineCell match {
        case first+:next =>_changeMachineCell(next, _changeMachineCell2(first._1.zipWithIndex,first._2,machineCellUpdate))
        case _ =>machineCellUpdate
      }
      @scala.annotation.tailrec
      def _changeMachineCell2(machineCellArray:ArraySeq[(Int,Int)], index:Int, machineCellUpdate:ArraySeq[ArraySeq[Int]]):ArraySeq[ArraySeq[Int]]= machineCellArray match {
        case first+:next  if first._1==1 && machineCell(index)!=first._2+1=> _changeMachineCell2(next,index,updateMachine(machineCellUpdate,index,0))
        case first+:next  if first._1==1 && machineCell(index)==first._2+1=> _changeMachineCell2(next,index,updateMachine(machineCellUpdate,index,1))
        case first+:next  if first._1==0 && machineCell(index)==first._2+1=> _changeMachineCell2(next,index,updateMachine(machineCellUpdate,index,1))
        case _+:next => _changeMachineCell2(next,index,machineCellUpdate)
        case _ => machineCellUpdate
      }
      _changeMachineCell(machineCellArray.zipWithIndex)
    }
    private def updateMachine(machineCellUpdate:ArraySeq[ArraySeq[Int]],index:Int,value:Int)={
      machineCellUpdate.lift(index) match {
        case Some(array) => machineCellUpdate.updated(index,array:+value)
        case None =>machineCellUpdate
      }
    }

    private def generatePartCell(): ArraySeq[ArraySeq[Int]] =
      0 to mCDPModelA.p map(_=>0 to mCDPModelA.c map(_=>0) to ArraySeq) to ArraySeq

    private def iterateTempPart(): ArraySeq[ArraySeq[Int]] =
      (0 to mCDPModelA.p).toList flatMap(_=> {
       val cellCount= (0 to mCDPModelA.c).toList.map(secondIndex=>
          (0 to mCDPModelA.m).toList map(thirdIndex=>
            machineCellArray.lift(thirdIndex).flatMap(element=>element.lift(secondIndex)
              .map(_*mCDPModelA.matrix.get(thirdIndex.toString.concat(secondIndex.toString).toInt).max)).sum))
        (0 to mCDPModelA.p).toList map(thirdIndex=>{
          val maxIndex = CommonMethod.iteraCellCount(cellCount(thirdIndex).zipWithIndex)
          partCellArray.updated(thirdIndex,updatePartCell(thirdIndex,maxIndex))
        })
      }) flatMap (_.toList) to ArraySeq

    private def updatePartCell(j:Int,maxIndex:Int):ArraySeq[Int]={
      partCellArray.lift(j).map(array=> array.lift(maxIndex) match {
        case Some(_) =>array.updated(maxIndex,1)
        case None => array
      }).toList.flatMap(_.toList) to ArraySeq
    }
    override def rollingTwigs(): Unit = {
      val rm  = Random
      val hitPoint: Int = rm.nextInt(mCDPModelA.m - 1 + 1) + 1
      val (dr,ds) =CommonMethod.calculusDRandDS(mCDPModelA)
      updateMachineRolling(ds,hitPoint,dr)
    }
    private def updateMachineRolling(iterator:Int,hitPoint:Int,DR:Int):List[Int]= {
      @scala.annotation.tailrec
      def _removeAndAddCellFirst(hitPoint:Int, machineCell:List[Int], index:Int=0):List[Int] = index match {
        case x if x<iterator => _removeAndAddCellFirst(hitPoint,updateListFirst(hitPoint),x+1)
        case _ => machineCell
      }
      def _removeAndAddCellSecond(mcdpM:Int, machineCell:List[Int], index:Int=0):List[Int] = index match {
        case x if x<iterator => _removeAndAddCellFirst(mcdpM,updateListSecond(mcdpM),x+1)
        case _ => machineCell
      }
      DR match {
        case 1 => _removeAndAddCellFirst(hitPoint,machineCell)
        case 0=>_removeAndAddCellSecond(mCDPModelA.m,machineCell)
      }
    }
    private def updateListFirst(index:Int): List[Int] ={
      val auxMachine = machineCell.zipWithIndex
        auxMachine.lift(index - 1) match {
        case Some(result) =>auxMachine.filter(_._2!=result._2).map(_._1) :+ result._1
        case None =>machineCell
      }
    }
    private def updateListSecond(index:Int): List[Int] ={
      machineCell.lift(index - 1) match {
        case Some(_) => machineCell.updated(index-1,machineCell(index-1))
        case None =>machineCell
      }
    }
    override def rollingTwigs2(): Unit = {
      val (dr,ds) =CommonMethod.calculusDRandDS(mCDPModelA)
      val fitnessI = SecondFitness.secondFitnessCalculusI(mCDPModelA,partCellArray,machineCellArray)
      val fitnessD = SecondFitness.secondFitnessCalculusD(mCDPModelA,partCellArray,machineCellArray)
      matchFitness(fitnessI,fitnessD,dr,ds)
    }
    private def matchFitness(fitnessI:Int,fitnessD:Int,dr:Int,ds:Int)=(fitnessI,fitnessD) match {
      case (fitI,fitD) if fitI>fitD=> matchFirstDR(dr)
      case (fitI,fitD) if fitI<fitD=> matchSecondDR(dr)
      case (fitI,fitD) if fitI==fitD=> matchThirdDR(dr)
    }
    private def matchFirstDR(dr:Int) =dr match {
      case 1 => updateMachineCell(0,(mCDPModelA.m/2)-1)
      case 0=>updateMachineCell((mCDPModelA.m/2)-1,0)
    }

    private def matchSecondDR(dr:Int) =dr match {
      case 1 => updateMachineCellSecond(mCDPModelA.m/2)
      case 0=>updateMachineCell(mCDPModelA.m-1,mCDPModelA.m/2)
    }

    private def updateMachineCellSecond(index:Int): List[Int] ={
      val auxMachine = machineCell.zipWithIndex
      auxMachine.lift(index) match {
        case Some(result) =>auxMachine.filter(_._2!=index).map(_._1):+result._1
        case None =>machineCell
      }
    }

    private def matchThirdDR(dr:Int) =dr match {
      case 1 => updateMachineCell(0,mCDPModelA.m-1)
      case 0=>updateMachineCell(0,mCDPModelA.m-1)
    }

    private def updateMachineCell(index:Int,indexUpdate:Int): List[Int] ={
      val auxMachine = machineCell.zipWithIndex
      auxMachine.lift(index) match {
        case Some(result) =>auxMachine.filter(_._2!=index).map{
          case (_, `indexUpdate`) =>result._1
          case (i, _) => i
        }
        case None =>machineCell
      }
    }
    override def changeAngle(): Unit = ???

    override def changeAngle2(): Unit = ???
  }
}
