package view.util

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

object Execution {
  implicit val executionContext: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())
}
