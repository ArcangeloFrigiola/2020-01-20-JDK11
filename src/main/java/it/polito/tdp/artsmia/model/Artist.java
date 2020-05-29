package it.polito.tdp.artsmia.model;

public class Artist {
	
	private int idArtist;
	private String name;
	/**
	 * @param idArtist
	 * @param name
	 */
	public Artist(int idArtist, String name) {
		super();
		this.idArtist = idArtist;
		this.name = name;
	}
	public int getIdArtist() {
		return idArtist;
	}
	public void setIdArtist(int idArtist) {
		this.idArtist = idArtist;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
