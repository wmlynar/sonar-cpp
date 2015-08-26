package org.sonar.plugins.cxx.cccc.decorators;

import org.sonar.api.measures.Metric;
import org.sonar.plugins.cxx.cccc.CxxCcccMetrics;
import org.sonar.plugins.cxx.cccc.utils.CxxAbstractTop5Dcorator;

public class CxxAverageTop5MaxNestingDecorator extends CxxAbstractTop5Dcorator {

	@Override
	public Metric dependOn() {
		return CxxCcccMetrics.AVERAGE_TOP5_MAX_NESTING_DISTRIB;
	}

	@Override
	public Metric generatesMetric() {
		return CxxCcccMetrics.AVERAGE_TOP5_MAX_NESTING;
	}

}
