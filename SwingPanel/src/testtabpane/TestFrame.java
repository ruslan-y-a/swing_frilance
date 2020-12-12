package testtabpane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class TestFrame extends JFrame {		

	private static interface Cell {
		public void setLabel();   			
		public void setLabel(String str); 
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 6551791996479874171L;
    private static final String DEFAULTVALUE="Value1";
    private static List<Row> datarows = new ArrayList<>(); 
    static void updateRowList(int i) {datarows.get(i).update();}
    public static TestFrame instance;
    public static int sortColumn=0;
    public static boolean reverseSort=false;

    public TestFrame() {
 
        super("calculated data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        Font font = new Font("Verdana", Font.PLAIN, 10);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(font);
 
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());     
 
        AboutTab at = new AboutTab();
                      
        JPanel dataPane = new JPanel();
        ///////////////
        TopTab TT = new TopTab(); TestPane TP = new TestPane();
        dataPane.add(TT, BorderLayout.CENTER);
        dataPane.add(TP, BorderLayout.CENTER);
          
        JPanel sumPane = new JPanel();
        sumPane.setPreferredSize(new Dimension(400, 40));
        sumPane.add(new JLabel("Total Cost Sum: " + Dao.getTotalCostSum().toString()), BorderLayout.PAGE_END);
        dataPane.add(sumPane);
        
        tabbedPane.addTab("DATA", dataPane);        
                      
        content.add(tabbedPane, BorderLayout.CENTER); 
        getContentPane().add(content);
 
        setPreferredSize(new Dimension(800, 600));
        
        tabbedPane.addTab("ABOUT", at);        
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);        
        
        JLabel labelListener = new JLabel("");
        labelListener.putClientProperty("TestPane", false);
        labelListener.putClientProperty("Sort", 0);                     
         
        add(labelListener);
        /*
        labelListener.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				  if (evt.getPropertyName().equals("Sort")) {

	            	  int isort = (Integer) labelListener.getClientProperty("Sort");
					  boolean bsort = !(Boolean) labelListener.getClientProperty("SortBack");
					  //List<DataRow> dataRows = Dao.getDataRows();
					  Dao.setDataRows(Dao.sort(isort, bsort));
	            	 System.out.println("Soting by: " + isort);
	                dataPane.removeAll();
	                dataPane.add(new TopTab(), BorderLayout.CENTER);
	                dataPane.add(new TestPane(), BorderLayout.CENTER);

	                JPanel sumPane = new JPanel();
	                sumPane.setPreferredSize(new Dimension(400, 40));
	                sumPane.add(new JLabel("Total Cost Sum: " + Dao.getTotalCostSum().toString()), BorderLayout.PAGE_END);
	                dataPane.add(sumPane);

	          //      labelListener.putClientProperty("Sort", 0);
			        labelListener.putClientProperty("SortBack", bsort);
			        //Dao.setDataRows(dataRows);
	                
	                labelListener.putClientProperty("TestPane", true);
	             } 
				
			}        	
        });*/
        //////////////////    
        this.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
/////////////////////////////////////////////////////				
			  if (evt.getPropertyName().equals("TestPane")) {
			//	  System.out.println("=========TestPane form fire");	
				 				  
				    dataPane.removeAll();
				    dataPane.add(new TopTab(), BorderLayout.CENTER);
			        dataPane.add(new TestPane(), BorderLayout.CENTER);

			        JPanel sumPane = new JPanel();
			        sumPane.setPreferredSize(new Dimension(400, 40));
			        sumPane.add(new JLabel("Total Cost Sum: " + Dao.getTotalCostSum().toString()), BorderLayout.PAGE_END);
			        dataPane.add(sumPane);
			        			        
			  }
////////////////////////////////////////////////
/////////////////////////////////////////////////////				
             if (evt.getPropertyName().equals("Sort")) {

            	  int isort = sortColumn;
				  boolean bsort = reverseSort;
				  //List<DataRow> dataRows = Dao.getDataRows();
				  Dao.setDataRows(Dao.sort(isort, bsort));
            	 System.out.println("Soting by: " + isort);
                dataPane.removeAll();
                dataPane.add(new TopTab(), BorderLayout.CENTER);
                dataPane.add(new TestPane(), BorderLayout.CENTER);

                JPanel sumPane = new JPanel();
                sumPane.setPreferredSize(new Dimension(400, 40));
                sumPane.add(new JLabel("Total Cost Sum: " + Dao.getTotalCostSum().toString()), BorderLayout.PAGE_END);
                dataPane.add(sumPane);

        //        labelListener.putClientProperty("Sort", 0);
                reverseSort=!reverseSort;
		        //Dao.setDataRows(dataRows);                
                //labelListener.putClientProperty("TestPane", true);
             } 
////////////////////////////////////////////////

			  
			}        	
        });
        ///////////////////////////////////
    }
 
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                instance = new TestFrame();
            }
        });
    }
    
    public class TestPane extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TestPane() {
        	  
            setLayout(new GridBagLayout());                                              
            GridBagConstraints gbc = new GridBagConstraints();                        
        //    System.out.println("=========TestPane painting");
            HeaderTab headerTab;
            for (int i = 0; i < 7; i++) {
            	headerTab = new HeaderTab(DataRow.getName(i),i);
            	headerTab.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
            	gbc.gridx = i;
                gbc.gridy = 0;
            	add(headerTab, gbc);             	
            }
            JPanel cellPane; 
            for (int row = 1; row <= 5; row++) {
            	DataRow dr =Dao.get(row-1);
            	Row rowCell = new Row(); datarows.add(rowCell);
                for (int col = 0; col < 7; col++) {
                    gbc.gridx = col;
                    gbc.gridy = row;

                    cellPane = (col < 4?new CellPane(90,20,col,row-1): new BaseJPane(90,20,col,row-1));       
                    rowCell.add((Cell)cellPane);
                    
                    Border border = null;
                    if (row <= 4) {
                        if (col < 6) {
                            border = new MatteBorder(1, 1, 0, 0, Color.BLACK);
                        } else {
                            border = new MatteBorder(1, 1, 0, 1, Color.BLACK);
                        }
                    } else {
                        if (col < 6) {
                            border = new MatteBorder(1, 1, 0, 0, Color.BLACK);
                        } else {
                            border = new MatteBorder(1, 1, 0, 1, Color.BLACK);
                        }
                    }
                    cellPane.setBorder(border);
                    add(cellPane, gbc);
                }
                rowCell.importData(dr);
            }            
            setBorder( new MatteBorder(0, 0, 1, 0, Color.BLACK));
        }
    }
