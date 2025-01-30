/** secon version of oreder system, with inputs*/

asm OrderSystem2
	
import ../../STDL/StandardLibrary
	
signature:

	dynamic abstract domain Orders
	abstract domain Products
	domain Quantity subsetof Integer
	enum domain OrderStatus = {INVOICED | PENDING | CANCELLED}

	static p1: Products
	static p2: Products
	static p3: Products

	dynamic controlled referencedProduct: Orders -> Products
	dynamic controlled orderState: Orders -> OrderStatus
	dynamic controlled orderQuantity: Orders -> Quantity
	dynamic controlled stockQuantity: Products -> Quantity
	
	// new orders to be inserted
	monitored newOrders: Seq(Prod(Products, Integer))

	// orders to be cancelled
	monitored ordersToCancel: Seq(Orders)

	// items to be inserted
	monitored newItems: Seq(Prod(Products, Integer))

definitions:
	
	macro rule r_DeleteStock($p in Products ,$q in Quantity) = 
		stockQuantity($p):= stockQuantity($p) - $q
	
	rule r_InvoiceSingleOrder = 
		choose $order in Orders with 
			orderState($order) = PENDING and
			orderQuantity($order) <= stockQuantity(referencedProduct($order)) do
		par
			orderState($order) := INVOICED 
			r_DeleteStock[referencedProduct($order), orderQuantity($order)] 
		endpar 

	/* --- incoming orders --- */
	rule r_AddOrders = 
		forall $pair in asSet(newOrders) with true do
			let ($product = first($pair), $quantity = second($pair)) in
				extend Orders with $order do par
					referencedProduct($order):= $product
					orderQuantity($order):= $quantity
					orderState($order):= PENDING
				endpar
			endlet

	/*--- cacellation of orders ---*/
	rule r_CancelOrders =
		forall $order in asSet(ordersToCancel) with true do
			orderState($order) := CANCELLED

	/*--- inserting new items in stock ---*/
	rule r_AddItems = 
		forall $item in asSet(newItems) with true do
			let ($product = first($item), $quantity = second($item)) in
				stockQuantity($product) := stockQuantity($product) + $quantity
			endlet

/* properties */
invariant over stockQuantity: (forall $p in Products with stockQuantity($p) >= 0)
invariant over orderState: (forall $o in Orders with isDef(orderState($o)))

/*------- main rule   --------*/	                 	
main rule r_Main = 
	seq
		par
			r_AddOrders[]
			r_CancelOrders[] 
			r_AddItems[]
		endpar
		r_InvoiceSingleOrder[]
	endseq

default init s0:   
	function stockQuantity($p in Products) = 100
