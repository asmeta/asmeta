asm whatTimeIsIt
		
import ../STDL/StandardLibrary
import ../STDL/TimeLibrary

		
signature:
		
out currentTime: String
		
definitions:
		
main rule r_Main=
	currentTime:="It is "+ toString(mCurrTimeHours) + "h:" + toString(mCurrTimeMins) + "min:"+ toString(mCurrTimeSecs) + "sec"