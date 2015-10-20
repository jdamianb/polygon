package polygon;

public class RangeFloat {
	
	private float min;
	private float max;
	
	public RangeFloat (float val1, float val2) {
		
		if(val1<=val2) {
			min=val1;
			max=val2;
		} else {
			min=val2;
			max=val1;
		}
		//System.out.printf("%.2f %.2f\n",min,max);
	}
	
	public boolean within (float value) {
		return new Range(min,max).within(value);
	}
}
