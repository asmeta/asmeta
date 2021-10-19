
public class MainClass {
	public static void main(String[] args) throws InterruptedException {
		int i=2;
		COMPortAdapter adapter = new COMPortAdapter("COM5");

		Thread.sleep(4000);

		
		
		adapter.sendData("CIAO SONO UNA STRINGA\n");

		String received = "";
			while (!received.contains("1")) {
				received = adapter.readData();
				if (received!="")
					System.out.println("***" + received);
				Thread.sleep(4000);
				adapter.sendData("CIAO SONO UNA STRINGA " + i +"\n");
				i++;
			}

		adapter.dispose();
	}
}
