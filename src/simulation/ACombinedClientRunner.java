package simulation;

import client.ACombinedClient;
import factory.ABinarySerializerFactory;
import factory.ATextualSerializerFactory;
import serialization.SerializerSelector;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.tags.DistributedTags;
import util.trace.port.serialization.extensible.ExtensibleSerializationTraceUtility;

public class ACombinedClientRunner {
	@Tags({Comp533Tags.CUSTOM_SERIALIZER, DistributedTags.CLIENT, DistributedTags.GIPC, Comp533Tags.CUSTOM_IPC})
	public static void main(String[] args){
		ExtensibleSerializationTraceUtility.setTracing();
		SerializerSelector.setSerializerFactory(new ABinarySerializerFactory());
		ACombinedClient.main(args);
	}
}
