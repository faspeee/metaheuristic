package controller

import view.BaseView

/**
 * @author Fabian Aspee Encina.
 *
 * Basic definition of Controller functionalities.
 * @tparam A
 *           The type of view class that i control. Should be subtype of
 *           [[view.BaseView]]
 *
 */
trait Controller[A<:BaseView] {
  /**
   *Sets the provided view as my controlled view.
   * @param view
   */
  def setView(view:A): Unit
}