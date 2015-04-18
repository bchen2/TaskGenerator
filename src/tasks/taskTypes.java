package tasks;


/*
 *Create 1 file each for Easy, Medium and Hard Tasks.
 *Add Task Difficulty to the Task Details : Example -> TaskDifficulty=1
 * */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class taskTypes {

	public static void main(String[] args) throws IOException {

		taskTypes T = new taskTypes();
		T.CreateTasks(46510);
		System.out.println("done");
		
	}//end main
	
	
	public void CreateTasks(int TotalNumberOfTasks){
		
		String filename = "20choose5(15504 ways).txt";
		Scanner s=null; 
		String line="";

		try {
			s = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//put every thing in one line then separate them and construct an array
		ArrayList<String[]> list2 = new ArrayList<String[]>();
		while(s.hasNext()) {
			line = s.nextLine().replaceAll("\\s+",  ",");
		   String eachline[]=line.split(",");
			list2.add(eachline);	
		}		
		s.close(); 

		try {
			PrintWriter out = new PrintWriter("config_types.properties");			
			String result="";

			//change the i to adjust task number
			int i=1;
			
			String TaskType = "task_Type = " + i;
			String NumOfSubtasks = "Num_Subtasks"+i+"= 5";
			String Tasks= "", SubTasks="";
			 
			
			for (String[] array: list2){
				
				 TaskType = "task_Type = " + i;
				 NumOfSubtasks = "Num_Subtasks"+i+"= 5";
				 
				SubTasks = array[0]+","+array[1]+","+array[2]+","+array[3]+","+array[4];;
			//	System.out.println(SubTasks);
				
				if(i<=TotalNumberOfTasks/3){
					Tasks = GetHardTaskSpec(i,5);	
				}
				
				else if(i >TotalNumberOfTasks/3 && i <= 2 * TotalNumberOfTasks/3 ){
					Tasks = GetAverageTaskSpec(i,5);	
				}
				
				else{

					Tasks = GetEasyTaskSpec(i,5);	
				}
				
				result=result+TaskType+"\n";
				result=result+ NumOfSubtasks+"\n";
				result=result+"Subtasks"+i+" = "+ SubTasks+       "\n";
				result = result + Tasks + "\n";
				i++;	
				out.write(result);
				result = "";
			}
			

			out.close();
		} 
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace(); }
		
	}
	
	
	public String GetHardTaskSpec(int i, int NumOfSubTasks){
		
		int NumOfHardSubTasksHelper = (int) Math.ceil(NumOfSubTasks/3.0);
		int NumOfHardSubTasks =  NumOfHardSubTasksHelper + (int) ( Math.random() * (NumOfSubTasks- NumOfHardSubTasksHelper));  
		int NumOfRemainingSubTasks = NumOfSubTasks - NumOfHardSubTasks; 	
		int NumOfAverageSubTasks = (int) (Math.random() * NumOfRemainingSubTasks);
		int NumOfEasySubTasks = NumOfRemainingSubTasks - NumOfAverageSubTasks;		
		String HardTask = MakeTaskHelper(i,NumOfSubTasks,NumOfHardSubTasks, NumOfAverageSubTasks,NumOfEasySubTasks ); // Get A HardTaskSpec	
		return HardTask;
	}
	
	public String GetAverageTaskSpec(int i, int NumOfSubTasks){
		
		int NumOfHardSubTasks = (int) (0.99 * Math.random()* NumOfSubTasks/3.0); 
		int NumOfRemainingSubTasks = NumOfSubTasks - NumOfHardSubTasks; 
		
		int NumOfAverageSubTasksHelper = NumOfHardSubTasks/3 ;
		int NumOfAverageSubTasks = 	NumOfAverageSubTasksHelper + (int) ( Math.random() * (NumOfRemainingSubTasks- NumOfAverageSubTasksHelper));   ;  // Make the task Average SubTask Dominant  ;  
		
		int NumOfEasySubTasks = NumOfRemainingSubTasks - NumOfAverageSubTasks;
		String AverageTask = MakeTaskHelper(i,NumOfSubTasks,NumOfHardSubTasks, NumOfAverageSubTasks,NumOfEasySubTasks ); // Get an AverageTaskSpec
		return AverageTask;
	}
	
	
	public String GetEasyTaskSpec(int i, int NumOfSubTasks){
		int NumOfHardSubTasks = (int) (0.99 * Math.random()* NumOfSubTasks/3.0); 
		int NumOfAverageSubTasks = (int) (0.99 * Math.random()* NumOfSubTasks/3.0); 
		int NumOfEasySubTasks = NumOfSubTasks - (NumOfHardSubTasks+NumOfAverageSubTasks); 
		String EasyTask = MakeTaskHelper(i,NumOfSubTasks,NumOfHardSubTasks, NumOfAverageSubTasks,NumOfEasySubTasks ); // Get an EasyTaskSpec
		return EasyTask;
	}
	
	public String MakeTaskHelper(int TaskNo, int NumOfSubTasks, int NumOfHardSubTasks, int NumOfAverageSubTasks, int NumOfEasySubTasks){
	
		double [] SubTaskQuality = new double[NumOfSubTasks];
		int [] NumOfAgents = new int[NumOfSubTasks];
		int i=0;
		
		while(i<NumOfHardSubTasks){
			SubTasks Temp = new SubTasks();
			Temp.MakeHardSubtask(900, 0.7, 1.0);
			SubTaskQuality[i] = Temp.getQuality();
			NumOfAgents[i] = Temp.getNumberOfAgents();
			i++;
		}
		while(i<NumOfHardSubTasks+NumOfAverageSubTasks){
			SubTasks Temp = new SubTasks();
			Temp.MakeAverageSubtask(900, 0.4, 0.6);
			SubTaskQuality[i] = Temp.getQuality();
			NumOfAgents[i] = Temp.getNumberOfAgents();
			i++;
		}
		while(i<NumOfSubTasks){
			SubTasks Temp = new SubTasks();
			Temp.MakeEasySubtask(900, 0.1, 0.3);
			SubTaskQuality[i] = Temp.getQuality();
			NumOfAgents[i] = Temp.getNumberOfAgents();
			i++;
		}
		
		String AgentSpec = "", QualitySpec="";
		AgentSpec = "Num_Agents"+ TaskNo + " = " ;
		QualitySpec = "Quality"+ TaskNo + " = " ;
		int j=0;
		
		while(j< NumOfSubTasks){
			AgentSpec += NumOfAgents[j] + ",";
			QualitySpec += SubTaskQuality[j] + ",";
			j++;
		}
		
		AgentSpec = AgentSpec.substring(0, AgentSpec.length()-1) + "\n";
		QualitySpec = QualitySpec.substring(0, QualitySpec.length()-1) + "\n";
		String TaskSpec = AgentSpec + QualitySpec;
		return TaskSpec;
	}
	
	
	public static double round1d(double a){
		double roundoff=Math.round(a*10.0)/10.0;
		return roundoff;
	}//end of method
	
}//end class










	










