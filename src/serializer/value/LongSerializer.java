package serializer.value;

import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.List;

import util.StringReaderUtil;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationFinished;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationInitiated;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationFinished;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationInitiated;

@Tags({Comp533Tags.LONG_SERIALIZER})
public class LongSerializer extends AbstractValueSerializer {

	@Override
	public Object objectFromStringReader(StringReader in, Class<?> aClass, List<Object> visitedObjects){
		Object out = Long.parseLong(StringReaderUtil.readNextToken(in, DELIMITER));
		return out;
	}

	@Override
	public void objectToStringBuffer(StringBuffer anOutputBuffer, Object anObject, List<Object> visitedObjects) {
		if(anObject instanceof Long)
			anOutputBuffer.append(anObject.toString() + DELIMITER);
		else
			throw new RuntimeException("Non Long value "+anObject.getClass().toString()+"in Long serializer");
	}

	@Override
	public void objectToByteBuffer(ByteBuffer anOutputBuffer, Object anObject, List<Object> visitedObjects) {
		if(anObject instanceof Long){
			anOutputBuffer.putLong((Long)anObject);
		}
		else
		{
			throw new RuntimeException("Non Long value "+anObject.getClass().toString()+"in Long serializer");
		}
	}

	@Override
	public Object objectFromByteBuffer(ByteBuffer anInputBuffer, Class<?> aClass, List<Object> visitedObjects) {
		Object out = anInputBuffer.getLong();
		//anInputBuffer.flip();
		return out;
	}

}
