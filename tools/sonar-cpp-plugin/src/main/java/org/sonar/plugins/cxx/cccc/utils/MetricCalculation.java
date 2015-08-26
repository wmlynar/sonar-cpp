package org.sonar.plugins.cxx.cccc.utils;

import org.sonar.plugins.cxx.cccc.domain.CcccFunction;
import org.sonar.plugins.cxx.cccc.domain.CcccModule;
import org.sonar.plugins.cxx.cccc.domain.CcccProject;
import org.sonar.plugins.cxx.cccc.domain.CcccProjectSummary;

public class MetricCalculation {

	private Top5PercentCalculator top5MethodComplexity = new Top5PercentCalculator();
	private Top5PercentCalculator top5MaxNesing = new Top5PercentCalculator();
	private Top5PercentCalculator top5MethodLength = new Top5PercentCalculator();
	
	public void computeMetrics(CcccProject model)
	{
		int totalComplexity = 0;
		int totalNumberOfFunctions = 0;
		int totalProjectLengthOfFunctions = 0; 
		int numberOfDocumentedClass = 0;
		int numberOfDocumentedAPI = 0;
		int totalNumberOfPublicMethods = 0;
		int numberOfClass = 0;
		double maxComplexityOfFunction = 0.0;
		double maxComplexityOfClass = 0.0;
		double maxNestingLevel = 0.0;
		int totalNestingLevel = 0;
		double maxFunctionLength = 0;
		double totalFunctionInClass = 0;
		double maxFunctionInClass = 0;
		double maxIF4 = 0;
		double maxFanIn = 0;
		double totalFanIn = 0;
		double maxFanOut = 0;
		double totalFanOut = 0;
		
		for (CcccModule module : model.getModules())
		{
			computeFunctionsMetrics(module);
			
			totalProjectLengthOfFunctions += module.getTotalLengthOfFunctions();
			totalNumberOfFunctions += module.getNumberOfFunctions();
			
			double averageLengthOfFunction = 0.0;
			double percentOfComments = 0.0;
			double percentDocumentedAPI = 0.0;
			double averageNestingLevel = 0.0;
			
			if (module.getNumberOfFunctions() > 0) {
				averageLengthOfFunction = 1.0 * module.getTotalLengthOfFunctions() / module.getNumberOfFunctions();
				averageNestingLevel = 1.0 * module.getTotalNestingLevel() / module.getNumberOfFunctions();
			}
						
			if (module.getLinesOfCode().getValue() > 0) {
				percentOfComments = (1.0 * module.getLinesOfComment().getValue() / module.getLinesOfCode().getValue()) * 100;
			}
			module.setAverageLengthOfFunction(averageLengthOfFunction);
			module.setPercentOfComments(percentOfComments);
			module.setAverageNestingLevel(averageNestingLevel);

			double complexityPerMethod = 0.0;
			if (module.getNumberOfFunctions() > 0) {
				complexityPerMethod = 1.0 * module.getComplexity().getValue() / module.getNumberOfFunctions();
			}
			module.getComplexityPerMemberFunction().setValue(complexityPerMethod);
			
			double complexityPerLineOfComment = 0.0;
			if (module.getLinesOfComment().getValue() > 0) {
				complexityPerLineOfComment = 1.0 * module.getComplexity().getValue() / module.getLinesOfComment().getValue();
			}
			module.getComplexityPerLineOfComment().setValue(complexityPerLineOfComment);
			if (module.getNumberOfPublicMethods() > 0) {
				percentDocumentedAPI = (1.0 * module.getNumberOfPublicDocumentedMethods() / module.getNumberOfPublicMethods()) * 100;
			}
			module.setPercentDocumentedAPI(percentDocumentedAPI);
			
			if (module.isClass() || module.getName().equals(CcccModule.ANONYMOUS))
			{
				totalComplexity += module.getComplexity().getValue();
				
				if (module.isClass()){
					numberOfClass++;
					if (module.isDocumented()) {
						numberOfDocumentedClass++;
					}
					totalFunctionInClass += module.getNumberOfFunctions();
					if (module.getNumberOfFunctions() > maxFunctionInClass) {
						maxFunctionInClass = module.getNumberOfFunctions();
					}
					// max complexity of class
					if (module.getComplexity().getValue() > maxComplexityOfClass) {
						maxComplexityOfClass = module.getComplexity().getValue();
					}
				}
				
				totalFanIn += module.getFanIn().getValue();
				totalFanOut += module.getFanOut().getValue();
				
				if (module.getIF4().getValue() > maxIF4) {
					maxIF4 = module.getIF4().getValue();
				}
				if (module.getFanIn().getValue() > maxFanIn) {
					maxFanIn = module.getFanIn().getValue();
				}
				if (module.getFanOut().getValue() > maxFanOut) {
					maxFanOut = module.getFanOut().getValue();
				}
				// max complexity of method
				if (module.getMaxComplexity() > maxComplexityOfFunction) {
					maxComplexityOfFunction = module.getMaxComplexity();
				}
				// max nesting level
				if (module.getMaxNestingLevel() > maxNestingLevel) {
					maxNestingLevel = module.getMaxNestingLevel();
				}
				// max function length
				if (module.getMaxLengthOfFunctions() > maxFunctionLength) {
					maxFunctionLength = module.getMaxLengthOfFunctions();
			}
		    
			}
			// saving computed metrics 
			numberOfDocumentedAPI += module.getNumberOfPublicDocumentedMethods();
			totalNumberOfPublicMethods += module.getNumberOfPublicMethods();
			totalNestingLevel += module.getTotalNestingLevel();
		}
		CcccProjectSummary summary = model.getSummary();
		summary.getComplexity().setValue(totalComplexity);
		summary.setNumberOfFunctions(totalNumberOfFunctions);
		summary.setTotalLengthOfFunctions(totalProjectLengthOfFunctions);
		summary.setNumberOfDocumentedAPI(numberOfDocumentedAPI + numberOfDocumentedClass);
		summary.setNumberOfPublicMethods(totalNumberOfPublicMethods);
		summary.setNumberOfClass(numberOfClass);
		summary.setMaxComplexityOfFunction(maxComplexityOfFunction);
		summary.setMaxComplexityOfClass(maxComplexityOfClass);
		summary.setMaxNestingLevel(maxNestingLevel);
		summary.setTotalNestingLevel(totalNestingLevel);
		summary.setMaxFunctionLength(maxFunctionLength);
		summary.setTotalFunctionsInClass(totalFunctionInClass);
		summary.setMaxFunctionsPerClass(maxFunctionInClass);
		summary.setMaxIF4(maxIF4);
		summary.setMaxFanIn(maxFanIn);
		summary.setMaxFanOut(maxFanOut);
		summary.setFanIn(totalFanIn);
		summary.setFanOut(totalFanOut);
		
		computeProjectMetrics(summary);
	}

