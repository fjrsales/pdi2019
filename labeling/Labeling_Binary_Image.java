package labeling;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.Random;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Labeling_Binary_Image implements PlugInFilter {

	private Color BACKGND = Color.WHITE;
	private Color FOREGND = Color.BLACK;
	private int COLOR_BOUND = 0xf000000; //256 x 256 x 256 tons
	private int w;
	private int h;
	private HashSet<Integer> visited;
	private LinkedHashSet<Color> colorSet;
	private HashMap<Color,LinkedHashSet<Point>> componentsMap;
	private int colorSeed;
	private Random rand;
	private Color currentColor;
	private int[] labeledPixels;
	
	@Override
	public void run(ImageProcessor ip) {
		// TODO Auto-generated method stub
			
		byte[] pixels = (byte[]) ip.getPixels();
		
		PriorityQueue<Integer> queue = new PriorityQueue<>();		
		LinkedHashSet<Point> tempSet;
		
		for (int index = 0; index < pixels.length; index++) {
			int pix = (int) pixels[index];
						
			if (isCandidate4NewComponent(index,pix)) {
				tempSet = new LinkedHashSet<>();
				generateNewColor();
				paintAndMarkAsVisited(index,tempSet);
				checkNeighborhood(pixels, index, queue,tempSet);
				while (!queue.isEmpty()) {
					int dummy = queue.poll().intValue();
					paintAndMarkAsVisited(dummy,tempSet);
					checkNeighborhood(pixels, dummy, queue,tempSet);
				}
				componentsMap.put(currentColor, tempSet);
				System.out.println("Total de Componentes: "+componentsMap.size()+"\tCor: "+currentColor.toString()+"\t Pixels:"+tempSet.size());
				//System.out.println(tempSet.toString());
			}
			
			
		}
		ImagePlus colorImp = NewImage.createRGBImage("Labeled Image", w, h, 1, NewImage.FILL_WHITE);
		ImageProcessor colorIp = colorImp.getProcessor();
		colorImp.show();
		colorIp.setPixels(labeledPixels);
		colorImp.updateAndDraw();
	}
		

	private boolean isCandidate4NewComponent(int index, int pix) {
		// TODO Auto-generated method stub
		if (!isCandidate(index, pix)) {
			return false;
		}else {
			boolean notPaintedYet = BACKGND.equals(new Color(labeledPixels[index]));
			return notPaintedYet;	
		}
		
	}


	private boolean isCandidate(int index, int pix) {
		// TODO Auto-generated method stub
		return (FOREGND.equals(new Color(pix))) && (!wasVisited(index));
	}



	private void paintAndMarkAsVisited(int index, LinkedHashSet<Point> tempSet) {
		// TODO Auto-generated method stub
		labeledPixels[index] = currentColor.getRGB();
		this.setVisited(index);
		this.add2TempSet(index,tempSet);
	}



	private void add2TempSet(int index, LinkedHashSet<Point> tempSet) {
		// TODO Auto-generated method stub
		tempSet.add(new Point(getX(index),getY(index)));
	}
	

	private void setVisited(int index) {
		// TODO Auto-generated method stub
		visited.add(index);
	}

	private void generateNewColor() {
		Color c;
		do {
			c = this.selectRandomColor();
		} while (colorSet.contains(c));
		setCurrentColor(c);
	}
	
	private void setCurrentColor(Color c) {
		// TODO Auto-generated method stub
		this.currentColor = c;
	}

	private Color selectRandomColor() {
		return new Color(rand.nextInt(COLOR_BOUND));
	}
	
	public void checkNeighborhood(byte[] pixels, int index, PriorityQueue<Integer> queue, LinkedHashSet<Point> tempSet) {
		
		int x = this.getX(index);
		int y = this.getY(index);
				
		for (int v = y - 1; v < y + 2; v++) {
			for (int u = x - 1; u < x + 2; u++) {
				if (isInside(u, v)) {
					int tempIndex = getIndex(u, v);
					int pix = (int) pixels[tempIndex];
					if ((isCandidate(tempIndex, pix))&&(!queue.contains(tempIndex))) {
						queue.add(tempIndex);
					}
				}
			}		
		}
			
	}
	
	private int getIndex(int u, int v) {
		// TODO Auto-generated method stub 
		return v*w+u;
	}

	private boolean isInside(int x,int y) {
		return (x >= 0 && x < w && y >= 0 && y < h);
	}

	public boolean wasVisited(int index) {
		return visited.contains(index);
	}
	
	public int getX(int index) {
		return index%w;
	}
	
	public int getY(int index) {
		return Math.floorDiv(index, w);
	}
	
	@Override
	public int setup(String arg0, ImagePlus imp) {
		// TODO Auto-generated method stub
		this.w = imp.getWidth();
		this.h = imp.getHeight();
		this.colorSet = new LinkedHashSet<>();
		this.componentsMap = new HashMap<>();
		this.colorSeed = 0;
		this.rand = new Random(colorSeed);
		this.labeledPixels = new int[w*h];
		Arrays.fill(labeledPixels, BACKGND.getRGB());
		this.visited = new HashSet<>();
		return DOES_8G;
	}

}
