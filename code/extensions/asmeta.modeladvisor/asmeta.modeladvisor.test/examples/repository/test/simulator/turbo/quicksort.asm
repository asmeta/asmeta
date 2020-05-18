asm quicksort
import ../../../STDL/StandardLibrary
	
signature:
controlled f: Seq(Integer)
static llist: Prod(Seq(Integer), Integer) -> Seq(Integer)
static glist: Prod(Seq(Integer), Integer) -> Seq(Integer)
		
definitions:

// extract the values lesser than the pivot
function llist($lst in Seq(Integer), $pivot in Integer) =
	if length($lst) = 0 then
		[]
	else
		let ($x = first($lst)) in
			if $x < $pivot then
				append(llist(tail($lst), $pivot), $x)
			else
				llist(tail($lst), $pivot)
			endif
		endlet
	endif

// extract the values greater than or equal to the pivot
function glist($lst in Seq(Integer), $pivot in Integer) =
	if length($lst) = 0 then
		[]
	else
		let ($x = first($lst)) in
			if $x >= $pivot then
				append(glist(tail($lst), $pivot), $x)
			else
				glist(tail($lst), $pivot)
			endif
		endlet
	endif

// quick sort
turbo rule r_qsort($lst in Seq(Integer)) =
	if length($lst) < 2 then
		result := $lst
	else
		let ($x = [], $y = [], 
		$tail = tail($lst), 
		$head = first($lst),
		$llist = llist($tail, $head),
		$glist = glist($tail, $head)) in seq
			$x <- r_qsort($llist)
			$y <- r_qsort($glist)
			// $x concat $head concat $y
			result := union(append($x, $head), $y)
		endseq endlet
	endif

main rule r_main = 
	f <- r_qsort([5,7,1,8,3,2,6,4,0,9])
	
