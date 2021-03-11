
public class MatrixThread extends Thread{
	
	public int sumatorio;
	int[] fila;
	int[] columna;
	
	public MatrixThread(int[] fila, int[] columna) {
		super();
		sumatorio=0;
		this.fila=fila;
		this.columna=columna;
	}
	
	@Override
	public void run() {
		for(int i=0;i<fila.length;i++) {
			sumatorio+=fila[i]*columna[i];
		}
	}
}
