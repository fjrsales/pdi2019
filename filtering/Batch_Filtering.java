package filtering;

import basic_examples.Opening_Image;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.plugin.PlugIn;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Batch_Filtering implements PlugIn {

	private int MIN = 1;
	private int MAX = 3;
	
	@Override
	public void run(String arg0) {
		// TODO Auto-generated method stub
		Opening_Image op = new Opening_Image();
		op.run("");
		ImagePlus imp = op.getImp();
		
		
		for (int m = MIN; m <= MAX; m++) {
			for (int n = MIN; n <= MAX; n++) {
				ImagePlus tempImp = imp.duplicate();
				Moving_Average ma = new Moving_Average();
				String tempArg = generateStringArgs(m, n);
				int dummy = ma.setup(tempArg, tempImp);
				if (dummy == PlugInFilter.DOES_8G) {
					ma.run(tempImp.getProcessor());
				}
				tempImp = ma.getImp();
				tempImp.setTitle(imp.getTitle()+"_"+tempArg);
				tempImp.show();
				IJ.save(tempImp, "./"+tempImp.getTitle());
			}	
		}
		
		
		
	}
	
	public String generateStringArgs(int m, int n) {
		return "min:"+m+"_max:"+n;
	}
	
}
