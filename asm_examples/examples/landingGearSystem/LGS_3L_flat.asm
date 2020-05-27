//applied flatteners: LR FR ChR MCR 
asm LGS_3L_flat
import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary
import ../../STDL/LTLlibrary

signature:
    enum domain LandingSet = {FRONT | LEFT | RIGHT}
    enum domain HandleStatus = {UP | DOWN}
    enum domain DoorStatus = {CLOSED | OPENING | OPEN | CLOSING}
    enum domain GearStatus = {RETRACTED | EXTENDING | EXTENDED | RETRACTING}
    enum domain CylinderStatus = {CYLINDER_RETRACTED | CYLINDER_EXTENDING | CYLINDER_EXTENDED | CYLINDER_RETRACTING}

    monitored handle: HandleStatus
    controlled doors: DoorStatus
    controlled gears: GearStatus
    derived cylindersDoors: CylinderStatus
    derived cylindersGears: CylinderStatus
    controlled generalElectroValve: Boolean
    controlled openDoorsElectroValve: Boolean
    controlled closeDoorsElectroValve: Boolean
    controlled retractGearsElectroValve: Boolean
    controlled extendGearsElectroValve: Boolean
    monitored gearsExtended: LandingSet -> Boolean
    monitored gearsRetracted: LandingSet -> Boolean
    monitored doorsClosed: LandingSet -> Boolean
    monitored doorsOpen: LandingSet -> Boolean
    monitored gearsShockAbsorber: LandingSet -> Boolean
    derived gearsExtended: Boolean
    derived gearsRetracted: Boolean
    derived doorsClosed: Boolean
    derived doorsOpen: Boolean
    derived gearsShockAbsorber: Boolean

