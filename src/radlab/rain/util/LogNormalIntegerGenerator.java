package radlab.rain.util;

import org.apache.commons.math3.distribution.LogNormalDistribution;

import radlab.rain.util.storage.KeyGenerator;

public class LogNormalIntegerGenerator extends KeyGenerator{

	LogNormalDistribution lnd;
	int last;
	int min=-1;
	int max=-1;
	public LogNormalIntegerGenerator(double mean, double standardDeviation){
		lnd = new LogNormalDistribution(mean, standardDeviation);
	}
	public LogNormalIntegerGenerator(int minValue, int maxValue, double mean, double standardDeviation){
		lnd = new LogNormalDistribution(mean, standardDeviation);
		if(minValue<maxValue){
		min=minValue;
		max=maxValue;
		}else{
			System.out.println("min value > max value! Ignoring min and max.");
		}
	}
	

	public String nextString() {
		// TODO Auto-generated method stub
		return String.valueOf(nextInt());
	}

	
	public String lastString() {
		// TODO Auto-generated method stub
		return String.valueOf(lastDouble());
	}
	
	public double lastDouble() {
		// TODO Auto-generated method stub
		return last;
	}

public int nextInt(){
		
		int sample;
		if(min>-1&&max>-1){			
			sample=(int)Math.round(lnd.sample());			
			if(sample<min){
				sample=min;
			}else if(sample>max){
				sample=max;
			}
		}else{
			sample=(int)Math.round(lnd.sample());
		}
		last=sample;
		last=sample;
		return sample;
		
	}
	@Override
	public int generateKey() {
		
		return nextInt();
	}


	
}
