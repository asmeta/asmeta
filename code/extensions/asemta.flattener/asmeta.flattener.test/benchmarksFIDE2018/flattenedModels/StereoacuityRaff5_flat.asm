//applied flatteners: MCR FR ChR AR LR CaR NR 
asm StereoacuityRaff5_flat
import ./STDL/StandardLibrary
import ./STDL/CTLlibrary
import ./STDL/LTLlibrary

signature:
    enum domain Answers = {SKIP | EXIT | AA | BB | CC | DD}
    enum domain Shapes = {AAA | BBB | CCC | DDD}
    enum domain OutMessage = {CERTIFICATE | NOTCERTIFICATE}
    domain Level subsetof Integer
    domain Certificate subsetof Integer
    domain RightAnswer subsetof Integer

    controlled test: Boolean
    controlled outMessage: OutMessage
    monitored choosenShape: Shapes
    monitored getAnswer: Answers
    controlled levelTest: Level
    controlled levelCertificate: Certificate
    controlled rightAnswer: RightAnswer
    controlled answerError: Boolean
    controlled answerSkip: Boolean
    controlled loop: Boolean
    derived currentAnswer: Answers

definitions:

    domain Level = {1,2,3,4,5,6}
    domain Certificate = {1,2,3,4,5,6,7}
    domain RightAnswer = {0,1,2}

    function currentAnswer = switch choosenShape case AAA:AA case BBB:BB case CCC:CC case DDD:DD endswitch


    LTLSPEC f(not(test))
    LTLSPEC g(implies(not(test),g(not(test))))
    LTLSPEC g(implies(test,isUndef(outMessage)))
    LTLSPEC g(implies(isDef(outMessage),not(test)))
    LTLSPEC u(isUndef(outMessage),g(isDef(outMessage)))
    LTLSPEC g(implies(eq(outMessage,CERTIFICATE),g(eq(outMessage,CERTIFICATE))))
    LTLSPEC g(implies(eq(outMessage,NOTCERTIFICATE),g(eq(outMessage,NOTCERTIFICATE))))
    CTLSPEC af(not(test))
    CTLSPEC or(af(eq(outMessage,CERTIFICATE)),ef(eq(outMessage,NOTCERTIFICATE)))
    CTLSPEC not(ef(and(not(test),eq(levelTest,1))))
    CTLSPEC ag(test)
    CTLSPEC not(ef(and(not(test),eq(outMessage,CERTIFICATE))))
    CTLSPEC a(isUndef(outMessage),ag(isDef(outMessage)))
    CTLSPEC ef(eq(outMessage,CERTIFICATE))
    CTLSPEC ef(eq(outMessage,NOTCERTIFICATE))
    CTLSPEC ag(implies(eq(outMessage,CERTIFICATE),ag(eq(outMessage,CERTIFICATE))))
    CTLSPEC ag(implies(eq(outMessage,NOTCERTIFICATE),ag(eq(outMessage,NOTCERTIFICATE))))
    main rule r_Main =
        par
            if and(and(and(and(test,not(eq(getAnswer,EXIT))),eq(getAnswer,SKIP)),answerSkip),lt(levelTest,6)) then
                par
                    levelTest := plus(levelTest,1)
                    answerError := false
                    answerSkip := false
                    rightAnswer := 0
                    levelCertificate := plus(levelCertificate,1)
                endpar
            endif
            if and(and(and(and(test,not(eq(getAnswer,EXIT))),not(eq(getAnswer,SKIP))),not(eq(getAnswer,currentAnswer))),not(answerError)) then
                answerError := true
            endif
            if and(not(test),not(eq(levelCertificate,7))) then
                outMessage := CERTIFICATE
            endif
            if and(not(test),eq(levelCertificate,7)) then
                outMessage := NOTCERTIFICATE
            endif
            if and(and(and(and(and(test,not(eq(getAnswer,EXIT))),not(eq(getAnswer,SKIP))),eq(getAnswer,currentAnswer)),not(gt(levelTest,1))),le(rightAnswer,1)) then
                rightAnswer := plus(rightAnswer,1)
            endif
            if and(and(and(and(and(test,not(eq(getAnswer,EXIT))),not(eq(getAnswer,SKIP))),eq(getAnswer,currentAnswer)),not(gt(levelTest,1))),not(le(rightAnswer,1))) then
                par
                    levelCertificate := minus(levelCertificate,1)
                    test := false
                endpar
            endif
            if and(test,eq(getAnswer,EXIT)) then
                par
                    test := false
                    levelCertificate := 7
                endpar
            endif
            if and(and(and(and(and(test,not(eq(getAnswer,EXIT))),not(eq(getAnswer,SKIP))),eq(getAnswer,currentAnswer)),gt(levelTest,1)),eq(loop,false)) then
                par
                    answerError := false
                    answerSkip := false
                    levelTest := minus(levelTest,1)
                    levelCertificate := minus(levelCertificate,1)
                endpar
            endif
            if and(and(and(test,not(eq(getAnswer,EXIT))),eq(getAnswer,SKIP)),not(answerSkip)) then
                answerSkip := true
            endif
            if and(and(and(test,not(eq(getAnswer,EXIT))),eq(getAnswer,SKIP)),answerSkip) then
                loop := true
            endif
            if and(and(and(and(test,not(eq(getAnswer,EXIT))),eq(getAnswer,SKIP)),answerSkip),not(lt(levelTest,6))) then
                test := false
            endif
            if and(and(and(and(and(test,not(eq(getAnswer,EXIT))),not(eq(getAnswer,SKIP))),not(eq(getAnswer,currentAnswer))),answerError),not(lt(levelTest,6))) then
                test := false
            endif
            if and(and(and(and(and(and(test,not(eq(getAnswer,EXIT))),not(eq(getAnswer,SKIP))),eq(getAnswer,currentAnswer)),gt(levelTest,1)),not(eq(loop,false))),le(rightAnswer,1)) then
                rightAnswer := plus(rightAnswer,1)
            endif
            if and(and(and(and(test,not(eq(getAnswer,EXIT))),not(eq(getAnswer,SKIP))),not(eq(getAnswer,currentAnswer))),answerError) then
                loop := true
            endif
            if and(and(and(and(and(and(test,not(eq(getAnswer,EXIT))),not(eq(getAnswer,SKIP))),eq(getAnswer,currentAnswer)),gt(levelTest,1)),not(eq(loop,false))),not(le(rightAnswer,1))) then
                par
                    levelCertificate := minus(levelCertificate,1)
                    test := false
                endpar
            endif
            if and(and(and(and(and(test,not(eq(getAnswer,EXIT))),not(eq(getAnswer,SKIP))),not(eq(getAnswer,currentAnswer))),answerError),lt(levelTest,6)) then
                par
                    levelTest := plus(levelTest,1)
                    answerError := false
                    answerSkip := false
                    rightAnswer := 0
                    levelCertificate := plus(levelCertificate,1)
                endpar
            endif
        endpar

default init s0:
    function test = true
    function levelTest = 6
    function levelCertificate = 7
    function rightAnswer = 0
    function answerError = false
    function answerSkip = false
    function loop = false
