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
	private JButton ExitButton, UpButton, DownButton, LeftButton, RightButton, d_UpLeftButton, d_UpRightButton, d_CentreButton, d_DownLeftButton, d_DownRightButton, ActButton, RunButton, ResetButton, Op1Button,Op2Button, Op3Button, CompassButton;
	private JButton GridButtons[] = new JButton[208];
	private JPanel MainPanel, RightPanel, BottomPanel, DirectionPanel, TopPanel, ActionsPanel, SliderPanel, TimerLabelPanel, TimerPanel, OptionsPanel;
	private JSlider SpeedSlider;
	private static JMenuBar MenuBar;
	private JMenu ScenarioMenu, EditMenu, ControlsMenu, HelpMenu;
	private JMenuItem ExitMenuItem, HelpMenuItem, AboutMenuItem;
	private ImageIcon CompassEast, CompassWest, CompassNorth, CompassSouth, ActIcon, RunIcon, ResetIcon, BGEmptyIcon, BGHorizTrack, BGTopLeft, BGTopRight, BGVertTrack, BGBottomLeft, BGBottomRight, CarEast, CarWest, CarNorth, CarSouth; //BGSandstone
	Integer[] intbox = {67,83,99,115,131,147,76,92,108,124,140,156};
	Integer[] walls = {16,32,48,64,80,96,112,128,144,160,176,31,47,63,79,95,111,127,143,159,175,191,207};
	Integer[] ceilfloor = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,193,194,195,196,197,198,199,200,201,202,203,204,205,206};
	Integer[] intceilfloor = {52,53,54,55,56,57,58,59,148,149,150,151,152,153,154,155};
	Integer[] intcorners = {51,60,147,156};
	int StartPos = 17;
	int CurrentPos = StartPos;
	int AutoTurn = 0;
	int CurrentSpeed = 50;
	int HourCount = 0;
	int MinuteCount = 0;
	int SecondCount = 0;
	boolean isRunning = false;
	
	Timer timer = new Timer(1, new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			AutoTurn = 1;
			try {
				Thread.sleep(CurrentSpeed);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			MoveCar(DirectionText.getText());
		}
	});

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
		MenuBar = new JMenuBar();
		CCarCrash frame;
		frame = new CCarCrash();
		frame.setTitle("CCarCrash - Car Race Application");
		frame.setSize(810, 650);
		frame.createGUI();
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
		MainPanel.setPreferredSize(new Dimension(620, 530));
		GridLayout MainLayout = new GridLayout(13, 16);
		MainPanel.setLayout(MainLayout);
		MainPanel.setBorder(BorderFactory.createRaisedBevelBorder());
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

		ActionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		ActionsPanel.setPreferredSize(new Dimension(470, 40));
		BottomPanel.add(ActionsPanel);

		SliderPanel = new JPanel();
		GridBagConstraints GridBagConstraint = new GridBagConstraints();
		SliderPanel.setPreferredSize(new Dimension(290, 40));
		SliderPanel.setLayout(new GridBagLayout());
		SliderLabel = new JLabel("Speed:");
		BottomPanel.add(SliderPanel);
		
		GridBagConstraint.gridx = 0;
		GridBagConstraint.gridy = 0;
		SliderPanel.add(SliderLabel);
		SpeedSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		SpeedSlider.setInverted(true);
		SpeedSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				CurrentSpeed = ((JSlider)e.getSource()).getValue();
				System.out.println(CurrentSpeed);
			}
		});
		GridBagConstraint.gridx = 0;
		GridBagConstraint.gridy = 1;
		SpeedSlider.setMajorTickSpacing(10);
		SpeedSlider.setMinorTickSpacing(5);
		SpeedSlider.setPaintTicks(true);
		SliderPanel.add(SpeedSlider);
		

		//Action buttons
		ActButton = new JButton("Act");
		RunButton = new JButton("Run");
		ResetButton = new JButton("Reset");

		ActButton.setMargin(new Insets(0, 10, 0, 10));
		RunButton.setMargin(new Insets(0, 10, 0, 10));
		ResetButton.setMargin(new Insets(0, 10, 0, 10));

		ActIcon = new ImageIcon("resources/step.png");
		RunIcon = new ImageIcon("resources/run.png");
		ResetIcon = new ImageIcon("resources/reset.png");

		ActButton.setIcon(ActIcon);
		RunButton.setIcon(RunIcon);
		ResetButton.setIcon(ResetIcon);

		ActionsPanel.add(ActButton);
		ActionsPanel.add(RunButton);
		ActionsPanel.add(ResetButton);

		ActButton.addActionListener(this);
		RunButton.addActionListener(this);
		ResetButton.addActionListener(this);

	}
	// TODO Find source of this code and source it
	public static boolean contains(Integer[] arr, Integer item) {
		return Arrays.stream(arr).anyMatch(item::equals);
	}

	public void createGUI()
	//Generate interface in main frame.
	//This involves actually creating the extra panels, buttons etc.
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container window = getContentPane();
		window.setLayout(new FlowLayout());

		createPanels();

		RightPanel.add(TopPanel);
		GridLayout topLayout = new GridLayout(3, 2);
		TopPanel.setLayout(topLayout);

		OptionLabel = new JLabel("Option:");
		OptionText = new JTextField("1");
		OptionText.setHorizontalAlignment(JTextField.CENTER);
		TopPanel.add(OptionLabel);
		TopPanel.add(OptionText);

		SquareLabel = new JLabel("Square:");
		SquareText = new JTextField(Integer.toString(StartPos));
		SquareText.setHorizontalAlignment(JTextField.CENTER);
		TopPanel.add(SquareLabel);
		TopPanel.add(SquareText);

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

		//Create Timer Label
		TimerLabel = new JLabel("DIGITAL TIMER");
		TimerLabel.setHorizontalAlignment(JLabel.CENTER);
		TimerLabelPanel.add(TimerLabel);

		//Create actual Timer
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
		
		
		HourText.setEnabled(false);
		HourText.setBackground(Color.BLACK);
		HourText.setDisabledTextColor(Color.WHITE);
		TimerPanel.add(HourText);
		
		TimerPanel.add(TimerSepLabel);
		
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

		DirectionPanel.add(d_UpLeftButton);
		DirectionPanel.add(UpButton);		
		DirectionPanel.add(d_UpRightButton);
		DirectionPanel.add(LeftButton);
		DirectionPanel.add(d_CentreButton);
		DirectionPanel.add(RightButton);
		DirectionPanel.add(d_DownLeftButton);
		DirectionPanel.add(DownButton);
		DirectionPanel.add(d_DownRightButton);

		UpButton.addActionListener(this);
		LeftButton.addActionListener(this);
		RightButton.addActionListener(this);
		DownButton.addActionListener(this);

		//Create option buttons
		Op1Button = new JButton("Option 1");
		Op2Button = new JButton("Option 2");
		Op3Button = new JButton("Option 3");
		ExitButton = new JButton("Exit");

		OptionsPanel.add(Op1Button);
		OptionsPanel.add(Op2Button);
		OptionsPanel.add(Op3Button);
		OptionsPanel.add(ExitButton);

		Op1Button.setMargin(new Insets(1, 1, 1, 1));
		Op2Button.setMargin(new Insets(1, 1, 1, 1));
		Op3Button.setMargin(new Insets(1, 1, 1, 1));
		ExitButton.setMargin(new Insets(1, 1, 1, 1));

		Op1Button.addActionListener(this);
		Op2Button.addActionListener(this);
		Op3Button.addActionListener(this);
		ExitButton.addActionListener(this);

		//Create menu bar and menu items
		ScenarioMenu = new JMenu("Scenario");
		EditMenu = new JMenu("Edit");
		ControlsMenu = new JMenu("Controls");
		HelpMenu = new JMenu("Help");
		
		MenuBar.add(ScenarioMenu);
		MenuBar.add(EditMenu);
		MenuBar.add(ControlsMenu);
		MenuBar.add(HelpMenu);

		//Create selections for menu items
		ExitMenuItem = new JMenuItem("Exit");
		HelpMenuItem = new JMenuItem("Help Topics");
		AboutMenuItem = new JMenuItem("About");
		
		ScenarioMenu.add(ExitMenuItem);
		HelpMenu.add(HelpMenuItem);
		HelpMenu.add(AboutMenuItem);

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
		//BGSandstone = new ImageIcon("resources/sandstone.jpg");
		CarEast = new ImageIcon(new ImageIcon("resources/car-e.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)); //Resize image to 40x40
		CarWest = new ImageIcon(new ImageIcon("resources/car-w.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)); //Resize image to 40x40
		CarNorth = new ImageIcon(new ImageIcon("resources/car-n.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));//Resize image to 40x40
		CarSouth = new ImageIcon(new ImageIcon("resources/car-s.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));//Resize image to 40x40
		CompassEast = new ImageIcon("resources/east.jpg");
		CompassWest = new ImageIcon("resources/west.jpg");
		CompassNorth = new ImageIcon("resources/north.jpg");
		CompassSouth = new ImageIcon("resources/south.jpg");
		
		//Load buttons
		LoadFrontend();
	}//End of createGUI();

	//Add buttons to main window
	public void LoadFrontend() {
		for (int nC = 0; nC < 208; nC++) {
			GridButtons[nC] = new JButton();
			MainPanel.add(GridButtons[nC]);
			GridButtons[nC].setBorderPainted(false);
			GridButtons[nC].setEnabled(false);
			if (nC == 0 | nC == 51) {
				GridButtons[nC].setIcon(BGTopLeft);
				GridButtons[nC].setDisabledIcon(BGTopLeft);
			} 
			else if (nC == 15 | nC == 60) {
				GridButtons[nC].setIcon(BGTopRight);
				GridButtons[nC].setDisabledIcon(BGTopRight);
			} 
			else if (nC == 192 | nC == 147) {
				GridButtons[nC].setIcon(BGBottomLeft);
				GridButtons[nC].setDisabledIcon(BGBottomLeft);
			} 
			else if (nC == 207 | nC == 156) {
				GridButtons[nC].setIcon(BGBottomRight);
				GridButtons[nC].setDisabledIcon(BGBottomRight);
			} 
			else if (contains(ceilfloor, nC) | contains(intceilfloor, nC)) {
				GridButtons[nC].setIcon(BGHorizTrack);
				GridButtons[nC].setDisabledIcon(BGHorizTrack);
			} 
			else if (contains(intbox, nC) | contains(walls, nC)) {
				GridButtons[nC].setIcon(BGVertTrack);
				GridButtons[nC].setDisabledIcon(BGVertTrack);
			} 
			else {
				GridButtons[nC].setIcon(BGEmptyIcon);
				GridButtons[nC].setDisabledIcon(BGEmptyIcon);
			}
			/*if (nC == 177) {
				GridButtons[nC].setIcon(BGSandstone);
				GridButtons[nC].setDisabledIcon(BGSandstone);}*/
		}
		CurrentPos = StartPos;
		CompassButton.setIcon(CompassEast);
		DirectionText.setText("East");
		SquareText.setText(Integer.toString(CurrentPos));
		GridButtons[StartPos].setIcon(CarEast);
		GridButtons[StartPos].setDisabledIcon(CarEast);
	}

	public void ReloadFrontend() {
		for (int nC = 0; nC < 208; nC++) {MainPanel.remove(GridButtons[nC]);}
		HourCount = 0;
		MinuteCount = 0;
		SecondCount = 0;
		HourText.setText("00");
		MinuteText.setText("00");
		SecondText.setText("00");
		LoadFrontend();
	}

	public void MoveCar(String direction) {
		
		if ("West".equals(direction)) {
			if (!contains(intbox, CurrentPos - 1) && !contains(walls, CurrentPos - 1) && !contains(intcorners, CurrentPos - 1)) {
				CurrentPos--;
				GridButtons[CurrentPos].setIcon(CarWest);
				GridButtons[CurrentPos].setDisabledIcon(CarWest);
				GridButtons[CurrentPos + 1].setIcon(BGEmptyIcon);
				GridButtons[CurrentPos + 1].setDisabledIcon(BGEmptyIcon);
				CompassButton.setIcon(CompassWest);
				DirectionText.setText(direction);}
			else {
				if (AutoTurn == 1) {MoveCar("North");}
			}
		}
		if ("East".equals(direction)) {
			if (!contains(intbox, CurrentPos + 1) && !contains(walls, CurrentPos + 1) && !contains(intcorners, CurrentPos + 1)) {
				CurrentPos++;
				GridButtons[CurrentPos].setIcon(CarEast);
				GridButtons[CurrentPos].setDisabledIcon(CarEast);
				GridButtons[CurrentPos - 1].setIcon(BGEmptyIcon);
				GridButtons[CurrentPos - 1].setDisabledIcon(BGEmptyIcon);
				CompassButton.setIcon(CompassEast);
				DirectionText.setText(direction);}
			else {
				if (AutoTurn == 1) {MoveCar("South");}
			}
		}
		if ("North".equals(direction)) {
			if (!contains(ceilfloor, CurrentPos - 16) && !contains(intceilfloor, CurrentPos - 16) && !contains(intcorners, CurrentPos - 16)) {
				CurrentPos -= 16;
				GridButtons[CurrentPos].setIcon(CarNorth);
				GridButtons[CurrentPos].setDisabledIcon(CarNorth);
				GridButtons[CurrentPos + 16].setIcon(BGEmptyIcon);
				GridButtons[CurrentPos + 16].setDisabledIcon(BGEmptyIcon);
				CompassButton.setIcon(CompassNorth);
				DirectionText.setText(direction);}
			else {
				if (AutoTurn == 1) {MoveCar("East");}
			}
		}
		if ("South".equals(direction)) {
			if (!contains(ceilfloor, CurrentPos + 16) && !contains(intceilfloor, CurrentPos + 16) && !contains(intcorners, CurrentPos + 16)) {
				CurrentPos += 16;
				GridButtons[CurrentPos].setIcon(CarSouth);
				GridButtons[CurrentPos].setDisabledIcon(CarSouth);
				GridButtons[CurrentPos - 16].setIcon(BGEmptyIcon);
				GridButtons[CurrentPos - 16].setDisabledIcon(BGEmptyIcon);
				CompassButton.setIcon(CompassSouth);
				DirectionText.setText(direction);} 
			else {
				if (AutoTurn == 1) {MoveCar("West");}
			}
		}
		SquareText.setText(Integer.toString(CurrentPos));
		AutoTurn = 0;
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if ((source == ExitButton) | (source == ExitMenuItem)) {
			int exitquestion = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (exitquestion == 0) {System.exit(0);}}
		if (source == HelpMenuItem) {
			JOptionPane.showMessageDialog(null,"The program is used as follows:\nUp Arrow: Up\nDown Arrow: Down\nLeft Arrow: Left\nRight Arrow: Right\nNote: On screen, not keyboard.\n\nAct performs one step.\nRun drives the car automatically.\nReset will reset all on screen values,\nand reset all positions.\nOption buttons for different modes.","Quick Help", JOptionPane.PLAIN_MESSAGE, new ImageIcon("resources/helpbook.png"));}
		if (source == AboutMenuItem) {
			JOptionPane.showMessageDialog(null,"This application has been written by Aidan Rayner,\nfor the University of Northampton CSY1020\nProblem Solving and Programming Course, in Term 2.","About This Program", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("resources/user.png"));}
		if (source == CompassButton) {
			JOptionPane.showMessageDialog(null, "The current direction is: " + DirectionText.getText(),"Current Direction", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("resources/" + DirectionText.getText().toLowerCase() + ".jpg"));}
		if (source == LeftButton) {MoveCar("West");}
		if (source == RightButton) {MoveCar("East");}
		if (source == UpButton) {MoveCar("North");}
		if (source == DownButton) {MoveCar("South");}
		if (source == ResetButton) {ReloadFrontend();}
		if (source == Op1Button) {
			ReloadFrontend();
			OptionText.setText("1");
		}
		if (source == ActButton) {
			AutoTurn = 1;
			MoveCar(DirectionText.getText());
		}
		if (source == RunButton) {
			if (isRunning) {
				RunButton.setText("Run");
				RunIcon = new ImageIcon("resources/run.png");
				RunButton.setIcon(RunIcon);
				ActButton.setEnabled(true);
				ResetButton.setEnabled(true);
				isRunning = false;
				timer.stop();
				DigitalTimer.stop();}
			else {
				RunButton.setText("Pause");
				RunIcon = new ImageIcon("resources/pause.png");
				RunButton.setIcon(RunIcon);
				ActButton.setEnabled(false);
				ResetButton.setEnabled(false);
				isRunning = true;
				timer.start();
				DigitalTimer.start();}
		}
	}	
}