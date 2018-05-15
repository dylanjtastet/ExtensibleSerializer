package factory;

import registry.SerializerRegistry;
import serialization.Serializer;
import serialization.SerializerFactory;
import serializer.logical.ABinarySerializer;
import util.annotations.Comp533Tags;
import util.annotations.Tags;

@Tags({Comp533Tags.LOGICAL_BINARY_SERIALIZER_FACTORY})
public class ABinarySerializerFactory implements SerializerFactory {
	public static Serializer singleton;
	@Override
	public Serializer createSerializer() {
		if(singleton == null){
			SerializerRegistry.setDefaults();
			singleton = new ABinarySerializer();
		}
		return singleton;
	}

}
