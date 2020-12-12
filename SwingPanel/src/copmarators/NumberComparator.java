package copmarators;
import java.util.Comparator;

import testtabpane.DataRow;

public class NumberComparator implements Comparator<DataRow> {
	private int column;	
	private boolean desc;
	public NumberComparator(int column, boolean desc) {this.column=column; this.desc=desc;}

	@Override
	public int compare(DataRow o1, DataRow o2) {
		if (column<1) {column=1;}
		int iDesc = 1; if (desc) {iDesc = -1;}
	    if (o1.get(column).getClass().toString().contains("Integer")) {
	    	Integer i1 = (Integer)o1.get(column); 
	    	Integer i2 = (Integer)o2.get(column);
	    	return (i1*iDesc<i2*iDesc?-1:1);
	    }
	    else {
	    	 Double d1 = (Double)o1.get(column); 
	    	 Double d2 = (Double)o2.get(column);
	    	return (d1*iDesc<d2*iDesc?-1:1);
	    }								
	}
}
