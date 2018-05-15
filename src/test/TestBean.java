package test;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import examples.serialization.StringHistory;

public class TestBean implements Serializable {
	private boolean testBool;
	private double testDouble;
	private Long testLong;
	private String testString;
	private Collection testCollection;
	private TestEnum testEnum;
	private int[] testArray;
	private Map<String, TestEnum> testMap;
	private StringHistory testListPattern;
	
	public TestBean(){
		
	}
	public boolean isTestBool() {
		return testBool;
	}
	public void setTestBool(boolean testBool) {
		this.testBool = testBool;
	}
	public double getTestDouble() {
		return testDouble;
	}
	public void setTestDouble(double testDouble) {
		this.testDouble = testDouble;
	}
	public Long getTestLong() {
		return testLong;
	}
	public void setTestLong(Long testLong) {
		this.testLong = testLong;
	}
	public String getTestString() {
		return testString;
	}
	public void setTestString(String testString) {
		this.testString = testString;
	}
	public Collection getTestCollection() {
		return testCollection;
	}
	public void setTestCollection(Collection testCollection) {
		this.testCollection = testCollection;
	}
	
	public TestEnum getTestEnum() {
		return testEnum;
	}
	public void setTestEnum(TestEnum testEnum) {
		this.testEnum = testEnum;
	}
	public int[] getTestArray() {
		return testArray;
	}
	public void setTestArray(int[] testArray) {
		this.testArray = testArray;
	}
	public Map<String, TestEnum> getTestMap() {
		return testMap;
	}
	public void setTestMap(Map<String, TestEnum> testMap) {
		this.testMap = testMap;
	}
	public StringHistory getTestListPattern() {
		return testListPattern;
	}
	public void setTestListPattern(StringHistory testListPattern) {
		this.testListPattern = testListPattern;
	}
	@Override
	public String toString(){
		return "testBool: "+testBool+" testDouble: "+testDouble+" testLong: "+testLong+" testString: "+
				testString+" testCollection: "+testCollection+" testEnum: "+testEnum+" testArray: "+printArray(testArray)+" testMap: "+testMap+
				" testListPattern: "+testListPattern;
	}
	
	private String printArray(int[] array){
		if(array != null){
			StringBuilder out = new StringBuilder();
			out.append("[");
			for(int i=0; i<array.length; i++){
				out.append(array[i]+", ");
			}
			out.append("]");
			return out.toString();
		}
		return null;
	}
	
}
