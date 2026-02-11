asm RoverDomains
import ../../STDL/StandardLibrary

export *

signature:

// --- Basic types ---
domain Coord subsetof Integer

// 2D discrete position (simple abstraction)
domain Position subsetof Prod(Coord, Coord)

// A plan is a finite sequence of positions
domain Plan subsetof Seq(Position)

// --- Utility functions (derived) ---
static distManhattan: Prod(Position, Position) -> Integer
static planLength: Plan -> Integer
static headPos: Plan -> Position
static tailPlan: Plan -> Plan
static isEmptyPlan: Plan -> Boolean

definitions:

domain Coord = {0 : 10}

function distManhattan($p in Position, $q in Position) =
    abs(first($p) - first($q)) + abs(second($p) - second($q))

function planLength($pl in Plan) =
    length($pl)

function isEmptyPlan($pl in Plan) =
    (length($pl) = 0)

function headPos($pl in Plan) =
    first($pl)

function tailPlan($pl in Plan) =
    tail($pl)