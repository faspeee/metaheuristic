package model.metaheuristic
trait ModelIntMCDP{
  def consistencyConstraint1():Boolean
  def consistencyConstraint2():Boolean
  def consistencyConstraint3():Boolean
  def checkConstraint():Boolean
  def calculateFitness():Int
  def convertToFinalMatrix():Unit
}
object ModelIntMCDP {
  def apply(mCdpModel:Option[MCDPModel],machineCell:Array[Array[Int]],partCell:Array[Array[Int]]): ModelIntMCDP =
    new ModelIntMCDPImp(mCdpModel,machineCell,partCell)
  private class ModelIntMCDPImp(mCdpModel:Option[MCDPModel],machineCell:Array[Array[Int]],partCell:Array[Array[Int]]) extends ModelIntMCDP{
    override def consistencyConstraint1(): Boolean = {
      var result = true
      mCdpModel foreach { value =>
        var flag, zeros, m, c = 0
        while (m < value.m && result) {
          while (c < value.c && result) {
            if (machineCell(m)(c) == 1) if (flag == 1) result = false else flag = 1
            else zeros += zeros + 1
            c += c + 1
          }
          if (zeros == value.c) result=false
          else zeros = 0
          m += m + 1
        }
      }
      result
    }
    override def consistencyConstraint2(): Boolean = {
      var result = true
      mCdpModel foreach { value =>
        var flag, zeros, m, c = 0
        while (m < value.m && result) {
          while (c < value.c && result) {
            if (partCell(m)(c) == 1) if (flag == 1) result = false else flag = 1
            else zeros += zeros + 1
            c += c + 1
          }
          if (zeros == value.c) result=false
          else zeros = 0
          m += m + 1
        }
      }
      result
    }

    override def consistencyConstraint3(): Boolean = {
      mCdpModel match {
        case Some(value) =>
          var c=0
          var resultF = true
          while(c<value.c && resultF){
             val result = for{
                m<- 0 to value.m
              }yield if(machineCell(m)(c)==1) 1 else 0
            if(result.count(_==1)>value.mMax) resultF = false else resultF= true
            c+=c+1
          }
          resultF
        case None =>true
      }
    }

    override def checkConstraint(): Boolean = {
       Option(consistencyConstraint2()).zip(Option(consistencyConstraint3())) match {
        case Some(value) if !value._1 || !value._2=>false
        case None => true
        case _ =>true
      }
    }

    override def calculateFitness(): Int = {
      mCdpModel match {
        case Some(value) =>
          val sum = for{
            k<- 0 to value.c
            i<- 0 to value.m
            j<- 0 to value.p
          }yield value.matrix.getOrElse(i.toString.concat(j.toString).toInt,0) * (1-machineCell(i)(k))
          sum.sum
        case None =>0
      }
    }

    override def convertToFinalMatrix(): Unit = ???
  }
}
