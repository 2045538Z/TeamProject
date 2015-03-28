import javax.swing.*;
import java.awt.*;

//class creates frame that will display a bar chart showing each ref's allocations sorted by id.
public class BarChartFrame{
	public static Referee[] RefArray;
	
	public BarChartFrame(Referee[] refs){
		RefArray = refs;
		int refLength = RefArray.length;
		JFrame frame = new JFrame();

	      final int FRAME_WIDTH = 600; // could be too high, need to decide what's a reasonable max number of allocations currently approx 40 will be visible
	      final int FRAME_HEIGHT = 480;

	      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
	      frame.setTitle("Referee Allocations");
	      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	      
	      RectangleComponent component = new RectangleComponent();
	      frame.add(component);
	      frame.setVisible(true);
	}
	
	public class RectangleComponent extends JComponent
	{  
		//
		private static final int RECT_WIDTH = 30;
		private static final int START_X = 50;
		private static final int START_Y = 400;
		private static final int BLANK_SPACE = 10;
		private static final int BELOW_BARS = 420;

	   //updates the display
	   public void paintComponent(Graphics g)
	   {  
	      int rectHeight;
	      // Recover Graphics2D
	      Graphics2D g2 = (Graphics2D) g;
	      g2.setColor(Color.lightGray);
	      g2.fillRect(0, 0, this.getWidth(), this.getHeight());
	      int x=START_X;
	      int y=START_Y;
	      

	      //bars
	      g2.setColor(Color.blue);
	      for (int i = 0; i < RefArray.length; i++){
	    	 // if(RefArray[i]!=null){
	    		  rectHeight = RefArray[i].getAllocations()*10;
	    		  g2.fillRect(x,y-rectHeight, RECT_WIDTH, rectHeight);
	    		  x += RECT_WIDTH + BLANK_SPACE;
	    		  
	    	 // }
	      }

	      //text locations - id below the bars, number of allocations will be above the bars
	      x = START_X;            
	      y = BELOW_BARS;             
	      g2.setColor(Color.black);
	      g2.setFont(new Font("Monospaced", Font.BOLD, 18));
	      int alloc;
	      for (int i = 0; i < RefArray.length; i++){
	    	  if(RefArray[i]!=null){
	    		  alloc = RefArray[i].getAllocations();
	    		  g2.drawString("" +alloc, x+4,START_Y - (alloc*10)-10);
	    		  g2.drawString(RefArray[i].getID(), x, y);
	    		  x += RECT_WIDTH + BLANK_SPACE; 
	    	  }
	      }
	   }
	}

}