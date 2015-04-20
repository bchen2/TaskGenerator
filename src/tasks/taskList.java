package tasks;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import tasks.Distribution;

/*
 * Choose Based on Task Distribution and Task Openness
 * */
public class taskList {

	public static void main(String[] Args){
		taskList L = new taskList();
		L.Run();
	}
	
	public void Run() {
		
		double TaskOpenness = 0.00;
		String [] AllConfigurations = {"HAE","HEA","AEH","AHE","EHA","EAH","UNIFORM"};
		
//		while(TaskOpenness <=1.00){
//				TaskListOrdering(TaskOpenness, 40, 15504*3, new Distribution(0.6,0.3,0.1));
//				TaskListOrdering(TaskOpenness, 40, 15504*3, new Distribution(0.6,0.1,0.3));
//				TaskListOrdering(TaskOpenness, 40, 15504*3, new Distribution(0.3,0.6,0.1));
//				TaskListOrdering(TaskOpenness, 40, 15504*3, new Distribution(0.1,0.6,0.3));
//				TaskListOrdering(TaskOpenness, 40, 15504*3, new Distribution(0.3,0.1,0.6));
//				TaskListOrdering(TaskOpenness, 40, 15504*3, new Distribution(0.1,0.3,0.6));
//				TaskListOrdering(TaskOpenness, 40, 15504*3, new Distribution(0.33,0.33,0.34));
//				
//				TaskOpenness += 0.25;
//		}
		TaskListOrdering(TaskOpenness, 40, 15504*3, new Distribution(0.6,0.3,0.1));
	}//end main
	
	
	void TaskListOrdering(double TaskOpenness, int InitiallyIntroducedTasks, int TotalNumberOfTask, Distribution TaskDistribution){
		
		int InitialNumberOfHardTasks = TaskDistribution.GetHardTaskCount(InitiallyIntroducedTasks); 
		int InitialNumberOfAvergaeTasks = TaskDistribution.GetAverageTaskCount(InitiallyIntroducedTasks); 
		int InitialNumberOfEasyTasks = TaskDistribution.GetEasyTaskCount(InitiallyIntroducedTasks);
		
		ArrayList<Integer> EasyTasksInsideSimulation, AverageTasksInsideSimulation, HardTasksInsideSimulation, AllTasksInsideSimulation, AllAvailableTasks;
		EasyTasksInsideSimulation = new ArrayList<Integer>();
		AverageTasksInsideSimulation = new ArrayList<Integer>();
		HardTasksInsideSimulation = new ArrayList<Integer>();
		AllTasksInsideSimulation = new ArrayList<Integer>();
		AllAvailableTasks = new ArrayList<Integer>();
		
		for (int i=1;i<TotalNumberOfTask+1;i++){//from 1 to total number of tasks
			AllAvailableTasks.add(i);		
		}
		
		//System.out.println(TotalNumberOfTask);
		
		// Add Initial Hard Tasks 
		for(int i=1; i<= InitialNumberOfHardTasks; i++){
			AllTasksInsideSimulation.add(i);
			HardTasksInsideSimulation.add(i);
			AllAvailableTasks.remove(i);
			//System.out.println("Hard i : "+ i);
		}
		
		int BeginIndex = (int) TotalNumberOfTask/3 - InitialNumberOfHardTasks;
		System.out.println( (int) TotalNumberOfTask/3);
		System.out.println(BeginIndex);
		// Add Initial Average Tasks 
		for(int i=BeginIndex; i<= BeginIndex+InitialNumberOfAvergaeTasks; i++){
			AllTasksInsideSimulation.add(i);
			AverageTasksInsideSimulation.add(i);
			AllAvailableTasks.remove(i);		
		//	System.out.println("Average i : "+ i);
		}
		
		BeginIndex = (int) 2*TotalNumberOfTask/3-(InitialNumberOfHardTasks+ InitialNumberOfAvergaeTasks);
		// Add Initial Difficult Tasks
		for(int i=BeginIndex; i<= BeginIndex+InitialNumberOfEasyTasks; i++){
			AllTasksInsideSimulation.add(i);
			EasyTasksInsideSimulation.add(i);
			AllAvailableTasks.remove(i);
		}
		
		//System.out.println(AverageTasksInsideSimulation.size());
		
		// Initial group of tasks have already been introduced, now for the rest of the tasks
		for (int i=InitiallyIntroducedTasks+1;i<TotalNumberOfTask;i++){
			
			try{
			
			//starting from InitiallyIntroducedTasks+1 since initially there are InitiallyIntroducedTasks task in AllTasksInsideSimulation
			double x=Math.random();
			
			if (x>TaskOpenness){ // chose task from  tasks which are already in the simulation.
				
				// Check for the distribution variable
				// Assumption is that from 0 to 1, 0 to EasyTaskPercentage, EasyTaskPercentage to AverageTaskPercentage and AverageTaskPercentage to 1
				
				double y = Math.random();
				
				// Add Easy Task
				if(y <= TaskDistribution.EasyPercentage){ 
					int RandomEasyTask = EasyTasksInsideSimulation.get((int)Math.random()*EasyTasksInsideSimulation.size()+1);
					AllTasksInsideSimulation.add(RandomEasyTask);				
				}
				
				//Add Average Task
				else if (y > TaskDistribution.EasyPercentage && y < TaskDistribution.EasyPercentage + TaskDistribution.AveragePercentage){
					int RandomAverageTask = AverageTasksInsideSimulation.get((int)Math.random()*AverageTasksInsideSimulation.size()+1);
					AllTasksInsideSimulation.add(RandomAverageTask);					
				}
				
				//Add Hard Task
				else{
					int RandomHardTask = HardTasksInsideSimulation.get((int)Math.random()*HardTasksInsideSimulation.size()+1);
					AllTasksInsideSimulation.add(RandomHardTask);					
				}
				
			}
			
			else{
				
				double y = Math.random();
				
				// Add Easy Task
				if(y <= TaskDistribution.EasyPercentage){ 
					int IndexVal =  (int)(Math.random()* (AllAvailableTasks.size()/3)) + (2*AllAvailableTasks.size()/3);
					int RandomEasyTask = AllAvailableTasks.get(IndexVal );
					AllTasksInsideSimulation.add(RandomEasyTask);	
					EasyTasksInsideSimulation.add(RandomEasyTask);
					AllAvailableTasks.remove(IndexVal);
					
				}
				
				//Add Average Task
				else if (y > TaskDistribution.EasyPercentage &&  y < (TaskDistribution.EasyPercentage + TaskDistribution.AveragePercentage)){
					int IndexVal = (int)((Math.random()*(AllAvailableTasks.size()/3)) + (AllAvailableTasks.size()/3) ) ;
					int RandomAverageTask = AllAvailableTasks.get(IndexVal);
					AllTasksInsideSimulation.add(RandomAverageTask);
					AverageTasksInsideSimulation.add(RandomAverageTask);
					AllAvailableTasks.remove(IndexVal);
				}
				
				//Add Hard Task
				else{
					int IndexVal = (int)(Math.random()*(AllAvailableTasks.size()/3));
					int RandomHardTask = AllAvailableTasks.get(IndexVal);
					AllTasksInsideSimulation.add(RandomHardTask);
					HardTasksInsideSimulation.add(RandomHardTask);
					AllAvailableTasks.remove(IndexVal);
				}
				
			}
			}
			catch(Exception E){
				System.out.println(E.getMessage());
			}
		} // end of for
		
	
//		try {
//			PrintWriter out = new PrintWriter("GeneratedTasks/config_task_TO"+TaskOpenness+"HP"+
//		                                       TaskDistribution.HardPercentage+"AP"+TaskDistribution.AveragePercentage
//		                      );
//			String result="";
//			int i=0;
//			for (int chosenTask: AllTasksInsideSimulation){
//				i++;
//				result=result+i+"="+chosenTask+"\n";
//			}
//			out.write(result);
//			out.close();
//		} 
//		catch (FileNotFoundException fnfe) {
//			fnfe.printStackTrace(); 
//			}
		
		
		
	}

}//end class
