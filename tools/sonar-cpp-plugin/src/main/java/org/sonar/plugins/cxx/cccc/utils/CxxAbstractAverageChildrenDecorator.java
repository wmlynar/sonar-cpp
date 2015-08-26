package org.sonar.plugins.cxx.cccc.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.FormulaData;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;

public abstract class CxxAbstractAverageChildrenDecorator implements Decorator {

    /**
    * Each metric is used individually. There are as many generated measures than metrics.
    * <p/>
    * <p><b>Important</b> : annotations are not inherited, so you have to copy the @DependedUpon annotation
    * when implementing this method.</p>
    *
    * @return not null list of metrics
    */
	public abstract List<CxxAverageGroupMetrics> averageMetrics();
	
	//public abstract Metric sumMetric();
	
	//public abstract Metric byMetric();
	/**
	 * {@inheritDoc}}
	 */
	public boolean shouldExecuteOnProject(Project arg0) {
		return true;
	}
	
	/**
	 * @return whether it should save zero if no child measures
	 */
	//protected abstract boolean sholudSaveZeroIfNoChildMeasures(); 

	private static Logger logger = LoggerFactory
	.getLogger(CxxAbstractAverageChildrenDecorator.class);
	
	public void decorate(Resource resource, DecoratorContext context) {
		logger.debug("AbstractaverageChildrenDecorator : decorate");
		if(!shouldDecoreateResurce(resource)) {
			return;
		}
		for (CxxAverageGroupMetrics metricsGroup : averageMetrics()) {
			Metric metric = metricsGroup.getComputeMetric();
			if (context.getMeasure(metric) == null)
			{
				Double totalMeasure = 0.0;
				Double totalByMeasure = 0.0;
				boolean hasApplicableChildren = false;

				for (DecoratorContext childrenData : context.getChildren())
				{
					Double childrenByMeasure = MeasureUtils.getValue(childrenData.getMeasure(metricsGroup.getByMetric()), null);
					Double childrenComplexity = MeasureUtils.getValue(childrenData.getMeasure(metricsGroup.getSumMetric()), null);

					if (childrenComplexity != null && childrenByMeasure != null && childrenByMeasure > 0.0)
					{
						totalByMeasure += childrenByMeasure;
						totalMeasure += childrenComplexity;
						hasApplicableChildren = true;
					}
				}
				logger.debug("totalMeasure = " + totalMeasure + " totalByMeasure = " + totalByMeasure);
				if (hasApplicableChildren) {
					context.saveMeasure(new Measure(metric, (totalMeasure / totalByMeasure)));
				}
			}
		}
	}
	
	/**
	 * @return whether the resource should be decorated or not
	 */
	public boolean shouldDecoreateResurce(Resource resource)
	{
		return true;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
