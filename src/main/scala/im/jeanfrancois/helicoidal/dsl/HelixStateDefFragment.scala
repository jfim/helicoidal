package im.jeanfrancois.helicoidal.dsl

import org.apache.helix.model.StateModelDefinition

/**
 * Trait used to build state model definitions through the usage of the Helix builder
 *
 * @author jfim
 */
private[dsl] trait HelixStateDefFragment {
  private[dsl] def applyToBuilder(builder: StateModelDefinition.Builder)
}
