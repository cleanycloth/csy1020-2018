import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.Timer;

public class CCarCrash extends JFrame implements ActionListener {
	// Added this next line to stop Java from pointlessly whinging.
	private static final long serialVersionUID = 1L;
	// Define new labels, text fields, buttons, and panels.
	// d_ tagged objects are set to be disabled (i.e. non-clickable disabled buttons).
	private JLabel OptionLabel, SquareLabel, DirectionLabel, TimerLabel, SliderLabel, TimerSepLabel, TimerSepLabel2;
	private JTextField OptionText, SquareText, DirectionText, HourText, MinuteText, SecondText;
	private JButton ExitButton, UpButton, DownButton, LeftButton, RightButton, d_UpLeftButton, d_UpRightButton,
			d_CentreButton, d_DownLeftButton, d_DownRightButton, ActButton, RunButton, ResetButton, Op1Button,
			Op2Button, Op3Button, CompassButton;
	private JButton GridButtons[] = new JButton[208];
	private JPanel MainPanel, RightPanel, BottomPanel, DirectionPanel, TopPanel, ActionsPanel, SliderPanel,
			TimerLabelPanel, TimerPanel, OptionsPanel;
	private JSlider SpeedSlider;
	private static JMenuBar MenuBar;
	private JMenu ScenarioMenu, EditMenu, ControlsMenu, HelpMenu;
	private JMenuItem ExitMenuItem, HelpMenuItem, AboutMenuItem;
	private ImageIcon CompassEast, CompassWest, CompassNorth, CompassSouth, ActIcon, RunIcon, ResetIcon, BGEmptyIcon,
			BGHorizTrack, BGTopLeft, BGTopRight, BGVertTrack, BGBottomLeft, BGBottomRight, CarEast, CarWest, CarNorth,
			CarSouth;
	Timer RunTimer = new Timer(true);
	Integer[] intbox = { 67, 83, 99, 115, 131, 147, 76, 92, 108, 124, 140, 156 };
	Integer[] walls = { 16, 32, 48, 64, 80, 96, 112, 128, 144, 160, 176, 31, 47, 63, 79, 95, 111, 127, 143, 159, 175, 191, 207 };
	Integer[] ceilfloor = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 193, 194, 195, 196, 197, 198, 199, 200, 201,	202, 203, 204, 205, 206 };
	Integer[] intceilfloor = { 52, 53, 54, 55, 56, 57, 58, 59, 148, 149, 150, 151, 152, 153, 154, 155 };
	Integer[] intcorners = { 51, 60, 147, 156 };
	int startpos = 17;
	int currentpos = startpos;
	int ActButtonActive = 0;
	// Create main frame

	public static void main(String[] args) {
		MenuBar = new JMenuBar();
		CCarCrash frame;
		frame = new CCarCrash();
		frame.setTitle("CCarCrash - Car Race Application");
		frame.setSize(810, 650);
		frame.createGUI();
		frame.setResizable(false);
		// frame.setLocationRelativeTo(null); //ADD THIS BACK IN LATER TO COMPLETE WORK
		frame.setJMenuBar(MenuBar);
		frame.setVisible(true);
	}

	private void createPanels()
	// Generate panels to be put on the main frame.
	{
		// Create Main panel, which contains the buttons used to create the car driving grid.
		Container window = getContentPane();
		MainPanel = new JPanel();
		MainPanel.setPreferredSize(new Dimension(620, 530));
		GridLayout MainLayout = new GridLayout(13, 16);
		MainPanel.setLayout(MainLayout);
		MainPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		window.add(MainPanel);

		// Create right sidebar, which contains thing such as the arrow buttons, timer etc.
		RightPanel = new JPanel();
		RightPanel.setPreferredSize(new Dimension(156, 530));
		RightPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		window.add(RightPanel);

		// Create bottom bar, which contains the act/run/reset buttons, and speed slider.
		BottomPanel = new JPanel();
		BottomPanel.setPreferredSize(new Dimension(797, 50));
		BottomPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		window.add(BottomPanel);

		// Create Top panel for right sidebar. Contains option, square, and direction boxes.
		TopPanel = new JPanel();
		TopPanel.setPreferredSize(new Dimension(150, 100));

		// Create Direction panel for right sidebar. Contains the up/down/left/right buttons.
		DirectionPanel = new JPanel();
		DirectionPanel.setPreferredSize(new Dimension(150, 125));

		// Create Timer Label panel for the right sidebar. This contains the "DIGITAL TIMER" text.
		TimerLabelPanel = new JPanel();
		TimerLabelPanel.setPreferredSize(new Dimension(150, 110));

		// Create Options panel for the right sidebar. This contains the options 1/2/3 buttons and Exit.
		OptionsPanel = new JPanel();
		OptionsPanel.setPreferredSize(new Dimension(150, 70));
		GridLayout OptionsLayout = new GridLayout(2, 2);
		OptionsPanel.setLayout(OptionsLayout);

		ActionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		ActionsPanel.setPreferredSize(new Dimension(470, 40));
		BottomPanel.add(ActionsPanel);

		SliderPanel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		SliderPanel.setPreferredSize(new Dimension(290, 40));
		SliderPanel.setLayout(new GridBagLayout());
		BottomPanel.add(SliderPanel);
		SliderLabel = new JLabel("Speed:");
		c.gridx = 0;
		c.gridy = 0;
		SliderPanel.add(SliderLabel);
		SpeedSlider = new JSlider(JSlider.HORIZONTAL, 10, 60, 35);
		c.gridx = 0;
		c.gridy = 1;
		SpeedSlider.setMajorTickSpacing(10);
		SpeedSlider.setMinorTickSpacing(5);
		SpeedSlider.setPaintTicks(true);
		SliderPanel.add(SpeedSlider);

		// Action buttons
		ActButton = new JButton("Act");
		ActButton.setMargin(new Insets(0, 10, 0, 10));
		RunButton = new JButton("Run");
		RunButton.setMargin(new Insets(0, 10, 0, 10));
		ResetButton = new JButton("Reset");
		ResetButton.setMargin(new Insets(0, 10, 0, 10));
		ActionsPanel.add(ActButton);
		ActIcon = new ImageIcon("resources/step.png");
		ActButton.setIcon(ActIcon);
		ActionsPanel.add(RunButton);
		RunIcon = new ImageIcon("resources/run.png");
		RunButton.setIcon(RunIcon);
		ResetIcon = new ImageIcon("resources/reset.png");
		ResetButton.setIcon(ResetIcon);
		ActionsPanel.add(ResetButton);

		ResetButton.addActionListener(this);
		ActButton.addActionListener(this);
		RunButton.addActionListener(this);

	}

	public static boolean contains(Integer[] arr, Integer item) {
		return Arrays.stream(arr).anyMatch(item::equals);
	}

	public void createGUI()
	// Generate interface in main frame.
	// This involves actually creating the extra panels, buttons etc.
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
		SquareText = new JTextField(Integer.toString(startpos));
		SquareText.setHorizontalAlignment(JTextField.CENTER);
		TopPanel.add(SquareLabel);
		TopPanel.add(SquareText);

		DirectionLabel = new JLabel("Direction:");
		DirectionText = new JTextField();
		DirectionText.setHorizontalAlignment(JTextField.CENTER);
		TopPanel.add(DirectionLabel);
		TopPanel.add(DirectionText);

		// Place Direction buttons panel
		GridLayout directionLayout = new GridLayout(3, 3);
		DirectionPanel.setLayout(directionLayout);
		RightPanel.add(DirectionPanel);

		// Place Timer Container
		GridLayout timerLabelPanelLayout = new GridLayout(5, 1);
		TimerLabelPanel.setLayout(timerLabelPanelLayout);
		RightPanel.add(TimerLabelPanel);

		// Create Timer Label
		TimerLabel = new JLabel("DIGITAL TIMER");
		TimerLabel.setHorizontalAlignment(JLabel.CENTER);
		TimerLabelPanel.add(TimerLabel);

		// Create actual Timer
		TimerPanel = new JPanel();
		TimerSepLabel = new JLabel(":");
		TimerSepLabel2 = new JLabel(":");
		TimerSepLabel.setHorizontalAlignment(JLabel.CENTER);
		TimerSepLabel2.setHorizontalAlignment(JLabel.CENTER);
		HourText = new JTextField("00");
		MinuteText = new JTextField("00");
		SecondText = new JTextField("00");
		GridLayout timerLayout = new GridLayout(1, 5);
		TimerPanel.setLayout(timerLayout);
		TimerLabelPanel.add(TimerPanel);
		TimerPanel.add(HourText);
		HourText.setEnabled(false);
		HourText.setBackground(Color.BLACK);
		HourText.setDisabledTextColor(Color.WHITE);
		TimerPanel.add(TimerSepLabel);
		TimerPanel.add(MinuteText);
		MinuteText.setEnabled(false);
		MinuteText.setBackground(Color.BLACK);
		MinuteText.setDisabledTextColor(Color.WHITE);
		TimerPanel.add(TimerSepLabel2);
		TimerPanel.add(SecondText);
		SecondText.setEnabled(false);
		SecondText.setBackground(Color.BLACK);
		SecondText.setDisabledTextColor(Color.WHITE);
		RightPanel.add(OptionsPanel);

		// Create directional buttons
		d_UpLeftButton = new JButton("");
		DirectionPanel.add(d_UpLeftButton);

		UpButton = new JButton("↑");
		DirectionPanel.add(UpButton);
		UpButton.addActionListener(this);

		d_UpRightButton = new JButton("");
		DirectionPanel.add(d_UpRightButton);

		LeftButton = new JButton("←");
		DirectionPanel.add(LeftButton);
		LeftButton.addActionListener(this);

		d_CentreButton = new JButton("");
		DirectionPanel.add(d_CentreButton);

		RightButton = new JButton("→");
		DirectionPanel.add(RightButton);
		RightButton.addActionListener(this);

		d_DownLeftButton = new JButton("");
		DirectionPanel.add(d_DownLeftButton);

		DownButton = new JButton("↓");
		DirectionPanel.add(DownButton);
		DownButton.addActionListener(this);

		d_DownRightButton = new JButton("");
		DirectionPanel.add(d_DownRightButton);

		// Create option buttons
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

		// Create menu bar and menu items
		ScenarioMenu = new JMenu("Scenario");
		MenuBar.add(ScenarioMenu);
		EditMenu = new JMenu("Edit");
		MenuBar.add(EditMenu);
		ControlsMenu = new JMenu("Controls");
		MenuBar.add(ControlsMenu);
		HelpMenu = new JMenu("Help");
		MenuBar.add(HelpMenu);

		// Create selections for menu items
		ExitMenuItem = new JMenuItem("Exit");
		ScenarioMenu.add(ExitMenuItem);
		ExitMenuItem.addActionListener(this);

		HelpMenuItem = new JMenuItem("Help Topics");
		HelpMenu.add(HelpMenuItem);
		HelpMenuItem.addActionListener(this);

		AboutMenuItem = new JMenuItem("About");
		HelpMenu.add(AboutMenuItem);
		AboutMenuItem.addActionListener(this);

		// Create compass button (NOTE: This should change depending on direction, but does not do that yet!)
		CompassButton = new JButton();
		RightPanel.add(CompassButton);
		CompassButton.addActionListener(this);

		CompassButton.setMargin(new Insets(1, 1, 1, 1));

		// Disable non-clickable buttons
		d_UpLeftButton.setEnabled(false);
		d_UpRightButton.setEnabled(false);
		d_DownLeftButton.setEnabled(false);
		d_DownRightButton.setEnabled(false);
		d_CentreButton.setEnabled(false);

		// Load Icons
		BGEmptyIcon = new ImageIcon("resources/space.png");
		BGHorizTrack = new ImageIcon("resources/wall-horiz.png");
		BGVertTrack = new ImageIcon("resources/wall-vert.png");
		BGTopLeft = new ImageIcon("resources/wall-NW.png");
		BGTopRight = new ImageIcon("resources/wall-NE.png");
		BGBottomLeft = new ImageIcon("resources/wall-SW.png");
		BGBottomRight = new ImageIcon("resources/wall-SE.png");
		CarEast = new ImageIcon(
				new ImageIcon("resources/car-e.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		CarWest = new ImageIcon(
				new ImageIcon("resources/car-w.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		CarNorth = new ImageIcon(
				new ImageIcon("resources/car-n.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		CarSouth = new ImageIcon(
				new ImageIcon("resources/car-s.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		CompassEast = new ImageIcon("resources/east.jpg");
		CompassWest = new ImageIcon("resources/west.jpg");
		CompassNorth = new ImageIcon("resources/north.jpg");
		CompassSouth = new ImageIcon("resources/south.jpg");
		// Load GUI
		LoadFrontend();
	}// End of createGUI();

	// Add buttons to main window
	public void LoadFrontend() {
		for (int nC = 0; nC < 208; nC++) {
			GridButtons[nC] = new JButton();
			MainPanel.add(GridButtons[nC]);
			GridButtons[nC].setBorderPainted(false);
			GridButtons[nC].setEnabled(false);
			if (nC == 0 | nC == 51) {
				GridButtons[nC].setIcon(BGTopLeft);
				GridButtons[nC].setDisabledIcon(BGTopLeft);
			} else if (nC == 15 | nC == 60) {
				GridButtons[nC].setIcon(BGTopRight);
				GridButtons[nC].setDisabledIcon(BGTopRight);
			} else if (nC == 192 | nC == 147) {
				GridButtons[nC].setIcon(BGBottomLeft);
				GridButtons[nC].setDisabledIcon(BGBottomLeft);
			} else if (nC == 207 | nC == 156) {
				GridButtons[nC].setIcon(BGBottomRight);
				GridButtons[nC].setDisabledIcon(BGBottomRight);
			} else if (contains(ceilfloor, nC) | contains(intceilfloor, nC)) {
				GridButtons[nC].setIcon(BGHorizTrack);
				GridButtons[nC].setDisabledIcon(BGHorizTrack);
			} else if (contains(intbox, nC) | contains(walls, nC)) {
				GridButtons[nC].setIcon(BGVertTrack);
				GridButtons[nC].setDisabledIcon(BGVertTrack);
			} else {
				GridButtons[nC].setIcon(BGEmptyIcon);
				GridButtons[nC].setDisabledIcon(BGEmptyIcon);
			}
		}
		currentpos = startpos;
		CompassButton.setIcon(CompassEast);
		DirectionText.setText("East");
		SquareText.setText(Integer.toString(currentpos));
		GridButtons[startpos].setIcon(CarEast);
		GridButtons[startpos].setDisabledIcon(CarEast);
	}

	public void ReloadFrontend() {
		for (int nC = 0; nC < 208; nC++) {
			MainPanel.remove(GridButtons[nC]);
		}
		LoadFrontend();
	}

	public void movecar(String direction) {
		if ("West".equals(direction)) {
			if (!contains(intbox, currentpos - 1) && !contains(walls, currentpos - 1)
					&& !contains(intcorners, currentpos - 1)) {
				currentpos--;
				GridButtons[currentpos].setIcon(CarWest);
				GridButtons[currentpos].setDisabledIcon(CarWest);
				GridButtons[currentpos + 1].setIcon(BGEmptyIcon);
				GridButtons[currentpos + 1].setDisabledIcon(BGEmptyIcon);
				CompassButton.setIcon(CompassWest);
				DirectionText.setText(direction);
			} else {
				if (ActButtonActive == 1) {
					movecar("North");
				}
			}
		}
		if ("East".equals(direction)) {
			if (!contains(intbox, currentpos + 1) && !contains(walls, currentpos + 1)
					&& !contains(intcorners, currentpos + 1)) {
				currentpos++;
				GridButtons[currentpos].setIcon(CarEast);
				GridButtons[currentpos].setDisabledIcon(CarEast);
				GridButtons[currentpos - 1].setIcon(BGEmptyIcon);
				GridButtons[currentpos - 1].setDisabledIcon(BGEmptyIcon);
				CompassButton.setIcon(CompassEast);
				DirectionText.setText(direction);
			} else {
				if (ActButtonActive == 1) {
					movecar("South");
				}
			}
		}
		if ("North".equals(direction)) {
			if (!contains(ceilfloor, currentpos - 16) && !contains(intceilfloor, currentpos - 16)
					&& !contains(intcorners, currentpos - 16)) {
				currentpos -= 16;
				GridButtons[currentpos].setIcon(CarNorth);
				GridButtons[currentpos].setDisabledIcon(CarNorth);
				GridButtons[currentpos + 16].setIcon(BGEmptyIcon);
				GridButtons[currentpos + 16].setDisabledIcon(BGEmptyIcon);
				CompassButton.setIcon(CompassNorth);
				DirectionText.setText(direction);
			} else {
				if (ActButtonActive == 1) {
					movecar("East");
				}
			}
		}
		if ("South".equals(direction)) {
			if (!contains(ceilfloor, currentpos + 16) && !contains(intceilfloor, currentpos + 16)
					&& !contains(intcorners, currentpos + 16)) {
				currentpos += 16;
				GridButtons[currentpos].setIcon(CarSouth);
				GridButtons[currentpos].setDisabledIcon(CarSouth);
				GridButtons[currentpos - 16].setIcon(BGEmptyIcon);
				GridButtons[currentpos - 16].setDisabledIcon(BGEmptyIcon);
				CompassButton.setIcon(CompassSouth);
				DirectionText.setText(direction);
			} else {
				if (ActButtonActive == 1) {
					movecar("West");
				}
			}
		}
		SquareText.setText(Integer.toString(currentpos));
		ActButtonActive = 0;
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if ((source == ExitButton) | (source == ExitMenuItem)) {
			int exitquestion = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Exit",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (exitquestion == 0) {
				System.exit(0);
			}
		}
		if (source == HelpMenuItem) {
			JOptionPane.showMessageDialog(null,
					"The program is used as follows:\nUp Arrow: Up\nDown Arrow: Down\nLeft Arrow: Left\nRight Arrow: Right\nNote: On screen, not keyboard.\n\nAct performs one step.\nRun drives the car automatically.\nReset will reset all on screen values,\nand reset all positions.\nOption buttons for different modes.",
					"Quick Help", JOptionPane.PLAIN_MESSAGE, new ImageIcon("resources/helpbook.png"));
		}
		if (source == AboutMenuItem) {
			JOptionPane.showMessageDialog(null,
					"This application has been written by Aidan Rayner,\nfor the University of Northampton CSY1020\nProblem Solving and Programming Course, in Term 2.",
					"About This Program", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("resources/user.png"));
		}
		if (source == CompassButton) {
			// if DirectionText.getText() == "Left"
			JOptionPane.showMessageDialog(null, "The current direction is: " + DirectionText.getText(),
					"Current Direction", JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon("resources/" + DirectionText.getText().toLowerCase() + ".jpg"));
		}
		if (source == LeftButton) {
			movecar("West");
		}
		if (source == RightButton) {
			movecar("East"); // This works.
		}
		if (source == UpButton) {
			movecar("North");
		}
		if (source == DownButton) {
			movecar("South");
		}
		if (source == ResetButton) {
			ReloadFrontend();
		}
		if (source == Op1Button) {
			ReloadFrontend();
			OptionText.setText("1");
		}
		if (source == ActButton) {
			ActButtonActive = 1;
			movecar(DirectionText.getText());
		}
		if (source == RunButton) {
			boolean RunButtonActive = true;
			while (RunButtonActive == true) {
				System.out.println("test");
				//Thread.sleep(1000);
				
			}
			ActButton.setEnabled(true);

		}
	}
	
}