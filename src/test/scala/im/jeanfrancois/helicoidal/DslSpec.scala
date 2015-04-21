package im.jeanfrancois.helicoidal

import im.jeanfrancois.helicoidal.dsl._
import org.scalatest.{FlatSpec, Matchers}

/**
 * Test suite for the various parts of the DSL
 *
 * @author jfim
 */
class DslSpec extends FlatSpec with Matchers {
  "A state model definition builder" should "properly create a state model definition" in {
    val Master = HelixState("MASTER")
    val Slave = HelixState("SLAVE")
    val Offline = HelixState("OFFLINE")

    val stateModelDef = StateModelDef("My state model")
      .withStates(
        Master priority 1,
        Slave priority 2,
        Offline)
      .withInitialState(Offline)
      .withTransitions(
        Offline → Slave priority 1,
        Slave → Offline,
        Slave → Master,
        Master → Slave
      )
      .withUpperBounds(
        Master upTo 1,
        Slave upTo ReplicaCount,
        Offline upTo NodeCount
      )
      .build()

    stateModelDef.getInitialState should be ("OFFLINE")
    stateModelDef.getNumInstancesPerState("MASTER") should be ("1")
    stateModelDef.getNumInstancesPerState("SLAVE") should be ("R")
    stateModelDef.getNumInstancesPerState("OFFLINE") should be ("N")
    stateModelDef.getNextStateForTransition("OFFLINE", "MASTER") should be ("SLAVE")
    stateModelDef.getNextStateForTransition("MASTER", "OFFLINE") should be ("SLAVE")
    stateModelDef.isValid should be (true)
  }
}
