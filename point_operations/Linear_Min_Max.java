package point_operations;

import java.util.Arrays;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Linear_Min_Max implements PlugInFilter {

	private int MAX_OUT = 255;
	private int MIN_OUT = 0;
	
	@Override
	public void run(ImageProcessor ip) {
		// TODO Auto-generated method stub
		
		int[] hist = ip.getHistogram();
		int h = ip.getHeight();
		int w = ip.getWidth();
		
//		System.out.println(Arrays.toString(hist));
		int min = this.getHistogramMin(hist);
		System.out.println(min);
		int max = this.getHistogramMax(hist);
		System.out.println(max);
		
		double a = ((MAX_OUT - MIN_OUT)/(double)(max - min));
		System.out.println("a: "+a);
//		double b = MIN_OUT - a*min;
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int temp = ip.get(x,y);
				double dummy = (a*((double)(temp - min))+ MIN_OUT);
//				System.out.println(x+"\t"+y+"\t"+temp+"\t"+dummy);
				ip.set(x, y, (int) Math.round(dummy)); 
			}	
		}
		
		System.out.println(min+"\t"+max);
		//System.out.println(min+"\t"+max);
	}

	private int getHistogramMax(int[] hist) {
		// TODO Auto-generated method stub
		int max = 255;
		for (int i = 255; i >= 0; i--) {
			if (hist[i] != 0) {
				max = i;
				break;
			}		
		}
		return max;
	}

	private int getHistogramMin(int[] hist) {
		// TODO Auto-generated method stub
		int min = 0;
		for (int i = 0; i < hist.length; i++) {
			if (hist[i] != 0) {
				min = i;
				break;
			}		
		}
		return min;
	}

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		
		// TODO Auto-generated method stub
		return DOES_8G;
	}

}
