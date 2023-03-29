
// FLIP_FLOP_0_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM
import java.util.Scanner;

class FLIP_FLOP_0_Exe {
	static void printControlled(FLIP_FLOP_0 esecuzione) {
		System.out.println("ctl_state = " + esecuzione.ctl_state.get().value);
	}

	static void askMonitored(FLIP_FLOP_0 esecuzione) {
		System.out.print("Inserire un valore booleano per high (true/false):  ");
		Scanner highinput = new Scanner(System.in);
		for (;;) {
			Boolean y;
			String highControllo = highinput.nextLine();
			if (highControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(highControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.high.set(y);
			break;
		}
		System.out.print("Inserire un valore booleano per low (true/false):  ");
		Scanner lowinput = new Scanner(System.in);
		for (;;) {
			Boolean y;
			String lowControllo = lowinput.nextLine();
			if (lowControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(lowControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.low.set(y);
			break;
		}
	}

	public static void main(String[] args) {
		System.out.println("INFO - file java creto e tradotto dal file originale FLIP_FLOP_0.asm");
		System.out.println("Inizio esecuzione del file FLIP_FLOP_0.java\n\n");
		FLIP_FLOP_0 esecuzione = new FLIP_FLOP_0();
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
