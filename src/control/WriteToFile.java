package control;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteToFile {

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
