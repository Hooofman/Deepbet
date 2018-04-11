package boundary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class used for writing made calculations to a txt-file
 * @author Oscar
 *
 */
public class WriteToFile {

	/**
	 * Writes text to a txt-file
	 * @param txt the text to write
	 */
	public static void appendTxt(String txt) {
		try (FileWriter fw = new FileWriter("testMatcher.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(txt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
