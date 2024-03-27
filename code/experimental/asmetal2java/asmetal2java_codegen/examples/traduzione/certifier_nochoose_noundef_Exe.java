// certifier_nochoose_noundef_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class certifier_nochoose_noundef_Exe {

	static void printControlled(certifier_nochoose_noundef esecuzione) {

		System.out.println("test = " + esecuzione.test.get());

		System.out.println(
				"outMessage = " + esecuzione.outMessage.oldValue.name());
		System.out.println(
				"currentAnswer = " + esecuzione.currentAnswer.oldValue.name());
		System.out.println("level = " + esecuzione.level.get().value);
		System.out.println("certifier = " + esecuzione.certifier.get().value);
		System.out.println(
				"certificato = " + esecuzione.certificato.get().value);
		System.out.println(
				"answerError = " + esecuzione.answerError.get().value);
		System.out.println("maxSkip = " + esecuzione.maxSkip.get().value);
		System.out.println("loop = " + esecuzione.loop.get());

	}

	static void askMonitored(certifier_nochoose_noundef esecuzione) {

		System.out.print(
				"Inserire un numero per indicare l'enumerativo per getAnswer "
						+ esecuzione.Answers_lista.toString() + ":  ");
		Scanner getAnswerinput = new Scanner(System.in);

		for (;;) {
			int x;
			String getAnswerControllo = getAnswerinput.nextLine();
			if (getAnswerControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(getAnswerControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.getAnswer.set(esecuzione.Answers_lista.get(x - 1));
			break;
		}
		System.out.print(
				"Inserire un numero per indicare l'enumerativo per choosenShape "
						+ esecuzione.Shapes_lista.toString() + ":  ");
		Scanner choosenShapeinput = new Scanner(System.in);

		for (;;) {
			int x;
			String choosenShapeControllo = choosenShapeinput.nextLine();
			if (choosenShapeControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(choosenShapeControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.choosenShape.set(esecuzione.Shapes_lista.get(x - 1));
			break;
		}

	}

public static void main(String[] args) {

		System.out.println("INFO - file java creto e tradotto dal file originale certifier_nochoose_noundef.asm");
		System.out.println("Inizio esecuzione del file certifier_nochoose_noundef.java\n\n");

		certifier_nochoose_noundef esecuzione = new certifier_nochoose_noundef();

		String continuare = "no";
		int stato =0;
		stato++;

		System.out.println("INITIAL STATE: ");

		do {

			System.out.println("<State "+ stato +" (controlled)>");

			//Aggiornamento valori dell'ASM e inserimento dati monitorati

			printControlled(esecuzione);
			askMonitored(esecuzione);
			esecuzione.UpdateASM();

			System.out.println("</State "+ stato +" (controlled)>");

			System.out.println("\n<Stato attuale>");
			printControlled(esecuzione);

			System.out.print("Vuoi continuare? (yes/no)  ");
			Scanner input = new Scanner(System.in);
			continuare = input.next();

			stato++;
		}

		while(continuare.contentEquals("yes") || continuare.contentEquals("Yes") || continuare.contentEquals("YES") );

		System.out.println("FINAL STATE:");

		//Valori finale delle variabili
		printControlled(esecuzione);

		System.out.println("esecuzione terminata");

	}

}


