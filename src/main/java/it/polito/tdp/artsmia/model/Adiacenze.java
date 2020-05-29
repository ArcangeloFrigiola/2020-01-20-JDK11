package it.polito.tdp.artsmia.model;

public class Adiacenze {
	
	private Artist artista1;
	private Artist artista2;
	private int peso;
	/**
	 * @param artista1
	 * @param artista2
	 * @param peso
	 */
	public Adiacenze(Artist artista1, Artist artista2, int peso) {
		super();
		this.artista1 = artista1;
		this.artista2 = artista2;
		this.peso = peso;
	}
	public Artist getArtista1() {
		return artista1;
	}
	public void setArtista1(Artist artista1) {
		this.artista1 = artista1;
	}
	public Artist getArtista2() {
		return artista2;
	}
	public void setArtista2(Artist artista2) {
		this.artista2 = artista2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
}
