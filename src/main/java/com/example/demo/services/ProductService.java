package com.example.demo.services;

import com.example.demo.commands.ProductForm;
import com.example.demo.domain.Product;

import java.util.List;


public interface ProductService {

    List<Product> listAll();

    Product getById(String id);

    Product saveOrUpdate(Product product);

    void delete(String id);

    Product saveOrUpdateProductForm(ProductForm productForm);
}
