package model.metaheuristic

import scala.collection.mutable.ListBuffer

trait MetaHeuristic{
  def run():Unit
}
object MetaHeuristic {
  def apply(population:Int,iteration:Int,MCDPModel: Option[MCDPModel]): MetaHeuristic = new MetaHeuristicImp(population,iteration,MCDPModel)
  private class MetaHeuristicImp(population:Int,iteration:Int,MCDPModel: Option[MCDPModel]) extends MetaHeuristic{
    var listPopulation: List[Solution] = List[Solution]()
    def generateInitialPopulation(): Unit = {
      0 to population foreach(pop=>{
        val randomSolution =  MCDPRandomSolution(MCDPModel)
        val randomSolutionFitness= constraintOkM(randomSolution)
        val solution = Solution(randomSolution.getMachine, randomSolution.getPart, randomSolutionFitness)
        listPopulation= listPopulation:+solution
      })
    }
    @scala.annotation.tailrec
    private def constraintOkM(randomSolution:MCDPRandomSolution,constraintOk:Boolean=false,fitness:Int=0):Int =constraintOk match {
      case x if !x =>randomSolution.createRandomSolution()
        val boctorModel = ModelIntMCDP(MCDPModel, randomSolution.getMachine, randomSolution.getPart)
        val constraintOKR = boctorModel.checkConstraint()
        if(constraintOKR) constraintOkM(randomSolution,constraintOKR,fitness=boctorModel.calculateFitness()) else constraintOkM(randomSolution,constraintOKR)
      case _ => fitness
    }
    def chooseBestSolutionInPopulation() = ???

    def generateNeighbourSolution() = ???

    override def run(): Unit = {
      generateInitialPopulation()
      chooseBestSolutionInPopulation()
      0 to iteration foreach(_ =>{
        generateNeighbourSolution()
        chooseBestSolutionInPopulation()
      })

    }
  }
}
