asm CartSimple 
// CHANGES
// No output messages
// Removed abstract domain and transformed into an enum domain (Drug)
// Reduced domain SubInteger and SubIntegerReduced
// Removed various variables also static
// Modified Main rule (not sequential because it is not supported)
// Modified DrugDetail rule (removed exist)
// Price fixed for generic drugs
// Removed loop (NO -> CLOSED)

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	// DOMAINS
	enum domain Drug = {LITIO | MORFINA | PARACETAMOLO} // Enum domain
	domain SubInteger subsetof Integer
	domain SubIntegerReduced subsetof Integer // Reduced for MP6
	enum domain States = {WAITING | ADD_PRODUCT_OR_EXIT | CHOOSE_GEN_COM | SELECTED_GENERIC | SELECTED_COMMERCIAL | CLOSED}
	enum domain Actions = {ORDER | EXIT}
	enum domain AddProduct = {YES | NO}
	enum domain Type = {GENERIC | COMMERCIAL}

	// CONTROLLED VARAIBLES
	dynamic controlled cartState: States
	dynamic controlled currentDrug: Drug
	dynamic controlled total: SubInteger
	dynamic controlled numOfProductsInCart: SubIntegerReduced

	// MONITORED VARAIBLES
	dynamic monitored action: Actions
	dynamic monitored selectedDrug: Drug 
	dynamic monitored selectedAddProduct: AddProduct
	dynamic monitored selectedDrugType: Type
	dynamic monitored insertQuantity: SubInteger
	dynamic monitored insertPrice: SubInteger
	
	// DERIVED FUNCTION
	derived valid: Boolean

definitions:
	// DOMAIN DEFINITIONS
	domain SubInteger = {0:10}
	domain SubIntegerReduced = {0:2}

	// DERIVED FUNCTION
	function valid = numOfProductsInCart < 2 // Max number of products in cart

	// MACRO RULE SUPPORT
	macro rule r_AddGenericToTotal =
		par
		total := total + 1*insertQuantity // Price fixed
		numOfProductsInCart := numOfProductsInCart + 1
		cartState := ADD_PRODUCT_OR_EXIT
		endpar
	
	macro rule r_AddCommercialToTotal =
		par
		total := total + insertPrice*insertQuantity
		numOfProductsInCart := numOfProductsInCart + 1
		cartState := ADD_PRODUCT_OR_EXIT
		endpar
	
	// MACRO RULE MAIN
	macro rule r_Waiting =
		if (cartState=WAITING) then
			par
			if (action=ORDER) then
				cartState := ADD_PRODUCT_OR_EXIT
			endif
			
			if (action=EXIT) then
				cartState := CLOSED
			endif
			endpar
		endif
	
	macro rule r_SelectAddProductOrExit =
		if (cartState=ADD_PRODUCT_OR_EXIT) then
			par
			if (selectedAddProduct=YES) then
				cartState := CHOOSE_GEN_COM
			endif
			
			if (selectedAddProduct=NO) then
				cartState := CLOSED // Removed loop
			endif
			endpar
		endif

	macro rule r_SelectDrugType =
		if (cartState=CHOOSE_GEN_COM) then
			par
			if (selectedDrugType=GENERIC) then
				cartState := SELECTED_GENERIC
			endif
			
			if (selectedDrugType=COMMERCIAL) then
				cartState := SELECTED_COMMERCIAL
			endif
			endpar
		endif
	
	macro rule r_DrugDetail = // Modified
		par
		if (cartState=SELECTED_GENERIC) then
			seq // Necessarily sequential
			currentDrug := selectedDrug
			r_AddGenericToTotal[]
			endseq
		endif
	
		if (cartState=SELECTED_COMMERCIAL) then
			r_AddCommercialToTotal[]
		endif
		endpar
		
	macro rule r_Closing = 
		cartState := CLOSED
		
	// CTL
	// I prodotti nel carrello sono sempre minori o uguali di due
	CTLSPEC ag(numOfProductsInCart <= 2)
	
	// Effettuato il pagamento si rimarr� nello stato CLOSED
	CTLSPEC ag(cartState = CLOSED implies ag(cartState = CLOSED))
	
	// Iniziato un ordine si finir� sicuramente nello stato CLOSED (Grazie al numero massimo di prodotti nel carrello si evitano loop)
	CTLSPEC ag(cartState = CHOOSE_GEN_COM implies af(cartState = CLOSED))

	// In ogni percorso, dopo aver cliccato EXIT si passa allo stato CLOSED
	CTLSPEC ag((cartState = WAITING and action = EXIT) implies ax(cartState = CLOSED))
	
	// In ogni percorso, dopo aver cliccato NO si passa allo stato CLOSED
	CTLSPEC ag((cartState=ADD_PRODUCT_OR_EXIT and selectedAddProduct = NO) implies ax(cartState = CLOSED))

	// MAIN RULE
	main rule r_Main =
		par
		r_Waiting[]
		if (valid) then
			par
			r_SelectAddProductOrExit[]
			r_SelectDrugType[]
			r_DrugDetail[]
			endpar
		else
			r_Closing[]
		endif
		endpar

// INITIAL STATE
default init s0:
	function cartState = WAITING
	function numOfProductsInCart = 0 
	function total = 0
