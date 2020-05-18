asm StereoacuityRaff5

import ./STDL/StandardLibrary
import ./STDL/CTLlibrary
import ./STDL/LTLlibrary

signature:
	enum domain Answers={SKIP | EXIT | AA | BB | CC | DD}
	enum domain Shapes = {AAA | BBB | CCC | DDD}
	enum domain OutMessage={CERTIFICATE | NOTCERTIFICATE}
	domain Level subsetof Integer
	domain Certificate subsetof Integer
	domain RightAnswer subsetof Integer

	dynamic controlled test: Boolean
	dynamic controlled outMessage: OutMessage
	dynamic monitored choosenShape : Shapes
	dynamic monitored getAnswer: Answers
	dynamic controlled levelTest: Level
	dynamic controlled levelCertificate: Certificate
	dynamic controlled rightAnswer: RightAnswer
	dynamic controlled answerError: Boolean
	dynamic controlled answerSkip: Boolean
	dynamic controlled loop: Boolean

	derived currentAnswer: Answers
 
definitions:
	domain Level={1..6}
	domain Certificate={1..7}
	domain RightAnswer={0..2}

    function currentAnswer =
	   switch choosenShape 
		   case AAA : AA 
		   case BBB : BB
		   case CCC : CC
		   case DDD : DD
	   endswitch

	macro rule r_goOut=
		par
			test:=false
			levelCertificate:=7
		endpar
		
	macro rule r_goUp=
		par
			levelTest:=levelTest+1
			answerError:=false
			answerSkip:=false	
			rightAnswer:=0
			levelCertificate:=levelCertificate+1
		endpar

	
	macro rule r_goUpCheckLevel=
		if levelTest<6 then
			r_goUp[]
		else
			test:=false
		endif

	macro rule r_wrong=
		par
			loop:=true
			r_goUpCheckLevel[]
		endpar
		
	macro rule r_wrongAnswer=
		if (answerError) then
			r_wrong[]
		else
			answerError:=true
		endif

	macro rule r_rightAnswNoLoop=
		par
			answerError:=false
			answerSkip:=false
			levelTest:=levelTest-1
			levelCertificate:=levelCertificate-1
		endpar

	macro rule r_certificate=
		if rightAnswer<=1 then
			rightAnswer:=rightAnswer+1
		else
			par
				levelCertificate:=levelCertificate-1
				test:=false
			endpar
		endif
		
	macro rule r_goDown=
		if loop=false then
			r_rightAnswNoLoop[]
		else
			r_certificate[]
		endif

	macro rule r_rightAnswer=
		if levelTest>1 then
			r_goDown[]
		else 
			r_certificate[]
		endif

	macro rule r_checkanswer($a in Answers)=
		if ($a = currentAnswer) then
			r_rightAnswer[]
		else
			r_wrongAnswer[]
		endif

	macro rule r_skip=
		par
			if (answerSkip) then
				r_wrong[]
			else
				answerSkip:=true
			endif
			skip
		endpar


	macro rule r_test=	
		if (getAnswer=EXIT) then
			r_goOut[]
		else
			if (getAnswer=SKIP) then
				r_skip[]
			else
				r_checkanswer[getAnswer]
			endif
		endif

	macro rule r_exit=
		if levelCertificate=7 then
			outMessage:=NOTCERTIFICATE
		else
	   		outMessage:=CERTIFICATE
		endif


	CTLSPEC af(not test)
	LTLSPEC f(not test)
	CTLSPEC af(outMessage=CERTIFICATE) or ef (outMessage=NOTCERTIFICATE)
	CTLSPEC not(ef (not test and levelTest=1)) 
	CTLSPEC ag(test)
	CTLSPEC not ef (not test and outMessage=CERTIFICATE)


	//if the test is terminated, it cannot start again
	//(for us, a test corresponds to a run of the ASM)
	LTLSPEC g(not test implies g(not test))
	
	//The decision whether or not to certify a patient can be made only when
	//the test is finished. Therefore, we verify that, during the test, the message
	//containing the certification decision is undefined.
	LTLSPEC g(test implies isUndef(outMessage))
	LTLSPEC g(isDef(outMessage) implies not test)
	
	//The message reporting the outcome of the test remains undefined since it becomes
	//defined with the outcome of the test and cannot become undefined again
	LTLSPEC u(isUndef(outMessage), g(isDef(outMessage))) // isUndef(outMessage) U g(isDef(outMessage))
	CTLSPEC a(isUndef(outMessage), ag(isDef(outMessage))) // a[isUndef(outMessage) U g(isDef(outMessage))]
	
	CTLSPEC ef(outMessage = CERTIFICATE)
	CTLSPEC ef(outMessage = NOTCERTIFICATE)
	
	//once a decision has been taken (either certified or not certified), it cannot be changed
	LTLSPEC g(outMessage = CERTIFICATE implies g(outMessage = CERTIFICATE))
	LTLSPEC g(outMessage = NOTCERTIFICATE implies g(outMessage = NOTCERTIFICATE))
	CTLSPEC ag(outMessage = CERTIFICATE implies ag(outMessage = CERTIFICATE))
	CTLSPEC ag(outMessage = NOTCERTIFICATE implies ag(outMessage = NOTCERTIFICATE))

	main rule r_Main =
		if (test) then
			r_test[]	
		else
			r_exit[]
		endif
		
default init s0:
	function test = true
	function levelTest=6
	function levelCertificate=7
	function rightAnswer=0
	function answerError=false
	function answerSkip=false
	function loop=false

