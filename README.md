# Helicoidal: A Scala wrapper for Apache Helix

*Only a few features are implemented at this point in time.*

## State model creation
```
import im.jeanfrancois.spiral.dsl._

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
```