
// LGS_GM_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM
import java.util.Scanner;

class LGS_GM_Exe {
	static void printControlled(LGS_GM esecuzione) {
		System.out.println("doors = " + esecuzione.doors.oldValue.name());
		System.out.println("gears = " + esecuzione.gears.oldValue.name());
	}

	static void askMonitored(LGS_GM esecuzione) {
		System.out.print("Inserire un numero per indicare l'enumerativo per handle "
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
	}

	public static void main(String[] args) {
		System.out.println("INFO - file java creto e tradotto dal file originale LGS_GM.asm");
		System.out.println("Inizio esecuzione del file LGS_GM.java\n\n");
		LGS_GM esecuzione = new LGS_GM();
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
