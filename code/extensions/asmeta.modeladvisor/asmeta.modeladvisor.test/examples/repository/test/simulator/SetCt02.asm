asm SetCt02

import ../../STDL/StandardLibrary

signature:
    
    abstract domain Orders
    abstract domain Products
    enum domain OrderStatus = { INVOICED|PENDING}
    domain Quantity subsetof Integer

    static o1: Orders
    static o2: Orders
    static o3: Orders

    static p1: Products
    static p2: Products

    static  referencedProduct : Orders -> Products
    static	orderQuantity : Orders -> Quantity
    static	pendingOrders: Products -> Powerset(Orders)
    controlled  orderState : Orders -> OrderStatus
    controlled  stockQuantity : Products -> Quantity

    controlled  f: Powerset(Orders)

definitions:

    // given a product return the set of orders of that product that are still PENDING

    function pendingOrders($p in Products) =
		{$o in Orders | orderState($o) = PENDING and referencedProduct($o) = $p : $o}


    function referencedProduct($o in Orders) = 
		switch $o
			case o1: p1
			case o2: p1
			case o3: p2
		endswitch

	function orderQuantity($o in Orders) = 
		switch $o
			case o1: 10
			case o2: 40
			case o3: 30
		endswitch

    macro rule r_DeleteStock ($p in Products, $q in Quantity)=
        stockQuantity($p) := stockQuantity($p) - $q

    macro rule r_InvoiceSingleOrder =
        choose $order in Orders with
           orderState($order) = PENDING and
           orderQuantity ($order) <= stockQuantity(referencedProduct($order))
        do par
           orderState ($order):= INVOICED
           r_DeleteStock [referencedProduct($order),orderQuantity($order)]
        endpar

main rule r_OrderSystem =
	f := pendingOrders(p1)

default init s0:
        
    function orderState($o in Orders) = PENDING
    function stockQuantity($p in Products) = 49
