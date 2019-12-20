package Practice;

import java.io.*;
import java.util.*;
import java.security.Key;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Practice {
	
	
	// Function to remove all the Soping words that i picked up from intenet
	public String removeStopWords(String str) throws FileNotFoundException {
		
		Vector v = new Vector();			// Creating object of two vector for dynamic memory
		Vector regex = new Vector();
		String [] paragraphs = str.split(" ");		// Spliting the input string into array of String
		
		File file = new File("C:/Users/Ahmad/Desktop/stoping words.txt"); 	// filepath where my stoping words file is located
		Scanner sc = new Scanner(file);	
		while (sc.hasNextLine()) 	// check the end of the file
		     v.add(sc.nextLine());	// adding the values one by one into vector to check for the words
		for(int i=0;i<v.size();i++) {
			regex.add("\\s*\\b"+v.elementAt(i)+"\\b\\s*");		// creating the regex with removing all spaces and blanks for rmove wor
		}

		for(int i=0;i<regex.size();i++) {
			String removeWord = (String) regex.elementAt(i);	// checking one by one the word with the regex and if equals then remove it
			str = str.replaceAll(removeWord," ");		
		}
		return str;		// returning the string by removing all the stoping words
		
	} 

	// Function the Write into the File
	public void writingIntoFile (HashMap <String,Integer>hMap, String fileName) throws IOException {
		JSONObject jo = new JSONObject();	// Creating the JSON Object
		Map m = new LinkedHashMap(hMap.size());	// Creating the obkect of LinkedHashMap to create multiple values against single key
	
		hMap.forEach((k,v) -> m.put(k,v));	// putting the values from hashMap to the data in JSON file
		
		jo.put("Forward Index",m);
		PrintWriter pw = new PrintWriter("C:/Users/Ahmad/Desktop/Forward-Index/"+fileName);	 // Create the object to write into the file
		pw.write(jo.toJSONString());
		pw.flush();
		pw.close();
	
	}

	// function to parse the JSON file 
	public void JSONFileParser(String filepath,String fileName) throws FileNotFoundException, IOException, ParseException {
		
		HashMap <String, Integer> hMap = new HashMap<String, Integer>();
		Object obj = new JSONParser().parse(new FileReader(filepath));
		JSONObject jo = (JSONObject) obj;
		Practice pa1 = new Practice();
		Set keys  =hMap.keySet();
		ArrayList<String> values = new ArrayList<String>();
		
		String content = (String) jo.get("text");
		String textWithoutStopingWords;
		content = content.toLowerCase();  // Lover Case all the the content
		
		textWithoutStopingWords = pa1.removeStopWords(content);
		textWithoutStopingWords = textWithoutStopingWords.replaceAll("[^a-zA-Z ]", "");			// removing all the data from the string excpet from alphabets
		textWithoutStopingWords = textWithoutStopingWords.trim().replaceAll(" +", " ");			// remove all the white space from the start of the string	
		
		
		String [] paragraphs = textWithoutStopingWords.split(" ");		//Split the String into the array on space
	
		/* the block below is to keep the data of how many time does the data appears in the document*/
		for(String paragraph : paragraphs) {
			if(hMap.containsKey(paragraph)) {
				int count = hMap.get(paragraph);
				hMap.put(paragraph, count + 1);
			}
			else {
				hMap.put(paragraph, 1);
			}
		}
//		---------------------------------------------------------------------------------------------
		pa1.writingIntoFile(hMap, fileName); // calling the functionto write into file

	}
	

	public static void main(String[] args) throws IOException, ParseException {
		
		Practice pa = new Practice();
		File folder = new File("C:/Users/Ahmad/desktop/blog/blog dataset/"); 	// folder director where my dataset is present
		File[] listOfFiles = folder.listFiles();	// Contain the data of the files placing the foldre
		int count = 0;
		
		for(File file : listOfFiles) {		//iterator through all files one-by-one
			
			String FileName = file.getName();
			
			
			/* This is just to resist the data to only create the index of upto 15 documents for the assignment*/
			//if(count == 25) {
			//	break;
			//}
			if(file.isFile()) {
				String fileName = file.getName();
				String firstfiveLetters = fileName.substring(0,5);
				
				if(!(firstfiveLetters.equalsIgnoreCase("bDone"))) {
					
				
				String newfileName = "bDone"+file.getName();

				String filepath = "C:/Users/Ahmad/desktop/blog/blog dataset/"+fileName;
				String newfilepath = "C:/Users/Ahmad/desktop/blog/blog dataset/"+newfileName;
				File f = new File ("C:/Users/Ahmad/desktop/blog/blog dataset/"+fileName);
				
				f.renameTo(new File(newfilepath));

			//	System.out.println(newfileName);
				pa.JSONFileParser(newfilepath,fileName);
				System.out.println(fileName+"is done");
				}
				else {
					System.out.println(fileName+ " Forward Index Already Created");
				}
			//	count++;
				
			}
		}


	}

}



