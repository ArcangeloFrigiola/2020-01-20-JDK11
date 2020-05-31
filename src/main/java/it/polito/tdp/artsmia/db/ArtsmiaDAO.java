package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenze;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
    public List<String> listRoles() {
		
		String sql = "SELECT DISTINCT(role) AS rl " + 
				"FROM authorship "+
				"ORDER BY role";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				result.add(res.getString("rl"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
    
	public Map<Integer, Artist> mapArtistByRole(String ruolo) {

		String sql = "SELECT DISTINCT(ar.artist_id) AS id, ar.name AS nome " + 
				"FROM artists AS ar, authorship AS au " + 
				"WHERE ar.artist_id=au.artist_id AND au.role=? ";
		
		Map<Integer, Artist>  result = new HashMap<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				result.put(res.getInt("id") ,new Artist(res.getInt("id"), res.getString("nome")));
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenze> listAdiacenze(String ruolo, Map<Integer, Artist> mappaArtisti) {

		String sql = "SELECT au1.artist_id, au2.artist_id, COUNT(DISTINCT eo2.exhibition_id) AS peso " + 
				"FROM exhibition_objects AS eo1, exhibition_objects AS eo2, authorship AS au1, authorship AS au2 " + 
				"WHERE au1.object_id = eo1.object_id AND au2.object_id = eo2.object_id  " + 
				"AND au1.artist_id!=au2.artist_id AND eo2.exhibition_id=eo1.exhibition_id " + 
				"AND au2.role=? AND au1.role=? " + 
				"GROUP BY au1.object_id, au2.object_id "+
				"ORDER BY peso DESC ";
		
		List<Adiacenze> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);
			st.setString(2, ruolo);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				result.add(new Adiacenze(mappaArtisti.get(res.getInt("au1.artist_id")), 
						mappaArtisti.get(res.getInt("au2.artist_id")), res.getInt("peso")));
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> listaPesiNelGrafo(String ruolo, Map<Integer, Artist> mappaArtisti) {

		String sql = "SELECT DISTINCT COUNT(DISTINCT eo2.exhibition_id) AS peso " + 
				"FROM exhibition_objects AS eo1, exhibition_objects AS eo2, authorship AS au1, authorship AS au2 " + 
				"WHERE au1.object_id = eo1.object_id AND au2.object_id = eo2.object_id  " + 
				"AND au1.artist_id!=au2.artist_id AND eo2.exhibition_id=eo1.exhibition_id " + 
				"AND au2.role=? AND au1.role=? " + 
				"GROUP BY au1.object_id, au2.object_id "+
				"ORDER BY peso DESC ";
		
		List<Integer> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);
			st.setString(2, ruolo);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				result.add(res.getInt("peso"));
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
