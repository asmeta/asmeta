asm ClientDisplayOutputWithCookieTags

//Second refinement

import ../StandardLibrary

signature:
	domain Device subsetof Agent
	enum domain HTMLTag = {TAG1 | TAG2 | TAG3 | TAG4 | TAG5 | TAG6}

	enum domain State = {WAITING_FOR_MESSAGE | EXECUTE_CLIENT_TESTS | CHECKING_FOR_EXTRA_RESOURCES | DISPLAYING_THE_MESSAGE} 
	enum domain Key = {CANVAS | TEXTSHADOW | AUDIO | FAIL_EX}
	enum domain SubKey = {OGG | MP3}
	enum domain MyBoolDom = {YES | NO}

	controlled state: Device -> State

	controlled modernizr: Prod(Device, Key) -> MyBoolDom //it says if the "key" is supported by the device
	controlled modernizrSub: Prod(Device, Key, SubKey) -> MyBoolDom //it says if the child of a "key" is supported by the device

	controlled cookie: Prod(Device, Key) -> MyBoolDom //it says if the "key" is present in the cookie
	controlled cookieSub: Prod(Device, Key, SubKey) -> MyBoolDom //it says if the child of a "key" is present in the cookie

	static keyParentalRel: Prod(Key, SubKey) -> Boolean //it says if a key is father of a child key

	monitored extraResources: Device -> Boolean
	static htmlTags: Prod(Device, HTMLTag)  -> Boolean //the html tags corresponding to each device; (instead of messages we work directly with the html tags)
	derived messageArrived: Device -> Boolean
	derived containsModernizrTests: HTMLTag -> Boolean
	derived clientTestsAvailable: Device -> Boolean

	static device1: Device
	static device2: Device
	static device3: Device

definitions:	
	function htmlTags($d in Device, $t in HTMLTag) =
		/*switch ($d,$t)
			case (device1, TAG3): true
			case (device1, TAG4): true
			case (device2, TAG1): true
			case (device2, TAG3): true
			otherwise false
			//device3 = undef (not defined locations are "undef" by default)
		endswitch*/
			($d =device1 and $t=TAG3) or
			($d =device1 and $t=TAG4) or
			($d =device2 and $t=TAG1) or
			($d =device2 and $t=TAG3)

	//define the derived functions
	function messageArrived($d in Device) = 
		(exist $t in HTMLTag with htmlTags($d,$t))

	function containsModernizrTests($t in HTMLTag) = 
		($t=TAG1 or $t=TAG2)

	function clientTestsAvailable($d in Device) = 
		(exist $t in HTMLTag with (containsModernizrTests($t) and htmlTags($d,$t)))

	function keyParentalRel($f in Key, $c in SubKey) =
		($f = AUDIO and ($c = OGG or $c = MP3))

	rule r_DownloadExtraResources = skip //this rule remains abstract

	rule r_DecryptMessage =	skip //this rule remains abstract

	rule r_updateCookieProfile =
		forall $k in Key with true do
			par
				cookie(self, $k) := modernizr(self, $k)
				forall $c in SubKey with keyParentalRel($k, $c) do
					cookieSub(self, $k, $c) := modernizrSub(self, $k, $c)
			endpar

	rule r_Display =
		par
			if (state(self) = WAITING_FOR_MESSAGE) then
				if (messageArrived(self)=true) then
					par
						r_DecryptMessage[]
						if (clientTestsAvailable(self)=true) then
							state(self) := EXECUTE_CLIENT_TESTS
						else
							state(self) := CHECKING_FOR_EXTRA_RESOURCES
						endif
					endpar
				endif
			endif
			if (state(self) = EXECUTE_CLIENT_TESTS) then
				par
					r_updateCookieProfile[] 
					state(self) := CHECKING_FOR_EXTRA_RESOURCES
				endpar
			endif
			if (state(self) = CHECKING_FOR_EXTRA_RESOURCES) then
				par
					if extraResources(self) then
						r_DownloadExtraResources[]
					endif
					state(self) := DISPLAYING_THE_MESSAGE	
				endpar
			endif
			if (state(self) = DISPLAYING_THE_MESSAGE) then
				//this device reached the final state -> the message was displayed
				skip
			endif
		endpar

	main rule r_Main =
		forall $d in Device do //all the devices run in parallel
			program($d)

default init initial_state:
	function state($d in Device) = WAITING_FOR_MESSAGE
	/*function modernizr($d in Device, $k in Key) =
											switch $d
												case device2:
													switch $k
														case CANVAS: YES
														case TEXTSHADOW: NO
														case AUDIO: YES
													endswitch
												endswitch
		function modernizrSub($d in Device, $k in Key, $c in SubKey) =
											switch $d
												case device2:
													switch $k
														case AUDIO:
															switch $c
																case OGG: NO
																case MP3: YES
															endswitch
													endswitch
												endswitch*/
		//function modernizr($d in Device, $k in Key) =
		//									if ($d = device2 and ($k = CANVAS or $k = AUDIO)) then YES endif
		function modernizr($d in Device, $k in Key) = at({(device2, CANVAS) -> YES, (device2, AUDIO) -> YES, (device2, TEXTSHADOW) -> NO}, ($d,$k)) 	
		//function modernizrSub($d in Device, $k in Key, $c in SubKey) =
											//if ($d = device2 and $k = AUDIO and ($c = MP3)) then YES endif
		function modernizrSub($d in Device, $k in Key, $c in SubKey) = at({(device2, AUDIO, OGG) -> NO, (device2, AUDIO, MP3) -> YES}, ($d,$k,$c))

	agent Device: 
		r_Display[]		