package test;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import examples.serialization.AStringHistory;
import examples.serialization.StringHistory;
import factory.ABinarySerializerFactory;
import factory.ATextualSerializerFactory;
import registry.SerializerRegistry;
import serialization.Serializer;
import serializer.dispatching.ADispatchingSerializer;
import serializer.dispatching.DispatchingSerializer;
import serializer.logical.ABinarySerializer;
import serializer.value.BooleanSerializer;
import serializer.value.FloatSerializer;
import serializer.value.IntegerSerializer;
import serializer.value.LongSerializer;
import serializer.value.ShortSerializer;
import serializer.value.StringSerializer;
import util.misc.RemoteReflectionUtility;

public class TestPlayground {
	public static void main(String[] args) throws StreamCorruptedException, NotSerializableException{
		ProgrammerDefinedCollection testCollection = (ProgrammerDefinedCollection)ProgrammerDefinedCollection.createFilledInstance();
		System.out.println(testCollection);
		Serializer serializer = new ABinarySerializerFactory().createSerializer();
		ByteBuffer buffer = serializer.outputBufferFromObject(testCollection);
		Object out = serializer.objectFromInputBuffer(buffer);
		System.out.println(out);
		System.out.println(out.equals(testCollection));
	}
}
