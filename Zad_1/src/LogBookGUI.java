/* Program: Aplikacja okienkowa wykonująca operacje na obiektach klasy LogBook i LogBookEntry. Na tym etapie posiada
 * możliwość tworzenia i odtwarzania plików o zadanych przez użytkownika nazwach
 * 
 * Aplikacja nie osiągneła jeszcze stanu, w którym da się z niej użytkować. GUI ma posiadać więcej funkji i co więcej, obecne mają
 * działać. Obecne funkcje programu wyróżniają się niskim prawdopodobieństwem pozytywnego wykonania.
 * 
 * Plik: LogBookGUI.java
 *
 * Autor: Szymon Cichy
 * Data: 2/11/2017
 */
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.function.Consumer;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import java.awt.FlowLayout;

import java.awt.GridLayout;


public class LogBookGUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	
	private static final String ABOUT = "A simple application that allows creation and edition of logbooks, diaries and other written records with timestamps\n"
			+ "Version: Very Alpha GUI\n"
			+ "Author: Szymon Cichy 235093";
	
	//Definicje zęści interfejsu użyte w programie
	private JPanel contentPane;
	private String filename;
	private LogBook current;
	private JMenuItem menuOpenExisting;
	private JMenuItem menuNew;
	private JMenuItem menuDelete;
	private JMenuItem menuAbout;
	private JTextField textOpen;
	private JButton btnOpen;
	private JPanel panel;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;

	/**
	 * Start programu
	 */
	public static void main(String[] args) throws LogBookException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogBookGUI frame = new LogBookGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Tworzenie ramki
	 */
	public LogBookGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		//menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("MENU");
		menuBar.add(mnNewMenu);
		menuOpenExisting = new JMenuItem("Open");
		mnNewMenu.add(menuOpenExisting);
		menuNew = new JMenuItem("New");
		mnNewMenu.add(menuNew);
		menuDelete = new JMenuItem("Delete");
		mnNewMenu.add(menuDelete);
		menuAbout = new JMenuItem("About LogBook");
		mnNewMenu.add(menuAbout);
		
		
		//Dodanie słuchaczy zdarzeń do menu
		menuOpenExisting.addActionListener(this);
		menuNew.addActionListener(this);
		menuDelete.addActionListener(this);
		menuAbout.addActionListener(this);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 40));
		
		panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(4, 1, 0, 10));
		//Action listener dla guzika New Entry
		btnNewButton = new JButton("New Entry");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {current.initializeNewEntry();
			} catch (LogBookException e) { 
				e.printStackTrace();	
			}
				try { PrintWriter out = new PrintWriter("src\\"+filename+".txt");
			Consumer<LogEntry> cLam;
			cLam=x->{x.writeToFile(out);
			out.write("\n\n\n");
			out.flush();
			};
			current.entryArray.forEach(cLam);}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
				
			}
		});
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("New button");
		panel.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("New button");
		panel.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("New button");
		panel.add(btnNewButton_3);
		

	}
	@Override
	public void actionPerformed(ActionEvent event){
		Object eventSource = event.getSource();
		if (eventSource == btnOpen) {
			try{
					filename = textOpen.getText();
					FileInputStream fileIn = new FileInputStream("src\\"+filename+".ser");
					ObjectInputStream objIn = new ObjectInputStream(fileIn);
					current = (LogBook) objIn.readObject();
					objIn.close();
					fileIn.close();
				} catch (IOException e ) {
					System.err.println(e);
					return;
				}catch (ClassNotFoundException e) {
					System.err.println(e);
					return;}
			}
			if (eventSource == menuOpenExisting) {
				JFrame frame = new JFrame("Open existing LogBook");
				filename = (String)JOptionPane.showInputDialog(frame, "File name:");
				try{
						FileInputStream fileIn = new FileInputStream("src\\"+filename+".ser");
						ObjectInputStream objIn = new ObjectInputStream(fileIn);
						current = (LogBook) objIn.readObject();
						objIn.close();
						fileIn.close();
					} catch (IOException e ) {
						System.err.println(e);
						return;
					}catch (ClassNotFoundException e) {
						System.err.println(e);
						return;}
			}
			if(eventSource == menuNew) {
				JFrame frame = new JFrame("Create new LogBook");
				filename = (String)JOptionPane.showInputDialog(frame, "File name:");
				if (filename == null || filename.equals(""))try {current = new LogBook("filename");
			} catch (LogBookException e) { 
				e.printStackTrace();	
			}
			}
			if(eventSource == menuDelete) {
				
			}
			if(eventSource == menuAbout) {
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame, ABOUT, "About LogBook", JOptionPane.PLAIN_MESSAGE);
			}
		}
}

