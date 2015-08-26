package org.sonar.plugins.cxx.cccc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;
import org.sonar.plugins.cxx.CxxLanguage;
import org.sonar.plugins.cxx.cccc.CxxCcccMetrics;

public abstract class CxxAbstractTop5Dcorator implements Decorator{
	@DependsUpon
	  public abstract Metric dependOn();

	private static Logger logger = LoggerFactory
	.getLogger(CxxAbstractTop5Dcorator.class);
	
	  @DependedUpon
	  public abstract Metric generatesMetric();

	  public void decorate(Resource resource, DecoratorContext context) {
	    if (shouldExecuteOn(resource, context)) {
	      Top5PercentCalculator calc = new Top5PercentCalculator();
	      for (DecoratorContext childContext : context.getChildren()) {
	          Measure complexity = childContext.getMeasure(dependOn());
	          if (complexity != null) {
	           //builder.add(complexity.getValue());
	        	  calc.add(calc.deserialize(complexity.getData()));
	          }
	      }
	      Measure measure = new Measure(generatesMetric(), calc.getTop5Average());
	      //measure.setPersistenceMode(PersistenceMode.MEMORY);
	      context.saveMeasure(measure);
	    //  logger.info("save {} ", calc.getTop5Average());
	    }
	  }

	  boolean shouldExecuteOn(Resource resource, DecoratorContext context) {
	    return ResourceUtils.isProject(resource) && context.getMeasure(generatesMetric()) == null;
	  }

	  public boolean shouldExecuteOnProject(Project project) {
	    return CxxLanguage.KEY.equals(project.getLanguageKey());
	  }
}
