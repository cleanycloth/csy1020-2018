import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
 
public class CCarCrash extends JFrame 
{
	//Added this next line to stop Java from pointlessly whinging.
	private static final long serialVersionUID = 1L;
	//Define new labels, text fields, buttons, and panels.
	//d_ tagged objects are set to be disabled (i.e. non-clickable disabled buttons).
	private JLabel OptionLabel, SquareLabel, DirectionLabel, TimerLabel, SliderLabel, TimerSepLabel, TimerSepLabel2;
    private JTextField OptionText, SquareText, DirectionText, HourText, MinuteText, SecondText;
	private JButton ExitButton, UpButton, DownButton, LeftButton, RightButton ,d_UpLeftButton, d_UpRightButton, d_CentreButton, d_DownLeftButton, d_DownRightButton, ActButton, RunButton, ResetButton, Op1Button, Op2Button, Op3Button, CompassButton;
	private JButton GridButtons[] = new JButton[208];
    private JPanel MainPanel, RightPanel, BottomPanel, DirectionPanel, TopPanel, ActionsPanel, SliderPanel, TimerLabelPanel, TimerPanel, OptionsPanel;
	private JSlider SpeedSlider;
	private static JMenuBar MenuBar;
	private JMenu ScenarioMenu, EditMenu, ControlsMenu, HelpMenu;
	private JMenuItem ExitMenuItem, HelpMenuItem, AboutMenuItem;
	private ImageIcon CompassIcon, ActIcon, RunIcon, ResetIcon, BGEmptyIcon, BGHorizTrack;
	//Create main frame
	
    public static void main (String[] args)
    {
		MenuBar = new JMenuBar();
		CCarCrash frame; 
		frame = new CCarCrash();
		frame.setTitle("CCarCrash - Car Race Application");
        frame.setSize(810, 650);
		frame.createGUI();
		frame.setResizable(false);
		//frame.setLocationRelativeTo(null); ADD THIS BACK IN LATER TO COMPLETE WORK
		frame.setJMenuBar(MenuBar);
		frame.setVisible(true);
    }

    private void createPanels()
    //Generate panels to be put on the main frame.
    {
    	Container window = getContentPane();
    	MainPanel = new JPanel();
		MainPanel.setPreferredSize(new Dimension(636, 542));
        GridLayout MainLayout = new GridLayout(13,16);
		MainPanel.setLayout(MainLayout);
		MainPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        window.add(MainPanel);
        
        RightPanel = new JPanel();
		RightPanel.setPreferredSize(new Dimension(156, 542));
		RightPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        window.add(RightPanel);
        
        BottomPanel = new JPanel();
		BottomPanel.setPreferredSize(new Dimension(797, 50));
		BottomPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        window.add(BottomPanel);
        
        TopPanel = new JPanel();
        TopPanel.setPreferredSize (new Dimension(150,100));
        
        DirectionPanel = new JPanel();
        DirectionPanel.setPreferredSize (new Dimension(150,100));

		TimerLabelPanel = new JPanel();
		TimerLabelPanel.setPreferredSize (new Dimension(150,140));
		
		OptionsPanel = new JPanel();
		OptionsPanel.setPreferredSize (new Dimension(150,70));
		GridLayout OptionsLayout = new GridLayout(2,2);
		OptionsPanel.setLayout(OptionsLayout);
		
		ActionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		ActionsPanel.setPreferredSize (new Dimension (470,40));
		BottomPanel.add(ActionsPanel);

		SliderPanel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		SliderPanel.setPreferredSize (new Dimension(290,40));
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
		
		//Action buttons
		ActButton = new JButton("Act");
		ActButton.setMargin(new Insets(0,10,0,10) );
		RunButton = new JButton("Run");
		RunButton.setMargin(new Insets(0,10,0,10) );
		ResetButton = new JButton("Reset");
		ResetButton.setMargin(new Insets(0,10,0,10) );
		ActionsPanel.add(ActButton);
		ActIcon = new ImageIcon("resources/step.png");
		ActButton.setIcon(ActIcon);
		ActionsPanel.add(RunButton);
		RunIcon = new ImageIcon("resources/run.png");
		RunButton.setIcon(RunIcon);
		ResetIcon = new ImageIcon("resources/reset.png");
		ResetButton.setIcon(ResetIcon);
		ActionsPanel.add(ResetButton);

    }


    private void createGUI() 
    //Generate interface in main frame.
    //This involves actually creating the extra panels, buttons etc.
    {
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout() );
       
        	createPanels();
        	
        	RightPanel.add(TopPanel);
	        GridLayout topLayout = new GridLayout(3,2);
	        TopPanel.setLayout(topLayout);
	       
	        OptionLabel = new JLabel("Option:");
	        OptionText = new JTextField("1");
	        OptionText.setHorizontalAlignment(JTextField.CENTER);
	        TopPanel.add(OptionLabel);
	        TopPanel.add(OptionText);

	        SquareLabel = new JLabel("Square:");
	        SquareText = new JTextField("1");
	        SquareText.setHorizontalAlignment(JTextField.CENTER);
	        TopPanel.add(SquareLabel);
	        TopPanel.add(SquareText);

	        DirectionLabel = new JLabel("Direction:");
	        DirectionText = new JTextField("Up");
	        DirectionText.setHorizontalAlignment(JTextField.CENTER);
	        TopPanel.add(DirectionLabel);
			TopPanel.add(DirectionText);
			
