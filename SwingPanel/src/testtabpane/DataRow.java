package testtabpane;

public class DataRow {
   
public DataRow(String publication, Double adprice, Integer readers, Integer ads, Double totalCost, Double fraction,
			Integer totalReaders, Integer publisherid) {		
		this.publication = publication;
		this.adprice = adprice;
		this.readers = readers;
		this.ads = ads;
		this.totalCost = totalCost;
		this.fraction = fraction;
		this.totalReaders = totalReaders;
		this.publisherid = publisherid;
	}
public DataRow(Integer id, String publication, Double adprice, Integer readers, Integer ads, Integer publisherid) {
	this.id=id;
	this.publication = publication;
	this.adprice = adprice;
	this.readers = readers;
	this.ads = ads;
	this.publisherid = publisherid;
}
public DataRow() {}
//////////////////////////////////////////////////////////	
  public static String getName(int i) {
	  
		   if (i<=0) {return "Publication";}; 	
		   switch (i) {  
		   case 1: return "Ad Price"; //break;
		   case 2: return "Readers"; //break;
		   case 3: return "Ads"; //break;
		   case 4: return "Total cost"; //break;
		   case 5: return "Fraction (%)"; //break;   
		   }
		   return "Total Readers";		
  }
  public Boolean checkSet(int i,Object o) {
	 try { 	    
	   switch (i) {
	   case 0:  this.publication =o.toString(); break;
	   case 1:  Double dd=Double.parseDouble(o.toString());
		        if (dd<0) {return false;} else {
		        	setAdprice(dd);}  
		        break;
	   case 2: Integer ii=Integer.parseInt(o.toString());
                if (ii<0) {return false;} else {setReaders(ii);}  
                 break;
	   case 3: Integer ii1=Integer.parseInt(o.toString());
               if (ii1<0) {return false;} else {
            	   setAds(ii1);}  
               break;  
	   }
	   return true;
	 } catch (Exception e) {return false;}  
}	
 /////////////////////////////////////////////////////////// 
 private Integer id;
 private String publication;
 private Double adprice=0d;
 private Integer readers=0;
 private Integer ads=0;
 private Double totalCost=0d;
 private Double fraction=0d;
 private Integer totalReaders=0;
 private Integer publisherid;
/////////////////////////////////////////
 public Integer getId() {return id;}
 public void setId(Integer id) {this.id = id;}
 public Integer getPublisherid() {return publisherid;} 
 public void setPublisherid(Integer publisherid) {this.publisherid = publisherid;}
 public String getPublication() {return publication;}
 public void setPublication(String publication) {this.publication = publication;}
 public Double getAdprice() {return adprice;}
 
 public void setAdprice(Double adprice) {
	 this.adprice = adprice; 
	 Dao.setTotalCost(this.totalCost, this.adprice*this.ads);	
	 this.totalCost =this.adprice*this.ads;
	 this.fraction = Dao.getFraction(this.totalCost);
   }
 
 public Integer getReaders() {return readers;}
 public void setReaders(Integer readers) {
	 if (totalReaders>0) {totalReaders-=this.readers*this.ads;} 
	   this.readers = readers; 
	   this.totalReaders =this.readers*this.ads; 
	 }
 public Integer getAds() {return ads;}
 
 public void setAds(Integer ads) {
	 if (totalReaders>0) {totalReaders-=this.readers*ads;}
		 
	 this.ads = ads; 
	 Dao.setTotalCost(this.totalCost, this.adprice*this.ads);	
	 this.totalCost =this.adprice*this.ads;
	 this.fraction = Dao.getFraction(this.totalCost);	 	
		 
	 this.totalReaders =this.readers*this.ads;	 
 }

 public void setTotalCost(Double totalCost) { this.totalCost = totalCost;}
 public Double getTotalCost() {return totalCost;}
 public Double getFraction() {return this.fraction;}; public Double setFraction(Double fraction) {return this.fraction=fraction;}
 public Integer getTotalReaders() {return this.totalReaders;}
 public void setTotalReaders(Integer totalReaders) { this.totalReaders = totalReaders;}

 public Object get(int i) {
   if (i<=0) {return publication;}; 	
   switch (i) {  
   case 1: return adprice; //break;
   case 2: return readers; //break;
   case 3: return ads; //break;
   case 4: return getTotalCost(); //break;
   case 5: return getFraction(); //break;   
   }
   return getTotalReaders();
 }

 public void del(int i) {
	   if (i<=0) {this.publication = null;}; 	
	   switch (i) {  
	   case 1: this.adprice = null; //break;
	   case 2: this.readers=null; //break;
	   case 3: this.ads=null; //break;	    
	   }
	   
	 }
@Override
public String toString() {
	return "DataRow [publication=" + publication + ", adprice=" + adprice + ", readers=" + readers + ", ads=" + ads
			+ ", totalCost=" + totalCost + ", fraction=" + fraction + ", totalReaders=" + totalReaders + "]";
}
 
 
}
