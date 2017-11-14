package LogBook;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JLayeredPane;
import javax.swing.JInternalFrame;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;

public class NewLogBookGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewLogBookGUI window = new NewLogBookGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NewLogBookGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNew = new JMenu("new");
		menuBar.add(mnNew);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mnNew.add(mntmNewMenuItem);
		
		JMenu mnTwo = new JMenu("two");
		menuBar.add(mnTwo);
		
		JRadioButtonMenuItem rdbtnmntmOne = new JRadioButtonMenuItem("one");
		mnTwo.add(rdbtnmntmOne);
		
		JMenuItem mntmTwo = new JMenuItem("two");
		mnTwo.add(mntmTwo);
		
		JRadioButtonMenuItem radioButtonMenuItem = new JRadioButtonMenuItem("New radio item");
		mnTwo.add(radioButtonMenuItem);
		frame.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel lblAb = new JLabel("Date:");
		lblAb.setAlignmentX(0.8f);
		panel.add(lblAb);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);
	}
}
