import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class RefereeList {

	// Declare an array of referee objects
	Referee [] referees;

	private final int MAX_REFEREES = 12;

	private int numReferees;

	// Default constructor
	public RefereeList() {
		referees = new Referee [MAX_REFEREES];
		numReferees = 0;
	}

	// Method to build the Referees Array from a .txt file
	public void buildRefereeList(FileReader fr) {
		/*
		 *  Instantiates a Scanner taking a FileReader as an object
		 *  the FileReader is passed from the initRefList method in JavaBallGUI
		 */
		Scanner in = new Scanner(fr);

		// Instantiates a null Referee which will be populated
		Referee ref = null;

		// Reads the file line by line
		while (in.hasNextLine()) {
			// each line is returned as a String
			String line = in.nextLine();

			// instantiates a Referee object by passing a string to the constructor in the Referee class
			ref = new Referee(line);

			//adds the ref to the array
			referees [numReferees] = ref;

			//increments the number of refs
			numReferees++;
		}
	}

	// Returns a string that will be added to the JTextArea to show the list of referees
	public String displayString() {
		// Instantiates a new StringBuilder object
		StringBuilder display = new StringBuilder();

		// Creates headings
		String refIDHead = new String("Referee ID");
		String fNameHead = new String("First Name");
		String lNameHead = new String("Last Name");
		String qualificationHead = new String("Qualification");
		String allocationsHead = new String("Allocations");
		String hAreaHead = new String("Home Area");
		String tAreasHead = new String("Travel Areas");

		// Appends the headings and formats them into evenly spaced columns
		display.append(String.format("%-13s%-13s%-13s%-13s%-13s%-13s%-13s\r\n", refIDHead,fNameHead,lNameHead,qualificationHead,allocationsHead,hAreaHead,tAreasHead));

		// Loops through each index of the referees Array
		for (int i = 0; i < numReferees; i++) {
			// Instantiates a string for the class name
			String refString = new String();

			// Returns the Referee object at each index
			Referee ref = referees[i];

			// creates a string containing the referee's details
			refString = String.format("%-13s%-13s%-13s%-13s%-13s%-13s%-13s\r\n", ref.getID(), ref.getFirstName(), ref.getLastName(), ref.getQualification(), ref.getAllocations(), ref.getArea(), ref.getTravel());

			// appends the referee's details
			display.append(refString);
		}

		// The StringBuilder is returned as a String
		return display.toString();

	}
	

	// A method to return a specific referee's index given their first and last name
	public int findRefByName (String firstName, String lastName) {
		//Instantiates a null Referee object
		Referee ref = null;

		// Sets up a while loop to go through each referee in the array and compare the names to the names being searched for
		boolean done = false;
		int index = 0;
		while (!done && index < MAX_REFEREES) {			
			ref = referees[index];
			// only searches for matches in non-null elements of the array
			if (null != ref && ref.getFirstName().equals(firstName) && ref.getLastName().equals(lastName))
				done = true;
			else
				index++;
		}
		// returns -1 if no match is found
		if (!done)
			return -1;

		// returns the matching referee's index in the array if one is found
		else
			return index; 
	}


	// Sorts array in alphabetical order of ID
	public void sortRefs() {
		Arrays.sort(referees);
	}
	
	// A method to delete a referee based on first and last names passed from the processDeletion method in JavaGUI
	public void deleteReferee(String firstName, String lastName)
	{
		// Searches for any existing referee with first and last names matching the user inputed values
		int refIndex = findRefByName(firstName, lastName);
		
		// Displays a warning if there is no referee with the user inputed names
		if (refIndex == -1)
		{
			JOptionPane.showMessageDialog(null, "No Referee with the Name: "+firstName+" "+lastName+" found", "No Match", JOptionPane.ERROR_MESSAGE);
		}

		// If a matching ref is found it's element in the array is set to null and the number of referees is decremented
		else
		{
			referees [refIndex] = null;
			numReferees--;
		}
	}
}
