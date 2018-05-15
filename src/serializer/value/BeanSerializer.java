package serializer.value;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import registry.SerializerRegistry;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.misc.RemoteReflectionUtility;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationFinished;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationInitiated;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationFinished;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationInitiated;

@Tags({Comp533Tags.BEAN_SERIALIZER})
public class BeanSerializer implements ValueSerializer {

	@Override
	public void objectToBuffer(Object anOutputBuffer, Object anObject, List<Object> visitedObjects)
			throws NotSerializableException {
		ExtensibleValueSerializationInitiated.newCase(this, anObject, anOutputBuffer);
		visitedObjects.add(anObject);
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(anObject.getClass());
			for(PropertyDescriptor p : beanInfo.getPropertyDescriptors()){
				if(p.getReadMethod() == null || p.getWriteMethod() == null){
					continue;
				}
				Method getter = p.getReadMethod();
				if(RemoteReflectionUtility.isTransient(getter)){
					continue;
				}
				Object property = null;
				try {
					property = getter.invoke(anObject);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace(System.out);
					continue;
				}
				SerializerRegistry.getDispatchingSerializer().objectToBuffer(anOutputBuffer, property, visitedObjects);
			}
			ExtensibleValueSerializationFinished.newCase(this, anObject, anOutputBuffer, visitedObjects);
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Object objectFromBuffer(Object anInputBuffer, Class<?> aClass, List<Object> retrievedObjects)
			throws StreamCorruptedException{
		ExtensibleBufferDeserializationInitiated.newCase(this, null, anInputBuffer, aClass);
		Object deserializedBean = null;
		try {
			deserializedBean = aClass.newInstance();
			retrievedObjects.add(deserializedBean);
		} catch (InstantiationException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(deserializedBean.getClass());
			for(PropertyDescriptor p : beanInfo.getPropertyDescriptors()){
				if(p.getReadMethod() == null || p.getWriteMethod() == null){
					continue;
				}
				Method writeMethod = p.getWriteMethod();
				if(RemoteReflectionUtility.isTransient(writeMethod)){
					continue;
				}
				try {
					writeMethod.invoke(deserializedBean, SerializerRegistry.getDispatchingSerializer().
							objectFromBuffer(anInputBuffer, retrievedObjects));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}	
			}
			RemoteReflectionUtility.invokeInitSerializedObject(deserializedBean);
			ExtensibleBufferDeserializationFinished.newCase(this, null, anInputBuffer, deserializedBean, retrievedObjects);
			return deserializedBean;
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
