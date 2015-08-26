package org.sonar.plugins.cxx.cccc.domain;

import java.util.ArrayList;
import java.util.List;

public class CcccModule {
	
	public static final String ANONYMOUS = "anonymous";
	
	private List<CcccFunction> functions = new ArrayList<CcccFunction>();
	
	// procedural summary
	private String name;
	private boolean isClass;
	private CcccMetric modules = new CcccMetric("number_of_modules");
	private CcccMetric linesOfCode = new CcccMetric("lines_of_code");
	private CcccMetric complexity = new CcccMetric("McCabes_cyclomatic_complexity");
	private CcccMetric linesOfComment = new CcccMetric("lines_of_comment");
	private CcccMetric linesOfCodePerLineOfComment= new CcccMetric("lines_of_code_per_line_of_comment");
	private CcccMetric complexityPerLineOfComment = new CcccMetric("McCabes_cyclomatic_complexity_per_line_of_comment");
	private CcccMetric linesOfJavadoc = new CcccMetric("lines_of_javadoc");
	private CcccMetric nocccc = new CcccMetric("nocccc");
	private CcccMetric maxNesting = new CcccMetric("max_nesting");
	
	// oo_design

	private CcccMetric weightedMethodsPerClassUnity = new CcccMetric("weighted_methods_per_class_unity");
	private CcccMetric weightedMethodsPerClassVisibility = new CcccMetric("weighted_methods_per_class_visibility");
	private CcccMetric depthOfInheritanceTree = new CcccMetric("depth_of_inheritance_tree");
	private CcccMetric numberOfChildren = new CcccMetric("number_of_children");
	private CcccMetric couplingBetweenObjects = new CcccMetric("coupling_between_objects");
	
	// structural summary

	private CcccMetric fanOutVisible = new CcccMetric("fan_out_visible");
	private CcccMetric fanOutConcrete = new CcccMetric("fan_out_concrete");
	private CcccMetric fanOut = new CcccMetric("fan_out");
	private CcccMetric fanInVisible = new CcccMetric("fan_in_visible");
	private CcccMetric fanInConcrete = new CcccMetric("fan_in_concrete");
	private CcccMetric fanIn = new CcccMetric("fan_in");
	private CcccMetric if4Visible = new CcccMetric("IF4_visible");
	private CcccMetric if4Concrete = new CcccMetric("IF4_concrete");
	private CcccMetric if4 = new CcccMetric("IF4");
	
	// module summary
	private CcccMetric linesOfCodePerMemberFunction = new CcccMetric("lines_of_code_per_member_function");
	private CcccMetric complexityPerMemberFunction = new CcccMetric("McCabes_cyclomatic_complexity_per_member_function");
	private CcccMetric if4PerMemberFunction = new CcccMetric("IF4_per_member_function");
	private CcccMetric if4VisiblePerMemberFunction = new CcccMetric("IF4_visible_per_member_function");
	private CcccMetric if4ConcretePerMemberFunction = new CcccMetric("IF4_visible_per_member_function");
	
	// module detail
	private String file;
	private int line;
	private CcccMetric moduleLinesOfCode = new CcccMetric("lines_of_code");
	private CcccMetric moduleComplexity = new CcccMetric("McCabes_cyclomatic_complexity");
	private CcccMetric moduleLinesOfComment = new CcccMetric("lines_of_comment");
	private CcccMetric moduleLinesOfCodePerLineOfComment= new CcccMetric("lines_of_code_per_line_of_comment");
	private CcccMetric moduleComplexityPerLineOfComment = new CcccMetric("McCabes_cyclomatic_complexity_per_line_of_comment");
	private CcccMetric moduleLinesOfJavadoc = new CcccMetric("lines_of_javadoc");
	private CcccMetric moduleNocccc = new CcccMetric("nocccc");
	private CcccMetric moduleMaxNesting = new CcccMetric("max_nesting");
	private CcccMetric lines = new CcccMetric("lines");

	
	private int numberOfFunctions;
	private int totalLengthOfFunctions; 
	private double averageLengthOfFunction;
	private double percentOfComments; 
	private int numberOfPublicMethods;
	private int numberOfPublicDocumentedMethods;
	private double percentDocumentedAPI; 
	private double maxComplexity;
	private double maxNestingLevel;
	private double averageNestingLevel;
	private int totalNestingLevel;
	private double maxLengthOfFunctions;

	public void setClass(boolean isClass) {
		this.isClass = isClass;
	}

	public void addFunction(CcccFunction function)
	{
		functions.add(function);
	}
	
	public CcccFunction getFunction(int index)
	{
		return functions.get(index);
	}
	// getters / setters
	public List<CcccFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(ArrayList<CcccFunction> functions) {
		this.functions = functions;
	}
	

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

	public CcccMetric getComplexity() {
		return complexity;
	}

