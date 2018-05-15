package serializer.value;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.io.StringReader;
import java.lang.reflect.Array;
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

@Tags({Comp533Tags.ARRAY_SERIALIZER})
public class ArraySerializer implements ValueSerializer {

	@Override
	public void objectToBuffer(Object anOutputBuffer, Object anObject, List<Object> visitedObjects)
			throws NotSerializableException {
		ExtensibleValueSerializationInitiated.newCase(this, anObject, anOutputBuffer);
		visitedObjects.add(anObject);
		DispatchingSerializer dispatchingSerializer = SerializerRegistry.getDispatchingSerializer();
		int len = Array.getLength(anObject);
		SerializerRegistry.getValueSerializer(Integer.class).objectToBuffer(anOutputBuffer, len, visitedObjects);
		for(int i = 0; i<len; i++){
			dispatchingSerializer.objectToBuffer(anOutputBuffer, Array.get(anObject, i), visitedObjects);
		}
		ExtensibleValueSerializationFinished.newCase(this, anObject, anOutputBuffer, visitedObjects);
	}

	@Override
	public Object objectFromBuffer(Object anInputBuffer, Class<?> aClass, List<Object> retrievedObjects)
			throws StreamCorruptedException{
		ExtensibleBufferDeserializationInitiated.newCase(this, null, anInputBuffer, aClass);
		int len = (int) SerializerRegistry.getValueSerializer(Integer.class).objectFromBuffer(anInputBuffer, Integer.class, retrievedObjects);
		DispatchingSerializer dispatchingSerializer = SerializerRegistry.getDispatchingSerializer();
		Class<?> deserializedClass = SerializerRegistry.getDeserializingClass(Array.class);
		Class<?> deserializedComponentType = aClass.getComponentType();
		Object deserializedObject = null;
		try {
			deserializedObject = deserializedClass.equals(Array.class)? Array.newInstance(deserializedComponentType, len) : deserializedClass.newInstance();
			retrievedObjects.add(deserializedObject);
		} catch (NegativeArraySizeException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		for(int i=0; i<len; i++){
			Object component = dispatchingSerializer.objectFromBuffer(anInputBuffer, retrievedObjects);
			if(deserializedObject.getClass().isArray()){
				Array.set(deserializedObject, i, component);
			}
			else{
				((Collection)deserializedObject).add(component);
			}
		}
		ExtensibleBufferDeserializationFinished.newCase(this, null, (anInputBuffer instanceof StringReader)? ((StringReader)anInputBuffer).toString() : anInputBuffer, deserializedObject, retrievedObjects);
		return deserializedObject;
	}

}
