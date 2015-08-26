package org.sonar.plugins.cxx.cccc.decorators;

import java.util.ArrayList;
import java.util.List;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.plugins.cxx.cccc.CxxCcccMetrics;
import org.sonar.plugins.cxx.cccc.utils.CxxAbstractAverageChildrenDecorator;
import org.sonar.plugins.cxx.cccc.utils.CxxAverageGroupMetrics;

public class CxxCcccAverageDecorator extends CxxAbstractAverageChildrenDecorator {

	@Override
	public List<CxxAverageGroupMetrics> averageMetrics() {
		CxxAverageGroupMetrics nestingLevel = new CxxAverageGroupMetrics(CxxCcccMetrics.AVERAGE_NESTING_LEVEL,
				CxxCcccMetrics.TOTAL_NESTING_LEVEL, CoreMetrics.FUNCTIONS);
		CxxAverageGroupMetrics functionLength = new CxxAverageGroupMetrics(CxxCcccMetrics.AVERAGE_LENGTH_OF_METHOD,
				CxxCcccMetrics.TOTAL_LENGTH_OF_METHOD, CoreMetrics.FUNCTIONS);
		CxxAverageGroupMetrics methodsInClass = new CxxAverageGroupMetrics(CxxCcccMetrics.AVERAGE_METHODS_IN_CLASS,
				CxxCcccMetrics.TOTAL_METHODS_IN_CLASS, CoreMetrics.CLASSES);
		CxxAverageGroupMetrics iF4 = new CxxAverageGroupMetrics(CxxCcccMetrics.AVERAGE_IF4,
				CxxCcccMetrics.IF4, CoreMetrics.CLASSES);
		CxxAverageGroupMetrics fanIn = new CxxAverageGroupMetrics(CxxCcccMetrics.AVERAGE_FAN_IN,
				CoreMetrics.AFFERENT_COUPLINGS, CoreMetrics.CLASSES);
		CxxAverageGroupMetrics fanOut = new CxxAverageGroupMetrics(CxxCcccMetrics.AVERAGE_FAN_OUT,
				CoreMetrics.EFFERENT_COUPLINGS, CoreMetrics.CLASSES);
		
		ArrayList<CxxAverageGroupMetrics> groupMetrics = new ArrayList<CxxAverageGroupMetrics>();
		groupMetrics.add(nestingLevel);
		groupMetrics.add(functionLength);
		groupMetrics.add(methodsInClass);
		groupMetrics.add(iF4);
		groupMetrics.add(fanIn);
		groupMetrics.add(fanOut);
		return groupMetrics;
	}

}
