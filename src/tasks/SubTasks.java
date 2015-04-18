/**
 * 
 */
package tasks;

/**
 * @author Anish
 *
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
	
	public void MakeEasySubtask(int TotalNumberOfAgents, double MinQuality, double MaxQuality){
		
		
		//int NumOfMaxAgent = (int)(TotalNumberOfAgents * Alpha) + 1; 
		int NumOfMaxAgent = 1;
		this.Quality = round1d(MinQuality+Math.random()*(MaxQuality-MinQuality));
		this.NumberOfAgents = (int)Math.ceil((Math.random()*NumOfMaxAgent));
	}
	
	public void MakeAverageSubtask(int TotalNumberOfAgents, double MinQuality, double MaxQuality){
		
		int RollDice = (int) Math.ceil(Math.random() * 3);
		//int NumOfMaxAgent = (int)(TotalNumberOfAgents * Beta) + 1; 
		//int NumOfMinAgent = (int) (TotalNumberOfAgents * Alpha)+1;
				
		if(RollDice == 1 || RollDice ==2){
			this.Quality = round1d(MinQuality+Math.random()*(MaxQuality-MinQuality));
			this.NumberOfAgents = (int) Math.ceil(Math.random()*2); // Either 1 or 2 
		}
		
		else{
			this.NumberOfAgents=2;
			this.Quality = round1d(0.1+Math.random()*(0.3-0.1));
		}
		
	}
	
	
	public void MakeHardSubtask(int TotalNumberOfAgents, double MinQuality, double MaxQuality){
		
		int RollDice = (int)Math.ceil(Math.random() * 5);
		//int NumOfMaxAgent = (int)(TotalNumberOfAgents * Beta) + 1; 
		if(RollDice<=3){
			this.Quality = round1d(MinQuality+Math.random()*(MaxQuality-MinQuality));
			this.NumberOfAgents =(int) Math.ceil((Math.random()*5));
		}
		else{
			this.NumberOfAgents = 3 + (int)Math.ceil( Math.random()* 2);
			this.Quality =  round1d(0.1+Math.random()*(0.9-0.1));
		}
	
		
	}
	
	// Case 1: (3,3), Case 2 : (1,3), Case 3: (2,3), Case 4: (), Case 5: ()

	
	public static double round1d(double a){
		double roundoff=Math.round(a*10.0)/10.0;
		return roundoff;
	}//end of method
}
