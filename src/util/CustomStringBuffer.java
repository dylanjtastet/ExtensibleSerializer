package util;

public class CustomStringBuffer{
	StringBuffer buf;
	int position;
	public CustomStringBuffer(int size){
		buf = new StringBuffer(size);
		position = 0;
	}
	
	public void append(Object value){
		buf.append(value);
	}
	
	public int length(){
		return buf.length();
	}
	
	public String read(){
		return read(1);
	}
	
	public String read(int amount){
		int newPos = (position + amount > buf.length())? buf.length() : position + amount;
		String out = buf.substring(position, newPos);
		position = newPos;
		return out;
	}
}
