package com.model;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ProductParser {

		public static List<Produit> fromJson(String productsJson) {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			
			return gson.fromJson(productsJson, new TypeToken<List<Produit>>() {
			}.getType());
		}
		
		public static String toJson(List<Produit> products) {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			
			return gson.toJson(products);
		}
		
		public static void main(String[] args) {
			String prodJson="[{'reference':'pr007','name':'ProdName007','description':'Pr007 Description','price':12760,'status':'DISPONIBLE'},{'reference':'pr0056','name':'ProdName0056','description':'Pr0056 Description','price':56090,'status':'DISPONIBLE'}]";
			System.out.println(fromJson(prodJson).get(0).getPrice());
			System.out.println(fromJson(prodJson).get(1).getPrice());
		

		}
}
