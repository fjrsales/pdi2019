package point_operations;

import ij.ImagePlus;
import ij.gui.NewImage;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Log_Compression_16_to_08_bits implements PlugInFilter {

	@Override
	public void run(ImageProcessor ip) {
		// TODO Auto-generated method stub
		
		short[] pixels = (short[]) ip.getPixels();
		byte[] newPixels = new byte[pixels.length];
		
		for (int i = 0; i < pixels.length; i++) {
			int temp = 0xffff & pixels[i];
			double dummy = (255.0/16.0)*Math.log(temp+1)/Math.log(2);
			newPixels[i] = (byte)((int) Math.round(dummy));
			if (temp == 65535) {
				System.out.println(i+"\t"+temp+"\t"+dummy);
			}
		}
		
		ImagePlus newImp = NewImage.createByteImage("", ip.getWidth(), ip.getHeight(), 1, NewImage.FILL_BLACK);
		newImp.getProcessor().setPixels(newPixels);
//		newImp.setTitle("Log Compressed Image");
		newImp.show("Log Compressed Image");
	}

	@Override
	public int setup(String arg0, ImagePlus imp) {
		// TODO Auto-generated method stub
		return DOES_16;
	}

}
