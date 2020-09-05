package model.metaheuristic

import java.io.File


trait ReadFile{
  def readFile(root:String):Option[List[File]]
}
object ReadFile extends ReadFile{
  override def readFile(root:String):Option[List[File]]={
    val read = new File(root)
    @scala.annotation.tailrec
    def _readFile(read:List[File], listFile:Option[List[File]]=None):Option[List[File]]= read match {
      case ::(directory, next) if directory.isDirectory => _readFile(next:::directory.listFiles().toList,listFile)
      case ::(file, next) if file.isFile => _readFile(next,listFile.map(_:+file))
      case Nil =>listFile
    }
    read match {
      case folder if folder.isDirectory && folder.exists() => _readFile(folder.listFiles().toList)
      case file =>Option(List(file))
    }
  }
}
