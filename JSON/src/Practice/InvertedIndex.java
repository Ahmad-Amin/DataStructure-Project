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
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class InvertedIndex {
	
	public boolean checkFileExist(String keyValueStart,String Name) throws IOException, ParseException {
		
		File folder = new File("C:/Users/Ahmad/Desktop/Inverted-Index/");
		File[] listOfFiles = folder.listFiles();
		
		for( File file:listOfFiles) {
			String fileName = file.getName();
			String fileNameStart = fileName.substring(0,1);
			
			if(file.isFile() && fileNameStart.equalsIgnoreCase(keyValueStart)) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
		
	
	public void CreateinvertedFile(HashMap<String,List<String>> hMap) throws IOException, ParseException {
		
		InvertedIndex iic = new InvertedIndex();
		//String[] checkFile = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		boolean check;
		for(Map.Entry<String, List<String>> entry : hMap.entrySet()) {
		
			String keyValue = entry.getKey();
			String keyValueStart = keyValue.substring(0,1);
			check = iic.checkFileExist(keyValueStart,keyValue);	
			if(!(check)) {
				System.out.println("File Not present");
				iic.CreateAndWriteIntoFile(hMap,keyValue);
			}
			else if (check){
				System.out.println("File is Present");
				iic.AlterTheFile(hMap, keyValue); 
			}
			
		//	System.out.println("key = "+entry.getKey()+", Value = "+entry.getValue());
			
		}
		
	}
	
	public void ReadIndexFromFile(String filepath,String fileName) throws FileNotFoundException, IOException, ParseException {
		
		InvertedIndex ii1 = new InvertedIndex();
		String key,inttoStr;
		long howManyTimes;
		
		Object obj = new JSONParser().parse(new FileReader(filepath));
		JSONObject jo = (JSONObject) obj;
		
		HashMap<String,List<String>> map = new HashMap<String,List<String>>();
		Map ForwardIndex = (Map) jo.get("Forward Index");
		Iterator<Map.Entry> itr1 = ForwardIndex.entrySet().iterator();
		List<String> valInFile;
		
		while(itr1.hasNext()) {
			valInFile = new ArrayList<String>();
			Map.Entry pair = itr1.next();
			key = (String) pair.getKey();
			howManyTimes = (long) pair.getValue();
			valInFile.add(fileName);
			inttoStr = Long.toString(howManyTimes);
			valInFile.add(inttoStr);
			map.put(key,valInFile);
		}
		//System.out.println("--->"+map);
		ii1.CreateinvertedFile(map);
	}
	
	public void AlterTheFile(HashMap<String, List<String>> hMap, String keyValue) throws FileNotFoundException {
		
		
	}
	
	public void CreateAndWriteIntoFile(HashMap<String, List<String>> hMap, String keyValue) throws FileNotFoundException {
		JSONObject jo = new JSONObject();
		List<String> valSetOne = new ArrayList<String>();
		
		PrintWriter pw = new PrintWriter("C:/Users/Ahmad/Desktop/Inverted-Index/"+keyValue.substring(0,1)+".json");
		valSetOne = hMap.get(keyValue);
		jo.put(keyValue, valSetOne);
		pw.write(jo.toJSONString());
		pw.flush();
		pw.close();
		System.out.println("-->"+keyValue.substring(0,1)+" is Done !");
		
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		Scanner sc = new Scanner(System.in);
		InvertedIndex ii = new InvertedIndex();
		
		File folder = new File("C:/Users/Ahmad/Desktop/Forward-Index/");
		File[] listOfFiles = folder.listFiles();
		int count = 0;
		
		for(File file:listOfFiles) {
			if(count==3) {
				break;
			}
			if(file.isFile()){
				String fileName = file.getName();
				String filepath = "C:/Users/Ahmad/Desktop/Forward-Index/"+fileName;
				ii.ReadIndexFromFile(filepath, fileName);
				count++;
			}
		}
					
		
	}

}
