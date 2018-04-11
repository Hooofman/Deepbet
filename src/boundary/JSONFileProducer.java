package boundary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

import com.google.gson.Gson;

public class JSONFileProducer {

	public static void writeToFile(String league, int year, String id, JSONObject jsonObj) throws FileNotFoundException {
		 Gson gson = new Gson();
		  
		  // convert java object to JSON format,
		  // and returned as JSON formatted string
		  String json = gson.toJson(jsonObj);
		  
		  try {
		   //write converted json data to a file named "CountryGSON.json"
		   FileWriter writer = new FileWriter("JSON/"+league+"/"+year+"_"+id+".json");
		   writer.write(json);
		   writer.close();
		  
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	public static JSONObject readFromFile(String league, int year, String id) {
		Gson gson = new Gson();
		JSONObject json = null;
		  try {
		  
		   System.out.println("Reading JSON from a file");
		   System.out.println("----------------------------");
		   
		   BufferedReader br = new BufferedReader(
		     new FileReader("JSON/"+league+"/"+year+"_"+id+".json"));
		   
		    //convert the json string back to object
		   json = gson.fromJson(br, JSONObject.class);
		  } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			  
		  }
		  
return json;
	}
}
