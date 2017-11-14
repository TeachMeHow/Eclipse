/*
 * Program: Typ wyliczeniowy, może być użyty do określania ważności wpisów w dzienniku, co może być przydatne np. przy ich sortowaniu,
 * wyświetlaniu czy szukaniu.
 * 
 * 
 * Plik: Importance.java
 * Autor: Szymon Cichy
 * Data: 2/11/2017
 */
public enum Levels {
	UNKNOWN("??????????"), NONE("not important"), LOW("some doodles"), MEDIUM_LOW ("don't mind"), MEDIUM("usual"), MEDIUM_HIGH("take a look"), 
	HIGH("Important!"), VERY_HIGH("LOOK AT ME");
	private final String message;
	Levels(String str)
	{
		this.message = str;
	}
	public String warningMessage() {return message;}
	
}