	private void computeProjectMetrics(CcccProjectSummary summary) {
		double complexityPerMethod = 0.0;
		double complexityPerLineOfComment = 0.0;
		double complexityPerModule = 0.0;
		double complexityPerClass = 0.0;
		double percentOfComments = 0.0;
		double averageLengthOfFunction = 0.0;
		double percentDocumentedAPI = 0.0; 
		double averageNestingLevel = 0.0;
		
		if (summary.getNumberOfFunctions() > 0)	{
			complexityPerMethod = 1.0 * summary.getComplexity().getValue() / summary.getNumberOfFunctions();
			averageLengthOfFunction = 1.0 * summary.getTotalLengthOfFunctions() / summary.getNumberOfFunctions();
			averageNestingLevel = 1.0 * summary.getTotalNestingLevel() / summary.getNumberOfFunctions();
		}
		
		if (summary.getLinesOfCode().getValue() > 0) {
			percentOfComments = (1.0 * summary.getLinesOfComment().getValue() / summary.getLinesOfCode().getValue()) * 100;
		}
		
		int totalAPI = summary.getNumberOfPublicMethods() + summary.getNumberOfClass();
		if (totalAPI > 0) {
			percentDocumentedAPI = (1.0 * summary.getNumberOfDocumentedAPI() / totalAPI) * 100;
		}
		
		if (summary.getModules().getValue() > 0) {
			complexityPerModule = 1.0 * summary.getComplexity().getValue() / summary.getModules().getValue();
		}
		
		if (summary.getNumberOfClass() > 0)	{
			complexityPerClass = 1.0 * summary.getComplexity().getValue() / summary.getNumberOfClass();
			summary.setAverageFunctionsPerClass(1.0 * summary.getTotalFunctionsInClass() / summary.getNumberOfClass());
			summary.setAverageIF4(1.0 * summary.getIF4().getValue() / summary.getNumberOfClass());
			summary.setAverageFanIn(1.0 * summary.getFanIn() / summary.getNumberOfClass());
			summary.setAverageFanOut(1.0 * summary.getFanOut() / summary.getNumberOfClass());
		}
		
		if (summary.getLinesOfComment().getValue() > 0)	{
			complexityPerLineOfComment = 1.0 * summary.getComplexity().getValue() / summary.getLinesOfComment().getValue();
		}
		
		summary.setNumberOfUndocumentedAPI(totalAPI - summary.getNumberOfDocumentedAPI());
		summary.setComplexityPerFunction(complexityPerMethod);
		summary.getComplexityPerLineOfComment().setValue(complexityPerLineOfComment);
		summary.getComplexityPerModule().setValue(complexityPerModule);
		summary.setComplexityPerClass(complexityPerClass);
		summary.setPercentOfComments(percentOfComments);
		summary.setAverageLengthOfFunction(averageLengthOfFunction);
		summary.setPercentDocumentedAPI(percentDocumentedAPI);
		summary.setAverageNestingLevel(averageNestingLevel);
		summary.setNumberOfPublicAPI(totalAPI);
		summary.setAverageTop5MethodComplexity(top5MethodComplexity.getTop5Average());
		summary.setAverageTop5MaxNesting(top5MaxNesing.getTop5Average());
		summary.setAverageTop5MethodLength(top5MethodLength.getTop5Average());
		summary.setAverageTop5MethodLengthDistrb(top5MethodLength.build());
		summary.setAverageTop5MaxNestingDistrb(top5MaxNesing.build());
		summary.setAverageTop5MethodComplexityDistrb(top5MethodComplexity.build());
	}

