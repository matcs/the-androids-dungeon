package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Category;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.model.ProductCategory;
import com.matcss.androidsdungeon.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategory> findAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory findProductCategoryById(int id) {
        return productCategoryRepository.findByProductCategoryId(id);
    }

    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public ProductCategory updateProductCategory(int id, ProductCategory productCategoryDto) {
        ProductCategory productCategory = findProductCategoryById(id);
        productCategory.setProduct(new Product(productCategoryDto.getProduct().getProductId()));
        productCategory.setCategory(new Category(productCategory.getProductCategoryId()));

        return productCategoryRepository.save(productCategoryDto);
    }

    public ProductCategory deleteProductCategory(int id) {
        ProductCategory productCategory = findProductCategoryById(id);
        if (productCategory != null)
            productCategoryRepository.delete(productCategory);
        return productCategory;
    }
}
