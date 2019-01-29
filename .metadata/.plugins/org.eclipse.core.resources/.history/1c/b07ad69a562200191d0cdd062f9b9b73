import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
 
public class CarApp extends JFrame 
{
	private JLabel jLOption, jLSquare, jlDirection;
    private JTextField jTFOption, jTFSquare, jTFDirection;
    private JButton jBExit; 
    private JButton jBUp, jBDown, jBLeft, jBRight ,jBUpLeft, jBUpRight, jBCentre, jBDownLeft, jBDownRight;
    private JPanel jPanelMain, jPanelRight, jPanelBottom, jPanelDirection, jPanelTop;
    
    public static void main (String[] args) //Method for declaring main
    {
        CarApp frame; 
        	frame = new CarApp();
        frame.setSize(1024, 768);
        frame.createGUI();
        frame.show();
    }

    private void createPanels()
    {
    	Container window = getContentPane();
    	jPanelMain = new JPanel();
        jPanelMain.setPreferredSize(new Dimension(800, 600));
        jPanelMain.setBackground(Color.BLACK);
        window.add(jPanelMain);
        
        jPanelRight = new JPanel();
        jPanelRight.setPreferredSize(new Dimension(190, 600));
        jPanelRight.setBackground(Color.PINK);
        window.add(jPanelRight);
        
        jPanelBottom = new JPanel();
        jPanelBottom.setPreferredSize(new Dimension(800, 50));
        jPanelBottom.setBackground(Color.GREEN);
        window.add(jPanelBottom);
        
        jPanelTop = new JPanel();
        jPanelTop.setPreferredSize (new Dimension(190,100));
        jPanelTop.setBackground(Color.ORANGE);
        
        jPanelDirection = new JPanel();
        jPanelDirection.setPreferredSize (new Dimension(190,120));
        jPanelDirection.setBackground(Color.RED);
    }


    private void createGUI() //Method for declaring createGUI (The interface)
    {
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout() );
       
        	createPanels();
        	
        	jPanelRight.add(jPanelTop);
	        GridLayout topLayout = new GridLayout(3,2);
	        jPanelTop.setLayout(topLayout);
	       
	        jLOption = new JLabel("Option:");
	        jTFOption = new JTextField("1");
	        jTFOption.setHorizontalAlignment(JTextField.CENTER);
	        jPanelTop.add(jLOption);
	        jPanelTop.add(jTFOption);

	        jLSquare = new JLabel("Square:");
	        jTFSquare = new JTextField("1");
	        jTFSquare.setHorizontalAlignment(JTextField.CENTER);
	        jPanelTop.add(jLSquare);
	        jPanelTop.add(jTFSquare);

	        jlDirection = new JLabel("Direction:");
	        jTFDirection = new JTextField("Up");
	        jTFDirection.setHorizontalAlignment(JTextField.CENTER);
	        jPanelTop.add(jlDirection);
	        jPanelTop.add(jTFDirection);

	        jPanelRight.add(jPanelDirection);
	        GridLayout directionLayout = new GridLayout(3,3);
	        jPanelDirection.setLayout(directionLayout);
	        
        	jBUpLeft = new JButton("");
    		jPanelDirection.add(jBUpLeft);
    		
	        jBUp = new JButton("↑");
	        jPanelDirection.add(jBUp);
	        
        	jBUpRight = new JButton("");
        	jPanelDirection.add(jBUpRight);
    		
    		jBLeft = new JButton("←");
    		jPanelDirection.add(jBLeft);
    			
    		jBCentre = new JButton("");
    		jPanelDirection.add(jBCentre);
        	
        	jBRight = new JButton("→");
        	jPanelDirection.add(jBRight);
        		
        	jBDownLeft = new JButton("");
        	jPanelDirection.add(jBDownLeft);
        	
	        jBDown = new JButton("↓");
	        jPanelDirection.add(jBDown);
	        	
	        jBDownRight = new JButton("");
	        jPanelDirection.add(jBDownRight);     	
       
	        jBUpLeft.setEnabled(false);
	        jBUpRight.setEnabled(false);
	        jBDownLeft.setEnabled(false);
	        jBDownRight.setEnabled(false);
	        jBCentre.setEnabled(false);
	        
        //Closing the Application using the button
        jBExit = new JButton("Exit");
        window.add(jBExit);
        
        jBExit.addActionListener(new ActionListener()
        		{
        			public void actionPerformed(ActionEvent e)
        				{
        					System.exit(0);
        				}
        		});

    }
    
}