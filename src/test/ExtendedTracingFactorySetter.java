package test;

import examples.gipc.counter.customization.ACustomSerializerFactory;
import examples.gipc.counter.customization.ATracingFactorySetter;
import factory.ABinarySerializerFactory;
import factory.ATextualSerializerFactory;
import serialization.SerializerSelector;

public class ExtendedTracingFactorySetter extends ATracingFactorySetter {

	@Override
	public void setFactories() {
		super.setFactories();
		SerializerSelector.setSerializerFactory(new ATextualSerializerFactory());	
	}
	
}
