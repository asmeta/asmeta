// testDefinition3_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class testDefinition3_Exe {

	static void printControlled(testDefinition3 esecuzione) {

		System.out.print("Person" + " = {");
		for (int i = 0; i < esecuzione.Person_lista.size(); i++)
			if (i != esecuzione.Person_lista.size() - 1)
				System.out.print(esecuzione.Person_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Person_lista.get(i));
		System.out.println("}");
		System.out.print("NumCard" + " = {");
		for (int i = 0; i < esecuzione.NumCard_lista.size(); i++)
			if (i != esecuzione.NumCard_lista.size() - 1)
				System.out.print(esecuzione.NumCard_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.NumCard_lista.get(i));
		System.out.println("}");
		System.out.print("Till" + " = {");
		for (int i = 0; i < esecuzione.Till_lista.size(); i++)
			if (i != esecuzione.Till_lista.size() - 1)
				System.out.print(esecuzione.Till_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Till_lista.get(i));
		System.out.println("}");
		System.out.print("Card" + " = {");
		for (int i = 0; i < esecuzione.Card_lista.size(); i++)
			if (i != esecuzione.Card_lista.size() - 1)
				System.out.print(esecuzione.Card_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Card_lista.get(i));
		System.out.println("}");
		System.out.print("Date" + " = {");
		for (int i = 0; i < esecuzione.Date_lista.size(); i++)
			if (i != esecuzione.Date_lista.size() - 1)
				System.out.print(esecuzione.Date_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Date_lista.get(i));
		System.out.println("}");
		System.out.print("Account" + " = {");
		for (int i = 0; i < esecuzione.Account_lista.size(); i++)
			if (i != esecuzione.Account_lista.size() - 1)
				System.out.print(esecuzione.Account_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Account_lista.get(i));
		System.out.println("}");
		System.out.println("number = " + esecuzione.number.get());

	}

	static void askMonitored(testDefinition3 esecuzione) {

		System.out.print("Inserire un valore Intero per seconds (Integer):  ");
		Scanner secondsinput = new Scanner(System.in);

		for (;;) {
			int x;
			String secondsControllo = secondsinput.nextLine();
			if (secondsControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(secondsControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.seconds_supporto.value = x;
			esecuzione.seconds.set(esecuzione.seconds_supporto);
			break;
		}

		System.out.print("Inserire un valore Intero per minutes (Integer):  ");
		Scanner minutesinput = new Scanner(System.in);

		for (;;) {
			int x;
			String minutesControllo = minutesinput.nextLine();
			if (minutesControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(minutesControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.minutes_supporto.value = x;
			esecuzione.minutes.set(esecuzione.minutes_supporto);
			break;
		}

		System.out.print("Inserire un valore Intero per hours (Integer):  ");
		Scanner hoursinput = new Scanner(System.in);

		for (;;) {
			int x;
			String hoursControllo = hoursinput.nextLine();
			if (hoursControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(hoursControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.hours_supporto.value = x;
			esecuzione.hours.set(esecuzione.hours_supporto);
			break;
		}

		System.out.print("Inserire un valore Intero per coins (Integer):  ");
		Scanner coinsinput = new Scanner(System.in);

		for (;;) {
			int x;
			String coinsControllo = coinsinput.nextLine();
			if (coinsControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(coinsControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.coins_supporto.value = x;
			esecuzione.coins.set(esecuzione.coins_supporto);
			break;
		}

	}

public static void main(String[] args) {

		System.out.println("INFO - file java creto e tradotto dal file originale testDefinition3.asm");
		System.out.println("Inizio esecuzione del file testDefinition3.java\n\n");

		testDefinition3 esecuzione = new testDefinition3();

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


