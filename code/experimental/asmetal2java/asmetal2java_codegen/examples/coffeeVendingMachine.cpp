// coffeeVendingMachine.cpp automatically generated from ASM2CODE

#include "coffeeVendingMachine.h"
using namespace coffeeVendingMachinenamespace;

// Conversion of ASM rules in C++ methods
void coffeeVendingMachine::r_serveProduct(Product _p) {
	{ //par
		available[1][_p] = (available[0][_p] - 1);
		coins[1] = (coins[0] + 1);
	} //endpar
}
void coffeeVendingMachine::r_Main() {
	if ((coins[0] < 25)) {
		if ((insertedCoin == HALF)) {
			if ((available[0][MILK] > 0)) {
				r_serveProduct (MILK);
			}
		} else {
			vector<const Product*> point0;
			for (auto const& _p : Product_elems)
				if ((_p != MILK) & (available[0][_p] > 0)) {
					point0.push_back(&_p);
				}
			int rndm = rand() % point0.size();
			{
				auto _p = *point0[rndm];
				if (point0.size() > 0) {
					r_serveProduct(_p);
				}
			}
		}
	}
}

// Static function definition

// Function and domain initialization
coffeeVendingMachine::coffeeVendingMachine() :
		// Static domain initialization 
		QuantityDomain_elems(set<int> { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }), CoinDomain_elems(
				set<int> { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
						16, 17, 18, 19, 20, 21, 22, 23, 24, 25 }), CoinType_elems(
				{ HALF, ONE }), Product_elems( { COFFEE, TEA, MILK }) {
	//Init static functions Abstract domain
	//Function initialization
	coins[0] = coins[1] = 0;
	for (auto const& _p : Product_elems) {
		available[0].insert( { _p, 10 });
		available[1].insert( { _p, 10 });
	}
}

// initialize controlled functions that contains monitored functions in the init term
void coffeeVendingMachine::initControlledWithMonitored() {
}

// Apply the update set
void coffeeVendingMachine::fireUpdateSet() {
	available[0] = available[1];
	coins[0] = coins[1];
}

//init static functions and elements of abstract domains


