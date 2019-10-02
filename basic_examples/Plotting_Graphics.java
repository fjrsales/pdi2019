package basic_examples;

import java.awt.Color;

import ij.gui.GenericDialog;
import ij.gui.Plot;
import ij.plugin.PlugIn;

public class Plotting_Graphics implements PlugIn {

	private double sinFreq, sampFreq, phase, amp;
	private int POINTS;
	private int K = 1000;
	private double cycles;
	
	 	
	@Override
	public void run(String arg0) {
		// TODO Auto-generated method stub
		
		
		GenericDialog dialog = this.createParametersDialog();
		this.getParameters(dialog);
		double[] y = this.generateSinusoid(amp, sinFreq, phase, sampFreq);
		double[] t = this.generateTimeArray(sampFreq);
		
		Plot p =  new Plot("Plotting Example", "Time (ms)", "Amplitude(V)");
		p.setColor(new Color(0,0,255));
		p.add("line",t,y);
		p.setColor(new Color(255,0,0));
		p.add("circle",t,y);
		p.show();
		
		Stack_Processing s = new Stack_Processing();
		
		Thread t1 = new Thread((Runnable) s);
		t1.run();
		
	}
	
	private void getParameters(GenericDialog gd) {
		if (gd.wasOKed()) {						
			this.amp = gd.getNextNumber(); 	 
			this.sinFreq = gd.getNextNumber(); 	
			this.phase = (Math.PI/180)*gd.getNextNumber();
			this.sampFreq = K*gd.getNextNumber();	// kHz!
			this.cycles = gd.getNextNumber();
			this.POINTS = (int) Math.floor(cycles*sampFreq/sinFreq);
		}
	}
	
	private GenericDialog createParametersDialog() {
		
		GenericDialog gd = new GenericDialog("Function Gen");	
		gd.addNumericField("Amplitude (V):", 5, 1);				
		gd.addNumericField("Freq (Hz):", 100, 1);
		gd.addNumericField("Phase (gr):", 30, 1);
		gd.addNumericField("Sampling Freq (kHz):", 1, 1);
		gd.addNumericField("Cycles", 3, 0);
		gd.pack();												
		gd.showDialog();
		return gd;
	}
	
	private double[] generateSinusoid(double amp, double freq, double phase, double sampFreq) {
		
		double[] sinus = new double[POINTS];
		for (int i = 0; i < sinus.length; i++) {
			double tempArg = phase + (2*Math.PI*freq/sampFreq)*i;
			sinus[i] = amp * Math.sin(tempArg);
		}
		
		return sinus;
	}
	
	private double[] generateTimeArray(double sampFreq) {
		
		double[] time = new double[POINTS];
		for (int i = 0; i < time.length; i++) {
			time[i] = 1000*i/(sinFreq*sampFreq);
		}
		return time;
	}
	
	
}
