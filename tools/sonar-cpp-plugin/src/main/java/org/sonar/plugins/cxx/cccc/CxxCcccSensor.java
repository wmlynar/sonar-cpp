package org.sonar.plugins.cxx.cccc;

import java.io.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.PersistenceMode;
import org.sonar.api.measures.RangeDistributionBuilder;
import org.sonar.api.resources.Project;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.rules.Violation;
import org.sonar.api.utils.SonarException;
import org.sonar.plugins.cxx.CxxClass;
import org.sonar.plugins.cxx.CxxFile;
import org.sonar.plugins.cxx.CxxFunction;
import org.sonar.plugins.cxx.CxxLanguage;
import org.sonar.plugins.cxx.CxxPlugin;
import org.sonar.plugins.cxx.cccc.decorators.CxxClassComplexityDistributionBuilder;
import org.sonar.plugins.cxx.cccc.decorators.CxxFunctionComplexityDistributionBuilder;
import org.sonar.plugins.cxx.cccc.domain.CcccFunction;
import org.sonar.plugins.cxx.cccc.domain.CcccModule;
import org.sonar.plugins.cxx.cccc.domain.CcccProject;
import org.sonar.plugins.cxx.cccc.domain.CcccProjectSummary;
import org.sonar.plugins.cxx.cccc.parser.CcccXmlParser;
import org.sonar.plugins.cxx.cccc.utils.FileUtils;
import org.sonar.plugins.cxx.cccc.violation.CcccViolation;
import org.sonar.plugins.cxx.cccc.violation.CcccFileViolation;

public class CxxCcccSensor implements Sensor{

	public static final String ANONYMOUS = "anonymous";
	
	private RuleFinder ruleFinder;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String outDir = ".cccc";
	
	public CxxCcccSensor(RuleFinder ruleFinder) {
		this.ruleFinder = ruleFinder;
	}

	public boolean shouldExecuteOnProject(Project project) {
		return CxxPlugin.KEY.equals(project.getLanguageKey());
	}

	public void analyse(Project project, SensorContext context) {
		
		final List<File> sources = project.getFileSystem().getSourceFiles(CxxLanguage.INSTANCE);
		try {
			CcccProject model = CcccXmlParser.parseCcccProject(sources, null);
			if (model != null && sources.size() > 0) {
				CcccXmlParser.collectViolations(model, project);
				collectMetricsFromCcccProject(project, context, model);
			}
		} catch (Exception e) {
			throw new SonarException(e.getMessage());
		}
		
		// delete all files with cccc output
		File dir = new File(outDir);
		FileUtils.clearDir(dir);
	}

