import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

//Array of Matches
public class MatchList {
	private RefereeList refList;	
	private Referee [] refArray;
	final String MatchAllocsFile = "MatchAllocs.txt";


	// ArrayList
	private List<Match> matchArrayList; // holds allocated match information
	private List<Referee> qualifedRefs; // List of referees who are qualified for the match's level
	private List<Referee> suitableRefs; //List of referees who be allocated, ordered on priority required

	// constructor
	public MatchList( RefereeList refereeList ) {

		refList = refereeList;
		refArray = new Referee[refList.returnNonNullRefs().length]; // creating a new array that will be populated by all referees currently held in the refList object
		matchArrayList = new ArrayList<Match>();

	}

	// Populates the suitableRefs array in order of suitability
	public void selectReferee(String level,String area, int week)
	{
		qualifedRefs =new ArrayList<Referee>();
		List<Referee> ref=new ArrayList<Referee>();
		refArray = refList.returnNonNullRefs(); // an array without null value is clone/copied
		Arrays.sort(refArray); // sort the referees in ascending allocation order

		for(int i=0;i<refArray.length;i++)
		{
			ref.add(refArray[i]);//add all referees
		}
		suitableRefs = new ArrayList<Referee>();
		List<Referee> homeReferee = new ArrayList<Referee>(); // for storing referees selected based on home area
		List<Referee> adjacentReferee = new ArrayList<Referee>(); // for storing referees selected based on adjacent to home area
		List<Referee> nonAdjacentReferee = new ArrayList<Referee>(); // // for storing referees selected based on non-adjacent to home area
		List<Referee> travelReferee = new ArrayList<Referee>();

		selectLevel(level, ref); // selecting referees based on qualification		
		int index = 0;

		if(area.equals("Central")) // when Central was selected from the GUI
		{	
			for (Referee r :ref)
			{
				if ( index < qualifedRefs.size() ) { // ensuring the qualifedRefs.get(index) is always less than index					
					if ( qualifedRefs.get(index).getArea().equals("Central") ) // home area is Central
					{						
						homeReferee.add( qualifedRefs.get(index) );												

					} else {

						if ( (qualifedRefs.get(index).getArea().equals("North") || qualifedRefs.get(index).getArea().equals("South")) && r.getTravel().charAt(1)=='Y') { // no qualified referees in home area, looking in adjacent area

							adjacentReferee.add( qualifedRefs.get(index) );

						}
						else if( qualifedRefs.get(index).getTravel().charAt(1)=='Y' )
						{
							travelReferee.add( qualifedRefs.get(index) );
						}
					}
				}
				index++;
			}
			suitableRefs.addAll( homeReferee );
			suitableRefs.addAll( adjacentReferee );
			suitableRefs.addAll(travelReferee);
			if ( suitableRefs.size() > 1 ) { 
				storeMatchList( suitableRefs, week, level,area );
			} else {
				JOptionPane.showMessageDialog(null, "Two suitable referees were not found for this match", "Not Enough Suitable Referees", JOptionPane.ERROR_MESSAGE);
			}
			// add by deleting referees
		}
			if(area.equals("North")) // when North was selected from the GUI
			{
				for(Referee r :ref)
				{
					if ( index < qualifedRefs.size() ) { // ensuring the qualifedRefs.get(index) is always less than index
						if( qualifedRefs.get(index).getArea().equals("North") ) // home area is North
						{
							homeReferee.add( qualifedRefs.get(index) ); // add referees who home area is North

						} else {
							if ( qualifedRefs.get(index).getArea().equals("Central") && qualifedRefs.get(index).getTravel().charAt(0)=='Y') { // no qualified referees in home area, looking in adjacent area
								adjacentReferee.add( qualifedRefs.get(index) ); // add referees who home area is Central
							} 
							else if(qualifedRefs.get(index).getTravel().charAt(0)=='Y')
							{
								travelReferee.add( qualifedRefs.get(index)); // add all people who are willing to travel North to our list
							}

							else {
								if ( qualifedRefs.get(index).getArea().equals("South")  && qualifedRefs.get(index).getTravel().charAt(0)=='Y') { // no qualified referees in adjacent area, looking in non-adjacent area
									nonAdjacentReferee.add( qualifedRefs.get(index) ); // add referees who home area is South
								}
								else if(qualifedRefs.get(index).getTravel().charAt(0)=='Y')
								{
									travelReferee.add( qualifedRefs.get(index)); // add all people who are willing to travel North to our list
								}							
							}
						}
					}

					index++;
				}
				suitableRefs.addAll( homeReferee );
				suitableRefs.addAll( adjacentReferee );
				suitableRefs.addAll( nonAdjacentReferee );
				suitableRefs.addAll( travelReferee );
				if ( suitableRefs.size() > 1 ) { 
					storeMatchList( suitableRefs, week, level,area );
				} else {
					JOptionPane.showMessageDialog(null, "Two suitable referees were not found for this match", "Not Enough Suitable Referees", JOptionPane.ERROR_MESSAGE);
				}
			}

			if(area.equals("South")) // when South was selected from the GUI
			{
				for(Referee r :ref)
				{
					if ( index < qualifedRefs.size() ) { // ensuring the qualifedRefs.get(index) is always less than index 
						if( qualifedRefs.get(index).getArea().equals("South") ) // home area is South
						{
							homeReferee.add( qualifedRefs.get(index) ); // add referees who home area is South
						}  else {
							if ( qualifedRefs.get(index).getArea().equals("Central")&&qualifedRefs.get(index).getTravel().charAt(2)=='Y' ) { // no qualified referees in home area, looking in adjacent area
								adjacentReferee.add( qualifedRefs.get(index) ); // add referees who home area is Central
							} 
							else if(qualifedRefs.get(index).getTravel().charAt(2)=='Y')
							{
								travelReferee.add( qualifedRefs.get(index)); // add all people who are willing to travel South to our list
							}

							else {
								if ( qualifedRefs.get(index).getArea().equals("North") ) { // no qualified referees in adjacent area, looking in non-adjacent area
									nonAdjacentReferee.add( qualifedRefs.get(index) ); // add referees who home area is South
								}
								else if(qualifedRefs.get(index).getTravel().charAt(0)=='Y')
								{
									travelReferee.add( qualifedRefs.get(index)); // add all people who are willing to travel South to our list
								}							
							}
						}
					}

					index++;
				}
				suitableRefs.addAll( homeReferee );
				suitableRefs.addAll( adjacentReferee );
				suitableRefs.addAll( nonAdjacentReferee );
				suitableRefs.addAll( travelReferee );
				if ( suitableRefs.size() > 1 ) { 
					storeMatchList( suitableRefs, week, level,area );
				} else {
					JOptionPane.showMessageDialog(null, "Two suitable referees were not found for this match", "Not Enough Suitable Referees", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	
	public void selectLevel(String level, List<Referee> r)//return level
	{
		List<Referee> ref = r;
		for(Referee reff:ref)
		{	
			int q = Character.getNumericValue(reff.getQualification().charAt(3));
			System.out.println(q);
			if(level.equals("Senior")&& q >1)
			{
				qualifedRefs.add(reff);

			}
			else if(level.equals("Junior")){
				qualifedRefs.add(reff);

			}
		}
	}

	// This method prepare data to save into matchAllocs.txt
	public void buildMatchAllocsFile() {

		FileWriter writer = null;
		String referees = this.toString();
		try {
			try {
				// open test-out.txt for writing
				writer = new FileWriter(MatchAllocsFile);
				// write four characters to the file
				writer.write(referees);

			}
			finally {
				// executed whether or not an exception is raised above
				// close the file assuming it was successfully opened
				if (writer  != null) writer.close();
			}
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File Not Found", "Input Exception", JOptionPane.ERROR_MESSAGE);
		}
	}

	// this method stores the referees selected into a Match and matchArrayList
	public void storeMatchList( List<Referee> refereeList, int week, String level ,String area) {
		//Match match = new Match();
		String matchLevel = level;  
		List<Referee> tempList = refereeList;

		Match match = new Match( week, matchLevel , area, tempList.get(0).getFirstName(), tempList.get(0).getLastName(), tempList.get(1).getFirstName(), tempList.get(1).getLastName() );
		refList.incrementAllocation( tempList.get(0).getFirstName(), tempList.get(0).getLastName() ); // adding allocation
		refList.incrementAllocation( tempList.get(1).getFirstName(), tempList.get(1).getLastName() );
		matchArrayList.add( match );			
	}
	// Generates a string for MatchAllocs.txt
	public String toString(){

		String headerString = String.format("%-6s%-8s%-8s%-24s%-24s","WEEK","LEVEL","AREA","REFEREE 1","REFEREE 2");
		StringBuilder matchListString = new StringBuilder();

		matchListString.append(headerString);

		matchListString.append("\r");
		//traverse array list and append string


		for (Match thisMatch : matchArrayList){
			matchListString.append(thisMatch.toString());
			matchListString.append("\n");
		}

		return matchListString.toString();
	}
	
	// Generates a string for the MatchReportFrame
	public String allocationReport(){
		// Creates headings
		String fNameHead = new String("First Name");
		String lNameHead = new String("Last Name");
		String allocationsHead = new String("Allocations");

		String headerString = (String.format("%-13s%-13s%-13s",fNameHead,lNameHead,allocationsHead));
		StringBuilder matchListString = new StringBuilder();

		matchListString.append(headerString);

		matchListString.append("\r\n");
		//traverse array list and append string

		for (int i=0; i<suitableRefs.size(); i++){
			Referee thisRef = suitableRefs.get(i);
			if (i == 2) {
				matchListString.append("\r\nOther Suitable Referees\r\n");
			}
			matchListString.append(String.format("%-13s%-13s%-13d",thisRef.getFirstName(),thisRef.getLastName(),thisRef.getAllocations()));
			matchListString.append("\n");
		}

		return matchListString.toString();
	}
}

