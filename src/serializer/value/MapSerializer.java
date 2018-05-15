package serializer.value;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import registry.SerializerRegistry;
import serializer.dispatching.DispatchingSerializer;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationFinished;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationInitiated;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationFinished;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationInitiated;

@Tags({Comp533Tags.MAP_SERIALIZER})
public class MapSerializer implements ValueSerializer {

	@Override
	public void objectToBuffer(Object anOutputBuffer, Object anObject, List<Object> visitedObjects)
			throws NotSerializableException {
		ExtensibleValueSerializationInitiated.newCase(this, anObject, anOutputBuffer);
		visitedObjects.add(anObject);
		DispatchingSerializer dispatchingSerializer = SerializerRegistry.getDispatchingSerializer();
		Map<Object, Object> target = (Map<Object,Object>) anObject;
		SerializerRegistry.getValueSerializer(Integer.class).objectToBuffer(anOutputBuffer, target.size(), visitedObjects);
		Set<Object> ks = target.keySet();
		for(Object component : ks){
			dispatchingSerializer.objectToBuffer(anOutputBuffer, component, visitedObjects);
			dispatchingSerializer.objectToBuffer(anOutputBuffer, target.get(component), visitedObjects);
		}
		ExtensibleValueSerializationFinished.newCase(this, anObject, anOutputBuffer, visitedObjects);
	}

	@Override
	public Object objectFromBuffer(Object anInputBuffer, Class<?> aClass, List<Object> retrievedObjects)
			throws StreamCorruptedException{
		ExtensibleBufferDeserializationInitiated.newCase(this, null, anInputBuffer, aClass);
		DispatchingSerializer dispatchingSerializer = SerializerRegistry.getDispatchingSerializer();
		int size = (int) SerializerRegistry.getValueSerializer(Integer.class).objectFromBuffer(anInputBuffer, Integer.class, retrievedObjects);
		Map<Object, Object> deserializedObject = (Map<Object, Object>)SerializerRegistry.getDeserializingObject(aClass);
		retrievedObjects.add(deserializedObject);
		for(int i=0; i<size; i++){
			deserializedObject.put(dispatchingSerializer.objectFromBuffer(anInputBuffer, retrievedObjects), 
					dispatchingSerializer.objectFromBuffer(anInputBuffer, retrievedObjects));
		}
		ExtensibleBufferDeserializationFinished.newCase(this, null, anInputBuffer, deserializedObject, retrievedObjects);
		return deserializedObject;
	}

}
