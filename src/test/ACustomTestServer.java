package test;

import examples.gipc.counter.customization.ACustomCounterServer;
import examples.gipc.counter.customization.FactorySetterFactory;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.trace.port.serialization.extensible.ExtensibleSerializationTraceUtility;

public class ACustomTestServer extends ACustomCounterServer {
	@Tags({Comp533Tags.CUSTOM_SERIALIZER_SERVER})
	public static void main(String[] args){
		ExtensibleSerializationTraceUtility.setTracing();
		FactorySetterFactory.setSingleton(new ExtendedTracingFactorySetter());
		launch();
	}
}
