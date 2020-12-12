package copmarators;

import java.util.Comparator;

import testtabpane.DataRow;

public class StringComparator implements Comparator<DataRow> {
	private boolean desc;
	public StringComparator(boolean desc) {this.desc=desc;}

	@Override
	public int compare(DataRow o1, DataRow o2) {
		int iDesc = 1; if (desc) {iDesc = -1;}
		return (o1.getAdprice()>o2.getAdprice()?-1*iDesc:1*iDesc);		
	}
	
	
}