	public void setComplexity(CcccMetric complexity) {
		this.complexity = complexity;
	}

	public CcccMetric getLinesOfComment() {
		return linesOfComment;
	}

	public void setLinesOfComment(CcccMetric linesOfComment) {
		this.linesOfComment = linesOfComment;
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

	public CcccMetric getLinesOfJavadoc() {
		return linesOfJavadoc;
	}

	public void setLinesOfJavadoc(CcccMetric linesOfJavadoc) {
		this.linesOfJavadoc = linesOfJavadoc;
	}

	public CcccMetric getNocccc() {
		return nocccc;
	}

	public void setNocccc(CcccMetric nocccc) {
		this.nocccc = nocccc;
	}

	public CcccMetric getMaxNesting() {
		return maxNesting;
	}

	public void setMaxNesting(CcccMetric maxNesting) {
		this.maxNesting = maxNesting;
	}
	
	public CcccMetric getWeightedMethodsPerClassUnity() {
		return weightedMethodsPerClassUnity;
	}

	public void setWeightedMethodsPerClassUnity(
			CcccMetric weightedMethodsPerClassUnity) {
		this.weightedMethodsPerClassUnity = weightedMethodsPerClassUnity;
	}

	public CcccMetric getWeightedMethodsPerClassVisibility() {
		return weightedMethodsPerClassVisibility;
	}

	public void setWeightedMethodsPerClassVisibility(
			CcccMetric weightedMethodsPerClassVisibility) {
		this.weightedMethodsPerClassVisibility = weightedMethodsPerClassVisibility;
	}

	public CcccMetric getDepthOfInheritanceTree() {
		return depthOfInheritanceTree;
	}

	public void setDepthOfInheritanceTree(CcccMetric depthOfInheritanceTree) {
		this.depthOfInheritanceTree = depthOfInheritanceTree;
	}

	public CcccMetric getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(CcccMetric numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public CcccMetric getCouplingBetweenObjects() {
		return couplingBetweenObjects;
	}

	public void setCouplingBetweenObjects(CcccMetric couplingBetweenObjects) {
		this.couplingBetweenObjects = couplingBetweenObjects;
	}

	public CcccMetric getFanOutVisible() {
		return fanOutVisible;
	}

	public void setFanOutVisible(CcccMetric fanOutVisible) {
		this.fanOutVisible = fanOutVisible;
	}

	public CcccMetric getFanOutConcrete() {
		return fanOutConcrete;
	}

	public void setFanOutConcrete(CcccMetric fanOutConcrete) {
		this.fanOutConcrete = fanOutConcrete;
	}

	public CcccMetric getFanOut() {
		return fanOut;
	}

	public void setFanOut(CcccMetric fanOut) {
		this.fanOut = fanOut;
	}

	public CcccMetric getFanInVisible() {
		return fanInVisible;
	}

	public void setFanInVisible(CcccMetric fanInVisible) {
		this.fanInVisible = fanInVisible;
	}

	public CcccMetric getFanInConcrete() {
		return fanInConcrete;
	}

	public void setFanInConcrete(CcccMetric fanInConcrete) {
		this.fanInConcrete = fanInConcrete;
	}

	public CcccMetric getFanIn() {
		return fanIn;
	}

	public void setFanIn(CcccMetric fanIn) {
		this.fanIn = fanIn;
	}

	public CcccMetric getIF4Visible() {
		return if4Visible;
	}

	public void setIF4Visible(CcccMetric iF4Visible) {
		if4Visible = iF4Visible;
	}

	public CcccMetric getIF4Concrete() {
		return if4Concrete;
	}

	public void setIF4Concrete(CcccMetric iF4Concrete) {
		if4Concrete = iF4Concrete;
	}

	public CcccMetric getIF4() {
		return if4;
	}

	public void setIF4(CcccMetric iF4) {
		if4 = iF4;
	}

	public CcccMetric getLinesOfCodePerMemberFunction() {
		return linesOfCodePerMemberFunction;
	}

	public void setLinesOfCodePerMemberFunction(
			CcccMetric linesOfCodePerMemberFunction) {
		this.linesOfCodePerMemberFunction = linesOfCodePerMemberFunction;
	}

	public CcccMetric getComplexityPerMemberFunction() {
		return complexityPerMemberFunction;
	}

	public void setComplexityPerMemberFunction(
			CcccMetric complexityPerMemberFunction) {
		this.complexityPerMemberFunction = complexityPerMemberFunction;
	}

	public CcccMetric getIF4PerMemberFunction() {
		return if4PerMemberFunction;
	}

	public void setIF4PerMemberFunction(CcccMetric iF4PerMemberFunction) {
		if4PerMemberFunction = iF4PerMemberFunction;
	}

	public CcccMetric getIF4VisiblePerMemberFunction() {
		return if4VisiblePerMemberFunction;
	}

	public void setIF4VisiblePerMemberFunction(
			CcccMetric iF4VisiblePerMemberFunction) {
		if4VisiblePerMemberFunction = iF4VisiblePerMemberFunction;
	}

	public CcccMetric getIF4ConcretePerMemberFunction() {
		return if4ConcretePerMemberFunction;
	}

	public void setIF4ConcretePerMemberFunction(
			CcccMetric iF4ConcretePerMemberFunction) {
		if4ConcretePerMemberFunction = iF4ConcretePerMemberFunction;
	}

	
public CcccMetric getModuleLinesOfCode() {
		return moduleLinesOfCode;
	}

	public void setModuleLinesOfCode(CcccMetric moduleLinesOfCode) {
		this.moduleLinesOfCode = moduleLinesOfCode;
	}

	public CcccMetric getModuleComplexity() {
		return moduleComplexity;
	}

	public void setModuleComplexity(CcccMetric moduleComplexity) {
		this.moduleComplexity = moduleComplexity;
	}

	public CcccMetric getModuleLinesOfComment() {
		return moduleLinesOfComment;
	}

	public void setModuleLinesOfComment(CcccMetric moduleLinesOfComment) {
		this.moduleLinesOfComment = moduleLinesOfComment;
	}

	public CcccMetric getModuleLinesOfCodePerLineOfComment() {
		return moduleLinesOfCodePerLineOfComment;
	}

	public void setModuleLinesOfCodePerLineOfComment(
			CcccMetric moduleLinesOfCodePerLineOfComment) {
		this.moduleLinesOfCodePerLineOfComment = moduleLinesOfCodePerLineOfComment;
	}

	public CcccMetric getModuleComplexityPerLineOfComment() {
		return moduleComplexityPerLineOfComment;
	}

	public void setModuleComplexityPerLineOfComment(
			CcccMetric moduleComplexityPerLineOfComment) {
		this.moduleComplexityPerLineOfComment = moduleComplexityPerLineOfComment;
	}

	public CcccMetric getModuleLinesOfJavadoc() {
		return moduleLinesOfJavadoc;
	}

	public void setModuleLinesOfJavadoc(CcccMetric moduleLinesOfJavadoc) {
		this.moduleLinesOfJavadoc = moduleLinesOfJavadoc;
	}

	public CcccMetric getModuleNocccc() {
		return moduleNocccc;
	}

	public void setModuleNocccc(CcccMetric moduleNocccc) {
		this.moduleNocccc = moduleNocccc;
	}

	public CcccMetric getModuleMaxNesting() {
		return moduleMaxNesting;
	}

	public void setModuleMaxNesting(CcccMetric moduleMaxNesting) {
		this.moduleMaxNesting = moduleMaxNesting;
	}

	public CcccMetric getLines() {
		return lines;
	}

	public void setLines(CcccMetric lines) {
		this.lines = lines;
	}

	/*	public int getMax_nesting() {
		return max_nesting;
	}

	public void setMax_nesting(int max_nesting) {
		this.max_nesting = max_nesting;
	}
*/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	

	public int getNumberOfFunctions() {
		return numberOfFunctions;
	}

	public void setNumberOfFunctions(int numberOfFunctions) {
		this.numberOfFunctions = numberOfFunctions;
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

	public double getPercentOfComments() {
		return percentOfComments;
	}

	public void setPercentOfComments(double percentOfComments) {
		this.percentOfComments = percentOfComments;
	}

	public int getNumberOfPublicMethods() {
		return numberOfPublicMethods;
	}

	public void setNumberOfPublicMethods(int numberOfPublicMethods) {
		this.numberOfPublicMethods = numberOfPublicMethods;
	}

	public int getNumberOfPublicDocumentedMethods() {
		return numberOfPublicDocumentedMethods;
	}

	public void setNumberOfPublicDocumentedMethods(
			int numberOfPublicDocumentedMethods) {
		this.numberOfPublicDocumentedMethods = numberOfPublicDocumentedMethods;
	}

	public double getPercentDocumentedAPI() {
		return percentDocumentedAPI;
	}

	public void setPercentDocumentedAPI(double percentDocumentedAPI) {
		this.percentDocumentedAPI = percentDocumentedAPI;
	}

	public double getMaxComplexity() {
		return maxComplexity;
	}

	public void setMaxComplexity(double maxComplexity) {
		this.maxComplexity = maxComplexity;
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
	
	public double getMaxLengthOfFunctions() {
		return maxLengthOfFunctions;
	}

	public void setMaxLengthOfFunctions(double maxLengthOfFunctions) {
		this.maxLengthOfFunctions = maxLengthOfFunctions;
	}

	public boolean isDocumented()
	{
		if (linesOfJavadoc.getValue() > 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isClass()
	{
		if (isClass == false) {
			return false;
		}
		else {
			return true;
		}
	}
	
}
