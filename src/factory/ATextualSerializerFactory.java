package factory;

import registry.SerializerRegistry;
import serialization.Serializer;
import serialization.SerializerFactory;
import serializer.logical.ATextualSerializer;
import util.annotations.Comp533Tags;
import util.annotations.Tags;

@Tags({Comp533Tags.LOGICAL_TEXTUAL_SERIALIZER_FACTORY})
public class ATextualSerializerFactory implements SerializerFactory {
	public static Serializer singleton;
	@Override
	public Serializer createSerializer() {
		if(singleton == null){
			SerializerRegistry.setDefaults();
			singleton = new ATextualSerializer();
		}
		return singleton;
	}

}