definitions:

    function gearsExtended = (forall $s in LandingSet with gearsExtended($s))
    function gearsRetracted = (forall $s in LandingSet with gearsRetracted($s))
    function doorsClosed = (forall $s in LandingSet with doorsClosed($s))
    function doorsOpen = (forall $s in LandingSet with doorsOpen($s))
    function gearsShockAbsorber = (forall $s in LandingSet with gearsShockAbsorber($s))
    function cylindersDoors = switch doors case OPEN:CYLINDER_EXTENDED case OPENING:CYLINDER_EXTENDING case CLOSING:CYLINDER_RETRACTING case CLOSED:CYLINDER_RETRACTED endswitch
    function cylindersGears = switch gears case RETRACTED:CYLINDER_RETRACTED case EXTENDING:CYLINDER_EXTENDING case EXTENDED:CYLINDER_EXTENDED case RETRACTING:CYLINDER_RETRACTING endswitch


    invariant over gears, doors: implies(or(eq(gears,EXTENDING),eq(gears,RETRACTING)),eq(doors,OPEN))
    invariant over gears, doors: implies(eq(doors,CLOSED),or(eq(gears,EXTENDED),eq(gears,RETRACTED)))
    LTLSPEC g(implies(g(eq(handle,DOWN)),f(and(eq(gears,EXTENDED),eq(doors,CLOSED)))))
    LTLSPEC g(implies(g(eq(handle,UP)),f(and(eq(gears,RETRACTED),eq(doors,CLOSED)))))
    LTLSPEC g(implies(g(eq(handle,DOWN)),x(g(neq(gears,RETRACTING)))))
    LTLSPEC g(implies(g(eq(handle,UP)),x(g(neq(gears,EXTENDING)))))
    LTLSPEC g(implies(or(extendGearsElectroValve,retractGearsElectroValve),eq(doors,OPEN)))
    LTLSPEC g(implies(or(openDoorsElectroValve,closeDoorsElectroValve),or(eq(gears,RETRACTED),eq(gears,EXTENDED))))
    LTLSPEC g(not(and(openDoorsElectroValve,closeDoorsElectroValve)))
    LTLSPEC g(not(and(extendGearsElectroValve,retractGearsElectroValve)))
    LTLSPEC g(implies(or(or(or(openDoorsElectroValve,closeDoorsElectroValve),extendGearsElectroValve),retractGearsElectroValve),generalElectroValve))
    CTLSPEC ag(implies(generalElectroValve,ef(not(generalElectroValve))))
    CTLSPEC ag(implies(or(extendGearsElectroValve,retractGearsElectroValve),eq(doors,OPEN)))
    CTLSPEC ag(implies(or(openDoorsElectroValve,closeDoorsElectroValve),or(eq(gears,RETRACTED),eq(gears,EXTENDED))))
    CTLSPEC ag(not(and(openDoorsElectroValve,closeDoorsElectroValve)))
    CTLSPEC ag(not(and(extendGearsElectroValve,retractGearsElectroValve)))
    CTLSPEC ag(implies(or(or(or(openDoorsElectroValve,closeDoorsElectroValve),extendGearsElectroValve),retractGearsElectroValve),generalElectroValve))
    CTLSPEC ag(ef(eq(handle,UP)))
    CTLSPEC ag(ef(eq(handle,DOWN)))
    CTLSPEC (forall $s in DoorStatus with ag(ef(eq(doors,$s))))
    CTLSPEC (forall $s in GearStatus with ag(ef(eq(gears,$s))))
    main rule r_Main =
        if eq(handle,UP) then
            if neq(gears,RETRACTED) then
                switch doors
                    case CLOSED:
                        par
                            generalElectroValve := true
                            openDoorsElectroValve := true
                            doors := OPENING
                        endpar
                    case CLOSING:
                        par
                            closeDoorsElectroValve := false
                            openDoorsElectroValve := true
                            doors := OPENING
                        endpar
                    case OPENING:
                        if doorsOpen then
                            par
                                openDoorsElectroValve := false
                                doors := OPEN
                            endpar
                        endif
                    case OPEN:
                        switch gears
                            case EXTENDED:
                                if gearsShockAbsorber then
                                    par
                                        retractGearsElectroValve := true
                                        gears := RETRACTING
                                    endpar
                                endif
                            case RETRACTING:
                                if gearsRetracted then
                                    par
                                        retractGearsElectroValve := false
                                        gears := RETRACTED
                                    endpar
                                endif
                            case EXTENDING:
                                par
                                    extendGearsElectroValve := false
                                    retractGearsElectroValve := true
                                    gears := RETRACTING
                                endpar
                        endswitch
                endswitch
            else 
                switch doors
                    case OPEN:
                        par
                            closeDoorsElectroValve := true
                            doors := CLOSING
                        endpar
                    case CLOSING:
                        if doorsClosed then
                            par
                                generalElectroValve := false
                                closeDoorsElectroValve := false
                                doors := CLOSED
                            endpar
                        endif
                    case OPENING:
                        par
                            closeDoorsElectroValve := true
                            openDoorsElectroValve := false
                            doors := CLOSING
                        endpar
                endswitch
            endif
        else 
            if neq(gears,EXTENDED) then
                switch doors
                    case CLOSED:
                        par
                            generalElectroValve := true
                            openDoorsElectroValve := true
                            doors := OPENING
                        endpar
                    case OPENING:
                        if doorsOpen then
                            par
                                openDoorsElectroValve := false
                                doors := OPEN
                            endpar
                        endif
                    case CLOSING:
                        par
                            closeDoorsElectroValve := false
                            openDoorsElectroValve := true
                            doors := OPENING
                        endpar
                    case OPEN:
                        switch gears
                            case RETRACTED:
                                par
                                    extendGearsElectroValve := true
                                    gears := EXTENDING
                                endpar
                            case EXTENDING:
                                if gearsExtended then
                                    par
                                        extendGearsElectroValve := false
                                        gears := EXTENDED
                                    endpar
                                endif
                            case RETRACTING:
                                par
                                    extendGearsElectroValve := true
                                    retractGearsElectroValve := false
                                    gears := EXTENDING
                                endpar
                        endswitch
                endswitch
            else 
                switch doors
                    case OPEN:
                        par
                            closeDoorsElectroValve := true
                            doors := CLOSING
                        endpar
                    case CLOSING:
                        if doorsClosed then
                            par
                                generalElectroValve := false
                                closeDoorsElectroValve := false
                                doors := CLOSED
                            endpar
                        endif
                    case OPENING:
                        par
                            closeDoorsElectroValve := true
                            openDoorsElectroValve := false
                            doors := CLOSING
                        endpar
                endswitch
            endif
        endif

default init s0:
    function doors = CLOSED
    function gears = EXTENDED
    function generalElectroValve = false
    function extendGearsElectroValve = false
    function retractGearsElectroValve = false
    function openDoorsElectroValve = false
    function closeDoorsElectroValve = false
