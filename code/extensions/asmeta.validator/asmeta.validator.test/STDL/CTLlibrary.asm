asm CTLlibrary
import ..\..\..\..\..\asm_examples\STDL\StandardLibrary
export *

signature:

    static eg: Boolean -> Boolean
    static ex: Boolean -> Boolean
    static ef: Boolean -> Boolean
    static ag: Boolean -> Boolean
    static ax: Boolean -> Boolean
    static af: Boolean -> Boolean
    static e: Prod(Boolean, Boolean) -> Boolean
    static a: Prod(Boolean, Boolean) -> Boolean
    static ew: Prod(Boolean, Boolean) -> Boolean
    static aw: Prod(Boolean, Boolean) -> Boolean
    static absenceG: Boolean -> Boolean
    static absenceBefore: Prod(Boolean, Boolean) -> Boolean
    static absenceAfter: Prod(Boolean, Boolean) -> Boolean
    static absenceBetween: Prod(Boolean, Boolean, Boolean) -> Boolean
    static absenceAfterUntil: Prod(Boolean, Boolean, Boolean) -> Boolean
    static ap: Prod(Boolean, Boolean) -> Boolean
    static pb: Prod(Boolean, Boolean, Boolean) -> Boolean
    static exN: Prod(Boolean, Natural) -> Boolean
    static axN: Prod(Boolean, Natural) -> Boolean

definitions:

// added by validator
default init s0__:
