//File name: CMSC335_Project3
//Due Date 10/12/2021
//Code created Date: 10/5/2021
//Purpose: - 
// -Components listed above should run in separate threads. 
//- Loop through the simulation with button(s) that can START, PAUSE, STOP, and continue on with simulation
//- Distance formulas will be used e.g: distance = Speed * time. Define all units of measure no acronyms type out meter/hour, or kilometer/hour not km/h or m/h
//- Distance between each light is 1000meters ( IS A COMPLETE STRAIGHT LINE) 
//- Y = 0 for your x,y positions. 
//- no physics is required, Cars will stop immediately on red light, and run through yellow and green lights. 
//1. Current time stamps in 1 second intervals
//2. Real-time Traffic light display for three major intersections
//3. X, Y positions and speed of up to 3 cars as they traverse each of the 3 intersection

package CMSC335_project3;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class trafficLightandCarGui extends JFrame implements Runnable, ChangeListener
{
	static JLabel CurrentTimeText = new JLabel();
	static JLabel trafficLight1at1000Text = new JLabel();
	static JLabel trafficLight2at2000Text = new JLabel();
	static JLabel trafficLight3at3000Text = new JLabel();
	
	//These JButton functions are for start, pause, and stop
	private JButton start = new JButton("Start Cars");
	private JButton pause = new JButton("Pause Cars");
	private JButton stop = new JButton("Stop Cars");
	
	//These JSliders display the position to represent as a mark of each car
	static JSlider car_1_Slider = new JSlider(0, 3000);
	static JSlider car_2_Slider = new JSlider(0, 3000);
	static JSlider car_3_Slider = new JSlider(0, 3000);
	static JSlider car_4_Slider = new JSlider(0, 3000);

	
	private static boolean carIsRunning;
	private static final AtomicBoolean trafficCarSimulatorIsRunning = new AtomicBoolean(false);
	
	
	TrafficLightIntersection Light_1 = new TrafficLightIntersection("1stThread", trafficLight1at1000Text);
	TrafficLightIntersection Light_2 = new TrafficLightIntersection("2ndThread", trafficLight2at2000Text);
	TrafficLightIntersection Light_3 = new TrafficLightIntersection("3rdThread", trafficLight3at3000Text);
	
	//creates 4 runnable car objects and a thread for each car object
	CarFormula car_1 = new CarFormula("Car_1Thread", 300, 0);
	CarFormula car_2 = new CarFormula("Car_2Thread", 1000, 0);
	CarFormula car_3 = new CarFormula("Car_3Thread", 1300, 500);
	CarFormula car_4 = new CarFormula("Car_4Thread", 2000, 1000);
	
	
	//This is an array that allows for a loop.
	CarFormula[] carObjectsArray = {car_1, car_2, car_3, car_4};
	TrafficLightIntersection[] TrafficLightintersectionArray = {Light_1, Light_2, Light_3};
	static Thread gui;
	
	
	Object [][] carStateData = 
		{ 
			{"Car #1", car_1.getX_Position(), 0, 0}, {"Car #2", car_2.getX_Position(), 0 ,0},
			{"Car #3", car_3.getX_Position(), 0 ,0}, {"Car #4", car_4.getX_Position(), 0 ,0}
		};
	
	
	String[] columnNameLabels = {"Car#" , "X-Position" , "Y-Position" , "Speed(Kilometers/hour)"};
	JTable carDataTable = new JTable(carStateData, columnNameLabels);
		
	

	public trafficLightandCarGui()
	{
		
		super("CMSC335_Project3_Nathan_Ma's_Traffic_and_Car_Tracker");
		carIsRunning = Thread.currentThread().isAlive();
		createGUI();
        setButtons();

	}
	
	private void display()
	{
		
		setSize(800,500);
		setVisible(true);
		
		// No one likes a window not in the center this next line allows for the frame to be centered to screen
		setLocationRelativeTo(null);
		
		// Allows for program to be closed by user clicking x
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void createGUI()
	{
		
		JLabel greetingTitle = new JLabel("Hello! You have accessed the traffic light and car Simulator!!");
		JLabel userInstruction1 = new JLabel("Please Click on the Start Button to start the Simulator!~~~ :)");
		
		JLabel currentTime = new JLabel("The Time right now is: ");
		
		//labels the intersections 1, 2 and 3)
		JLabel trafficLight1at1000 = new JLabel("Intersection 1 (1000 Meters):");
		JLabel trafficLight2at2000 = new JLabel("Intersection 2 (2000 Meters):");
		JLabel trafficLight3at3000 = new JLabel("Intersection 3 (3000 Meters):");
		
		car_1_Slider.addChangeListener(this);
		car_2_Slider.addChangeListener(this);
		car_3_Slider.addChangeListener(this);
		car_4_Slider.addChangeListener(this);

		car_1_Slider.setValue(car_1.getX_Position());
		car_2_Slider.setValue(car_2.getX_Position());
		car_3_Slider.setValue(car_3.getX_Position());
		car_4_Slider.setValue(car_4.getX_Position());
		
		
		car_1_Slider.setPaintTicks(true);
		car_1_Slider.setMajorTickSpacing(1000);

		
		car_2_Slider.setPaintTicks(true);
		car_2_Slider.setMajorTickSpacing(1000);

		

		carDataTable.setPreferredScrollableViewportSize(new Dimension(650, 70));
		carDataTable.setFillsViewportHeight(true);
		
		
		JPanel carDataPanel = new JPanel();
		
		
		// This is for allowing a table to be display in a panel in the GUI
		JScrollPane scrollpaneForcarDataTable = new JScrollPane(carDataTable);
		carDataPanel.add(scrollpaneForcarDataTable);
		
		//GroupLayout for displaying a hierarchical group which components in order to position them in a Container 
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(currentTime).addComponent(CurrentTimeText)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				        .addGroup(layout.createSequentialGroup()
				        		.addComponent(greetingTitle).addComponent(userInstruction1)))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						 .addGroup(layout.createSequentialGroup()    
				                    .addComponent(start).addComponent(pause).addComponent(stop)))       
				                    .addComponent(car_1_Slider).addComponent(car_2_Slider).addComponent(car_3_Slider).addComponent(car_4_Slider) 			                  
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addGroup(layout.createSequentialGroup()
								.addComponent(trafficLight1at1000).addComponent(trafficLight1at1000Text).addContainerGap(20,20) 
								.addComponent(trafficLight2at2000).addComponent(trafficLight2at2000Text).addContainerGap(20,20)								
								.addComponent(trafficLight3at3000).addComponent(trafficLight3at3000Text))						
								.addComponent(carDataPanel)))
				
				.addContainerGap(30,30)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createSequentialGroup()
					.addComponent(currentTime)
					.addComponent(CurrentTimeText))
					.addGap(20, 20, 20)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(greetingTitle).addComponent(userInstruction1))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(start).addComponent(pause).addComponent(stop))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(car_1_Slider).addComponent(car_2_Slider).addComponent(car_3_Slider).addComponent(car_4_Slider))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(trafficLight1at1000).addComponent(trafficLight1at1000Text)
							.addComponent(trafficLight2at2000).addComponent(trafficLight2at2000Text)
							.addComponent(trafficLight3at3000).addComponent(trafficLight3at3000Text))
					.addComponent(carDataPanel)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addGap(20, 20, 20)) 
					.addGap(20, 20, 20)
							
		);
							
		pack();
					
	}
	
	private void setButtons() 
	{
    
		//Start car and intersection threads with start button
        start.addActionListener((ActionEvent e) -> 
        {
            
        	if(!trafficCarSimulatorIsRunning.get()) 
        	{
                
        		System.out.println(Thread.currentThread().getName() + " calls for start.");
        		Light_1.start();
        		Light_2.start();
        		Light_3.start();
        		car_1.start();
        		car_2.start();
        		car_3.start();
        		car_4.start();

                gui.start();
                start.setEnabled(false);
                
                
                
            }
        
        	//Sets trafficCarSimulatorIsRunning to true
           	trafficCarSimulatorIsRunning.set(true);   
           	
        });
        
        pause.addActionListener((ActionEvent e) -> {
        
        	if(trafficCarSimulatorIsRunning.get()) 
        	{
                //Loop through cars and intersections to call suspend()
                for(CarFormula color_variable: carObjectsArray) 
                {
                	
                	color_variable.suspend();
                    System.out.println(Thread.currentThread().getName() + " is now suspended");
                
                }
                
                for(TrafficLightIntersection color_variable: TrafficLightintersectionArray) 
                {
                  
                	//Call interrupt for sleeping intersection threads
                	color_variable.interrupt();
                	color_variable.suspend();
                
                }
                
                pause.setText("Keeping Going Forward");
                trafficCarSimulatorIsRunning.set(false);
            
        	}else
        	{
                for(CarFormula color_variable: carObjectsArray) 
                {
                    
                	if(color_variable.carisSuspended.get()) {
                    	
                		color_variable.resume();
                        System.out.println(Thread.currentThread().getName() + " is now resuming");
                    
                	}
                
                }
                
                for(TrafficLightIntersection color_variable: TrafficLightintersectionArray) 
                {
                
                	color_variable.resume();
               
                }
                
                pause.setText("Pause Cars");
                trafficCarSimulatorIsRunning.set(true);
            
        	}
       
        });
        
        stop.addActionListener((ActionEvent e) -> 
        {
        	pause.setEnabled(false);
        	if(trafficCarSimulatorIsRunning.get()) 
        	{
                
        		System.out.println(Thread.currentThread().getName() + " is now being put to a stop");
                
        		for(CarFormula color_variable: carObjectsArray) 
        		{
                    
        			color_variable.stop();
                
        		}
                for(TrafficLightIntersection color_variable: TrafficLightintersectionArray) 
                {
                
                	color_variable.stop();
               
                }
                
                trafficCarSimulatorIsRunning.set(false);
            }
        });

	}    
	
	@Override
    public void stateChanged(ChangeEvent e) 
	{
       
		//Table updates data as the slider change
		carStateData[0][1] = car_1_Slider.getValue();
		carStateData[1][1] = car_2_Slider.getValue();
		carStateData[2][1] = car_3_Slider.getValue();
		carStateData[3][1] = car_4_Slider.getValue();

        
        //Table updates speed
		carStateData[0][3] = car_1.getCarSpeed() + " kilometers/hours";
		carStateData[1][3] = car_2.getCarSpeed() + " kilometers/hours";
		carStateData[2][3] = car_3.getCarSpeed() + " kilometers/hours";
		carStateData[3][3] = car_4.getCarSpeed() + " kilometers/hours";
		
		
        //Table is updated with changed information
		carDataTable.repaint();
    
	}
	
	private void getData() 
	{
        if(trafficCarSimulatorIsRunning.get()) 
        {
        //Get colors for intersections, if Red check xPosition
	        switch(Light_1.getTrafficLightColor()) 
	        {
	            case "Red":
	                for(CarFormula color_variable: carObjectsArray) 
	                {
	                    //If car xPosition is within 500 meters and light is red, set suspend to true for car to wait
	                    if(color_variable.getX_Position()>800 && color_variable.getX_Position()<1000) 
	                    {
	                    	color_variable.carisAtLight.set(true);
	                    }
	                }
	                break;
	            
	            case "Green":
	                for(CarFormula color_variable: carObjectsArray)
	                {
	                    if(color_variable.carisAtLight.get()) 
	                    {
	                    	color_variable.resume();
	                    }
	                }
	                break;
	        }
	        
	        switch(Light_2.getTrafficLightColor())
	        {
	          
	        	case "Red":
	                for(CarFormula color_variable: carObjectsArray) 
	                {
	                    //If car xPosition is within 500 meters and light is red, set suspend to true for car to wait
	                    if(color_variable.getX_Position()>1800 && color_variable.getX_Position()<2000)
	                    {
	                    
	                    	color_variable.carisAtLight.set(true);
	                   
	                    }
	                
	                }
	               
	                break;
	            
	        	case "Green":
	                for(CarFormula color_variable:carObjectsArray) 
	                {
	                   
	                	if(color_variable.carisAtLight.get()) 
	                	{
	                    
	                		color_variable.resume();
	                  
	                	}
	                
	                }
	               
	                break;
	       
	        }
	        
	        switch(Light_3.getTrafficLightColor()) 
	        {
	            case "Red":
	                for(CarFormula color_variable: carObjectsArray)
	                {
	                    //If car xPosition is within 500 meters and light is red, set suspend to true for car to wait
	                    
	                	if(color_variable.getX_Position()>2800 && color_variable.getX_Position()<3000) 
	                    {
	                   
	                		color_variable.carisAtLight.set(true);
	                   
	                    }
	               
	                }
	                break;
	            
	            case "Green":
	                for(CarFormula color_variable: carObjectsArray) 
	                {
	                    if(color_variable.carisAtLight.get())
	                    {
	                    	color_variable.resume();
	                    }
	                }
	                break;
	        }
        }
        
    }
    
	
	@Override
    public void run() 
	{
        
		while(carIsRunning) 
        {
            
			//Car Sliders are set to running, if the simulator is running. 
            if(trafficCarSimulatorIsRunning.get()) 
            {
            
            	car_1_Slider.setValue(car_1.getX_Position());
            	car_2_Slider.setValue(car_2.getX_Position());
            	car_3_Slider.setValue(car_3.getX_Position());
            	car_4_Slider.setValue(car_4.getX_Position());

            	getData();
            }
        }
    }
	
	public static void main(String[] args) 
	{
	 
		// executes project3 code
		
		trafficLightandCarGui test = new trafficLightandCarGui();
		
		test.display();
		gui = new Thread(test);
		Thread currentTime = new Thread(new CurrentTime());
		currentTime.start();
		
	}

}
