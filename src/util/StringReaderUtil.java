package util;

import java.io.IOException;
import java.io.StringReader;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class StringReaderUtil {
	public static String readNextToken(StringReader in, char delim){
		StringBuilder token = new StringBuilder();
		int next;
		try {
			next = in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		while(next != -1 && next != delim){
			token.append((char)next);
			try {
				next = in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
		return token.toString();
	}
	
	public static String readNextTokenBB(ByteBuffer in, char delim){
		char next;
		StringBuilder token = new StringBuilder();
		try{
			next = in.getChar();
		}
		catch(BufferUnderflowException e){
			return "";
		}
		while(next != delim){
			token.append(next);
			try{
				next = in.getChar();
			}
			catch(BufferUnderflowException e){
				break;
			}
		}
		return token.toString();
	}
}
