package serializer.dispatching;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Collection;
import java.util.List;

import registry.SerializerRegistry;
import serializer.value.ValueSerializer;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.misc.RemoteReflectionUtility;

@Tags({Comp533Tags.DISPATCHING_SERIALIZER})
public class ADispatchingSerializer implements DispatchingSerializer {

	@Override
	public void objectToBuffer(Object anOutputBuffer, Object anObject, List<Object> visitedObjects)
			throws NotSerializableException {

		if(anObject == null){
			SerializerRegistry.getNullSerializer().nullToBuffer(anOutputBuffer);
			return;
		}
		if(visitedObjects.contains(anObject)){
			SerializerRegistry.getValueSerializer(String.class).objectToBuffer(anOutputBuffer, "REF", visitedObjects);
			SerializerRegistry.getValueSerializer(Integer.class).objectToBuffer(anOutputBuffer, 
					new Integer(visitedObjects.indexOf(anObject)), visitedObjects);
		}
		else{
			Class<?> objClass = anObject.getClass();
			ValueSerializer valueSerializer = objClass.isArray()? SerializerRegistry.getArraySerializer():
				objClass.isEnum()? SerializerRegistry.getEnumSerializer():
				SerializerRegistry.getValueSerializer(objClass);
			if(valueSerializer != null || Serializable.class.isAssignableFrom(objClass) || RemoteReflectionUtility.isList(objClass)
					|| Collection.class.isAssignableFrom(objClass)){
				SerializerRegistry.getValueSerializer(String.class).objectToBuffer(anOutputBuffer, objClass.getName(), visitedObjects);
				//Write classname
			}
			if(valueSerializer != null){
				valueSerializer.objectToBuffer(anOutputBuffer, anObject, visitedObjects);
			}
			else if(RemoteReflectionUtility.isList(objClass)){
				SerializerRegistry.getListPatternSerializer().objectToBuffer(anOutputBuffer, anObject, visitedObjects);
			}
			else if(Collection.class.isAssignableFrom(objClass)){
				SerializerRegistry.getValueSerializer(Collection.class).objectToBuffer(anOutputBuffer, anObject, visitedObjects);
			}
			else if(Serializable.class.isAssignableFrom(objClass)){
				SerializerRegistry.getBeanSerializer().objectToBuffer(anOutputBuffer, anObject, visitedObjects);
			}
			else{
				throw new NotSerializableException();
			}
		}
	}

	@Override
	public Object objectFromBuffer(Object anInputBuffer, List<Object> retrievedObjects)
			throws StreamCorruptedException {
		NullSerializer nullSerializer = SerializerRegistry.getNullSerializer();
		String tokenName = (String)SerializerRegistry.getValueSerializer(String.class).objectFromBuffer(anInputBuffer, String.class, retrievedObjects);
		try {
			if(tokenName.equals("REF")){
				return retrievedObjects.get(
						(int) SerializerRegistry.getValueSerializer(Integer.class).
						objectFromBuffer(anInputBuffer, Integer.class, retrievedObjects));
			}
			else if(tokenName.equals(nullSerializer.getNullClassName())){
				return nullSerializer.nullFromBuffer(anInputBuffer);
			}
			else{
				Class<?> targetClass = Class.forName(tokenName);
				ValueSerializer valueSerializer = targetClass.isEnum()? SerializerRegistry.getEnumSerializer(): 
					targetClass.isArray()? SerializerRegistry.getArraySerializer():
					SerializerRegistry.getValueSerializer(targetClass);	
				if(valueSerializer != null){
					return valueSerializer.objectFromBuffer(anInputBuffer, targetClass, retrievedObjects);
				}
				else{
					if(RemoteReflectionUtility.isList(targetClass)){
						return SerializerRegistry.getListPatternSerializer().objectFromBuffer(anInputBuffer, targetClass, retrievedObjects);
					}
					else if(Collection.class.isAssignableFrom(targetClass)){
						return SerializerRegistry.getValueSerializer(Collection.class).objectFromBuffer(anInputBuffer, targetClass, retrievedObjects);
					}
					else if(Serializable.class.isAssignableFrom(targetClass)){
						return SerializerRegistry.getBeanSerializer().objectFromBuffer(anInputBuffer, targetClass, retrievedObjects);
					}
					else{
						throw new StreamCorruptedException();
					}
				}
			}
		} catch (ClassNotFoundException  e) {
			return null;
		}
	}

}
