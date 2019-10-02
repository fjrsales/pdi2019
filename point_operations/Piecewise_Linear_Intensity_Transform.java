package point_operations;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Piecewise_Linear_Intensity_Transform implements PlugInFilter {
	
	private int CHANGE_INPUT = 127;
	private int w;
	private int h;
	private double a1,a2;
	private byte MASK = (byte)0xff;
	
	@Override
	public void run(ImageProcessor ip) {
		// TODO Auto-generated method stub
		int[] hist = ip.getHistogram();
		this.h = ip.getHeight();
		this.w = ip.getWidth();

		int min = HistogramUtil.getHistogramMin(hist);
		int max = HistogramUtil.getHistogramMax(hist);
		
		GenericDialog gd = this.createParametersDialog();
		// Variable Initialization
		double newMin = 0;
		double newMax = 255;
		double changeIn = 127;
		double changeOut = 100;
				
		if (gd.wasOKed()) {
			changeIn = gd.getNextNumber();
			changeOut = gd.getNextNumber();
			newMin = (int) gd.getNextNumber();
			newMax = (int) gd.getNextNumber();
		} else {
			IJ.error("ERRO!!!", "Plugin Terminado Prematuramente!");
		}
		
		this.calculateLinearTransformCoeficients(min, max, changeIn, changeOut, newMin, newMax);
		byte[] pixels = (byte[])ip.getPixels();
		byte[] newPixels = pixels.clone();
		
		for (int i = 0; i < pixels.length; i++) {
			int temp = 0xff & pixels[i];
			double dummy;
			if (temp < changeIn) {
				dummy = (a1*((double)(temp - min))+ newMin);
			}else {
				dummy = (a2*((double)(temp - changeIn))+ changeOut);
			}
			newPixels[i] = (byte) (MASK & ((int)Math.round(dummy)));
		}
		ip.setPixels(newPixels);
		
	}

	@Override
	public int setup(String arg0, ImagePlus imp) {
		// TODO Auto-generated method stub
		return DOES_8G;
	}
	
	private GenericDialog createParametersDialog() {
		
		GenericDialog gd = new GenericDialog("Piecewise Linear Intensity Transform");	
		gd.addNumericField("Change Point [Input]", 127, 1);
		gd.addNumericField("Change Point [Output]", 100, 1);
		gd.addMessage("----------------------");
		gd.addNumericField("New Min:", 0, 1);				
		gd.addNumericField("New Max:", 255, 1);
		gd.pack();												
		gd.showDialog();
		return gd;
	}
	
	private void calculateLinearTransformCoeficients(int min, int max, double changeIn, double changeOut, double newMin, double newMax) {
		
		this.a1 = ((changeOut - newMin)/(double)(changeIn - min));
		this.a2 = ((changeOut - newMax)/(double)(changeIn - max));
		
	}

}
