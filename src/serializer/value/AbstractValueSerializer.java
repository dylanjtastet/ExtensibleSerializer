package serializer.value;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.List;

import util.CustomStringBuffer;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationFinished;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationInitiated;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationFinished;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationInitiated;

public abstract class AbstractValueSerializer implements ValueSerializer {

	@Override
	public void objectToBuffer(Object anOutputBuffer, Object anObject, List<Object> visitedObjects)
			throws NotSerializableException {
		ExtensibleValueSerializationInitiated.newCase(this, anObject, anOutputBuffer);
		if(anOutputBuffer instanceof ByteBuffer){
			objectToByteBuffer((ByteBuffer)anOutputBuffer, anObject, visitedObjects);
		}
		else{
			objectToStringBuffer((StringBuffer) anOutputBuffer, anObject, visitedObjects);
		}
		ExtensibleValueSerializationFinished.newCase(this, anObject, anOutputBuffer, visitedObjects);
	}

	@Override
	public Object objectFromBuffer(Object anInputBuffer, Class<?> aClass, List<Object> visitedObjects)
			throws StreamCorruptedException{
		ExtensibleBufferDeserializationInitiated.newCase(this, null, anInputBuffer, aClass);
		Object out = null;
		if(anInputBuffer instanceof ByteBuffer){
			out = objectFromByteBuffer((ByteBuffer) anInputBuffer, aClass, visitedObjects);
		}
		else{
			out = objectFromStringReader((StringReader) anInputBuffer, aClass, visitedObjects);
		}
		ExtensibleBufferDeserializationFinished.newCase(this, null, anInputBuffer, out, visitedObjects);
		return out;
	}
	
	public abstract void objectToStringBuffer(StringBuffer anOutputBuffer, Object anObject, List<Object> visitedObjects);
	public abstract Object objectFromStringReader(StringReader anInputBuffer, Class<?> aClass, List<Object> retreivedObjects);
	public abstract void objectToByteBuffer(ByteBuffer anOutputBuffer, Object anObject, List<Object> retreivedObjects);
	public abstract Object objectFromByteBuffer(ByteBuffer anInputBuffer, Class<?> aClass, List<Object> retreivedObjects);
	// !!!! Determine when to flip bytebuffers !!!!!
}
