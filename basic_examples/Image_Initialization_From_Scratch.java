package basic_examples;

import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.NewImage;
import ij.plugin.PlugIn;

public class Image_Initialization_From_Scratch implements PlugIn {

	@Override
	public void run(String arg0) {
		// Inicializacao das variaveis
		int width = 0; 
		int height = 0; 
		int slices = 0;
		String title = "";
		
		GenericDialog gd = new GenericDialog("Image Info:");	// Cria a janela de dialogo
		gd.addNumericField("Width (pix):", 250, 0);				// Insere campo numerico
		gd.addNumericField("Height (pix):", 150, 0);
		gd.addNumericField("Slices (un):", 5, 0);
		gd.addStringField("Title:", "New Image");				// Insere campo de texto
		gd.pack();												// Junta tudo e empacota
		gd.showDialog();										// Mostra o display
		
		if (gd.wasOKed()) {										// Entra somente se o "OK" for clicado
			width = (int) gd.getNextNumber(); 					// Retorna o próximo numero (double) e faz conversao (int) 
			height = (int) gd.getNextNumber(); 					// Retorna o próximo numero (double) e faz conversao (int)
			slices = (int) gd.getNextNumber();
			title = gd.getNextString();
		}
		ImagePlus imp = NewImage.createRGBImage(title, width, height, slices, NewImage.FILL_WHITE);	// Metodo para criar uma imagem nova
		imp.show();												// Mostra a imagem
	}
	
 

}
