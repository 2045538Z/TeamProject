// Referee Object
public class Referee implements Comparable<Referee>{

	private String id;
	private String firstName;
	private String lastName;
	private String qualification;
	private int allocations;
	private String area;
	private String travel;      // e.g. north middle south  YNY
	private String fileLine;


	// constructor for initial construction from file data, string is one line of the file

	public Referee(String referee) {
		//split code and assign to instance variables
		fileLine = referee;
		String[] refdata=fileLine.split("\\s+"); //split will use any white space as delimeter 
		id = refdata[0];
		firstName = refdata[1];
		lastName = refdata[2];
		qualification = refdata[3];
		allocations = Integer.parseInt(refdata[4]); //parse to integer for number of allocations 
		area = refdata[5];
		travel = refdata[6];
	}



	//accessors  
	public String getID(){
		return id;
	}
	public String getFirstName(){
		return firstName;
	}
	public String getLastName(){
		return lastName;
	}
	public String getQualification(){
		return qualification;
	}
	public int getAllocations(){
		return allocations;
	}
	public String getArea(){
		return area;
	}
	public String getTravel(){
		return travel;
	}


	//mutators 
	public void setID(String id){
		this.id = id;
	}
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	public void setQualification(String qualification){
		this.qualification = qualification;
	}
	public void setAllocations(int allocations){
		this.allocations = allocations;
	}
	public void setArea(String area){
		this.area = area;
	}
	public void setTravel(String travel){
		this.travel = travel;
	}


	// compare refs on allocations
	public int compareTo(Referee other) {
			int thisAllocs = this.getAllocations();
			int otherAllocs = other.getAllocations();
			
			if (thisAllocs < otherAllocs)
				return -1;
			else if (thisAllocs == otherAllocs)
				return 0;
			else
				return 1;
	}
	public String returnDetails(){
		String s = String.format("%s %s %s %s %d %s %s\r\n", id,firstName,lastName,qualification,allocations,area, travel);
		return s;
	}
	
	public void incrementAllocation() {
		this.allocations++;
	}
}
