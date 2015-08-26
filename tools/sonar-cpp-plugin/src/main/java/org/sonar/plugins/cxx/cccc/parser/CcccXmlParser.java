package org.sonar.plugins.cxx.cccc.parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.plugins.cxx.cccc.domain.CcccFunction;
import org.sonar.plugins.cxx.cccc.domain.CcccMetric;
import org.sonar.plugins.cxx.cccc.domain.CcccModule;
import org.sonar.plugins.cxx.cccc.domain.CcccProject;
import org.sonar.plugins.cxx.cccc.domain.CcccProjectSummary;
import org.sonar.plugins.cxx.cccc.utils.FileUtils;
import org.sonar.plugins.cxx.cccc.utils.MetricCalculation;
import org.sonar.plugins.cxx.cccc.violation.CcccViolation;


public class CcccXmlParser {
	
	public static final String CCCC_FILE_NAME="cccc.xml";
	public static final String DEFAULT_OUTDIR = ".cccc";
	public static final String DECLARATION = "declaration";
	public static final String DEFINITION = "definition";
	public static final String PROJECT_SUMMARY = "project_summary";
	public static final String PROCEDURAL_SUMMARY = "procedural_summary";
	public static final String MODULE = "module";
	public static final String OO_DESIGN = "oo_design";
	public static final String STRUCTURAL_SUMMARY = "structural_summary";
	public static final String NAME = "name";
	public static final String FILE = "file";
	public static final String LINE = "line";
	public static final String MODULE_SUMMARY = "module_summary";
	public static final String MODULE_DETAIL = "module_detail";
	public static final String SOURCE_REFERENCE = "source_reference";
	public static final String MEMBER_FUNCTION = "member_function";
	public static final String PROCEDURAL_DETAIL = "procedural_detail";
	public static final String DESCRIPTION = "description";

	
	
	
	public static CcccProject parseCcccProject(final List<File> sources, String outputDirectory) throws Exception {
		CcccProject model = null;
		if (sources.size() > 0) {
			String cmd = FileUtils.getCommandString(sources, outputDirectory);
			FileUtils.executeCommand(cmd);
			CcccXmlParser parser = new CcccXmlParser();
			model = parser.parseProject(outputDirectory);
		}
		return model;
	}
	
