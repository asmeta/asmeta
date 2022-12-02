package asmeta.fmvclib.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AsmetaModelParameters {
	AsmetaModelParameter[] value();
}