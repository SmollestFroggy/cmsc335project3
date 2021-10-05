package CMSC335_project3;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentTime implements Runnable
{
    private boolean carisRunning;
    private String timePattern = "hh:mm:ss a";
    private SimpleDateFormat timeFormat = new SimpleDateFormat(timePattern); 
    Date date = new Date(System.currentTimeMillis());

    public CurrentTime() 
    {
        this.carisRunning = Thread.currentThread().isAlive();
    }
    
    public String getTime() 
    {
   
    	date = new Date(System.currentTimeMillis());
        return timeFormat.format(date);
    
    }

    @Override
    public void run() 
    {
    
    	//While running, constantly update current time
        while (carisRunning)
        {
            
        	trafficLightandCarGui.CurrentTimeText.setText(getTime());
        
        } 
    }
    
}