package sydney;

import java.util.ArrayList;
import java.util.Random;

public class museu {
	static Random gerador = new Random();
	public static void main(String[] args) {
		int ordem = 40;
		Aresta[] arestas = new Aresta[120];
		int[][] matriz = new int[ordem][ordem];
		
		for (int i = 0; i < 120; i++) {
			arestas[i] = arestaAleatoria(ordem, matriz);
		}
		ArrayList<Integer> melhorCaminho = floydWarshall(ordem, arestas);
		String saida = "Melhor caminho: ";
		for (Integer i : melhorCaminho) {
			saida += ((i + 1) + " -> ");
		}
		System.out.println(saida.substring(0, saida.length() - 4) + "\n\n");
		gerarGrafo(ordem, arestas, melhorCaminho);
	}
	
	public static int verticeAleatorio(int[] verts) {
		return verts[gerador.nextInt(verts.length)];
	}
	
	public static Aresta arestaAleatoria(int ordem, int[][] matriz) {
		int orig = 1;
		int dest = 1;
		int distancia = 100;
		//evitar loops, melhor exigir que arestas tenham origem e destino diferentes
		while (orig == dest || distancia > 4 || matriz[orig][dest] != 0) {
			orig = gerador.nextInt(ordem);
			dest = gerador.nextInt(ordem);
			//distancia = Math.abs(orig/6 - dest/6) + Math.abs(orig % 6 - dest % 6);
			distancia = Math.abs(orig - dest);
		}
		matriz[orig][dest] = distancia;
		matriz[dest][orig] = distancia;
		return new Aresta(orig, dest, gerador.nextInt(10)+1);
	}
	
	public static ArrayList<Integer> floydWarshall(int ordem, Aresta[] arestas) {
		int[][] distancias = new int[ordem][ordem];
		int[][] prox = new int[ordem][ordem];
		for (int i = 0; i < ordem; i++) {
			for (int j = 0; j < ordem; j++) {
				if (i == j) {
					distancias[i][j] = 0;
					prox[i][j] = j;
				} else {
					distancias[i][j] = 100000;
					prox[i][j] = -1;
				}
			}
		}
		for (Aresta a : arestas) {
			if (a.valor < distancias[a.orig][a.dest]) {
				distancias[a.orig][a.dest] = a.valor;
				distancias[a.dest][a.orig] = a.valor;
				prox[a.orig][a.dest] = a.dest;
				prox[a.dest][a.orig] = a.orig;
			}
		}
		for (int k = 0; k < ordem; k++) {
			for (int i = 0; i < ordem; i++) {
				for (int j = 0; j < ordem; j++) {
					if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
						distancias[i][j] = distancias[i][k] + distancias[k][j];
						prox[i][j] = prox[i][k];
					}
				}
			}
		}
		
		return caminho(ordem, 0, ordem - 1, prox);
	}
	
	public static ArrayList<Integer> caminho(int ordem, int orig, int dest, int[][] prox) {
		ArrayList<Integer> saida = new ArrayList<Integer>();
		if (prox[orig][dest] == -1) {
			return saida;
		}
		saida.add(orig);
		while (orig != dest) {
			orig = prox[orig][dest];
			saida.add(orig);
		}
		return saida;
	}
	
	//use http://www.webgraphviz.com/ para visualizar a saída
	public static void gerarGrafo(int ordem, Aresta[] arestas, ArrayList<Integer> caminho) {
		String saida = "graph ";
		String seta = " -- ";
		saida += "\"grafo\" {\nnode [width=1.0,height=1.0];\n";
		for (int i = 0; i < ordem; i++) {
			String nomeVert = "N" + (i + 1);
			if(i == 0) {
				nomeVert = "Entrada";
			}
			if (i == ordem - 1) {
				nomeVert = "Saída";
			}
			saida += "N" + (i + 1) + " [label=\"" + nomeVert;
			saida += "\",fontsize=18];\n";
		}
		for (int i = 0; i < arestas.length; i++) {
			saida += "N" + (arestas[i].orig + 1) + seta; //origem
			saida += "N" + (arestas[i].dest + 1) + " [";
			saida += "label=" + arestas[i].valor + ",";
			//System.out.println(arestas[i].toString() + "," + caminho.indexOf(arestas[i].dest) + "," + caminho.indexOf(arestas[i].orig));
			int acheiDest = caminho.indexOf(arestas[i].dest);
			int acheiOrig = caminho.indexOf(arestas[i].orig);
			if(Math.abs(acheiDest - acheiOrig) == 1 && acheiDest > -1 && acheiOrig > -1) {
				saida += "color=\"blue\",fontcolor=\"blue\",";
			}
			saida += "weight=1,style=\"setlinewidth(2.0)\",fontsize=16];\n";
		}
		saida += "}";
		System.out.println(saida);
	}
}
