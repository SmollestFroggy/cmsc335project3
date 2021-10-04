

package CMSC335_project3;

import java.util.concurrent.ThreadLocalRandom;



public class CarFormula 
{

	private int X_Position;
	private int Y_Position = 0;

	
	private String nameOfThread = "";
	public Thread thread1;

	
	//This is a constructor for the name, lowest, and highest: the range of the initial X_Position of car is based off highest and lowest'
	public CarFormula(String thread_name, int highest, int lowest) 
	{
		
		this.nameOfThread = thread_name;
		this.X_Position = ThreadLocalRandom.current().nextInt(lowest,highest);
		System.out.println("Creating threadname: " + nameOfThread);
			
	}

	public synchronized int getX_Position()
	{
        
		return X_Position;
 
	}
	
	
	
}
