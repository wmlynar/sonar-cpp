package org.sonar.plugins.cxx.cccc.decorators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.PersistenceMode;
import org.sonar.api.measures.RangeDistributionBuilder;
import org.sonar.api.resources.Java;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;
import org.sonar.plugins.cxx.CxxFunction;
import org.sonar.plugins.cxx.CxxLanguage;
import org.sonar.plugins.cxx.cccc.CxxCcccMetrics;


public final class CxxFunctionComplexityDistributionBuilder implements Decorator {

	 // public static final Number[] LIMITS = {1, 2, 4, 6, 9, 12, 16, 20, 25};
	  public static final Number[] LIMITS = {1, 2, 4, 8, 16, 32};

		private static Logger logger = LoggerFactory
		.getLogger(CxxClassComplexityDistributionBuilder.class);
		
		
	  @DependsUpon
	  public Metric dependOnComplexity() {
	    return CoreMetrics.COMPLEXITY;
	  }

	  @DependedUpon
	  public Metric generatesFunctionComplexityDistribution() {
	    return CoreMetrics.FUNCTION_COMPLEXITY_DISTRIBUTION;
	  }

	  public void decorate(Resource resource, DecoratorContext context) {
	    if (shouldExecuteOn(resource, context)) {
	    	
	      RangeDistributionBuilder builder = new RangeDistributionBuilder(CoreMetrics.FUNCTION_COMPLEXITY_DISTRIBUTION, LIMITS);
	     // RangeDistributionBuilder logBuilder = new RangeDistributionBuilder(CxxCcccMetrics.FUNCTION_COMPLEXITY_LOGDISTRIBUTION, LOGLIMITS);
	      for (DecoratorContext childContext : context.getChildren()) {
	        if (childContext.getResource() instanceof CxxFunction) {
	          Measure complexity = childContext.getMeasure(CoreMetrics.COMPLEXITY);
	          if (complexity != null) {
	            builder.add(complexity.getValue());
	           // logBuilder.add(complexity.getValue());
	          }
	        }
	      }
	      Measure measure = builder.build(true);
	      measure.setPersistenceMode(PersistenceMode.MEMORY);
	      context.saveMeasure(measure);
	     // context.saveMeasure(logBuilder.build(true).setPersistenceMode(PersistenceMode.MEMORY));
	    }
	  }

	  boolean shouldExecuteOn(Resource resource, DecoratorContext context) {
	    return ResourceUtils.isFile(resource);
	  }

	  public boolean shouldExecuteOnProject(Project project) {
	    return CxxLanguage.KEY.equals(project.getLanguageKey());
	  }
	}

