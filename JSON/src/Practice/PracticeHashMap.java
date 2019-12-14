package Practice;

import java.util.*;

public class PracticeHashMap {

	public static void main(String[] args) {
		
		HashMap <String,List<String>> map = new HashMap<String,List<String>>();
		Scanner sc = new Scanner(System.in);
		String Name;
		int value1 = 0;
		String value2 = null;
		String Stringvalue1 = null;
		System.out.println("Enter the Key");
		Name = sc.next();
		System.out.println("Enter the 1st value (Integer)");
		value1 = sc.nextInt();
		System.out.println("Enter the 2nd Value (String)");
		value2 = sc.next();
		Stringvalue1 = Integer.toString(value1);
		
		List<String> valSetOne = new ArrayList<String>();
		List<String> valSetOne1 = new ArrayList<String>();
		valSetOne.add(value2);
		valSetOne.add(Stringvalue1);
		map.put(Name,valSetOne);
		valSetOne1 = map.get(Name);

		System.out.println("The values in HashMap is :"+valSetOne1);
	}

}
