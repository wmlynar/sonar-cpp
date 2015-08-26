package org.sonar.plugins.cxx.cccc.domain;

public class CcccFunction {
	
	private CcccMetric modules = new CcccMetric("number_of_modules");
	private CcccMetric linesOfCode = new CcccMetric("lines_of_code");
	private CcccMetric complexity = new CcccMetric("McCabes_cyclomatic_complexity");
	private CcccMetric linesOfComment = new CcccMetric("lines_of_comment");
	private CcccMetric linesOfCodePerLineOfComment= new CcccMetric("lines_of_code_per_line_of_comment");
	private CcccMetric complexityPerLineOfComment = new CcccMetric("McCabes_cyclomatic_complexity_per_line_of_comment");
	private CcccMetric linesOfJavadoc = new CcccMetric("lines_of_javadoc");
	private CcccMetric visibility = new CcccMetric("visibility");
	private CcccMetric nocccc = new CcccMetric("nocccc");
	private CcccMetric maxNesting = new CcccMetric("max_nesting");
	private CcccMetric lines = new CcccMetric("lines");
	
	private String name;
	private String file;
	private int line;
		
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
	
	public CcccMetric getVisibility() {
		return visibility;
	}
	public void setVisibility(CcccMetric visibility) {
		this.visibility = visibility;
	}
	public CcccMetric getMaxNesting() {
		return maxNesting;
	}
	public void setMaxNesting(CcccMetric maxNesting) {
		this.maxNesting = maxNesting;
	}
	public void setNocccc(CcccMetric nocccc) {
		this.nocccc = nocccc;
	}
	public CcccMetric getNocccc() {
		return nocccc;
	}
	
	public CcccMetric getLines() {
		return lines;
	}
	public void setLines(CcccMetric lines) {
		this.lines = lines;
	}
	public boolean isDocumented() {
		if (linesOfJavadoc.getValue() > 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isPublic() {
		if (visibility.getValue() == 2) {
			return true;
		}
		else {
			return false;
		}
	}

}
