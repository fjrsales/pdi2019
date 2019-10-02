package basic_examples;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Inverter_Image implements PlugInFilter {

	@Override
	public void run(ImageProcessor ip) {
		// TODO Auto-generated method stub
		//ip.invert();
		
		int h = ip.getHeight();
		int w = ip.getWidth();
		
		/*		
		for (int lin = 0; lin < h; lin++) {
			for (int col = 0; col < w; col++) {
				int temp = ip.getPixel(col, lin);
				System.out.println(temp);
				ip.set(lin, col, 255-temp);
			}
		}
		*/
		byte[] pixels = (byte[]) ip.getPixels();
		
		for (int i = 0; i < pixels.length; i++) {
			
			System.out.println("byte: " + pixels[i]);
			int temp = 0xff & pixels[i];
			System.out.println("int: "+ temp);
		}
		
	}

	@Override
	public int setup(String arg0, ImagePlus imp) {
		// TODO Auto-generated method stub
		return DOES_8G;
	}

}
