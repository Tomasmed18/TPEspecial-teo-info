package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ArchivoTexto {
	
	BufferedReader br;
	
	public ArchivoTexto(String nombreArchivo) throws IOException{
		File dir = new File(".");
		System.out.println("Cargando archivo '" + dir.getCanonicalPath() + File.separator + nombreArchivo +"'" );
		File fin = new File(dir.getCanonicalPath() + File.separator + nombreArchivo);
		this.cargarArchivo(fin);
	}
	
	public void cargarArchivo(File fin) throws IOException {
		FileInputStream fis = new FileInputStream(fin);
		br = new BufferedReader(new InputStreamReader(fis));
	}
	
	public String next() throws IOException{
		String line = null;
		line = br.readLine();
		
		if (line == null)
			br.close();
		return line;
	}
	
}
