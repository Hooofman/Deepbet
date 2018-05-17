package oldUnused;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

import com.google.gson.Gson;

import boundary.FetchApi;

public class JSONFileProducer {

	public static void writeToFile(String id, String sort, JSONObject jsonObj) throws FileNotFoundException {
		 Gson gson = new Gson();
		  
		  // convert java object to JSON format,
		  // and returned as JSON formatted string
		  String json = gson.toJson(jsonObj);
		  
		  try {
		   //write converted json data to a file named "CountryGSON.json"
		   FileWriter writer = new FileWriter("JSON/"+id+"_"+sort+".json");
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
	public static void main(String[] args) {
		try {
			String[] pl = {"113", "114", "4", "301", "341", "354", "398", "426", "445"};
			for(int i = 0; i < pl.length; i++) {
				JSONFileProducer.writeToFile(pl[i], "competition", FetchApi.getJsonSeason(Integer.parseInt(pl[i])));
				
				JSONFileProducer.writeToFile(pl[i], "matches", FetchApi.getJsonMatches(Integer.parseInt(pl[i])));
				
				JSONFileProducer.writeToFile(pl[i], "teams", FetchApi.getJsonTeams(Integer.parseInt(pl[i])));
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
