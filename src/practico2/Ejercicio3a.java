package practico2;

public class Ejercicio3a {
	private static final float[] probAcumPrimerSimbolo = {1f, 1f, 1f};
	private static final float[][] probAcumSiguienteSimbolo = {{1f/4f,   3f/4f, 0f},
													       {3f/4f, 1f, 1f/2f},
													       {1f,   1f,   1f}};
	
	public static final float EPSILON = 0.000001f;
	
	private boolean converge(float prob1, float prob2){
		return (Math.abs((prob1 - prob2)) < EPSILON);
	}
	
	
	public int primerSimbolo(){
		float x = (float) Math.random();
		for (int i = 0; i < probAcumPrimerSimbolo.length; i++)
			if (x < probAcumPrimerSimbolo[i])
				return i;
		return -1;
	}
	
	public int siguienteSimbolo(int anterior){
		float x = (float) Math.random();
		for (int i = 0; i < probAcumSiguienteSimbolo.length; i++){
			if (x < probAcumSiguienteSimbolo[i][anterior])
				return i;}
		return -1;
	}
	
	public float probabilidadEmision(int simbolo, int instante){
		int exitos = 0;
		int emisiones = 0;
		float prob = 0;
		float prob_ant = -1;
		
		while (!converge(prob_ant, prob) || emisiones < 1000){
			int s = primerSimbolo();
			for (int i = 1; i <= instante; i++){
				s = siguienteSimbolo(s);
			}
			if (s == simbolo)
				exitos++;
			emisiones++;
			prob_ant = prob;
			prob = (float) (exitos) / emisiones;
		}
		return prob;
	}
	
	
	
	public static void main(String[] args){
		Ejercicio3a ej3 = new Ejercicio3a();
		System.out.println("Porbabilidad de emitir un 0 en el instante 1: " + ej3.probabilidadEmision(0, 1));
		System.out.println("Porbabilidad de emitir un 0 en el instante 2: " + ej3.probabilidadEmision(0, 2));
		System.out.println("Porbabilidad de emitir un 0 en el instante 3: " + ej3.probabilidadEmision(0, 3));
	}
}
