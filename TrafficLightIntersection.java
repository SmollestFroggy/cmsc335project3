package CMSC335_project3;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JLabel;


public class TrafficLightIntersection implements Runnable
{

	private final String[] traffic_light_colors = {"Green", "Yellow", "RED"};
	private int color_variable = 0;
	private String colorOfLight = traffic_light_colors[color_variable];
	
	private final AtomicBoolean carisRunning = new AtomicBoolean(false);
	public final AtomicBoolean carisSuspended = new AtomicBoolean(false);
	
	Thread thread1;
	String nameOfThread;
	
	// This is so that you have a JLabel to print into
	private JLabel intersectionLabel;
	
	public TrafficLightIntersection(String thread_name, JLabel intersectionLabel ) 
	{
	
		this.nameOfThread = thread_name;
		this.intersectionLabel = intersectionLabel;
		System.out.println("Generating name: " + nameOfThread);
	 
	}

	//This is synchronized method for the getting the Current Traffic light Color
    public synchronized String getTrafficLightColor() 
    {
    
    	this.colorOfLight = traffic_light_colors[color_variable];
        return this.colorOfLight;
    
    }

    public void suspend() 
    {
    
    	carisSuspended.set(true);
        System.out.println(nameOfThread + "is now suspending.");
    
    }
    
    public synchronized void resume() 
    {
    
    	carisSuspended.set(false);
        notify();
        System.out.println(nameOfThread + "is now resuming.");
    
    }
    
    public void start() 
    {
    
    	System.out.println(nameOfThread + " is now starting. ");
        
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
        System.out.println(nameOfThread + "has now been stopped." );
   
    }
    
    public void interrupt()
    {
       
    	//If the light is in sleep mode, hitting the pause button will call interrupt to wake it. 
    	thread1.interrupt();
    
    }
    
    @Override
    public void run() 
    
    {
        System.out.println("Running " + nameOfThread);
        carisRunning.set(true);
        while(carisRunning.get())
        {
            try 
            {
                synchronized(this) 
                {
                        while(carisSuspended.get()) 
                        {
                            
                        	System.out.println(nameOfThread + " waiting");
                            wait();
                       
                        }
                    
                }
                
                switch (getTrafficLightColor())
                {
                   
                	case "Green":
                    	intersectionLabel.setForeground(new Color(0,200,10)); ///Makes Text  color to green
                    	intersectionLabel.setText(getTrafficLightColor());
                        //Stay green for 10 seconds
                        Thread.sleep(10000);
                        color_variable++;
                        break;
                    
                	case "Yellow":
                    	intersectionLabel.setForeground(new Color(247, 226, 35)); ///Makes Text  color yellow
                    	intersectionLabel.setText(getTrafficLightColor());
                        //Yellow for 5 seconds
                        Thread.sleep(5000);
                        color_variable++;
                        break;
                    
                    case "Red":
                        intersectionLabel.setForeground(Color.RED); //Makes Text color red
                        intersectionLabel.setText(getTrafficLightColor());
                        //Red for 5 seconds
                        Thread.sleep(5000);
                        //Set i back to 0
                        color_variable = 0;
                        break;
                   
                    default:
                        break;
                }

            } catch (InterruptedException ex) {
                //If thread gets interrupted, set suspended true
            	carisSuspended.set(true);
            }
        
        
        }
    }


    
    
}
