package model.metaheuristic

import java.io.File

import scala.io.{BufferedSource, Source}

object FileFunction {
  def readFirstRow(file:File):Option[MCDPModel]={
    val bufferedSource = Source.fromFile(file.getAbsolutePath)
    val firstLine = bufferedSource.getLines().take(1).toList.flatMap(line=>line.split(",").map(_.toInt))
    val matrix = readMatrix(bufferedSource)
    firstLine match {
      case List(first,second,third,fourth)=>Option(MCDPModel(first,second,third,fourth,matrix))
      case Nil =>None
    }
  }

  private def readMatrix(bufferedSource:BufferedSource):Map[Int,Int]={
    val firstLine=bufferedSource.getLines().take(1)
    bufferedSource.getLines().filter(line=> !firstLine.contains(line)).toList.zipWithIndex.flatMap(line=>line._1.split(",")
      .zipWithIndex.map(values=>
      line._2.toString.concat(values._2.toString).toInt->values._1.toInt)).toMap
  }
}
