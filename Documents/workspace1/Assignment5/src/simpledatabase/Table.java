package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Table extends Operator {
	private BufferedReader br = null;
	private boolean getAttribute = false;
	private Tuple tuple;
	private String attributeLine,dataTypeLine;
	public Table(String from) {
		this.from = from;// operator form

		// Create buffer reader
		try {
			br = new BufferedReader(
					new InputStreamReader(getClass().getResourceAsStream("/datafile/" + from + ".csv")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create a new tuple and return the tuple to its parent. Set the attribute
	 * list if you have not prepare the attribute list
	 * 
	 * @return the tuple
	 */
	@Override
	public Tuple next() {
		// Delete the lines below and add your code here
		String tupleLine = "";
		if (br != null) {
			try {
				if (!getAttribute) {
					attributeLine = br.readLine();
					dataTypeLine = br.readLine();
					tupleLine = br.readLine();
					
					if (!tupleLine.equals("")) {
						tuple = new Tuple(attributeLine, dataTypeLine, tupleLine);
						tuple.setAttributeName();
						tuple.setAttributeType();
						tuple.setAttributeValue();
						getAttribute = true;

						return tuple;
					} else {
						return null;
					}
				} else {
					tupleLine = br.readLine();
					if (tupleLine==null) {
						return null;
					} else {
						tuple = new Tuple(attributeLine, dataTypeLine, tupleLine);
						tuple.setAttributeName();
						tuple.setAttributeType();
						tuple.setAttributeValue();
						return tuple;
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;

	}

	/**
	 * The function is used to get the attribute list of the tuple
	 * 
	 * @return the attribute list
	 */
	public ArrayList<Attribute> getAttributeList() {
		return tuple.getAttributeList();
	}

}