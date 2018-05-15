package serializer.value;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.List;

import util.CustomStringBuffer;
import util.StringReaderUtil;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationFinished;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationInitiated;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationFinished;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationInitiated;

@Tags({Comp533Tags.INTEGER_SERIALIZER})
public class IntegerSerializer extends  AbstractValueSerializer {
	
	@Override
	public Object objectFromStringReader(StringReader in, Class<?> aClass, List<Object> visitedObjects){
		Object out = Integer.parseInt(StringReaderUtil.readNextToken(in, DELIMITER));
		return out;
	}

	@Override
	public void objectToStringBuffer(StringBuffer anOutputBuffer, Object anObject, List<Object> visitedObjects) {
		if(anObject instanceof Integer)
			anOutputBuffer.append(anObject.toString() + DELIMITER);
		else
			throw new RuntimeException("Non integer value "+anObject.getClass().toString()+"in INTEGER serializer");
	}

	@Override
	public void objectToByteBuffer(ByteBuffer anOutputBuffer, Object anObject, List<Object> visitedObjects) {
		if(anObject instanceof Integer){
			anOutputBuffer.putInt((int)anObject);
		}
		else
		{
			throw new RuntimeException("Non integer value "+anObject.getClass().toString()+"in INTEGER serializer");
		}
	}

	@Override
	public Object objectFromByteBuffer(ByteBuffer anInputBuffer, Class<?> aClass, List<Object> visitedObjects) {
		Object out = anInputBuffer.getInt();
		//anInputBuffer.flip();
		return out;
	}

}
