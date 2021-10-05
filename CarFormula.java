

package CMSC335_project3;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;


public class CarFormula implements Runnable
{

	private int X_Position;
	private final AtomicBoolean carisRunning = new AtomicBoolean(false);
	public final AtomicBoolean carisAtLight = new AtomicBoolean(false);
	public final AtomicBoolean carisSuspended = new AtomicBoolean(false);
	
	private String nameOfThread = "";
	public Thread thread1;
	private int speedofCar = 0;
	
	//This is a constructor for the name, lowest, and highest: the range of the initial X_Position of car is based off max and min'
	public CarFormula(String thread_name, int max, int min) 
	{
		
		this.nameOfThread = thread_name;
		this.X_Position = ThreadLocalRandom.current().nextInt(min,max);
		System.out.println("Creating threadname: " + nameOfThread);
			
	}

	public synchronized int getX_Position()
	{
        
		return X_Position;
 
	}
	
	public int getCarSpeed() {
        if(carisRunning.get()) {
            if(carisAtLight.get()) 
            	speedofCar = 0;
            else 
                //Incrementing 5 meters every 1/10th of a second.
                //Thats 50 meters per second, 3000 meters per minute
                //3 km per minute * 60 for 180 kph
            	speedofCar = 3*60;
        } else 
        	speedofCar = 0;
        return speedofCar;
    }
	
	public void start() 
	{
        
		System.out.println("Starting " + nameOfThread);
		if(thread1 == null)
        {
        	
        	thread1 = new Thread(this, nameOfThread);
        	thread1.start();
       
        }
    }
	
	public void stop()
	{
    
		thread1.interrupt();
		carisRunning.set(false);
        System.out.println("Stopping now at " + nameOfThread);
    
	}
	
	public void suspend() 
	{
     
		carisSuspended.set(true);
        System.out.println("Suspending " + nameOfThread);
    
	}

	//must be synchronized
	public synchronized void resume() 
	{
       
		//If statement for carisSuspended, which sets carisSuspended to false and notify();
        if(carisSuspended.get() || carisAtLight.get())
        {
        
        	carisSuspended.set(false);
        	carisAtLight.set(false);
            notify();
            System.out.println("Resuming " + nameOfThread);
       
        }
    }
	@Override
	public void run()
	{
		
		System.out.println("Car is Running " + nameOfThread);
		carisRunning.set(true);
		
		while(carisRunning.get())
		{
			try{
				
				while(X_Position < 3000) 
				{
					
					synchronized(this)
					{
						
						while(carisSuspended.get() || carisAtLight.get())
						{		
							
							System.out.println(nameOfThread + "is waiting");
							wait();
							
						}
					}
					//the if statement will check if still running
					if(carisRunning.get())
					{
						
						Thread.sleep(100);
						X_Position+=5;
							
					}
						
				}
				X_Position = 0;
			}catch (InterruptedException ex)
			{
				System.err.println (ex);
				return;
				
			}
		}
	}
	
}