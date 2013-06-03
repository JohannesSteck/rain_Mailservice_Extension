package radlab.rain.util;

public class testLogNormal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long sum=0;
		LogNormalIntegerGenerator lng = new LogNormalIntegerGenerator(0.87, 0.739);
		int sampleSize =100;
for(int i = sampleSize;i<100;i++){
	int sample = lng.generateKey();
	System.out.println(sample);
	sum=sum+sample;	
}
System.out.println("Average: "+sum/sampleSize);
	}

}
