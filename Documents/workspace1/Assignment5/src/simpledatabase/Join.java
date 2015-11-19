package simpledatabase;

import java.util.ArrayList;

public class Join extends Operator {

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;

	// Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();

	}

	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		//Delete the lines below and add your code here
		int commonAttribute1=-1,commonAttribute2=-1;
		Tuple leftTuple=leftChild.next();
		Tuple rightTuple=rightChild.next();
		Tuple whereTuple=null;
		Operator operator;
		if(leftTuple!=null){
			operator=rightChild;
			whereTuple=leftTuple;
		}else if(rightTuple!=null){
			operator=leftChild;
			whereTuple=rightTuple;
		}else{
			return null;
		}
		Tuple tuple=operator.child.next();
		for(int i=0;i<whereTuple.getAttributeList().size();i++)
		{
			for(int j=0;j<tuple.getAttributeList().size();j++)
			{
				if(whereTuple.getAttributeName(i).equals(tuple.getAttributeName(j)))
				{
						commonAttribute1=i;
						commonAttribute2=j;
						break;
				}
			}
			
		}
		if(commonAttribute1!=-1&&commonAttribute2!=-1){
			do{
				if(whereTuple.getAttributeValue(commonAttribute1).equals(tuple.getAttributeValue(commonAttribute2))){
					newAttributeList=new ArrayList<Attribute>(tuple.getAttributeList());
					for(int i=0;i<whereTuple.getAttributeList().size();i++){
						newAttributeList.add(whereTuple.getAttributeList().get(i));
					}
					break;
				}else{
					tuple=operator.child.next();
				}
			}while(tuple!=null);
			operator.child=new Table(operator.child.from);
		}
		
		if(newAttributeList.size()==0){
			return null;
		}else{
			return new Tuple(newAttributeList);
		}
	}

	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}