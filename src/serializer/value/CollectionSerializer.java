package serializer.value;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

import registry.SerializerRegistry;
import serializer.dispatching.DispatchingSerializer;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationFinished;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationInitiated;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationFinished;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationInitiated;

@Tags({Comp533Tags.COLLECTION_SERIALIZER})
public class CollectionSerializer implements ValueSerializer {

	@SuppressWarnings("unchecked")
	@Override
	public void objectToBuffer(Object anOutputBuffer, Object anObject, List<Object> visitedObjects)
			throws NotSerializableException, IllegalArgumentException {
		ExtensibleValueSerializationInitiated.newCase(this, anObject, anOutputBuffer);
		visitedObjects.add(anObject);
		DispatchingSerializer dispatchingSerializer = SerializerRegistry.getDispatchingSerializer();
		if(!Collection.class.isAssignableFrom(anObject.getClass())){
			throw new IllegalArgumentException();
		}
		Collection<Object> target = (Collection<Object>) anObject;
		SerializerRegistry.getValueSerializer(Integer.class).objectToBuffer(anOutputBuffer, target.size(), visitedObjects);
		for(Object o : target){
			dispatchingSerializer.objectToBuffer(anOutputBuffer, o, visitedObjects);
		}
		ExtensibleValueSerializationFinished.newCase(this, anObject, anOutputBuffer, visitedObjects);
	}

	@Override
	public Object objectFromBuffer(Object anInputBuffer, Class<?> aClass, List<Object> retrievedObjects)
			throws StreamCorruptedException{
		ExtensibleBufferDeserializationInitiated.newCase(this, null, anInputBuffer, aClass);
		DispatchingSerializer dispatchingSerializer = SerializerRegistry.getDispatchingSerializer();
		Collection<Object> deserializedCollect = (Collection<Object>) SerializerRegistry.getDeserializingObject(aClass);
		retrievedObjects.add(deserializedCollect);
		int count = (int)SerializerRegistry.getValueSerializer(Integer.class).objectFromBuffer(anInputBuffer, Integer.class, retrievedObjects);
		for(int i=0; i<count; i++){
			deserializedCollect.add(dispatchingSerializer.objectFromBuffer(anInputBuffer, retrievedObjects));
		}
		ExtensibleBufferDeserializationFinished.newCase(this, null, anInputBuffer, deserializedCollect, retrievedObjects);
		return deserializedCollect;
	}

}
