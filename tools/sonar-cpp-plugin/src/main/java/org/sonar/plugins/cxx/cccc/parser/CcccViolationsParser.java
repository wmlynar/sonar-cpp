package org.sonar.plugins.cxx.cccc.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.resources.Project;
import org.sonar.plugins.cxx.cccc.CcccPluginProperties;
import org.sonar.plugins.cxx.cccc.domain.CcccFunction;
import org.sonar.plugins.cxx.cccc.domain.CcccModule;
import org.sonar.plugins.cxx.cccc.domain.CcccProject;
import org.sonar.plugins.cxx.cccc.violation.CcccViolation;


public class CcccViolationsParser {
	
	private double complexityThreshold1 = 20;
	private double complexityThreshold2 = 40;
	private static final String COMPLEXITY_OF_METHOD_MESSAGE = "Complexity of method > ";
	private static final String COMPLEXITY_OF_METHOD_TYPE = "ComplexityOfMethod";
	private static final String CRITICAL_COMPLEXITY_OF_METHOD_MESSAGE = "Complexity of method > ";
	private static final String CRITICAL_COMPLEXITY_OF_METHOD_TYPE = "CriticalComplexityOfMethod";

	private double linesInMethodThreshold1 = 30;
	private double linesInMethodThreshold2 = 100;
	private static final String LINES_IN_METHOD_MESSAGE = "Number of lines in method > ";
	private static final String LINES_IN_METHOD_TYPE = "LinesInMethodViolation";
	private static final String CRITICAL_LINES_IN_METHOD_MESSAGE = "Number of lines in method > ";
	private static final String CRITICAL_LINES_IN_METHOD_TYPE = "CriticalLinesInMethodViolation";

	private static final String UNDOCUMENTED_API_MESSAGE = "Undocumented public method or class";
	private static final String UNDOCUMENTED_API_TYPE = "UndocumentedAPIViolation";

	private double nestingThreshold1 = 3;
	private double nestingThreshold2 = 6;
	private static final String MAX_NESTING_MESSAGE = "Max nesting level > ";
	private static final String MAX_NESTING_TYPE = "MaxNestingViolation";
	private static final String CRITICAL_MAX_NESTING_MESSAGE = "Max nesting level > ";
	private static final String CRITICAL_MAX_NESTING_TYPE = "CriticalMaxNestingViolation";

	private double methodsInClassThreshold1 = 20;
	private double methodsInClassThreshold2 = 40;
	private static final String MAX_METHODS_MESSAGE = "Number of methods in class > ";
	private static final String MAX_METHODS_TYPE = "MaxMethodsViolation";
	private static final String CRITICAL_MAX_METHODS_MESSAGE = "Number of methods in class > ";
	private static final String CRITICAL_MAX_METHODS_TYPE = "CriticalMaxMethodsViolation";

	public List<CcccViolation> parseModel(CcccProject model) {
		

		List<CcccViolation> violations = new ArrayList<CcccViolation>();
		
		CcccViolation complexityViolation = new CcccViolation(COMPLEXITY_OF_METHOD_TYPE, COMPLEXITY_OF_METHOD_MESSAGE + complexityThreshold1);
		CcccViolation criticalComplexityViolation = new CcccViolation(CRITICAL_COMPLEXITY_OF_METHOD_TYPE, CRITICAL_COMPLEXITY_OF_METHOD_MESSAGE + complexityThreshold2);
		CcccViolation linesInMethodViolation = new CcccViolation(LINES_IN_METHOD_TYPE, LINES_IN_METHOD_MESSAGE + linesInMethodThreshold1);
		CcccViolation criticalLinesInMethodViolation = new CcccViolation(CRITICAL_LINES_IN_METHOD_TYPE, CRITICAL_LINES_IN_METHOD_MESSAGE + linesInMethodThreshold2);
		CcccViolation undocumentedAPIViolation = new CcccViolation(UNDOCUMENTED_API_TYPE, UNDOCUMENTED_API_MESSAGE);
		CcccViolation maxNestingViolation = new CcccViolation(MAX_NESTING_TYPE, MAX_NESTING_MESSAGE + nestingThreshold1);
		CcccViolation criticalMaxNestingViolation = new CcccViolation(CRITICAL_MAX_NESTING_TYPE, CRITICAL_MAX_NESTING_MESSAGE + nestingThreshold2);
		CcccViolation methodsInClassViolation = new CcccViolation(MAX_METHODS_TYPE, MAX_METHODS_MESSAGE + methodsInClassThreshold1);
		CcccViolation criticalMethodsInClassViolation = new CcccViolation(CRITICAL_MAX_METHODS_TYPE, CRITICAL_MAX_METHODS_MESSAGE + methodsInClassThreshold2);
		
		for (CcccModule module : model.getModules()) {

			// skip when marked with special comment
			if (module.getNocccc().getValue() != 0) {
				continue;
			}

			if (module.isClass()) {
				// check if documented
				if (!module.isDocumented()) {
					undocumentedAPIViolation.addLine(module.getFile(), module.getLine());
				}
				
				// check max methods in class
				if(module.getNumberOfFunctions() > methodsInClassThreshold2) {
					criticalMethodsInClassViolation.addLine(module.getFile(), module.getLine());
				} else if(module.getNumberOfFunctions() > methodsInClassThreshold1) {
					methodsInClassViolation.addLine(module.getFile(), module.getLine());
				}
			}
			
			
			for (CcccFunction function : module.getFunctions()) {
				
				// skip when marked with special comment
				if (function.getNocccc().getValue() != 0) {
					continue;
				}

				// check max complexity
				if (function.getComplexity().getValue() > complexityThreshold2) {
					criticalComplexityViolation.addLine(function.getFile(), function.getLine());
				} else if (function.getComplexity().getValue() > complexityThreshold1) {
					complexityViolation.addLine(function.getFile(), function.getLine());
				}
				
				// check max lines of code
				if (function.getLinesOfCode().getValue() > linesInMethodThreshold2) {
					criticalLinesInMethodViolation.addLine(function.getFile(), function.getLine());
				} else if (function.getLinesOfCode().getValue() > linesInMethodThreshold1) {
					linesInMethodViolation.addLine(function.getFile(), function.getLine());
				}
				
				// check documentation
				if (function.isDocumented() == false && function.isPublic() == true) {
					undocumentedAPIViolation.addLine(function.getFile(), function.getLine());
				}
				
				// check nesting level
				if (function.getMaxNesting().getValue() > nestingThreshold2) {
					criticalMaxNestingViolation.addLine(function.getFile(), function.getLine());
				} else if (function.getMaxNesting().getValue() > nestingThreshold1) {
					maxNestingViolation.addLine(function.getFile(), function.getLine());
				}
			}
			
		}
		violations.add(complexityViolation);
		violations.add(criticalComplexityViolation);
		violations.add(linesInMethodViolation);
		violations.add(criticalLinesInMethodViolation);
		violations.add(undocumentedAPIViolation);
		violations.add(maxNestingViolation);
		violations.add(methodsInClassViolation);
		violations.add(criticalMethodsInClassViolation);
		return violations;
	}
	
