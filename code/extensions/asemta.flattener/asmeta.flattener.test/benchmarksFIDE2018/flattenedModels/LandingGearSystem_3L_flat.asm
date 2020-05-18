//applied flatteners: MCR FR ChR AR LR CaR NR 
asm LandingGearSystem_3L_flat
import ./STDL/StandardLibrary
import ./STDL/CTLlibrary
import ./STDL/LTLlibrary

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
        par
            if and(and(and(and(eq(handle,UP),neq(gears,RETRACTED)),eq(OPEN,doors)),eq(RETRACTING,gears)),gearsRetracted) then
                par
                    retractGearsElectroValve := false
                    gears := RETRACTED
                endpar
            endif
            if and(and(and(not(eq(handle,UP)),neq(gears,EXTENDED)),eq(OPEN,doors)),eq(RETRACTING,gears)) then
                par
                    extendGearsElectroValve := true
                    retractGearsElectroValve := false
                    gears := EXTENDING
                endpar
            endif
            if and(and(not(eq(handle,UP)),not(neq(gears,EXTENDED))),eq(OPENING,doors)) then
                par
                    closeDoorsElectroValve := true
                    openDoorsElectroValve := false
                    doors := CLOSING
                endpar
            endif
            if and(and(and(eq(handle,UP),neq(gears,RETRACTED)),eq(OPEN,doors)),eq(EXTENDING,gears)) then
                par
                    extendGearsElectroValve := false
                    retractGearsElectroValve := true
                    gears := RETRACTING
                endpar
            endif
            if and(and(and(not(eq(handle,UP)),not(neq(gears,EXTENDED))),eq(CLOSING,doors)),doorsClosed) then
                par
                    generalElectroValve := false
                    closeDoorsElectroValve := false
                    doors := CLOSED
                endpar
            endif
            if and(and(and(and(eq(handle,UP),neq(gears,RETRACTED)),eq(OPEN,doors)),eq(EXTENDED,gears)),gearsShockAbsorber) then
                par
                    retractGearsElectroValve := true
                    gears := RETRACTING
                endpar
            endif
            if and(and(and(not(eq(handle,UP)),neq(gears,EXTENDED)),eq(OPEN,doors)),eq(RETRACTED,gears)) then
                par
                    extendGearsElectroValve := true
                    gears := EXTENDING
                endpar
            endif
            if and(and(eq(handle,UP),neq(gears,RETRACTED)),eq(CLOSING,doors)) then
                par
                    closeDoorsElectroValve := false
                    openDoorsElectroValve := true
                    doors := OPENING
                endpar
            endif
            if and(and(and(eq(handle,UP),neq(gears,RETRACTED)),eq(OPENING,doors)),doorsOpen) then
                par
                    openDoorsElectroValve := false
                    doors := OPEN
                endpar
            endif
            if and(and(and(not(eq(handle,UP)),neq(gears,EXTENDED)),eq(OPENING,doors)),doorsOpen) then
                par
                    openDoorsElectroValve := false
                    doors := OPEN
                endpar
            endif
            if and(and(eq(handle,UP),not(neq(gears,RETRACTED))),eq(OPEN,doors)) then
                par
                    closeDoorsElectroValve := true
                    doors := CLOSING
                endpar
            endif
            if and(and(not(eq(handle,UP)),not(neq(gears,EXTENDED))),eq(OPEN,doors)) then
                par
                    closeDoorsElectroValve := true
                    doors := CLOSING
                endpar
            endif
            if and(and(not(eq(handle,UP)),neq(gears,EXTENDED)),eq(CLOSING,doors)) then
                par
                    closeDoorsElectroValve := false
                    openDoorsElectroValve := true
                    doors := OPENING
                endpar
            endif
            if and(and(and(and(not(eq(handle,UP)),neq(gears,EXTENDED)),eq(OPEN,doors)),eq(EXTENDING,gears)),gearsExtended) then
                par
                    extendGearsElectroValve := false
                    gears := EXTENDED
                endpar
            endif
            if and(and(eq(handle,UP),neq(gears,RETRACTED)),eq(CLOSED,doors)) then
                par
                    generalElectroValve := true
                    openDoorsElectroValve := true
                    doors := OPENING
                endpar
            endif
            if and(and(and(eq(handle,UP),not(neq(gears,RETRACTED))),eq(CLOSING,doors)),doorsClosed) then
                par
                    generalElectroValve := false
                    closeDoorsElectroValve := false
                    doors := CLOSED
                endpar
            endif
            if and(and(eq(handle,UP),not(neq(gears,RETRACTED))),eq(OPENING,doors)) then
                par
                    closeDoorsElectroValve := true
                    openDoorsElectroValve := false
                    doors := CLOSING
                endpar
            endif
            if and(and(not(eq(handle,UP)),neq(gears,EXTENDED)),eq(CLOSED,doors)) then
                par
                    generalElectroValve := true
                    openDoorsElectroValve := true
                    doors := OPENING
                endpar
            endif
        endpar

default init s0:
    function doors = CLOSED
    function gears = EXTENDED
    function generalElectroValve = false
    function extendGearsElectroValve = false
    function retractGearsElectroValve = false
    function openDoorsElectroValve = false
    function closeDoorsElectroValve = false
