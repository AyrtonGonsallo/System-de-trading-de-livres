package com.dao;

import java.util.List;

import com.model.Produit;

public interface ProduitDao {
	public List<Produit> getListALL();
	public List<Produit> getList();
	public List<Produit> get3List();
	public void updateDISP(int id);
	public void updateDISP();
	public void updateVENDU(int id);
	public void updateVENDU();
}