	/**
	 * Check if properties if configuration are different then default values 
	 * @param project Sonar project
	 */
	public void getConfigurationProperties (Project project) {
		if (project == null) {
			return;
		}
		nestingThreshold1 = getProperty(project, CcccPluginProperties.CCCC_MAX_NESTING_PARAM_PROPERTY, 
				CcccPluginProperties.CCCC_MAX_NESTING_PARAM_DEFAULT_VALUE, nestingThreshold1);
		nestingThreshold2 = getProperty(project, CcccPluginProperties.CCCC_CRITICAL_MAX_NESTING_PARAM_PROPERTY, 
				CcccPluginProperties.CCCC_CRITICAL_MAX_NESTING_PARAM_DEFAULT_VALUE, nestingThreshold2);
		complexityThreshold1 = getProperty(project, CcccPluginProperties.CCCC_COMPLEXITY_OF_METHOD_PROPERTY, 
				CcccPluginProperties.CCCC_COMPLEXITY_OF_METHOD_DEFAULT_VALUE, complexityThreshold1);
		complexityThreshold2 = getProperty(project, CcccPluginProperties.CCCC_CRITICAL_COMPLEXITY_OF_METHOD_PROPERTY, 
				CcccPluginProperties.CCCC_CRITICAL_COMPLEXITY_OF_METHOD_DEFAULT_VALUE, complexityThreshold2);
		linesInMethodThreshold1 = getProperty(project, CcccPluginProperties.CCCC_LINES_IN_METHOD_PROPERTY, 
				CcccPluginProperties.CCCC_LINES_IN_METHOD_DEFAULT_VALUE, linesInMethodThreshold1);
		linesInMethodThreshold2 = getProperty(project, CcccPluginProperties.CCCC_CRITICAL_LINES_IN_METHOD_PROPERTY, 
				CcccPluginProperties.CCCC_CRITICAL_LINES_IN_METHOD_DEFAULT_VALUE, linesInMethodThreshold2);
		methodsInClassThreshold1 = getProperty(project, CcccPluginProperties.CCCC_MAX_METHODS_PROPERTY, 
				CcccPluginProperties.CCCC_MAX_METHODS_DEFAULT_VALUE, methodsInClassThreshold1);
		methodsInClassThreshold2 = getProperty(project, CcccPluginProperties.CCCC_CRITICAL_MAX_METHODS_PROPERTY, 
				CcccPluginProperties.CCCC_CRITICAL_MAX_METHODS_DEFAULT_VALUE, methodsInClassThreshold2);
	}
	
	
	/**
	 * Get property value form project configuration and check if is different then threshold
	 * @param project Sonar project
	 * @param key Property Key
	 * @param defaultValue Property default value
	 * @param threshold 
	 * @return property value if is different then threshold
	 */
	public double getProperty(Project project, String key, double defaultValue, double threshold) {
		try {
			double property = project.getConfiguration().getDouble(key, defaultValue);
			if (property != threshold) {
				threshold = property;
			}
		}
		catch (ConversionException e) {
			e.printStackTrace();
		}
		return threshold;
	}
}
