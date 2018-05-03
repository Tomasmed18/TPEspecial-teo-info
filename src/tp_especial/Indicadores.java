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
			sumatoria += Math.pow((valores[i]-media), 2); // (x[i] - <X>)^2
			
		}
		sumatoria = sumatoria / (valores.length - 1); // (sumatoria / (N - 1))
		return (float) Math.sqrt(sumatoria);
	}
	
	public static float correlacionCruzada(int[] valores1, int[] valores2){
		float sumatoria = 0f;
		for (int i = 0; i < valores1.length; i++)
			sumatoria += (float) valores1[i] * (float) valores2[i];
		return (float)sumatoria / valores1.length;
	}
	
	public static float covarianzaCruzada(int[] valores1, int[] valores2){
		float correlacion = Indicadores.correlacionCruzada(valores1, valores2);
		float media1 = Indicadores.media(valores1);
		float media2 = Indicadores.media(valores2);
		
		return correlacion - (media1 * media2);
	}
	
	public static float coeficienteCorrelacionCruzada(int[] valores1, int[] valores2){
		float covarianza = Indicadores.covarianzaCruzada(valores1, valores2);
		float desvio1 = Indicadores.desviacionEstandar(valores1);
		float desvio2 = Indicadores.desviacionEstandar(valores2);
		
		return covarianza / (desvio1 * desvio2);
	}
}


