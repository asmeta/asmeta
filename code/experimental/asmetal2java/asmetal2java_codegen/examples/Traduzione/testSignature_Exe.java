// testSignature_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class testSignature_Exe {

	static void printControlled(testSignature esecuzione) {

		System.out.print("NumCard" + " = {");
		for (int i = 0; i < esecuzione.NumCard_lista.size(); i++)
			if (i != esecuzione.NumCard_lista.size() - 1)
				System.out.print(esecuzione.NumCard_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.NumCard_lista.get(i));
		System.out.println("}");
		System.out.print("Sfortuna" + " = {");
		for (int i = 0; i < esecuzione.Sfortuna_lista.size(); i++)
			if (i != esecuzione.Sfortuna_lista.size() - 1)
				System.out.print(esecuzione.Sfortuna_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Sfortuna_lista.get(i));
		System.out.println("}");
		System.out.print("Dinam" + " = {");
		for (int i = 0; i < esecuzione.Dinam_lista.size(); i++)
			if (i != esecuzione.Dinam_lista.size() - 1)
				System.out.print(esecuzione.Dinam_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Dinam_lista.get(i));
		System.out.println("}");
		System.out.println("dominioC1 = " + esecuzione.dominioC1.get());
		System.out.println("dominioC6 = " + esecuzione.dominioC6.get());
		System.out.println("funC1 = " + esecuzione.funC1.get());

		System.out.println("funC2 = " + esecuzione.funC2.oldValue.name());
		System.out.println("funC3 = " + esecuzione.funC3.get().value);
		for (int i = 0; i < esecuzione.Color_lista.size(); i++) {
		System.out.println(" funC6 =>  (" + esecuzione.Color_lista.get(i) +") 
				= " + esecuzione.funC6.oldValues.get(esecuzione.Color_lista.get(i)).value );
			}
		System.out.println("bool = " + esecuzione.bool.get());

	}

	static void askMonitored(testSignature esecuzione) {

		System.out.print("Inserire un valore Intero per funM1 (Integer):  ");
		Scanner funM1input = new Scanner(System.in);

		for (;;) {
			int x;
			String funM1Controllo = funM1input.nextLine();
			if (funM1Controllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(funM1Controllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.funM1.set(x);
			break;
		}

		System.out.print(
				"Inserire un numero per indicare l'enumerativo per funM2 "
						+ esecuzione.Color_lista.toString() + ":  ");
		Scanner funM2input = new Scanner(System.in);

		for (;;) {
			int x;
			String funM2Controllo = funM2input.nextLine();
			if (funM2Controllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(funM2Controllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.funM2.set(esecuzione.Color_lista.get(x - 1));
			break;
		}
		System.out.print("Inserire un valore Intero per funM3 (Integer):  ");
		Scanner funM3input = new Scanner(System.in);

		for (;;) {
			int x;
			String funM3Controllo = funM3input.nextLine();
			if (funM3Controllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(funM3Controllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.funM3_supporto.value = x;
			esecuzione.funM3.set(esecuzione.funM3_supporto);
			break;
		}

	}

public static void main(String[] args) {

		System.out.println("INFO - file java creto e tradotto dal file originale testSignature.asm");
		System.out.println("Inizio esecuzione del file testSignature.java\n\n");

		testSignature esecuzione = new testSignature();

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


