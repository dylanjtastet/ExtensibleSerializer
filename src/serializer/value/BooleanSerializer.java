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

@Tags({Comp533Tags.BOOLEAN_SERIALIZER})
public class BooleanSerializer extends AbstractValueSerializer {
	@Override
	public Object objectFromStringReader(StringReader in, Class<?> aClass, List<Object> visitedObjects){
		Object out = Boolean.parseBoolean(StringReaderUtil.readNextToken(in, DELIMITER));
		return out;
	}

	@Override
	public void objectToStringBuffer(StringBuffer anOutputBuffer, Object anObject, List<Object> visitedObjects) {
		if(anObject instanceof Boolean)
			anOutputBuffer.append(anObject.toString() + DELIMITER);
		else
			throw new RuntimeException("Non Boolean value "+anObject.getClass().toString()+"in boolean serializer");
	}

	@Override
	public void objectToByteBuffer(ByteBuffer anOutputBuffer, Object anObject, List<Object> visitedObjects) {
		if(anObject instanceof Boolean){
			anOutputBuffer.put((byte)(((boolean)anObject)?1:0));
		}
		else
		{
			throw new RuntimeException("Non boolean value "+anObject.getClass().toString()+"in boolean serializer");
		}
	}

	@Override
	public Object objectFromByteBuffer(ByteBuffer anInputBuffer, Class<?> aClass, List<Object> visitedObjects) {
		byte out = anInputBuffer.get();
		//anInputBuffer.flip();
		return out == 1? true:false;
	}
}
