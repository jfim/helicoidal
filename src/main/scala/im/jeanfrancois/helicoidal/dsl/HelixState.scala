package im.jeanfrancois.helicoidal.dsl

import org.apache.helix.model.StateModelDefinition.Builder

/**
 * Represents a state for a Helix state machine
 *
 * @author jfim
 */
case class HelixState(name: String) extends HelixStateDefFragment {
  /**
   * Defines the priority for this state, with lower priorities having higher precedence
   */
  def priority(statePriority: Int): HelixState = new HelixState(name) {
    override def applyToBuilder(builder: Builder) = builder.addState(name, statePriority)
  }

  /**
   * Defines a state transition between two states
   */
  def →(to: HelixState): HelixStateTransition = HelixStateTransition(this, to)

  /**
   * Defines a state transition between two states
   */
  def ->(to: HelixState) = →(to)

  /**
   * Defines an upper bound for this state
   */
  def upTo(maximumStateCount: Int): HelixStateUpperBound = HelixStateUpperBound(this, maximumStateCount.toString)

  /**
   * Defines an upper bound for this state, currently defined dynamic constraints are [[ReplicaCount]] (up to
   * the number of replicas per partition) and [[NodeCount]] (up to the number of nodes in the cluster).
   */
  def upTo(constraint: DynamicStateConstraint): HelixStateUpperBound = HelixStateUpperBound(this, constraint.constraintString)

  override def applyToBuilder(builder: Builder) = builder.addState(name)
}
