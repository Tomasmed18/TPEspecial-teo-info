package tp_especial;

public class Indicadores {
	
	public static float media(int[] valores){
		int suma = 0;
		for (int i = 0; i < valores.length ; i++){
			suma += valores[i];
		}
		return (float) suma/ valores.length;
	}
	
	public static float desviacionEstandar(int[] valores){
		float media = Indicadores.media(valores);
		float sumatoria = 0;
		for (int i = 0; i < valores.length; i++){
			sumatoria += Math.pow((i-media), 2); // (x[i] - <X>)^2
		}
		sumatoria = sumatoria / (valores.length - 1); // (sumatoria / (N - 1))
		return (float) Math.sqrt(sumatoria);
	}
}
