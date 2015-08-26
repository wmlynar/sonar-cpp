package org.sonar.plugins.cxx.cccc.domain;

/**
 * Represents metric in cccc xml file 
 * @author tomtom
 *
 */
public class CcccMetric {
	
	private String tag;
	private double value;
	private int level;
	
	public CcccMetric(String tag) {
		super();
		this.tag = tag;
	}
	public CcccMetric(String tag, double value, int level) {
		super();
		this.tag = tag;
		this.value = value;
		this.level = level;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void addValue(double value) {
		this.value += value;
	}
}
