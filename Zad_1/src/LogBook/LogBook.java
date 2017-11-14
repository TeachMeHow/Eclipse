/*
 * Program: Klasa posiada zmienne i typy danych przechowujące wpisy do dziennika i informacje o nich. Posiada metody pozwalające na
 * stworzenie plików dziennika oraz ich edycję (usuwaniem zajmuje się klasa LogBookConsoleApp). Metody tworzą wpisy i przechowują je w 
 * ArrayList, i zapisują je do pliku używając serializacji. Posiada dwa konstruktory, jeden tworzący dziennik o zadanej nazwie i drugi, który
 * nazwę tworzy automatycznie. Klasa posiada swoje menu i metody pomocne przy implementacji klasy w aplikacji konsolowej, np. swoje własne
 * menu.
 * Plik: LogBook.java
 * Autor: Szymon Cichy
 * Data: 2/11/2017
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
//Do łapania błędów klas LogEntry, LogBook i LogBookConsoleApp
class LogBookException extends Exception{
	private static final long serialVersionUID = 1L;
	public LogBookException(String message) {
		super(message);
	}	
}
//deklaracja klasy
public class LogBook implements java.io.Serializable {
	//konstruktor z nazwą pliku - wywołuje metodę tworzącą plik na dysku
	public LogBook(String name) throws LogBookException {
    	initializeNewFile(name);
    	entryArray = new ArrayList<>();
    	LogBookCount++;
    }
	//konstruktor bezargumentowy - sam tworzy nazwę
    public LogBook() throws LogBookException
    {
    	initializeNewFile("LogBook_"+String.valueOf(++LogBookCount));
    	entryArray = new ArrayList<>();
    	
    }
//pętla menu - do użytku w aplikacji konsolowej  
public void runMenu() throws LogBookException{
	//jak zamknę System.in to nie mogę już otworzyć, stąd @
	@SuppressWarnings("resource")
	Scanner keyboard = new Scanner(System.in)	;
	int choice = -1;
	//pętla menu - min. 1 wykonanie
		do {
			System.out.println("MENU\nCurrently "+entryArray.size()+countEntries()
					+ "\n1. New entry\n"
					+ "2. View previous entries\n"
					+ "3. Delete last entry\n"
					+ "0. Save & Exit");
			 try {
				choice = Integer.parseInt(keyboard.nextLine());
			} catch(NumberFormatException e) {
				e.printStackTrace();
			}
			//opcje menu
			switch (choice)
			{
			//Save & Exit - write every entry to file
			case 0: try { PrintWriter out = new PrintWriter("src\\"+filename+".txt");
			Consumer<LogEntry> cLam;
			cLam=x->{x.writeToFile(out);
			out.write("\n\n\n");
			out.flush();
			};
			entryArray.forEach(cLam);}
			catch (FileNotFoundException e) {
				throw new LogBookException(filename+ ".txt not found");
			};
			
			
			Serialize();
			break;
			//New Entry
			case 1: 
			initializeNewEntry();
			System.out.println("On a scale from 1 to 7, how important is it?");
			try {
				choice = Integer.parseInt(keyboard.nextLine());
			} catch(NumberFormatException e) {
				e.printStackTrace();}
				entryArray.get(entryArray.size()-1).setPriority(choice);
			break;
			// View previous
			case 2:
				Consumer<LogEntry> cLam;
				cLam=x->x.displayEntry();
				entryArray.forEach(cLam);
				; break;
			// Delete last
			case 3: entryArray.remove(entryArray.size()-1);
				; break;
			// Kiedy użytkownik wybierze opcję nie będącą w menu program wyrzuci błąd
			default:
				System.out.println("Choice out of range");
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					throw new LogBookException("Waiting...");
				}
			break;
			}
			
		}while (choice != 0);	
	}//koniec menu

//nowy plik na dysku - UWAGA nadpisuje
//TODO flow control
 	private void initializeNewFile(String name) throws LogBookException{
 		this.filename=name;
 		PrintWriter out;
 		try {
    		out = new PrintWriter("src\\"+name+".txt");
    		out.close();
    	} catch (FileNotFoundException e) {
    		throw new LogBookException("File not found.");
    		}
	};
	//zapis całego dziennika do pliku binarnego - serializacja
	public void Serialize() throws LogBookException {
		try{ FileOutputStream fileOut = new FileOutputStream("src//"+filename+".ser");
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		objOut.writeObject(this);
		objOut.close();
		} catch(FileNotFoundException e) {
			StringBuffer buffer = new StringBuffer(filename);
			buffer.append(".ser not found");
			String message = buffer.toString();
			throw new LogBookException(message);
		} catch(IOException e) {
			System.err.println(e);
		}
	};
	//Gettery i settery
	
	//zwraca wpis w postaci tekstowej
	public String getEntry(int i) {
		if(i>-1 && i< entryArray.size())
			return entryArray.get(i).toString();
		else return "No such entry.";
		}
	//tworzy wpis i dodaje go do bazy danych oraz pyta o dane
	public void initializeNewEntry() throws LogBookException{
		if(entryArray.size()<MAX_ENTRIES) {
		entryArray.add(new LogEntry());
		int pos = entryArray.size()-1;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Subject: ");
		entryArray.get(pos).setSubject(keyboard.nextLine());
		System.out.println("Message: ");
		entryArray.get(pos).setMessage(keyboard.nextLine());
		}
		else System.out.println("LogBook is full!");
	}
	//nic ważnego
	private String countEntries () {
	if (this.entryArray.size() != 1) return " entries";
	else return " entry"; }
	//metoda pomocna do śledzenia ilości dzienników, powinna być wywołana przy usuwaniu plików z dziennikiem
	public static void decreaseLogBookCount() {
		LogBookCount--;
	};
    //tablica przechowująca wpisy - dane
    public ArrayList<LogEntry> entryArray;
    //Maksymalna liczba wpisów do pojedynczego dziennika
    private static final int MAX_ENTRIES = 50;
    //Liczba stworzonych dzienników
    private static int LogBookCount = 0;
    private static final long serialVersionUID = 1L;
    //zmienna przechowuje wspólną nazwę plików, które odnoszą się czy też są dziennikiem. Zmienia się rozszerzenie.
    private String filename;
}