package serializer.dispatching;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

import registry.SerializerRegistry;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationFinished;
import util.trace.port.serialization.extensible.ExtensibleBufferDeserializationInitiated;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationFinished;
import util.trace.port.serialization.extensible.ExtensibleValueSerializationInitiated;

@Tags({Comp533Tags.NULL_SERIALIZER})
public class ANullSerializer implements NullSerializer{

	@Override
	public String getNullClassName() {
		return "null";
	}

	@Override
	public void nullToBuffer(Object out) {
		ExtensibleValueSerializationInitiated.newCase(this, null, out);
		try {
			SerializerRegistry.getValueSerializer(String.class).objectToBuffer(out, "null", null);
			ExtensibleValueSerializationFinished.newCase(this, null, out, null);
		} catch (NotSerializableException e) {
			System.err.println("This shouldn't really happen");
			e.printStackTrace();
		}
	}

	@Override
	public Object nullFromBuffer(Object in) {
		ExtensibleBufferDeserializationInitiated.newCase(this, null, in, Integer.class);
		ExtensibleBufferDeserializationFinished.newCase(this, null, in, new Object(), new Object());
//		try {
//			SerializerRegistry.getValueSerializer(String.class).objectFromBuffer(in, null, null);
//		} catch (StreamCorruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NotSerializableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return null;
	}
	
}