	private void computeFunctionsMetrics(CcccModule module) {
		int complexity = 0;
		int numberOfFunctions = 0;
		int numberOfPublicMethods = 0;
		int numberOfPublicDocumentedMethos = 0;
		
		int totalLengthOfFunctions = 0;
		double maxComplexity = 0.0;
		double maxNesting = 0.0;
		int totalNesting = 0;
		double maxLengthOfFunction = 0;
		
		for (CcccFunction function : module.getFunctions()) {
			// default complexity should be 1
			function.getComplexity().setValue(function.getComplexity().getValue() + 1);
			// total Length Of Functions
			totalLengthOfFunctions += function.getLinesOfCode().getValue();
			totalNesting += function.getMaxNesting().getValue();
			// max complexity
			if (function.getComplexity().getValue() > maxComplexity) {
				maxComplexity = function.getComplexity().getValue();
			}
			// max nesting level
			if (function.getMaxNesting().getValue() > maxNesting) {
				maxNesting = function.getMaxNesting().getValue();
			}
			// max funnction length
			if (function.getLinesOfCode().getValue() > maxLengthOfFunction)	{
				maxLengthOfFunction = function.getLinesOfCode().getValue();
			}
			numberOfFunctions++;
			complexity += function.getComplexity().getValue();
		    if (function.isPublic()) {
		    	numberOfPublicMethods++;
		    	if (function.isDocumented()) {
		    		numberOfPublicDocumentedMethos++;
		    	}
		    }
		    
		    if (function.getLinesOfComment().getValue() != 0) {
		    	function.getComplexityPerLineOfComment().setValue(function.getComplexity().getValue() / function.getLinesOfComment().getValue());
		    }
		    
		    top5MethodComplexity.add(function.getComplexity().getValue());
		    top5MaxNesing.add(function.getMaxNesting().getValue());
		    top5MethodLength.add(function.getLinesOfCode().getValue());
		}
		module.getComplexity().setValue(complexity);
		module.setNumberOfFunctions(numberOfFunctions);
		module.setNumberOfPublicMethods(numberOfPublicMethods);
		module.setNumberOfPublicDocumentedMethods(numberOfPublicDocumentedMethos);
		module.setTotalLengthOfFunctions(totalLengthOfFunctions);
		module.setMaxComplexity(maxComplexity);
		module.setMaxNestingLevel(maxNesting);
		module.setTotalNestingLevel(totalNesting);
		module.setMaxLengthOfFunctions(maxLengthOfFunction);
	}
	
}
