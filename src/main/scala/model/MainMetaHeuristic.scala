package model

import java.io.File

import model.metaheuristic.{FileFunction, MetaHeuristic, ReadFile}

trait MainMetaHeuristic{
  def initMetaHeuristic(neighbors: List[Int], iteration: List[Int]):Unit
}
object MainMetaHeuristic{
  def apply(root:String, isParallel:Boolean=false): MainMetaHeuristic = new MainMetaHeuristicImp(root, isParallel)
  private class MainMetaHeuristicImp(root:String, isParallel:Boolean=false) extends MainMetaHeuristic{
    override def initMetaHeuristic(neighbors: List[Int], iteration: List[Int]): Unit = {
      val listFile = ReadFile.readFile(root)
      if(isParallel)
        print("Parallel")
      else
        setMainSequential(neighbors,iteration,listFile)
    }
    def setMainSequential(neighbors: List[Int], iteration: List[Int], listFile:Option[List[File]]):Unit={
     neighbors.foreach(population=>iteration.foreach(itera=>listFile.toList.flatten.foreach(file=>{
       val fileFunction = FileFunction.readFirstRow(file)
       val metaHeuristic = MetaHeuristic(population,itera,fileFunction)
       0 to 31 foreach(_=>{
         metaHeuristic.run()
       })
     })))
    }
  }

}
