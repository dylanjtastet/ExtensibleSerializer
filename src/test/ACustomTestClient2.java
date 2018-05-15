package test;

import examples.gipc.counter.customization.ACustomCounterClient;
import examples.gipc.counter.customization.ACustomSerializerFactory;
import examples.gipc.counter.customization.FactorySetterFactory;
import factory.ABinarySerializerFactory;
import serialization.SerializerSelector;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.trace.port.serialization.extensible.ExtensibleSerializationTraceUtility;

public class ACustomTestClient2 extends ACustomCounterClient {
	@Tags({Comp533Tags.CUSTOM_SERIALIZER_CLIENT2})
	public static void main(String[] args){
		ExtensibleSerializationTraceUtility.setTracing();
		FactorySetterFactory.setSingleton(new ExtendedTracingFactorySetter());
		launch("Client2");
	}
}
