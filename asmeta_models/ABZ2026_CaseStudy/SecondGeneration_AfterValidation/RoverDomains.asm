asm RoverDomains
import ../../STDL/StandardLibrary
export *

signature:

// --- Basic types ---
domain Coord subsetof Integer
domain BatteryPct subsetof Integer

// --- Utility functions (derived) ---
static distManhattan: Prod(Prod(Coord, Coord), Prod(Coord, Coord)) -> Integer
static planLength: Seq(Prod(Coord, Coord)) -> Integer
static headPos: Seq(Prod(Coord, Coord)) -> Prod(Coord, Coord)
static tailPlan: Seq(Prod(Coord, Coord)) -> Seq(Prod(Coord, Coord))
static isEmptyPlan: Seq(Prod(Coord, Coord)) -> Boolean

definitions:

// Initialize concrete domains
domain Coord = {-10:10}
domain BatteryPct = {0:100}

function distManhattan($p in Prod(Coord, Coord), $q in Prod(Coord, Coord)) =
    abs(first($p) - first($q)) + abs(second($p) - second($q))

function planLength($pl in Seq(Prod(Coord,Coord))) =
    length($pl)

function isEmptyPlan($pl in Seq(Prod(Coord,Coord))) =
    (length($pl) = 0)

function headPos($pl in Seq(Prod(Coord,Coord))) =
    first($pl)

function tailPlan($pl in Seq(Prod(Coord,Coord))) =
    tail($pl)
