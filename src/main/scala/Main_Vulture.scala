import javafx.application.Application
import javafx.stage.Stage
import view.fxview.mainview.MainViewFX

private class Main_Vulture extends Application{
  override def start(primaryStage: Stage): Unit = {
   MainViewFX(primaryStage).show()
  }
}
object Main_Vulture {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Main_Vulture])
  }
}
