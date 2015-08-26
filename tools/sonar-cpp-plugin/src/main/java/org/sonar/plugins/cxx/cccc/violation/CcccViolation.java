package org.sonar.plugins.cxx.cccc.violation;

import java.util.ArrayList;
import java.util.List;

public class CcccViolation {
	
	private String type;
	private String message;
	private List<CcccFileViolation> files = new ArrayList<CcccFileViolation>();
	
	
	public CcccViolation() {
		super();
	}

	public CcccViolation(String type, String message) {
		super();
		this.type = type;
		this.message = message;
	}
	
	public void addLine(String fileName, Integer line) {
		for(CcccFileViolation file : files) {
			if (!fileName.isEmpty() && fileName.equals(file.getName())) {
				file.addLine(line);
				return;
			}
		}
		CcccFileViolation file = new CcccFileViolation(fileName);
		file.addLine(line);
		files.add(file);
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<CcccFileViolation> getFiles() {
		return files;
	}
	public void setFiles(List<CcccFileViolation> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		String s = "Message " + message + "\n";
		for (CcccFileViolation file : files) {
			s += file.toString();
		}
		return s;
	}

}
