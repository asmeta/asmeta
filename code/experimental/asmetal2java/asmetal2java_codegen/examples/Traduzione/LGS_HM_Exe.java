// LGS_HM_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class LGS_HM_Exe {

	static void printControlled(LGS_HM esecuzione) {

		System.out.println("doors = " + esecuzione.doors.oldValue.name());
		System.out.println("gears = " + esecuzione.gears.oldValue.name());
		System.out.println(
				"generalElectroValve = "
						+ esecuzione.generalElectroValve.get());

		System.out.println(
				"openDoorsElectroValve = "
						+ esecuzione.openDoorsElectroValve.get());

		System.out.println(
				"closeDoorsElectroValve = "
						+ esecuzione.closeDoorsElectroValve.get());

		System.out.println(
				"retractGearsElectroValve = "
						+ esecuzione.retractGearsElectroValve.get());

		System.out.println(
				"extendGearsElectroValve = "
						+ esecuzione.extendGearsElectroValve.get());

		System.out.println("anomaly = " + esecuzione.anomaly.get());

	}

	static void askMonitored(LGS_HM esecuzione) {

		System.out.print(
				"Inserire un numero per indicare l'enumerativo per handle "
						+ esecuzione.HandleStatus_lista.toString() + ":  ");
		Scanner handleinput = new Scanner(System.in);

		for (;;) {
			int x;
			String handleControllo = handleinput.nextLine();
			if (handleControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(handleControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.handle.set(esecuzione.HandleStatus_lista.get(x - 1));
			break;
		}
		System.out.print(
				"Inserire un valore booleano per timeout (true/false):  ");
		Scanner timeoutinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String timeoutControllo = timeoutinput.nextLine();
			if (timeoutControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(timeoutControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.timeout.set(y);
			break;
		}

		for (int i = 0; i < esecuzione.LandingSet_lista.size(); i++) {
			System.out.print(
					"Inserire un valore booleano per il dato enumerativo "
							+ esecuzione.LandingSet_lista.get(i)
							+ " della lista gearsExtended (true/false):  ");
			Scanner gearsExtendedinput = new Scanner(System.in);
			for (;;) {
				Boolean y;
				String gearsExtendedControllo = gearsExtendedinput.nextLine();
				if (gearsExtendedControllo.isEmpty())
					break;
				try {
					y = Boolean.parseBoolean(gearsExtendedControllo);
				} catch (Exception e) {
					System.out.println(
							"hai inserito un valore sbagliato, riprova");
					continue;
				}
				// setti la variabile
				esecuzione.gearsExtended.set(esecuzione.LandingSet_lista.get(i),
						y);
				break;
			}
		}

		for (int i = 0; i < esecuzione.LandingSet_lista.size(); i++) {
			System.out.print(
					"Inserire un valore booleano per il dato enumerativo "
							+ esecuzione.LandingSet_lista.get(i)
							+ " della lista gearsRetracted (true/false):  ");
			Scanner gearsRetractedinput = new Scanner(System.in);
			for (;;) {
				Boolean y;
				String gearsRetractedControllo = gearsRetractedinput.nextLine();
				if (gearsRetractedControllo.isEmpty())
					break;
				try {
					y = Boolean.parseBoolean(gearsRetractedControllo);
				} catch (Exception e) {
					System.out.println(
							"hai inserito un valore sbagliato, riprova");
					continue;
				}
				// setti la variabile
				esecuzione.gearsRetracted.set(
						esecuzione.LandingSet_lista.get(i), y);
				break;
			}
		}

		for (int i = 0; i < esecuzione.LandingSet_lista.size(); i++) {
			System.out.print(
					"Inserire un valore booleano per il dato enumerativo "
							+ esecuzione.LandingSet_lista.get(i)
							+ " della lista doorsClosed (true/false):  ");
			Scanner doorsClosedinput = new Scanner(System.in);
			for (;;) {
				Boolean y;
				String doorsClosedControllo = doorsClosedinput.nextLine();
				if (doorsClosedControllo.isEmpty())
					break;
				try {
					y = Boolean.parseBoolean(doorsClosedControllo);
				} catch (Exception e) {
					System.out.println(
							"hai inserito un valore sbagliato, riprova");
					continue;
				}
				// setti la variabile
				esecuzione.doorsClosed.set(esecuzione.LandingSet_lista.get(i),
						y);
				break;
			}
		}

		for (int i = 0; i < esecuzione.LandingSet_lista.size(); i++) {
			System.out.print(
					"Inserire un valore booleano per il dato enumerativo "
							+ esecuzione.LandingSet_lista.get(i)
							+ " della lista doorsOpen (true/false):  ");
			Scanner doorsOpeninput = new Scanner(System.in);
			for (;;) {
				Boolean y;
				String doorsOpenControllo = doorsOpeninput.nextLine();
				if (doorsOpenControllo.isEmpty())
					break;
				try {
					y = Boolean.parseBoolean(doorsOpenControllo);
				} catch (Exception e) {
					System.out.println(
							"hai inserito un valore sbagliato, riprova");
					continue;
				}
				// setti la variabile
				esecuzione.doorsOpen.set(esecuzione.LandingSet_lista.get(i), y);
				break;
			}
		}

		for (int i = 0; i < esecuzione.LandingSet_lista.size(); i++) {
			System.out.print(
					"Inserire un valore booleano per il dato enumerativo "
							+ esecuzione.LandingSet_lista.get(i)
							+ " della lista gearsShockAbsorber (true/false):  ");
			Scanner gearsShockAbsorberinput = new Scanner(System.in);
			for (;;) {
				Boolean y;
				String gearsShockAbsorberControllo =
						gearsShockAbsorberinput.nextLine();
				if (gearsShockAbsorberControllo.isEmpty())
					break;
				try {
					y = Boolean.parseBoolean(gearsShockAbsorberControllo);
				} catch (Exception e) {
					System.out.println(
							"hai inserito un valore sbagliato, riprova");
					continue;
				}
				// setti la variabile
				esecuzione.gearsShockAbsorber.set(
						esecuzione.LandingSet_lista.get(i), y);
				break;
			}
		}

	}

public static void main(String[] args) {

		System.out.println("INFO - file java creto e tradotto dal file originale LGS_HM.asm");
		System.out.println("Inizio esecuzione del file LGS_HM.java\n\n");

		LGS_HM esecuzione = new LGS_HM();

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


