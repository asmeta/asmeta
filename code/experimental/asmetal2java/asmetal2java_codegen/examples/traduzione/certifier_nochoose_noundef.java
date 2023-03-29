// certifier_nochoose_noundef.java automatically generated from ASM2CODE

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.Bag;
import java.util.concurrent.ThreadLocalRandom;
import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Octet;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

abstract class certifier_nochoose_noundef_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static enum Answers {
		UNDEFA, SKIP, EXIT, AA, BB, CC, DD
	}

	List<Answers> Answers_lista = new ArrayList<Answers>();

	//Variabile di tipo Concreto o Enumerativo

	static enum Shapes {
		AAA, BBB, CCC, DDD
	}

	List<Shapes> Shapes_lista = new ArrayList<Shapes>();

	//Variabile di tipo Concreto o Enumerativo

	static enum OutMessage {
		UNDEFM, CERTIFICATE, NOTCERTIFICATE
	}

	List<OutMessage> OutMessage_lista = new ArrayList<OutMessage>();

	//Variabile di tipo Concreto o Enumerativo

	static class Level {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Level Level_elem = new Level();

	List<Integer> Level_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class Certifier {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Certifier Certifier_elem = new Certifier();

	List<Integer> Certifier_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class AnswerError {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	AnswerError AnswerError_elem = new AnswerError();

	List<Integer> AnswerError_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class Certificato {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Certificato Certificato_elem = new Certificato();

	List<Integer> Certificato_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class MaxSkip {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	MaxSkip MaxSkip_elem = new MaxSkip();

	List<Integer> MaxSkip_elems = new ArrayList<Integer>();

	//Metodi di supporto per l'implementazione delle funzioni controlled

	class zeroC<Domain> {

		Domain oldValue;
		Domain newValue;

		void set(Domain d) {

			newValue = d;
		}

		Domain get() {

			return oldValue;
		}
	}

	static class nC<Domain, Codomain> {

		Map<Domain, Codomain> oldValues = new HashMap<>();
		Map<Domain, Codomain> newValues = new HashMap<>();

		void set(Domain d, Codomain c) {

			newValues.put(d, c);
		}

		Codomain get(Domain d) {

			return oldValues.get(d);
		}
	}

	//Metodi di supporto per l'implementazione delle funzioni non controlled

	class zero<Domain> {

		Domain Value;

		void set(Domain d) {

			Value = d;
		}

		Domain get() {

			return Value;
		}
	}

	class n<Domain, Codomain> {

		Map<Domain, Codomain> Values = new HashMap<>();

		void set(Domain d, Codomain c) {

			Values.put(d, c);
		}

		Codomain get(Domain d) {

			return Values.get(d);
		}
	}

	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	//Funzione di tipo Controlled
	zeroC<Boolean> test = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<OutMessage> outMessage = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Answers> currentAnswer = new zeroC<>();

	//Funzione di tipo monitored
	zero<Answers> getAnswer = new zero<>();

	//Funzione di tipo monitored
	zero<Shapes> choosenShape = new zero<>();

	//Funzione di tipo Controlled
	zeroC<Level> level = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Certifier> certifier = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Certificato> certificato = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<AnswerError> answerError = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<MaxSkip> maxSkip = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> loop = new zeroC<>();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_chooseshape();

	abstract
	void r_goOut();

	abstract
	void r_2ErrorGoBack();

	abstract
	void r_wrongCheckLevel();

	abstract
	void r_wrong();

	abstract
	void r_wrongAnswer();

	abstract
	void r_setCertifier();

	abstract
	void r_rightAnswNoLoop();

	abstract
	void r_rightAnswLoop();

	abstract
	void r_levelno1();

	abstract
	void r_level1();

	abstract
	void r_rightAnswer();

	abstract
	void r_checkanswer();

	abstract
	void r_skip();

	abstract
	void r_test();

	abstract
	void r_exit();

	abstract
	void r_Main();

}

class certifier_nochoose_noundef extends certifier_nochoose_noundef_sig {

	// Inizializzazione di funzioni e domini

	certifier_nochoose_noundef() {

		//Definizione iniziale dei domini statici

		Level.elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4, 5, 6));
		Level_elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4, 5, 6));
		Certifier.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
		Certifier_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
		AnswerError.elems = Collections.unmodifiableList(Arrays.asList(0, 1));
		AnswerError_elems = Collections.unmodifiableList(Arrays.asList(0, 1));
		Certificato.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2));
		Certificato_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2));
		MaxSkip.elems = Collections.unmodifiableList(Arrays.asList(0, 1));
		MaxSkip_elems = Collections.unmodifiableList(Arrays.asList(0, 1));
		//setto la lista di elementi di supporto della classe enumerativa
		for(Answers i : Answers.values())
		Answers_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(Shapes i : Shapes.values())
		Shapes_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(OutMessage i : OutMessage.values())
		OutMessage_lista.add(i);

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		//Inizializzazione delle funzioni

		test.oldValue = test.newValue = true;

		Level_elem.value = 6;

		level.oldValue = level.newValue = Level_elem;

		Certifier_elem.value = 0;

		certifier.oldValue = certifier.newValue = Certifier_elem;

		Certificato_elem.value = 0;

		certificato.oldValue = certificato.newValue = Certificato_elem;

		AnswerError_elem.value = 0;

		answerError.oldValue = answerError.newValue = AnswerError_elem;

		loop.oldValue = loop.newValue = false;

		MaxSkip_elem.value = 0;

		maxSkip.oldValue = maxSkip.newValue = MaxSkip_elem;

		outMessage.oldValue = outMessage.newValue = OutMessage.UNDEFM;
		currentAnswer.oldValue = currentAnswer.newValue = Answers.UNDEFA;

	}

	// Definizione delle funzioni statiche

	// Conversione delle regole ASM in metodi java

	@Override
	void r_chooseshape() {
		if(choosenShape.get()==Shapes.AAA) {
			currentAnswer.set(Answers.AA);
		} else if(choosenShape.get()==Shapes.BBB) {
			currentAnswer.set(Answers.BB);
		} else if(choosenShape.get()==Shapes.CCC) {
			currentAnswer.set(Answers.CC);
		} else if(choosenShape.get()==Shapes.DDD) {
			currentAnswer.set(Answers.DD);
		}
	}

	@Override
	void r_goOut() {
		{ //par
			test.set(false);
			Certifier Certifier_s = new Certifier();
			Certifier_s.value = (//certifier.get().value
					0);
			certifier.set(Certifier_s);
		} //endpar
	}

	@Override
	void r_2ErrorGoBack() {
		{ //par
			Level Level_s = new Level();
			Level_s.value = (//level.get().value
					(level.get().value
							+ 1));
			level.set(Level_s);
			AnswerError AnswerError_s = new AnswerError();
			AnswerError_s.value = (//answerError.get().value
					0);
			answerError.set(AnswerError_s);
			MaxSkip MaxSkip_s = new MaxSkip();
			MaxSkip_s.value = (//maxSkip.get().value
					0);
			maxSkip.set(MaxSkip_s);
			if ((certifier.get().value
							== 6)) {
				Certifier Certifier_s = new Certifier();
				Certifier_s.value = ( //certifier.get().value
						0);
				certifier.set(Certifier_s);
			} else {
				Certifier Certifier_s = new Certifier();
				Certifier_s.value = ( //certifier.get().value
						(certifier.get().value
								+ 1));
				certifier.set(Certifier_s);
			}
		} //endpar
	}

	@Override
	void r_wrongCheckLevel() {
		if ((level.get().value
						< 6)) {
			r_2ErrorGoBack();
		} else {
			test.set(false);
		}
	}

	@Override
	void r_wrong() {
		if ((loop.get() == false)) {
			{ //par
				loop.set(true);
				r_wrongCheckLevel();
			} //endpar
		} else {
			{ //par
				Certificato Certificato_s = new Certificato();
				Certificato_s.value = (//certificato.get().value
						0);
				certificato.set(Certificato_s);
				r_wrongCheckLevel();
			} //endpar
		}
	}

	@Override
	void r_wrongAnswer() {
		if ((answerError.get().value
						== 0)) {
			AnswerError AnswerError_s = new AnswerError();
			AnswerError_s.value = ( //answerError.get().value
					(answerError.get().value
							+ 1));
			answerError.set(AnswerError_s);
		} else {
			r_wrong();
		}
	}

	@Override
	void r_setCertifier() {
		if ((level.get().value
						== 6)) {
			Certifier Certifier_s = new Certifier();
			Certifier_s.value = ( //certifier.get().value
					6);
			certifier.set(Certifier_s);
		} else {
			Certifier Certifier_s = new Certifier();
			Certifier_s.value = ( //certifier.get().value
					(certifier.get().value
							- 1));
			certifier.set(Certifier_s);
		}
	}

	@Override
	void r_rightAnswNoLoop() {
		{ //par
			AnswerError AnswerError_s = new AnswerError();
			AnswerError_s.value = (//answerError.get().value
					0);
			answerError.set(AnswerError_s);
			MaxSkip MaxSkip_s = new MaxSkip();
			MaxSkip_s.value = (//maxSkip.get().value
					0);
			maxSkip.set(MaxSkip_s);
			Level Level_s = new Level();
			Level_s.value = (//level.get().value
					(level.get().value
							- 1));
			level.set(Level_s);
			r_setCertifier();
		} //endpar
	}

	@Override
	void r_rightAnswLoop() {
		if ((certificato.get().value
						<= 1)) {
			Certificato Certificato_s = new Certificato();
			Certificato_s.value = ( //certificato.get().value
					(certificato.get().value
							+ 1));
			certificato.set(Certificato_s);
		} else {
			{ //par
				test.set(false);
				r_setCertifier();
			} //endpar
		}
	}

	@Override
	void r_levelno1() {
		if ((loop.get() == false)) {
			r_rightAnswNoLoop();
		} else {
			r_rightAnswLoop();
		}
	}

	@Override
	void r_level1() {
		if ((certificato.get().value
						<= 1)) {
			{ //par
				Certifier Certifier_s = new Certifier();
				Certifier_s.value = (//certifier.get().value
						1);
				certifier.set(Certifier_s);
				Certificato Certificato_s = new Certificato();
				Certificato_s.value = (//certificato.get().value
						(certificato.get().value
								+ 1));
				certificato.set(Certificato_s);
			} //endpar
		} else {
			test.set(false);
		}
	}

	@Override
	void r_rightAnswer() {
		if ((level.get().value
						> 1)) {
			r_levelno1();
		} else {
			r_level1();
		}
	}

	@Override
	void r_checkanswer() {
		if ((currentAnswer.get() == getAnswer.get())) {
			r_rightAnswer();
		} else {
			r_wrongAnswer();
		}
	}

	@Override
	void r_skip() {
		{ //par
			if ((maxSkip.get().value
							== 0)) {
				MaxSkip MaxSkip_s = new MaxSkip();
				MaxSkip_s.value = ( //maxSkip.get().value
						1);
				maxSkip.set(MaxSkip_s);
			} else {
				r_wrong();
			}
			;
		} //endpar
	}

	@Override
	void r_test() {
		{ //par
			r_chooseshape();
			if ((currentAnswer.get() != Answers.UNDEFA)) {
				if ((getAnswer.get() == Answers.EXIT)) {
					r_goOut();
				} else if ((getAnswer.get() == Answers.SKIP)) {
					r_skip();
				} else {
					r_checkanswer();
				}
			}
		} //endpar
	}

	@Override
	void r_exit() {
		if ((outMessage.get() != OutMessage.UNDEFM)) {
			if ((certifier.get().value
							== 0)) {
				outMessage.set(OutMessage.NOTCERTIFICATE);
			} else {
				outMessage.set(OutMessage.CERTIFICATE);
			}
		}
	}

	@Override
	void r_Main() {
		if (test.get()) {
			r_test();
		} else {
			r_exit();
		}
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		test.oldValue = test.newValue;
		outMessage.oldValue = outMessage.newValue;
		currentAnswer.oldValue = currentAnswer.newValue;
		level.oldValue = level.newValue;
		certifier.oldValue = certifier.newValue;
		certificato.oldValue = certificato.newValue;
		answerError.oldValue = answerError.newValue;
		maxSkip.oldValue = maxSkip.newValue;
		loop.oldValue = loop.newValue;
	}

	//Metodo per l'aggiornamento dell'asm
	void UpdateASM()
	{
		r_Main();
		fireUpdateSet();
		initControlledWithMonitored();
	}

public static void main(String[] args) {
	}

}


