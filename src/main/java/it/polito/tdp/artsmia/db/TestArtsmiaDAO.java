package it.polito.tdp.artsmia.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenze;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Exhibition;

public class TestArtsmiaDAO {

	public static void main(String[] args) {

		ArtsmiaDAO dao = new ArtsmiaDAO();

		/*System.out.println("Test objects:");
		List<ArtObject> objects = dao.listObjects();
		System.out.println(objects.get(0));
		System.out.println(objects.size());
		
		System.out.println("Test exhibitions:");
		List<Exhibition> exhibitions = dao.listExhibitions();
		System.out.println(exhibitions.get(0));
		System.out.println(exhibitions.size());*/
		
		
		/*Map<Integer, Artist> map = new HashMap<>(dao.mapArtistByRole("artist"));
		for(Artist ar : map.values()) {
			System.out.println(ar.getName());
		}*/
		List<Adiacenze> list = new ArrayList<>(dao.listAdiacenze("artist", dao.mapArtistByRole("artist")));
		for(Adiacenze a : list) {
			System.out.println(a.getArtista1().getName()+" "+a.getArtista2().getName()+" "+a.getPeso());
		}

	}

}
