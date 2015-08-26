package org.sonar.plugins.cxx.cccc.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Top5PercentCalculator {
	private static final String PAIR_SEPARATOR = ";";
	private static final String FIELD_SEPARATOR = "=";
	private Map<Double, Integer> values = new HashMap<Double, Integer>();
	int size = 0;
	public void add(double value) {
		if (!values.containsKey(value)) {
			values.put(value, 1);
		} 
		else {
			int count = values.get(value);
			values.put(value, ++count);
		}
		size++;
	}
	
	public void add(Map<Double, Integer> data) {
		for (Entry<Double, Integer> entry :  data.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
				add(entry.getKey());
			}
		}
	}

	public double getTop5Average() {
		int top5Number = (int) Math.ceil(size * 0.05);
		Object[] key = values.keySet().toArray();  
	    Arrays.sort (key, Collections.reverseOrder());
	    
/*	    for   (int   i   =   0;   i   <   key.length;   i++)   {  
            System.out.println(key[i] + " = " + values.get(key[i]));  
	    }*/
	 
		double sum = 0;
		
		int index = 0;
		int i = 0;
		int n = 0;
		while (i < top5Number) {
			if (n == 0) {
				n = values.get(key[index]);
			}
        	sum += (Double)key[index];
        	i++;
       		n--;
       		if (n == 0) {
       			index++;
       		}
		}
		if (top5Number > 0)  {
			return sum / top5Number;
		}
		else {
			return 0;
		}
	}
	
	public String build() {
		StringBuilder sb = new StringBuilder();
	    if (values != null) {
	        boolean first = true;
	        for (Entry entry : values.entrySet()) {
	        	if (!first) {
	        		sb.append(PAIR_SEPARATOR);
	            }
	        	sb.append(entry.getKey());
	        	sb.append(FIELD_SEPARATOR);
	        	sb.append(entry.getValue());
	        first = false;
	      }
	    }
	    return sb.toString();
	}
	
	public static Map<Double, Integer> deserialize(String data) {
		String [] entries = data.split(PAIR_SEPARATOR);
		Map<Double, Integer> values = new HashMap<Double, Integer>();
		for (String entry : entries) {
			String [] pair = entry.split(FIELD_SEPARATOR);
			Double key = Double.parseDouble(pair[0]);
			Integer value = Integer.parseInt(pair[1]);
			values.put(key, value);
		}
		return values;
	}
}
