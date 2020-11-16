// SIS_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class SIS_Exe {

	static void printControlled(SIS esecuzione) {

		System.out.println(
				"waterpressure = " + esecuzione.waterpressure.get().value);
		System.out.println("pressure = " + esecuzione.pressure.oldValue.name());
		System.out.println("overridden = " + esecuzione.overridden.get());

		System.out.println(
				"safetyInjection = "
						+ esecuzione.safetyInjection.oldValue.name());

	}

	static void askMonitored(SIS esecuzione) {

		System.out.print(
				"Inserire un numero per indicare l'enumerativo per reset "
						+ esecuzione.Switch_lista.toString() + ":  ");
		Scanner resetinput = new Scanner(System.in);

		for (;;) {
			int x;
			String resetControllo = resetinput.nextLine();
			if (resetControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(resetControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.reset.set(esecuzione.Switch_lista.get(x - 1));
			break;
		}
		System.out.print(
				"Inserire un numero per indicare l'enumerativo per block "
						+ esecuzione.Switch_lista.toString() + ":  ");
		Scanner blockinput = new Scanner(System.in);

		for (;;) {
			int x;
			String blockControllo = blockinput.nextLine();
			if (blockControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(blockControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.block.set(esecuzione.Switch_lista.get(x - 1));
			break;
		}
		System.out.print("Inserire un valore Intero per delta (Integer):  ");
		Scanner deltainput = new Scanner(System.in);

		for (;;) {
			int x;
			String deltaControllo = deltainput.nextLine();
			if (deltaControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(deltaControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.delta_supporto.value = x;
			esecuzione.delta.set(esecuzione.delta_supporto);
			break;
		}

	}

public static void main(String[] args) {

		System.out.println("INFO - file java creto e tradotto dal file originale SIS.asm");
		System.out.println("Inizio esecuzione del file SIS.java\n\n");

		SIS esecuzione = new SIS();

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


