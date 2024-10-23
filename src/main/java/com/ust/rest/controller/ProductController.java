package com.ust.rest.controller;

//import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ust.rest.model.Product;

@RestController //Controller+responseBody //Spring MVC-Response-we return a ViewResolver-write to body of html
@RequestMapping("/product/api1.0")
public class ProductController {
	
	ArrayList<Product> productList=new ArrayList<>();
	{
	productList.add(new Product(101,"Nike","Feature walk",1234,15000));
	productList.add(new Product(102,"Adidas","Comfort walk",134,12800));
	productList.add(new Product(103,"Puma","Firm Grip",456,1672));
	}
	
	
	@GetMapping(value="/product/{id}",produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getById(@PathVariable ("id") long id)
	{
		Optional<Product> optional =productList.stream().filter(product->product.getProductId()==id).findFirst();
		return ResponseEntity.status(HttpStatus.OK).body(optional.get());
	}
	
	
	@PostMapping(value="/addProduct",consumes=MediaType.APPLICATION_JSON_VALUE
			,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> addproduct(@RequestBody Product product)
	{
		boolean flag=false;
		if(product!=null)
			flag=productList.add(product);
		return flag?ResponseEntity.status(201).body(product):ResponseEntity.status(404).body(null);
	}
	
	
	@PutMapping(value="/modify",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> updateProduct(@RequestBody Product product)
	{
		Optional<Product> optional=productList.stream().filter(p->p.getProductId()==product.getProductId()).findFirst();
		
		Product temp=optional.get();
		temp.setBrand(product.getBrand());
		temp.setDescription(product.getDescription());
		temp.setQty(product.getQty());
		temp.setPrice(product.getPrice());
//		int index=productList.indexOf(temp);
//		productList.remove(index);
//		productList.add(index,temp);
		return null;	
	}
	
	@DeleteMapping(value="/delete/{id}")
	public void removeProduct(@PathVariable ("id") long id)
	{
		Optional<Product> optional =productList.stream().filter(p->p.getProductId()==id).findFirst();
		productList.remove(optional.get());
	}
	
	
	@GetMapping(value="/productsinfo",produces=MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<List<Product>> displayProducts()
     {
		return ResponseEntity.ok( productList);
     }
}
