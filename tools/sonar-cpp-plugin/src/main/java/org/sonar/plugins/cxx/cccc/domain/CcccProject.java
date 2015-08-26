package org.sonar.plugins.cxx.cccc.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.sonar.plugins.cxx.cccc.violation.CcccViolation;

public class CcccProject {
	
	private List<CcccModule> modules = new ArrayList<CcccModule>();
	private CcccProjectSummary summary = new CcccProjectSummary();
	private List<CcccViolation> violations = new ArrayList<CcccViolation>();
	
	public CcccProjectSummary getSummary() {
		return summary;
	}

	public void setSummary(CcccProjectSummary summary) {
		this.summary = summary;
	}

	public void setModules(List<CcccModule> modules) {
		this.modules = modules;
	}

	public List<CcccModule> getModules() {
		return modules;
	}
	 
	public List<CcccViolation> getViolations() {
		return violations;
	}

	public void setViolations(List<CcccViolation> violations) {
		this.violations = violations;
	}
	
	public CcccModule getModuleByName(String name) throws NoSuchElementException {
		for(CcccModule module : modules) {
			if(module.getName().equals(name)) {
				return module;
			}
		}
		throw new NoSuchElementException("Module " + name + " not found");
	}

	public void add(CcccModule module) {
		modules.add(module);
	}

}
