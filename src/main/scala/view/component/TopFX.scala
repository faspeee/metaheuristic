package view.component

import java.net.URL
import java.util.ResourceBundle

import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, TextField}
import view.component.parent.TopFXParent
import view.fxview.component.{AbstractComponent, Component}
import view.util.ResourceBundleUtil._

trait  TopFX extends Component[TopFXParent]

object TopFX {
  def apply(): TopFX = new InitialViewBoxImp()

  private class InitialViewBoxImp() extends AbstractComponent[TopFXParent]("TopFX")
    with TopFX{

    @FXML
    var neighbors: TextField = _
    @FXML
    var iteration: TextField = _
    @FXML
    var start: Button = _
    @FXML
    var error: Label = _

    override def initialize(location: URL, resources: ResourceBundle): Unit = {
      super.initialize(location, resources)
      neighbors.setPromptText(resources.getResource("neighbors"))
      iteration.setPromptText(resources.getResource("iteration"))
      start.setText(resources.getResource("start"))
      error.setVisible(false)
      start.setOnAction(_=>checkValue())
    }
    def checkValue():Unit={
      if(neighbors.getText().isEmpty)
        error.setText(resources.getResource("error-message-neighbors"))
      if(neighbors.getText().isEmpty)
        error.setText(resources.getResource("error-message-iteration"))
      if(neighbors.getText().isEmpty && iteration.getText().isEmpty)
        error.setText(resources.getResource("error-message-both"))
      else
        callStartMetaHeuristic()
    }
    def callStartMetaHeuristic():Unit= {
      println(parent)
      parent.startMetaHeuristic(neighbors.getText().toInt,iteration.getText().toInt)
    }
  }
}
