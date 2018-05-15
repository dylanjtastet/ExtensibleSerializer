package serializer.logical;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import registry.SerializerRegistry;
import serialization.Serializer;
import util.annotations.Comp533Tags;
import util.annotations.Tags;

@Tags({Comp533Tags.LOGICAL_TEXTUAL_SERIALIZER})
public class ATextualSerializer implements Serializer {
	private ByteBuffer bb;
	private StringBuffer sb;
	private List<Object> retreivedObjects;
	private List<Object> visitedObjects;
	
	public ATextualSerializer(){
		bb = ByteBuffer.allocate(10000000);
		sb = new StringBuffer();
		retreivedObjects = new ArrayList<Object>();
		visitedObjects = new ArrayList<Object>();
	}
	@Override
	public Object objectFromInputBuffer(ByteBuffer inputBuffer) throws StreamCorruptedException {
		retreivedObjects.clear();
		byte[] remainingBytes = new byte[inputBuffer.remaining()];
		inputBuffer.mark();
		inputBuffer.get(remainingBytes);
		String in = new String(remainingBytes);
		StringReader reader = new StringReader(in);
		Object out = SerializerRegistry.getDispatchingSerializer().objectFromBuffer(reader, retreivedObjects);
		if(out==null){
			inputBuffer.reset();
		}
		return out;
	}

	@Override
	public ByteBuffer outputBufferFromObject(Object object) throws NotSerializableException {
		if(object instanceof ByteBuffer){
			return null;
		}
		sb.setLength(0);
		bb.clear();
		visitedObjects.clear();
		SerializerRegistry.getDispatchingSerializer().objectToBuffer(sb, object, visitedObjects);
		bb.put(sb.toString().getBytes());
		bb.flip();
		return bb;
	}

}
