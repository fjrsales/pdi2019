package basic_examples;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.NewImage;
import ij.plugin.PlugIn;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

public class Stack_Processing implements PlugIn, Runnable{

	private int W = 400;
	private int H = 400;
	private int N = 10;
	
	@Override
	public void run(String arg0) {
		// TODO Auto-generated method stub
		
		// Imagem Randomizada Criada Manualmente
		ImagePlus randomizedImp = this.createRandomImagePlus(W,H,N);
		
		// Relatorio
		System.out.println("[Imagem: "+randomizedImp.getTitle()+"]");
		this.printColorReport(randomizedImp);
		System.out.println("------------------------------------- \n");

		// Imagem Criada Pelo ImageJ
		ImagePlus autoGenImp = NewImage.createRGBImage("Stack",W, H, N, NewImage.FILL_RANDOM);
		
		
		//Relatorio
		System.out.println("[Imagem: "+autoGenImp.getTitle()+"]");
		this.printColorReport(autoGenImp);
		System.out.println("------------------------------------- \n");
		
		randomizedImp.show();
		autoGenImp.show();
		
	}
	

	private ImagePlus createRandomImagePlus(int w, int h, int n) {
		// TODO Auto-generated method stub
		ImageStack ims = new ImageStack(w, h, n);
		for (int i = 0; i < n; i++) {
			ImagePlus tempImp = NewImage.createRGBImage("Stack",W, H, i, NewImage.FILL_RANDOM);
			ims.setProcessor(tempImp.getProcessor(), i+1);
		}
		return new ImagePlus("Randomized Image", ims);
	}

	private void printColorReport(ImagePlus imp) {
		// TODO Auto-generated method stub
		ImageStack ims = imp.getImageStack();
		int n = ims.getSize();
		for (int i = 0; i < n; i++) {
			ColorProcessor colorIp = (ColorProcessor) ims.getProcessor(i+1);	//Posicao inicial: 1 [Excecao no JAVA] ATENCAO!!!
			Color c = this.calculateMostFreqColor(colorIp);
			Color mean = new Color(this.getMeanColor(colorIp));
			System.out.println("A cor mais frequente do slice "+i+" é: "+c.toString()+" e a cor média é: "+mean.toString());
		}
		
	}

	public int getMeanColor(ImageProcessor tempIp) {
		
		int[] pixels = (int[]) tempIp.getPixels();
		int n = pixels.length;
		double mean = 0;
		for (int i = 0; i < n; i++) {
			 mean += (double) (pixels[i]/n);
		}
			
		return (int) mean;
	}
	
	public Color calculateMostFreqColor(ColorProcessor colorIp) {
		
		int w = colorIp.getWidth();
		int h = colorIp.getHeight();
		
		HashMap<Color, Integer> map = new HashMap<Color,Integer>();  //<K,V>: Key - Chave; V: Valor [Para cada chave (Color), tem um valor (num. contagens)]
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				Color tempColor = colorIp.getColor(x, y);
				if (map.containsKey(tempColor)) {
					int temp = map.get(tempColor).intValue();
					map.replace(tempColor,temp , temp+1);
					//this.incrementValue(map,tempColor);
				} else {
					map.put(tempColor, 1);
				}
			}
		}
		
		Integer max = this.getGreatestValueFromMap(map);
		Set<Color> keys = map.keySet();
		Color modalColor = new Color(0);
		for (Iterator<Color> iterator = keys.iterator(); iterator.hasNext();) {
			Color color = (Color) iterator.next();
			if (map.get(color).equals(max)) {
				return color;
			}
			modalColor = color;
		}
		return modalColor;
	}

	private Integer getGreatestValueFromMap(HashMap<Color, Integer> map) {
		// TODO Auto-generated method stub
		Collection<Integer> values = map.values();
		TreeSet<Integer> orderedSet = new TreeSet<>();
		
		for (Iterator<Integer> iterator = values.iterator(); iterator.hasNext();) {
			Integer temp = (Integer) iterator.next();
			orderedSet.add(temp);
		} 
		return orderedSet.last();
		
	}

	private void incrementValue(HashMap<Color, Integer> map, Color key) {
		// TODO Auto-generated method stub
		Integer value = map.get(key);
		int newValue = value.intValue() + 1 ;
		map.replace(key, newValue);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.run("");
	}
	
	
	
}
