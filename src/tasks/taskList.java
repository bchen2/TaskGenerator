package tasks;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import tasks.Distribution;

/*
 * Choose Based on Task Distribution and Task Openness
 * */
public class taskList {
	Boolean DebugMode=true;
	
	public static void main(String[] Args){
		taskList L = new taskList();
		L.Run();
	}
	
	public void Run() {
		
		double TaskOpenness = 0.00;
		String [] AllConfigurations = {"HAE","HEA","AEH","AHE","EHA","EAH","UNIFORM"};
		/**
		 * note the distribution make sence when the initallyIntroducedTasks >=10, otherwise it wont distribute correctly
		 */
		
		while(TaskOpenness <=1.00){
//				TaskListOrdering(TaskOpenness, 40, 15504, new Distribution(0.6,0.3,0.1));
//				TaskListOrdering(TaskOpenness, 40, 15504, new Distribution(0.6,0.1,0.3));
//				TaskListOrdering(TaskOpenness, 40, 15504, new Distribution(0.3,0.6,0.1));
//				TaskListOrdering(TaskOpenness, 40, 15504, new Distribution(0.1,0.6,0.3));
//				TaskListOrdering(TaskOpenness, 40, 15504, new Distribution(0.3,0.1,0.6));
//				TaskListOrdering(TaskOpenness, 40, 15504, new Distribution(0.1,0.3,0.6));
				TaskListOrdering(TaskOpenness, 40, 15504, new Distribution(0.33,0.33,0.34));
				
				TaskOpenness += 0.25;
		}
	}//end main
	
	
	void TaskListOrdering(double TaskOpenness, int InitiallyIntroducedTasks, int TotalNumberOfTask, Distribution TaskDistribution){
		Random random = new Random();
		
		
		int InitialNumberOfHardTasks = TaskDistribution.GetHardTaskCount(InitiallyIntroducedTasks); 
		int InitialNumberOfAvergaeTasks = TaskDistribution.GetAverageTaskCount(InitiallyIntroducedTasks); 
		int InitialNumberOfEasyTasks = InitiallyIntroducedTasks-InitialNumberOfHardTasks-InitialNumberOfAvergaeTasks;
		
		
		/**
		 * we put the Item as String, so letter on we can remove object from the array, if we put it as Integer, 
		 * then later the remove function always use remove index instead of remove object
		 */
		ArrayList<String> EasyTasksInsideSimulation, AverageTasksInsideSimulation, HardTasksInsideSimulation, AvailableEasyTasks, AvailableAverageTasks,AvailableHardTasks;
		EasyTasksInsideSimulation = new ArrayList<String>();
		AverageTasksInsideSimulation = new ArrayList<String>();
		HardTasksInsideSimulation = new ArrayList<String>();
		AvailableEasyTasks = new ArrayList<String>();
		 AvailableAverageTasks = new ArrayList<String>();
		 AvailableHardTasks=new ArrayList<String>();
		 
		 ArrayList<String> TaskList =new ArrayList<String>();
		 
		 
		 int HardTaskBeginIndex=1; int HardTaskEndIndex=TotalNumberOfTask;//15504
		 int AverageTaskBeginIndex=HardTaskEndIndex+1;//15505
		 int AverageTaskEndinIndex=TotalNumberOfTask*2;//31008
		 int EasyTaskBeginIndex=AverageTaskEndinIndex+1;//31009
		 int EasyTaskEndIndex=TotalNumberOfTask*3;//46512
		
		for (int i=HardTaskBeginIndex;i<=HardTaskEndIndex;i++){//add HardTasks to AvailableHardTasks
			 AvailableHardTasks.add(String.valueOf(i));
		}
		
		for (int i=AverageTaskBeginIndex;i<=AverageTaskEndinIndex;i++){
			 AvailableAverageTasks.add(String.valueOf(i));
		}
		
		for (int i=EasyTaskBeginIndex;i<=EasyTaskEndIndex;i++){
			AvailableEasyTasks.add(String.valueOf(i));
		}
		
	
		print("InitalHard="+InitialNumberOfHardTasks+" InitalAvg="+InitialNumberOfAvergaeTasks+" InitalEasy="+InitialNumberOfEasyTasks);
		//calculate the initial tasks's index in the total TasksList
		int BeginHardIndex=1; 
		int EndHardIndex=InitialNumberOfHardTasks;
		int BeginAvgIndex = InitialNumberOfHardTasks+1;
		int EndAvgIndex=BeginAvgIndex+InitialNumberOfAvergaeTasks-1;
		int BeginEasyIndex=EndAvgIndex+1;
		int EndEasyIndex=BeginEasyIndex+InitialNumberOfEasyTasks-1;
		int BeginIndex=EndEasyIndex+1;
		print("TO = "+TaskOpenness);
		print("InitalHardBegin :"+BeginHardIndex);
		print("InitalHardEnd :"+EndHardIndex);
		print("InitalAvgBegin :"+BeginAvgIndex);
		print("InitalAvgEnd :"+EndAvgIndex);
		print("InitalEasyBegin :"+BeginEasyIndex);
		print("InitalEasyEnd :"+EndEasyIndex);
		// Add Initial Hard Tasks 
		for(int i=1; i<= EndHardIndex; i++){
			int randomIndex=random.nextInt(AvailableHardTasks.size());
			String randomItem=AvailableHardTasks.get(randomIndex);
			 AvailableHardTasks.remove(randomItem);
			 HardTasksInsideSimulation.add(randomItem);
			 TaskList.add(randomItem);
			//print("Hard i : "+ i);
		}
		print("HardTasksInsideSimulation="+HardTasksInsideSimulation.size());
		
		
		// Add Initial Average Tasks 
		for(int i=BeginAvgIndex; i<= EndAvgIndex; i++){
			int randomIndex=random.nextInt(AvailableAverageTasks.size());
			String randomItem=AvailableAverageTasks.get(randomIndex);
			AvailableAverageTasks.remove(randomItem);
			AverageTasksInsideSimulation.add(randomItem);
			 TaskList.add(randomItem);
		//	print("Average i : "+ i);
		}
		print("AverageTasksInsideSimulation="+AverageTasksInsideSimulation.size());
		
		
		
	
		// Add Initial Easy Tasks
		for(int i=BeginEasyIndex; i<= EndEasyIndex; i++){
			
			int randomIndex=random.nextInt(AvailableEasyTasks.size());
			String randomItem=AvailableEasyTasks.get(randomIndex);
			AvailableEasyTasks.remove(randomItem);
			EasyTasksInsideSimulation.add(randomItem);
			 TaskList.add(randomItem);
		}
		
		print("EasyTasksInsideSimulation="+EasyTasksInsideSimulation.size());
		
		
		// Initial group of tasks have already been introduced, now for the rest of the tasks
		for (int i=BeginIndex;i<=TotalNumberOfTask;i++){
			
			try{
			
			double chanceForNewTask=Math.random();
			
			if (chanceForNewTask>TaskOpenness){ // chose task from  tasks which are already in the simulation.
				
				// Check for the distribution variable
				// Assumption is that from 0 to 1, 0 to EasyTaskPercentage, EasyTaskPercentage to AverageTaskPercentage and AverageTaskPercentage to 1
				
				double chanceForTaskDifficulty = Math.random();
				
				// Add Easy Task
				if(chanceForTaskDifficulty <= TaskDistribution.EasyPercentage){ 
					int randomIndex=random.nextInt(EasyTasksInsideSimulation.size());
					String randomItem=EasyTasksInsideSimulation.get(randomIndex);
					 TaskList.add(randomItem);
				}
				
				//Add Average Task
				else if (chanceForTaskDifficulty > TaskDistribution.EasyPercentage && chanceForTaskDifficulty <= TaskDistribution.EasyPercentage + TaskDistribution.AveragePercentage){
					int randomIndex=random.nextInt(AverageTasksInsideSimulation.size());
					String randomItem=AverageTasksInsideSimulation.get(randomIndex);
					 TaskList.add(randomItem);
				}
				
				//Add Hard Task
				else{
					int randomIndex=random.nextInt(HardTasksInsideSimulation.size());
					String randomItem=HardTasksInsideSimulation.get(randomIndex);
					 TaskList.add(randomItem);
				}
				
			}
			
			else{/*add new tasks into the TaskList from the three Available TaskList */
				
				double y = Math.random();
				
				// Add Easy Task
				if(y <= TaskDistribution.EasyPercentage){ 
					int randomIndex=random.nextInt(AvailableEasyTasks.size());
					String randomItem=AvailableEasyTasks.get(randomIndex);
					AvailableEasyTasks.remove(randomItem);
					EasyTasksInsideSimulation.add(randomItem);
					 TaskList.add(randomItem);
					
				}
				
				//Add Average Task
				else if (y > TaskDistribution.EasyPercentage &&  y < (TaskDistribution.EasyPercentage + TaskDistribution.AveragePercentage)){
					int randomIndex=random.nextInt(AvailableAverageTasks.size());
					String randomItem=AvailableAverageTasks.get(randomIndex);
					AvailableAverageTasks.remove(randomItem);
					AverageTasksInsideSimulation.add(randomItem);
					 TaskList.add(randomItem);
				}
				
				//Add Hard Task
				else{
					int randomIndex=random.nextInt(AvailableHardTasks.size());
					String randomItem=AvailableHardTasks.get(randomIndex);
					 AvailableHardTasks.remove(randomItem);
					 HardTasksInsideSimulation.add(randomItem);
					 TaskList.add(randomItem);
				}
				
			}
			}
			catch(Exception E){
				print(E.getMessage());
			}
		} // end of for
		
	
		try {
			int HardPer=(int) (TaskDistribution.HardPercentage*100);
			int AvgPer=(int) (TaskDistribution.AveragePercentage*100);
			PrintWriter out = new PrintWriter("GeneratedTasks/config_task_TO"+TaskOpenness+"HP"+
		                                      HardPer+"AP"+AvgPer);
//			PrintWriter out = new PrintWriter("GeneratedTasks/testfile");
  
			
			String result="";
			int i=0;
			for (String chosenTask: TaskList){
				i++;
				result=result+i+"="+chosenTask+"\n";
			}
			out.write(result);
			out.close();
		} 
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace(); 
			}
		
		
		
	}
	
	
	
	public void print(String s){
		if (this.DebugMode){
			System.out.println(s);
		}
	}

}//end class