//////////////////////////////////////////////////////////////////////////	
///////CELL////////////////////////////////////////////////////////////	
///////////////////////////////////////////////////////////////////    
    public class CellPane extends JPanel implements Cell {// AbstractJPane
    	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;   
      	public int getRow() {return x;}
		public void setRow(int x) {this.x = x;}
		public int getCell() {return y;}
		public void getCell(int y) {this.y = y;} 
														
		public void setLabel() {						
			    String ss=DEFAULTVALUE;
	        	try {ss=Dao.read(x,y);} catch (Exception e) {} 	        	
	        	label = new JLabel(ss);
			}
		public void setLabel(String str) {label = new JLabel(str);}
		
		private JLabel label;// = new JLabel("Value1");	
		public JLabel getLabel() {return label;}
	    private int xd; private int yd;		
	    private int x,y;
        private Color defaultBackground;      
        private int buttonSize=100;                                         

		public CellPane(int xd, int yd,int x, int y) {
			this.xd=xd; this.yd=yd; this.x=x; this.y=y;
        			        	
        	setBackground(Color.WHITE);            
        	setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx=1;
            Border border = new MatteBorder(1, 1, 1, 1, Color.BLACK);
            setLabel();
        	add(getLabel());           	           
        	MyButton mb2 = new MyButton(buttonSize,yd,x,y);
        	mb2.setBorder(border);        	        	
            add(mb2, gbc);
	
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    defaultBackground = getBackground();
                    setBackground(Color.BLUE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(defaultBackground);
                }
            });                                 
        }

        @Override
        public Dimension getPreferredSize() {
        	 return new Dimension(xd, yd);
        }       
        
    }