			//Place Direction buttons panel
	        GridLayout directionLayout = new GridLayout(3,3);
			DirectionPanel.setLayout(directionLayout);
			RightPanel.add(DirectionPanel);
			
			//Place Timer Container
			GridLayout timerLabelPanelLayout = new GridLayout(5,1);
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
			TimerSepLabel.setHorizontalAlignment(JLabel.CENTER);
			TimerSepLabel2.setHorizontalAlignment(JLabel.CENTER);
			HourText = new JTextField("00");
			MinuteText = new JTextField("00");
			SecondText = new JTextField("00");
			GridLayout timerLayout = new GridLayout(1,5);
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
	        
	        //Create directional buttons
	        
        	d_UpLeftButton = new JButton("");
    		DirectionPanel.add(d_UpLeftButton);
    		
	        UpButton = new JButton("↑");
	        DirectionPanel.add(UpButton);
	        
        	d_UpRightButton = new JButton("");
        	DirectionPanel.add(d_UpRightButton);
    		
    		LeftButton = new JButton("←");
    		DirectionPanel.add(LeftButton);
    			
    		d_CentreButton = new JButton("");
    		DirectionPanel.add(d_CentreButton);
        	
        	RightButton = new JButton("→");
        	DirectionPanel.add(RightButton);
        		
        	d_DownLeftButton = new JButton("");
        	DirectionPanel.add(d_DownLeftButton);
        	
	        DownButton = new JButton("↓");
	        DirectionPanel.add(DownButton);

	        d_DownRightButton = new JButton("");
			DirectionPanel.add(d_DownRightButton);    
			
			

			//Create option buttons
			Op1Button = new JButton("Option 1");
			Op2Button = new JButton("Option 2");
			Op3Button = new JButton("Option 3");
			ExitButton = new JButton("Exit");
			OptionsPanel.add(Op1Button);
			OptionsPanel.add(Op2Button);
			OptionsPanel.add(Op3Button);
			OptionsPanel.add(ExitButton);
			Op1Button.setMargin(new Insets(1, 1, 1, 1) );
			Op2Button.setMargin(new Insets(1, 1, 1, 1) );
			Op3Button.setMargin(new Insets(1, 1, 1, 1) );
			ExitButton.setMargin(new Insets(1, 1, 1, 1) );

			//Create menu bar and menu items
			ScenarioMenu = new JMenu("Scenario");
			MenuBar.add(ScenarioMenu);
			EditMenu = new JMenu("Edit");
			MenuBar.add(EditMenu);
			ControlsMenu = new JMenu("Controls");
			MenuBar.add(ControlsMenu);
			HelpMenu = new JMenu("Help");
			MenuBar.add(HelpMenu);

			//Create selections for menu items
			ExitMenuItem = new JMenuItem(new AbstractAction("Exit"){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			});
			ScenarioMenu.add(ExitMenuItem);
			HelpMenuItem = new JMenuItem(new AbstractAction("Help Topics"){
			private static final long serialVersionUID = 1L; //ignore thx
			public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null,"The program is used as follows:\nW: Up\nS: Down\nA: Left\nD: Right\nAct performs one step\nRun drives the car automatically\nReset will reset all on screen values,\nand reset all positions\nOption buttons for different modes\nThe rest should be self-explanatory.","Quick Help",JOptionPane.WARNING_MESSAGE);
				}
			});
			HelpMenu.add(HelpMenuItem);
			AboutMenuItem = new JMenuItem(new AbstractAction("About"){
			private static final long serialVersionUID = 1L; //ignore thx
			public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null,"This application has been written by Aidan Rayner,\nfor the University of Northampton CSY1020\nProblem Solving and Programming Course, in Term 2.","About This Program",JOptionPane.INFORMATION_MESSAGE);
				}
			});
			HelpMenu.add(AboutMenuItem);

			//Create compass button (NOTE: This should change depending on direction, but does not do that yet!)
			CompassIcon = new ImageIcon("resources/north.jpg");
			CompassButton = new JButton();
			CompassButton.setEnabled(false);
			CompassButton.setIcon(CompassIcon);
			CompassButton.setDisabledIcon(CompassIcon);
			RightPanel.add(CompassButton);
			
			CompassButton.setMargin(new Insets(1, 1, 1, 1) );
	        
	        //Disable non-clickable buttons
	        d_UpLeftButton.setEnabled(false);
	        d_UpRightButton.setEnabled(false);
	        d_DownLeftButton.setEnabled(false);
	        d_DownRightButton.setEnabled(false);
			d_CentreButton.setEnabled(false);

			//Add buttons to main window
			BGEmptyIcon = new ImageIcon("resources/space.png");
			BGHorizTrack = new ImageIcon("resources/wall-horiz.png");
			for (int nC=0; nC<208; nC++) 
			{
				GridButtons[nC] = new JButton();
				MainPanel.add(GridButtons[nC]);
				GridButtons[nC].setEnabled(false);
				if (nC == 0) {
					GridButtons[nC].setIcon(BGHorizTrack);
					GridButtons[nC].setDisabledIcon(BGHorizTrack);
				}
				if (nC >= 1  && nC < 15) {
					GridButtons[nC].setIcon(BGHorizTrack);
					GridButtons[nC].setDisabledIcon(BGHorizTrack);
				}
				else {
					GridButtons[nC].setIcon(BGEmptyIcon);
					GridButtons[nC].setDisabledIcon(BGEmptyIcon);
				}
				
			}
		
	    //Make the exit button exit the program 
        ExitButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        		{
        			System.exit(0);
        		}
        });

    }
    
}