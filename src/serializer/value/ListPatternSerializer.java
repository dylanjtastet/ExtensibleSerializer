package serializer.value;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.util.List;

import registry.SerializerRegistry;
import serializer.dispatching.DispatchingSerializer;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.misc.RemoteReflectionUtility;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationFinished;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationInitiated;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationFinished;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationInitiated;

@Tags({Comp533Tags.LIST_PATTERN_SERIALIZER})
public class ListPatternSerializer implements ValueSerializer {

	@Override
	public void objectToBuffer(Object anOutputBuffer, Object anObject, List<Object> visitedObjects)
			throws NotSerializableException {
		ExtensibleValueSerializationInitiated.newCase(this, anObject, anOutputBuffer);
		visitedObjects.add(anObject);
		DispatchingSerializer dispatchingSerializer = SerializerRegistry.getDispatchingSerializer();
		int size = RemoteReflectionUtility.listSize(anObject);
		SerializerRegistry.getValueSerializer(Integer.class).objectToBuffer(anOutputBuffer, size, visitedObjects);
		for(int i=0; i<size; i++){
			dispatchingSerializer.objectToBuffer(anOutputBuffer, RemoteReflectionUtility.listGet(anObject, i), visitedObjects);
		}
		ExtensibleValueSerializationFinished.newCase(this, anObject, anOutputBuffer, visitedObjects);
	}

	@Override
	public Object objectFromBuffer(Object anInputBuffer, Class<?> aClass, List<Object> retrievedObjects)
			throws StreamCorruptedException{
		ExtensibleBufferDeserializationInitiated.newCase(this, null, anInputBuffer, aClass);
		DispatchingSerializer dispatchingSerializer = SerializerRegistry.getDispatchingSerializer();
		int size = (int) SerializerRegistry.getValueSerializer(Integer.class).objectFromBuffer(anInputBuffer, Integer.class, retrievedObjects);
		Object deserializedObj = SerializerRegistry.getDeserializingObject(aClass);
		retrievedObjects.add(deserializedObj);
		for(int i=0; i<size; i++){
			RemoteReflectionUtility.listAdd(deserializedObj, dispatchingSerializer.objectFromBuffer(anInputBuffer, retrievedObjects));
		}
		RemoteReflectionUtility.invokeInitSerializedObject(deserializedObj);
		ExtensibleBufferDeserializationFinished.newCase(this, null, anInputBuffer, deserializedObj, retrievedObjects);
		return deserializedObj;
	}

}
