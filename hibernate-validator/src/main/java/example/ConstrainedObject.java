package example;

public class ConstrainedObject {

	public ConstrainedObject(String constrainedString) {
		this.constrainedString = constrainedString;
	}

	// this has a @Size(min=2) constraint applied by constraints.xml
	private String constrainedString;

}
