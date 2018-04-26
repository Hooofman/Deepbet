package gui;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Class to write settings to a text file.
 * 
 * @author Sven Lindqvist
 *
 */
public class WriteToFile {
	/**
	 * 
	 * @param str
	 *            The data to be saved in a text file.
	 * @param filePath
	 *            The directory to where the data is to be saved.
	 */
	public static void write(String str, String filePath) {
		String[] array = str.split(",", 0);
		try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))) {
			for (String s : array) {
				bos.write(s);
				bos.newLine();
				System.out.println(s);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Filepath not found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
