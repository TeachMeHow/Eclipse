package logBook;
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
import java.util.Collection;
import java.util.Iterator;
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
public class LogBook implements java.io.Serializable, Iterable<LogEntry> {
	//konstruktor z nazwą pliku - wywołuje metodę tworzącą plik na dysku
	public LogBook(String name) throws LogBookException, LogEntryException {
    	initializeNewFile(name);
    	//Domyślnie ArrayList
    	type = GroupType.ARRAY_LIST;
    	collection = this.type.createCollection();
    	LogBookCount++;
    }
	//konstruktor bezargumentowy - sam tworzy nazwę
    public LogBook() throws LogBookException, LogEntryException
    {
    	initializeNewFile("LogBook_"+String.valueOf(++LogBookCount));
    	//Domyślnie ArrayList
    	type = GroupType.ARRAY_LIST;
    	collection = this.type.createCollection();
    	LogBookCount++;
    }
    //konstruktor
    public LogBook(String name, GroupType newType) throws LogBookException, LogEntryException {
    	initializeNewFile(name);
    	if (type == null ) {
    		throw new LogEntryException("Incorrect collection type");
    	}
    	type = newType;
    	collection = this.type.createCollection();
    	LogBookCount++;
    }
    public LogBook(String name, GroupType newType, Collection<LogEntry> c) throws LogBookException, LogEntryException {
    	initializeNewFile(name);
    	if (type == null ) {
    		throw new LogEntryException("Incorrect collection type");
    	}
    	type = newType;
    	collection = this.type.createCollection(c);
    	LogBookCount++;
    }
    
//Maksymalna liczba wpisów do pojedynczego dziennika
	private static final int MAX_ENTRIES = 50;
	//tablica przechowująca wpisy - dane
	private ArrayList<LogEntry> entryArray;
	private Collection<LogEntry> collection;
	private GroupType type;
//zmienna przechowuje wspólną nazwę plików, które odnoszą się czy też są dziennikiem. Zmienia się rozszerzenie.
	private String filename;
private static final long serialVersionUID = 1L;
//Liczba stworzonych dzienników
private static int LogBookCount = 0;
//pętla menu - do użytku w aplikacji konsolowej  
public void runMenu() throws LogBookException{
	//jak zamknę System.in to nie mogę już otworzyć, stąd @SupressWarnings("resource")
	//no longer exists
	Scanner keyboard = new Scanner(System.in)	;
	//TODO keyboard from UI
	int choice = -1;
	//pętla menu - min. 1 wykonanie
		do {
			System.out.println("MENU\nCurrently "+collection.size()+countEntries()
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
			case 0: 
			try { @SuppressWarnings("resource")
			PrintWriter out = new PrintWriter("src\\"+filename+".txt");
			Iterator<LogEntry> it = collection.iterator();
			
			while (it.hasNext())
			{
				out.print(it.next().toString() + "\n\n");
				out.flush();
			}
			}
			catch (FileNotFoundException e) {
				throw new LogBookException(filename+ ".txt not found");
			}
			;
			
			
			Serialize();
			break;
			//New Entry
			case 1: 
			initializeNewEntry(keyboard);
			
			break;
			// View previous
			case 2:
				Iterator<LogEntry> it = collection.iterator();
				while(it.hasNext()) {
					System.out.println(it.next().toString());
				}
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
	//tworzy wpis i dodaje go do bazy danych oraz pyta o dane
	/**
	 * @param input Scanner object
	 * @throws LogBookException
	 */
	//TODO remake !!!
	public void initializeNewEntry(Scanner keyboard) throws LogBookException{
		if(collection.size()<MAX_ENTRIES) {
			LogEntry nu = new LogEntry();
			int choice = -1;
			System.out.println("Subject: ");
			nu.setSubject(keyboard.nextLine());
			System.out.println("Message: ");
			nu.setMessage(keyboard.nextLine());
			System.out.println("On a scale from 1 to 7, how important is it?");
			try {
				choice = Integer.parseInt(keyboard.nextLine());
			} catch(NumberFormatException e) {
				e.printStackTrace();}
			nu.setPriority(choice);
		collection.add(nu);
		}
		else System.out.println("LogBook is full!");
	}
	//metoda pomocna do śledzenia ilości dzienników, powinna być wywołana przy usuwaniu plików z dziennikiem
	public static void decreaseLogBookCount() {
		LogBookCount--;
	};
    //nic ważnego
	//
	private String countEntries () {
	if (this.collection.size() != 1) return " entries";
	else return " entry"; }
	@Override
	public Iterator<LogEntry> iterator() {
		return collection.iterator();
	}
	public boolean add(LogEntry e) {
		return collection.add(e);
	}
	public boolean addAll(Collection<? extends LogEntry> c) {
		return collection.addAll(c);
	}
	public void clear() {
		// TODO Auto-generated method stub		
	}
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isEmpty() {
		if (collection.size() == 0) return true;
		else return false;
	}
	public boolean remove(Object o) {
		return collection.remove(o);
	}
	public boolean removeAll(Collection<?> c) {
		return collection.removeAll(c);
	}
	public int size() {
		return collection.size();
	}
}