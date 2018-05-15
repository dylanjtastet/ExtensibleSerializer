package serializer.dispatching;

import util.annotations.Comp533Tags;
import util.annotations.Tags;

@Tags({Comp533Tags.NULL_SERIALIZER})
public interface NullSerializer {
	public String getNullClassName();
	public void nullToBuffer(Object out);
	public Object nullFromBuffer(Object in);
}
