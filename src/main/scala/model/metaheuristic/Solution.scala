package model.metaheuristic
trait Solution
object Solution {
  def apply(machineCell:Array[Array[Int]],partCell:Array[Array[Int]],fitness:Int): Solution = new SolutionImp(machineCell,partCell,fitness)
  private class SolutionImp(machineCell:Array[Array[Int]],partCell:Array[Array[Int]],fitness:Int) extends Solution
}
