package simulation;

import server.ACombinedServer;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.tags.DistributedTags;
import util.trace.port.serialization.extensible.ExtensibleSerializationTraceUtility;
import factory.ABinarySerializerFactory;
import factory.ATextualSerializerFactory;
import serialization.SerializerSelector;
@Tags({Comp533Tags.CUSTOM_SERIALIZER, DistributedTags.SERVER, DistributedTags.GIPC, Comp533Tags.CUSTOM_IPC})
public class ACombinedServerRunner {
	public static void main(String[] args){
		ExtensibleSerializationTraceUtility.setTracing();
		SerializerSelector.setSerializerFactory(new ABinarySerializerFactory());
		ACombinedServer.main(args);
	}
}
