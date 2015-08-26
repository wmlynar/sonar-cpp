package org.sonar.plugins.cxx.cccc.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

/**
 * Get max value of measure from children resources.
 * 
 */
public abstract class CxxAbstractGetMaxChildrenDecorator implements Decorator {

    /**
    * Each metric is used individually. There are as many generated measures than metrics.
    * <p/>
    * <p><b>Important</b> : annotations are not inherited, so you have to copy the @DependedUpon annotation
    * when implementing this method.</p>
    *
    * @return not null list of metrics
    */
	public abstract List<Metric> generatesMetrics();

	/**
	 * 
	 * @return
	 */
	public abstract String resourceQualifer();
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
	.getLogger(CxxAbstractGetMaxChildrenDecorator.class);
	
	public void decorate(Resource resource, DecoratorContext context) {
		if(!shouldDecoreateResurce(resource)) {
			return;
		}
		if (resourceQualifer() == null || resource.getQualifier().equals(resourceQualifer())) {
			for(Metric metric : generatesMetrics())
			{
				if (context.getMeasure(metric) == null)
				{
					Double max = 0.0;
					for (Measure measure : context.getChildrenMeasures(metric))
					{
						if (measure.getValue() > max) {
							max = measure.getValue();
						}
					}
					context.saveMeasure(new Measure(metric, max));
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
