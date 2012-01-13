import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		schreibe("Hello Wörld", "single");

	}



private static void schreibe(String text, String sl) {
	String dateiName = null;
    FileOutputStream schreibeStrom;
    int name = 0;
    boolean exist;
    do {
    	dateiName = sl + name + ".txt";
	    exist = (new File(dateiName)).exists();
    } while (exist == true);
	try {
		schreibeStrom = new FileOutputStream(dateiName);
		for (int i=0; i < text.length(); i++){
	    	schreibeStrom.write((byte)text.charAt(i));
	    }
	    schreibeStrom.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}