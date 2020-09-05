package view.component

import java.net.URL
import java.util.ResourceBundle

import javafx.fxml.FXML
import javafx.scene.layout.BorderPane
import view.component.parent.BaseBoxParent
import view.fxview.component.{AbstractComponent, Component}

trait BaseBox extends Component[BaseBoxParent]{
  def drawResult():Unit
}
object BaseBox {
  def apply(): BaseBox = new InitialViewBoxImp()

  private class InitialViewBoxImp() extends AbstractComponent[BaseBoxParent]("BaseFX")
    with BaseBox{

    var centerFx:CenterFX = _
    var topFX : TopFX = _
    @FXML
    var baseBox: BorderPane = _

    override def initialize(location: URL, resources: ResourceBundle): Unit = {
      centerFx = CenterFX()
      topFX = TopFX()
      baseBox.setTop(topFX.setParent(parent).pane)
      baseBox.setCenter(centerFx.setParent(parent).pane)
    }

    override def drawResult(): Unit = centerFx.drawStatus()
  }

}
