package org.sonar.plugins.cxx.cccc.violation;

import java.util.ArrayList;
import java.util.List;

public class CcccFileViolation {
	private String name;
	private List<Integer> lines = new ArrayList<Integer>();
	
	public CcccFileViolation(String name) {
		super();
		this.name = name;
	}
	
	public void addLine(Integer line) {
		lines.add(line);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getLines() {
		return lines;
	}

	public void setLines(List<Integer> lines) {
		this.lines = lines;
	}
	
	@Override
	public String toString() {
		String s = "Name " + name + "\n";
		for (Integer line : lines) {
			s += "Line " + line + "\n";
		}
		return s;
	}

}
