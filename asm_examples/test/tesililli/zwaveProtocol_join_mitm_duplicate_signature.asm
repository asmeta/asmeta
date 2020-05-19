asm zwaveProtocol_join_mitm_duplicate_signature

import  ../../STDL/StandardLibrary
//import CTLlibrary


signature:
	domain Slave subsetof Agent
	domain ESlave subsetof Agent
	domain Controller subsetof Agent
	domain EController subsetof Agent
	enum domain Time ={ TB1	| TA1 | TB2 | TAI1 | TA2 | TB3 | TAI2 | TBI1}		
	enum domain SlaveType = {DOOR_LOCK_NAT | DOOR_LOCK_UP | GARAGE_LOCK_NAT | GARAGE_LOCK_UP | SWITCH_NAT | LIGHT_NAT | SENSOR_NAT | SECURITY_SENSOR_NAT | THERMOSTAT_NAT | THERMOSTAT_UP | ALARM_NAT | ALARM_UP | LEGACY_DEVICE}
	enum domain ControllerType = {CONTROLLER_S2 | CONTROLLER_LEGACY}
	enum domain KeyType = {KPUB_SLAVE | KPUB_CONTROLLER | KPUB_MITM_CTRL | KPUB_MITM_SLV | OB_KEY_MITM_CTRL | OB_KEY_MITM_SLV | OB_KEY_SLAVE | OB_KEY_CTRL}
	enum domain AESKeyType = { KT | KT_ERROR }
	enum domain PinType = {PIN_OK | PIN_ERROR}
	enum domain ProtocolMessageType = { KEX_GET | KEX_REP | KEX_SET | PUB_KEY_REP_JOIN | PUB_KEY_REP_CTRL | NONCE_GET | NONCE_REPORT | EC_SEI_KEX_SET_ECHO | EC_KEX_REPORT_ECHO | KEX_FAIL_KEX_KEY | KEX_FAIL_KEX_SCHEME | KEX_FAIL_KEX_CURVE | KEX_FAIL_CANCEL | KEX_FAIL_AUTH | KEX_FAIL_DECRYPT}    
	enum domain ControllerStateType = {INIT_CTRL | WAIT_EVAL_CSA | WAIT_EVAL_KEX_CURVE | WAIT_EVAL_KEX_SCHEME | WAIT_EVAL_KEX_KEY | WAIT_EVAL_USER_KEY | WAIT_SEI_KEX_SET_ECHO | WAIT_NONCE | INSERT_PIN | WAIT_PIN_OR_KEY | WAIT_ECDH_PUB_JOIN | WAIT_KEX_REP | ADD_MODE | OK_C | ERROR_C | TIMEOUT_C}
	enum domain SlaveStateType = {INIT_SLV | LEARN_MODE | WAIT_KEX_SET | WAIT_EVAL_SET_KEX_KEY | WAIT_EVAL_SET_KEX_SCHEME | WAIT_EVAL_SET_KEX_CURVE | WAIT_EVAL_SET_CSA | WAIT_ECDH_PUB_CTRL | INSERT_PIN_CSA | WAIT_NONCE_REP_REI | WAIT_KEX_REPORT_ECHO | OK_S | ERROR_S | TIMEOUT_S}	
	
	//domain Mitm subsetof Agent
		
	monitored userGrantS2Access: Boolean	
	monitored userGrantS2Auth: Boolean
	monitored userGrantS2Unauth: Boolean
	monitored userGrantS0: Boolean	
	monitored userCsa: Controller -> Boolean
	monitored pinCode: PinType	
	monitored controller: Controller -> ControllerType
	monitored slave: Slave -> SlaveType
	monitored passed: Time -> Boolean
	monitored nearToEnd: Time -> Boolean //send response 2 seconds before timer timeout
	
	controlled controllerState: Controller-> ControllerStateType
	controlled slaveState: Slave -> SlaveStateType
	controlled protocolMessage: Prod(Slave,EController) -> ProtocolMessageType
	controlled protocolMessage: Prod(EController,Slave) -> ProtocolMessageType
	controlled protocolMessage: Prod(ESlave,Controller) -> ProtocolMessageType
	controlled protocolMessage: Prod(Controller,ESlave) -> ProtocolMessageType
	controlled startTimer: Time -> Boolean
	controlled messageArrived: ProtocolMessageType -> Boolean
	controlled pinSaved: PinType
	controlled pinHacked: PinType
	
	//basic s2 controller specs
	controlled ctrlCsa: Controller -> Boolean
	controlled ctrlSkex: Controller -> Boolean
	controlled ctrlEcdh: Controller -> Boolean
	controlled ctrlS2Access: Controller -> Boolean	
	controlled ctrlS2Auth: Controller -> Boolean
	controlled ctrlS2Unauth: Controller -> Boolean
	controlled ctrlS0: Controller -> Boolean
	
	//basic s2 slave specs
	controlled slvCsa: Slave -> Boolean
	controlled slvSkex: Slave -> Boolean
	controlled slvEcdh: Slave -> Boolean
	controlled slvS2Access: Slave -> Boolean
	controlled slvS2Auth: Slave -> Boolean
	controlled slvS2Unauth: Slave -> Boolean
	controlled slvS0: Slave -> Boolean
	
	//controllore requested specs when (controller, salve) saved on slave when (slave,slave) saved on controller when (controller,controller)
	//confirmation (slave ,controller)
	controlled reqCsa: Prod(Slave,Slave) -> Boolean
	controlled reqCsa: Prod(Controller,Controller) -> Boolean
	controlled reqCsa: Prod(ESlave,Controller) -> Boolean
	controlled reqCsa: Prod(Controller,ESlave) -> Boolean
	controlled reqCsa: Prod(Slave,EController) -> Boolean
	controlled reqCsa: Prod(EController,Slave) -> Boolean
	controlled reqCsa: Prod(ESlave,ESlave) -> Boolean
	controlled reqCsa: Prod(EController,EController) -> Boolean
	controlled reqSkex: Prod(Slave,Slave) -> Boolean
	controlled reqSkex: Prod(Controller,Controller) -> Boolean
	controlled reqSkex: Prod(ESlave,Controller) -> Boolean
	controlled reqSkex: Prod(Controller,ESlave) -> Boolean
	controlled reqSkex: Prod(Slave,EController) -> Boolean
	controlled reqSkex: Prod(EController,Slave) -> Boolean
	controlled reqSkex: Prod(ESlave,ESlave) -> Boolean
	controlled reqSkex: Prod(EController,EController) -> Boolean	
	controlled reqEcdh: Prod(Slave,Slave) -> Boolean
	controlled reqEcdh: Prod(Controller,Controller) -> Boolean
	controlled reqEcdh: Prod(ESlave,Controller) -> Boolean
	controlled reqEcdh: Prod(Controller,ESlave) -> Boolean
	controlled reqEcdh: Prod(Slave,EController) -> Boolean
	controlled reqEcdh: Prod(EController,Slave) -> Boolean
	controlled reqEcdh: Prod(ESlave,ESlave) -> Boolean
	controlled reqEcdh: Prod(EController,EController) -> Boolean
	controlled reqS2Access: Prod(Slave,Slave) -> Boolean
	controlled reqS2Access: Prod(Controller,Controller) -> Boolean
	controlled reqS2Access: Prod(ESlave,Controller) -> Boolean
	controlled reqS2Access: Prod(Controller,ESlave) -> Boolean
	controlled reqS2Access: Prod(Slave,EController) -> Boolean
	controlled reqS2Access: Prod(EController,Slave) -> Boolean
	controlled reqS2Access: Prod(ESlave,ESlave) -> Boolean
	controlled reqS2Access: Prod(EController,EController) -> Boolean
	controlled reqS2Auth: Prod(Slave,Slave) -> Boolean
	controlled reqS2Auth: Prod(Controller,Controller) -> Boolean
	controlled reqS2Auth: Prod(ESlave,Controller) -> Boolean
	controlled reqS2Auth: Prod(Controller,ESlave) -> Boolean
	controlled reqS2Auth: Prod(Slave,EController) -> Boolean
	controlled reqS2Auth: Prod(EController,Slave) -> Boolean
	controlled reqS2Auth: Prod(ESlave,ESlave) -> Boolean
	controlled reqS2Auth: Prod(EController,EController) -> Boolean
	controlled reqS2Unauth: Prod(Slave,Slave) -> Boolean
	controlled reqS2Unauth: Prod(Controller,Controller) -> Boolean
	controlled reqS2Unauth: Prod(ESlave,Controller) -> Boolean
	controlled reqS2Unauth: Prod(Controller,ESlave) -> Boolean
	controlled reqS2Unauth: Prod(Slave,EController) -> Boolean
	controlled reqS2Unauth: Prod(EController,Slave) -> Boolean
	controlled reqS2Unauth: Prod(ESlave,ESlave) -> Boolean
	controlled reqS2Unauth: Prod(EController,EController) -> Boolean	
	controlled reqS0: Prod(Slave,Slave) -> Boolean
	controlled reqS0: Prod(Controller,Controller) -> Boolean
	controlled reqS0: Prod(ESlave,Controller) -> Boolean
	controlled reqS0: Prod(Controller,ESlave) -> Boolean
	controlled reqS0: Prod(Slave,EController) -> Boolean
	controlled reqS0: Prod(EController,Slave) -> Boolean
	controlled reqS0: Prod(ESlave,ESlave) -> Boolean
	controlled reqS0: Prod(EController,EController) -> Boolean	

	
	//slave garanted specs when (slave,controller) saved on slave when (slave,slave) saved on controller when (controller,controller)
	//confirmation (controller, salve)
	controlled grantCsa: Prod(Slave,Slave) -> Boolean
	controlled grantCsa: Prod(Controller,Controller) -> Boolean
	controlled grantCsa: Prod(ESlave,Controller) -> Boolean
	controlled grantCsa: Prod(Controller,ESlave) -> Boolean
	controlled grantCsa: Prod(Slave,EController) -> Boolean
	controlled grantCsa: Prod(EController,Slave) -> Boolean
	controlled grantCsa: Prod(ESlave,ESlave) -> Boolean
	controlled grantCsa: Prod(EController,EController) -> Boolean
	controlled grantSkex: Prod(Slave,Slave) -> Boolean
	controlled grantSkex: Prod(Controller,Controller) -> Boolean
	controlled grantSkex: Prod(ESlave,Controller) -> Boolean
	controlled grantSkex: Prod(Controller,ESlave) -> Boolean
	controlled grantSkex: Prod(Slave,EController) -> Boolean
	controlled grantSkex: Prod(EController,Slave) -> Boolean
	controlled grantSkex: Prod(ESlave,ESlave) -> Boolean
	controlled grantSkex: Prod(EController,EController) -> Boolean	
	controlled grantEcdh: Prod(Slave,Slave) -> Boolean
	controlled grantEcdh: Prod(Controller,Controller) -> Boolean
	controlled grantEcdh: Prod(ESlave,Controller) -> Boolean
	controlled grantEcdh: Prod(Controller,ESlave) -> Boolean
	controlled grantEcdh: Prod(Slave,EController) -> Boolean
	controlled grantEcdh: Prod(EController,Slave) -> Boolean
	controlled grantEcdh: Prod(ESlave,ESlave) -> Boolean
	controlled grantEcdh: Prod(EController,EController) -> Boolean
	controlled grantS2Access: Prod(Slave,Slave) -> Boolean
	controlled grantS2Access: Prod(Controller,Controller) -> Boolean
	controlled grantS2Access: Prod(ESlave,Controller) -> Boolean
	controlled grantS2Access: Prod(Controller,ESlave) -> Boolean
	controlled grantS2Access: Prod(Slave,EController) -> Boolean
	controlled grantS2Access: Prod(EController,Slave) -> Boolean
	controlled grantS2Access: Prod(ESlave,ESlave) -> Boolean
	controlled grantS2Access: Prod(EController,EController) -> Boolean
	controlled grantS2Auth: Prod(Slave,Slave) -> Boolean
	controlled grantS2Auth: Prod(Controller,Controller) -> Boolean
	controlled grantS2Auth: Prod(ESlave,Controller) -> Boolean
	controlled grantS2Auth: Prod(Controller,ESlave) -> Boolean
	controlled grantS2Auth: Prod(Slave,EController) -> Boolean
	controlled grantS2Auth: Prod(EController,Slave) -> Boolean
	controlled grantS2Auth: Prod(ESlave,ESlave) -> Boolean
	controlled grantS2Auth: Prod(EController,EController) -> Boolean
	controlled grantS2Unauth: Prod(Slave,Slave) -> Boolean
	controlled grantS2Unauth: Prod(Controller,Controller) -> Boolean
	controlled grantS2Unauth: Prod(ESlave,Controller) -> Boolean
	controlled grantS2Unauth: Prod(Controller,ESlave) -> Boolean
	controlled grantS2Unauth: Prod(Slave,EController) -> Boolean
	controlled grantS2Unauth: Prod(EController,Slave) -> Boolean
	controlled grantS2Unauth: Prod(ESlave,ESlave) -> Boolean
	controlled grantS2Unauth: Prod(EController,EController) -> Boolean
	controlled grantS0: Prod(Agent,Agent) -> Boolean
	/*controlled grantS0: Prod(Slave,Slave) -> Boolean
	controlled grantS0: Prod(Controller,Controller) -> Boolean
	controlled grantS0: Prod(ESlave,Controller) -> Boolean
	controlled grantS0: Prod(Controller,ESlave) -> Boolean
	controlled grantS0: Prod(Slave,EController) -> Boolean
	controlled grantS0: Prod(EController,Slave) -> Boolean
	controlled grantS0: Prod(ESlave,ESlave) -> Boolean
	controlled grantS0: Prod(EController,EController) -> Boolean*/

	controlled showCsaDialog: Controller -> Boolean	
	controlled aesSlaveKey : Slave -> AESKeyType
	controlled aesCtrlKey : Controller -> AESKeyType
	controlled aesSlaveKey : ESlave -> AESKeyType
	controlled aesCtrlKey : EController -> AESKeyType
	controlled sendedPubKeyS: Slave ->  KeyType
	controlled sendedPubKeyC: Controller ->  KeyType
	controlled sendedPubKeyS: ESlave ->  KeyType
	controlled sendedPubKeyC: EController ->  KeyType
	
	derived keyAssociation: Controller -> KeyType
	derived keyAssociation: Slave -> KeyType
	derived keyAssociation: EController -> KeyType
	derived keyAssociation: ESlave -> KeyType
	
	static messageDecrypt: Prod(AESKeyType,AESKeyType) -> Boolean
	static aesPinKeyKey: Prod(PinType,KeyType,KeyType) -> AESKeyType	
	static aesKeyKey: Prod(KeyType,KeyType) -> AESKeyType
	
	static devRelCsa: SlaveType -> Boolean
	static devRelS2Access: SlaveType -> Boolean
	static devRelS2Auth: SlaveType -> Boolean
	static devRelS2Unauth: SlaveType -> Boolean	
	static devRelSkex: SlaveType -> Boolean
	static devRelEcdh: SlaveType -> Boolean
		
	static nodeA: Controller
	static nodeB: Slave
	static nodeEC: EController
	static nodeES: ESlave
		

