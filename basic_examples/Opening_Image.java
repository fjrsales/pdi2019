package basic_examples;

import ij.ImagePlus;
import ij.io.OpenDialog;
import ij.io.Opener;
import ij.plugin.PlugIn;

public class Opening_Image implements PlugIn {

	@Override
	public void run(String arg0) {
		// TODO Auto-generated method stub
		
		// Carregar a janela para selecionar a imagem [somente para selecionar o caminho do arquivo]
		OpenDialog op = new OpenDialog("Select an image to open:", "//home//fernando", null);
		String path = op.getPath();				// Selecionando o caminho da imagem
		Opener opener = new Opener();			// Classe que abre a imagem
		ImagePlus imp = opener.openImage(path);	// Abre a imagem e retorna um objeto ImagePlus
		imp.show();								// Mostra a imagem
	}

}
