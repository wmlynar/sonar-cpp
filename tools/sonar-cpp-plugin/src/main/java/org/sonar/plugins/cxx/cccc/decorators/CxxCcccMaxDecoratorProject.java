package org.sonar.plugins.cxx.cccc.decorators;

import java.util.Arrays;
import java.util.List;


import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Resource;
import org.sonar.api.web.ResourceQualifier;
import org.sonar.plugins.cxx.cccc.CxxCcccMetrics;
import org.sonar.plugins.cxx.cccc.utils.CxxAbstractGetMaxChildrenDecorator;

public class CxxCcccMaxDecoratorProject extends CxxAbstractGetMaxChildrenDecorator{

	
	@Override
	public List<Metric> generatesMetrics() {
		return Arrays.asList(
				CxxCcccMetrics.MAX_COMPLIXITY_OF_METHOD, 
				CxxCcccMetrics.MAX_COMPLIXITY_OF_CLASS, 
				CxxCcccMetrics.MAX_NESTING_LEVEL, 
				CxxCcccMetrics.MAX_LINES_OF_CODE_IN_FUNCTION,
				CxxCcccMetrics.MAX_METHODS_IN_CLASS,
				CxxCcccMetrics.MAX_IF4,
				CxxCcccMetrics.MAX_FAN_IN,
				CxxCcccMetrics.MAX_FAN_OUT);
	}

	@Override
	public String resourceQualifer() {
		return Resource.QUALIFIER_PROJECT;
	}

	
}
