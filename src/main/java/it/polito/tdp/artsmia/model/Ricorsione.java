package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Ricorsione {
	
	private Graph<Artist, DefaultWeightedEdge> grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	private List<Artist> best = new ArrayList<>();
	
	public List<Artist> cercaCamminoMassimo(Graph<Artist, DefaultWeightedEdge> grafo, Artist artistaPartenza) {
		
		this.grafo = grafo;
		
		List<Integer> listaPesiPaesePartenza = new ArrayList<>();
		List<Artist> vicini = new ArrayList<>(Graphs.neighborListOf(this.grafo, artistaPartenza));
		for(Artist a: vicini) {
			
			int num = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(artistaPartenza, a));
			if(!listaPesiPaesePartenza.contains(num)) {
				listaPesiPaesePartenza.add(num);
			}
			
		}
		
		for(Integer pesoArco: listaPesiPaesePartenza) {
			
			List<Artist> parziale = new ArrayList<>();
			parziale.add(artistaPartenza);
			cerca(parziale, artistaPartenza, pesoArco);
			
		}
		
		return this.best;
		
	}
	
	private void cerca(List<Artist> parziale, Artist artistaPrecedente, int pesoArco) {
		
		if(parziale.size()>=this.best.size()) {
			this.best = new ArrayList<>(parziale);
		}
		
		List<Artist> vicini = new ArrayList<>(Graphs.neighborListOf(this.grafo, artistaPrecedente));
		
		for(Artist a: vicini) {
			
			if(!parziale.contains(a)) {
				
				DefaultWeightedEdge arco = this.grafo.getEdge(artistaPrecedente, a);
				int peso = (int) this.grafo.getEdgeWeight(arco);
				if(peso==pesoArco) {
					parziale.add(a);
					cerca(parziale, a, pesoArco);
					parziale.remove(a);
				}
			}
			
		}
	}
}
