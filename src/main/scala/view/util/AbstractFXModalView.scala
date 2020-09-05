package view.util

import java.net.URL
import java.util.ResourceBundle

import javafx.application.Platform
import javafx.fxml.{FXML, Initializable}
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import view.BaseView
import view.loader.FXLoader
import view.util.ResourceBundleUtil._
/**
 * @author Fabian Aspee Encina.
 *
 * Template class of type [[view.BaseView]] with basic funtionality to show
 * and hide a view loaded from fxml file.
 * @param myStage
 *                The [[javafx.stage.Stage]] where the view is Shown.
 *
 */
abstract class AbstractFXModalView(val myStage:Stage) extends Initializable with BaseView{
  /**
   * The base pane of the fxView where the components are added.
   */
  @FXML
  protected var pane: StackPane = _
  protected var generalResources: ResourceBundle = _

  private val PREDEF_WIDTH_SIZE: Double = 550
  private val PREDEF_HEIGHT_SIZE: Double = 400
  /**
   * Stage of this view.
   */
  FXLoader.loadScene(myStage,this,"Base")

  override def initialize(location: URL, resources: ResourceBundle): Unit ={
    myStage.setTitle(resources.getResource("name"))
    generalResources = resources
    myStage.setResizable(false)
    myStage.setMinWidth(PREDEF_WIDTH_SIZE)
    myStage.setMinHeight(PREDEF_HEIGHT_SIZE)
  }
  override def close(): Unit = {
    Platform.exit()
  }

  override def show(): Unit =
    myStage.show()

  override def hide(): Unit =
    myStage.hide()

  myStage.setOnCloseRequest(e => {
    e.consume()
    close()
  })

}
