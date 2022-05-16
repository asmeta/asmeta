// ATM3.h automatically generated from ASMETA2CODE
#ifndef ATM3_H
#define ATM3_H

//Arduino.h uses WString instead...
#include<string>
typedef std::string String;
#include<iostream> 
#include<vector> 
using namespace std;
#include <set>
#include <map>
#include <list>
#include <bits/stl_tree.h>
#include <boost/tuple/tuple.hpp>
#define ANY String

/////////////////////////////////////////////////
/// DOMAIN DEFINITIONS
/////////////////////////////////////////////////
/* Domain definitions here */
namespace ATM3namespace {
class NumCard;
enum State {
	AWAITCARD,
	AWAITPIN,
	CHOOSE,
	OUTOFSERVICE,
	CHOOSEAMOUNT,
	STANDARDAMOUNTSELECTION,
	OTHERAMOUNTSELECTION
};

typedef int MoneySize;

enum Service {
	BALANCE, WITHDRAWAL, EXIT
};

enum MoneySizeSelection {
	STANDARD, OTHER
};

}

using namespace ATM3namespace;

class ATM3namespace::NumCard {
public:
	static std::set<NumCard*> elems;
	NumCard() {
		elems.insert(this);
	}
};

class ATM3 {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	const set<State> State_elems;

	const set<MoneySize> MoneySize_elems;

	const set<Service> Service_elems;

	const set<MoneySizeSelection> MoneySizeSelection_elems;

public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	NumCard* currCard[2];
	State atmState[2];
	static int pin(NumCard* param0_pin);
	map<NumCard*, bool> accessible[2];
	int moneyLeft[2];
	map<NumCard*, int> balance[2];
	int numOfBalanceChecks[2];
	NumCard* insertedCard;
	int insertedPin;
	Service selectedService;
	MoneySize insertMoneySizeStandard;
	int insertMoneySizeOther;
	MoneySizeSelection standardOrOther;
	bool allowed(NumCard* param0_allowed, int param1_allowed);
	static NumCard* card1;

	static NumCard* card2;

	static NumCard* card3;

	static int minMoney();

	static int maxPrelievo();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_subtractFrom(NumCard* _c, int _m);
	void r_goOutOfService();
	void r_insertcard();
	void r_enterPin();
	void r_chooseService();
	void r_chooseAmount();
	void r_grantMoney(int _m);
	void r_processMoneyRequest(int _m);
	void r_prelievo();
	void r_Main();

	ATM3();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

