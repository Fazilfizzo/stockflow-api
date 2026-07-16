package com.fizoind.stockflow_api.product.controller;

import com.fizoind.stockflow_api.product.dto.ProductCreateDTO;
import com.fizoind.stockflow_api.product.dto.ProductResponseDTO;
import com.fizoind.stockflow_api.product.service.ProductService;
import com.fizoind.stockflow_api.supplier.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestPart("dto") ProductCreateDTO productCreateDTO, @RequestPart("image") MultipartFile file) throws IOException{
        return new ResponseEntity<>(productService.createProduct(productCreateDTO, file), HttpStatus.CREATED);
    }

//    @GetMapping("/products")
//    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
//        logger.info("Starting fetch all products request");
//        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
//    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponseDTO>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String keyword
    ) {
        Pageable pageable = PageRequest.of(page, size);
        logger.info("Starting fetch products fetch page");
        return new ResponseEntity<>(productService.getProducts(pageable, keyword), HttpStatus.OK);
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("products/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        Path imagePath = Paths.get("uploads").resolve(filename);
        org.springframework.core.io.Resource resource = new UrlResource(imagePath.toUri());

        if(!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(imagePath);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

//    @GetMapping("/products/search")
//    public ResponseEntity<List<ProductResponseDTO>> search(@RequestParam String keyword) {
//        return new ResponseEntity<>(productService.search(keyword), HttpStatusCode.valueOf(200));
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
