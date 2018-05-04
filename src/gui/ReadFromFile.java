package gui;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Class to read Strings from a text file.
 * 
 * @author Sven Lindqvist
 *
 */
public class ReadFromFile {
	/**
	 * Reads lines of text from a text file. Each word is separated with a ','.
	 * 
	 * @param filePath
	 *            Which file to be read.
	 * @return A String containing the data from the text file.
	 */
	public static String readFromFile(String filePath) {
		String data = "";
		String temp = "";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
			temp = br.readLine();
			while (temp != null) {
				data += temp + ",";
				temp = br.readLine();
				// System.out.println(data);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
}
