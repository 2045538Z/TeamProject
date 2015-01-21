// Referee Object
public class Referee implements Comparable<Referee>{

	private String id;
	private String firstName;
	private String lastName;
	private String qualification;
	private int allocations;
	private String area;
	private String travel;      // e.g. north middle south  YNY


	// constructor for initial construction from file data, string is one line of the file

	public Referee(String referee) {
		//split code and assign to instance variables
		String[] refdata=fileLine.split(" ");
		id = refdata[0];
		firstName = refdata[1];
		lastName = refdata[2];
		qualification = refdata[3];
		allocations = Integer.parseInt(refdata[4]); //parse to integer for number of allocations 
		area = refdata[5];
		travel = refdata[6];
	}



	//accessors  
	public getID(){
		return id;
	}
	public getFirstName(){
		return firstName;
	}
	public getLastName(){
		return lastName;
	}
	public getQualification(){
		return qualification;
	}
	public getAllocations(){
		return allocations;
	}
	public getArea(){
		return area;
	}
	public getTravel(){
		return travel;
	}


	//mutators 
	public setID(String id){
		this.id = id;
	}
	public setFirstName(String firstName){
		this.firstName = firstName;
	}
	public setLastName(String lastName){
		this.lastName = lastName;
	}
	public setQualification(String qualification){
		this.qualification = qualification;
	}
	public setAllocations(int allocations){
		this.allocations = allocations;
	}
	public setArea(String area){
		this.area = area;
	}
	public setTravel(String travel){
		this.travel = travel;
	}


	// compare ids lexicographically 
	public int compareTo(Referee other){
		if (this != null && other !=null)
		{
			int result = this.id.compareTo(other.getID());
			return result;
		}
		return 0;
	}


}
