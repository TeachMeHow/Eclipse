package logBook;
/*
 * Program: Klasa reprezentująca wpis w dzienniku. W moim programie jest używana jako część klasy LogBook. Przechowuje informacje o temacie,
 * 	treści i dacie wpisu. Posiada dwa konstruktory oraz metody użyteczne przy tworzeniu aplikacji konsolowych displayEntry() i toString()
 * Plik: LogBookEntry.java
 * Autor: Szymon Cichy
 * Data: 2/11/2017
 */
import java.io.PrintWriter;
import java.time.*;
import java.util.Iterator;
import java.util.Objects;

class LogEntryException extends Exception{
	private static final long serialVersionUID = 1L;
	public LogEntryException(String message) {
		super(message);
	}	
}

public class LogEntry implements java.io.Serializable, Comparable<LogEntry>{
	//TODO: time/date, 
	private String subject;
	private String message;
	LocalDateTime time;
	private static final long serialVersionUID = 1L;
	
	//private String Location;
	public LogEntry() {
		time = LocalDateTime.now();
	};
	
	public LogEntry(String sub) {	
		this.subject = sub;
		time = LocalDateTime.now();
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
	public void setTime(LocalDateTime newTime) {
		this.time = newTime;
	}
	public LocalDateTime getTime() {
		return this.time;
	}
	public void writeToFile(PrintWriter out) {
		out.write(time.toString()+'\n');
		out.write("Subject: "+subject+'\n');
		out.write("Message: "+message+'\n');	
	};
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("");
		String str = new String("");
		buffer.append(this.priority).append("\n").append(this.time.toString()).append("\n>Subject: ").
		append(this.subject).append("\n>Message: ").append(this.message).append("\n");
		str = buffer.toString();
		return str;
	}
	@Override
	public boolean equals(Object other)
	{
		if(other == this) return true;
		else if (!(other instanceof LogEntry)) return false;
		LogEntry same = (LogEntry) other;
		return (same.message == this.message) &&
				(same.subject == this.subject);
	}
	@Override
	public int hashCode()
	{
		return Objects.hash(message, subject);
	}
	private String priority;

	/**
	 * Compare dates
	 * -1 for older than other
	 * 1 for younger than other
	 * 0 is for same but not likely to occur
	 */
	@Override
	public int compareTo(LogEntry o) {
		return this.time.compareTo(o.getTime());
	}
}
