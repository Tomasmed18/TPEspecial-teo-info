package tp_especial;

public class Fuente {

	private float[] probabilidadesEmision;
	private float[] probabilidadesEmisionAcumulada;
	public Fuente(ImagenEscalaGrises entrada) {
		
		this.probabilidadesEmision=entrada.getProbabilidadesGrises();
		this.probabilidadesEmisionAcumulada=util.OperacionesArreglo.acumularArreglo(probabilidadesEmision);
	}
	
	
	public int emitir() {
		double prob=Math.random();
		
		int i=0;
		while(prob>this.probabilidadesEmisionAcumulada[i]) {
			i++;
		}
		
		return i;
		
		}
	
	
		
}

