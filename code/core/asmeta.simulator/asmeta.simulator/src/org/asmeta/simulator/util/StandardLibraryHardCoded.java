package org.asmeta.simulator.util;

public class StandardLibraryHardCoded {
	public final static String StandardLibrary = "module StandardLibrary\n" + 
			"\n" + 
			"export *\n" + 
			"\n" + 
			"signature :\n" + 
			"	/*----------- Domains --------------*/\n" + 
			"	anydomain Any\n" + 
			"	anydomain D\n" + 
			"	anydomain D1\n" + 
			"	anydomain D2\n" + 
			"	anydomain D3\n" + 
			"	anydomain D4\n" + 
			"	anydomain D5\n" + 
			"	anydomain D6\n" + 
			"	anydomain D7\n" + 
			"	anydomain D8\n" + 
			"	anydomain D9\n" + 
			"\n" + 
			"	basic domain Complex\n" + 
			"	basic domain Real\n" + 
			"	basic domain Integer\n" + 
			"	basic domain Natural\n" + 
			"	basic domain Char\n" + 
			"	basic domain String\n" + 
			"	basic domain Boolean\n" + 
			"	basic domain Undef\n" + 
			"	\n" + 
			"	abstract domain Agent\n" + 
			"	abstract domain Reserve\n" + 
			"\n" + 
			"	// ************** very nasty functions\n" + 
			"	// used to print something\n" + 
			"	static print: D -> Integer\n" + 
			"	static print: Prod(D, D1) -> Integer\n" + 
			"	static print: Prod(D, D1, D2) -> Integer\n" + 
			"	static print: Prod(D, D1, D2, D3) -> Integer\n" + 
			"	static print: Prod(D, D1, D2, D3, D4) -> Integer\n" + 
			"	static print: Prod(D, D1, D2, D3, D4, D5) -> Integer\n" + 
			"	static print: Prod(D, D1, D2, D3, D4, D5, D6) -> Integer\n" + 
			"	static print: Prod(D, D1, D2, D3, D4, D5, D6, D7) -> Integer\n" + 
			"	static print: Prod(D, D1, D2, D3, D4, D5, D6, D7, D8) -> Integer\n" + 
			"	static print: Prod(D, D1, D2, D3, D4, D5, D6, D7, D8, D9) -> Integer\n" + 
			"	// **************\n" + 
			"\n" + 
			"/*-----------Reserved 0-ary function--------------------*/\n" + 
			"	controlled result: Any\n" + 
			"\n" + 
			"/*----------- ASM Functions on all Domains -------------*/\n" + 
			"	static eq: Prod(D,D) -> Boolean\n" + 
			"	static neq: Prod(D,D) -> Boolean\n" + 
			"	static isDef: D -> Boolean\n" + 
			"	static isUndef: D -> Boolean\n" + 
			"\n" + 
			"/*----------- Relational operators with mixed arithmetic domains -----*/\n" + 
			"	static eq: Prod(Natural, Integer) -> Boolean\n" + 
			"	static eq: Prod(Integer, Natural) -> Boolean\n" + 
			"	static eq: Prod(Real, Natural) -> Boolean\n" + 
			"	static eq: Prod(Natural, Real) -> Boolean\n" + 
			"	static eq: Prod(Real, Integer) -> Boolean\n" + 
			"	static eq: Prod(Integer, Real) -> Boolean\n" + 
			"\n" + 
			"/*----------- Basic Functions on Complex--------------*/	\n" + 
			"	static re: Complex -> Real\n" + 
			"	static im: Complex -> Real\n" + 
			"	static plus: Prod(Complex, Complex) -> Complex\n" + 
			"	static minus: Prod (Complex, Complex) -> Complex\n" + 
			"	static mult: Prod (Complex, Complex) -> Complex\n" + 
			"	static div: Prod (Complex, Complex) -> Complex\n" + 
			"	static minus: Complex -> Complex\n" + 
			"	static norm: Complex -> Complex\n" + 
			"	static iszero: Complex -> Boolean\n" + 
			"	static toString: Complex -> String\n" + 
			"\n" + 
			"/*----------- Basic Functions on Real--------------*/\n" + 
			"	static plus: Real -> Real\n" + 
			"	static plus: Prod(Real, Real) -> Real\n" + 
			"	static minus: Real -> Real\n" + 
			"	static minus: Prod(Real, Real) -> Real\n" + 
			"	static mult: Prod (Real, Real) -> Real\n" + 
			"	static div: Prod (Real, Real) -> Real\n" + 
			"	static abs: Real -> Real\n" + 
			"	static floor: Real -> Integer\n" + 
			"	static round: Real -> Integer\n" + 
			"	static sqrt: Real -> Real\n" + 
			"	static pwr: Prod (Real, Real) -> Real\n" + 
			"	static max: Prod (Real, Real) -> Real\n" + 
			"	static min: Prod (Real, Real) -> Real\n" + 
			"	static toString: Real -> String\n" + 
			"	static lt: Prod (Real, Real) -> Boolean\n" + 
			"	static gt: Prod (Real, Real) -> Boolean\n" + 
			"	static le: Prod (Real, Real) -> Boolean\n" + 
			"	static ge: Prod (Real, Real) -> Boolean\n" + 
			"	//CONVERSION\n" + 
			"	static rtoi: Real -> Integer\n" + 
			"	static rton: Real -> Natural\n" + 
			"\n" + 
			"	// returns the range between two numbers\n" + 
			"	static range: Prod(Natural, Natural) -> Powerset(Natural)\n" + 
			"\n" + 
			"/*----------- Basic Functions on Integer --------------*/\n" + 
			"	static plus: Integer -> Integer\n" + 
			"	static plus: Prod(Integer, Integer) -> Integer\n" + 
			"	static minus: Integer -> Integer\n" + 
			"	static minus: Prod(Integer, Integer) -> Integer\n" + 
			"	static mult: Prod(Integer, Integer) -> Integer\n" + 
			"	static div: Prod(Integer, Integer) -> Real\n" + 
			"	static abs: Integer -> Integer\n" + 
			"	static idiv: Prod(Integer, Integer) -> Integer\n" + 
			"	static mod: Prod(Integer, Integer) -> Integer\n" + 
			"	static mod: Prod(Integer, Natural) -> Integer\n" + 
			"	static mod: Prod(Natural, Integer) -> Integer\n" + 
			"	static max: Prod(Integer, Integer) -> Integer\n" + 
			"	static min: Prod(Integer, Integer) -> Integer\n" + 
			"	static toString: Integer -> String\n" + 
			"	static lt: Prod(Integer, Integer) -> Boolean\n" + 
			"	static gt: Prod(Integer, Integer) -> Boolean\n" + 
			"	static le: Prod(Integer, Integer) -> Boolean\n" + 
			"	static ge: Prod(Integer, Integer) -> Boolean\n" + 
			"	//CONVERSION\n" + 
			"	static itor: Integer -> Real\n" + 
			"	static iton: Integer -> Natural\n" + 
			"\n" + 
			"/*----------- Basic Functions on Natural --------------*/\n" + 
			"	static plus: Prod(Natural, Natural) -> Natural\n" + 
			"	static minus: Prod(Natural, Natural) -> Integer\n" + 
			"	static mult: Prod(Natural, Natural) -> Natural\n" + 
			"	static div: Prod(Natural, Natural) -> Real\n" + 
			"	static idiv: Prod(Natural, Natural) -> Natural\n" + 
			"	static mod: Prod(Natural, Natural) -> Natural\n" + 
			"	static max: Prod(Natural, Natural) -> Natural\n" + 
			"	static min: Prod(Natural, Natural) -> Natural\n" + 
			"	static toString: Natural -> String\n" + 
			"	static lt: Prod(Natural, Natural) -> Boolean\n" + 
			"	static gt: Prod(Natural, Natural) -> Boolean\n" + 
			"	static le: Prod(Natural, Natural) -> Boolean\n" + 
			"	static ge: Prod(Natural, Natural) -> Boolean\n" + 
			"	//CONVERSION\n" + 
			"	static ntoi: Natural -> Integer\n" + 
			"	static ntor: Natural -> Real\n" + 
			"\n" + 
			"/*----------- Basic Functions on Char --------------*/\n" + 
			"	static toString: Char -> String\n" + 
			"	static lt: Prod(Char, Char) -> Boolean\n" + 
			"	static gt: Prod(Char, Char) -> Boolean\n" + 
			"	static le: Prod(Char, Char) -> Boolean\n" + 
			"	static ge: Prod(Char, Char) -> Boolean\n" + 
			"\n" + 
			"/*----------- Basic Functions on String --------------*/\n" + 
			"	static size: String -> Integer\n" + 
			"	static plus: Prod(String, String) -> String\n" + 
			"	static concat: Prod(String, String) -> String\n" + 
			"	static toUpper: String -> String\n" + 
			"	static toLower: String -> String\n" + 
			"	static subString: Prod(String, Integer, Integer) -> String\n" + 
			"	static contains: Prod(String, String) -> Boolean //Does the first argument contain the second argument?\n" + 
			"	static toInteger: String -> Integer\n" + 
			"	static toNatural: String -> Natural\n" + 
			"	static toReal: String -> Real\n" + 
			"	static toComplex: String -> Complex\n" + 
			"	static toChar: String -> Char\n" + 
			"	static split: Prod(String, String) -> Seq(String)\n" + 
			"\n" + 
			"/*----------- Basic Functions on Boolean --------------*/\n" + 
			"	static or: Prod(Boolean, Boolean) -> Boolean\n" + 
			"	static xor: Prod(Boolean, Boolean) -> Boolean\n" + 
			"	static and: Prod(Boolean, Boolean) -> Boolean\n" + 
			"	static not: Boolean -> Boolean\n" + 
			"	static implies: Prod(Boolean, Boolean) -> Boolean\n" + 
			"	static iff: Prod(Boolean, Boolean) -> Boolean\n" + 
			"\n" + 
			"/*----------- Basic Functions on Agent--------------*/	\n" + 
			"	static id: Agent -> String\n" + 
			"	static getAgent: String -> Agent\n" + 
			"	static program: Agent -> Rule\n" + 
			"	controlled self: Agent\n" + 
			"	static eq: Prod(Agent,Agent) -> Boolean\n" + 
			"	static neq: Prod(Agent,Agent) -> Boolean\n" + 
			"\n" + 
			"/*----------- Basic Functions on Sets--------------*/\n" + 
			"	static size: Powerset(D)-> Integer\n" + 
			"	static contains: Prod(Powerset(D), D) -> Boolean\n" + 
			"	static notin: Prod(Powerset(D), D)-> Boolean\n" + 
			"	static allin: Prod(Powerset(D), Powerset(D)) -> Boolean\n" + 
			"	static notallin: Prod(Powerset(D), Powerset(D)) -> Boolean\n" + 
			"	static isEmpty: Powerset(D) -> Boolean\n" + 
			"	static notEmpty: Powerset(D) -> Boolean\n" + 
			"	static sum: Powerset(D) -> D\n" + 
			"	static union: Prod(Powerset(D), Powerset(D)) -> Powerset(D)\n" + 
			"	static union: Prod(Powerset(D), Bag(D)) -> Bag(D)\n" + 
			"	// ANGELO 2018/7/31 perch� equality e non eq??? - parse rejects \"a = {5}\"\n" + 
			"	static equality: Prod(Powerset(D), Powerset(D)) -> Boolean\n" + 
			"	static intersection: Prod(Powerset(D), Powerset(D)) -> Powerset(D)\n" + 
			"	static intersection: Prod(Powerset(D), Bag(D)) -> Powerset(D)\n" + 
			"	static difference: Prod(Powerset(D), Powerset(D)) -> Powerset(D)\n" + 
			"	static including: Prod(Powerset(D), D) -> Powerset(D)\n" + 
			"	static excluding: Prod(Powerset(D), D) -> Powerset(D)\n" + 
			"	static symmetricDifference: Prod(Powerset(D), Powerset(D)) -> Powerset(D)\n" + 
			"	//static count: Prod(Powerset(D),D) -> Natural // PA 18/12/2010 Maybe not needed on sets. An element either belong or not belong to a set. contains should be enough. \n" + 
			"	static asSequence: Powerset(D) -> Seq(D)\n" + 
			"	static asBag: Powerset(D) -> Bag(D)\n" + 
			"\n" + 
			"	//Return one element of the given set, value undefined if set is empty\n" + 
			"	static chooseone: Powerset(D) -> D\n" + 
			"\n" + 
			"/*----------- Basic Functions on Sequences--------------*/\n" + 
			"	static count: Prod(Seq(D),D) -> Natural\n" + 
			"	static length: Seq(D) -> Integer\n" + 
			"	static isEmpty: Seq(D) -> Boolean\n" + 
			"	static contains: Prod(Seq(D), D)-> Boolean\n" + 
			"	static union: Prod(Seq(D), Seq(D)) -> Seq(D)\n" + 
			"	// ANGELO 2018/7/31 equality is missing\n" + 
			"	static append: Prod(Seq(D), D) -> Seq(D)\n" + 
			"	static prepend: Prod(D, Seq(D)) -> Seq(D)\n" + 
			"	static insertAt: Prod(Seq(D), Natural, D) -> Seq(D)\n" + 
			"	static subSequence: Prod(Seq(D), Natural, Natural) -> Seq(D)\n" + 
			"	static at: Prod(Seq(D),Natural) -> D\n" + 
			"	static indexOf: Prod(Seq(D),D) -> Integer\n" + 
			"	static first: Seq(D) -> D\n" + 
			"	static last: Seq(D) -> D\n" + 
			"	static asBag: Seq(D) -> Bag(D)\n" + 
			"	static asSet: Seq(D) -> Powerset(D)\n" + 
			"	static excluding: Prod(Seq(D),D) -> Seq(D)\n" + 
			"	static tail: Seq(D) -> Seq(D)\n" + 
			"	static replaceAt: Prod(Seq(D),Natural,D) -> Seq(D)//Da decidere se tenerla. Suggerita da Luca Baggio.\n" + 
			"\n" + 
			"/*----------- Basic Functions on Bags--------------*/\n" + 
			"	static union: Prod(Bag(D),Bag(D))->Bag(D)\n" + 
			"	static union: Prod(Bag(D),Powerset(D))->Bag(D)\n" + 
			"	static contains: Prod(Bag(D), D)-> Boolean\n" + 
			"	static intersection: Prod(Bag(D),Bag(D))->Bag(D)\n" + 
			"	static intersection: Prod(Bag(D),Powerset(D))->Bag(D)\n" + 
			"	static including: Prod(Bag(D),D) -> Bag(D)\n" + 
			"	static excluding: Prod(Bag(D),D) -> Bag(D)\n" + 
			"	static count: Prod(Bag(D),D) -> Natural\n" + 
			"	static asSequence: Bag(D) -> Seq(D)\n" + 
			"	static asSet: Bag(D) -> Powerset(D)\n" + 
			"	static sum: Bag(D) -> D\n" + 
			"\n" + 
			"/*----------- Basic Functions on Maps--------------*/\n" + 
			"	static merge: Prod(Map(D1,D2),Map(D1,D2)) -> Map(D1,D2)\n" + 
			"	static assign: Prod(Map(D1,D2),D1,D2) -> Map(D1,D2)\n" + 
			"	static at: Prod(Map(D1,D2),D1) -> D2\n" + 
			"\n" + 
			"/*----------- Basic Functions on Products--------------*/\n" + 
			"	static at: Prod(Prod(D1,D2),Natural) -> Any\n" + 
			"	static at: Prod(Prod(D1,D2,D3),Natural) -> Any\n" + 
			"	static at: Prod(Prod(D1,D2,D3,D4),Natural) -> Any\n" + 
			"\n" + 
			"	static indexOf: Prod(Prod(D1,D2),Any) -> Integer\n" + 
			"	static indexOf: Prod(Prod(D1,D2,D3),Any) -> Integer\n" + 
			"	static indexOf: Prod(Prod(D1,D2,D3,D4),Any) -> Integer\n" + 
			"\n" + 
			"	static first: Prod(D1,D2) -> D1\n" + 
			"	static first: Prod(D1,D2,D3) -> D1\n" + 
			"	static first: Prod(D1,D2,D3,D4) -> D1\n" + 
			"	static first: Prod(D1,D2,D3,D4,D5) -> D1\n" + 
			"	static first: Prod(D1,D2,D3,D4,D5,D6) -> D1\n" + 
			"	static first: Prod(D1,D2,D3,D4,D5,D6,D7) -> D1\n" + 
			"	static first: Prod(D1,D2,D3,D4,D5,D6,D7,D8) -> D1\n" + 
			"	static first: Prod(D1,D2,D3,D4,D5,D6,D7,D8,D9) -> D1\n" + 
			"\n" + 
			"	static second: Prod(D1,D2) -> D2\n" + 
			"	static second: Prod(D1,D2,D3) -> D2\n" + 
			"	static second: Prod(D1,D2,D3,D4) -> D2\n" + 
			"	static second: Prod(D1,D2,D3,D4,D5) -> D2\n" + 
			"	static second: Prod(D1,D2,D3,D4,D5,D6) -> D2\n" + 
			"	static second: Prod(D1,D2,D3,D4,D5,D6,D7) -> D2\n" + 
			"	static second: Prod(D1,D2,D3,D4,D5,D6,D7,D8) -> D2\n" + 
			"	static second: Prod(D1,D2,D3,D4,D5,D6,D7,D8,D9) -> D2\n" + 
			"\n" + 
			"	static third: Prod(D1,D2,D3) -> D3\n" + 
			"	static third: Prod(D1,D2,D3,D4) -> D3\n" + 
			"	static third: Prod(D1,D2,D3,D4,D5) -> D3\n" + 
			"	static third: Prod(D1,D2,D3,D4,D5,D6) -> D3\n" + 
			"	static third: Prod(D1,D2,D3,D4,D5,D6,D7) -> D3\n" + 
			"	static third: Prod(D1,D2,D3,D4,D5,D6,D7,D8) -> D3\n" + 
			"	static third: Prod(D1,D2,D3,D4,D5,D6,D7,D8,D9) -> D3\n" + 
			"\n" + 
			"	static fourth: Prod(D1,D2,D3,D4) -> D4\n" + 
			"	static fourth: Prod(D1,D2,D3,D4,D5) -> D4\n" + 
			"	static fourth: Prod(D1,D2,D3,D4,D5,D6) -> D4\n" + 
			"	static fourth: Prod(D1,D2,D3,D4,D5,D6,D7) -> D4\n" + 
			"	static fourth: Prod(D1,D2,D3,D4,D5,D6,D7,D8) -> D4\n" + 
			"	static fourth: Prod(D1,D2,D3,D4,D5,D6,D7,D8,D9) -> D4\n" + 
			"\n" + 
			"	static fifth: Prod(D1,D2,D3,D4,D5) -> D5\n" + 
			"	static fifth: Prod(D1,D2,D3,D4,D5,D6) -> D5\n" + 
			"	static fifth: Prod(D1,D2,D3,D4,D5,D6,D7) -> D5\n" + 
			"	static fifth: Prod(D1,D2,D3,D4,D5,D6,D7,D8) -> D5\n" + 
			"	static fifth: Prod(D1,D2,D3,D4,D5,D6,D7,D8,D9) -> D5\n" + 
			"\n" + 
			"	static sixth: Prod(D1,D2,D3,D4,D5,D6) -> D6\n" + 
			"	static sixth: Prod(D1,D2,D3,D4,D5,D6,D7) -> D6\n" + 
			"	static sixth: Prod(D1,D2,D3,D4,D5,D6,D7,D8) -> D6\n" + 
			"	static sixth: Prod(D1,D2,D3,D4,D5,D6,D7,D8,D9) -> D6\n" + 
			"\n" + 
			"	static seventh: Prod(D1,D2,D3,D4,D5,D6,D7) -> D7\n" + 
			"	static seventh: Prod(D1,D2,D3,D4,D5,D6,D7,D8) -> D7\n" + 
			"	static seventh: Prod(D1,D2,D3,D4,D5,D6,D7,D8,D9) -> D7\n" + 
			"\n" + 
			"	static eighth: Prod(D1,D2,D3,D4,D5,D6,D7,D8) -> D8\n" + 
			"	static eighth: Prod(D1,D2,D3,D4,D5,D6,D7,D8,D9) -> D8\n" + 
			"\n" + 
			"	static ninth: Prod(D1,D2,D3,D4,D5,D6,D7,D8,D9) -> D9\n" + 
			"\n" + 
			"	static toString: D -> String\n" + 
			"\n" + 
			"\n" + 
			"	static pre: D -> D\n" + 
			"\n" + 
			"/*----------- Java time--------------*/\n" + 
			"	static currTimeNanosecs: Integer	\n" + 
			"	static currTimeMillisecs: Integer\n" + 
			"	static currTimeSecs: Integer\n" + 
			"\n" + 
			"definitions:\n" + 
			"";

