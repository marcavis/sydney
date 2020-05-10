package sydney;

public class Aresta {
	public int orig;
	public int dest;
	public int valor;
	
	public Aresta(int orig, int dest, int valor) {
		this.orig = orig;
		this.dest = dest;
		this.valor = valor;
	}
	public int getOrig() {
		return orig;
	}
	public void setOrig(int orig) {
		this.orig = orig;
	}
	public int getDest() {
		return dest;
	}
	public void setDest(int dest) {
		this.dest = dest;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return "N" + (orig + 1) + " -- N" + (dest + 1);
	}
}