	private void collectMetricsFromCcccProject(Project project, SensorContext context,
			CcccProject model) {
		if (model.getModules().size() > 0) {
			CcccProjectSummary summary = model.getSummary();
		
			context.saveMeasure(project, CoreMetrics.COMPLEXITY, summary.getComplexity().getValue());
			context.saveMeasure(project, CoreMetrics.FUNCTION_COMPLEXITY, summary.getComplexityPerFunction());
			context.saveMeasure(project, CoreMetrics.CLASS_COMPLEXITY, summary.getComplexityPerClass());
			context.saveMeasure(project, CoreMetrics.PUBLIC_API, (double)summary.getNumberOfPublicAPI());
			context.saveMeasure(project, CoreMetrics.PUBLIC_UNDOCUMENTED_API, (double)summary.getNumberOfUndocumentedAPI());
			context.saveMeasure(project, CoreMetrics.COMMENT_LINES_DENSITY, summary.getPercentOfComments());
			context.saveMeasure(project, CxxCcccMetrics.AVERAGE_LENGTH_OF_METHOD, summary.getAverageLengthOfFunction());
			context.saveMeasure(project, CoreMetrics.FUNCTIONS, (double) summary.getNumberOfFunctions());
			context.saveMeasure(project, CoreMetrics.CLASSES, (double) summary.getNumberOfClass());
			context.saveMeasure(project, CxxCcccMetrics.MAX_COMPLIXITY_OF_METHOD, summary.getMaxComplexityOfFunction());
			context.saveMeasure(project, CxxCcccMetrics.MAX_COMPLIXITY_OF_CLASS, summary.getMaxComplexityOfClass());
			context.saveMeasure(project, CxxCcccMetrics.MAX_NESTING_LEVEL,  summary.getMaxNestingLevel());
			context.saveMeasure(project, CxxCcccMetrics.AVERAGE_NESTING_LEVEL,  summary.getAverageNestingLevel());
			context.saveMeasure(project, CxxCcccMetrics.IF4, summary.getIF4().getValue());
			context.saveMeasure(project, CxxCcccMetrics.MAX_LINES_OF_CODE_IN_FUNCTION, summary.getMaxFunctionLength());
			context.saveMeasure(project, CxxCcccMetrics.AVERAGE_METHODS_IN_CLASS, summary.getAverageFunctionsPerClass());
			context.saveMeasure(project, CxxCcccMetrics.MAX_METHODS_IN_CLASS, summary.getMaxFunctionsPerClass());
			context.saveMeasure(project, CxxCcccMetrics.AVERAGE_IF4, summary.getAverageIF4());
			context.saveMeasure(project, CxxCcccMetrics.MAX_IF4, summary.getMaxIF4());
			context.saveMeasure(project, CxxCcccMetrics.AVERAGE_FAN_IN, summary.getAverageFanIn());
			context.saveMeasure(project, CxxCcccMetrics.MAX_FAN_IN, summary.getMaxFanIn());
			context.saveMeasure(project, CxxCcccMetrics.AVERAGE_FAN_OUT, summary.getAverageFanOut());
			context.saveMeasure(project, CxxCcccMetrics.MAX_FAN_OUT, summary.getMaxFanOut());
			context.saveMeasure(project, CoreMetrics.AFFERENT_COUPLINGS, summary.getFanIn());
			context.saveMeasure(project, CoreMetrics.EFFERENT_COUPLINGS, summary.getFanOut());
			context.saveMeasure(project, CxxCcccMetrics.AVERAGE_TOP5_METHOD_COMPLEXITY, summary.getAverageTop5MethodComplexity());
			context.saveMeasure(project, CxxCcccMetrics.AVERAGE_TOP5_MAX_NESTING, summary.getAverageTop5MaxNesting());
			context.saveMeasure(project, CxxCcccMetrics.AVERAGE_TOP5_METHOD_LENGHT, summary.getAverageTop5MethodLength());
			context.saveMeasure(project, new Measure(CxxCcccMetrics.AVERAGE_TOP5_METHOD_LENGHT_DISTRIB, summary.getAverageTop5MethodLengthDistrb()));
			context.saveMeasure(project, new Measure(CxxCcccMetrics.AVERAGE_TOP5_MAX_NESTING_DISTRIB, summary.getAverageTop5MaxNestingDistrb()));
			context.saveMeasure(project, new Measure(CxxCcccMetrics.AVERAGE_TOP5_METHOD_COMPLEXITY_DISTRIB, summary.getAverageTop5MethodComplexityDistrb()));
			collectMetricsFromCcccModules(project, context, model);
			collectViolations(model, project, context);
		}
	}

	private void collectMetricsFromCcccModules(Project project, SensorContext context,
			CcccProject model) {
		for (CcccModule module : model.getModules()) {
			if (module.isClass()) {
				final double mcc = module.getComplexity().getValue();
				
				
				CxxClass cxxClass = CxxClass.fromFileName(project, module.getFile(), module.getName(), false);
				context.saveMeasure(cxxClass, CoreMetrics.AFFERENT_COUPLINGS, (double)module.getFanIn().getValue());
				context.saveMeasure(cxxClass, CoreMetrics.EFFERENT_COUPLINGS, (double)module.getFanOut().getValue());
				context.saveMeasure(cxxClass, CxxCcccMetrics.MAX_COMPLIXITY_OF_CLASS, mcc);
				context.saveMeasure(cxxClass, CoreMetrics.COMPLEXITY, mcc);
				context.saveMeasure(cxxClass, CxxCcccMetrics.MAX_METHODS_IN_CLASS, (double)module.getNumberOfFunctions());
				context.saveMeasure(cxxClass, CxxCcccMetrics.TOTAL_METHODS_IN_CLASS, (double)module.getNumberOfFunctions());
				context.saveMeasure(cxxClass, CoreMetrics.FUNCTION_COMPLEXITY, module.getComplexityPerMemberFunction().getValue());
				saveSource(context, module.getFile(), module.getLine(), cxxClass);
			}
			logger.debug("Moduels name =  " +  module.getName());
			if (module.isClass() || module.getName().equals(ANONYMOUS)) {
				collectMetricsFromCcccFunctins(project, context, module);
			}
		}
	}

