package util;

public class OperacionesArreglo {
	
	public static boolean converge(float[] v1, float[] v2, float epsilon){
		for (int i = 0; i < v1.length; i++){
			if (Math.abs((v1[i] - v2[i])) > epsilon)
				return false;
		}
		return true;
	}
	
	static public void inicializarArreglo(int[] arreglo, int valor)
	{
		for (int i = 0; i < arreglo.length; i++) 
		{
			arreglo[i] = valor;
		}
	}
	
	static public void inicializarArreglo(float[] arreglo, float valor)
	{
		for (int i = 0; i < arreglo.length; i++) 
		{
			arreglo[i] = valor;
		}
	}
	
	static public void copiarArreglo(float[] original, float[] copia) 
	{
		for (int i = 0; i < copia.length; i++) 
		{
			copia[i] = original[i];
		}
	}
}
