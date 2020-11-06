// ATM3.cpp automatically generated from ASM2CODE

#include "ATM3.h"
using namespace ATM3namespace;

// Conversion of ASM rules in C++ methods
void ATM3::r_subtractFrom(NumCard* _c, int _m) {
	balance[1][_c] = (balance[0][_c] - _m);
}
void ATM3::r_goOutOfService() {
	atmState[1] = OUTOFSERVICE;
}
void ATM3::r_insertcard() {
	if ((atmState[0] == AWAITCARD)) {
		if ([&]()->bool { /*<--- ExistsTerm*/
			for(auto _c : NumCard::elems)
			if((_c == insertedCard))
			return true;
			return false;
		}()) {
			{ //par
				currCard[1] = insertedCard;
				atmState[1] = AWAITPIN;
			} //endpar
		}
	}
}
void ATM3::r_enterPin() {
	if ((atmState[0] == AWAITPIN)) {
		if ((insertedPin == pin(currCard[0])) & accessible[0][currCard[0]]) {
			{ //par
				atmState[1] = CHOOSE;
				numOfBalanceChecks[1] = 0;
			} //endpar
		} else {
			atmState[1] = AWAITCARD;
		}
	}
}
void ATM3::r_chooseService() {
	if ((atmState[0] == CHOOSE)) {
		{ //par
			if ((selectedService == BALANCE)) {
				if ((numOfBalanceChecks[0] == 0)) {
					numOfBalanceChecks[1] = (numOfBalanceChecks[0] + 1);
				} else {
					atmState[1] = AWAITCARD;
				}
			}
			if ((selectedService == WITHDRAWAL)) {
				atmState[1] = CHOOSEAMOUNT;
			}
			if ((selectedService == EXIT)) {
				atmState[1] = AWAITCARD;
			}
		} //endpar
	}
}
void ATM3::r_chooseAmount() {
	if ((atmState[0] == CHOOSEAMOUNT)) {
		{ //par
			if ((standardOrOther == STANDARD)) {
				atmState[1] = STANDARDAMOUNTSELECTION;
			}
			if ((standardOrOther == OTHER)) {
				atmState[1] = OTHERAMOUNTSELECTION;
			}
		} //endpar
	}
}
void ATM3::r_grantMoney(int _m) {
	{ //par
		accessible[1][currCard[1]] = false;
		r_subtractFrom(currCard[0], _m);
		moneyLeft[1] = (moneyLeft[0] - _m);
		atmState[1] = AWAITCARD;
	} //endpar
}
void ATM3::r_processMoneyRequest(int _m) {
	if (allowed(currCard[0], _m)) {
		r_grantMoney(_m);
	}
}
void ATM3::r_prelievo() {
	{ //par
		if ((atmState[0] == STANDARDAMOUNTSELECTION)) {
			if ([&]()->bool { /*<--- ExistsTerm*/
				for(auto _m : MoneySize_elems)
				if((_m == insertMoneySizeStandard))
				return true;
				return false;
			}()) {
				if ((insertMoneySizeStandard <= moneyLeft[0])) {
					r_processMoneyRequest (insertMoneySizeStandard);
				}
			}
		}
		if ((atmState[0] == OTHERAMOUNTSELECTION)) {
			if (((insertMoneySizeOther % 10) == 0)) {
				if ((insertMoneySizeOther <= moneyLeft[0])) {
					r_processMoneyRequest (insertMoneySizeOther);
				}
			}
		}
	} //endpar
}
void ATM3::r_Main() {
	if ((moneyLeft[0] < minMoney())) {
		r_goOutOfService();
	} else {
		{ //par
			r_insertcard();
			r_enterPin();
			r_chooseService();
			r_chooseAmount();
			{ //seq
				r_prelievo_seq();
				if (!accessible[0][currCard[0]]) {
					accessible[1][currCard[1]] = true;
					accessible[0][currCard[0]] = accessible[1][currCard[1]];
				}
			} //endseq
		} //endpar
	}
}

// Static function definition
int ATM3::minMoney() {
	return 200;
}
int ATM3::maxPrelievo() {
	return 1000;
}
int ATM3::pin(NumCard* _c) {
	return [&]() { /*<--- caseTerm*/
		if(_c==card1)
		return 1;
		else if(_c==card2)
		return 2;
		else if(_c==card3)
		return 3;
	}();
}
bool ATM3::allowed(NumCard* _c, int _m) {
	return (balance[0][_c] >= _m);
}

// Function and domain initialization
ATM3::ATM3() :
		// Static domain initialization 
		MoneySize_elems(set<int> { 10, 20, 40, 50, 100, 150, 200 }), State_elems(
				{ AWAITCARD, AWAITPIN, CHOOSE, OUTOFSERVICE, CHOOSEAMOUNT,
						STANDARDAMOUNTSELECTION, OTHERAMOUNTSELECTION }), Service_elems(
				{ BALANCE, WITHDRAWAL, EXIT }), MoneySizeSelection_elems( {
				STANDARD, OTHER }) {
	//Init static functions Abstract domain
	card1 = new NumCard;
	card2 = new NumCard;
	card3 = new NumCard;
	//Function initialization
	atmState[0] = atmState[1] = AWAITCARD;
	moneyLeft[0] = moneyLeft[1] = 1000;
	for (const auto& _c : NumCard::elems) {
		balance[0].insert( { _c, [&]() { /*<--- caseTerm*/
			if(_c==card1)
			return 3000;
			else if(_c==card2)
			return 1652;
			else if(_c==card3)
			return 548;
		}() });
		balance[1].insert( { _c, [&]() { /*<--- caseTerm*/
			if(_c==card1)
			return 3000;
			else if(_c==card2)
			return 1652;
			else if(_c==card3)
			return 548;
		}() });
	}
	for (const auto& _c : NumCard::elems) {
		accessible[0].insert( { _c, true });
		accessible[1].insert( { _c, true });
	}
}

// initialize controlled functions that contains monitored functions in the init term
void ATM3::initControlledWithMonitored() {
}

// Apply the update set
void ATM3::fireUpdateSet() {
	currCard[0] = currCard[1];
	atmState[0] = atmState[1];
	accessible[0] = accessible[1];
	moneyLeft[0] = moneyLeft[1];
	balance[0] = balance[1];
	numOfBalanceChecks[0] = numOfBalanceChecks[1];
}

//init static functions and elements of abstract domains
std::set<NumCard*> NumCard::elems;
NumCard*ATM3::card1;
NumCard*ATM3::card2;
NumCard*ATM3::card3;


