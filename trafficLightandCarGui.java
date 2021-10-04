package CMSC335_project3;


import java.awt.Dimension;

import javax.swing.*;



public class trafficLightandCarGui extends JFrame 
{
	
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

	//creates 3 runnable car objects and a thread for each car object
	CarFormula car_1 = new CarFormula("Car_1Thread", 200, 0);
	CarFormula car_2 = new CarFormula("Car_2Thread", 600, 0);
	CarFormula car_3 = new CarFormula("Car_3Thread", 1500, 500);

	//This is an array that allows for a loop.
	CarFormula[] carObjectsArray = {car_1, car_2, car_3};
	static Thread gui;
	
	
	Object [][] carStateData = { {"Car #1", car_1.getX_Position(), 0, 0}, {"Car #2", car_2.getX_Position() , 0 ,0}, {"Car #3", car_3.getX_Position(), 0 ,0}};
	
	
	String[] columnNameLabels = {"Car#" , "X-Position" , "Y-Position" , "Speed(in Kilometers per hour)"};
	JTable carDataTable = new JTable(carStateData, columnNameLabels);
		
	

	public trafficLightandCarGui()
	{
		
		super("CMSC335_Project3_Nathan_Ma's_Traffic_and_Car_Tracker");
		createGUI();
		
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
		
		//labels the intersections 1, 2 and 3)
		JLabel trafficLight1at1000 = new JLabel("Intersection 1 (1000 Meters):");
		JLabel trafficLight2at2000 = new JLabel("Intersection 2 (2000 Meters):");
		JLabel trafficLight3at3000 = new JLabel("Intersection 3 (3000 Meters):");
		
		car_1_Slider.setValue(car_1.getX_Position());
		car_2_Slider.setValue(car_2.getX_Position());
		car_3_Slider.setValue(car_3.getX_Position());
		
		
		car_1_Slider.setPaintTicks(true);
		car_1_Slider.setPaintTrack(true);
		car_1_Slider.setPaintLabels(true);
		car_1_Slider.setMajorTickSpacing(1000);
		car_1_Slider.setMinorTickSpacing(100);
		
		
		
		car_2_Slider.setPaintTicks(true);
		car_2_Slider.setPaintTrack(true);
		car_2_Slider.setPaintLabels(true);
		car_2_Slider.setMajorTickSpacing(1000);
		car_2_Slider.setMinorTickSpacing(100);
		
		car_3_Slider.setPaintTicks(true);
		car_3_Slider.setPaintTrack(true);
		car_3_Slider.setPaintLabels(true);
		car_3_Slider.setMajorTickSpacing(1000);
		car_3_Slider.setMinorTickSpacing(100);
		
		carDataTable.setPreferredScrollableViewportSize(new Dimension(400, 50));
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
						.addComponent(greetingTitle).addComponent(userInstruction1)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						 .addGroup(layout.createSequentialGroup()    
				                    .addComponent(start).addComponent(pause).addComponent(stop)))       
				                    .addComponent(car_1_Slider).addComponent(car_2_Slider).addComponent(car_3_Slider) 			                  
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addGroup(layout.createSequentialGroup()
								.addComponent(trafficLight1at1000).addComponent(trafficLight1at1000Text).addContainerGap(20,20) 
								.addComponent(trafficLight2at2000).addComponent(trafficLight2at2000Text).addContainerGap(20,20)								
								.addComponent(trafficLight3at3000).addComponent(trafficLight3at3000Text))						
								.addComponent(carDataPanel)))
				.addContainerGap(10,10)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createSequentialGroup().addComponent(greetingTitle).addComponent(userInstruction1))
					.addGap(10, 10, 10)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(start).addComponent(pause).addComponent(stop))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(car_1_Slider).addComponent(car_2_Slider).addComponent(car_3_Slider))
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
	
	
	public static void main(String[] args) 
	{
	
		// executes project3 code
		
		trafficLightandCarGui test = new trafficLightandCarGui();
		
		test.display();
		
	}

}
