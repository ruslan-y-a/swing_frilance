package testtabpane;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import copmarators.NumberComparator;
import copmarators.StringComparator;

public class Dao {
	private static String publisher="Name";	
	private static List<DataRow> dataRows = new ArrayList<>();
	private static Double totalCostSum=0d;			
	public static void setDataRows(List<DataRow> dataRows) {Dao.dataRows = dataRows;}
	public static List<DataRow> getDataRows() {return dataRows;}
	public static String getPublisher() {return publisher;}
	public static void setPublisher(String publisher) {Dao.publisher = publisher;}
	
	public static Double getTotalCostSum() {return Dao.totalCostSum;}  
	public static void setTotalCost(double oldV, double newV) {
		if (totalCostSum>0) {totalCostSum-=oldV;} 			
		  totalCostSum+=newV; updateFractions();
		  }
	
	public static double getFraction(double totalCost) {
		if (totalCostSum>0) {
			return Math.round(10000*totalCost/getTotalCostSum())/100d;
			}
		return 0d;
	}
	public static void updateFractions() {
	//	double t = 100*x.getTotalCost()/getTotalCostSum();
		dataRows.forEach(x -> x.setFraction(
				Math.round(10000*x.getTotalCost()/getTotalCostSum())/100d
				));
	}
	
	public static DataRow get(int i) {
		DataRow dr =null;
    	try {dr=dataRows.get(i);} catch (Exception e) {}
    	if (dr==null) {dr=new DataRow(); add(dr);}
		return dr;
	}
	
	public static String read(int x,int y) {
		String ss="";		
    	try {ss=dataRows.get(y).get(x).toString();} catch (Exception e) {}    	
		return ss;
	}
	
	public static int add(DataRow dr) {
		dataRows.add(dr); return dataRows.size()-1;
	}
	
	public static boolean set(int i,DataRow dr) {		
    	if (dataRows.size()<i) {return false;}
    	else if(dataRows.size()==i) {dataRows.add(dr); }
    	else {dataRows.set(i, dr) ;}
		return true;
	}
	
	public static List<DataRow> sort(int column, boolean desc) {
		Comparator<DataRow> cp;
		 if (column<=0) {cp = new StringComparator(desc);} 	
		 else {cp = new NumberComparator(column, desc);}
		 List<DataRow> ndrl = new ArrayList<DataRow>(dataRows); ndrl.sort(cp); 
		return ndrl;
	}
	
}
