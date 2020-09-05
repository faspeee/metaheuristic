package controller

import view.traitview.MainView

trait MainController extends AbstractController[MainView] {
  def startMetaHeuristic(neighbors: Int, iteration: Int): Unit

}

object MainController {
  def apply(): MainController = new MainControllerImp()

  private class MainControllerImp() extends MainController {
    override def startMetaHeuristic(neighbors: Int, iteration: Int): Unit = ???
  }

}
