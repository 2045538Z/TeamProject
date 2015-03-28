import java.awt.*;

import javax.swing.*;

import java.io.*;
import java.util.*;  
import java.awt.event.*;

//main GUI class

public class JavaBallGUI extends JFrame implements ActionListener {

	//TextArea with list of all refs
	private static JTextArea refDisplay;
	
	//Jbuttons 
	private JButton searchButton, addButton, allocateButton, displayBarChart, saveExit;
	
	//textFields for Referee's 1st and last names.
	private static JTextField firstNameText, lastNameText;
	
	//comboBoxes for weeknumber, level and area
	private JComboBox weekCombo, levelCombo, areaCombo;
	
	private static RefereeList RefList;
	private AddUpdateGUI AddUpdate;
	private MatchList matchList;
	
	final String refsIn = "RefereesIn.txt";
	final int maxRefs = 12;
	private String firstName;
	private String lastName;
	
	
	//constructor
	public JavaBallGUI(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Java Ball Referee Allocations");
		setSize(800,360);
		
		refDisplay = new JTextArea(9,0);  //change values to make appropriate size
		JScrollPane scroll = new JScrollPane(refDisplay);
		refDisplay.setFont(new Font("Courier", Font.PLAIN, 14));
		add(scroll, BorderLayout.NORTH);
		refDisplay.setEditable(false);
		layoutTop();
		layoutBottom();
		initRefList();
		matchList= new MatchList(RefList);
		
	}
	
	
	public void layoutTop(){
		//instantiate panel for top 
		JPanel top = new JPanel(new GridLayout(1,2));
		top.setBorder(BorderFactory.createLoweredBevelBorder());
		
		//add upper label, button and textfield 
		JPanel panel1 = new JPanel(new GridLayout(2,1));
		//tPanel1 for firstName Label and field, bPanel1 for last name field n label
		JPanel tPanel1 = new JPanel(); 
		JPanel bPanel1 = new JPanel();
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameText = new JTextField(17);
		searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		//searchButton.setEnabled(enable);
		
		JLabel lastNameLabel = new JLabel("Last Name ");
		lastNameText = new JTextField(17);
		
		tPanel1.add(firstNameLabel);
		tPanel1.add(firstNameText);
		bPanel1.add(lastNameLabel);
		bPanel1.add(lastNameText);
		panel1.add(tPanel1);
		panel1.add(bPanel1);
		top.add(panel1);
		
		//add lower label button and textField
		JPanel panel2 = new JPanel();
		
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		addButton.setPreferredSize(new Dimension(80, 25));
		
		
		panel2.add(addButton);
		panel2.add(searchButton);
		top.add(panel2);
		add(top, BorderLayout.CENTER);
		
	}
	
