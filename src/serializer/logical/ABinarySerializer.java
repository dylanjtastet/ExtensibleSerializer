package serializer.logical;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import registry.SerializerRegistry;
import serialization.Serializer;
import util.annotations.Comp533Tags;
import util.annotations.Tags;

@Tags({Comp533Tags.LOGICAL_BINARY_SERIALIZER})
public class ABinarySerializer implements Serializer {
	private List<Object> retreivedObjects;
	private List<Object> visitedObjects;
	private ByteBuffer bb;
	
	public ABinarySerializer(){
		retreivedObjects = new ArrayList<Object>();
		visitedObjects = new ArrayList<Object>();
		bb = ByteBuffer.allocate(10000000);
	}
	
	@Override
	public Object objectFromInputBuffer(ByteBuffer inputBuffer) throws StreamCorruptedException {
		retreivedObjects.clear();
		return SerializerRegistry.getDispatchingSerializer().objectFromBuffer(inputBuffer, retreivedObjects);
	}

	@Override
	public ByteBuffer outputBufferFromObject(Object object) throws NotSerializableException {
//		if(object instanceof ByteBuffer){
//			return null;
//		}
		visitedObjects.clear();
		bb.clear();
		SerializerRegistry.getDispatchingSerializer().objectToBuffer(bb, object, visitedObjects);
		bb.flip();
		return bb;
	}

}
