package im.jeanfrancois.helicoidal.dsl

import org.apache.helix.model.StateModelDefinition.Builder

/**
 * A state transition, usually instantiated through the -> method of [[HelixState]].
 *
 * @author jfim
 */
case class HelixStateTransition(from: HelixState, to: HelixState) extends HelixStateDefFragment {
  private[dsl] override def applyToBuilder(builder: Builder) = builder.addTransition(from.name, to.name)

  /**
   * Defines the priority for this state transition, with lower values having higher precedence.
   */
  def priority(prio: Int): HelixStateTransition = new HelixStateTransition(from, to) {
    private[dsl] override def applyToBuilder(builder: Builder) = builder.addTransition(from.name, to.name, prio)
  }
}
