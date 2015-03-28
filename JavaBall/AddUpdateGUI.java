import java.awt.*;

import javax.swing.*;

import java.io.*;
import java.awt.event.*;

public class AddUpdateGUI extends JFrame implements ActionListener {
	//textFields
	private JTextField allocateText;
	
	//JButtons
	private JButton addButton;
	private JButton deleteButton;
	private JButton updateButton;
	
	
	//comboboxes
	private JComboBox homeCombo, qualCombo, weekCombo;
	
	
	//radioBuutons
	private JCheckBox northCheck; 
	private JCheckBox centralCheck;
	private JCheckBox southCheck;
	
	private JTextArea display;
	
	private boolean isAdd;
	private boolean success = false;
	final String firstName;
	final String lastName;
	private String area;
	private String qualification;
	private String allocations;
	private String travelArea;
	
	private RefereeList RefList;
	
	public AddUpdateGUI(boolean add, String fName, String sname, RefereeList refs){	
		isAdd = add;
		firstName = fName;
		lastName = sname;
		RefList = refs;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Java Ball Referee Allocations");
		setSize(500,220);
		
		JPanel top = new JPanel();
		display  = new JTextArea(2,40);
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		display.setEditable(false);
		top.add(display);
		
		this.add(top,BorderLayout.NORTH);
		buttonLayout();
		fieldsLayout();
		
		updateDisplay();
	}
	
	public void buttonLayout(){
		JPanel buttons = new JPanel();
		
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		buttons.add(addButton);
		addButton.setVisible(isAdd);
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		deleteButton.setVisible(!isAdd);
		buttons.add(deleteButton);
		
		
		updateButton = new JButton("Update");
		updateButton.addActionListener(this);
		updateButton.setVisible(!isAdd);
		buttons.add(updateButton);
		
		
		add(buttons, BorderLayout.CENTER);
		
	}
	
	public void fieldsLayout(){
		JPanel fields = new JPanel(new GridLayout(2,1));
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		
		JLabel homeLabel = new JLabel("Home area");
		String[] areas = {"North", "Central", "South"};
		homeCombo = new JComboBox(areas);
		homeCombo.setSelectedIndex(2);
		
		
		JLabel qualLabel =  new JLabel("Qualification");
		String[] quals = {"NJB1","NJB2","NJB3","NJB4","IJB1","IJB2","IJB3","IJB4"};
		qualCombo = new JComboBox(quals);
		qualCombo.setSelectedIndex(7);
		
		
		JLabel numLabel = new JLabel("<html>Number of <br> allocations<html>");
		String[] weeks = new String[52];
		int year = 52;
		for(int i=1;i<=year;i++){
			weeks[i-1]=""+i;
		}
		weekCombo = new JComboBox(weeks);
		weekCombo.setSelectedIndex(0);
		
		JLabel travelLabel = new JLabel("Travel areas:   ");
		JLabel northLabel = new JLabel("North ");
		northCheck = new JCheckBox();
		
		JLabel centralLabel = new JLabel("Central ");
		centralCheck = new JCheckBox();
		
		JLabel southLabel = new JLabel("South ");
		southCheck = new JCheckBox();
		
		
		right.add(travelLabel);
		right.add(northLabel);
		right.add(northCheck);
		right.add(centralLabel);
		right.add(centralCheck);
		right.add(southLabel);
		right.add(southCheck);
		
		left.add(homeLabel);
		left.add(homeCombo);
		left.add(qualLabel);
		left.add(qualCombo);
		left.add(numLabel);
		left.add(weekCombo);
		
		
		fields.add(left);
		fields.add(right);
		add(fields, BorderLayout.SOUTH);
		
	}
	
	public void actionPerformed(ActionEvent e){
		//what happens if add button is pressed
		if(e.getSource()==addButton){
			getDetails();
			RefList.addReferee(firstName,lastName,qualification,allocations,area,travelArea);
			if(success){
				JavaBallGUI.updateGUIDisplay();  
				this.setVisible(false);
			}
			//enable = true;
		}
		
		//deletes the selected referee
		else if(e.getSource()==deleteButton){
			RefList.deleteReferee(firstName, lastName);
			JavaBallGUI.updateGUIDisplay();  
			this.setVisible(false);
		}
		
		//reads the field, checkboxes and comboboxes and updates the selected ref's details 
		else if(e.getSource()==updateButton){

			getDetails();
				RefList.deleteReferee(firstName, lastName);
				RefList.addReferee(firstName,lastName,qualification,allocations,area,travelArea);
				JavaBallGUI.updateGUIDisplay();  
				this.setVisible(false);

		}
	}
	
	public void getDetails() {
		//Generates a string from the user's selection in homeComboBox
		String[] travelAreaArray = {"","",""};
		area = (String)homeCombo.getSelectedItem();
		
		//Generates a string from the user's selection in homeComboBox
		qualification = (String)qualCombo.getSelectedItem();
		
		//Generates a string from the user's input into allocateText
		allocations = (String)weekCombo.getSelectedItem();
		
		//Instantiates an empty string for travelArea
		travelArea = new String("");
		
		//Checks each permutation of selected checkboxes and sets the travelArea String accordingly
		if(homeCombo.getSelectedItem().equals("North")){
			travelAreaArray[0]="Y";
			if(centralCheck.isSelected()){
				travelAreaArray[1]="Y";}
			else{
				travelAreaArray[1]="N";}
			if(southCheck.isSelected()){
				travelAreaArray[2]="Y";}
			else{
				travelAreaArray[2]="N";}
		}
		else if(homeCombo.getSelectedItem().equals("Central")){
			travelAreaArray[1]="Y";
			if(northCheck.isSelected()){
				travelAreaArray[0]="Y";}
			else{
				travelAreaArray[0]="N";}
			if(southCheck.isSelected()){
				travelAreaArray[2]="Y";}
			else{
				travelAreaArray[2]="N";}
		}
		else{
			travelAreaArray[2] = "Y";
			if(northCheck.isSelected()){
				travelAreaArray[0]="Y";}
			else{
				travelAreaArray[0]="N";}
			if(centralCheck.isSelected()){
				travelAreaArray[1]="Y";}
			else{
				travelAreaArray[1]="N";}
		}
		travelArea = ""+travelAreaArray[0]+travelAreaArray[1]+travelAreaArray[2];
		success = true;
	}
	
	//method will update the display if a ref is added/searched for
	public void updateDisplay(){		
		//Adds the name of the referee to be added to the display 
		if(isAdd) {
			String names = firstName +" " +lastName;
			display.setText(names);
			
		}
		// Updates the combo boxes and check boxes to reflect the refs actual details
		// This means the user only needs to change the fields they wish to update
		else {		
			int is = RefList.findRefByName(firstName, lastName);
			display.setText(RefList.returnRefString(is));

			Referee ref = RefList.returnRefByName(firstName, lastName);
			homeCombo.setSelectedItem(ref.getArea());
			qualCombo.setSelectedItem(ref.getQualification());
			weekCombo.setSelectedItem(""+ref.getAllocations());
			
			String travel = ref.getTravel();
			
			if (travel.charAt(0) == 'Y'){
				northCheck.setSelected(true);
			}
			if (travel.charAt(1) == 'Y'){
				centralCheck.setSelected(true);
			}
			if (travel.charAt(2) == 'Y'){
				southCheck.setSelected(true);
			}
		}
	}
}