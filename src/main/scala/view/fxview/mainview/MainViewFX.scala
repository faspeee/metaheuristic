package view.fxview.mainview

import java.net.URL
import java.util.ResourceBundle

import javafx.stage.Stage
import view.component.BaseBox
import view.component.parent.BaseBoxParent
import controller.MainController
import view.traitview.MainView
import view.util.AbstractFXModalView

object MainViewFX {
  def apply(primaryStage: Stage): MainView = new MainViewFX(primaryStage)
  private class MainViewFX(primaryStage: Stage) extends AbstractFXModalView(primaryStage) with MainView
  with BaseBoxParent{
    var myController : MainController = _
    var baseBox : BaseBox = _

    override def initialize(location: URL, resources: ResourceBundle): Unit = {
      super.initialize(location, resources)
      myController = MainController()
      baseBox = BaseBox()
      myController.setView(this)
      pane.getChildren.add(baseBox.setParent(this).pane)
      myStage.setResizable(false)
    }

    override def startMetaHeuristic(neighbors: Int, iteration: Int): Unit = myController.startMetaHeuristic(neighbors,iteration)
  }
}
