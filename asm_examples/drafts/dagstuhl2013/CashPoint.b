/* ATM
 * Author: 
 * Creation date: 13-09-10
 */
MACHINE
    ATM

SETS
    ACCOUNT = {a1,a2}; TILL = {t1,t2}; CARD={c1,c2}; PIN={p1,p2}; TILL_STATE = {wait_card,wait_pin,valid_pin}; DATE = {d1,d2}

CONSTANTS
    withdraw_limit
    
PROPERTIES
    withdraw_limit : NAT

VARIABLES
    accounts, balance, tills, legal_cards,
    daily_withdraw_sum, pin, till_card, till_state, card_account, card_last_used_date, current_date
    
INVARIANT
    accounts <: ACCOUNT &
    balance : accounts --> NAT &
    tills <: TILL &
    legal_cards <: CARD &
    daily_withdraw_sum : legal_cards --> NAT &
    pin : legal_cards --> PIN &
    till_card : TILL +-> CARD &
    till_state : tills --> TILL_STATE &
    card_account : legal_cards --> accounts &
    card_last_used_date : legal_cards --> DATE &
    current_date : DATE
    
INITIALISATION

    ANY aa,lc,tt WHERE aa <: ACCOUNT & lc <: CARD & tt <: TILL THEN
        accounts := aa ||
        balance :: aa --> NAT ||
        legal_cards := lc ||
        daily_withdraw_sum :: lc --> NAT ||
        pin :: lc --> PIN ||
        tills := tt ||
        till_state := tt* {wait_card} ||
	card_account :: lc --> aa ||
	card_last_used_date :: lc --> DATE
    END ||
    till_card := {} ||
    current_date :: DATE

OPERATIONS
    
    insert_card(a_till,a_card) =
    PRE
        a_till : TILL &
        a_card : CARD &
        till_state(a_till) = wait_card &
        a_card : legal_cards
    THEN
        till_state(a_till) := wait_pin ||
        till_card(a_till) := a_card
    END;
    
    enter_pin(a_till,a_pin) =
    PRE
        a_till : TILL &
        a_pin : PIN &
        a_pin = pin(till_card(a_till)) &
	till_state(a_till) = wait_pin
    THEN
        till_state(a_till) := valid_pin ||
	IF current_date /= card_last_used_date(till_card(a_till)) THEN
	    daily_withdraw_sum(till_card(a_till)) := 0 ||
	    card_last_used_date(till_card(a_till)) := current_date
	END    
    END;

    withdraw(a_till,amount) =
    PRE        
        a_till : TILL &
	amount : NAT1 &
	till_state(a_till) = valid_pin &
	amount <= balance(card_account(till_card(a_till))) &
	daily_withdraw_sum(till_card(a_till)) + amount <= withdraw_limit
    THEN
	balance(card_account(till_card(a_till))) := balance(card_account(till_card(a_till))) - amount ||
	daily_withdraw_sum(till_card(a_till)) := daily_withdraw_sum(till_card(a_till)) + amount
    END
END
