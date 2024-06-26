package com.lefpap.honeyeshop.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v.1.0/products")
public class ProductController {
    private final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductDao productDao;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> queryProducts(@Valid @ModelAttribute ProductsFilters filters) {
        LOG.info("Querying products with filters: {}", filters);
        List<Product> products = productDao.findAll(filters.toSpecification());
        LOG.info("Found {} products", products.size());
        return products;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProduct(@PathVariable(name = "id") Long productId) {
        LOG.info("Getting product with id: {}", productId);
        Product product = productDao.findById(productId).orElseThrow(EntityNotFoundException::new);
        LOG.info("Found product: {}", product);
        return product;
    }
}
