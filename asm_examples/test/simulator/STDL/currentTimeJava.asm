asm currentTimeJava

import  ../../../STDL/StandardLibrary
	
signature:
	controlled timeNanosecs: Integer
	controlled oldTimeNanosecs: Integer
	controlled timeMillsecs: Integer
	controlled oldTimeMillsecs: Integer
	controlled timeSecs: Integer
	controlled oldTimeSecs: Integer

definitions:

	invariant inv_timeNanosecs over timeNanosecs, oldTimeNanosecs: timeNanosecs >= oldTimeNanosecs
	invariant inv_timeMillisecs over timeMillsecs, oldTimeMillsecs: timeMillsecs >= oldTimeMillsecs
	invariant inv_timeSecs over timeSecs, oldTimeSecs: timeSecs >= oldTimeSecs

	main rule r_main =
		par
			timeNanosecs := currTimeNanosecs //function of standard library
			oldTimeNanosecs := timeNanosecs
			timeMillsecs := currTimeMillisecs //function of standard library
			oldTimeMillsecs := timeMillsecs
			timeSecs := currTimeSecs //function of standard library
			oldTimeSecs := timeSecs
		endpar

default init s0:
    function timeNanosecs = 1
    function oldTimeNanosecs = 0
    function timeMillsecs = 1
    function oldTimeMillsecs = 0
    function timeSecs = 1
    function oldTimeSecs = 0
