package org.sonar.plugins.cxx.cccc.domain;

public class CcccProjectSummary {
	
	private CcccMetric modules = new CcccMetric("number_of_modules");
	private CcccMetric linesOfCode = new CcccMetric("lines_of_code");
	private CcccMetric linesOfCodePerModule= new CcccMetric("lines_of_code_per_module");
	private CcccMetric complexity = new CcccMetric("McCabes_cyclomatic_complexity");
	private CcccMetric complexityPerModule = new CcccMetric("McCabes_cyclomatic_complexity_per_module");
	private CcccMetric linesOfComment = new CcccMetric("lines_of_comment");
	private CcccMetric linesOfCommentPerModule = new CcccMetric("lines_of_comment_per_module");
	private CcccMetric linesOfCodePerLineOfComment= new CcccMetric("lines_of_code_per_line_of_comment");
	private CcccMetric complexityPerLineOfComment = new CcccMetric("McCabes_cyclomatic_complexity_per_line_of_comment");
	private CcccMetric if4 = new CcccMetric("IF4");
	private CcccMetric if4PerModule = new CcccMetric("IF4_per_module");
	private CcccMetric if4Visible = new CcccMetric("IF4_visible");
	private CcccMetric if4VisiblePerModule= new CcccMetric("IF4_visible_per_module");
	private CcccMetric if4Concrete = new CcccMetric("IF4_concrete");
	//private CcccMetric IF4_concrete;	// error in cccc.xml twice the same node
	private CcccMetric rejectedLinesOfCode = new CcccMetric("rejected_lines_of_code");
	
	private int numberOfFunctions;
	private double complexityPerFunction;
	private double percentOfComments; 
	private int totalLengthOfFunctions; 
	private double averageLengthOfFunction;
	private int numberOfClass;
	private int numberOfDocumentedAPI;
	private int numberOfUndocumentedAPI;
	private int numberOfPublicMethods;
	private int numberOfPublicAPI;
	private double percentDocumentedAPI; 
	private double complexityPerClass;
	private double maxComplexityOfFunction;
	private double maxComplexityOfClass;
	private double maxNestingLevel;
	private double averageNestingLevel;
	private int totalNestingLevel;
	private double maxFunctionLength;
	private double totalFunctionsInClass;
	private double averageFunctionsPerClass;
	private double maxFunctionsPerClass;
	private double averageIF4;
	private double maxIF4;
	private double averageFanIn;
	private double maxFanIn;
	private double averageFanOut;
	private double maxFanOut;
	private double fanIn;
	private double fanOut;
	private double averageTop5MethodComplexity;
	private double averageTop5MaxNesting;
	private double averageTop5MethodLength;
	private String averageTop5MethodLengthDistrb;
	private String averageTop5MethodComplexityDistrb;
	private String averageTop5MaxNestingDistrb;
	
	public CcccMetric getModules() {
		return modules;
	}
	public void setModules(CcccMetric modules) {
		this.modules = modules;
	}
	
