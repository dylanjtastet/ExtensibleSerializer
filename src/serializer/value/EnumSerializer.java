package serializer.value;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.util.List;

import registry.SerializerRegistry;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationFinished;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationInitiated;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationFinished;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationInitiated;

@Tags({Comp533Tags.ENUM_SERIALIZER})
public class EnumSerializer implements ValueSerializer {

	@Override
	public void objectToBuffer(Object anOutputBuffer, Object anObject, List<Object> visitedObjects)
			throws NotSerializableException {
		ExtensibleValueSerializationInitiated.newCase(this, anObject, anOutputBuffer);
		SerializerRegistry.getValueSerializer(String.class).objectToBuffer(anOutputBuffer, anObject.toString(), visitedObjects);
		ExtensibleValueSerializationFinished.newCase(this, anObject, anOutputBuffer, visitedObjects);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object objectFromBuffer(Object anInputBuffer, Class<?> aClass, List<Object> retrievedObjects)
			throws StreamCorruptedException{
		ExtensibleBufferDeserializationInitiated.newCase(this, null, anInputBuffer, aClass);
		String valName = (String) SerializerRegistry.getValueSerializer(String.class).objectFromBuffer(anInputBuffer, aClass, retrievedObjects);
		try{
			Object out = Enum.valueOf((Class<Enum>)aClass, valName);
			ExtensibleBufferDeserializationFinished.newCase(this, null, anInputBuffer, out, retrievedObjects);
			return out;
		}
		catch(Exception e){
			throw new StreamCorruptedException();
		}
	}

}
