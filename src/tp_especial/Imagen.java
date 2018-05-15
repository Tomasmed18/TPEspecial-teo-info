package tp_especial;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Imagen {
	BufferedImage img = null;
	int alto;
	int ancho;
	
	public Imagen(String nombreArchivo){
		try{
			img = ImageIO.read(new File(nombreArchivo));
		}catch (IOException e){
			System.out.println("No se pudo cargar la imagen '" + nombreArchivo + "'");
			e.printStackTrace();
		}
		
		alto = img.getHeight();
		ancho = img.getWidth();
	}
	
	
	public int getAlto(){
		return alto;
	}
	
	public int getAncho(){
		return ancho;
	}
	
	public int getPixel(int x, int y){
		return img.getRGB(x, y);
	}
	
	public int getRed(int x, int y){
		return (this.getPixel(x, y) >> 16) & 0x000000FF;
	}
	
	public int getGreen(int x, int y){
		return (this.getPixel(x, y) >> 8) & 0x000000FF;
	}
	
	public int getBlue(int x, int y){
		return (this.getPixel(x, y)) & 0x000000FF;
	}
	
	public int[] getArregloPixeles(){ //va recorriendo la imagen por columnas
		int[] arreglo = new int[this.getAlto() * this.getAncho()];
		int alto = this.getAlto();
		int ancho = this.getAncho();
		for (int x = 0; x < ancho; x++)
			for (int y = 0; y < alto; y++){
				arreglo[x * alto + y] = this.getPixel(x, y);
			}
		return arreglo;
	}
	
	public int[][] getMatrizPixeles(){
		int[][] matriz = new int[this.getAlto()][this.getAncho()];
		for (int x = 0; x < this.getAlto(); x++)
			for (int y = 0; y < this.getAncho(); y++){
				matriz[x][y] = getPixel(x, y);
			}
		return matriz;
	}
	
	public Imagen(int[] arrPixeles, int alto, int ancho) {
		img= new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < ancho; x++)
			for (int y = 0; y < alto; y++){
				int aux=arrPixeles[x * alto + y];
				int a=aux<<24;
				int r=aux<<16;
				int g=aux<<8;
				int b=aux;
				int color=0;
				color=a|r|g|b;
				img.setRGB(x, y, color );
			}
		
		
	}
	
	public void guardarImagen(String nombre) {
		try {
			ImageIO.write(img, "bmp", new File(nombre));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