	public final static String CTLlibrary = "module CTLlibrary\n" + 
			"\n" + 
			"export *\n" + 
			"\n" + 
			"signature:\n" + 
			"	/*-----------CTL formulas------------*/\n" + 
			"	static eg: Boolean -> Boolean	//exists globally\n" + 
			"	static ex: Boolean -> Boolean	//exists next state\n" + 
			"	static ef: Boolean -> Boolean	//exists finally\n" + 
			"	static ag: Boolean -> Boolean	//forall globally\n" + 
			"	static ax: Boolean -> Boolean	//forall next state\n" + 
			"	static af: Boolean -> Boolean	//forall finally\n" + 
			"	static e: Prod(Boolean, Boolean) -> Boolean	//exists until\n" + 
			"	static a: Prod(Boolean, Boolean) -> Boolean //forall until\n" + 
			"\n" + 
			"	/*-----------Patterns------------*/\n" + 
			"	static ew: Prod(Boolean, Boolean) -> Boolean  //exists weak until -- E[p U q] | EGp.\n" + 
			"	static aw: Prod(Boolean, Boolean) -> Boolean //forall weak until -- !E[!q U !(p | q)].\n" + 
			"\n" + 
			"\n" + 
			"	//http://patterns.projects.cis.ksu.edu/documentation/patterns/ctl.shtml\n" + 
			"	//Absence (P is false)\n" + 
			"	//Globally - AG(!P)\n" + 
			"	static absenceG: Boolean -> Boolean\n" + 
			"	//(*) Before R - A[(!P | AG(!R)) W R]\n" + 
			"	static absenceBefore: Prod(Boolean, Boolean) -> Boolean // absenceBefore(P, R)\n" + 
			"	//After Q - AG(Q -> AG(!P))\n" + 
			"	static absenceAfter: Prod(Boolean, Boolean) -> Boolean // absenceAfter(P, Q)\n" + 
			"	//(*) Between Q and R - AG(Q & !R -> A[(!P | AG(!R)) W R])\n" + 
			"	static absenceBetween: Prod(Boolean, Boolean, Boolean) -> Boolean // absenceBetween(P, Q, R)\n" + 
			"	//(*) After Q until R - AG(Q & !R -> A[!P W R])\n" + 
			"	static absenceAfterUntil: Prod(Boolean, Boolean, Boolean) -> Boolean // absenceAfterUntil(P, Q, R)\n" + 
			"\n" + 
			"	//Precedence (S precedes P)\n" + 
			"	//(*) Globally 	\n" + 
			"	//A[!P W S]\n" + 
			"	static ap: Prod(Boolean, Boolean) -> Boolean // ap(P, S)\n" + 
			"	//(*) Before R 	\n" + 
			"	//A[(!P | AG(!R)) W (S | R)]\n" + 
			"	static pb: Prod(Boolean, Boolean, Boolean) -> Boolean // pb(P, S, R)\n" + 
			"	//(*) After Q 	\n" + 
			"	//A[!Q W (Q & A[!P W S])]\n" + 
			"	//(*) Between Q and R 	\n" + 
			"	//AG(Q & !R -> A[(!P | AG(!R)) W (S | R)])\n" + 
			"	//(*) After Q until R 	\n" + 
			"	//AG(Q & !R -> A[!P W (S | R)])\n" + 
			"\n" + 
			"	//Response (S responds to P)\n" + 
			"	//Globally\n" + 
			"	//AG(P -> AF(S))\n" + 
			"	//(*) Before R	\n" + 
			"	//A[((P -> A[!R U (S & !R)]) | AG(!R)) W R]\n" + 
			"	//(*) After Q	\n" + 
			"	//A[!Q W (Q & AG(P -> AF(S))] \n" + 
			"	//(*) Between Q and R	\n" + 
			"	//AG(Q & !R -> A[((P -> A[!R U (S & !R)]) | AG(!R)) W R])\n" + 
			"	//(*) After Q until R	\n" + 
			"	//AG(Q & !R -> A[(P -> A[!R U (S & !R)]) W R])\n" + 
			"\n" + 
			"	/*-----------My CTL formulas------------*/\n" + 
			"	static exN: Prod(Boolean, Natural) -> Boolean	//exists after N states\n" + 
			"	static axN: Prod(Boolean, Natural) -> Boolean	//forall paths, after N states\n" + 
			"definitions:\n" + 
			"";

	public final static String LTLlibrary = "module LTLlibrary\n" + 
			"\n" + 
			"export *\n" + 
			"\n" + 
			"signature:\n" + 
			"	/*-----------LTL formulas------------*/ \n" + 
			"	//Future\n" + 
			"	static x: Boolean -> Boolean			//next state\n" + 
			"	static g: Boolean -> Boolean			//globally\n" + 
			"	static f: Boolean -> Boolean			//finally\n" + 
			"	static u: Prod(Boolean, Boolean) -> Boolean	//until\n" + 
			"	static v: Prod(Boolean, Boolean) -> Boolean	//releases\n" + 
			"	//Past\n" + 
			"	static y: Boolean -> Boolean			//previous state\n" + 
			"	static z: Boolean -> Boolean			//not previous state not\n" + 
			"	static h: Boolean -> Boolean			//historically\n" + 
			"	static o: Boolean -> Boolean			//once\n" + 
			"	static s: Prod(Boolean, Boolean) -> Boolean	//since\n" + 
			"	static t: Prod(Boolean, Boolean) -> Boolean	//triggered\n" + 
			"\n" + 
			"definitions:\n" + 
			"";
}
