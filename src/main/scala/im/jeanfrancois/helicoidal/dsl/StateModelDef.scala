package im.jeanfrancois.helicoidal.dsl

import org.apache.helix.model.StateModelDefinition
import org.apache.helix.model.StateModelDefinition.Builder

/**
 * State model definition, which contains the legal states, initial state, legal state transitions and upper bounds on
 * state instances.
 *
 * {{{
 *  import im.jeanfrancois.spiral.dsl._
 *
 *  val Master = HelixState("MASTER")
 *  val Slave = HelixState("SLAVE")
 *  val Offline = HelixState("OFFLINE")
 *
 *  val stateModelDef = StateModelDef("My state model")
 *    .withStates(
 *      Master priority 1,
 *      Slave priority 2,
 *      Offline)
 *    .withInitialState(Offline)
 *    .withTransitions(
 *      Offline -> Slave priority 1,
 *      Slave -> Offline,
 *      Slave -> Master,
 *      Master -> Slave
 *    )
 *    .withUpperBounds(
 *      Master upTo 1,
 *      Slave upTo ReplicaCount,
 *      Offline upTo NodeCount
 *    )
 * }}}
 * @author jfim
 */
class StateModelDef(val name: String) {
  private var fragments = Seq.empty[HelixStateDefFragment]

  /**
   * Turns this model state definition into a Helix StateModelDefinition
   */
  def build(): StateModelDefinition = {
    val builder = new Builder(name)
    fragments.foreach(_.applyToBuilder(builder))
    builder.build()
  }

  /**
   * Defines the valid states for Helix
   */
  def withStates(states: HelixState*): StateModelDef = {
    fragments = fragments ++ states :+ Dropped
    this
  }

  /**
   * Defines the initial state for Helix
   */
  def withInitialState(state: HelixState): StateModelDef = {
    fragments = fragments :+ new HelixStateDefFragment {
      override def applyToBuilder(builder: Builder) = builder.initialState(state.name)
    }
    this
  }

  /**
   * Defines the valid transitions for Helix
   */
  def withTransitions(stateTransitions: HelixStateTransition*): StateModelDef = {
    fragments = fragments ++ stateTransitions
    val transitionsToDropped = fragments.collect {
      case s: HelixState ⇒ HelixStateTransition(s, Dropped)
    }
    fragments = fragments ++ transitionsToDropped
    this
  }

  /**
   * Defines the instance count upper bounds
   */
  def withUpperBounds(bounds: HelixStateUpperBound*): StateModelDef = {
    fragments = fragments ++ bounds
    this
  }
}

object StateModelDef {
  def apply(name: String) = new StateModelDef(name)
}
