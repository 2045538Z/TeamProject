//class displays the allocated
import java.awt.Font;
import java.awt.*;
import javax.swing.*;

public class MatchReportFrame extends JFrame{
	private JTextArea display;
	private String alloc;
	public MatchReportFrame(int w, String a){
		int week = w;
		alloc = a;
		
		JFrame frame = new JFrame();
		frame.setSize(500, 300);
		frame.setTitle("Allocated referees for Week "+ week);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  	
		frame.setVisible(true);
		
		display  = new JTextArea(6,40);
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		frame.add(display);
		display.setText(alloc);
		
	}
	
}
