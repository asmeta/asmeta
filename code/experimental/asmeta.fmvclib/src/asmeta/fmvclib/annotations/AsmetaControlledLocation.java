package asmeta.fmvclib.annotations;

import asmeta.fmvclib.annotations.AsmetaModelParameter.LocationType;

public @interface AsmetaControlledLocation {

	String asmLocationName();

	LocationType asmLocationType();

}
