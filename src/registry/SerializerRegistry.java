package registry;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import serializer.dispatching.ADispatchingSerializer;
import serializer.dispatching.ANullSerializer;
import serializer.dispatching.DispatchingSerializer;
import serializer.dispatching.NullSerializer;
import serializer.value.ArraySerializer;
import serializer.value.BeanSerializer;
import serializer.value.BooleanSerializer;
import serializer.value.CollectionSerializer;
import serializer.value.DoubleSerializer;
import serializer.value.EnumSerializer;
import serializer.value.FloatSerializer;
import serializer.value.IntegerSerializer;
import serializer.value.ListPatternSerializer;
import serializer.value.LongSerializer;
import serializer.value.MapSerializer;
import serializer.value.ShortSerializer;
import serializer.value.StringSerializer;
import serializer.value.ValueSerializer;
import util.annotations.Comp533Tags;
import util.annotations.Tags;

@Tags({Comp533Tags.SERIALIZER_REGISTRY})
public class SerializerRegistry {
	private static HashMap<Class<?>, ValueSerializer> valueSerializers = new HashMap<Class<?>, ValueSerializer>();
	private static HashMap<Class<?>, Class<?>> deserializingClasses = new HashMap<Class<?>, Class<?>>();
	private static DispatchingSerializer dispatchingSerializer;
	private static ValueSerializer beanSerializer;
	private static NullSerializer nullSerializer;
	private static ValueSerializer enumSerializer;
	private static ValueSerializer arraySerializer;
	private static ValueSerializer listPatternSerializer;
	
	public static void setDefaults(){
		registerDispatchingSerializer(new ADispatchingSerializer());
		CollectionSerializer collectionSerializer = new CollectionSerializer();
		MapSerializer mapSerializer = new MapSerializer();
		registerValueSerializer(Integer.class, new IntegerSerializer());
		registerValueSerializer(Boolean.class, new BooleanSerializer());
		registerValueSerializer(Double.class, new DoubleSerializer());
		registerValueSerializer(Long.class, new LongSerializer());
		registerValueSerializer(Short.class, new ShortSerializer());
		registerValueSerializer(String.class, new StringSerializer());
		registerValueSerializer(Float.class, new FloatSerializer());
		registerValueSerializer(Collection.class, collectionSerializer);
		registerValueSerializer(ArrayList.class, collectionSerializer);
		registerValueSerializer(Vector.class, collectionSerializer);
		registerValueSerializer(HashSet.class, collectionSerializer);
		registerValueSerializer(Hashtable.class, mapSerializer);
		registerValueSerializer(HashMap.class, mapSerializer);
		registerValueSerializer(TreeMap.class, mapSerializer);
		registerDeserializingClass(Collection.class, ArrayList.class);
		registerDeserializingClass(Array.class, Array.class);
		registerDeserializingClass(Map.class, TreeMap.class);
		registerBeanSerializer(new BeanSerializer());
		registerNullSerializer(new ANullSerializer());
		registerEnumSerializer(new EnumSerializer());
		registerArraySerializer(new ArraySerializer());
		registerListPatternSerializer(new ListPatternSerializer());
	}
	
	
	public static void registerValueSerializer(Class<?> aClass, ValueSerializer anExternalSerializer){
		valueSerializers.put(aClass, anExternalSerializer);
	}
	
	public static ValueSerializer getValueSerializer(Object o){
		return getValueSerializer(o.getClass());
	}

	public static ValueSerializer getValueSerializer(Class<?> aClass){
		return valueSerializers.get(aClass);
	}
	
	public static void registerDeserializingClass(Class<?> aClass, Class<?> deserializingClass){
		deserializingClasses.put(aClass, deserializingClass);
	}
	
	public static void registerDispatchingSerializer(DispatchingSerializer serializer){
		dispatchingSerializer = serializer; 
	}
	
	public static DispatchingSerializer getDispatchingSerializer(){
		return dispatchingSerializer;
	}
	
	public static void registerBeanSerializer(BeanSerializer serializer){
		beanSerializer = serializer;
	}
	public static ValueSerializer getBeanSerializer(){
		return beanSerializer;
	}
	
	public static Object getDeserializingObject(Class<?> aClass){
		try {
			return deserializingClasses.getOrDefault(aClass, aClass).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			System.err.println("COULD NOT INSTANTIATE NEW OBJECT! Cause was:");
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.err.println("This really shouldn't happen.");
			e.printStackTrace();
			return null;
		}
	}
	
	public static Class<?> getDeserializingClass(Class<?> aClass){
		return deserializingClasses.getOrDefault(aClass, aClass);
	}
	
	public static void registerNullSerializer(NullSerializer ns){
		nullSerializer = ns;
	}
	
	public static NullSerializer getNullSerializer(){
		return nullSerializer;
	}
	
	public static void  registerEnumSerializer(ValueSerializer es){
		enumSerializer = es;
	}
	
	public static ValueSerializer getEnumSerializer(){
		return enumSerializer;
	}
	
	public static void registerArraySerializer(ValueSerializer as){
		arraySerializer = as;
	}
	
	public static ValueSerializer getArraySerializer(){
		return arraySerializer;
	}
	
	public static void registerListPatternSerializer(ValueSerializer lps){
		listPatternSerializer = lps;
	}
	
	public static ValueSerializer getListPatternSerializer(){
		return listPatternSerializer;
	}
}
