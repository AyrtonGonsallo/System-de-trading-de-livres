package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.model.Produit;
import com.model.StatusProduit;

public class ProduitDaoImpl implements ProduitDao {
	Connexion c=new Connexion();
	@Override
	public List<Produit> getList() {
		
		Connection conn = c.getConn();
		List<Produit> list = new ArrayList<Produit>();
		try {
			String sql="select * from produit where status='DISPONIBLE' ";
			Statement st=conn.prepareStatement(sql);
		
			ResultSet rs=st.executeQuery(sql);
			
			while(rs.next()) {
				Produit p=new Produit();
				p.setDescription(rs.getString("description"));
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getInt("price"));
				p.setReference(rs.getString("reference"));
				p.setStatus(StatusProduit.valueOf(rs.getString("status")));
				p.setCategory(rs.getString("category"));
				list.add(p);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<Produit> getListALL() {
		
		Connection conn = c.getConn();
		List<Produit> list = new ArrayList<Produit>();
		try {
			String sql="select * from produit";
			Statement st=conn.prepareStatement(sql);
		
			ResultSet rs=st.executeQuery(sql);
			
			while(rs.next()) {
				Produit p=new Produit();
				p.setDescription(rs.getString("description"));
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getInt("price"));
				p.setReference(rs.getString("reference"));
				p.setStatus(StatusProduit.valueOf(rs.getString("status")));
				p.setCategory(rs.getString("category"));
				list.add(p);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<Produit> get3List() {
		int nbr=0;
		Connection conn = c.getConn();
		List<Produit> list = new ArrayList<Produit>();
		try {
			String sql="select * from produit where status='DISPONIBLE' ";
			Statement st=conn.prepareStatement(sql);
		
			ResultSet rs=st.executeQuery(sql);
			
			while(rs.next()) {
				if(nbr<=2){
					Produit p=new Produit();
					p.setDescription(rs.getString("description"));
					p.setId(rs.getInt("id"));
					p.setName(rs.getString("name"));
					p.setPrice(rs.getInt("price"));
					p.setReference(rs.getString("reference"));
					p.setStatus(StatusProduit.valueOf(rs.getString("status")));
					p.setCategory(rs.getString("category"));
					list.add(p);
				}
				nbr++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void updateVENDU() {
		Connection conn = c.getConn();
		try {
			String sql="update produit set status='VENDU' where status='DISPONIBLE' ";
			PreparedStatement st=conn.prepareStatement(sql);
		
			st.execute();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		

	}
	@Override
	public void updateVENDU(int id) {
		Connection conn = c.getConn();
		try {
			String sql="update produit set status='VENDU' where status='DISPONIBLE' and id=?";
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, id);
			st.execute();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		

	}
	@Override
	public void updateDISP(int id) {
		Connection conn = c.getConn();
		try {
			String sql="update produit set status='DISPONIBLE' where status='VENDU' and id=?";
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, id);
			st.execute();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		

	}
	@Override
	public void updateDISP() {
		Connection conn = c.getConn();
		try {
			String sql="update produit set status='DISPONIBLE' where status='VENDU' ";
			PreparedStatement st=conn.prepareStatement(sql);
		
			st.execute();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		

		
	}

}
