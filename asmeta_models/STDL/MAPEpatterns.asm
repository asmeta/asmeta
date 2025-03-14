module MAPEpatterns

import StandardLibrary
import LTLLibrary

export *

signature:
	static correctStep: Prod(Boolean, Boolean, Powerset(Boolean)) -> Boolean
	static both: Prod(Boolean, Boolean) -> Boolean
	static requires: Prod(Boolean, Powerset(Boolean)) -> Boolean
	static ensures: Prod(Boolean, Powerset(Boolean)) -> Boolean

definitions:
	function correctStep($p in Boolean, $q in Boolean, $s in Powerset(Boolean)) =
		$p implies u((forall $e in $s with not($e)), $q)

	function both($p in Boolean, $q in Boolean) =
		($p implies (f($q) or o($q))) and ($q implies (f($p) or o($p)))

	function requires($p in Boolean, $s in Powerset(Boolean)) =
		$p implies (forall $e in $s with o($e))

	function ensures($p in Boolean, $s in Powerset(Boolean)) =
		$p implies (forall $e in $s with f($e))
