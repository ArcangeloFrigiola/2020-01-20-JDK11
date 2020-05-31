package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao;
	private Graph<Artist, DefaultWeightedEdge> grafo;
	private Map<Integer, Artist> mappaArtisti;
	private List<Adiacenze> listaAdiacenze;
	private String artistiConnessi;
	
	public Model(){
		this.dao = new ArtsmiaDAO();
	}
	
	public List<String> getAllRoles(){
		return this.dao.listRoles();
	}
	
	public void generateGraph(String ruolo) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.mappaArtisti= new HashMap<>(this.dao.mapArtistByRole(ruolo));
		this.listaAdiacenze = new ArrayList<>(this.dao.listAdiacenze(ruolo, mappaArtisti));
		artistiConnessi = "Artisti Connessi: \n";
		
		Graphs.addAllVertices(this.grafo, this.mappaArtisti.values());
		
		for(Adiacenze a: this.listaAdiacenze) {
			
			if(!this.grafo.containsEdge(a.getArtista1(), a.getArtista2())){
				Graphs.addEdgeWithVertices(this.grafo, a.getArtista1(), a.getArtista2(), a.getPeso());
				artistiConnessi += a.getArtista1().getName()+"("+a.getArtista1().getIdArtist()+") - "
				+a.getArtista2().getName() +"("+a.getArtista2().getIdArtist()+"), expo comuni: "+a.getPeso()+"\n";
			}
			
		}
		
	}
	
	public String getArtistiConnessi() {
		return this.artistiConnessi;
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}

	public Artist controllaCodiceInserito(int codiceArtista) {
		
		for(Adiacenze a: this.listaAdiacenze) {
			if(a.getArtista1().getIdArtist()==codiceArtista) {
				return a.getArtista1();
			}else if(a.getArtista2().getIdArtist()==codiceArtista) {
				return a.getArtista2();
			}
		}
		return null;
	}
	
	public String cercaPercorso(Artist artista) {
		
		Ricorsione ric = new Ricorsione();
		List<Artist> cammino = new ArrayList<>(ric.cercaCamminoMassimo(this.grafo, artista));
		String result = "Esposizioni coinvolte: "+ (cammino.size()-1)+"\n";
		for(Artist a: cammino) {
			result+=a.getName()+" ("+a.getIdArtist()+")\n";
		}
		return result;
	}

}
