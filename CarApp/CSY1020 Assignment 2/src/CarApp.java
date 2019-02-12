import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
 
public class CarApp extends JFrame 
{
	//Added this next line to stop Java from pointlessly whinging.
	private static final long serialVersionUID = 1L;
	//Define new labels, text fields, buttons, and panels.
	//d_ tagged objects are set to be disabled (i.e. non-clickable disabled buttons).
	private JLabel OptionLabel, SquareLabel, DirectionLabel;
    private JTextField OptionText, SquareText, DirectionText;
    private JButton ExitButton, UpButton, DownButton, LeftButton, RightButton ,d_UpLeftButton, d_UpRightButton, d_CentreButton, d_DownLeftButton, d_DownRightButton;
    private JPanel MainPanel, RightPanel, BottomPanel, DirectionPanel, TopPanel, ActionsPanel, SliderPanel, TimerPanel, OptionsPanel;
    
    //Create main frame
    public static void main (String[] args)
    {
        CarApp frame; 
        frame = new CarApp();
        frame.setSize(810, 650);
		frame.createGUI();
		frame.setResizable(false);
        frame.setVisible(true);
    }

    private void createPanels()
    //Generate panels to be put on the main frame.
    {
    	Container window = getContentPane();
    	MainPanel = new JPanel();
        MainPanel.setPreferredSize(new Dimension(636, 532));
        MainPanel.setBackground(Color.BLACK);
        window.add(MainPanel);
        
        RightPanel = new JPanel();
        RightPanel.setPreferredSize(new Dimension(150, 532));
        RightPanel.setBackground(Color.PINK);
        window.add(RightPanel);
        
        BottomPanel = new JPanel();
        BottomPanel.setPreferredSize(new Dimension(780, 50));
        BottomPanel.setBackground(Color.GREEN);
        window.add(BottomPanel);
        
        TopPanel = new JPanel();
        TopPanel.setPreferredSize (new Dimension(150,100));
        TopPanel.setBackground(Color.ORANGE);
        
        DirectionPanel = new JPanel();
        DirectionPanel.setPreferredSize (new Dimension(150,120));
		DirectionPanel.setBackground(Color.RED);

		TimerPanel = new JPanel();
		TimerPanel.setPreferredSize (new Dimension(150,150));
		TimerPanel.setBackground(Color.DARK_GRAY);
		
		OptionsPanel = new JPanel();
		OptionsPanel.setPreferredSize (new Dimension(150,50));
		OptionsPanel.setBackground(Color.GREEN);
		
		ActionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		ActionsPanel.setPreferredSize (new Dimension (300,30));
		ActionsPanel.setBackground(Color.BLUE);
		BottomPanel.add(ActionsPanel);

		SliderPanel = new JPanel();
		SliderPanel.setPreferredSize (new Dimension(300,30));
		SliderPanel.setBackground(Color.LIGHT_GRAY);
		BottomPanel.add(SliderPanel);

		

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

			RightPanel.add(DirectionPanel);
	        GridLayout directionLayout = new GridLayout(3,3);
			DirectionPanel.setLayout(directionLayout);
			
			RightPanel.add(TimerPanel);
			RightPanel.add(OptionsPanel);
	        
	        //Create exit button
	        ExitButton = new JButton("Exit");
	        RightPanel.add(ExitButton);
	        
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
	        
	        //Disable non-clickable buttons
	        d_UpLeftButton.setEnabled(false);
	        d_UpRightButton.setEnabled(false);
	        d_DownLeftButton.setEnabled(false);
	        d_DownRightButton.setEnabled(false);
	        d_CentreButton.setEnabled(false);
		
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