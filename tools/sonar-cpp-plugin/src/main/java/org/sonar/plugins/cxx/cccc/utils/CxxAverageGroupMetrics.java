package org.sonar.plugins.cxx.cccc.utils;

import org.sonar.api.measures.Metric;

/**
 * Group of metric for calculating average value
 */
public class CxxAverageGroupMetrics {
	
	private Metric computeMetric;
	private Metric sumMetric;
	private Metric byMetric;
	
	public CxxAverageGroupMetrics(Metric computeMetric, Metric sumMetric,
			Metric byMetric) {
		super();
		this.computeMetric = computeMetric;
		this.sumMetric = sumMetric;
		this.byMetric = byMetric;
	}

	public Metric getComputeMetric() {
		return computeMetric;
	}

	public void setComputeMetric(Metric computeMetric) {
		this.computeMetric = computeMetric;
	}

	public Metric getSumMetric() {
		return sumMetric;
	}

	public void setSumMetric(Metric sumMetric) {
		this.sumMetric = sumMetric;
	}

	public Metric getByMetric() {
		return byMetric;
	}

	public void setByMetric(Metric byMetric) {
		this.byMetric = byMetric;
	}
}
