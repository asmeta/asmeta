asm m2

import ../../../STDL/StandardLibrary

signature:

abstract domain A
abstract domain B
domain SubA subsetof A
domain SubB subsetof B

controlled f: Integer

static a: A
static b: B
static sa: SubA
static sb: SubB

definitions:

macro rule r_m1($x in A, $y in B)=
	f := 1

macro rule r_m1($x in SubA, $y in SubB)=
	f := 2

main rule r_main=
	r_m1[a, sb]
	
