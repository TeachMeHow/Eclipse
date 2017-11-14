package LogBook;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LogBookHardGUI {
	private static final int RIGHT = 4;
	private JFrame mainFrame;
	private JButton button1;
	private JLabel label1;
	LogBookHardGUI() {
		startGUI();
	}
	public static void main(String[] args) {
		LogBookHardGUI testGUI = new LogBookHardGUI();
	}
	
	


public void startGUI()
{
	mainFrame = new JFrame("LogBook");
	mainFrame.setSize(400, 400);
	mainFrame.setLayout(new GridLayout(3,0));
	button1 = new JButton("blonk");	
	label1 = new JLabel("",RIGHT);
	
	mainFrame.getContentPane().add(new JPanel());
	
	button1.addActionListener(new MyListener());
	
	mainFrame.setVisible(true);
}

class MyListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		label1.setText("CLICK");
		
	}
	
}

}