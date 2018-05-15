package test;

import examples.serialization.SerializationTester;
import factory.ABinarySerializerFactory;
import factory.ATextualSerializerFactory;
import serialization.SerializerSelector;
import serializer.logical.ABinarySerializer;
import util.trace.port.serialization.extensible.ExtensibleSerializationTraceUtility;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationInitiated;
public class MainTest {

	public static void main(String[] args) {
		ExtensibleSerializationTraceUtility.setTracing();
		SerializerSelector.setSerializerFactory(new ATextualSerializerFactory());
		SerializationTester.testSerialization();
	}

}
