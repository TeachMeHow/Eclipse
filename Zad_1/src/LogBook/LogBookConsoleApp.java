package LogBook;
/*LogBookTest to konsolowy interfejs użytkownika. Odpowiada za tworzenie dzienników bądz logów. Odpowiada za tworzenie, usuwanie czy
edycję plików. Tworzy obiekty klasy LogBook, które pózniej same odpowiadają za działania na swojej zawartości używając własnego menu.
W trakcie trwania programu można stworzyć i usunąć wiele dzienników

Program przy użyciu tego interfejsu powinien w (prawie) całości działać.

Plik: LogBookConsoleApp.java
Autor: Szymon Cichy
Data: 2/11/2017
*/
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.*;
//deklaracja klasy
public class LogBookConsoleApp {
	//stałe tekstowe - do menu i o programie
	private static final String MENU = "MENU\n"
			+ "1. Open existing LogBook\n"
			+ "2. New LogBook\n"
			+ "3. Delete Logbook\n"
			+ "4. About \"LogBook\"\n"
			+ "0. Save & Exit";
	private static final String ABOUT = "About LogBook\n"
			+ "A simple application that allows creation and edition of logbooks, diaries and other written records with timestamps\n"
			+ "Version: Almost Beta TUI\n"
			+ "Author: Szymon Cichy 235093";
	//menu() to główna metoda programu - działa na zasadzie pętli, wykonuje funkcje dopóki użytkownik nie powie inaczej.
	//odpowiada za tworzenie i edycję indywidualnych dzienników/logów. Każdy dziennik to osobny plik, który ta (LogBookTest) aplikacja
	//otwiera, zmienia czy usuwa. Za działania na zawartości dzienników odpowiada klasa LogBook
	private void runMenu() throws LogBookException{
	int choice = -1;
	String filename;
	@SuppressWarnings("resource")
	Scanner keyboard = new Scanner(System.in);
	//początek menu - pętla
	do {
		System.out.println(MENU);
		try {choice = Integer.parseInt(keyboard.nextLine());}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
		switch (choice)
		{
		//Save & Exit
		case 0: break;
		//Open existing LogBook
		case 1: 
			/*Kod ma wyświetlać nazwy wszytkich plików z rozszerzeniem .ser do deserializacji, jednak jeszcze nie udało mi się 
			 * tego dobrze zrealizować
			 */
			//TODO needs some work, doesn't list files
			System.out.println("Currently available LogBooks:\n");
			File f = new File("src\\");
			File[] paths = f.listFiles();
			FileFilter filter = new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					String path = (f.getAbsolutePath().toLowerCase());
					if (path.endsWith(".ser")) return true;
					else return false;
				};
			};
			paths = f.listFiles(filter);
			for(File path:paths)
			{
				System.out.println(path);
			}
			//pytanie o nazwę dziennika
			System.out.println("Name of the LogBook: ");
			filename = keyboard.nextLine();
			LogBook existingLog = null;
			//deserializacja i wczytanie obiektu z pliku .ser
			try {
				FileInputStream fileIn = new FileInputStream("src\\"+filename+".ser");
				ObjectInputStream objIn = new ObjectInputStream(fileIn);
				existingLog = (LogBook) objIn.readObject();
				objIn.close();
				fileIn.close();
			} catch (IOException e ) {
				System.err.println(e);
				return;
			} catch (ClassNotFoundException e) {
				System.err.println(e);
				return;
			}
			//start własnego menu dziennika
			existingLog.runMenu();
			break;
		//New LogBook
		//Tworzy nowy dziennik
		case 2: 
			System.out.println("Name your LogBook:");
			filename = keyboard.nextLine();
			if (filename == null || filename.equals("")) {LogBook howtonameyou = new LogBook();
			howtonameyou.runMenu();}
			else {LogBook newLog = new LogBook(filename);
			newLog.runMenu();};
			break;
		//Delete LogBook
		//Usuwa plik z rozszerzeniem .txt i .ser
		case 3: System.out.println("Name the LogBook you want to delete: ");
		filename = keyboard.nextLine();
		Path pathtxt = FileSystems.getDefault().getPath("src\\", filename + ".txt");
		Path pathser = FileSystems.getDefault().getPath("src\\", filename + ".ser");
		//TODO ifexists?
		try {
		Files.deleteIfExists(pathtxt);
		Files.deleteIfExists(pathser);
		System.out.println(filename +" deleted.");
		LogBook.decreaseLogBookCount();
		}
		//żeby przypadkiem nie usunęło folderu
		catch (DirectoryNotEmptyException e) {
			throw new LogBookException("Directory not empty.");
		}catch (IOException e) {
				System.err.println(e);
		}
		break;
		//About LogBook
		case 4: System.out.println(ABOUT) ; break;
		// Kiedy użytkownik wybierze opcję nie będącą w menu program wyrzuci błąd
		default:
			System.out.println("Choice out of range");
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				throw new LogBookException("Thread has been interrupted.");
			}
		break;
		}
		}while (choice != 0);}
	//koniec menu
		
	//main
	public static void main(String args[]) throws LogBookException{  
		Scanner keyboard = new Scanner(System.in);
		//tworzy instancję programu i otwiera pętlę
		LogBookConsoleApp app = new LogBookConsoleApp();
		app.runMenu();
		keyboard.close();
		
			
		
	}
}
