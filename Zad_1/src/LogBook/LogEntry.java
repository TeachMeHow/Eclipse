package LogBook;
/*
 * Program: Klasa reprezentująca wpis w dzienniku. W moim programie jest używana jako część klasy LogBook. Przechowuje informacje o temacie,
 * 	treści i dacie wpisu. Posiada dwa konstruktory oraz metody użyteczne przy tworzeniu aplikacji konsolowych displayEntry() i toString()
 * Plik: LogBookEntry.java
 * Autor: Szymon Cichy
 * Data: 2/11/2017
 */
import java.io.PrintWriter;
import java.time.*;

public class LogEntry implements java.io.Serializable{
	//TODO: time/date, 
	private String subject;
	private String message;
	private String time;
	private static final long serialVersionUID = 1L;
	
	//private String Location;
	public LogEntry() {
		time = LocalDateTime.now().toString();
	};
	
	public LogEntry(String sub) {	
		this.subject = sub;
		time = LocalDateTime.now().toString();
	};
	//Gettery i settery
	
	public void setSubject(String sub) {
		this.subject = sub;
	}
	public String getSubject() {
		return this.subject;
	}
	public void setMessage(String mes) {
		this.message = mes;
	}
	public String getMessage() {
		return this.message;
	}
	//from 1 to 7
	public void setPriority(int scale)
	{
		switch(scale) {
		case 1: this.priority = Levels.NONE.warningMessage();
		break;
		case 2: this.priority = Levels.LOW.warningMessage();
		break;
		case 3: this.priority = Levels.MEDIUM_LOW.warningMessage();
		break;
		case 4: this.priority = Levels.MEDIUM.warningMessage();
		break;
		case 5: this.priority = Levels.MEDIUM_HIGH.warningMessage();
		break;
		case 6: this.priority = Levels.HIGH.warningMessage();
		break;
		case 7: this.priority = Levels.VERY_HIGH.warningMessage();
		break;
		default: this.priority = Levels.UNKNOWN.warningMessage();
		}
		
	}
	public void writeToFile(PrintWriter out) {
		out.write(time+'\n');
		out.write("Subject: "+subject+'\n');
		out.write("Message: "+message+'\n');	
	};
	//Do użycia w aplikacji konsolowej
	public void displayEntry() {
		System.out.print(this.toString());
	};
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("");
		String str = new String("");
		buffer.append(this.priority).append("\n").append(this.time).append("\n>Subject: ").
		append(this.subject).append("\n>Message: ").append(this.message).append("\n");
		str = buffer.toString();
		return str;
	}
	private String priority;
}
