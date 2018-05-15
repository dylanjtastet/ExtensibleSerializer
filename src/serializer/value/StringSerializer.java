package serializer.value;

import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.List;

import util.StringReaderUtil;
import util.annotations.Comp533Tags;
import util.annotations.Tags;

@Tags({Comp533Tags.STRING_SERIALIZER})
public class StringSerializer extends AbstractValueSerializer {

	@Override
	public void objectToStringBuffer(StringBuffer anOutputBuffer, Object anObject, List<Object> visitedObjects) {
		if(anObject instanceof String){
			anOutputBuffer.append((String)anObject + DELIMITER);
		}
		else{
			throw new RuntimeException("Non String value in String serializer");
		}
	}

	@Override
	public Object objectFromStringReader(StringReader anInputBuffer, Class<?> aClass, List<Object> retreivedObjects) {
		String out = StringReaderUtil.readNextToken(anInputBuffer, DELIMITER);
		return out;
	}

	@Override
	public void objectToByteBuffer(ByteBuffer anOutputBuffer, Object anObject, List<Object> visitedObjects) {
		char[] chars = ((String) anObject).toCharArray();
		for(char c : chars){
			anOutputBuffer.putChar(c);
		}
		anOutputBuffer.putChar(DELIMITER);
	}

	@Override
	public Object objectFromByteBuffer(ByteBuffer anInputBuffer, Class<?> aClass, List<Object> retreivedObjects) {
		String out = StringReaderUtil.readNextTokenBB(anInputBuffer, DELIMITER);
		return out;
	}

}
