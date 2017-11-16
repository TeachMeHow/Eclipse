package logBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public enum GroupType { ARRAY_LIST("List: array"), HASH_SET("Set: hash");
	String type;
	private GroupType(String typeName)
	{
		type = typeName;
	}
	@Override
	public String toString()
	{
		return type;
	}
	
	public static GroupType find(String typeName)
	{
		for(GroupType type: values())
		{
			// A little of scope play
			if (type.type.equals(typeName)) return type;
		}
		return null;
	}
	public Collection<LogEntry> createCollection(Collection<LogEntry> c) {
		switch (this)
		{
		case ARRAY_LIST: return new ArrayList<LogEntry>(c);
		case HASH_SET: return new HashSet<LogEntry>(c);
		}
		return null;
	}
	public Collection<LogEntry> createCollection() throws LogEntryException{
		switch (this)
		{
		case ARRAY_LIST: return new ArrayList<LogEntry>();
		case HASH_SET: return new HashSet<LogEntry>();
		default: throw new LogEntryException("Collection type not implemented");
		}
	}

}
