asm buffer

import ../libraries/StandardLibrary

signature:
    // Same status domain as Producer/Consumer for simplicity
    enum domain StatusDomain = {IDLE | HELLO_WORLD}

    // Monitored variable: receives status from Producer
    dynamic monitored incomingStatus: StatusDomain
    // Controlled variable: status to be forwarded to Consumer
    dynamic controlled outgoingStatus: StatusDomain

definitions:

    // Simple forwarding rule: just pass the status through
    main rule r_ForwardStatus =
        outgoingStatus := incomingStatus

default init s0:
    // Initial state for the controlled variable
    function outgoingStatus = IDLE