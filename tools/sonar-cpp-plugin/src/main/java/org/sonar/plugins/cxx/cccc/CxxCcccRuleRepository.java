package org.sonar.plugins.cxx.cccc;

import org.sonar.plugins.cxx.utils.CxxAbstractRuleRepository;

public class CxxCcccRuleRepository extends CxxAbstractRuleRepository{
	@Override
	protected String repositoryFileName() {
		return "/cccc.xml";
	}
	
	@Override
	protected String repositoryName() {
		return "cccc";
	}
}
