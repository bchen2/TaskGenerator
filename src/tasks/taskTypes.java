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
		T.CreateTasks(46512);//15504 Hard tasks, 15504 average tasks and 15504 easy tasks, output in this order
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
			PrintWriter out1 = new PrintWriter("test_types.properties");	
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
				out1.write(TestTasks(Tasks)+"\n");
				
				result=result+TaskType+"\n";
				result=result+ NumOfSubtasks+"\n";
				result=result+"Subtasks"+i+" = "+ SubTasks+       "\n";
				result = result + Tasks + "\n";
				i++;	
				out.write(result);
				result = "";
			}
			

			out.close();
			out1.close();
		} 
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace(); }
		
	}
	
	
	public String GetHardTaskSpec(int i, int NumOfSubTasks){
		
		int NumOfHardSubTasksHelper = (int) Math.ceil(NumOfSubTasks/3.0);//gives 2, equal or more than 2 subtasks in a task, then this task is hard
		int NumOfHardSubTasks =  NumOfHardSubTasksHelper + (int) ( Math.random() * (NumOfSubTasks- NumOfHardSubTasksHelper+1));
//		System.out.println("Hard = "+NumOfHardSubTasks);
		int NumOfRemainingSubTasks = NumOfSubTasks - NumOfHardSubTasks; 	
//		System.out.println("remaining=" +NumOfRemainingSubTasks);
		
		int NumOfAverageSubTasks=0;
		int NumOfEasySubTasks=0;
		if (NumOfRemainingSubTasks!=0){
			 NumOfAverageSubTasks = (int) (Math.random() * NumOfRemainingSubTasks+1);
			 NumOfEasySubTasks = NumOfRemainingSubTasks - NumOfAverageSubTasks;	
		}
		
//		System.out.println("Easy = "+NumOfEasySubTasks);
//		System.out.println("@@ Hard ="+NumOfHardSubTasks+" Avg="+ NumOfAverageSubTasks+" Easy="+NumOfEasySubTasks);
		String HardTask = MakeTaskHelper(i,NumOfSubTasks,NumOfHardSubTasks, NumOfAverageSubTasks,NumOfEasySubTasks ); // Get A HardTaskSpec	
//		System.out.println(TestTasks(HardTask));
		return HardTask;
	}
	
	public String GetAverageTaskSpec(int i, int NumOfSubTasks){
		int NumOfHardSubTasksHelper = (int) Math.ceil(NumOfSubTasks/3.0);//gives 2, equal or more than 2 subtasks in a task, then this task is hard
		int NumOfHardSubTasks = (int) ( Math.random()* NumOfHardSubTasksHelper); //gives 0 or 1
		int NumOfRemainingSubTasks = NumOfSubTasks - NumOfHardSubTasks; 
		
		int NumOfAverageSubTasksHelper =  (int) Math.ceil(NumOfSubTasks/3.0);//gives 2
		int NumOfAverageSubTasks = 	NumOfAverageSubTasksHelper + (int) ( Math.random() * (NumOfRemainingSubTasks- NumOfAverageSubTasksHelper));   ;  // Make the task Average SubTask Dominant  ;  
		
		int NumOfEasySubTasks = NumOfRemainingSubTasks - NumOfAverageSubTasks;
//		System.out.println("@@ Hard ="+NumOfHardSubTasks+" Avg="+ NumOfAverageSubTasks+" Easy="+NumOfEasySubTasks);
		String AverageTask = MakeTaskHelper(i,NumOfSubTasks,NumOfHardSubTasks, NumOfAverageSubTasks,NumOfEasySubTasks ); // Get an AverageTaskSpec
//		System.out.println(TestTasks(AverageTask));
		return AverageTask;
	}
	
	
	public String GetEasyTaskSpec(int i, int NumOfSubTasks){
		int N_by_3 = (int) Math.ceil(NumOfSubTasks/3.0);//gives 2
		int NumOfHardSubTasks = (int) ( Math.random()* N_by_3); //gives 0,1
		int NumOfAverageSubTasks = (int) ( Math.random()* N_by_3);//gives 0 or 1
		int NumOfEasySubTasks = NumOfSubTasks - (NumOfHardSubTasks+NumOfAverageSubTasks); 
//		System.out.println("@@ Hard ="+NumOfHardSubTasks+" Avg="+ NumOfAverageSubTasks+" Easy="+NumOfEasySubTasks);
		String EasyTask = MakeTaskHelper(i,NumOfSubTasks,NumOfHardSubTasks, NumOfAverageSubTasks,NumOfEasySubTasks ); // Get an EasyTaskSpec
//		System.out.println(TestTasks(EasyTask));
		return EasyTask;
	}
	
	public String MakeTaskHelper(int TaskNo, int NumOfSubTasks, int NumOfHardSubTasks, int NumOfAverageSubTasks, int NumOfEasySubTasks){
//	System.out.println("#### MakeTaskHelper::  hard="+NumOfHardSubTasks+" average="+NumOfAverageSubTasks+" easy="+NumOfEasySubTasks);
		double [] SubTaskQuality = new double[NumOfSubTasks];
		int [] NumOfAgents = new int[NumOfSubTasks];
		int i=0;
		
		while(i<NumOfHardSubTasks){
//			System.out.println("Make Hard");
			SubTasks Temp = new SubTasks();
			Temp.MakeHardSubtask(900, 0.7, 1.0);
			SubTaskQuality[i] = Temp.getQuality();
			NumOfAgents[i] = Temp.getNumberOfAgents();
			i++;
		}
		while(i<NumOfHardSubTasks+NumOfAverageSubTasks){
//			System.out.println("Make Avg");
			SubTasks Temp = new SubTasks();
			Temp.MakeAverageSubtask(900, 0.4, 0.6);
			SubTaskQuality[i] = Temp.getQuality();
			NumOfAgents[i] = Temp.getNumberOfAgents();
			i++;
		}
		while(i<NumOfSubTasks){
//			System.out.println("Make Easy");
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
	
/**
 * @author: Bin CHen
 *  * @param TaskSpec
 * @return
 * this method takes taskSepec, example: 
 * Num_Agents3 = 1,1,1,1,2
 *Quality3 = 0.1,0.7,0.2,0.2,0.1
 *and it returns the task is "Hard, Average, or Easy" task according to the definition on  Table 3.2 Ad_Hoc_Paper_Introduction_Methodology_2014_10_10
 */
public String TestTasks(String TaskSpec){

	
		int [] SubTaskAgentReq = new int[5];
		double [] SubTaskQualityReq = new double[5];
		
//		int NumHardAgCnt=0, HumAvgAgCnt=0, NumEasyAgCnt=0;
//		//n>=3 hardAgentCnt   n=2 averageAgCnt , n=1 EasyAgCnt
//		int NumHardQualCnt=0, NumAvgQualCnt=0, NumEasyQualCnt=0;
//		//0.7≤qualThresh<1.0  hardQuality  , 0.3≤qualThresh<0.7 averageQuality  , 0≤qualThresh<0.3  easyQuality
		
		int HardSubtask=0;int  AvgSubtask=0; int EasySubtask=0;
		
		String [] Lines = TaskSpec.split("\n");
//		System.out.println(Lines[0]);
//		System.out.println(Lines[1]);
		String [] NumAgentsLine=Lines[0].split("=");
//		System.out.println(NumAgentsLine[1].trim());
		String [] QualityLine=Lines[1].split("=");
		String [] SubTaskAgentReqStr = NumAgentsLine[1].trim().split(",");
		String [] SubTaskQualityReqStr = QualityLine[1].trim().split(",");
//		System.out.println( QualityLine[1].trim());
		
		for(int i=0; i<5; i++){
			SubTaskAgentReq[i] = Integer.parseInt(SubTaskAgentReqStr[i]);
			SubTaskQualityReq[i] = Double.parseDouble(SubTaskQualityReqStr[i]);
		}
		
//		System.out.println(Arrays.toString(SubTaskAgentReq));
//		System.out.println(Arrays.toString(SubTaskQualityReq));
		
		for(int i=0; i<5; i++){
//			System.out.println("i="+i);
//			String subSpec="Agent "+SubTaskAgentReq[i]+"  Qual "+SubTaskQualityReq[i];
			
			if(SubTaskAgentReq[i] >= 3 || SubTaskQualityReq[i]>=0.7){
				HardSubtask++;
//				System.out.println(subSpec+ " hard");
			}
			else if( SubTaskAgentReq[i]==2 || SubTaskQualityReq[i] >=0.3){
				AvgSubtask++;
//				System.out.println(subSpec+ " Avg");
			}
			else{
				EasySubtask++;
//				System.out.println(subSpec+ " Easy");
			}
			

			
		}
//		System.out.println("hard="+HardSubtask+" Avg="+AvgSubtask+" Easy="+EasySubtask);

		if (HardSubtask>=2){
			return "Hard";
		}else if(AvgSubtask>=2){
			return "Average";
		}else{
			return "Easy";
		}
	}
	
	
	
}//end class










	










