asm RoverDomains
import ../../STDL/StandardLibrary
export *

signature:

// --- Basic types ---
domain Coord subsetof Integer
domain BatteryPct subsetof Integer

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

// Initialize concrete domains
domain Coord = {-10000:10000}
domain BatteryPct = {0:100}

function distManhattan($pp in Prod(Position, Position)) =
    abs(first(first($pp)) - first(second($pp))) + abs(second(first($pp)) - second(second($pp)))

function planLength($pl in Plan) =
    length($pl)

function isEmptyPlan($pl in Plan) =
    (length($pl) = 0)

function headPos($pl in Plan) =
    first($pl)

function tailPlan($pl in Plan) =
    rest($pl)