	private void collectMetricsFromCcccFunctins(Project project, SensorContext context,
			CcccModule module) {
		for(CcccFunction fun : module.getFunctions())
		{
			logger.debug("Function name = " + fun.getName());
			final String fileName = fun.getFile();
			final double mcc = fun.getComplexity().getValue();
			final double flc = fun.getLinesOfCode().getValue();
			final double fline = fun.getLine();
			final double lines = fun.getLines().getValue();
			final double flcom = fun.getLinesOfComment().getValue();
			final double maxNesting = fun.getMaxNesting().getValue();
									
			CxxFunction cxxfun = CxxFunction.fromFileName(project, fileName, fun.getName(), false);
			context.saveMeasure(cxxfun, CoreMetrics.COMPLEXITY, mcc);
			context.saveMeasure(cxxfun, CoreMetrics.LINES, lines);
			context.saveMeasure(cxxfun, CoreMetrics.NCLOC, flc);
			context.saveMeasure(cxxfun, CxxCcccMetrics.FUNCTION_LINE, fline);
			context.saveMeasure(cxxfun, CoreMetrics.COMMENT_LINES, flcom);
			context.saveMeasure(cxxfun, CxxCcccMetrics.MAX_NESTING_LEVEL, maxNesting);
			context.saveMeasure(cxxfun, CxxCcccMetrics.MAX_COMPLIXITY_OF_METHOD, mcc);
			context.saveMeasure(cxxfun, CxxCcccMetrics.MAX_COMPLIXITY_OF_METHOD_PER_FILE, mcc);
			context.saveMeasure(cxxfun, CxxCcccMetrics.MAX_LINES_OF_CODE_IN_FUNCTION, flc);
			context.saveMeasure(cxxfun, CxxCcccMetrics.TOTAL_NESTING_LEVEL, maxNesting);
			context.saveMeasure(cxxfun, CxxCcccMetrics.TOTAL_LENGTH_OF_METHOD, flc);
			saveSource(context, fileName, fline, lines, cxxfun);
		}
	}
	
	private void saveSource(SensorContext context, final String fileName,
			final double fline, final double lines, CxxFunction cxxfun) {
		File file = new File(fileName);
		try {
			String source = FileUtils.readFileToString(file, (int)fline, (int)fline + (int)lines);
			context.saveSource(cxxfun, source);
		} catch (Exception e) {
			throw new SonarException(e.getMessage());
		}
	}
	
	private void saveSource(SensorContext context, final String fileName,
			final double fline, CxxClass cxxClass) {
		File file = new File(fileName);
		try {
			String source = FileUtils.readFileToString(file, (int)fline, 0);
			context.saveSource(cxxClass, source);
		} catch (Exception e) {
			throw new SonarException(e.getMessage());
		}
	}
	
	
	private void collectViolations(CcccProject model, Project project, SensorContext context) {
	    for(CcccViolation ccccViolation : model.getViolations()) {
	 	    final String type     = ccccViolation.getType();
	 	    final String message  = ccccViolation.getMessage();
	 	    logger.debug("type = " + type + " message = " + message);
	
		    for (CcccFileViolation file : ccccViolation.getFiles()) {
		    	 final String filename = file.getName();
		    	 logger.debug("file " + filename);
		     
			     for(Integer line : file.getLines()) {
			    	  logger.debug("line " + line);  
			    	
			    	  final CxxFile ressource = CxxFile.fromFileName(project, filename, false);
			    	  final Rule rule = ruleFinder.   findByKey(CxxCcccRuleRepository.REPOSITORY_KEY, type);
			    	  final Violation v = Violation.create(rule, ressource);
		
			    	  v.setMessage(message);
			    	  v.setLineId(line);
			    	  context.saveViolation(v);        
			      }
		     }
	    }    
	}

	@Override
	public String toString() {
	  return getClass().getSimpleName();
	}
}