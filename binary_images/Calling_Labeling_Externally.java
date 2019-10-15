package binary_images;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import labeling.Labeling_Binary_Image;

public class Calling_Labeling_Externally implements PlugInFilter {

	private ImagePlus imp;

	@Override
	public void run(ImageProcessor ip) {
		// TODO Auto-generated method stub
		Labeling_Binary_Image labeler = new Labeling_Binary_Image();
		labeler.setup("", imp);
		labeler.run(ip);
		HashMap<Color, LinkedHashSet<Point>> islands = labeler.getComponentsMap();
		
		ImagePlus colorImp = labeler.getColorImp();
		
		Set<Color> cores = islands.keySet();
		for (Iterator<Color> iterator = cores.iterator(); iterator.hasNext();) {
			Color color = (Color) iterator.next();
			System.out.println("------- Cor: "+color.toString());
			LinkedHashSet<Point> tempPontos = islands.get(color);
			int s = Math.round((color.getBlue()+color.getGreen()+color.getRed())/3);
			for (Iterator<Point> iterator2 = tempPontos.iterator(); iterator2.hasNext();) {
				Point point = (Point) iterator2.next();
				System.out.println(point.toString());
				 
				ip.set(point.x, point.y,s);
			}
		}
		
		
		}

	@Override
	public int setup(String arg0, ImagePlus imp) {
		// TODO Auto-generated method stub
		this.imp = imp;
		return DOES_8G;
	}

}
