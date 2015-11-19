package simpledatabase;

import java.util.ArrayList;

public class Sort extends Operator {

	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;

	public Sort(Operator child, String orderPredicate) {
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();

	}

	/**
	 * The function is used to return the sorted tuple
	 * 
	 * @return tuple
	 */
	@Override
	public Tuple next() {
		// Delete the lines below and add your code here
		int wherePosition = -1;
		Tuple tuple = child.next();
		if (tuplesResult.size() == 0 && tuple != null) {
			while (tuple != null) {
				tuplesResult.add(tuple);
				tuple = child.next();
			}
			for (int i = 0; i < getAttributeList().size(); i++) {
				if (orderPredicate.equals(tuplesResult.get(0).getAttributeName(i))) {
					wherePosition = i;
					break;
				}
			}
			for(int i=0;i<tuplesResult.size();i++){
				Tuple compateTuple=tuplesResult.get(i);
				int min=i;
				for(int j=i+1;j<tuplesResult.size();j++){
					Tuple compateTuple2=tuplesResult.get(j);
					int result=-1;
						Type dataType=compateTuple.getAttributeType(wherePosition);
						switch(dataType.type){
						case INTEGER:
							result=Integer.compare((int)compateTuple.getAttributeValue(wherePosition), (int)compateTuple2.getAttributeValue(wherePosition));
							break;
						case DOUBLE:
							result=Double.compare((double)compateTuple.getAttributeValue(wherePosition), (double)compateTuple2.getAttributeValue(wherePosition));
							break;
						case LONG:
							result=Long.compare((long)compateTuple.getAttributeValue(wherePosition), (long)compateTuple2.getAttributeValue(wherePosition));
							break;
						case SHORT:
							result=Short.compare((short)compateTuple.getAttributeValue(wherePosition), (short)compateTuple2.getAttributeValue(wherePosition));
							break;
						case FLOAT:
							result=Float.compare((float)compateTuple.getAttributeValue(wherePosition), (float)compateTuple2.getAttributeValue(wherePosition));
							break;
						case STRING:
							result=((String)compateTuple.getAttributeValue(wherePosition)).compareTo((String)compateTuple2.getAttributeValue(wherePosition));
							break;
						case BOOLEAN:
							result=Boolean.compare((boolean)compateTuple.getAttributeValue(wherePosition), (boolean)compateTuple2.getAttributeValue(wherePosition));
							break;
						case CHAR:
							result=((String)compateTuple.getAttributeValue(wherePosition)).compareTo((String)compateTuple2.getAttributeValue(wherePosition));
							break;
						case BYTE:
							result=Byte.compare((byte)compateTuple.getAttributeValue(wherePosition),(byte)compateTuple2.getAttributeValue(wherePosition));
							break;
						}
						if(result>=0){
							min=j;
						}
				}
				if(min!=i){
					tuplesResult.set(i, tuplesResult.get(min));
					tuplesResult.set(min,compateTuple);
				}
			}
		}
		if (tuplesResult.size() != 0) {
			Tuple getTuple = tuplesResult.remove(0);
			newAttributeList=new ArrayList<Attribute>(getTuple.getAttributeList());
			return new Tuple(newAttributeList);
		} else
			return null;
	}

	/**
	 * The function is used to get the attribute list of the tuple
	 * 
	 * @return attribute list
	 */
	public ArrayList<Attribute> getAttributeList() {
		return child.getAttributeList();
	}

}