package im.jeanfrancois.helicoidal

import org.apache.helix.model.StateModelDefinition

import scala.language.implicitConversions

/**
 * Contains various utilities to have a more Scala-like interface with Helix.
 *
 * == Overview ==
 *
 * Various Helix model objects can be constructed using a Scala-like syntax. For example:
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
 *
 * @author jfim
 */
package object dsl {
  implicit def stateModelDefToStateModelDefinition(modelDef: StateModelDef): StateModelDefinition = modelDef.build()

  private[dsl] case class DynamicStateConstraint(constraintString: String)
  val ReplicaCount = DynamicStateConstraint("R")
  val NodeCount = DynamicStateConstraint("N")

  /**
   * The Helix dropped state.
   */
  val Dropped = HelixState("DROPPED")
}
