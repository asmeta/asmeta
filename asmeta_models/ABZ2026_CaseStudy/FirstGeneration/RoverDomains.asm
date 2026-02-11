asm RoverDomains
import ../../STDL/StandardLibrary

signature:

// --- Basic types ---
domain Coord subsetof Integer
domain BatteryPct subsetof Real

// 2D discrete position (simple abstraction)
domain Position subsetof Prod(Coord, Coord)

// A plan is a finite sequence of positions
domain Plan subsetof Seq(Position)

// --- Utility functions (derived) ---
static distManhattan: Position * Position -> Integer
static planLength: Plan -> Integer
static headPos: Plan -> Position
static tailPlan: Plan -> Plan
static isEmptyPlan: Plan -> Boolean

definitions:

function distManhattan($p in Position, $q in Position) =
    abs(fst($p) - fst($q)) + abs(snd($p) - snd($q))

function planLength($pl in Plan) =
    length($pl)

function isEmptyPlan($pl in Plan) =
    (length($pl) = 0)

function headPos($pl in Plan) =
    first($pl)

function tailPlan($pl in Plan) =
    rest($pl)