/////////////////CALCULATED CELL////////////////////////////////////////
    public class BaseJPane extends JPanel  implements Cell {	
    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = -2715985846176038406L;
    	        private JLabel label;// = new JLabel("Calculated");
    	        private int x,y;
    	        private int xd, yd;
//////////////////////////////////////////////////    	            	        	      
    	      	public int getRow() {return x;}
    			public void setRow(int x) {this.x = x;}
    			public int getCell() {return y;}
    			public void getCell(int y) {this.y = y;} 
    		
    			public JLabel getLabel() {return label;}			
    			
    			public BaseJPane(int xd, int yd, int x, int y) {
    				this.xd=xd; this.yd=yd; this.x=x; this.y=y; 
    				setLabel();
    				add(label);
    			 }    			
    			public void setLabel() {      				
    				try {    					
    				    label = new JLabel(Dao.read(x,y));
    				    } catch (Exception e) {label = new JLabel("Calculated");}
    				}    			
    			public void setLabel(String str) {    				 
    				 label = new JLabel(str);
    				}
    			
    		    @Override
    	        public Dimension getPreferredSize() {
    	        	 return new Dimension(xd, yd);
    	        }
    }
    /////////////////////////////////////////////////////////   
///////////////////////////////////////    
public class MyButton extends JPanel {    	
/**
* 
*/
private static final long serialVersionUID = 5427018828124528105L;
//private CellPane cellPane;
private int xcell,ycell;
private JLabel label = new JLabel("Edit");    	
private Color defaultBackground;      
private int xd; private int yd;

public MyButton(int xd, int yd,int xcell, int ycell) {this(xd,yd); this.xcell=xcell; this.ycell=ycell; } //this.cellPane=cellPane;

public MyButton(int xd, int yd) {     
  this.xd=xd; this.yd=yd;
  add(label);	  
  addMouseListener(new MouseAdapter() {
   @Override
   public void mouseEntered(MouseEvent e) {
     defaultBackground = getBackground();
     setBackground(Color.GREEN);
   }
  @Override
  public void mouseExited(MouseEvent e) {
    setBackground(defaultBackground);
  }

  public void mouseClicked(MouseEvent e) {                
   //System.out.println("setting x y" + xcell + " "+ ycell);          
  String svalue = DEFAULTVALUE;              
  try {svalue = Dao.get(ycell).get(xcell).toString();} catch (Exception f) {}
   Object result = JOptionPane.showInputDialog(
    MyButton.this,
    "New Value:",
    "Edit", 
    JOptionPane.QUESTION_MESSAGE, 
    null, null, svalue);
////////////////////remove
  if (!svalue.equals(DEFAULTVALUE) && (result==null || ((String)result).isBlank())) {  
    int result1 = JOptionPane.showConfirmDialog(
      MyButton.this, 
      "<html><h2>Delete " +svalue +" ?</h2>",
      "Deleting",
      JOptionPane.YES_NO_OPTION);
    if (result1 == JOptionPane.YES_OPTION) {
       Dao.get(ycell).del(xcell);
      instance.firePropertyChange("TestPane", false, true);
    }
////////////////////save                	     
} else {
//	 System.out.println("2)setting x y" + xcell + " "+ ycell);     
   DataRow dr = Dao.get(ycell);
   if (!dr.checkSet(xcell, result)) {
     JOptionPane.showConfirmDialog(
	  MyButton.this, 
      "<html><h2>Wrong data: " + result +". Try again!</h2>",
      "ERROR",
      JOptionPane.YES_OPTION);
   } else {                    		            
     Dao.set(ycell, dr);	                     
//	 System.out.println("setting Dao " + Dao.read(xcell,ycell));
   instance.firePropertyChange("TestPane", false, true);
          }                    	 
      }
    }
  });
}

   @Override
   public Dimension getPreferredSize() {
    return new Dimension(xd, yd);
   }
}
 //////////////////////////////////////////////////////////////////////
 //TOP
 ///////////////////////////////////////////////////////////////
public class TopButton extends JPanel {    	
/**
* 
*/
private static final long serialVersionUID = 5427018828124528105L;
//private CellPane cellPane;
private JLabel label;    	
private Color defaultBackground;      
private int xd; private int yd;

public TopButton(int xd, int yd) {     
  this.xd=xd; this.yd=yd;
  label = new JLabel("Edit");
  add(label);	  
  ////////////////////
  addMouseListener(new MouseAdapter() {
    @Override
     public void mouseEntered(MouseEvent e) {
      defaultBackground = getBackground();
      setBackground(Color.GREEN);}
   
    @Override
     public void mouseExited(MouseEvent e) {
     setBackground(defaultBackground);}

     public void mouseClicked(MouseEvent e) {                            
      String svalue = Dao.getPublisher();
      Object result = JOptionPane.showInputDialog(
	  TopButton.this,
      "New Value:",
      "Edit", 
      JOptionPane.QUESTION_MESSAGE, 
      null, null, svalue);
      
       Dao.setPublisher((String)result);
      instance.firePropertyChange("TestPane", false, true);     
     }     
    });  
  /////////////////////
  }
 
