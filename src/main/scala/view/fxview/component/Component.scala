package view.fxview.component

import javafx.scene.layout.Pane

/**
 * @author Fabian Aspee Encina
 *
 * Generic component of the view.
 *
 * @tparam A
 *           The parent/container of the component.
 *
 */
trait Component[A] {

  /**
   * Gets the pane of the component.
   * @return
   *        The pane where the component is contained, loaded from fxml.
   */
  val pane: Pane

  /**
   * Setter for the parent of this [[view.fxview.component.Component]].
   * @param parent
   *               The parent to set.
   */
  def setParent(parent:A):Component[A]

  /**
   * Disables the component, making it not interactive.
   */
  def disable(): Unit

  /**
   * Enables the component, making it interactive
   */
  def enable(): Unit


}


