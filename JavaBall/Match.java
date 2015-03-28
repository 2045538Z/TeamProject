
//Match Object Class
public class Match {
	
	// match data
	private int week; 
	private String level;
	private String area;
	private String ref1FirstName;
	private String ref1LastName;
	private String ref2FirstName;
	private String ref2LastName;
	
	//default constructor
	Match() {}
	// constructor
	Match( int w, String lvl, String a, String ref1FN, String ref1LN, String ref2FN, String ref2LN ) {
		week = w; 
		level = lvl;
		area = a;
		ref1FirstName = ref1FN;
		ref1LastName = ref1LN;
		ref2FirstName = ref2FN;
		ref2LastName = ref2LN;
	}
	
	public void setWeek( int w ) {
		
		w = week; 
	}

	public void setLevel( String l ) {
		
		l = level; 
	}

	public void setArea( String a ) {
		
		a = area; 
	}

	
	public int getWeek() {
		
		return week; 
	}

	public String getLevel() {
		
		return level; 
	}

	public String getArea() {
		
		return area; 
	}
	
	public void setRef1FirstName( String firstName ) {
		
		firstName = ref1FirstName;
	}

	public void ref1LastName( String lastName ) {
		
		lastName = ref1LastName;
	}
	
	public String ref1FirstName() {
		
		return ref1FirstName;
	}

	public String ref1LastName() {
		
		return ref1LastName;
	}	
	
	public void setRef2FirstName( String firstName ) {
		
		firstName = ref2FirstName;
	}

	public void ref2LastName( String lastName ) {
		
		lastName = ref2LastName;
	}
	
	public String ref2FirstName() {
		
		return ref2FirstName;
	}

	public String ref2LastName() {
		
		return ref2LastName;
	}		
	

	public String toString(){
		String ref1 =(ref1FirstName+" "+ref1LastName);
		String ref2 =(ref2FirstName+" "+ref2LastName);
		String matchInfo = String.format("%-6s%-8s%-8s%-24s%-24s",week,level,area,ref1,ref2);				
		return  matchInfo;
	}

}