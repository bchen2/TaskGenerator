package tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class taskList_withSameTaskOrder {

	public static void main(String[] args) {
		//enter taskOpenness and totalNumOfTasks here
		double taskOpenness = 0.9;
		int totalNumOfTasks=15504;		
		ArrayList<Integer> allAvilableTasks = new ArrayList<Integer>();
		
		//initialize allAvilableTasks		
		/**read in file which has the task order*/
		String fileName = "config_task1.0.properties";
		Scanner s = null;
		String line="";
	
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (s.hasNext()){//from 1 to total number of tasks
			line=s.next();
			String[] Token=line.split("=");
			allAvilableTasks .add(Integer.parseInt(Token[1]));
		}
		ArrayList<Integer> chosenTasks = new ArrayList<Integer>();
		int initialTask=allAvilableTasks.get(0);
		chosenTasks.add(initialTask);//Initialize the first task 
		allAvilableTasks.remove(initialTask);		
		int allAvilableTasksIndexCount=0;
		//generating task lists according to taskOpenness
		//*************************************************
		for (int i=1;i<totalNumOfTasks;i++){
			//starting from 1 since initially there is one task in chosenTasks
			double x=Math.random();
			if (x>taskOpenness){
				//choose a task from the chosenTask arrayList (old task)
				int randomIndex=(int) (Math.random()*chosenTasks.size());						
				//add this task to chosen tasks
				chosenTasks.add(chosenTasks.get(randomIndex));								 
			}
			else{
				//choose a new task from allAvilableTasks in order (new task)						
				//add this task to chosen tasks
				chosenTasks.add(allAvilableTasks.get(allAvilableTasksIndexCount));				
				//increase the count
				allAvilableTasksIndexCount++;				
			}
		}
		System.out.println("Choosen task size ="+chosenTasks.size());	
		//output task list into a file
		//***************************************************
		try {
			PrintWriter out = new PrintWriter("config_task"+taskOpenness+"SameOrder.properties");
			String result="";
			int i=0;
			for (int chosenTask: chosenTasks){
				i++;				
				result=result+i+"="+chosenTask+"\n";
			}
			out.write(result);
			out.close();
		} 
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace(); 
			}
	}//end main
}//end class
