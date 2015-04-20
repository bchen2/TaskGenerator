/**
 * 
 */
package tasks;

/**
 * @author Anish
 *Modified by Bin Chen 
 *Last Modified: 2015/4/20
 */
public class SubTasks {

	private int NumberOfAgents;
	private double Quality;
	private double Alpha = 0.0033, Beta= 0.00556;
	private int HighestNumOfAgentsRequired=5;

	
	public int getNumberOfAgents() {
		return NumberOfAgents;
	}
	
	public void setNumberOfAgents(int numberOfAgents) {
		NumberOfAgents = numberOfAgents;
	}
	
	public double getQuality() {
		return Quality;
	}
	
	public void setQuality(double quality) {
		Quality = quality;
	}
	
	
	

/*
 * Notice for the use of Math.random
 *reference: http://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
 *
 * Min + (Math.random() * (Max - Min))
 *	You now will get a value in the range [Min,Max). example, if Max=10, Min=5, then Following our example, that means [5,10)
 *	Min + (int)(Math.random() * ((Max - Min) + 1))
 * this way we get [5,10]
 */
	
	
	public void MakeEasySubtask(int TotalNumberOfAgents, double MinQuality, double MaxQuality){
		
		
		//int NumOfMaxAgent = (int)(TotalNumberOfAgents * Alpha) + 1; 
		int NumOfMaxAgent = 1;
		this.Quality = roundDown1d(MinQuality+Math.random()*(MaxQuality-MinQuality));
		this.NumberOfAgents = (int)Math.ceil((Math.random()*NumOfMaxAgent));
	}
	
	public void MakeAverageSubtask(int TotalNumberOfAgents, double MinQuality, double MaxQuality){
		
		int RollDice = (int) Math.ceil(Math.random() * 3); // this gives 1, 2 or 3
		//int NumOfMaxAgent = (int)(TotalNumberOfAgents * Beta) + 1; 
		//int NumOfMinAgent = (int) (TotalNumberOfAgents * Alpha)+1;
		
		/*
		 * the quality Q must be between  0.3<=Q<0.7, numAgent can be either 1 or 2
		 */
		if(RollDice == 1 || RollDice ==2){
			this.Quality = round1d(MinQuality+Math.random()*(MaxQuality-MinQuality));
			this.NumberOfAgents = (int) Math.ceil(Math.random()*2); // Either 1 or 2 
		}
		
		else{
			/*
			 * numAgent get fixed to be 2, 0<=Q<0.3
			 */
			this.NumberOfAgents=2;
			this.Quality = round1d(0.1+Math.random()*(0.3-0.1));//either 0.1 or 0.2
		}
		
	}
	
	
	public void MakeHardSubtask(int TotalNumberOfAgents, double MinQuality, double MaxQuality){
		//Min=0.7  Max=1.0
		
		/*
		 * According to Table 3.1 on Ad_Hoc_Paper_Introduction_Methodology_2014_10_10, there can be 5 situations that a subtask is 
		 * defined as Hard subtask, the RollDice decides which situation it falls into.
		 */
		int RollDice = (int)Math.ceil(Math.random() * 5); //gives 1,2,3,4,5
		//int NumOfMaxAgent = (int)(TotalNumberOfAgents * Beta) + 1; 
		if(RollDice<=3){
			this.Quality = roundDown1d(MinQuality+Math.random()*(MaxQuality-MinQuality));//not include 1.0, it gives 0.7,0.8,0.9
			this.NumberOfAgents =(int) Math.ceil((Math.random()*5));//gives 1 to 5
		}
		else{
			this.NumberOfAgents = 3 + (int)Math.ceil( Math.random()* 2);//either 3,4,5
			this.Quality =  round1d(0.1+Math.random()*(0.9-0.1));//give 0.1 to 0.9
		}
	
		
	}
	
	// Case 1: (3,3), Case 2 : (1,3), Case 3: (2,3), Case 4: (), Case 5: ()

	
	public static double round1d(double a){
		double roundoff=Math.round(a*10.0)/10.0;
		return roundoff;
	}//end of method
	
	
	public static double roundDown1d(double a){
		double roundoff=(Math.floor(a*10.0))/10.0;
		return roundoff;
	}//end of method
	
	public static void main(String[] args) {
//		double MinQuality=0.7; double MaxQuality=1.0;
//		double x=MinQuality+Math.random()*(MaxQuality-MinQuality);
//		System.out.println(x);
//		System.out.println((Math.floor(x*10.0))/10.0);
		double x=Math.random()*(5-2+1);
		System.out.println(x);
		System.out.println((int)x);
		
		
		taskTypes tp = new taskTypes();
		
//		String taskSpec=tp.GetHardTaskSpec(3,5);
		String taskSpec=tp.GetAverageTaskSpec(3,5);
//		String taskSpec=tp.GetEasyTaskSpec(3,5);
		
//		String a="Num_Agents3 = 1,1,1,1,2\nQuality3 = 0.1,0.7,0.2,0.2,0.1";
//		System.out.println(a);
//		System.out.println(tp.TestTasks(a));
		System.out.println(tp.TestTasks(taskSpec));
	}
}
