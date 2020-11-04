package com.example.moviecatalog.datamodel;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

public class TvsResponse {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private ArrayList<TvsItem> results;

	@SerializedName("total_results")
	private int totalResults;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(ArrayList<TvsItem> results){
		this.results = results;
	}

	public ArrayList<TvsItem> getResults(){
		return results;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	@Override
 	public String toString(){
		return 
			"TvsResponse{" +
			"page = '" + page + '\'' +
			",total_pages = '" + totalPages + '\'' +
			",results = '" + results + '\'' +
			",total_results = '" + totalResults + '\'' +
			"}";
		}
}