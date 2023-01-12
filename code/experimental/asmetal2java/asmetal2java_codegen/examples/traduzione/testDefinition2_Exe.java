// testDefinition2_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class testDefinition2_Exe {

	static void printControlled(testDefinition2 esecuzione) {

		System.out.print("Lift" + " = {");
		for (int i = 0; i < esecuzione.Lift_lista.size(); i++)
			if (i != esecuzione.Lift_lista.size() - 1)
				System.out.print(esecuzione.Lift_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Lift_lista.get(i));
		System.out.println("}");
		System.out.println("numA = " + esecuzione.numA.get());

		System.out.println("numB = " + esecuzione.numB.get());

		for (int i = 0; i < esecuzione.Product_lista.size(); i++) {
		System.out.println(" available =>  (" + esecuzione.Product_lista.get(i) +") 
				= " + esecuzione.available.oldValues.get(esecuzione.Product_lista.get(i)).value );
			}
			System.out.println("coins = " + esecuzione.coins.get().value);
			for (int i = 0; i < esecuzione.Actors_lista.size(); i++) {
			System.out.println("position =>  (" + esecuzione.Actors_lista.get(i) +") 
					= "+ esecuzione.position.oldValues.get(esecuzione.Actors_lista.get(i)));
				}
				System.out.println("num_fibo = " + esecuzione.num_fibo.get());

				System.out.println("indice = " + esecuzione.indice.get());

				System.out.println("succ_fibo = " + esecuzione.succ_fibo.get());
				System.out.println(
						"ctl_state = " + esecuzione.ctl_state.get().value);
				System.out.println(
						"doors = " + esecuzione.doors.oldValue.name());
				System.out.println(
						"gears = " + esecuzione.gears.oldValue.name());
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

				System.out.println(
						"controlledfunction = "
								+ esecuzione.controlledfunction.get());

			}

			static void askMonitored(testDefinition2 esecuzione) {

				System.out.print(
						"Inserire un valore booleano per high (true/false):  ");
				Scanner highinput = new Scanner(System.in);

				for (;;) {
					Boolean y;
					String highControllo = highinput.nextLine();
					if (highControllo.isEmpty())
						break;
					try {
						y = Boolean.parseBoolean(highControllo);
					} catch (Exception e) {
						System.out.println(
								"hai inserito un valore sbagliato, riprova");
						continue;
					}
					// setti la variabile
					esecuzione.high.set(y);
					break;
				}

				System.out.print(
						"Inserire un valore booleano per low (true/false):  ");
				Scanner lowinput = new Scanner(System.in);

				for (;;) {
					Boolean y;
					String lowControllo = lowinput.nextLine();
					if (lowControllo.isEmpty())
						break;
					try {
						y = Boolean.parseBoolean(lowControllo);
					} catch (Exception e) {
						System.out.println(
								"hai inserito un valore sbagliato, riprova");
						continue;
					}
					// setti la variabile
					esecuzione.low.set(y);
					break;
				}

				System.out.print(
						"Inserire un valore Intero per monitoredfunction (Integer):  ");
				Scanner monitoredfunctioninput = new Scanner(System.in);

				for (;;) {
					int x;
					String monitoredfunctionControllo =
							monitoredfunctioninput.nextLine();
					if (monitoredfunctionControllo.isEmpty())
						break;
					try {
						x = Integer.parseInt(monitoredfunctionControllo);
					} catch (Exception e) {
						System.out.println(
								"hai inserito un valore sbagliato, riprova");
						continue;
					}

					esecuzione.monitoredfunction.set(x);
					break;
				}

			}

		public static void main(String[] args) {

				System.out.println("INFO - file java creto e tradotto dal file originale testDefinition2.asm");
				System.out.println("Inizio esecuzione del file testDefinition2.java\n\n");

				testDefinition2 esecuzione = new testDefinition2();

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


