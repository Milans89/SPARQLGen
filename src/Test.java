import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new FileReader("Ontology.txt"));
			String zeile = null;
			while ((zeile = in.readLine()) != null) {
				System.out.println("Gelesene Zeile: " + zeile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