	public void layoutBottom(){
		//instantiates panel for bottom
		JPanel bottom = new JPanel(new GridLayout(1,2));
		
		//loop populates the array with numbers 1-52
		JLabel weekLabel = new JLabel("Week number: ");
		String[] weeks = new String[52];
		int year = 52;
		for(int i=1;i<=year;i++){
			weeks[i-1]=""+i;
		}
		weekCombo = new JComboBox(weeks);
		weekCombo.setSelectedIndex(0);
		
		
		JLabel levelLabel = new JLabel("Level");
		String[] levels = {"Junior","Senior"};
		levelCombo = new JComboBox(levels);
		levelCombo.setSelectedIndex(1);
		
		
		JLabel areaLabel = new JLabel("Area");
		String[] areas = {"North", "Central","South"};
		areaCombo = new JComboBox(areas);
		areaCombo.setSelectedIndex(2);
	
		
		JPanel comboPanel = new JPanel(new GridLayout(3,1));
		comboPanel.add(weekLabel);
		comboPanel.add(weekCombo);
		comboPanel.add(levelLabel);
		comboPanel.add(levelCombo);
		comboPanel.add(areaLabel);
		comboPanel.add(areaCombo);
		
		//instantiates the lower panel with the buttons
		JPanel buttonPanel = new JPanel();
	
		allocateButton = new JButton("Allocate");
		allocateButton.addActionListener(this);
		displayBarChart = new JButton("Display Bar Chart");  
		displayBarChart.addActionListener(this);
		saveExit = new JButton("Save and Exit");
		saveExit.addActionListener(this);
		buttonPanel.add(allocateButton);
		buttonPanel.add(displayBarChart);
		buttonPanel.add(saveExit);
		
		bottom.add(comboPanel, BorderLayout.WEST);
		bottom.add(buttonPanel, BorderLayout.EAST);
		add(bottom, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent ae){
		//if search button is pressed 
		if(ae.getSource()==searchButton){
			firstName =firstNameText.getText().trim();
			lastName = lastNameText.getText().trim();
			//if 1st and last name text fields are non null opens addUpdate frame with isAdd boolean variable set to false
			if(!firstName.equals("")&&!lastName.equals("")){
				
				int is = RefList.findRefByName(firstName, lastName);
				
				if(is>=0){
					AddUpdate = new AddUpdateGUI(false, firstName, lastName, RefList); 
					AddUpdate.setVisible(true);
					// enable = false;   //disable search button while AddUpdateGUI is visible
				}
				else{
					JOptionPane.showMessageDialog(null, "Referee " + firstName + " " +lastName+ " not found.",null, JOptionPane.ERROR_MESSAGE);			
				}	 
			}
			else{
				JOptionPane.showMessageDialog(null, "Please enter the referee's first name and last name.");
			}
			
		}
		
		//what happens if addButton is pressed. if 1st and last name text fields are non null opens addUpdate frame with isAdd  boolean variable set to true
		else if(ae.getSource()==addButton){
			firstName =firstNameText.getText().trim();
			lastName = lastNameText.getText().trim();
			
			if(checkValidName(firstName)&&checkValidName(lastName)) {

				if(!firstName.equals("")&&!lastName.equals("")){
					AddUpdate = new AddUpdateGUI(true, firstName, lastName, RefList);
					AddUpdate.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(null, "Please enter the referee's first name and last name.");
				}
			}
		}
		
		//what happens if allocate button is pressed
		else if(ae.getSource()==allocateButton){
			int week = Integer.parseInt((String) weekCombo.getSelectedItem());
			String level = (String) levelCombo.getSelectedItem();
			String area = (String) areaCombo.getSelectedItem();
			matchList.selectReferee(level,area,week);
			MatchReportFrame mrf = new MatchReportFrame(week,matchList.allocationReport());
			updateGUIDisplay();
			
			
		}
		
		//what happens if barchart button is pressed
		else if(ae.getSource()==displayBarChart){
			BarChartFrame bar = new BarChartFrame(RefList.returnNonNullRefs());
		}
		
		//else save and exit the program
		else{
			matchList.buildMatchAllocsFile();
			RefList.refereesOutWriter();
			System.exit(0);
		}
		
	}
	
	//initialises the refereeList by reading the file creating the referee:ist objects and printing the read ref details to the display
	//if no refereesIN.txt is found program closes -- might not need to do this -> have blank reflist
	public void initRefList(){
		try{
			FileReader in = new FileReader(refsIn);
			RefList = new RefereeList(in);
			updateGUIDisplay();
		}
		catch(IOException e){		
			JOptionPane.showMessageDialog(null, "RefereesIn.txt not found!");
			System.exit(0);
		}
		
	}
	
	//method will update the display with the details of the refs in the reflist array
	public static void updateGUIDisplay(){
		//first clears the display
		refDisplay.setText(""); 
		firstNameText.setText("");
		lastNameText.setText("");
		refDisplay.setText(RefList.displayString());
			
	}
	
	//method gets the fist name from the text field
	public String getFirstName(){
		String firstName = firstNameText.getText();
		return firstName;
	}
	
	//method gets the lastName from the textfield
	public String getlastName(){
		String lastName = lastNameText.getText();
		return lastName;
	}
	
	// Checks if user inputed first name and last name are only one word each  
	public boolean checkValidName(String refName) {
		
		String[] name = refName.split("\\s");
		
		if (name.length > 1) {
			JOptionPane.showMessageDialog(null, "Please use one word per name or hyphenate.");
			firstNameText.setText(null);
			lastNameText.setText(null);
			return false;
		}
		return true;
		
	}
}