    public CcccProject parseProject(String outputDirectory) throws JDOMException, IOException  {
    	if (outputDirectory == null) {
    		outputDirectory = DEFAULT_OUTDIR;
    	}
		File file = new File(outputDirectory + "/" + CCCC_FILE_NAME);
		if (file.exists())
		{
			CcccProject project = new CcccProject();
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document)builder.build(file);
			Element root = document.getRootElement();
			
			project.setSummary(parseProjectSummary(root));
			parseCcccModules(project, root);
			parseModuleFromFiles(project, outputDirectory);
			MetricCalculation metricCalculation = new MetricCalculation(); 
			metricCalculation.computeMetrics(project);
			return project;
		}
		else {
			throw new FileNotFoundException("File " + CCCC_FILE_NAME + " not found!");
		}
    }

	public static void collectViolations(CcccProject model) {
		CcccViolationsParser violationsParser = new CcccViolationsParser();
		List<CcccViolation> violations = violationsParser.parseModel(model);
		model.setViolations(violations);
	}
	
	public static void collectViolations(CcccProject model, Project project) {
		CcccViolationsParser violationsParser = new CcccViolationsParser();
		violationsParser.getConfigurationProperties(project);
		List<CcccViolation> violations = violationsParser.parseModel(model);
		model.setViolations(violations);
	}
	
    
    private void parseMetric(CcccMetric metric, Element element) throws JDOMException {
    	List<Element> children = element.getChildren(metric.getTag());
    	if (children.size() == 0) {
    		throw new JDOMException("There is no element " + metric.getTag());
    	} else if (children.size() == 1) {
    		metric.setValue(safeParseFloat(children.get(0).getAttribute("value").getValue()));
    		metric.setLevel(safeParseInt(children.get(0).getAttributeValue("level")));
    	} else {
    		for (Element child : children) {
    			metric.addValue(safeParseFloat(child.getAttribute("value").getValue()));
    		}
    	}
    }
    
    private CcccProjectSummary parseProjectSummary(Element root) throws JDOMException {
    	Element summary = root.getChild(PROJECT_SUMMARY);
		CcccProjectSummary projectSummary = new CcccProjectSummary();
		
		parseMetric(projectSummary.getModules(), summary);
		parseMetric(projectSummary.getLinesOfCode(), summary);
		parseMetric(projectSummary.getLinesOfCodePerModule(), summary);
		parseMetric(projectSummary.getComplexity(), summary);
		parseMetric(projectSummary.getComplexityPerModule(), summary);
		parseMetric(projectSummary.getLinesOfComment(), summary);
		parseMetric(projectSummary.getLinesOfCodePerLineOfComment(), summary);
		parseMetric(projectSummary.getComplexityPerLineOfComment(), summary);
		parseMetric(projectSummary.getIF4(), summary);
		parseMetric(projectSummary.getIF4PerModule(), summary);
		parseMetric(projectSummary.getIF4Visible(), summary);
		parseMetric(projectSummary.getIF4VisiblePerModule(), summary);
		//parseMetric(projectSummary.getIF4Concrete(), summary);
		parseMetric(projectSummary.getRejectedLinesOfCode(), summary);
		
		return projectSummary;
    }
    
    private List<CcccModule> parseCcccModules(CcccProject project, Element root) throws JDOMException  {
    	List<CcccModule> modules = new ArrayList<CcccModule>();
    	Element proceduralSummary = root.getChild(PROCEDURAL_SUMMARY);
    	parseProceduralSummary(project, proceduralSummary);
    	Element ooDesign = root.getChild(OO_DESIGN);
    	parseOODesign(project, ooDesign);
    	Element structuralSummary = root.getChild(STRUCTURAL_SUMMARY);
    	parseStructuralSummary(project, structuralSummary);
    	return modules;
    }
    
    private void parseProceduralSummary(CcccProject project, Element proceduralSummary) throws JDOMException {
    	List<Element> modulesList = proceduralSummary.getChildren(MODULE);
    	for (Element moduleElemnt : modulesList) {
    		CcccModule module = new CcccModule();
    		String name = moduleElemnt.getChildText(NAME);
    		module.setName(name);
    		parseMetric(module.getLinesOfCode(), moduleElemnt);
    		parseMetric(module.getComplexity(), moduleElemnt);
    		parseMetric(module.getLinesOfComment(), moduleElemnt);
    		parseMetric(module.getLinesOfCodePerLineOfComment(), moduleElemnt);
    		parseMetric(module.getComplexityPerLineOfComment(), moduleElemnt);
    		parseMetric(module.getLinesOfJavadoc(), moduleElemnt);
    		parseMetric(module.getNocccc(), moduleElemnt);
    		parseMetric(module.getMaxNesting(), moduleElemnt);
    		project.add(module);
    	}
    }
    
    private void parseOODesign(CcccProject project, Element ooDesign) throws JDOMException {
    	List<Element> modulesList = ooDesign.getChildren(MODULE);
    	
    	for (Element moduleElemnt : modulesList) {
    		String name = moduleElemnt.getChildText(NAME);
    		CcccModule module = project.getModuleByName(name);
    		parseMetric(module.getWeightedMethodsPerClassUnity(), moduleElemnt);
    		parseMetric(module.getWeightedMethodsPerClassVisibility(), moduleElemnt);
    		parseMetric(module.getDepthOfInheritanceTree(), moduleElemnt);
    		parseMetric(module.getNumberOfChildren(), moduleElemnt);
    		parseMetric(module.getCouplingBetweenObjects(), moduleElemnt);
    	}
    }
    
    private void parseStructuralSummary(CcccProject project, Element structuralSummary) throws NoSuchElementException, JDOMException {
    	List<Element> modulesList = structuralSummary.getChildren(MODULE);
    	
    	for (Element moduleElemnt : modulesList) {
    		String name = moduleElemnt.getChildText(NAME);
    		CcccModule module = project.getModuleByName(name);
    		parseMetric(module.getFanOut(), moduleElemnt);
    		parseMetric(module.getFanOutVisible(), moduleElemnt);
    		parseMetric(module.getFanOutConcrete(), moduleElemnt);
    		parseMetric(module.getFanIn(), moduleElemnt);
    		parseMetric(module.getFanInVisible(), moduleElemnt);
    		parseMetric(module.getFanInConcrete(), moduleElemnt);
    		parseMetric(module.getIF4(), moduleElemnt);
    		parseMetric(module.getIF4Visible(), moduleElemnt);
    		parseMetric(module.getIF4Concrete(), moduleElemnt);
    	}
    }
    
    private void parseModuleFromFiles(CcccProject project, String outputDirectory) throws JDOMException, IOException {
    	for (CcccModule module : project.getModules()) {
    		String fileName = outputDirectory + "/" + module.getName() + ".xml";
			File file = new File(fileName);
			parseModuleFromFile(module, file);
    	}
    }
    
    private void parseModuleFromFile(CcccModule module, File file) throws JDOMException, IOException {
    	SAXBuilder builder = new SAXBuilder();
		org.jdom.Document document = (org.jdom.Document)builder.build(file);
		Element root = document.getRootElement();
		pareseModuleSummary(module, root);
		pareseModuleDetail(module, root);
		Element proceduralDetail = root.getChild(PROCEDURAL_DETAIL);
		parseMemberFunction(module, proceduralDetail);
    }
    
    private void pareseModuleSummary(CcccModule module, Element root) throws JDOMException {
    	Element summary = root.getChild(MODULE_SUMMARY);

    	//parseMetric(module.getLinesOfCodePerMemberFunction(), summary);
		parseMetric(module.getComplexityPerMemberFunction(), summary);
		parseMetric(module.getIF4PerMemberFunction(), summary);
		parseMetric(module.getIF4VisiblePerMemberFunction(), summary);
		parseMetric(module.getIF4ConcretePerMemberFunction(), summary);
    }
    
    private void pareseModuleDetail(CcccModule module, Element root) throws JDOMException {
    	Element summary = root.getChild(MODULE_DETAIL);
    	if (summary.getChildren().size() > 0) {
	    	parseModuleDetailSource(module, summary);
	    	
	    	parseMetric(module.getModuleLinesOfCode(), summary);
			parseMetric(module.getModuleComplexity(), summary);
			parseMetric(module.getModuleLinesOfComment(), summary);
			parseMetric(module.getModuleLinesOfCodePerLineOfComment(), summary);
			parseMetric(module.getModuleComplexityPerLineOfComment(), summary);
			parseMetric(module.getModuleLinesOfJavadoc(), summary);
			parseMetric(module.getModuleNocccc(), summary);
			parseMetric(module.getModuleMaxNesting(), summary);
			parseMetric(module.getLines(), summary);
    	}
    }

	private void parseModuleDetailSource(CcccModule module, Element summary) {
		List<Element> description = summary.getChildren(DESCRIPTION);
		List<Element> sourceReference = summary.getChildren(SOURCE_REFERENCE);
		for (int i = 0; i < sourceReference.size(); i++) {
			if (description.get(i).getText().equals(DEFINITION)) {
				String file = sourceReference.get(i).getAttributeValue(FILE);
				String line = sourceReference.get(i).getAttributeValue(LINE);
				module.setFile(file);
				module.setLine(safeParseInt(line));
				module.setClass(true);
			}
		}
	}
    
    private void parseMemberFunction(CcccModule module, Element proceduralDetail) throws JDOMException {
    	List<Element> functionList = proceduralDetail.getChildren(MEMBER_FUNCTION);
    	for (Element functionElement : functionList) {
    		CcccFunction function = new CcccFunction();
    		String name = functionElement.getChildText(NAME);
    		function.setName(name);
    		parseFunctionExtent(function, functionElement);
    		parseMetric(function.getLinesOfCode(), functionElement);
    		parseMetric(function.getComplexity(), functionElement);
    		parseMetric(function.getLinesOfComment(), functionElement);
    		parseMetric(function.getLinesOfCodePerLineOfComment(), functionElement);
    		parseMetric(function.getComplexityPerLineOfComment(), functionElement);
    		parseMetric(function.getLinesOfJavadoc(), functionElement);
    		parseMetric(function.getVisibility(), functionElement);
    		parseMetric(function.getNocccc(), functionElement);
    		parseMetric(function.getMaxNesting(), functionElement);
    		parseMetric(function.getLines(), functionElement);
    		double maxNesting = function.getMaxNesting().getValue();
    		if (maxNesting > 0) {
    			function.getMaxNesting().setValue(maxNesting - 1);
    		}
    		module.addFunction(function);
    	}
    }
    
    /**
	 * Parse extend section in function to get declaration and definition line and file
	 * @param function
	 * @return
	 * @throws  
	 */
	private CcccFunction parseFunctionExtent(CcccFunction function, Element element) throws JDOMException
	{
		// Parse extend section in function to get declaration and definition line and file
		List<Element> extents = element.getChildren("extent");
	    // to check if there is definition element
	    boolean ifDefined = false; 
	    for (Element extent : extents)
	    {
	    	String str;
	    	String desctription = extent.getChildText(DESCRIPTION);
	    	// only if there is no definition element
	    	if (desctription.equals(DECLARATION) && !ifDefined) {
		    	str = extent.getChild(SOURCE_REFERENCE).getAttributeValue(FILE);
			    function.setFile(str);
			    str = extent.getChild(SOURCE_REFERENCE).getAttributeValue(LINE);
			    function.setLine(safeParseInt(str));
	    	}
	    	if (desctription.equals(DEFINITION)) {
	    		str = extent.getChild(SOURCE_REFERENCE).getAttributeValue(FILE);
			    function.setFile(str);
			    str =  extent.getChild(SOURCE_REFERENCE).getAttributeValue(LINE);
			    function.setLine(safeParseInt(str));
			    ifDefined = true;
	    	}
	    }
	    return function;
	}
    
	private float safeParseFloat(String str) {
		try {
			return Float.parseFloat(str);
		} catch (NumberFormatException e) {
			return Float.NaN;
		}
	}
	
	private int safeParseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
