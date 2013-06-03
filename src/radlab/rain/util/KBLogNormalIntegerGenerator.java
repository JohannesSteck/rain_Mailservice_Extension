package radlab.rain.util;

import org.apache.commons.math3.distribution.LogNormalDistribution;

import radlab.rain.util.storage.KeyGenerator;

public class KBLogNormalIntegerGenerator extends KeyGenerator{
//multiplies values by 1024 to represent KB
	LogNormalDistribution lnd;
	int last;
	int min=-1;
	int max=-1;
	public KBLogNormalIntegerGenerator(double mean, double standardDeviation){
		lnd = new LogNormalDistribution(mean, standardDeviation);
	}
	public KBLogNormalIntegerGenerator(int minValue, int maxValue, double mean, double standardDeviation){
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
		//long st = System.currentTimeMillis();
		int sample;
		if(min>-1&&max>-1){			
			sample=(int)Math.round(lnd.sample()*1024);			
			if(sample/1024<min){
				sample=min;
			}else if(sample/1024>max){
				sample=max;
			}
		}else{
			sample=(int)Math.round(lnd.sample()*1024);
		}
		last=sample;
		//long en = System.currentTimeMillis();
		//System.out.println("sampling time: "+((en-st))+" ms");
		return sample;
		
	}
	@Override
	public int generateKey() {
		
		return nextInt();
	}


	
}
