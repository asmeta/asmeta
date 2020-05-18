asm temp_spec0
import scenariosfortest\\mon\\../StandardLibrary

signature:

    // added by validator
    controlled step__: Integer
    // converted to controlled by validator
    controlled a: Integer

definitions:

    macro rule r_main =
        skip


    // new main added by validator
    main rule r_main__ =
        switch step__
			case 0:
				seq
					r_main[]
					step__ := step__ + 1
				endseq
		endswitch
    default init s0:
        // added by validator
        function step__ = 0
        function a = 1