	public CcccMetric getLinesOfCode() {
		return linesOfCode;
	}
	public void setLinesOfCode(CcccMetric linesOfCode) {
		this.linesOfCode = linesOfCode;
	}
	public CcccMetric getLinesOfCodePerModule() {
		return linesOfCodePerModule;
	}
	public void setLinesOfCodePerModule(CcccMetric linesOfCodePerModule) {
		this.linesOfCodePerModule = linesOfCodePerModule;
	}
	public CcccMetric getComplexity() {
		return complexity;
	}
	public void setComplexity(CcccMetric complexity) {
		this.complexity = complexity;
	}
	public CcccMetric getComplexityPerModule() {
		return complexityPerModule;
	}
	public void setComplexityPerModule(CcccMetric complexityPerModule) {
		this.complexityPerModule = complexityPerModule;
	}
	public CcccMetric getLinesOfComment() {
		return linesOfComment;
	}
	public void setLinesOfComment(CcccMetric linesOfComment) {
		this.linesOfComment = linesOfComment;
	}
	public CcccMetric getLinesOfCommentPerModule() {
		return linesOfCommentPerModule;
	}
	public void setLinesOfCommentPerModule(CcccMetric linesOfCommentPerModule) {
		this.linesOfCommentPerModule = linesOfCommentPerModule;
	}
	public CcccMetric getLinesOfCodePerLineOfComment() {
		return linesOfCodePerLineOfComment;
	}
	public void setLinesOfCodePerLineOfComment(
			CcccMetric linesOfCodePerLineOfComment) {
		this.linesOfCodePerLineOfComment = linesOfCodePerLineOfComment;
	}
	public CcccMetric getComplexityPerLineOfComment() {
		return complexityPerLineOfComment;
	}
	public void setComplexityPerLineOfComment(CcccMetric complexityPerLineOfComment) {
		this.complexityPerLineOfComment = complexityPerLineOfComment;
	}
	public CcccMetric getIF4PerModule() {
		return if4PerModule;
	}
	public void setIF4PerModule(CcccMetric iF4PerModule) {
		if4PerModule = iF4PerModule;
	}
	public CcccMetric getIF4Visible() {
		return if4Visible;
	}
	public void setIF4Visible(CcccMetric iF4Visible) {
		if4Visible = iF4Visible;
	}
	public CcccMetric getIF4VisiblePerModule() {
		return if4VisiblePerModule;
	}
	public void setIF4VisiblePerModule(CcccMetric iF4VisiblePerModule) {
		if4VisiblePerModule = iF4VisiblePerModule;
	}
	public CcccMetric getIF4Concrete() {
		return if4Concrete;
	}
	public void setIF4Concrete(CcccMetric iF4Concrete) {
		if4Concrete = iF4Concrete;
	}
	public CcccMetric getRejectedLinesOfCode() {
		return rejectedLinesOfCode;
	}
	public void setRejectedLinesOfCode(CcccMetric rejectedLinesOfCode) {
		this.rejectedLinesOfCode = rejectedLinesOfCode;
	}
	public CcccMetric getIF4() {
		return if4;
	}
	public void setIF4(CcccMetric iF4) {
		if4 = iF4;
	}
	public int getNumberOfFunctions() {
		return numberOfFunctions;
	}
	public void setNumberOfFunctions(int numberOfFunctions) {
		this.numberOfFunctions = numberOfFunctions;
	}
	public double getPercentOfComments() {
		return percentOfComments;
	}
	public void setPercentOfComments(double percentOfComments) {
		this.percentOfComments = percentOfComments;
	}
	public double getComplexityPerFunction() {
		return complexityPerFunction;
	}
	public void setComplexityPerFunction(double complexityPerFunction) {
		this.complexityPerFunction = complexityPerFunction;
	}

	public int getTotalLengthOfFunctions() {
		return totalLengthOfFunctions;
	}
	public void setTotalLengthOfFunctions(int totalLengthOfFunctions) {
		this.totalLengthOfFunctions = totalLengthOfFunctions;
	}
	public double getAverageLengthOfFunction() {
		return averageLengthOfFunction;
	}
	public void setAverageLengthOfFunction(double averageLengthOfFunction) {
		this.averageLengthOfFunction = averageLengthOfFunction;
	}

	public int getNumberOfDocumentedAPI() {
		return numberOfDocumentedAPI;
	}
	public void setNumberOfDocumentedAPI(int numberOfDocumentedAPI) {
		this.numberOfDocumentedAPI = numberOfDocumentedAPI;
	}
	
