package tp_especial;

import util.OperacionesArreglo;
import util.OperacionesMatriz;

public class Canales {
	private static final float EPSILON = 0.00000000001f;
	
	
	public static float ruido(ImagenEscalaGrises entrada, ImagenEscalaGrises salida){
		float[][] matConjunta = Probabilidades.matrizProbabilidadesConjuntas(entrada, salida); //P(X, Y)
		float[][] matCondicional = Probabilidades.matrizTransicion(entrada, salida); //P(Y/X)
		
		float sumatoria = 0;
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++)
				if (matCondicional[i][j] != 0)
					sumatoria += (matConjunta[i][j] * Math.log10(matCondicional[i][j]));
		
		return (sumatoria * (float) (-3.322)); //se multiplica por 3.322 para pasar de log10 a log2
	} 
	
	public static float perdida(ImagenEscalaGrises entrada, ImagenEscalaGrises salida){
		float[][] matConjunta = Probabilidades.matrizProbabilidadesConjuntas(entrada, salida); //P(X, Y)
		float[][] matCondicional = Probabilidades.matrizTransicion(salida, entrada); // P(X/Y)
		
		float sumatoria = 0;
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++)
				if (matCondicional[j][i] != 0)
					sumatoria += (matConjunta[i][j] * Math.log10(matCondicional[j][i]));
									/*se usa j para filas e i para columnas porque el método 
									 * Probabilidades.matrizTransicion(IN, OUT) pone IN en las columnas
									 * y como en este caso se usó con la salida como IN, hay que usar la matriz al reves 
									 */
		
		return (sumatoria * (float) (-3.322)); //se multiplica por 3.322 para pasar de log10 a log2
	}
	
	public static float informacionMutua(ImagenEscalaGrises entrada, ImagenEscalaGrises salida){
		float[][] matConjunta = Probabilidades.matrizProbabilidadesConjuntas(entrada, salida); //P(X, Y)
		float[] probX = new float[16]; // P(X)
		float[] probY = new float[16]; // P(Y)
		OperacionesArreglo.inicializarArreglo(probX, 0);
		OperacionesArreglo.inicializarArreglo(probY, 0);
		
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++){
				probX[j] += matConjunta[i][j];
				probY[i] += matConjunta[i][j];
			}
		
		float sumatoria = 0;
		
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++)
				if ((matConjunta[i][j] != 0) && (probX[j] != 0) && (probY[i] != 0)){ //para que de bien el logaritmo
					float l = matConjunta[i][j] / (probX[j] * probY[i]);
					sumatoria += matConjunta[i][j] * Math.log10(l);
				}
		
		return sumatoria * (float) (3.322); //se multiplica por 3.322 para pasar de log10 a log2
	}
	
	
	public static float ruidoPorSimulacion(Fuente fuente, Canal canal, int iteraciones){
		int[][] ocurrencias = new int[16][16];
		OperacionesMatriz.inicializar(ocurrencias, 0, 16, 16);
		int[] ocurrenciasX = new int[16];
		OperacionesArreglo.inicializarArreglo(ocurrenciasX, 0);
		
		float ruido = 0f;
		int it = 0; //contador de iteraciones
		while (iteraciones >= ++it){
			int entrada = fuente.emitir(); //se emite el simbolo
			int salida = canal.transmitirSimbolo(entrada); //se pasa por el canal
			ocurrenciasX[entrada/17]++;
			ocurrencias[salida/17][entrada/17]++;
			
			ruido = 0;
			for (int i = 0; i < 16; i++)
				for (int j = 0; j < 16; j++)
					if ((ocurrencias[i][j] != 0) && (ocurrenciasX[j] != 0))
						ruido += ((float)ocurrencias[i][j] / (float) it) * Math.log10((float) ocurrencias[i][j] / (float) ocurrenciasX[j]);
					/*  ocurrencias[i][j] / it = P(i, j)
					 *  ocurrencias[i][j] / ocurrenciasX[j] = P(i/j)
					 */
			ruido = ruido * (float) (-3.322);

		}

		
		return ruido;
	}
	
	public static float ruidoPorSimulacion(Fuente fuente, Canal canal){
		int[][] ocurrencias = new int[16][16];
		OperacionesMatriz.inicializar(ocurrencias, 0, 16, 16);
		int[] ocurrenciasX = new int[16];
		OperacionesArreglo.inicializarArreglo(ocurrenciasX, 0);
		
		float ruido = 0f;
		float ruidoAnterior = -1f;
		int it = 0; //contador de iteraciones
		while (!converge(ruido, ruidoAnterior) || (it < 10000)){
			ruidoAnterior = ruido;
			it++;
			
			int entrada = fuente.emitir(); //se emite el simbolo
			int salida = canal.transmitirSimbolo(entrada); //se pasa por el canal
			ocurrenciasX[entrada/17]++;
			ocurrencias[salida/17][entrada/17]++;
			
			ruido = 0;
			for (int i = 0; i < 16; i++)
				for (int j = 0; j < 16; j++)
					if ((ocurrencias[i][j] != 0) && (ocurrenciasX[j] != 0))
						ruido += ((float)ocurrencias[i][j] / (float) it) * Math.log10((float) ocurrencias[i][j] / (float) ocurrenciasX[j]);
					/*  ocurrencias[i][j] / it = P(i, j)
					 *  ocurrencias[i][j] / ocurrenciasX[j] = P(i/j)
					 */
			ruido = ruido * (float) (-3.322);
		}
		return ruido;
	}
	
	public static float perdidaPorSimulacion(Fuente fuente, Canal canal, int iteraciones){
		int[][] ocurrencias = new int[16][16];
		OperacionesMatriz.inicializar(ocurrencias, 0, 16, 16);
		int[] ocurrenciasY = new int[16];
		OperacionesArreglo.inicializarArreglo(ocurrenciasY, 0);
		
		float perdida = 0f;
		int it = 0; //contador de iteraciones
		while (iteraciones >= ++it){
			int entrada = fuente.emitir();
			int salida = canal.transmitirSimbolo(entrada);
			ocurrenciasY[salida/17]++;
			ocurrencias[salida/17][entrada/17]++;
			
			perdida = 0f;
			for (int i = 0; i < 16; i++)
				for (int j = 0; j < 16; j++)
					if ((ocurrencias[i][j] != 0) && (ocurrenciasY[i] != 0))
						perdida += ((float)ocurrencias[i][j] / (float) it) * Math.log10((float) ocurrencias[i][j] / (float) ocurrenciasY[i]);
					/*  ocurrencias[i][j] / it = P(i, j)
					 *  ocurrencias[i][j] / ocurrenciasY[i] = P(j/i)
					 */
			perdida = perdida * (float) (-3.322);
		}
		return perdida;
	}
	
	public static float perdidaPorSimulacion(Fuente fuente, Canal canal){
		int[][] ocurrencias = new int[16][16];
		OperacionesMatriz.inicializar(ocurrencias, 0, 16, 16);
		int[] ocurrenciasY = new int[16];
		OperacionesArreglo.inicializarArreglo(ocurrenciasY, 0);
		
		float perdida = 0f;
		float perdidaAnterior = -1f;
		int it = 0; //contador de iteraciones
		while (!converge(perdida, perdidaAnterior) || (it < 10000)){
			perdidaAnterior = perdida;
			it++;
			
			int entrada = fuente.emitir(); //se emite el simbolo
			int salida = canal.transmitirSimbolo(entrada); //se pasa por el canal
			ocurrenciasY[salida/17]++;
			ocurrencias[salida/17][entrada/17]++;
			
			perdida = 0;
			for (int i = 0; i < 16; i++)
				for (int j = 0; j < 16; j++)
					if ((ocurrencias[i][j] != 0) && (ocurrenciasY[i] != 0))
						perdida += ((float)ocurrencias[i][j] / (float) it) * Math.log10((float) ocurrencias[i][j] / (float) ocurrenciasY[i]);
					/*  ocurrencias[i][j] / it = P(i, j)
					 *  ocurrencias[i][j] / ocurrenciasY[i] = P(j/i)
					 */
			perdida = perdida * (float) (-3.322);
		}
		return perdida;
	}
	
	public static float informacionMutuaPorSimulacion(Fuente fuente, Canal canal, int iteraciones){
		int[][] ocurrencias = new int[16][16];
		OperacionesMatriz.inicializar(ocurrencias, 0, 16, 16);
		int[] ocurrenciasX = new int[16];
		OperacionesArreglo.inicializarArreglo(ocurrenciasX, 0);
		int[] ocurrenciasY = new int[16];
		OperacionesArreglo.inicializarArreglo(ocurrenciasY, 0);
		
		float infoMutua = 0f;
		int it = 0; //contador de iteraciones
		while (iteraciones >= ++it){
			int entrada = fuente.emitir();
			int salida = canal.transmitirSimbolo(entrada);
			ocurrenciasX[entrada/17]++;
			ocurrenciasY[salida/17]++;
			ocurrencias[salida/17][entrada/17]++;
			
			infoMutua = 0f;
			for (int i = 0; i < 16; i++)
				for (int j = 0; j < 16; j++){
					float probXY = ((float)ocurrencias[i][j] / (float) it); //P(X, Y)
					float probX = ((float)ocurrenciasX[j] / (float) it); //P(X)
					float probY = ((float)ocurrenciasY[i] / (float) it); //P(Y)
					if ((probXY != 0) && (probX != 0) && (probY != 0))
						infoMutua += probXY * Math.log10((probXY) / (probX * probY));
								// P(X, Y) * log2(P(X, Y)/P(X)P(Y))
				}
						
			infoMutua = infoMutua * (float) (3.322);
		}
		return infoMutua;
	}
	
	public static float informacionMutuaPorSimulacion(Fuente fuente, Canal canal){
		int[][] ocurrencias = new int[16][16];
		OperacionesMatriz.inicializar(ocurrencias, 0, 16, 16);
		int[] ocurrenciasX = new int[16];
		OperacionesArreglo.inicializarArreglo(ocurrenciasX, 0);
		int[] ocurrenciasY = new int[16];
		OperacionesArreglo.inicializarArreglo(ocurrenciasY, 0);
		
		float infoMutua = 0f;
		float infoMutuaAnterior = -1f;
		int it = 0; //contador de iteraciones
		
		while (!converge(infoMutua, infoMutuaAnterior) || (it < 10000)){
			infoMutuaAnterior = infoMutua;
			it++;
			
			int entrada = fuente.emitir();
			int salida = canal.transmitirSimbolo(entrada);
			ocurrenciasX[entrada/17]++;
			ocurrenciasY[salida/17]++;
			ocurrencias[salida/17][entrada/17]++;
			
			infoMutua = 0f;
			for (int i = 0; i < 16; i++)
				for (int j = 0; j < 16; j++){
					float probXY = ((float)ocurrencias[i][j] / (float) it);
					float probX = ((float)ocurrenciasX[j] / (float) it);
					float probY = ((float)ocurrenciasY[i] / (float) it);
					if ((probXY != 0) && (probX != 0) && (probY != 0))
						infoMutua += probXY * Math.log10((probXY) / (probX * probY));
				}
						
			infoMutua = infoMutua * (float) (3.322);
		}
		return infoMutua;
	}
	

	private static boolean converge(float n, float m) {
		return (Math.abs(n - m) < EPSILON);
	}
	
	
}
