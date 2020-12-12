package testtabpane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class LsqlDao {
	private static final String CON_STR = "jdbc:sqlite:d:/Program Files/sqlite/DB/sdata.db";	    
	private Connection connection; 
    public synchronized Connection getConnection() {
    	if(connection == null) {    		
    		try {
				connection = DriverManager.getConnection(CON_STR);
			} catch (SQLException e) {e.printStackTrace();}    	   
    	 }
    		return connection;
    	}
    
    public LsqlDao() throws SQLException, ClassNotFoundException {     
    	Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection(CON_STR);
    }
/////////////////////////////////////////////////////////////////////////    
//////////////////////////////////////////////////////////////////////////
    private static Double totalCostSum=0d;	
    
    
    
    public void delete(String str) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM data WHERE id IN (SELECT * FROM (SELECT publishers.id FROM publishers WHERE publishers.publisher=?) AS p)")) {
            statement.setString(1, str);         
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM data WHERE id = ?")) {
            statement.setObject(1, id);         
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
////////////////////////////////////////////////////////////////////////////    
    private String sortOrder(int column, boolean desc) {
    	String sname="";
    	String adsc = " ASC ";
    	if (desc) {adsc = " DESC ";}
    	if (column<=0) {sname ="publisherid";}
    	switch (column) {
    	case 1: sname ="publication"; break;
    	case 2: sname ="adprice"; break;
    	case 3: sname ="readers"; break;
    	case 4: sname ="ads"; break;
    	case 5: sname ="totalcost"; break;
    	case 6: sname ="fraction"; break;    	
    	}
    	if (column>6) {sname ="totalreaders";}
    	    	    	    	    	    	    	    	
        return "ORDER BY " +sname + adsc;	
	}
    
    public List<DataRow> sort(int column, boolean desc) {	
		 List<DataRow> ndrl = new ArrayList<DataRow>();// ndrl.sort(cp); 
		
		 try (Statement statement = this.connection.createStatement()) {          	        
	            ResultSet resultSet = statement.executeQuery("SELECT * FROM data LEFT JOIN publishers ON data.publisherid=publishers.id " + sortOrder(column, desc));
	        
	            while (resultSet.next()) {
	            	ndrl.add(new DataRow(resultSet.getInt("id"),
	                		             resultSet.getString("publication"),
	                                     resultSet.getDouble("adprice"),
	                                     resultSet.getInt("readers"),
	                                     resultSet.getInt("ads"),	                                     
	                                     resultSet.getInt("publisherid")));
	                                           
	            }	        
	 
	        } catch (SQLException e) {
	            e.printStackTrace(); return null;
	        }
		 	
		 updateTatals(ndrl);
		 return ndrl;
	}
    
    public void updateTatals(List<DataRow> list) {
    	 totalCostSum=0d;  
    	 for(DataRow x:list) {x.setTotalCost(x.getAdprice()*x.getAds()); totalCostSum+=x.getTotalCost();};
    	 for(DataRow dr:list) {
    		 dr.setTotalReaders(dr.getReaders()*dr.getAds());	 
    		 dr.setFraction(100*dr.getTotalCost()/totalCostSum);	 
    	 };
    }
    //////////////////////////////
    public void update(DataRow dataRow) {      
        try (PreparedStatement statement = this.connection.prepareStatement(
        		"UPDATE data SET publication= ?, adprice= ?, readers = ?, ads = ?, publisherid = ? WHERE id = ?")) {
            statement.setInt(6, dataRow.getId());            
            statement.setString(1, dataRow.getPublication());
            statement.setDouble(2, dataRow.getAdprice());
            statement.setInt(3, dataRow.getReaders());
            statement.setInt(4, dataRow.getAds());
            statement.setInt(5, dataRow.getPublisherid());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void add(DataRow dataRow) {      
        try (PreparedStatement statement = this.connection.prepareStatement(
                        "INSERT INTO data('publication', 'adprice', 'readers', 'ads', 'publisherid') " +
                         "VALUES(?, ?, ?, ?, ?)")) {
            statement.setString(1, dataRow.getPublication());            
            statement.setDouble(2, dataRow.getAdprice());
            statement.setInt(3, dataRow.getReaders());
            statement.setInt(4, dataRow.getAds());
            statement.setInt(5, dataRow.getPublisherid());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
