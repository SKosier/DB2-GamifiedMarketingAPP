package it.polimi.db.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BannedWords {
	
	private	List<String> bannedList = new ArrayList<String>();
	
	public Integer initializeList() {
		try{
			Scanner s = new Scanner(new File("D:\\Docs\\Uni\\Data_Bases_2\\Project\\GamifiedMarketingAPP\\CurseWordsListv2.txt"));
			//while (s.hasNext()){
			//   bannedList.add(s.next());
			//}
			while (s.hasNextLine()){
		    	bannedList.add(s.nextLine());
				}
			s.close();
			return bannedList.size();
		}
    	catch(FileNotFoundException ex)
    	{
    	System.out.println("File does Not Exist Please Try Again: ");
    	}


		return -1;
	}
	
	public void listCheckPrint()
	{
		for (int i = 0; i < bannedList.size(); i++)  
			System.out.print(bannedList.get(i) + " ");
    }

}
