package com.fizoind.stockflow_api.product.service;

import com.fizoind.stockflow_api.category.entity.Category;
import com.fizoind.stockflow_api.category.exception.CategoryNotFoundException;
import com.fizoind.stockflow_api.category.repository.CategoryRepository;
import com.fizoind.stockflow_api.file.FileStorageService;
import com.fizoind.stockflow_api.product.dto.ProductCreateDTO;
import com.fizoind.stockflow_api.product.dto.ProductResponseDTO;
import com.fizoind.stockflow_api.product.entity.Product;
import com.fizoind.stockflow_api.product.exception.ProductNotFoundException;
import com.fizoind.stockflow_api.product.mapper.ProductMapper;
import com.fizoind.stockflow_api.product.repository.ProductRepository;
import com.fizoind.stockflow_api.supplier.entity.Supplier;
import com.fizoind.stockflow_api.supplier.entity.SupplierStatus;
import com.fizoind.stockflow_api.supplier.exception.SupplierNotFoundException;
import com.fizoind.stockflow_api.supplier.repository.SupplierRepository;
import com.fizoind.stockflow_api.supplier.service.SupplierService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Value("${default.localhost}")
    private String url;

    @Value("${app.upload.dir}")
    private String uploadDir;

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    public ProductService(ProductRepository productRepository, SupplierRepository supplierRepository, CategoryRepository categoryRepository, FileStorageService fileStorageService) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.categoryRepository = categoryRepository;
        this.fileStorageService = fileStorageService;
    }

    public ProductResponseDTO createProduct(ProductCreateDTO productCreateDTO, MultipartFile file) throws IOException{
        Supplier supplier = supplierRepository.findById(productCreateDTO.getSupplierId()).orElseThrow(() -> new SupplierNotFoundException(productCreateDTO.getSupplierId()));
        if ((supplier.getStatus() != SupplierStatus.ACTIVE)) {
            throw new RuntimeException("Supplier is not ACTIVE");
        }

        Category category = categoryRepository.findById(productCreateDTO.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(productCreateDTO.getCategoryId()));

        Product product = ProductMapper.toEntity(productCreateDTO, supplier, category);
        product.setSku((category.getName().substring(0, 3).toUpperCase() + "-" + productRepository.countByCategory(category) + 1));
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url);
        urlBuilder.append("products/images/");
        urlBuilder.append(fileStorageService.saveFile(file));
        product.setImageUrl(urlBuilder.toString());
        Product saved_product = productRepository.save(product);
        return ProductMapper.toproductResponseDTO(saved_product);
    }


    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.getAllProducts()
                .stream()
                .map(ProductMapper::toproductResponseDTO)
                .toList();
    }

    public ProductResponseDTO getProductById(Long product_id) {
        Product product = productRepository.findById(product_id).orElseThrow(() -> new ProductNotFoundException(product_id));
        return ProductMapper.toproductResponseDTO(product);
    }

//    public List<ProductResponseDTO> search(String keyword) {
//        return productRepository.findByNameContainingIgnoreCase(keyword)
//                .stream()
//                .map(ProductMapper::toproductResponseDTO)
//                .toList();
//    }

    public Page<ProductResponseDTO> getProducts(Pageable pageable, String keyword) {
        Page<Product> products;

        if (keyword == null || keyword.isBlank()) {
            products = productRepository.findAll(pageable);
        } else {
            products = productRepository.search(keyword, pageable);
        }

        return products.map(ProductMapper::toproductResponseDTO);
    }

    public void checkStock() {
        List<String> products_low_stock = productRepository.getLowStockProducts();

        logger.info("Products with low stock: {}", products_low_stock);
    }

   public void deleteProduct(Long product_id) {
       Product product = productRepository.findById(product_id).orElseThrow(() -> new ProductNotFoundException(product_id));
       productRepository.delete(product);
   }
}
