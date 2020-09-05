package view.component

import java.net.URL
import java.util.ResourceBundle

import view.component.parent.CenterFXParent
import view.fxview.component.{AbstractComponent, Component}

trait CenterFX extends Component[CenterFXParent]{
  def drawStatus():Unit
}
object CenterFX{
  def apply(): CenterFX = new CenterFXImp()

  private class CenterFXImp()  extends AbstractComponent[CenterFXParent]("CenterFX") with CenterFX{
    override def initialize(location: URL, resources: ResourceBundle): Unit =
      super.initialize(location, resources)

    override def drawStatus(): Unit = ???
  }

}
