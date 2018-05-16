package boundary;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Class to write settings to a text file.
 * 
 * @author Sven Lindqvist, Oscar Malmqvist
 *
 */
public class WriteToFile {
	/**
	 * 
	 * @param str The data to be saved in a text file.
	 * @param filePath The directory to where the data is to be saved.
	 */
	public static void write(String str, String filePath) {
		String[] array = str.split(",", 0);
		try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))) {
			for (String s : array) {
				bos.write(s);
				bos.newLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Filepath not found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes text to a txt-file
	 * 
	 * @param txt the text to write
	 */
	public static void appendTxt(String txt) {
		try (FileWriter fw = new FileWriter(System.getProperty("user.dir")+"/SavedFiles/testMatcher.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(txt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
