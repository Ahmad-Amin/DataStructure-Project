package Practice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class InvertedIndexv2 {
	
	HashMap<String,Object> map = new HashMap();
	Vector <String> valuesOfInvertedIndexKey = new Vector<String>();

	Vector <String> Vec = new Vector<String>();
	
	public boolean FileExistOrNot(char keyStartingWord) {
		
		File folder = new File("C:/Users/Ahmad/Desktop/Inverted-Index-v2/");
		File[] listOfFiles = folder.listFiles();
		
		for(File file:listOfFiles) {
			String fileName = file.getName();
			char fileNameStartingWord = fileName.charAt(0);
			if(keyStartingWord == fileNameStartingWord) {
				return true;
			}
		}
		return false;
	}

	public void OpenExistingFile(char keyStartingWord, String key, String fileName, String filePath) throws IOException, ParseException {

		
		boolean wordPresent = false;
		JSONParser  parser = new JSONParser();
		JSONObject record = null;
		
		record = (JSONObject) parser.parse(new FileReader("C:/Users/Ahmad/Desktop/Inverted-Index-v2/"+keyStartingWord+".json"));
		
		InvertedIndexv2 v3 = new InvertedIndexv2();
			
		//	System.out.println(key+" <----> "+Vec.elementAt(i));
		if(record.containsKey(key)) {
			
			JSONParser parser1 = new JSONParser();
			JSONObject record1 = null;
			
			record1 = (JSONObject) parser1.parse(new FileReader("C:/Users/Ahmad/Desktop/Inverted-Index-v2/"+keyStartingWord+".json"));
			
			String newFilePath = "C:/Users/Ahmad/Desktop/blog/blog dataset/bDone"+fileName;
			
			JSONArray r1 = (JSONArray) record1.get(key);
			JSONObject obj1 = new JSONObject();
			
			obj1.put("Document", fileName);
			obj1.put("filePath",newFilePath);
			
			r1.add(obj1);
			
			FileWriter file1 = new FileWriter("C:/Users/Ahmad/Desktop/Inverted-Index-v2/"+keyStartingWord+".json");
		    file1.write(record1.toJSONString());
		        
		    file1.flush();
		    file1.close();
					
		}
		else {
			
			System.out.println("Not contained the key");
			JSONParser  parser2 = new JSONParser();
			JSONObject record2 = null;
			record2 = (JSONObject) parser2.parse(new FileReader("C:/Users/Ahmad/Desktop/Inverted-Index-v2/"+keyStartingWord+".json"));
			JSONArray r2 = new JSONArray();
			JSONObject obj2 = new JSONObject();
			
			String newFilePath = "C:/Users/Ahmad/Desktop/blog/blog dataset/bDone"+fileName;
			Map m2 = new LinkedHashMap();
			
			m2.put("Document",fileName);
			m2.put("filePath",newFilePath);
			r2.add(m2);
			record2.put(key,r2);
			
			
			FileWriter file2 = new FileWriter("C:/Users/Ahmad/Desktop/Inverted-Index-v2/"+keyStartingWord+".json");
			file2.write(record2.toJSONString());
			
			file2.flush();
			file2.close();
		}			
		
		System.out.println(wordPresent);
		
		
		
		
	//	v3.writeIntoFile(map,key,filePath,fileName);
		
	//	System.out.println(map);
	//	System.out.println(Vec.size());
	//	v3.print(map);
			
	}
	
	public void writeIntoFile(HashMap <String,Object> map,String key,String filePath,String fileName) {
		
		
	}
	
	
	public void print(HashMap <String,Object> map) {
		
		Iterator itr1 = map.entrySet().iterator();
		int count=0;
		System.out.println(map);
//		for(Entry<String, Object> key:map.entrySet()) {
//			System.out.println("The value of Count is : "+count);
//			count++;
//			System.out.println(key.getKey()+" : "+key.getValue());
//		}

	}
	
	public void CreateAndWriteIntoNewFile(char keyStartingWord, String key, String fileName, String filePath) throws IOException {
		
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		String newFilePath = "C:/Users/Ahmad/Desktop/blog/blog dataset/bDone"+fileName;
		
		Map data1 = new LinkedHashMap();
			
		data1.put("Document",fileName);
		data1.put("filePath",newFilePath);
		
		ja.add(data1);
		jo.put(key,ja);
		
		PrintWriter pw = new PrintWriter("C:/Users/Ahmad/Desktop/Inverted-Index-v2/"+keyStartingWord+".json");
		pw.write(jo.toJSONString());
		pw.flush();
		pw.close();
	}
	
	public void ReadIndexFromFile(String filePath,String fileName) throws FileNotFoundException, IOException, ParseException {
		
		Object obj = new JSONParser().parse(new FileReader(filePath));
		InvertedIndexv2 iiv2 = new InvertedIndexv2();
		JSONObject jo = (JSONObject) obj;
		String key;
		
		Map ForwardIndex = (Map) jo.get("Forward Index");
		Iterator<Map.Entry> itr = ForwardIndex.entrySet().iterator();
		while(itr.hasNext()) {
			
			Map.Entry pair = itr.next();
			key = (String) pair.getKey();
			char keyStartingWord = key.charAt(0);
			//System.out.println(key);

			int asciValue = (int) keyStartingWord;
			asciValue = asciValue - 97;
									
			boolean checkResult = iiv2.FileExistOrNot(keyStartingWord);
			if(checkResult) {
	//			System.out.println(key + "\t File Already Exists");
				iiv2.OpenExistingFile(keyStartingWord,key,fileName,filePath);
				
			}
			else {
	//			System.out.println(key + "\t File Not Exists");
				
				iiv2.CreateAndWriteIntoNewFile(keyStartingWord,key,fileName,filePath);
			}	
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		InvertedIndexv2 iiv2 = new InvertedIndexv2();
		
//		HashMap<String, String> map = new HashMap<String,String >[10]();
		File folder = new File("C:/Users/Ahmad/Desktop/Forward-Index/");
		File[] listOfFiles = folder.listFiles();
		int count=0;
		
		for(File file:listOfFiles) {
			if(count == 15) {
				break;
			}	
			String fileName = file.getName();
			String filePath = "C:/Users/Ahmad/Desktop/Forward-Index/"+fileName;
			
			iiv2.ReadIndexFromFile(filePath,fileName);
			count++;
		}
		System.out.println("done");
	//	iiv2.print();
	}

}