	public int getNumberOfUndocumentedAPI() {
		return numberOfUndocumentedAPI;
	}
	public void setNumberOfUndocumentedAPI(int numberOfUndocumentedAPI) {
		this.numberOfUndocumentedAPI = numberOfUndocumentedAPI;
	}
	public int getNumberOfPublicMethods() {
		return numberOfPublicMethods;
	}
	public void setNumberOfPublicMethods(int numberOfPublicMethods) {
		this.numberOfPublicMethods = numberOfPublicMethods;
	}
	public int getNumberOfPublicAPI() {
		return numberOfPublicAPI;
	}
	public void setNumberOfPublicAPI(int numberOfPublicAPI) {
		this.numberOfPublicAPI = numberOfPublicAPI;
	}
	public double getPercentDocumentedAPI() {
		return percentDocumentedAPI;
	}
	public void setPercentDocumentedAPI(double percentDocumentedAPI) {
		this.percentDocumentedAPI = percentDocumentedAPI;
	}
	public int getNumberOfClass() {
		return numberOfClass;
	}
	public void setNumberOfClass(int numberOfClass) {
		this.numberOfClass = numberOfClass;
	}
	public double getComplexityPerClass() {
		return complexityPerClass;
	}
	public void setComplexityPerClass(double complexityPerClass) {
		this.complexityPerClass = complexityPerClass;
	}
	public double getMaxComplexityOfFunction() {
		return maxComplexityOfFunction;
	}
	public void setMaxComplexityOfFunction(double maxComplexityOfFunction) {
		this.maxComplexityOfFunction = maxComplexityOfFunction;
	}
	public double getMaxComplexityOfClass() {
		return maxComplexityOfClass;
	}
	public void setMaxComplexityOfClass(double maxComplexityOfClass) {
		this.maxComplexityOfClass = maxComplexityOfClass;
	}
	public double getMaxNestingLevel() {
		return maxNestingLevel;
	}
	public void setMaxNestingLevel(double maxNestingLevel) {
		this.maxNestingLevel = maxNestingLevel;
	}
	public double getAverageNestingLevel() {
		return averageNestingLevel;
	}
	public void setAverageNestingLevel(double averageNestingLevel) {
		this.averageNestingLevel = averageNestingLevel;
	}
	public int getTotalNestingLevel() {
		return totalNestingLevel;
	}
	public void setTotalNestingLevel(int totalNestingLevel) {
		this.totalNestingLevel = totalNestingLevel;
	}
	public double getMaxFunctionLength() {
		return maxFunctionLength;
	}
	public void setMaxFunctionLength(double maxFunctionLength) {
		this.maxFunctionLength = maxFunctionLength;
	}
	public double getAverageFunctionsPerClass() {
		return averageFunctionsPerClass;
	}
	public void setAverageFunctionsPerClass(double averageFunctionsPerClass) {
		this.averageFunctionsPerClass = averageFunctionsPerClass;
	}
	public double getMaxFunctionsPerClass() {
		return maxFunctionsPerClass;
	}
	public void setMaxFunctionsPerClass(double maxFunctionsPerClass) {
		this.maxFunctionsPerClass = maxFunctionsPerClass;
	}
	public double getTotalFunctionsInClass() {
		return totalFunctionsInClass;
	}
	public void setTotalFunctionsInClass(double totalFunctionsInClass) {
		this.totalFunctionsInClass = totalFunctionsInClass;
	}
	public double getAverageIF4() {
		return averageIF4;
	}
	public void setAverageIF4(double averageIF4) {
		this.averageIF4 = averageIF4;
	}
	public double getMaxIF4() {
		return maxIF4;
	}
	public void setMaxIF4(double maxIF4) {
		this.maxIF4 = maxIF4;
	}
	public double getAverageFanIn() {
		return averageFanIn;
	}
	public void setAverageFanIn(double averageFanIn) {
		this.averageFanIn = averageFanIn;
	}
	public double getMaxFanIn() {
		return maxFanIn;
	}
	public void setMaxFanIn(double maxFanIn) {
		this.maxFanIn = maxFanIn;
	}
	public double getAverageFanOut() {
		return averageFanOut;
	}
	public void setAverageFanOut(double averageFanOut) {
		this.averageFanOut = averageFanOut;
	}
	public double getMaxFanOut() {
		return maxFanOut;
	}
	public void setMaxFanOut(double maxFanOut) {
		this.maxFanOut = maxFanOut;
	}
	public double getFanIn() {
		return fanIn;
	}
	public void setFanIn(double fanIn) {
		this.fanIn = fanIn;
	}
	public double getFanOut() {
		return fanOut;
	}
	public void setFanOut(double fanOut) {
		this.fanOut = fanOut;
	}
	public double getAverageTop5MethodComplexity() {
		return averageTop5MethodComplexity;
	}
	public void setAverageTop5MethodComplexity(double averageTop5MethodComplexity) {
		this.averageTop5MethodComplexity = averageTop5MethodComplexity;
	}
	public double getAverageTop5MaxNesting() {
		return averageTop5MaxNesting;
	}
	public void setAverageTop5MaxNesting(double averageTop5MaxNesting) {
		this.averageTop5MaxNesting = averageTop5MaxNesting;
	}
	public double getAverageTop5MethodLength() {
		return averageTop5MethodLength;
	}
	public void setAverageTop5MethodLength(double averageTop5MethodLength) {
		this.averageTop5MethodLength = averageTop5MethodLength;
	}
	public String getAverageTop5MethodLengthDistrb() {
		return averageTop5MethodLengthDistrb;
	}
	public void setAverageTop5MethodLengthDistrb(
			String averageTop5MethodLengthDistrb) {
		this.averageTop5MethodLengthDistrb = averageTop5MethodLengthDistrb;
	}
	public String getAverageTop5MethodComplexityDistrb() {
		return averageTop5MethodComplexityDistrb;
	}
	public void setAverageTop5MethodComplexityDistrb(
			String averageTop5MethodComplexityDistrb) {
		this.averageTop5MethodComplexityDistrb = averageTop5MethodComplexityDistrb;
	}
	public String getAverageTop5MaxNestingDistrb() {
		return averageTop5MaxNestingDistrb;
	}
	public void setAverageTop5MaxNestingDistrb(String averageTop5MaxNestingDistrb) {
		this.averageTop5MaxNestingDistrb = averageTop5MaxNestingDistrb;
	}
}
