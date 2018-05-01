package practico2;

public class Ejercicio2b {
	
	private static final float[] probAcumPrimerDia = {1f/3f, 2f/3f, 3f/3f};
	private static final float[][] probAcumSiguienteDia = {{0f,   1f/4f, 1f/4f},
													       {1f/2f, 3f/4f, 1f/2f},
													       {1f,   1f,   1f}};
													
	
	public static final int SOLEADO = 0;
	public static final int NUBLADO = 1;
	public static final int LLUVIOSO = 2;
	public static final float EPSILON = 0.000001f;
	
	private boolean converge(float[] v1, float[] v2){
		for (int i = 0; i < v1.length; i++){
			if (Math.abs((v1[i] - v2[i])) > EPSILON)
				return false;
		}
		return true;
	}
	
	
	public int primerDia(){
		float x = (float) Math.random();
		for (int i = 0; i < probAcumPrimerDia.length; i++)
			if (x < probAcumPrimerDia[i])
				return i;
		return -1;
	}
	
	public int diaSiguiente(int anterior){
		float x = (float) Math.random();
		for (int i = 0; i < probAcumSiguienteDia.length; i++){
			if (x < probAcumSiguienteDia[i][anterior])
				return i;}
		return -1;
	}
	
	public float[] calcularVectorEstacionario(){
		int[] emisiones = {0, 0, 0};
		int pasos = 0;
		
		float[] vEst = {0f, 0f, 0f};
		float[] vEst_ant = {-1f, 0f, 0f};
		
		int d = primerDia();
		
		while (!converge(vEst_ant, vEst) || (pasos < 1000)){
			d = diaSiguiente(d);
			
			emisiones[d]++;
			pasos++;
			for (int i = 0; i < vEst.length; i++){
				vEst_ant[i] = vEst[i];
				vEst[i] = (float)emisiones[i]/pasos;
			}
			
		}
		return vEst;
	}
	
	
	
	public static void main(String[] args){
		
		float[] vecEst = (new Ejercicio2b()).calcularVectorEstacionario();
		System.out.println("Vector estacionario: ");
		for (int i = 0; i < vecEst.length; i++){
			System.out.println("v[" + i + "] = " + vecEst[i]);
		}
	}
}