definitions:	
	function devRelCsa($s in SlaveType) =
		switch( $s )
			case DOOR_LOCK_NAT: false
			case DOOR_LOCK_UP: true
			case GARAGE_LOCK_NAT:false
			case GARAGE_LOCK_UP: true
			case SWITCH_NAT: false
			case LIGHT_NAT: false
			case SENSOR_NAT: false
			case SECURITY_SENSOR_NAT: false
			case THERMOSTAT_NAT: false
			case THERMOSTAT_UP: true
			case ALARM_NAT: false
			case ALARM_UP: true
			case LEGACY_DEVICE:true
		endswitch
		
	function devRelS2Access($s in SlaveType) =
		switch( $s )
			case DOOR_LOCK_NAT: true
			case DOOR_LOCK_UP: true
			case GARAGE_LOCK_NAT: true
			case GARAGE_LOCK_UP: true
			case SWITCH_NAT: false
			case LIGHT_NAT: false
			case SENSOR_NAT: false
			case SECURITY_SENSOR_NAT: false
			case THERMOSTAT_NAT: false
			case THERMOSTAT_UP: false
			case ALARM_NAT: false
			case ALARM_UP: false
			case LEGACY_DEVICE:false
		endswitch
	
	function devRelS2Auth($s in SlaveType) =
		switch( $s )
			case DOOR_LOCK_NAT: false
			case DOOR_LOCK_UP: false
			case GARAGE_LOCK_NAT:false
			case GARAGE_LOCK_UP: false
			case SWITCH_NAT: false
			case LIGHT_NAT: false
			case SENSOR_NAT: false
			case SECURITY_SENSOR_NAT: true
			case THERMOSTAT_NAT: true
			case THERMOSTAT_UP: true
			case ALARM_NAT: true
			case ALARM_UP: true
			case LEGACY_DEVICE:false
		endswitch
	
	function devRelS2Unauth($s in SlaveType) =
		switch( $s )
			case DOOR_LOCK_NAT: false
			case DOOR_LOCK_UP: false
			case GARAGE_LOCK_NAT:false
			case GARAGE_LOCK_UP: false
			case SWITCH_NAT: true
			case LIGHT_NAT: true
			case SENSOR_NAT: true
			case SECURITY_SENSOR_NAT: true
			case THERMOSTAT_NAT: true
			case THERMOSTAT_UP: true
			case ALARM_NAT: true
			case ALARM_UP: true
			case LEGACY_DEVICE:false
		endswitch
		
	function devRelSkex($s in SlaveType) =
		switch( $s )
			case DOOR_LOCK_NAT: true
			case DOOR_LOCK_UP: true
			case GARAGE_LOCK_NAT: true
			case GARAGE_LOCK_UP: true
			case SWITCH_NAT: true
			case LIGHT_NAT: true
			case SENSOR_NAT: true
			case SECURITY_SENSOR_NAT: true
			case THERMOSTAT_NAT: true
			case THERMOSTAT_UP: true
			case ALARM_NAT: true
			case ALARM_UP: true
			case LEGACY_DEVICE:false
		endswitch
	
	function devRelEcdh($s in SlaveType) =
		switch( $s )
			case DOOR_LOCK_NAT: true
			case DOOR_LOCK_UP: true
			case GARAGE_LOCK_NAT: true
			case GARAGE_LOCK_UP: true
			case SWITCH_NAT: true
			case LIGHT_NAT: true
			case SENSOR_NAT: true
			case SECURITY_SENSOR_NAT: true
			case THERMOSTAT_NAT: true
			case THERMOSTAT_UP: true
			case ALARM_NAT: true
			case ALARM_UP: true
			case LEGACY_DEVICE:false
		endswitch	
		
	function keyAssociation($a in Controller) =	
		switch( $a )
			case nodeA: KPUB_CONTROLLER 		
		endswitch
		
	function keyAssociation($a in Slave) =	
		switch( $a )
			case nodeB: KPUB_SLAVE
		endswitch	

	function keyAssociation($a in EController) =	
		switch( $a )				  
			case nodeEC: KPUB_MITM_CTRL
		endswitch
		
	function keyAssociation($a in ESlave) =	
		switch( $a ) 
			case nodeES: KPUB_MITM_SLV
		endswitch

	function messageDecrypt($ac in AESKeyType,$as in AESKeyType) =
		if( $ac = KT and $as = KT) then
			true
		else
			false		
		endif
		
	function aesPinKeyKey($p in PinType,$kp in KeyType,$k in KeyType) =
		if($p = PIN_OK)then
			if(($kp = OB_KEY_MITM_SLV and $k = KPUB_CONTROLLER) or( $kp = OB_KEY_SLAVE and $k = KPUB_MITM_CTRL))then
				KT
			else
				KT_ERROR
			endif
		else
			KT_ERROR
		endif
		
	function aesKeyKey($kc in KeyType,$ks in KeyType) =
		if(($kc = KPUB_CONTROLLER and $ks = KPUB_MITM_SLV) or ($kc =KPUB_MITM_CTRL and $ks = KPUB_SLAVE))then
			KT
		else
			KT_ERROR
		endif	
					
				
	rule r_initEnvCtrl =
		if(controllerState(self) = INIT_CTRL)then
			let($ctype =controller(self)) in
				if($ctype = CONTROLLER_S2)then
					par				
						// csa set to 0 for default max security	
						ctrlCsa(self) := false
						ctrlSkex(self) := true
						ctrlEcdh(self) := true
						ctrlS2Access(self) := true	
						ctrlS2Auth(self) := true
						ctrlS2Unauth(self) := true
						ctrlS0(self) := true
						showCsaDialog(self) := false
						controllerState(self) := ADD_MODE
					endpar
				else
					if($ctype = CONTROLLER_LEGACY)then
						par				
							// csa set to 0 for default max security	
							ctrlCsa(self) := false
							ctrlSkex(self) := false
							ctrlEcdh(self) := false
							ctrlS2Access(self) := false	
							ctrlS2Auth(self) := false
							ctrlS2Unauth(self) := false
							ctrlS0(self) := true
							showCsaDialog(self) := false
							controllerState(self) := ADD_MODE
						endpar				
					endif
				endif
			endlet			
		endif
	
	rule r_initEnvSlv =
		if(slaveState(self) = INIT_SLV)then
			par
							
				let($type = slave(self)) in
					par
						slvSkex(self) := devRelSkex( $type )
						slvEcdh(self) := devRelEcdh( $type )	
						slvCsa(self) := devRelCsa( $type )
						slvS2Access(self) := devRelS2Access( $type )
						slvS2Auth(self) := devRelS2Auth( $type )
						slvS2Unauth(self) := devRelS2Unauth( $type )
					endpar
				endlet
				slvS0(self) := true
				slaveState(self) := LEARN_MODE
				startTimer(TB1) := true
			endpar		
		endif
		
	rule r_timeoutTb1 =
		if(startTimer(TB1))then
			if(slaveState(self) = LEARN_MODE and passed(TB1) ) then			
				slaveState(self) := TIMEOUT_S		
			endif	
		endif	
			
	
	rule r_kexGet =
		if(controllerState(self) = ADD_MODE ) then
			choose $eslv in ESlave with true do
				par 
					protocolMessage( self , $eslv ):= KEX_GET
					controllerState(self) := WAIT_KEX_REP
					startTimer(TA1) := true
				endpar			
		endif
	
	rule r_kexGetReplay =
		choose $slv in Slave with true do
			if(slaveState( $slv ) = LEARN_MODE) then				
				protocolMessage( self , $slv ):= KEX_GET						
			endif	
			
	
	rule r_kexReport =
		choose $ectrl in EController with true do
			if(slaveState(self) = LEARN_MODE  and protocolMessage(  $ectrl , self ) = KEX_GET and not passed(TB1)) then
				par
					slaveState(self) := WAIT_KEX_SET
					protocolMessage(  self, $ectrl ) := KEX_REP
					startTimer(TB2) := true
					startTimer(TB1) := false				
					//save for control of authenticity
					reqCsa(self,self) := slvCsa(self)
					reqSkex(self,self) := slvSkex(self)
					reqEcdh(self,self) := slvEcdh(self)
					reqS2Access(self,self) := slvS2Access(self)
					reqS2Auth(self,self) := slvS2Auth(self)
					reqS2Unauth(self,self) := slvS2Unauth(self)
					reqS0(self,self) := slvS0(self)
					// KEX REPORT payload
					reqCsa( self , $ectrl ) := slvCsa(self)
					reqSkex( self , $ectrl ) := slvSkex(self)
					reqEcdh( self , $ectrl ) := slvEcdh(self)
					reqS2Access( self , $ectrl ) := slvS2Access(self)
					reqS2Auth( self , $ectrl ) := slvS2Auth(self)
					reqS2Unauth( self , $ectrl ) := slvS2Unauth(self)
					reqS0( self , $ectrl ) := slvS0(self)
					
				endpar
					
			endif
			
	rule r_kexSetReplay =	
		choose $slv in Slave with true do
			if(protocolMessage( $slv , self ) = KEX_REP )then
				par		
					messageArrived(KEX_REP) := true
					protocolMessage(  self, $slv ) := KEX_SET			
					//save for control of authenticity					
					reqCsa(self,self) := slvCsa( $slv )
					reqSkex(self,self) := slvSkex( $slv )
					reqEcdh(self,self) := slvEcdh( $slv )
					reqS2Access(self,self) := slvS2Access( $slv )
					reqS2Auth(self,self) := slvS2Auth( $slv )
					reqS2Unauth(self,self) := slvS2Unauth( $slv )
					reqS0(self,self) := slvS0($slv )					
					// KEX SET payload, auto approves all requested key
					grantCsa( self , $slv ) := slvCsa( $slv )
					grantSkex( self , $slv ) := slvSkex( $slv )
					grantEcdh( self , $slv ) := slvEcdh( $slv )
					grantS2Access( self , $slv ) := slvS2Access( $slv )
					grantS2Auth( self , $slv ) := slvS2Auth( $slv )
					grantS2Unauth( self , $slv ) := slvS2Unauth( $slv )
					grantS0( self , $slv ) := slvS0($slv )
					// save KEX SET payload for control of authenticity
					grantCsa( self , self ) := slvCsa( $slv )
					grantSkex( self , self ) := slvSkex( $slv )
					grantEcdh( self , self ) := slvEcdh( $slv )
					grantS2Access( self , self ) := slvS2Access( $slv )
					grantS2Auth( self , self ) := slvS2Auth( $slv )
					grantS2Unauth( self , self ) := slvS2Unauth( $slv )
					grantS0( self , self ) := slvS0($slv )
				endpar
			endif
	
	rule r_kexReportReplay =
		choose $ctrl in Controller, $ectrl in EController with true do
			if(protocolMessage(  $ctrl , self ) = KEX_GET and  messageArrived(KEX_REP) = true and nearToEnd(TA1))then
				par
					protocolMessage(  self, $ctrl ) := KEX_REP
					//save for control of authenticity
					reqCsa(self,self) := reqCsa($ectrl , $ectrl )
					reqSkex(self,self) := reqSkex($ectrl , $ectrl )
					reqEcdh(self,self) := reqEcdh($ectrl , $ectrl )
					reqS2Access(self,self) := reqS2Access($ectrl , $ectrl )
					reqS2Auth(self,self) := reqS2Auth($ectrl , $ectrl )
					reqS2Unauth(self,self) := reqS2Unauth($ectrl , $ectrl )
					reqS0(self,self) := reqS0($ectrl , $ectrl )
					// KEX REPORT payload
					reqCsa( self , $ctrl ) := reqCsa($ectrl , $ectrl )
					reqSkex( self , $ctrl ) := reqSkex($ectrl , $ectrl )
					reqEcdh( self , $ctrl ) := reqEcdh($ectrl , $ectrl )
					reqS2Access( self , $ctrl ) := reqS2Access($ectrl , $ectrl )
					reqS2Auth( self , $ctrl ) := reqS2Auth($ectrl , $ectrl )
					reqS2Unauth( self , $ctrl ) :=  reqS2Unauth($ectrl , $ectrl )
					reqS0( self , $ctrl ) := reqS0($ectrl , $ectrl )
				endpar
			endif
			
	rule r_timeoutTa1 =
		if(startTimer(TA1))then
			if(controllerState(self) = WAIT_KEX_REP and passed(TA1)) then			
				controllerState(self) := TIMEOUT_C		
			endif
		endif
		
	rule r_timeoutTia1 =
		if(startTimer(TAI1))then
			if( (controllerState(self) = WAIT_EVAL_KEX_KEY and controllerState(self) = WAIT_EVAL_KEX_SCHEME or controllerState(self) = WAIT_EVAL_KEX_CURVE or controllerState(self) = WAIT_EVAL_CSA) and passed(TAI1)) then			
				controllerState(self) := TIMEOUT_C		
			endif
		endif	
	
	rule r_evalKexReport =
		choose $eslv in ESlave with true do
			if(controllerState(self) = WAIT_KEX_REP  and protocolMessage(  $eslv , self ) = KEX_REP and not passed(TA1)) then
				par				
					controllerState(self) := WAIT_EVAL_KEX_KEY
					startTimer(TAI1) := true
					startTimer(TA1) := false
				endpar				
			else			
				if(controllerState(self) = WAIT_EVAL_KEX_KEY  and protocolMessage(  $eslv , self ) = KEX_REP and (reqS2Access(  $eslv , self ) = ctrlS2Access(self) or reqS2Auth(  $eslv , self ) = ctrlS2Auth(self) or reqS2Unauth(  $eslv , self ) = ctrlS2Unauth(self) or reqS0(  $eslv , self ) = ctrlS0(self)) and not passed(TAI1)) then
					par
						controllerState(self) := WAIT_EVAL_KEX_SCHEME
						//save KEX REPORT key list reveived for reeslvding
						if(reqS2Access(  $eslv , self ) != ctrlS2Access(self) or (reqS2Access(  $eslv , self ) = ctrlS2Access(self) and reqS2Access(  $eslv , self ) = false ))then
							reqS2Access(self,self) := false
						else
							reqS2Access(self,self) := true
						endif
						if(reqS2Auth(  $eslv , self ) != ctrlS2Auth(self) or (reqS2Auth(  $eslv , self ) = ctrlS2Auth(self) and reqS2Auth(  $eslv , self ) = false))then
							reqS2Auth(self,self) := false
						else
							reqS2Auth(self,self) := true
						endif
						if(reqS2Unauth(  $eslv , self ) != ctrlS2Unauth(self) or (reqS2Unauth(  $eslv , self ) = ctrlS2Unauth(self) and reqS2Unauth(  $eslv , self ) = false))then
							reqS2Unauth(self,self) := false
						else
							reqS2Unauth(self,self) := true
						endif
						if(reqS0(  $eslv , self ) != ctrlS0(self) or (reqS0(  $eslv , self ) = ctrlS0(self) and reqS0(  $eslv , self ) = false))then
							reqS0(self,self) := false
						else
							reqS0(self,self) := true
						endif										
					endpar					
				else
					if(controllerState(self) = WAIT_EVAL_KEX_KEY and not passed(TAI1) and protocolMessage(  $eslv , self ) = KEX_REP and not (reqS2Access(  $eslv , self ) = ctrlS2Access(self) or reqS2Auth(  $eslv , self ) = ctrlS2Auth(self) or reqS2Unauth(  $eslv , self ) = ctrlS2Unauth(self) or reqS0(  $eslv , self ) = ctrlS0(self))) then
						par
							protocolMessage(  self , $eslv ) := KEX_FAIL_KEX_KEY
							controllerState(self) := ERROR_C
						endpar
					else
						if(controllerState(self) = WAIT_EVAL_KEX_SCHEME and not passed(TAI1) and protocolMessage(  $eslv , self ) = KEX_REP and reqSkex(  $eslv , self ) = true ) then
							par
								controllerState(self) := WAIT_EVAL_KEX_CURVE
								//save KEX REPORT scheme 
								reqSkex(  self , self ) := true							
							endpar
						else
							if(controllerState(self) = WAIT_EVAL_KEX_SCHEME and not passed(TAI1) and protocolMessage(  $eslv , self ) = KEX_REP and  reqSkex(  $eslv , self ) = false ) then
								par
									protocolMessage(  self , $eslv ) := KEX_FAIL_KEX_SCHEME
									controllerState(self) := ERROR_C
								endpar	
							else
								if(controllerState(self) = WAIT_EVAL_KEX_CURVE and not passed(TAI1) and protocolMessage(  $eslv , self ) = KEX_REP and reqEcdh(  $eslv , self ) = true ) then
									par
										controllerState(self) := WAIT_EVAL_CSA
										//save KEX REPORT curve
										reqEcdh(  self , self ) := true
									endpar
								else
									if(controllerState(self) = WAIT_EVAL_KEX_CURVE and not passed(TAI1) and protocolMessage(  $eslv , self ) = KEX_REP and  reqEcdh(  $eslv , self ) = false ) then
										par
											protocolMessage(  self , $eslv ) := KEX_FAIL_KEX_CURVE
											controllerState(self) := ERROR_C
										endpar	
									else
										if(controllerState(self) = WAIT_EVAL_CSA and not passed(TAI1) and protocolMessage(  $eslv , self ) = KEX_REP)then
											let($csa = userCsa(self)) in 
												if(reqCsa(  $eslv , self ) = $csa )then
													par
														controllerState(self) := WAIT_ECDH_PUB_JOIN													
														if(reqCsa(  $eslv , self ) = true )then
															par												
																showCsaDialog(self) := true
																//save	KEX REPORT csa
																reqCsa(  self , self ) := true
															endpar
														else
															if(reqCsa(  $eslv , self ) = false )then																													
																	//save	KEX REPORT csa
																	reqCsa(  self , self ) := false														
															endif																			
														endif
													endpar
												else
												
													par
														controllerState(self) := ERROR_C
														protocolMessage( self , $eslv ) := KEX_FAIL_CANCEL
													endpar
												endif
											endlet
										endif																				
									endif
								endif							
							endif
						endif						
					endif				
				endif					
			endif
	
	rule r_timeoutTa2 =
		if(startTimer(TA2))then
			if(controllerState(self) = WAIT_ECDH_PUB_JOIN and passed(TA2)) then			
				controllerState(self) := TIMEOUT_C		
			endif
		endif	
			
	rule r_kexSet =
		choose $eslv in ESlave with true do
			if(controllerState(self) = WAIT_ECDH_PUB_JOIN and not passed(TAI1) and protocolMessage(  $eslv , self ) = KEX_REP and not(protocolMessage( self, $eslv ) = KEX_SET))then
				par
					protocolMessage( self, $eslv ) := KEX_SET					
					startTimer(TA2) := true
					startTimer(TAI1) := false				
					//KEX SET payload
					grantCsa( self , $eslv ) := reqCsa(self,self)
					grantSkex(  self , $eslv ) := ctrlSkex(self)
					grantEcdh(  self , $eslv ) := ctrlEcdh(self)
					//save KEX SET csa,skex,ecdh
					grantCsa(self,self) := reqCsa(self,self)
					grantSkex(self,self) := ctrlSkex(self)
					grantEcdh(self,self) := ctrlEcdh(self)
					if(reqS2Access(self,self) = reqS2Access( $eslv , self ) and reqS2Access( $eslv , self ) = true )then
						if(userGrantS2Access)then
							par
								grantS2Access( self , $eslv ) := true
								//save KEX SET S2access key
								grantS2Access(self,self) := true
							endpar
						else
							par
								grantS2Access( self , $eslv ) := false
								//save KEX SET S2access key
								grantS2Access(self,self) := false
							endpar
						endif
					else
						par
							grantS2Access( self , $eslv ) := false
							//save KEX SET S2access key
							grantS2Access(self,self) := false
						endpar
					endif
					if(reqS2Auth(self,self) = reqS2Auth( $eslv , self ) and reqS2Auth( $eslv , self ) = true )then
						if(userGrantS2Auth)then
							par
								grantS2Auth( self , $eslv ) := true
								//save KEX SET S2Auth key
								grantS2Auth(self,self) := true
							endpar
						else
							par
								grantS2Auth( self , $eslv ) := false
								//save KEX SET S2Auth key
								grantS2Auth(self,self) := false
							endpar
						endif
					else
						par
							grantS2Auth( self , $eslv ) := false
							//save KEX SET S2Auth key
							grantS2Auth(self,self) := false
						endpar
					endif
					if(reqS2Unauth(self,self) = reqS2Unauth( $eslv , self ) and reqS2Unauth( $eslv , self ) = true )then
						if(userGrantS2Unauth)then
							par
								grantS2Unauth( self , $eslv ) := true
								//save KEX SET S2Unauth
								grantS2Unauth(self,self) := true
							endpar
						else
							par
								grantS2Unauth( self , $eslv ) := false
								//save KEX SET S2Unauth
								grantS2Unauth(self,self) := false
							endpar
						endif
					else
						par
							grantS2Unauth( self , $eslv ) := false
							//save KEX SET S2Unauth
							grantS2Unauth(self,self) := false
						endpar
					endif
					if(reqS0(self,self) = reqS0( $eslv , self ) and reqS0( $eslv , self ) = true )then
						if(userGrantS0)then
							par
								grantS0( self , $eslv ) := true
								//save KEX SET S0
								grantS0(self,self) := true
							endpar
						else
							par
								grantS0( self , $eslv ) := false
								//save KEX SET S0
								grantS0(self,self) := false
							endpar
						endif
					else
						par
							grantS0( self , $eslv ) := false
							//save KEX SET S0
							grantS0(self,self) := false
						endpar
					endif
				endpar
			endif			
	
	rule r_saveKexSet =
		choose $ctrl in Controller with true do
			if(protocolMessage( $ctrl , self ) = KEX_SET)then 
				par
					grantCsa(self,self) := grantCsa( $ctrl , self )
					grantSkex(self,self) := grantSkex( $ctrl , self )
					grantEcdh(self,self) := grantEcdh( $ctrl , self )
					grantS2Access(self,self) := grantS2Access( $ctrl , self )
					grantS2Auth(self,self) := grantS2Auth( $ctrl , self )
					grantS2Unauth(self,self) := grantS2Unauth( $ctrl , self )
					grantS0(self,self) := grantS0( $ctrl , self )
				endpar		
			endif
			
	
	
		
	rule r_timeoutTb2 =
		if(startTimer(TB2))then
			if(slaveState(self) = WAIT_KEX_SET and passed(TB2)) then			
				slaveState(self) := TIMEOUT_S		
			endif
		endif
	
	rule r_evalKexSet =
		choose $ectrl in EController with true do
			if(slaveState(self) = WAIT_KEX_SET and not passed(TB2) and protocolMessage(  $ectrl , self ) = KEX_SET) then				
				par
					slaveState(self) := WAIT_EVAL_SET_KEX_KEY
					startTimer(TB2) := false
				endpar				
			else			
				if(slaveState(self) = WAIT_EVAL_SET_KEX_KEY  and protocolMessage(  $ectrl , self ) = KEX_SET and ((grantS2Access( $ectrl , self ) = reqS2Access(self,self) or (grantS2Access( $ectrl , self ) = false and reqS2Access(self,self) = true))  and (grantS2Auth( $ectrl , self ) = reqS2Auth(self,self) or (grantS2Auth( $ectrl , self ) = false and reqS2Auth(self,self) = true)) and (grantS2Unauth( $ectrl , self ) = reqS2Unauth(self,self) or (grantS2Unauth( $ectrl , self ) = false and reqS2Unauth(self,self) = true)) and (grantS0( $ectrl , self ) = reqS0(self,self) or (grantS0( $ectrl , self ) = false and reqS0(self,self) = true)))) then
					par
						slaveState(self) := WAIT_EVAL_SET_KEX_SCHEME
						//save KEX SET key list
						grantS2Access(self,self) := grantS2Access(  $ectrl , self )
						grantS2Auth(self,self) := grantS2Auth(  $ectrl , self )
						grantS2Unauth(self,self) := grantS2Unauth(  $ectrl , self )
						grantS0(self,self) := grantS0(  $ectrl , self )
					endpar
				else
					if(slaveState(self) = WAIT_EVAL_SET_KEX_KEY and protocolMessage(  $ectrl , self ) = KEX_SET and not ((grantS2Access( $ectrl , self ) = reqS2Access(self,self) or (grantS2Access( $ectrl , self ) = false and reqS2Access(self,self) = true))  and (grantS2Auth( $ectrl , self ) = reqS2Auth(self,self) or (grantS2Auth( $ectrl , self ) = false and reqS2Auth(self,self) = true)) and (grantS2Unauth( $ectrl , self ) = reqS2Unauth(self,self) or (grantS2Unauth( $ectrl , self ) = false and reqS2Unauth(self,self) = true)) and (grantS0( $ectrl , self ) = reqS0(self,self) or (grantS0( $ectrl , self ) = false and reqS0(self,self) = true)))) then
						par
							protocolMessage(  self , $ectrl ) := KEX_FAIL_KEX_KEY
							slaveState(self) := ERROR_S
						endpar
					else
						if(slaveState(self) = WAIT_EVAL_SET_KEX_SCHEME and protocolMessage(  $ectrl , self ) = KEX_SET and grantSkex( $ectrl , self ) = true ) then
							par
								slaveState(self) := WAIT_EVAL_SET_KEX_CURVE
								//save KEX SET scheme
								grantSkex(self,self) := true
							endpar
						else
							if(slaveState(self) = WAIT_EVAL_SET_KEX_SCHEME and protocolMessage(  $ectrl , self ) = KEX_SET and  grantSkex( $ectrl , self ) = false ) then
								par
									protocolMessage(  self , $ectrl ) := KEX_FAIL_KEX_SCHEME
									slaveState(self) := ERROR_S
								endpar	
							else
								if(slaveState(self) = WAIT_EVAL_SET_KEX_CURVE and protocolMessage(  $ectrl , self ) = KEX_SET and grantEcdh( $ectrl , self ) = true ) then
									par
										slaveState(self) := WAIT_EVAL_SET_CSA
										//save KEX SET curve
										grantEcdh(self,self) := true
									endpar
								else
									if(slaveState(self) = WAIT_EVAL_SET_KEX_CURVE and protocolMessage(  $ectrl , self ) = KEX_SET and  grantEcdh( $ectrl , self ) = false ) then
										par
											protocolMessage(  self , $ectrl ) := KEX_FAIL_KEX_CURVE
											slaveState(self) := ERROR_S
										endpar	
									else										
										if(slaveState(self) = WAIT_EVAL_SET_CSA and protocolMessage(  $ectrl , self ) = KEX_SET and grantCsa( $ectrl , self ) = reqCsa( self , self ) ) then
											par
												slaveState(self) := WAIT_ECDH_PUB_CTRL												
												// save KEX SET csa
												grantCsa(self,self) := grantCsa( $ectrl , self )
												
											endpar
										else
											if(slaveState(self) = WAIT_EVAL_SET_CSA and protocolMessage(  $ectrl , self ) = KEX_SET and  grantCsa( $ectrl , self ) != reqCsa( self , self )) then
												par
													slaveState(self) := ERROR_S
													protocolMessage( self , $ectrl ) := KEX_FAIL_CANCEL
												endpar
											endif
										endif																				
									endif
								endif							
							endif
						endif						
					endif				
				endif					
			endif
	
	rule r_timeoutTb3 =
		if(startTimer(TB3))then
			if(slaveState(self) = WAIT_ECDH_PUB_CTRL and passed(TB3))then
				slaveState(self) := TIMEOUT_S
			endif
		endif				
	
	
	rule r_sendSlvPubKey =
		choose $ectrl in EController with true do
			if(slaveState(self) = WAIT_ECDH_PUB_CTRL and protocolMessage(  $ectrl , self ) = KEX_SET )then
				par
					protocolMessage( self, $ectrl ) := PUB_KEY_REP_JOIN
					startTimer(TB3) := true
					if(grantCsa( self , self ) = true )then												
						sendedPubKeyS(self) := KPUB_SLAVE	
					else 
						sendedPubKeyS(self) := OB_KEY_SLAVE															
					endif
				endpar
			endif
			
			
	rule r_sendEctrlKey =
		choose $slv in Slave with true do
			if(protocolMessage( $slv , self ) = PUB_KEY_REP_JOIN and sendedPubKeyS( $slv ) = OB_KEY_SLAVE)then
				par					
					protocolMessage( self , $slv ) := PUB_KEY_REP_CTRL
					sendedPubKeyC(self) := KPUB_MITM_CTRL
				endpar
			endif
			
	//no time control needed
	rule r_sendEslvKey = 
		choose $ctrl in Controller with true do
			if(protocolMessage( $ctrl , self ) = KEX_SET and pinHacked = PIN_OK)then
				par
					protocolMessage( self, $ctrl ) := PUB_KEY_REP_JOIN
					sendedPubKeyS(self) := OB_KEY_MITM_SLV	
				endpar
			endif
			
		
	rule r_timeoutTia2 =
	if(startTimer(TAI2))then
		if(controllerState(self) = INSERT_PIN and controllerState(self) = WAIT_NONCE and controllerState(self) = WAIT_SEI_KEX_SET_ECHO and passed(TAI2)) then			
			controllerState(self) := TIMEOUT_C	
		endif
	endif			
			
	rule r_insertPin =
		choose $eslv in ESlave with true do
			if(controllerState(self) = WAIT_ECDH_PUB_JOIN  and not passed(TA2) and protocolMessage( $eslv ,self ) = PUB_KEY_REP_JOIN ) then
				par
					controllerState(self) := INSERT_PIN
					startTimer(TAI2) := true
					startTimer(TA2) := false
				endpar
			else
				if(controllerState(self) = INSERT_PIN and not passed(TAI2) and grantCsa( self , self ) = false and protocolMessage( $eslv ,self ) = PUB_KEY_REP_JOIN) then
					par
						controllerState(self) := WAIT_NONCE
						protocolMessage( self , $eslv ) := PUB_KEY_REP_CTRL
						sendedPubKeyC(self) := KPUB_CONTROLLER
						if(pinCode = PIN_OK) then
							pinSaved := PIN_OK
						else
							pinSaved := PIN_ERROR
						endif
					endpar
				else
					if(controllerState(self) = INSERT_PIN and not passed(TAI2) and grantCsa( self , self ) = true and protocolMessage( $eslv ,self ) = PUB_KEY_REP_JOIN) then
						par
							controllerState(self) := WAIT_NONCE
							protocolMessage( self , $eslv ) := PUB_KEY_REP_CTRL
							sendedPubKeyC(self) := OB_KEY_CTRL	
						endpar	
					endif
				endif	
			endif
	
	rule r_timeoutTib1 =
		if(startTimer(TBI1))then
			if(slaveState(self) = INSERT_PIN_CSA and slaveState(self) = WAIT_NONCE_REP_REI and slaveState(self) = WAIT_KEX_REPORT_ECHO and passed(TBI1))then
				slaveState(self) := TIMEOUT_S
			endif
		endif
	
	rule r_insertPinCsa =
		choose $ectrl in EController with true do
			if(slaveState(self) = WAIT_ECDH_PUB_CTRL and not passed(TB3) and protocolMessage( $ectrl ,self ) = PUB_KEY_REP_CTRL)then
				par				
					slaveState(self) := INSERT_PIN_CSA
					startTimer(TBI1) := true
					startTimer(TB3) := false
				endpar					
			else
				if(slaveState(self) = INSERT_PIN_CSA and not passed(TBI1) and grantCsa( self , self ) = false and protocolMessage( $ectrl ,self ) = PUB_KEY_REP_CTRL) then
					par
						slaveState(self) := WAIT_NONCE_REP_REI
						protocolMessage( self , $ectrl ) := NONCE_GET					
					endpar
				else
					if(slaveState(self) = INSERT_PIN_CSA and not passed(TBI1) and grantCsa( self , self ) = true and protocolMessage( $ectrl ,self ) = PUB_KEY_REP_CTRL) then
						par
							slaveState(self) := WAIT_NONCE_REP_REI
							protocolMessage( self , $ectrl ) := NONCE_GET	
							if(pinCode = PIN_OK) then
								pinSaved := PIN_OK
							else
								pinSaved := PIN_ERROR
							endif	
						endpar	
					endif
				endif	
			endif
			
	rule r_nonceGetReplay =
		choose $ctrl in Controller with true do 
			if(protocolMessage( $ctrl ,self ) = PUB_KEY_REP_CTRL)then
				protocolMessage( self , $ctrl ) := NONCE_GET
			endif
			
	rule r_nonceReport =
		choose $eslv in ESlave with true do
			if(controllerState(self) = WAIT_NONCE and not passed(TAI2) and protocolMessage( $eslv ,self ) = NONCE_GET)then
				par
					controllerState(self) := WAIT_SEI_KEX_SET_ECHO
					protocolMessage( self , $eslv ) := NONCE_REPORT					
				endpar
			endif
	
	rule r_nonceReportReplay =
		choose $slv in Slave with true do
			if(protocolMessage( $slv ,self ) = NONCE_GET)then				
					protocolMessage( self , $slv ) := NONCE_REPORT
			endif
	
	rule r_SPANestablishment =
		choose $ectrl in EController with true do
			if(slaveState(self) = WAIT_NONCE_REP_REI and not passed(TBI1) and protocolMessage( $ectrl ,self ) = NONCE_REPORT)then
				let($k = keyAssociation(self),$skc = sendedPubKeyC( $ectrl ) , $pin = pinSaved) in				
					if(grantCsa( self , self ) = true)then
						par							
							aesSlaveKey(self) := aesPinKeyKey($pin ,$skc, $k )							
							slaveState(self) := WAIT_KEX_REPORT_ECHO
							protocolMessage( self , $ectrl ) := EC_SEI_KEX_SET_ECHO
							grantCsa( self , $ectrl ) := grantCsa(self,self)
							grantEcdh( self , $ectrl )  := grantEcdh(self,self)
							grantSkex( self , $ectrl ) := grantSkex(self,self)
							grantS2Access( self , $ectrl ) := grantS2Access(self,self)  
							grantS2Auth( self , $ectrl ) := grantS2Auth(self,self) 
							grantS2Unauth( self , $ectrl ) := grantS2Unauth(self,self)  
							grantS0( self , $ectrl ) := grantS0(self,self)  
						endpar
					else
						par
							
							aesSlaveKey(self) := aesKeyKey($skc, $k )							
							slaveState(self) := WAIT_KEX_REPORT_ECHO
							protocolMessage( self , $ectrl ) := EC_SEI_KEX_SET_ECHO
							grantCsa( self , $ectrl ) := grantCsa(self,self)
							grantEcdh( self , $ectrl ) := grantEcdh(self,self)
							grantSkex( self , $ectrl ) := grantSkex(self,self)
							grantS2Access( self , $ectrl ) := grantS2Access(self,self)  
							grantS2Auth( self , $ectrl ) := grantS2Auth(self,self) 
							grantS2Unauth( self , $ectrl ) := grantS2Unauth(self,self)  
							grantS0( self , $ectrl ) := grantS0(self,self)  
						endpar					
					endif
				endlet	
			endif
			
			
	rule  r_bruteForce=
		choose $slv in Slave with true do
			if(protocolMessage( $slv , self ) = EC_SEI_KEX_SET_ECHO)then
				if(not nearToEnd(TA2)) then
					pinHacked := PIN_OK
				else
					pinHacked := PIN_ERROR
				endif	
			endif		
			
	rule r_SPANRepaly =
		choose $ctrl in Controller with true do
			if(protocolMessage( $ctrl , self ) = NONCE_REPORT)then
				let($k = keyAssociation(self),$skc = sendedPubKeyC( $ctrl )) in	
					par
						protocolMessage( self , $ctrl ) := EC_SEI_KEX_SET_ECHO
						aesSlaveKey(self) := aesKeyKey($skc, $k )	
						grantCsa( self , $ctrl ) := grantCsa(self,self)
						grantEcdh( self , $ctrl ) := grantEcdh(self,self)
						grantSkex( self , $ctrl ) := grantSkex(self,self)
						grantS2Access( self , $ctrl ) := grantS2Access(self,self)  
						grantS2Auth( self , $ctrl ) := grantS2Auth(self,self) 
						grantS2Unauth( self , $ctrl ) := grantS2Unauth(self,self)  
						grantS0( self , $ctrl ) := grantS0(self,self)
					endpar
				endlet	
			endif
	
	rule r_evalkexSetEcho =
		choose $eslv in ESlave with true do
			if(controllerState(self) = WAIT_SEI_KEX_SET_ECHO and not passed(TAI2) and protocolMessage( $eslv ,self ) = EC_SEI_KEX_SET_ECHO)then
				par
					startTimer(TAI2) := false
					let($kc = keyAssociation(self), $ks = sendedPubKeyS( $eslv ),$aesks = aesSlaveKey( $eslv ),$pin = pinSaved) in
						if(grantCsa(self,self) = true)then						
							let($aesCsa1 = aesKeyKey($kc , $ks )) in
								if(messageDecrypt( $aesCsa1 , $aesks )) then
									if(grantCsa( $eslv , self ) = grantCsa(self,self) and grantEcdh( $eslv , self ) = grantEcdh(self,self) and grantSkex( $eslv , self ) = grantSkex(self,self) and grantS2Access( $eslv , self ) = grantS2Access(self,self) and grantS2Auth( $eslv , self ) = grantS2Auth(self,self) and grantS2Unauth( $eslv , self ) = grantS2Unauth(self,self) and grantS0( $eslv , self ) = grantS0(self,self)) then
										par
											controllerState(self) := OK_C
											protocolMessage( self , $eslv ) := EC_KEX_REPORT_ECHO
											showCsaDialog(self) := false																						
											aesCtrlKey(self) := aesKeyKey($kc , $ks )											
											reqCsa( self , $eslv ) := reqCsa(self,self)
											reqEcdh( self , $eslv ) := reqEcdh(self,self)
											reqSkex( self , $eslv ) := reqSkex(self,self)
											reqS2Access( self , $eslv ) := reqS2Access(self,self)  
											reqS2Auth( self , $eslv ) := reqS2Auth(self,self) 
											reqS2Unauth( self , $eslv ) := reqS2Unauth(self,self)  
											reqS0( self , $eslv ) := reqS0(self,self)  
										endpar
									else
										par
											controllerState(self) := ERROR_C
											protocolMessage( self , $eslv ) := KEX_FAIL_AUTH
										endpar
									endif
								else
									par
										controllerState(self) := ERROR_C
										protocolMessage( self , $eslv ) := KEX_FAIL_DECRYPT
									endpar
								endif
							endlet						
						else						
							let($aesCsa0 = aesPinKeyKey($pin, $ks , $kc )) in
								if(messageDecrypt( $aesCsa0 ,$aesks )) then
									if(grantCsa( $eslv , self ) = grantCsa(self,self) and grantEcdh( $eslv , self ) = grantEcdh(self,self) and grantSkex( $eslv , self ) = grantSkex(self,self) and grantS2Access( $eslv , self ) = grantS2Access(self,self) and grantS2Auth( $eslv , self ) = grantS2Auth(self,self) and grantS2Unauth( $eslv , self ) = grantS2Unauth(self,self) and grantS0( $eslv , self ) = grantS0(self,self)) then
										par
											controllerState(self) := OK_C
											protocolMessage( self , $eslv ) := EC_KEX_REPORT_ECHO
											showCsaDialog(self) := false										
											aesCtrlKey(self) := aesPinKeyKey($pin , $ks , $kc )										
											reqCsa( self , $eslv ) := reqCsa(self,self)
											reqEcdh( self , $eslv ) := reqEcdh(self,self)
											reqSkex( self , $eslv ) := reqSkex(self,self)
											reqS2Access( self , $eslv ) := reqS2Access(self,self)  
											reqS2Auth( self , $eslv ) := reqS2Auth(self,self) 
											reqS2Unauth( self , $eslv ) := reqS2Unauth(self,self)  
											reqS0( self , $eslv ) := reqS0(self,self)  
										endpar
									else
										par
											controllerState(self) := ERROR_C
											protocolMessage( self , $eslv ) := KEX_FAIL_AUTH
										endpar
									endif
								else
									par
										controllerState(self) := ERROR_C
										protocolMessage( self , $eslv ) := KEX_FAIL_DECRYPT
									endpar
								endif
							endlet											
						endif
					endlet
				endpar
			endif	
			
	rule r_evalkexReportEcho =	
		choose $ectrl in EController with true do
			if(slaveState(self) = WAIT_KEX_REPORT_ECHO and not passed(TBI1) and protocolMessage( $ectrl ,self ) = EC_KEX_REPORT_ECHO)then
				par				
					let($kc = aesCtrlKey( $ectrl ), $ks = aesSlaveKey(self)) in						
							if(messageDecrypt( $kc , $ks )) then
								if(reqCsa( $ectrl , self ) = reqCsa(self,self) and reqEcdh( $ectrl , self ) = reqEcdh(self,self) and reqSkex( $ectrl , self ) = reqSkex(self,self) and reqS2Access( $ectrl , self ) = reqS2Access(self,self) and reqS2Auth( $ectrl , self ) = reqS2Auth(self,self) and reqS2Unauth( $ectrl , self ) = reqS2Unauth(self,self) and reqS0( $ectrl , self ) = reqS0(self,self)) then
									slaveState(self) := OK_S			 
								else
									par
										slaveState(self) := ERROR_S
										protocolMessage( self , $ectrl ) := KEX_FAIL_AUTH
									endpar
								endif
							else
								par
									slaveState(self) := ERROR_S
									protocolMessage( self , $ectrl ) := KEX_FAIL_DECRYPT
								endpar
							endif					
					endlet
					startTimer(TBI1) := false
				endpar	
			endif
			
	rule r_kexReportEchoReplay =
		choose $slv in Slave with true do
			if(protocolMessage( $slv ,self ) = EC_KEX_REPORT_ECHO and pinHacked = PIN_OK)then
				let($kc = keyAssociation(self), $ks = sendedPubKeyS( $slv ), $pin = pinHacked) in
					par
						protocolMessage( self , $slv ) := EC_KEX_REPORT_ECHO
						aesCtrlKey(self) := aesPinKeyKey($pin , $ks , $kc )
						reqCsa( self , $slv ) := reqCsa(self,self)
						reqEcdh( self , $slv ) := reqEcdh(self,self)
						reqSkex( self , $slv ) := reqSkex(self,self)
						reqS2Access( self , $slv ) := reqS2Access(self,self)  
						reqS2Auth( self , $slv ) := reqS2Auth(self,self) 
						reqS2Unauth( self , $slv ) := reqS2Unauth(self,self)  
						reqS0( self , $slv ) := reqS0(self,self)
					endpar
				endlet
			endif 
	
	rule r_failCatchSlave =
		choose $ectrl in EController with true do
			if((protocolMessage( $ectrl ,self ) = KEX_FAIL_KEX_KEY or protocolMessage( $ectrl ,self ) = KEX_FAIL_KEX_SCHEME or protocolMessage( $ectrl ,self ) = KEX_FAIL_KEX_CURVE or protocolMessage( $ectrl ,self ) = KEX_FAIL_CANCEL or protocolMessage( $ectrl ,self ) = KEX_FAIL_AUTH or protocolMessage( $ectrl ,self ) = KEX_FAIL_DECRYPT) and slaveState(self) != TIMEOUT_S )then
				slaveState(self) := ERROR_S
			endif
			
	rule r_failCatchCtrl =
		choose $eslv in Slave with true do
			if((protocolMessage( $eslv ,self ) = KEX_FAIL_KEX_KEY or protocolMessage( $eslv ,self ) = KEX_FAIL_KEX_SCHEME or protocolMessage( $eslv ,self ) = KEX_FAIL_KEX_CURVE or protocolMessage( $eslv ,self ) = KEX_FAIL_CANCEL or protocolMessage( $eslv ,self ) = KEX_FAIL_AUTH or protocolMessage( $eslv ,self ) = KEX_FAIL_DECRYPT) and controllerState(self) != TIMEOUT_C)then
				controllerState(self) := ERROR_C
			endif				
	
	rule r_controllerRule =
		par
			r_initEnvCtrl[]
			r_kexGet[]
			r_timeoutTa1[]
			r_timeoutTia1[]
			r_evalKexReport[]
			r_timeoutTa2[]
			r_kexSet[]
			r_timeoutTia2[]
			r_insertPin[]
			r_nonceReport[]
			r_evalkexSetEcho[]
			r_failCatchCtrl[]
		endpar
		
	rule r_controllerMitmRule =
		par
			r_kexGetReplay[]
			r_kexSetReplay[] //save received report and send kex set without change anything
			r_sendEctrlKey[]
			r_nonceReportReplay[]
			r_bruteForce[]
			r_kexReportEchoReplay[] 		
		endpar
	
	rule r_slaveRule =
		par
			r_initEnvSlv[]
			r_timeoutTb1[]
			r_kexReport[]
			r_timeoutTb2[]
			r_evalKexSet[]
			r_timeoutTb3[]
			r_sendSlvPubKey[]
			r_timeoutTib1[] 
			r_insertPinCsa[]
			r_SPANestablishment[]
			r_evalkexReportEcho[]
			r_failCatchSlave[]
						
		endpar
	
	rule r_slaveMitmRule =
		par
			r_kexReportReplay[]
			r_saveKexSet[]
			r_sendEslvKey[]
			r_nonceGetReplay[]
			r_SPANRepaly[]
						
		endpar
		
		

	//CTL spec
	
	//CTLSPEC not(ef(controllerState(nodeA)=OK_C and slaveState(nodeB)=OK_S))
	
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeA,nodeB) = KEX_FAIL_KEX_SCHEME))
	
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeB,nodeA) = KEX_FAIL_KEX_SCHEME))
	
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeA,nodeB) = KEX_FAIL_KEX_KEY))
	
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeB,nodeA) = KEX_FAIL_KEX_KEY))
	
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeB,nodeA) = KEX_FAIL_KEX_CURVE))
	
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeA,nodeB) = KEX_FAIL_KEX_CURVE))
	
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeB,nodeA) = KEX_FAIL_CANCEL))
	
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeA,nodeB) = KEX_FAIL_CANCEL))
	
	//gives true, because KEX_FAIL_DECRYPT is throwed when KEX REPORT ECHO was faked or there was an error
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeB,nodeA) = KEX_FAIL_DECRYPT))
	
	//KEX_FAIL_DECRYPT when pin inserted on the slave side
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeA,nodeB) = KEX_FAIL_DECRYPT and grantCsa(nodeA,nodeA)= true))
	
	//KEX_FAIL_DECRYPT when pin inserted on the controller side
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeA,nodeB) = KEX_FAIL_DECRYPT and grantCsa(nodeA,nodeA)= false))
	
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeB,nodeA) = KEX_FAIL_AUTH))
	
	//CTLSPEC not(ef(controllerState(nodeA)=ERROR_C and slaveState(nodeB)=ERROR_S and protocolMessage(nodeA,nodeB) = KEX_FAIL_AUTH))
	
	
	
	main rule r_Main =
		par
			program(nodeA)
			program(nodeES)
			program(nodeEC)
			program(nodeB)		
		endpar



	
default init s0:
	function slaveState($a in Slave) = if($a = nodeB )then INIT_SLV endif
	function controllerState($c in Controller) = if($c = nodeA )then INIT_CTRL endif
	function startTimer($s in Time)= false
	function pinHacked = PIN_ERROR
	function messageArrived($m in ProtocolMessageType) = false
	
	agent Controller:		
			r_controllerRule[]
			
	agent EController:		
			r_controllerMitmRule[]
				
	agent Slave:
			r_slaveRule[]
			
	agent ESlave:		
			r_slaveMitmRule[]
		
				
