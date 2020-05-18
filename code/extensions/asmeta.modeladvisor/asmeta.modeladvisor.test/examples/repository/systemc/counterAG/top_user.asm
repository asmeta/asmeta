module top_user

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	import ../sched/chanint
	import ../sched/clock
	import count_stim_user
	import ../counter2/display
	import ../counter2/count
	export r_initTop, dOUT

signature:

static lOAD: ChanBoolean
static dIN: ChanInteger
static dOUT: ChanInteger
static cLOCK: Clock

static u_count: Count
static u_count_stim: Count_stim_user
static u_display: Display

definitions:

	macro rule r_initTop =
	// extend ChanBoolean with $lOAD do
	// extend ChanInteger with $dIN, $dOUT do
	// extend Clock with $cLOCK do
	// extend Count with $u_count do
	// extend Count_stim with $u_count_stim do
	// extend Display with $u_display do
		seq
			// first: init channels
			r_init[lOAD]
			r_initInt[dIN]
			r_initInt[dOUT]
			r_initClock[cLOCK, 20, 0.5, 10, true]
			// second: init modules
			r_initCount_stim[u_count_stim, 8, lOAD, cLOCK, dIN, dOUT]
			r_initDisplay[u_display, dOUT]
			r_initCount[u_count, lOAD, cLOCK, dIN, dOUT]
		endseq

