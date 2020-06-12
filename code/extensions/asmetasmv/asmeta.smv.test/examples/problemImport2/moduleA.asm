module moduleA

export r_a

signature:
	dynamic controlled foo: Boolean

definitions:
	rule r_a =
		foo := true
