package com.maxtrain.bootcamp.prs.requestline;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.maxtrain.bootcamp.prs.product.Product;
import com.maxtrain.bootcamp.prs.request.Request;

@Entity
public class Requestline {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int quantity;
	@ManyToOne
	@JoinColumn(name="requestID")
	private Request request;
	@ManyToOne
	@JoinColumn(name="productID")
	private Product product;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Requestline() {}
	
	@Override
	public String toString() {
		return String.format("Reqline: %s", 0);
	}
}
