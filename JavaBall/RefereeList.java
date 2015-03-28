import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class RefereeList {

	// Declare an array of referee objects
	private Referee [] referees;

	private final int MAX_REFEREES = 12;
	private int numReferees;
	
	//Declare an array of non-null referee objects which will be used for sorting and display
	private Referee[] nonNullRefs;

	final String refsOutText = "RefereesOut.txt";

	// Default constructor
	public RefereeList(FileReader in) {
		referees = new Referee [MAX_REFEREES];
		numReferees = 0;
		buildRefereeList(in);
	}

	// Method to build the Referees Array from a .txt file
	public void buildRefereeList(FileReader fr) {
		/*
		 *  Instantiates a Scanner taking a FileReader as an object
		 *  the FileReader is passed from the initRefList method in JavaBallGUI
		 */

		Scanner in = new Scanner(fr);

		try {
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
		
		// Closes the scanner to prevent resource leak
		finally {
			in.close();
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
		String qualificationHead = new String("Qualification ");
		String allocationsHead = new String("Allocations");
		String hAreaHead = new String("Home Area");
		String tAreasHead = new String("Travel Areas");

		// Appends the headings and formats them into evenly spaced columns
		display.append(String.format("%-13s%-13s%-13s%-13s%-13s%-13s%-13s\r\n", refIDHead,fNameHead,lNameHead,qualificationHead,allocationsHead,hAreaHead,tAreasHead));
		
		//sorts the array
		sortReferees();
		
		// Loops through each index of the nonNullRefs array
		for (int i = 0; i < nonNullRefs.length; i++) {
			// Instantiates a string for the referee's details
			String refString = new String();

			// Returns the Referee object at each index
			Referee ref = nonNullRefs[i];

			// creates a string containing the referee's details
			if (ref !=null) {
			refString = String.format("%-13s%-13s%-13s%-14s%-13s%-13s%-13s\r\n", ref.getID(), ref.getFirstName(), ref.getLastName(), ref.getQualification(), ref.getAllocations(), ref.getArea(), ref.getTravel());
			}
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

	// A method to return a specific referee's index given their first and last name
	public Referee returnRefByName (String firstName, String lastName) {
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
			return null;

		// returns the matching referee's index in the array if one is found
		else
			return ref;
	}
	
	// A method to return a specific referee given their id
	public int initialMatches (char fi, char li) {
		//Instantiates a null Referee object
		Referee ref = null;

		int initialMatches = 1;

		// Sets up a for loop to go through each referee in the array and compare the initials to the initials being searched for
		for (int i = 0; i < MAX_REFEREES; i++) {			
			ref = referees[i];
			
			if(ref!=null){
			// gets the first name initial of the referee object
			char firstInitial = ref.getFirstName().charAt(0);
			// gets the last name initial of the referee object
			char lastInitial = ref.getLastName().charAt(0);

			// searches for matches in non-null elements of the array
			if (null != ref && firstInitial == fi && lastInitial == li)
				initialMatches++;
			}
		}
		return initialMatches;
	}

	public void sortReferees() {
		
		// Instantiates a new array of referee objects to hold non-null elements from the referees array
		nonNullRefs = new Referee [numReferees];
		int i = 0;
		// Loops through the referees array
		
		for (Referee r : referees) {
			
			//Referee ref = referees[i];
			
			//Adds each non null ref to the nonNullRefs array
			if (r != null) {
				nonNullRefs[i] = r;
				i++;
			}
		}
		
		//Arrays.sort(nonNullRefs);
		Arrays.sort(nonNullRefs, new Comparator<Referee>() {
			public int compare(Referee this1, Referee other1) {

				String thisID = this1.getID();
				String otherID = other1.getID();
				return thisID.compareTo(otherID);
			}
		});
	}

	// A method to delete a referee based on first and last names passed from the processDeletion method in JavaGUI
	public void deleteReferee(String firstName, String lastName) {
		// Searches for any existing referee with first and last names matching the user inputed values
		int refIndex = findRefByName(firstName, lastName);

		// Displays a warning if there is no referee with the user inputed names
		if (refIndex == -1) {
			JOptionPane.showMessageDialog(null, "No Referee with the Name: "+firstName+" "+lastName+" found", "No Match", JOptionPane.ERROR_MESSAGE);
		}

		// If a matching ref is found it's element in the array is set to null and the number of referees is decremented
		else {
			referees [refIndex] = null;
			numReferees--;
		}
	}

	// finds the index of the first null element in the array
	public int findNullReferee(Referee[] referees) {
		int index = -1;
		// Loops through the referees array
		for (int i= 0; i< MAX_REFEREES; i++) {

			// if an element is null it's index is returned
			if (referees[i] == null) {
				index = i;
				break;
			}
		}
		return index;
		
	}

	public void addReferee(String fN, String lN, String q, String a, String ha, String t) {
		if (numReferees >= 12) {
			JOptionPane.showMessageDialog(null, "Maximum number of 12 Referees reached, cannot add a new Referee.", "Max Referees Reached", JOptionPane.ERROR_MESSAGE);	
		}

		else {
			//Instantiates a string for the new referee that can be passed to the constructor for referee
			String refString = new String();

			int idNum = initialMatches(fN.charAt(0),lN.charAt(0));

			// Generates a new id for the new referee
			String id = new String(""+Character.toUpperCase(fN.charAt(0))+Character.toUpperCase(lN.charAt(0))+idNum);

			//fills the new referees details into the ref string
			refString = (id+" "+fN+" "+lN+" "+q+" "+a+" "+ha+" "+t);

			// creates a new referee object
			Referee newRef = new Referee(refString);

			// adds referee to the first null element in the array
			referees [findNullReferee(referees)] = newRef;

			//increments the number of referees
			numReferees++;
		}
	}

	// writes to the RefereesOut.txt file
	public void refereesOutWriter() {
		FileWriter fw =null;
      try{
		try {
			// instantiates a new FileWriter object RefereesOut.txt = refsOutText
			fw = new FileWriter(refsOutText);

			// instantiates a new StringBuilder object
			StringBuilder refsOut = new StringBuilder();

			//Loops through the referees array
			for (int i=0; i < MAX_REFEREES; i++) {

				Referee ref = referees[i];

				/*
				 *  If a non-null referee is found the values for ID, first name, last name, qualification, allocations, home area, 
				 *  and travel areas are formatted to a string and each string is appended to the Stringbuilder
				 */
				if (ref !=null) 
				{
					refsOut.append(String.format("%s %s %s %s %d %s %s\r\n",ref.getID(), ref.getFirstName(), ref.getLastName(), ref.getQualification(), ref.getAllocations(), ref.getArea(), ref.getTravel()));
				}
			}

			// Returns the StringBuilder as a string and writes it to the file
			fw.write(refsOut.toString());

		} 
		finally {
			// executed whether or not an exception is raised above
			// close the file assuming it was successfully opened
			if (fw  != null) fw.close();
		}
      }
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File Not Found", "Input Exception", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public void incrementAllocation( String firstname, String lastname) {
		Referee ref = returnRefByName( firstname, lastname );
		ref.incrementAllocation();		
	}
	
	public String returnRefString(int i){
		String out = referees[i].returnDetails();
		return out;
	}
	
	//method returns the Referee[] array
	public Referee[] returnNonNullRefs(){
		return nonNullRefs;
	}
	
}