package org.sonar.plugins.cxx.cccc;

import java.util.ArrayList;
import java.util.List;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;
import org.sonar.api.measures.SumChildDistributionFormula;
import org.sonar.api.measures.SumChildValuesFormula;

public class CxxCcccMetrics implements Metrics{

	/** Design Quality Domain */
	public static final String DOMAIN_DESIGN = "Design";
	public static final String DOMAIN_COMPLEXITY = "Complexity";
	public static final String DOMAIN_CCCC = "CCCC";
	public static final String DOMAIN_CCCC_STRUCTURAL = "CCCC Structural Metrics";
	
	public static final Metric FUNCTION_LINE = new Metric(
			"cccc_function_line",
			"Function line",
			"Function start line",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric TOTAL_LENGTH_OF_METHOD = new Metric(
			"cccc_total_length_of_method",
			"Total length of methods",
			"Total length of methods metric",
			Metric.ValueType.INT, Metric.DIRECTION_NONE , false, DOMAIN_CCCC).
			setFormula(new SumChildValuesFormula(false));
	
	public static final Metric AVERAGE_LENGTH_OF_METHOD = new Metric(
			"cccc_average_length_of_method",
			"Average length of method",
			"Average length of method metric",
			Metric.ValueType.FLOAT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric MAX_COMPLIXITY_OF_METHOD = new Metric(
			"cccc_max_complexity_of_method",
			"Max complexity of method",
			"Max complexity of method metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric MAX_COMPLIXITY_OF_METHOD_PER_FILE = new Metric(
			"cccc_max_complexity_of_method_per_file",
			"Max complexity of method per file",
			"Max complexity of method per file metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric MAX_COMPLIXITY_OF_CLASS = new Metric(
			"cccc_max_complexity_of_class",
			"Max complexity of class",
			"Max complexity of class metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric MAX_NESTING_LEVEL = new Metric(
			"cccc_max_nesting_level",
			"Max nesting level",
			"Max nesting level metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric AVERAGE_NESTING_LEVEL = new Metric(
			"cccc_average_nesting_level",
			"Average nesting level",
			"Average nesting level metric",
			Metric.ValueType.FLOAT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric TOTAL_NESTING_LEVEL = new Metric(
			"cccc_total_nesting_level",
			"Total nesting level",
			"Total nesting level metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC).
			setFormula(new SumChildValuesFormula(false));
	
	public static final Metric IF4 = new Metric(
			"cccc_IF4",
			"Information Flow measure",
			"Measure of information flow between modules suggested by Henry and Kafura. " +
			"The analyser makes an approximate count of this by counting inter-module couplings " +
			"identified in the module interfaces.",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC_STRUCTURAL);
	
	public static final Metric MAX_LINES_OF_CODE_IN_FUNCTION = new Metric(
			"cccc_max_lines_of_code_in_function",
			"Max lines of code in fucntion",
			"Max lines of code in fucntion metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric TOTAL_METHODS_IN_CLASS = new Metric(
			"cccc_total_methods_in_class",
			"Total methods in class",
			"Total methods in class metric",
			Metric.ValueType.INT, Metric.DIRECTION_NONE, false, DOMAIN_CCCC).
			setFormula(new SumChildValuesFormula(false));
	
	public static final Metric TOTAL_IF4 = new Metric(
			"cccc_total_IF4",
			"Total IF4",
			"Total IF4 metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC_STRUCTURAL);
	
	public static final Metric AVERAGE_IF4  = new Metric(
			"cccc_average_IF4",
			"Average IF4",
			"Average IF4 metric",
			Metric.ValueType.FLOAT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC_STRUCTURAL);
	
	public static final Metric MAX_IF4  = new Metric(
			"cccc_max_IF4",
			"Max IF4 ",
			"Max IF4  metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC_STRUCTURAL);
	
	public static final Metric TOTAL_FAN_IN = new Metric(
			"cccc_total_fan_in",
			"Total fan in",
			"Total fan in metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC_STRUCTURAL);
	
	public static final Metric AVERAGE_FAN_IN  = new Metric(
			"cccc_average_fan_in",
			"Average fan in",
			"Average fan in metric",
			Metric.ValueType.FLOAT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC_STRUCTURAL);
	
	public static final Metric MAX_FAN_IN  = new Metric(
			"cccc_max_fan_in",
			"Max fan in",
			"Max fan in  metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC_STRUCTURAL);
	
	public static final Metric TOTAL_FAN_OUT = new Metric(
			"cccc_total_fan_out",
			"Total fan out",
			"Total fan out metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC_STRUCTURAL);
	
	public static final Metric AVERAGE_FAN_OUT  = new Metric(
			"cccc_average_fan_out",
			"Average fan out",
			"Average fan out metric",
			Metric.ValueType.FLOAT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC_STRUCTURAL);
	
	public static final Metric MAX_FAN_OUT  = new Metric(
			"cccc_max_fan_out",
			"Max fan out",
			"Max fan out  metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC_STRUCTURAL);
	
	public static final Metric AVERAGE_METHODS_IN_CLASS = new Metric(
			"cccc_average_methods_in_class",
			"Methods / class",
			"Average methods number in class metric",
			Metric.ValueType.FLOAT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric MAX_METHODS_IN_CLASS = new Metric(
			"cccc_max_methods_in_class",
			"Max methods in class",
			"Max methods in class metric",
			Metric.ValueType.INT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric AVERAGE_TOP5_METHOD_COMPLEXITY = new Metric(
			"cccc_average_top5_methods_complexity",
			"Average of top 5 percent complexity",
			"Average of top 5 percent complexity metric",
			Metric.ValueType.FLOAT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric AVERAGE_TOP5_MAX_NESTING = new Metric(
			"cccc_average_top5_max_nesting",
			"Average of top 5 percent max nesting level",
			"Average of top 5 percent max nesting level metric",
			Metric.ValueType.FLOAT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric AVERAGE_TOP5_METHOD_LENGHT = new Metric(
			"cccc_average_top5_methods_length",
			"Average of top 5 percent of methods lenght",
			"Average of top 5 percent of methods lenght metric",
			Metric.ValueType.FLOAT, Metric.DIRECTION_WORST, false, DOMAIN_CCCC);
	
	public static final Metric AVERAGE_TOP5_METHOD_LENGHT_DISTRIB = new Metric(
			"cccc_average_top5_methods_length_distrib",
			"Average of top 5 percent of methods lenght",
			"Average of top 5 percent of methods lenght metric",
			Metric.ValueType.DATA, Metric.DIRECTION_NONE, false, null);
	
	public static final Metric AVERAGE_TOP5_MAX_NESTING_DISTRIB = new Metric(
			"cccc_average_top5_max_nesting_distrib",
			"Average of top 5 percent max nesting level",
			"Average of top 5 percent max nesting level metric",
			Metric.ValueType.DATA, Metric.DIRECTION_NONE, false, null);
	
	public static final Metric AVERAGE_TOP5_METHOD_COMPLEXITY_DISTRIB = new Metric(
			"cccc_average_top5_methods_complexity_distrib",
			"Average of top 5 percent complexity",
			"Average of top 5 percent complexity metric",
			Metric.ValueType.DATA, Metric.DIRECTION_NONE, false, null);
	
	
	public static final String CLASS_COMPLEXITY_LOGDISTRIBUTION_KEY = "class_complexity_logdistribution";
	  public static final Metric CLASS_COMPLEXITY_LOGDISTRIBUTION = new Metric(CLASS_COMPLEXITY_LOGDISTRIBUTION_KEY,
	      "Classes distribution /complexity", "Classes distribution /complexity", Metric.ValueType.DISTRIB, Metric.DIRECTION_NONE, true,
	      DOMAIN_COMPLEXITY).setFormula(new SumChildDistributionFormula());

	  public static final String FUNCTION_COMPLEXITY_LOGDISTRIBUTION_KEY = "function_complexity_logdistribution";
	  public static final Metric FUNCTION_COMPLEXITY_LOGDISTRIBUTION = new Metric(FUNCTION_COMPLEXITY_LOGDISTRIBUTION_KEY,
	      "Functions distribution /complexity", "Functions distribution /complexity", Metric.ValueType.DISTRIB, Metric.DIRECTION_NONE, true,
	      DOMAIN_COMPLEXITY).setFormula(new SumChildDistributionFormula());

	  public static final String FILE_COMPLEXITY_LOGDISTRIBUTION_KEY = "file_complexity_logdistribution";
	  public static final Metric FILE_COMPLEXITY_LOGDISTRIBUTION = new Metric(FILE_COMPLEXITY_LOGDISTRIBUTION_KEY, "Files distribution /complexity",
	      "Files distribution /complexity", Metric.ValueType.DISTRIB, Metric.DIRECTION_NONE, true, DOMAIN_COMPLEXITY)
	      .setFormula(new SumChildDistributionFormula());

	  public static final String PARAGRAPH_COMPLEXITY_LOGDISTRIBUTION_KEY = "paragraph_complexity_logdistribution";
	  public static final Metric PARAGRAPH_COMPLEXITY_LOGDISTRIBUTION = new Metric(PARAGRAPH_COMPLEXITY_LOGDISTRIBUTION_KEY,
	      "Paragraph distribution /complexity", "Paragraph distribution /complexity", Metric.ValueType.DISTRIB, Metric.DIRECTION_NONE, true,
	      DOMAIN_COMPLEXITY).setFormula(new SumChildDistributionFormula());

	
	public List<Metric> getMetrics() {
		final List<Metric> metrics = new ArrayList<Metric>();
		
		metrics.add(TOTAL_NESTING_LEVEL);
		metrics.add(FUNCTION_LINE);
		metrics.add(TOTAL_LENGTH_OF_METHOD);
		metrics.add(AVERAGE_LENGTH_OF_METHOD);
		metrics.add(MAX_COMPLIXITY_OF_METHOD);
		metrics.add(MAX_COMPLIXITY_OF_METHOD_PER_FILE);
		metrics.add(MAX_COMPLIXITY_OF_CLASS);
		metrics.add(MAX_NESTING_LEVEL);
		metrics.add(AVERAGE_NESTING_LEVEL);
		metrics.add(MAX_LINES_OF_CODE_IN_FUNCTION);
		metrics.add(TOTAL_METHODS_IN_CLASS);
		metrics.add(AVERAGE_METHODS_IN_CLASS);
		metrics.add(MAX_METHODS_IN_CLASS);
		metrics.add(IF4);
		metrics.add(TOTAL_IF4);
		metrics.add(AVERAGE_IF4);
		metrics.add(MAX_IF4);
		metrics.add(TOTAL_FAN_IN);
		metrics.add(AVERAGE_FAN_IN);
		metrics.add(MAX_FAN_IN);
		metrics.add(TOTAL_FAN_OUT);
		metrics.add(AVERAGE_FAN_OUT);
		metrics.add(MAX_FAN_OUT);
		metrics.add(AVERAGE_TOP5_METHOD_COMPLEXITY);
		metrics.add(AVERAGE_TOP5_MAX_NESTING);
		metrics.add(AVERAGE_TOP5_METHOD_LENGHT);
		metrics.add(AVERAGE_TOP5_METHOD_LENGHT_DISTRIB);
		metrics.add(AVERAGE_TOP5_METHOD_COMPLEXITY_DISTRIB);
		metrics.add(AVERAGE_TOP5_MAX_NESTING_DISTRIB);
		metrics.add(CLASS_COMPLEXITY_LOGDISTRIBUTION);
		metrics.add(FUNCTION_COMPLEXITY_LOGDISTRIBUTION);
		metrics.add(FILE_COMPLEXITY_LOGDISTRIBUTION);
		metrics.add(PARAGRAPH_COMPLEXITY_LOGDISTRIBUTION);
		return metrics;
	}
}