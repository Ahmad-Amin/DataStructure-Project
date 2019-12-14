package Practice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class InvertIndex {
	HashMap<String,String> map = new HashMap<String,String>();  // Creating the object of HashMpa with a global scope

	
public void ReadIndexFromFile(String filepath,String fileName) throws FileNotFoundException, IOException, ParseException {
		
		Object obj = new JSONParser().parse(new FileReader(filepath));
		JSONObject jo = (JSONObject) obj;
		String key;
		
		
		Map ForwardIndex = (Map) jo.get("Forward Index");
		Iterator<Map.Entry> itr1 = ForwardIndex.entrySet().iterator();   // creating the iterator to iterator through the each value of the key from JSON file
		
		while(itr1.hasNext()) {     // condition to check whether the file has thesome content left or not
			
			Map.Entry pair = itr1.next();
			key = (String) pair.getKey();  // getting the value from JSON file

			
			/*This whole block is to check whether the element is in the hashMap or not so that we can continously 
			 * check for the documents which have same words*/
			
			if(map.containsKey(key)) { 				// Goes into this block if hashMap contains the key 
				String Doc = map.get(key);        
				map.put(key, Doc+","+fileName);		// append the new Filename if the the key already has the value
			}
			else {
				String Doc = map.get(key);
				map.put(key, fileName);				// it will simply add the fileName as value and the word as a key
			}
			
//			------------------------------------------------------------------------------------------------------------
			
		}
	
	}

public void writeIntoFile(HashMap<String,String>hMap) throws FileNotFoundException {
		
		JSONObject jo = new JSONObject();
		hMap.forEach((k,v) -> jo.put(k,v));   // Write all the data from HashMap into JSON format using JSON Object
		String fileName = "InvertedIndex";
		PrintWriter pw = new PrintWriter("C:/Users/Ahmad/Desktop/Inverted-Index/"+fileName+".json");  // Address of the file Which will contain all the inverted index
		
		pw.write(jo.toJSONString());   // to write into the file
		pw.flush();
		pw.close();		// Close the printWriter 
	}

public void print() throws FileNotFoundException {
		InvertIndex write = new InvertIndex();
		write.writeIntoFile(map);		// Calling the function to write into the file
		
		/* The above block is just to see the output in to code editor*/
		
//		for(Map.Entry<String, String> entry : map.entrySet()) {
//			
//			System.out.println("key = "+entry.getKey()+", Value = "+entry.getValue());
//		}

	}
	

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		
		InvertIndex ii = new InvertIndex();
		
		File folder = new File("C:/Users/Ahmad/Desktop/Forward-Index/");
		File[] listOfFiles = folder.listFiles();			// Creating the array which contains all the files from the given directory folder
		
		for(File file:listOfFiles) {			// iterator through each file one-by-one
			if(file.isFile()){
				String fileName = file.getName();		// Getting the name of the File
				String filepath = "C:/Users/Ahmad/Desktop/Forward-Index/"+fileName;		// the path in the PC where the Forward Index is present
				ii.ReadIndexFromFile(filepath, fileName);	// calling out the function to read the Forward index file and do futher functionalities
			}
		}
		ii.print();	// To print the data and to store the data in to file

	}

}
