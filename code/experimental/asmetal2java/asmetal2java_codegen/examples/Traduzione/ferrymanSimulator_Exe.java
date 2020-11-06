// ferrymanSimulator_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class ferrymanSimulator_Exe {

	static void printControlled(ferrymanSimulator esecuzione) {

		for (int i = 0; i < esecuzione.Actors_lista.size(); i++) {
		System.out.println("position =>  (" + esecuzione.Actors_lista.get(i) +") 
				= "+ esecuzione.position.oldValues.get(esecuzione.Actors_lista.get(i)));
			}

		}

		static void askMonitored(ferrymanSimulator esecuzione) {

			System.out.print(
					"Inserire un numero per indicare l'enumerativo per carry "
							+ esecuzione.Actors_lista.toString() + ":  ");
			Scanner carryinput = new Scanner(System.in);

			for (;;) {
				int x;
				String carryControllo = carryinput.nextLine();
				if (carryControllo.isEmpty())
					break;
				try {
					x = Integer.parseInt(carryControllo);
				} catch (Exception e) {
					System.out.println(
							"hai inserito un valore sbagliato, riprova");
					continue;
				}

				esecuzione.carry.set(esecuzione.Actors_lista.get(x - 1));
				break;
			}

		}

	public static void main(String[] args) {

			System.out.println("INFO - file java creto e tradotto dal file originale ferrymanSimulator.asm");
			System.out.println("Inizio esecuzione del file ferrymanSimulator.java\n\n");

			ferrymanSimulator esecuzione = new ferrymanSimulator();

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


