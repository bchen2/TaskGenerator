package tasks;

public class Distribution {
	double HardPercentage, AveragePercentage, EasyPercentage;
	
	public Distribution(double Hard, double Average, double Easy){
		this.HardPercentage = Hard;
		this.AveragePercentage = Average;
		this.EasyPercentage = Easy;
		
	}
	
	public int GetEasyTaskCount(int TotalTasks){
		return (int) (EasyPercentage * TotalTasks); 
	}
	
	
	public int GetAverageTaskCount(int TotalTasks){
		return (int) (AveragePercentage * TotalTasks); 
	}
	
	
	public int GetHardTaskCount(int TotalTasks){
		return (int) (HardPercentage * TotalTasks); 
	}


}
