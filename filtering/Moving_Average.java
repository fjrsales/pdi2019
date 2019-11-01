package filtering;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import point_operations.HistogramUtil;

public class Moving_Average implements PlugInFilter {

	private int h;
	private int w;
	private int m;
	private int n;
	private boolean skipDialog;
	private ImagePlus imp;
	
	@Override
	public void run(ImageProcessor ip) {
		// TODO Auto-generated method stub
		this.autoMinMax(ip);
		if (!skipDialog) {
			GenericDialog gd = this.createParametersDialog();
			while (!gd.wasOKed()) {
				gd = this.createParametersDialog();
			}
			this.m = (int) gd.getNextNumber();
			this.n = (int) gd.getNextNumber();
		}
		
		byte[] newPixels = new byte[w*h];
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int temp = 0;
				int sum = 0;
				int cont = 0;
				for (int l = -n; l <= n; l++) {
					for (int k = -m; k <= m; k++) {
						int u = x + k;
						int v = y + l;
						if (!isOutside(u, v)) {
							temp = ip.get(u,v);
							sum += temp;
							cont++;
						}
					}
				}
				newPixels[y*w+x] = (byte)((int)sum/cont);
			}	
		}
		ip.setPixels(newPixels);	
	}
	
	public boolean isOutside(int x, int y) {
		return ((x < 0)||(x >= w)||(y < 0)||(y >= h));
	}
	

	@Override
	public int setup(String arg, ImagePlus imp) {
		// TODO Auto-generated method stub
		this.imp = imp;
		this.skipDialog = false; 
		if (!arg.equals("")) {
			String[] dummy = arg.split("_");
			String mStr = dummy[0].split(":")[1];
			String nStr = dummy[1].split(":")[1];
			this.m = Integer.parseInt(mStr);
			this.n = Integer.parseInt(nStr);
			this.skipDialog = true;
		} 		
		this.h = imp.getHeight();
		this.w = imp.getWidth();
		return DOES_8G;
	}
	
	public void autoMinMax(ImageProcessor ip) {
		int[] hist = ip.getHistogram();
		int min = HistogramUtil.getHistogramMin(hist);
		int max = HistogramUtil.getHistogramMax(hist);

		double newMin = 0;
		double newMax = 255;
		
		double a = ((newMax - newMin)/(double)(max - min));
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int temp = ip.get(x,y);
				double dummy = (a*((double)(temp - min))+ newMin);
				ip.set(x, y, (int) Math.round(dummy)); 
			}	
		}
	}
	
	private GenericDialog createParametersDialog() {
		
		GenericDialog gd = new GenericDialog("Moving Average");
		gd.addMessage("Width = 2M + 1");
		gd.addMessage("Height = 2N + 1");
		gd.addNumericField("M: ", 3, 1);				
		gd.addNumericField("N: ", 3, 1);
		gd.pack();												
		gd.showDialog();
		return gd;
	}

	public ImagePlus getImp(){
		return this.imp;
	}

	
}
