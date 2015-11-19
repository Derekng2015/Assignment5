package simpledatabase;

import java.util.ArrayList;

public class Selection extends Operator {

	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;

	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate,
			String whereValuePredicate) {
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		attributeList = new ArrayList<Attribute>();

	}

	/**
	 * Get the tuple which match to the where condition
	 * 
	 * @return the tuple
	 */
	@Override
	public Tuple next() {
		// Delete the lines below and add your code here
		if (child.from.equals(whereTablePredicate)) {
			do {
				Tuple tuple = child.next();
				if (tuple != null) {
					attributeList = getAttributeList();
					for (int i = 0; i < attributeList.size(); i++) {
						if (tuple.getAttributeName(i).equals(whereAttributePredicate)) {
							if (tuple.getAttributeValue(i).equals(whereValuePredicate)) {
								return new Tuple(attributeList);
							}
						}

					}
				} else {
					return null;
				}
			} while (true);
		}
		return null;

	}

	/**
	 * The function is used to get the attribute list of the tuple
	 * 
	 * @return the attribute list
	 */
	public ArrayList<Attribute> getAttributeList() {

		return (child.getAttributeList());
	}

}