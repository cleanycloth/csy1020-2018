/*
Program:	CCarCrash - A car maze navigation program
Filename:	CCarCrash.java // CCarCrash.class
@author:	Aidan Rayner (18415915)
Course: 	BSc Computing
Module:		CSY1020 Problem Solving and Programming
Tutor:		Gary Hill
@version: 	1.0 - Release Build with AI
Date: 		Build 1.0 date 5/5/19 @ 3:34pm
*/
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class CCarCrash extends JFrame implements ActionListener {
	//Added this next line to stop Java from pointlessly whinging.
	private static final long serialVersionUID = 1L;
	//Define new labels, text fields, buttons, and panels.
	//d_ tagged objects are set to be disabled (i.e. non-clickable disabled buttons).
	private JLabel OptionLabel, SquareLabel, DirectionLabel, TimerLabel, SliderLabel, TimerSepLabel, TimerSepLabel2;
	private JTextField OptionText, SquareText, DirectionText, HourText, MinuteText, SecondText;
	private JButton ExitButton, UpButton, DownButton, LeftButton, RightButton, d_UpLeftButton, d_UpRightButton, d_CentreButton, 
	d_DownLeftButton, d_DownRightButton, ActButton, RunButton, ResetButton, Op1Button,Op2Button, Op3Button, CompassButton;
	private JButton GridButtons[] = new JButton[208];
	private JPanel MainPanel, RightPanel, BottomPanel, DirectionPanel, TopPanel, ActionsPanel, SliderPanel, TimerLabelPanel, 
	TimerPanel, OptionsPanel;
	private JSlider SpeedSlider;
	private static JMenuBar MenuBar;
	private JMenu ScenarioMenu, EditMenu, ControlsMenu, HelpMenu;
	private JMenuItem ExitMenuItem, HelpMenuItem, AboutMenuItem;
	private ImageIcon CompassEast, CompassWest, CompassNorth, CompassSouth, ActIcon, RunIcon, ResetIcon, BGEmptyIcon, BGHorizTrack, 
	BGTopLeft, BGTopRight, BGVertTrack, BGBottomLeft, BGBottomRight, CarEast, CarWest, CarNorth, CarSouth; 
	//BGSandstone would've been here if I used it.
	//The next few lines describe the location of the internal and external walls, corners, and ceiling/floors.
	Integer[] ceilfloor = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,193,194,195,196,197,198,199,200,201,202,203,204,205,206};
	Integer[] walls = {16,32,48,64,80,96,112,128,144,160,176,31,47,63,79,95,111,127,143,159,175,191,207};
	Integer[] corners = {0,15,192,207}; //This was going to be used, but it never ended up working out. This is left in for legacy reasons.
	Integer[] intcorners = {34,45,162,173}; //This is not used for the generation, instead just for collision detection.
	Integer[] intwalls = {50,66,82,98,114,130,146,61,77,93,109,125,141,157};
	Integer[] intceilfloor = {35,36,37,38,39,40,41,42,43,44,163,164,165,166,167,168,169,170,171,172};
	//StartPos is the current starting position of the car. This never changes.
	int StartPos = 17;
	//This line sets the current position. When the game starts, this is the same as the start position.
	int CurrentPos = StartPos;
	//This determines if the Act/Run buttons are used. These turn the car automatically, the arrows do not.
	int AutoTurn = 0;
	//This sets the default speed of the slider.
	int CurrentSpeed = 50;
	//These next few lines set the default timer counters. They keep track of the digital timer's time.
	int HourCount = 0;
	int MinuteCount = 0;
	int SecondCount = 0;
	//This checks if the run button has been pressed. By default, it's off.
	boolean isRunning = false;
	
	//This sets up the car run timer. It checks the speed of the car every 60ms, which then will sleep the program according to the
	//speed slider. The try/catch statement is required. The program then moves the car in the direction chosen.
	Timer timer = new Timer(60, new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			AutoTurn = 1;
			try {
				Thread.sleep(CurrentSpeed);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			driveCar(DirectionText.getText());
		}
	});
	//This sets up the digital timer timer. Every 1 second, it updates.
	//If the second counter ends up being 60, it will remove 60 from the second timer and add 1 to the minute timer.
	//This also applies from minutes to hours.
	//This will also add "0" to the beginning of the timer if the counter is 9 or less.
	//This means it will show as "09" instead of just "9".
	Timer DigitalTimer = new Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			SecondCount++;
			if (SecondCount == 60) {
				SecondCount-=60;
				MinuteCount+=1;
			}
			if (MinuteCount == 60) {
				MinuteCount-=60;
				HourCount+=1;
			}
			if (SecondCount <= 9) {
				SecondText.setText("0"+Integer.toString(SecondCount));
			} 
			else {
				SecondText.setText(Integer.toString(SecondCount));
			}
			if (MinuteCount <= 9) {
				MinuteText.setText("0"+Integer.toString(MinuteCount));
			} 
			else {
				MinuteText.setText(Integer.toString(MinuteCount));
			}
			if (HourCount <= 9) {
				HourText.setText("0"+Integer.toString(HourCount));
			} 
			else {
				HourText.setText(Integer.toString(HourCount));
			}
		}
	});
	//Create main frame
	public static void main(String[] args) {
		//Set up menu bar and frame.
		MenuBar = new JMenuBar();
		CCarCrash frame;
		frame = new CCarCrash();
		//Set the title of the application and the size, then create it.
		frame.setTitle("CCarCrash - Car Race Application");
		frame.setSize(810, 650);
		frame.createGUI();
		//Disable resizing, display in the middle, give it the menu bar we created, and make it visible.
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setJMenuBar(MenuBar);
		frame.setVisible(true);
	}

	private void createPanels()
	//Generate panels to be put on the main frame.
	{
		//Create Main panel, which contains the buttons used to create the car driving grid.
		Container window = getContentPane();
		MainPanel = new JPanel();
		//Set the size to 620x530.
		MainPanel.setPreferredSize(new Dimension(620, 530));
		//Set the panel to be 16x13. Java is awkward in that GridLayout is the wrong way around.
		//This says it should be 13x16 but it's not, it's actually 16x13.
		GridLayout MainLayout = new GridLayout(13, 16);
		MainPanel.setLayout(MainLayout);
		//Make the border around it bevelled.
		MainPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		//Add to MainPanel.
		window.add(MainPanel);

		//Create right sidebar, which contains thing such as the arrow buttons, timer etc.
		RightPanel = new JPanel();
		RightPanel.setPreferredSize(new Dimension(156, 530));
		RightPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		window.add(RightPanel);

		//Create bottom bar, which contains the act/run/reset buttons, and speed slider.
		BottomPanel = new JPanel();
		BottomPanel.setPreferredSize(new Dimension(797, 50));
		BottomPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		window.add(BottomPanel);

		//Create Top panel for right sidebar. Contains option, square, and direction boxes.
		TopPanel = new JPanel();
		TopPanel.setPreferredSize(new Dimension(150, 100));

		//Create Direction panel for right sidebar. Contains the up/down/left/right buttons.
		DirectionPanel = new JPanel();
		DirectionPanel.setPreferredSize(new Dimension(150, 125));

		//Create Timer Label panel for the right sidebar. This contains the "DIGITAL TIMER" text.
		TimerLabelPanel = new JPanel();
		TimerLabelPanel.setPreferredSize(new Dimension(150, 110));

		//Create Options panel for the right sidebar. This contains the options 1/2/3 buttons and Exit.
		OptionsPanel = new JPanel();
		OptionsPanel.setPreferredSize(new Dimension(150, 70));
		GridLayout OptionsLayout = new GridLayout(2, 2);
		OptionsPanel.setLayout(OptionsLayout);

		//Create Actions panel, which will show the Act/Run/Reset buttons later on.
		ActionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		ActionsPanel.setPreferredSize(new Dimension(470, 40));
		BottomPanel.add(ActionsPanel);

		//Create the slider panel., which will be used for the speed.
		SliderPanel = new JPanel();
		//GridBagConstraints is used to keep the slider in the correct position.
		GridBagConstraints GridBagConstraint = new GridBagConstraints();
		SliderPanel.setPreferredSize(new Dimension(290, 40));
		SliderPanel.setLayout(new GridBagLayout());
		//Set the X and Y axis in the GridBag to zero. This centres it.
		GridBagConstraint.gridx = 0;
		GridBagConstraint.gridy = 0;
		//Add the panel.
		BottomPanel.add(SliderPanel);

		

	}
	//Source: https://www.wikitechy.com/technology/can-test-array-contains-certain-value/
	//Scroll to the bottom of the page to find the code used.
	//This is used to check if a number is in an array. These are given in the arguments.
	public static boolean contains(Integer[] arr, Integer item) {
		return Arrays.stream(arr).anyMatch(item::equals);
	}

	public void createGUI()
	//Generate interface in main frame.
	//This involves actually creating the extra panels, buttons etc.
	{
		//Make sure the program closes when you press the exit button.
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Find the active window and set it as "window".
		Container window = getContentPane();
		//Give this window the flow layout. This makes it easier to place things on screen.
		window.setLayout(new FlowLayout());

		//Run createPanels, which is shown above.
		createPanels();

		//Add the TopPanel to the panel on the right. This one contains Option/Square/Direction.
		RightPanel.add(TopPanel);
		//Set layout to be 2x3.
		GridLayout topLayout = new GridLayout(3, 2);
		TopPanel.setLayout(topLayout);

		//Add the option label and field.
		OptionLabel = new JLabel("Option:");
		OptionText = new JTextField("1");
		//Centre the text in the box.
		OptionText.setHorizontalAlignment(JTextField.CENTER);
		TopPanel.add(OptionLabel);
		TopPanel.add(OptionText);

		//Add the square field and label.
		SquareLabel = new JLabel("Square:");
		SquareText = new JTextField(Integer.toString(StartPos));
		SquareText.setHorizontalAlignment(JTextField.CENTER);
		TopPanel.add(SquareLabel);
		TopPanel.add(SquareText);

		//Add the direction field and label.
		DirectionLabel = new JLabel("Direction:");
		DirectionText = new JTextField();
		DirectionText.setHorizontalAlignment(JTextField.CENTER);
		TopPanel.add(DirectionLabel);
		TopPanel.add(DirectionText);

		//Place Direction buttons panel
		GridLayout directionLayout = new GridLayout(3, 3);
		DirectionPanel.setLayout(directionLayout);
		RightPanel.add(DirectionPanel);

		//Place Timer Container
		GridLayout timerLabelPanelLayout = new GridLayout(5, 1);
		TimerLabelPanel.setLayout(timerLabelPanelLayout);
		RightPanel.add(TimerLabelPanel);

		//Create Timer Label. This is given its own panel and is added first.
		TimerLabel = new JLabel("DIGITAL TIMER");
		TimerLabel.setHorizontalAlignment(JLabel.CENTER);
		TimerLabelPanel.add(TimerLabel);

		//Create actual Timer. Use "00" as default values. Display the labels in the TimerPanel.
		TimerPanel = new JPanel();
		TimerSepLabel = new JLabel(":");
		TimerSepLabel2 = new JLabel(":");
		HourText = new JTextField("00");
		MinuteText = new JTextField("00");
		SecondText = new JTextField("00");
		
		TimerSepLabel.setHorizontalAlignment(JLabel.CENTER);
		TimerSepLabel2.setHorizontalAlignment(JLabel.CENTER);

		GridLayout timerLayout = new GridLayout(1, 5);
		TimerPanel.setLayout(timerLayout);
		TimerLabelPanel.add(TimerPanel);
		
		//Create the timer text boxes.
		//Disallow user input.
		HourText.setEnabled(false);
		//Set background colour to black.
		HourText.setBackground(Color.BLACK);
		//Set the text colour to white.
		HourText.setDisabledTextColor(Color.WHITE);
		//Add it to the panel.
		TimerPanel.add(HourText);
		
		//Add the separator label.
		TimerPanel.add(TimerSepLabel);
		//See comments above. for the next ~15 lines.
		MinuteText.setEnabled(false);
		MinuteText.setBackground(Color.BLACK);
		MinuteText.setDisabledTextColor(Color.WHITE);
		TimerPanel.add(MinuteText);
		
		TimerPanel.add(TimerSepLabel2);
		
		SecondText.setEnabled(false);
		SecondText.setBackground(Color.BLACK);
		SecondText.setDisabledTextColor(Color.WHITE);
		TimerPanel.add(SecondText);

		RightPanel.add(OptionsPanel);

		//Create directional buttons
		d_UpLeftButton = new JButton("");
		UpButton = new JButton("↑");
		d_UpRightButton = new JButton("");
		LeftButton = new JButton("←");
		d_CentreButton = new JButton("");
		RightButton = new JButton("→");
		d_DownLeftButton = new JButton("");
		DownButton = new JButton("↓");
		d_DownRightButton = new JButton("");

		//Add the buttons in the correct order. 
		//Disabled, then direction, then disabled, then direction, and so on.
		DirectionPanel.add(d_UpLeftButton);
		DirectionPanel.add(UpButton);		
		DirectionPanel.add(d_UpRightButton);
		DirectionPanel.add(LeftButton);
		DirectionPanel.add(d_CentreButton);
		DirectionPanel.add(RightButton);
		DirectionPanel.add(d_DownLeftButton);
		DirectionPanel.add(DownButton);
		DirectionPanel.add(d_DownRightButton);

		//Give all four direction buttons action listeners.
		UpButton.addActionListener(this);
		LeftButton.addActionListener(this);
		RightButton.addActionListener(this);
		DownButton.addActionListener(this);

		//Create option buttons
		Op1Button = new JButton("Option 1");
		Op2Button = new JButton("Option 2");
		Op3Button = new JButton("Option 3");
		ExitButton = new JButton("Exit");

		//Add them to the options panel.
		OptionsPanel.add(Op1Button);
		OptionsPanel.add(Op2Button);
		OptionsPanel.add(Op3Button);
		OptionsPanel.add(ExitButton);

		//Give them all 1px margins.
		Op1Button.setMargin(new Insets(1, 1, 1, 1));
		Op2Button.setMargin(new Insets(1, 1, 1, 1));
		Op3Button.setMargin(new Insets(1, 1, 1, 1));
		ExitButton.setMargin(new Insets(1, 1, 1, 1));

		//Give all buttons action listeners.
		Op1Button.addActionListener(this);
		Op2Button.addActionListener(this);
		Op3Button.addActionListener(this);
		ExitButton.addActionListener(this);

		//Create a label for the slider.
		SliderLabel = new JLabel("Speed:");
		//Add the label.
		SliderPanel.add(SliderLabel);
		
		//Create the slider. 
		SpeedSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		GridBagConstraints GridBagConstraint = new GridBagConstraints();
		//Instead of being 0-100, invert it so it is 100-0.
		SpeedSlider.setInverted(true);
		//Whenever the slider is changed, listen out for it and change the current speed accordingly
		//by retrieving the value of the slider.
		SpeedSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				CurrentSpeed = ((JSlider)e.getSource()).getValue();
			}
		});
		//Set the slider to be slightly lower. This makes it look centred.
		GridBagConstraint.gridx = 0;
		GridBagConstraint.gridy = 1;
		//Set the major slider marks to be 10 apart, and the minor ones 5 apart. Add them to the slider.
		SpeedSlider.setMajorTickSpacing(10);
		SpeedSlider.setMinorTickSpacing(5);
		SpeedSlider.setPaintTicks(true);
		//Add the slider.
		SliderPanel.add(SpeedSlider);
		
		//Action buttons
		ActButton = new JButton("Act");
		RunButton = new JButton("Run");
		ResetButton = new JButton("Reset");
		
		//Set the size of the buttons to have 10px either side of them.
		ActButton.setMargin(new Insets(0, 10, 0, 10));
		RunButton.setMargin(new Insets(0, 10, 0, 10));
		ResetButton.setMargin(new Insets(0, 10, 0, 10));
		//Set the icons to the appropriate files.
		ActIcon = new ImageIcon("resources/step.png");
		RunIcon = new ImageIcon("resources/run.png");
		ResetIcon = new ImageIcon("resources/reset.png");
		//Give the buttons their icons.
		ActButton.setIcon(ActIcon);
		RunButton.setIcon(RunIcon);
		ResetButton.setIcon(ResetIcon);
		//Add the buttons to the panel.
		ActionsPanel.add(ActButton);
		ActionsPanel.add(RunButton);
		ActionsPanel.add(ResetButton);
		//Add listeners to them to detect button presses.
		ActButton.addActionListener(this);
		RunButton.addActionListener(this);
		ResetButton.addActionListener(this);

		//Create menu bar and menu items
		ScenarioMenu = new JMenu("Scenario");
		EditMenu = new JMenu("Edit");
		ControlsMenu = new JMenu("Controls");
		HelpMenu = new JMenu("Help");
		
		//Add them to the menu bar.
		MenuBar.add(ScenarioMenu);
		MenuBar.add(EditMenu);
		MenuBar.add(ControlsMenu);
		MenuBar.add(HelpMenu);

		//Create selections for menu items
		ExitMenuItem = new JMenuItem("Exit");
		HelpMenuItem = new JMenuItem("Help Topics");
		AboutMenuItem = new JMenuItem("About");
		
		//Add them to the menus themselves. This turns them into menu items.
		ScenarioMenu.add(ExitMenuItem);
		HelpMenu.add(HelpMenuItem);
		HelpMenu.add(AboutMenuItem);

		//Give them action listeners.
		ExitMenuItem.addActionListener(this);
		HelpMenuItem.addActionListener(this);
		AboutMenuItem.addActionListener(this);

		//Create compass button
		CompassButton = new JButton();
		RightPanel.add(CompassButton);
		CompassButton.addActionListener(this);
		CompassButton.setMargin(new Insets(1, 1, 1, 1));

		//Disable non-clickable buttons
		d_UpLeftButton.setEnabled(false);
		d_UpRightButton.setEnabled(false);
		d_DownLeftButton.setEnabled(false);
		d_DownRightButton.setEnabled(false);
		d_CentreButton.setEnabled(false);

		//Load Icons
		BGEmptyIcon = new ImageIcon("resources/space.png");
		BGHorizTrack = new ImageIcon("resources/wall-horiz.png");
		BGVertTrack = new ImageIcon("resources/wall-vert.png");
		BGTopLeft = new ImageIcon("resources/wall-NW.png");
		BGTopRight = new ImageIcon("resources/wall-NE.png");
		BGBottomLeft = new ImageIcon("resources/wall-SW.png");
		BGBottomRight = new ImageIcon("resources/wall-SE.png");
		//BGSandstone = new ImageIcon("resources/sandstone.jpg"); -  This was going to be used but never was.
		//The next four lines import the car pictures. getScaledInstance is used to scale the images down as the images are too big.
		//They are resized to be 40x40px, and use the default scaling method to do so.
		CarEast = new ImageIcon(new ImageIcon("resources/car-e.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)); //Resize image to 40x40
		CarWest = new ImageIcon(new ImageIcon("resources/car-w.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)); //Resize image to 40x40
		CarNorth = new ImageIcon(new ImageIcon("resources/car-n.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));//Resize image to 40x40
		CarSouth = new ImageIcon(new ImageIcon("resources/car-s.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));//Resize image to 40x40
		CompassEast = new ImageIcon("resources/east.jpg");
		CompassWest = new ImageIcon("resources/west.jpg");
		CompassNorth = new ImageIcon("resources/north.jpg");
		CompassSouth = new ImageIcon("resources/south.jpg");
		
		//Run LoadFrontend() which creates the grid of buttons.
		LoadFrontend();
	}//End of createGUI();

	//Add buttons to main window
	public void LoadFrontend() {
		//Run 207 times.
		for (int nC = 0; nC < 208; nC++) {
			//Create the button, store it in the array.
			GridButtons[nC] = new JButton();
			//Add it to the screen.
			MainPanel.add(GridButtons[nC]);
			//Remove the border and make them unclickable.
			GridButtons[nC].setBorderPainted(false);
			GridButtons[nC].setEnabled(false);
			//These next four if statements set the corners of the external and internal boxes.
			//Top left corners
			if (nC == 0 | nC == 34) {
				//With the way Java works, if the icon is disabled, the icon AND the disabled icon both need to be set.
				GridButtons[nC].setIcon(BGTopLeft);
				GridButtons[nC].setDisabledIcon(BGTopLeft);
			} 
			//Top right corners
			else if (nC == 15 | nC == 45) {
				GridButtons[nC].setIcon(BGTopRight);
				GridButtons[nC].setDisabledIcon(BGTopRight);
			} 
			//Bottom left corners
			else if (nC == 192 | nC == 162) {
				GridButtons[nC].setIcon(BGBottomLeft);
				GridButtons[nC].setDisabledIcon(BGBottomLeft);
			} 
			//Bottom right corners
			else if (nC == 207 | nC == 173) {
				GridButtons[nC].setIcon(BGBottomRight);
				GridButtons[nC].setDisabledIcon(BGBottomRight);
			} 
			//If the value of the counter is found in the ceilfloor/intceilfloor arrays
			//(which defines the top and bottom of the internal and external boxes)
			//set the icons to be horizontal.
			else if (contains(ceilfloor, nC) | contains(intceilfloor, nC)) {
				GridButtons[nC].setIcon(BGHorizTrack);
				GridButtons[nC].setDisabledIcon(BGHorizTrack);
			} 
			//If the value of the counter is found in the walls/intwalls arrays
			//(which defines the sides of the internal and external boxes)
			//set the icons to be vertical.
			else if (contains(intwalls, nC) | contains(walls, nC)) {
				GridButtons[nC].setIcon(BGVertTrack);
				GridButtons[nC].setDisabledIcon(BGVertTrack);
			} 
			//If the value is not found in any of the arrays, set the icon to be
			//the blank icon.
			else {
				GridButtons[nC].setIcon(BGEmptyIcon);
				GridButtons[nC].setDisabledIcon(BGEmptyIcon);
			}
			//This is unused code. It was going to set the bottom left corner,
			//but I didn't end up implementing this.
			/*if (nC == 177) {
				GridButtons[nC].setIcon(BGSandstone);
				GridButtons[nC].setDisabledIcon(BGSandstone);}*/
		}
		//Reset the position of the car to the original starting position.
		CurrentPos = StartPos;
		//Reset the compass icon to be east, and set the Direction text box to this.
		//This is the default direction the car faces.
		CompassButton.setIcon(CompassEast);
		DirectionText.setText("East");
		//Set the square text box to be the current position of the car, in this case, 17.
		SquareText.setText(Integer.toString(CurrentPos));
		//Place the car at square 17.
		GridButtons[StartPos].setIcon(CarEast);
		GridButtons[StartPos].setDisabledIcon(CarEast);
	}

	public void ReloadFrontend() {
		//Run this 207 times. Delete all of the icons on screen.
		for (int nC = 0; nC < 208; nC++) {MainPanel.remove(GridButtons[nC]);}
		//Reset all timer counters to zero.
		HourCount = 0;
		MinuteCount = 0;
		SecondCount = 0;
		//Reset the displays to zero for the counter.
		HourText.setText("00");
		MinuteText.setText("00");
		SecondText.setText("00");
		//Reload the icons again.
		LoadFrontend();
	}

	public void driveCar(String direction) {
		//Check what direction the car is facing. If it is "West", perform this action.
		//.equals is used as Java can be fussy when == "West" is used.
		if ("West".equals(direction)) {
			//If the car's position in front of it isn't contained in one of the arrays defining the walls, continue.
			if (!contains(intwalls, CurrentPos - 1) && !contains(walls, CurrentPos - 1) && !contains(intcorners, CurrentPos - 1)) {
				//Take one off the current position to make the car go west.
				CurrentPos--;
				//Set the icon of the car to be one step in front.
				GridButtons[CurrentPos].setIcon(CarWest);
				GridButtons[CurrentPos].setDisabledIcon(CarWest);
				//Place a blank icon behind the car to create the illusion of the car moving forwards.
				GridButtons[CurrentPos + 1].setIcon(BGEmptyIcon);
				GridButtons[CurrentPos + 1].setDisabledIcon(BGEmptyIcon);
				//Set the icon of the compass to be west.
				CompassButton.setIcon(CompassWest);
				//Update the direction of the Direction field to be where the car is driving.
				DirectionText.setText(direction);
			}
			else { //If the run or act button has been pressed, AutoTurn will be active. If so, turn the car 90 degrees.
				   //In this case, this would turn the car north.
				if (AutoTurn == 1) {driveCar("North");}
			}
		}//The code below is very much the same as above, but the appropriate directions are chosen and the car steps accordingly.
		if ("East".equals(direction)) {
			if (!contains(intwalls, CurrentPos + 1) && !contains(walls, CurrentPos + 1) && !contains(intcorners, CurrentPos + 1)) {
				//Add 1 to the car's position to make it go east.
				CurrentPos++;
				GridButtons[CurrentPos].setIcon(CarEast);
				GridButtons[CurrentPos].setDisabledIcon(CarEast);
				GridButtons[CurrentPos - 1].setIcon(BGEmptyIcon);
				GridButtons[CurrentPos - 1].setDisabledIcon(BGEmptyIcon);
				CompassButton.setIcon(CompassEast);
				DirectionText.setText(direction);
			}
			else {//If the run or act button has been pressed, AutoTurn will be active. If so, turn the car 90 degrees.
				  //In this case, this would turn the car south.
				if (AutoTurn == 1) {driveCar("South");}
			}
		}
		if ("North".equals(direction)) {
			if (!contains(ceilfloor, CurrentPos - 16) && !contains(intceilfloor, CurrentPos - 16) && !contains(intcorners, CurrentPos - 16)) {
				//16 is taken away here as the car needs to move an entire row downwards. One row is 16 blocks long.
				CurrentPos -= 16;
				GridButtons[CurrentPos].setIcon(CarNorth);
				GridButtons[CurrentPos].setDisabledIcon(CarNorth);
				GridButtons[CurrentPos + 16].setIcon(BGEmptyIcon);
				GridButtons[CurrentPos + 16].setDisabledIcon(BGEmptyIcon);
				CompassButton.setIcon(CompassNorth);
				DirectionText.setText(direction);
			}
			else {//If the run or act button has been pressed, AutoTurn will be active. If so, turn the car 90 degrees.
				  //In this case, this would turn the car east.
				if (AutoTurn == 1) {driveCar("East");}
			}
		}
		if ("South".equals(direction)) {
			if (!contains(ceilfloor, CurrentPos + 16) && !contains(intceilfloor, CurrentPos + 16) && !contains(intcorners, CurrentPos + 16)) {
				//16 is added here as the car needs to move an entire row upwards. 
				CurrentPos += 16;
				GridButtons[CurrentPos].setIcon(CarSouth);
				GridButtons[CurrentPos].setDisabledIcon(CarSouth);
				GridButtons[CurrentPos - 16].setIcon(BGEmptyIcon);
				GridButtons[CurrentPos - 16].setDisabledIcon(BGEmptyIcon);
				CompassButton.setIcon(CompassSouth);
				DirectionText.setText(direction);
			} 
			else {//If the run or act button has been pressed, AutoTurn will be active. If so, turn the car 90 degrees.
				//In this case, this would turn the car west.
				if (AutoTurn == 1) {driveCar("West");}
			}
		}
		//Set the square text field to be the current position of the car.
		SquareText.setText(Integer.toString(CurrentPos));
		//Disable auto turn. This means if the act button is pressed, when the arrows are pressed afterwards
		//the car won't automatically turn when it shouldn't. The run button will re-enable this anyway.
		AutoTurn = 0;
	}

	public void actionPerformed(ActionEvent event) {
		//Find out what button was pressed.
		Object source = event.getSource();
		//If the exit button or the exit menu item under Scenario was clicked, present a message.
		if ((source == ExitButton) | (source == ExitMenuItem)) {
			int exitquestion = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			//exitquestion gives off state codes. If it is zero, i.e. "yes" was chose, the program will quit. 
			//Any other inputs will not quit and just close the box.
			if (exitquestion == 0) {System.exit(0);}
		}
		if (source == HelpMenuItem) {
			//Display a message box explaining how the program is used.
			JOptionPane.showMessageDialog(null,"The program is used as follows:\nUp Arrow: Up\nDown Arrow: Down\nLeft Arrow: Left\nRight Arrow: Right\nNote: On screen, not keyboard.\n\nAct performs one step.\nRun drives the car automatically.\nReset will reset all on screen values,\nand reset all positions.\nOption buttons for different modes.","Quick Help", JOptionPane.PLAIN_MESSAGE, new ImageIcon("resources/helpbook.png"));
		}
		if (source == AboutMenuItem) {
			//Display a message box explaining my information.
			JOptionPane.showMessageDialog(null,"This application has been written by Aidan Rayner,\nfor the University of Northampton CSY1020\nProblem Solving and Programming Course, in Term 2.","About This Program", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("resources/user.png"));
		}
		if (source == CompassButton) {
			//Display the current direction along with the appropriate compass icon.
			JOptionPane.showMessageDialog(null, "The current direction is: " + DirectionText.getText(),"Current Direction", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("resources/" + DirectionText.getText().toLowerCase() + ".jpg"));
		}
		//Depending on what buttons were pressed, perform different actions.
		//In this case, the left button makes the car go West, the right button East, and so on.
		//Reset will run the ReloadFrontend funciton above.
		if (source == LeftButton) {driveCar("West");}
		if (source == RightButton) {driveCar("East");}
		if (source == UpButton) {driveCar("North");}
		if (source == DownButton) {driveCar("South");}
		if (source == ResetButton) {ReloadFrontend();}
		if (source == Op1Button) {
			ReloadFrontend();
			//Reload the frontend and set the option text box to "1".
			OptionText.setText("1");
		}
		if (source == ActButton) {
			//Enable autoturn. This will make the car turn itself when it gets to a corner.
			AutoTurn = 1;
			//Move the car in the direction it is currently facing. This will be changed when it gets to a corner.
			driveCar(DirectionText.getText());
		}
		if (source == RunButton) {
			//If the program is running, do this bit of code.
			if (isRunning) {
				//Set the text of the run button to "Run" and set the appropriate icon.
				RunButton.setText("Run");
				RunIcon = new ImageIcon("resources/run.png");
				RunButton.setIcon(RunIcon);
				//Re-enable the act and reset buttons, and all three option buttons.
				ActButton.setEnabled(true);
				ResetButton.setEnabled(true);
				Op1Button.setEnabled(true);
				Op2Button.setEnabled(true);
				Op3Button.setEnabled(true);
				//Set isRunning to false. This means the program is not running.
				isRunning = false;
				//Stop all timers.
				timer.stop();
				DigitalTimer.stop();
			}
			else {//If the program is not running, run it.
				//Set the text of the run button to "pause" and set the appropriate icon.
				RunButton.setText("Pause");
				RunIcon = new ImageIcon("resources/pause.png");
				RunButton.setIcon(RunIcon);
				//Disable the act and reset buttons. This means the user cannot make one step
				//or reset the application whilst the code is running.
				//All three option buttons are disabled to disallow layout changes.
				ActButton.setEnabled(false);
				ResetButton.setEnabled(false);
				Op1Button.setEnabled(false);
				Op2Button.setEnabled(false);
				Op3Button.setEnabled(false);
				//Set isRunning to true. This means the program is running.
				isRunning = true;
				//Start the car movement timer and digital timer.
				timer.start();
				DigitalTimer.start();
			}
		}
	}	
}