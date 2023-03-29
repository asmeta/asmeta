
// ATM3_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM
import java.util.Scanner;

class ATM3_Exe {
	static void printControlled(ATM3 esecuzione) {
		System.out.print("NumCard" + " = {");
		for (int i = 0; i < esecuzione.NumCard_lista.size(); i++)
			if (i != esecuzione.NumCard_lista.size() - 1)
				System.out.print(esecuzione.NumCard_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.NumCard_lista.get(i));
		System.out.println("}");
		System.out.println("atmState = " + esecuzione.atmState.oldValue.name());
		System.out.println("moneyLeft = " + esecuzione.moneyLeft.get());
		System.out.println("numOfBalanceChecks = " + esecuzione.numOfBalanceChecks.get());
	}

	static void askMonitored(ATM3 esecuzione) {
		System.out.print("Inserire un valore Intero per insertedPin (Integer):  ");
		Scanner insertedPininput = new Scanner(System.in);
		for (;;) {
			int x;
			String insertedPinControllo = insertedPininput.nextLine();
			if (insertedPinControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(insertedPinControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			esecuzione.insertedPin.set(x);
			break;
		}
		System.out.print("Inserire un numero per indicare l'enumerativo per selectedService "
				+ esecuzione.Service_lista.toString() + ":  ");
		Scanner selectedServiceinput = new Scanner(System.in);
		for (;;) {
			int x;
			String selectedServiceControllo = selectedServiceinput.nextLine();
			if (selectedServiceControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(selectedServiceControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			esecuzione.selectedService.set(esecuzione.Service_lista.get(x - 1));
			break;
		}
		System.out.print("Inserire un valore Intero per insertMoneySizeStandard (Integer):  ");
		Scanner insertMoneySizeStandardinput = new Scanner(System.in);
		for (;;) {
			int x;
			String insertMoneySizeStandardControllo = insertMoneySizeStandardinput.nextLine();
			if (insertMoneySizeStandardControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(insertMoneySizeStandardControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			esecuzione.insertMoneySizeStandard_supporto.value = x;
			esecuzione.insertMoneySizeStandard.set(esecuzione.insertMoneySizeStandard_supporto);
			break;
		}
		System.out.print("Inserire un valore Intero per insertMoneySizeOther (Integer):  ");
		Scanner insertMoneySizeOtherinput = new Scanner(System.in);
		for (;;) {
			int x;
			String insertMoneySizeOtherControllo = insertMoneySizeOtherinput.nextLine();
			if (insertMoneySizeOtherControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(insertMoneySizeOtherControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			esecuzione.insertMoneySizeOther.set(x);
			break;
		}
		System.out.print("Inserire un numero per indicare l'enumerativo per standardOrOther "
				+ esecuzione.MoneySizeSelection_lista.toString() + ":  ");
		Scanner standardOrOtherinput = new Scanner(System.in);
		for (;;) {
			int x;
			String standardOrOtherControllo = standardOrOtherinput.nextLine();
			if (standardOrOtherControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(standardOrOtherControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			esecuzione.standardOrOther.set(esecuzione.MoneySizeSelection_lista.get(x - 1));
			break;
		}
	}

	public static void main(String[] args) {
		System.out.println("INFO - file java creto e tradotto dal file originale ATM3.asm");
		System.out.println("Inizio esecuzione del file ATM3.java\n\n");
		ATM3 esecuzione = new ATM3();
		String continuare = "no";
		int stato = 0;
		stato++;
		System.out.println("INITIAL STATE: ");
		do {
			System.out.println("<State " + stato + " (controlled)>");
			//Aggiornamento valori dell'ASM e inserimento dati monitorati
			printControlled(esecuzione);
			askMonitored(esecuzione);
			esecuzione.UpdateASM();
			System.out.println("</State " + stato + " (controlled)>");
			System.out.println("\n<Stato attuale>");
			printControlled(esecuzione);
			System.out.print("Vuoi continuare? (yes/no)  ");
			Scanner input = new Scanner(System.in);
			continuare = input.next();
			stato++;
		} while (continuare.contentEquals("yes") || continuare.contentEquals("Yes") || continuare.contentEquals("YES"));
		System.out.println("FINAL STATE:");
		//Valori finale delle variabili
		printControlled(esecuzione);
		System.out.println("esecuzione terminata");
	}
}