   @Override
   public Dimension getPreferredSize() {
    return new Dimension(xd, yd);
   }
}

public class CellPaneTop extends JPanel {
   	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	private int xd; private int yd;
    //   private Color defaultBackground;   
       private JLabel label;
       private int buttonSize=100;
                                       
		public CellPaneTop(int xd, int yd) {
       	this.xd=xd; this.yd=yd;    
       	label = new JLabel(Dao.getPublisher());
       	setBackground(Color.WHITE);
           setLayout(new GridBagLayout());
           GridBagConstraints gbc = new GridBagConstraints();
           gbc.anchor = GridBagConstraints.EAST;
           gbc.weightx=1;
           Border border = new MatteBorder(1, 1, 1, 1, Color.BLACK);
       	  add(label);           	           
       	TopButton mb2 = new TopButton(buttonSize,yd);
       	mb2.setBorder(border);        	   
       	//setBorder( new MatteBorder(0, 0, 1, 0, Color.BLACK));
           add(mb2, gbc);            
       }
       @Override
       public Dimension getPreferredSize() {
       	 return new Dimension(xd, yd);
       }
   }

    //////////////////////////////////////////////////
    public class TopTab extends JPanel {    	    		
        //private Color defaultBackground;       
        private JLabel label = new JLabel("Publisher:");       
                
		public TopTab() {     
				  	    
		        setLayout(new BorderLayout());     		        		        		        		        		        
		        Font font = new Font("Verdana", Font.PLAIN, 10);		        	      
		        setFont(font);
		        setLayout(new GridLayout(1,4,40,10));
		        		        
	            GridBagConstraints gbc = new GridBagConstraints();
	            gbc.anchor = GridBagConstraints.CENTER;
	            gbc.weightx=1;
		        
	            CellPaneTop cp = new CellPaneTop(250,22);
		        Border border = new MatteBorder(1, 1, 1, 1, Color.BLACK);		      		     
		        add(label); add(cp); 		
		        cp.setBorder(border);		     
        	  setPreferredSize(new Dimension(330, 22));
        	//  setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        }
/*
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(xd, yd);
        } */
    }
    
    public class AboutTab extends JPanel {    	    		
       /**
		 * 
		 */
		private static final long serialVersionUID = -6545815388754933878L;
	// private Color defaultBackground;   		
        private JLabel label = new JLabel("Lorem ipsum");  
                
		public AboutTab() {     		
			  add(label);	            
        	  setPreferredSize(new Dimension(800, 600));        	  
        }
/*
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(xd, yd);
        } */
    }
    public class HeaderTab extends JPanel {    	    		
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//private Color defaultBackground;
        private JLabel label;  private int x; 
        public int getCol() {return this.x;}
                
		public HeaderTab(String str, int x) {     
			  label = new JLabel(str); add(label); this.x=x;
			  
			  addMouseListener(new MouseAdapter() {		            
		          public void mouseClicked(MouseEvent e) {             
		        	  int sOld = sortColumn;
		        	  sortColumn=x;
		              System.out.println("Clicked on column: " + x );
		        	  instance.firePropertyChange("Sort", (sOld==x?x-1:sOld), x);			  		             
		  
		          }     
		       });  
		
		}

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(90, 20);
        } 
    }
    public class Row {
    	private List<Cell> dataRow = new ArrayList<>(7);
    	public void add(Cell cell) {dataRow.add(cell);}
    	public Cell get(int i) {return (dataRow.size()<=i?null: dataRow.get(i));}
    	public void update() {dataRow.forEach(x -> x.setLabel());}
    	public void importData(DataRow dr) {
    		try {
    		   for (int i=0;i<7;i++) {dataRow.get(i).setLabel(dr.get(i).toString());}
    		} catch(Exception e) {}
    	}
    }
}
