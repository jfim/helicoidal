package im.jeanfrancois.helicoidal.dsl

import org.apache.helix.model.StateModelDefinition.Builder

/**
 * An upper instance count constraint, normally instantiated through the upTo method of [[HelixState]].
 *
 * @author jfim
 */
case class HelixStateUpperBound(state: HelixState, upperBound: String) extends HelixStateDefFragment {
  private[dsl] override def applyToBuilder(builder: Builder) = builder.dynamicUpperBound(state.name, upperBound)
}
