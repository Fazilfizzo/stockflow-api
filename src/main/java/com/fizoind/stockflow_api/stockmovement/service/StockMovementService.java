package com.fizoind.stockflow_api.stockmovement.service;

import com.fizoind.stockflow_api.cartItem.entity.CartItem;
import com.fizoind.stockflow_api.orderItem.dto.OrderItemDTO;
import com.fizoind.stockflow_api.product.entity.Product;
import com.fizoind.stockflow_api.product.exception.ProductNotFoundException;
import com.fizoind.stockflow_api.product.repository.ProductRepository;
import com.fizoind.stockflow_api.stockmovement.dto.StockInDTO;
import com.fizoind.stockflow_api.stockmovement.dto.StockMovementResponseDTO;
import com.fizoind.stockflow_api.stockmovement.entity.MovementType;
import com.fizoind.stockflow_api.stockmovement.entity.StockMovement;
import com.fizoind.stockflow_api.stockmovement.exception.InsufficientStockException;
import com.fizoind.stockflow_api.stockmovement.mapper.StockMovementMapper;
import com.fizoind.stockflow_api.stockmovement.repository.StockMovementRepository;
import com.fizoind.stockflow_api.supplier.entity.Supplier;
import com.fizoind.stockflow_api.supplier.entity.SupplierStatus;
import com.fizoind.stockflow_api.supplier.exception.InactiveSupplierException;
import com.fizoind.stockflow_api.supplier.exception.SupplierNotFoundException;
import com.fizoind.stockflow_api.supplier.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockMovementService {
    private static final Logger log = LoggerFactory.getLogger(StockMovementService.class);

     private final StockMovementRepository stockMovementRepository;
     private final ProductRepository productRepository;
     private final SupplierRepository supplierRepository;

    public StockMovementService(ProductRepository productRepository, StockMovementRepository stockMovementRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.supplierRepository = supplierRepository;
    }

    public StockMovementResponseDTO stockIn(StockInDTO stockInDTO) {
        log.debug("product id: " + stockInDTO.getProductId());
        Product product = productRepository.findById(stockInDTO.getProductId()).orElseThrow(() -> new ProductNotFoundException(stockInDTO.getProductId()));

        Supplier supplier = supplierRepository.findById(stockInDTO.getSupplierId()).orElseThrow(() -> new SupplierNotFoundException(stockInDTO.getSupplierId()));

        if (supplier.getStatus().equals(SupplierStatus.INACTIVE)) {
            throw new InactiveSupplierException("Supplier is INACTIVE. You can't order");
        }

        StockMovement stockMovement = new StockMovement();
        stockMovement = StockMovementMapper.toEntity(stockInDTO, product);
        stockMovement.setMovementType(MovementType.RESTOCK);
        stockMovement.setMovementDate(LocalDateTime.now());
        stockMovement.setQuantity(stockInDTO.getQuantity());
        stockMovement = stockMovementRepository.save(stockMovement);
        stockMovement.setReference("RESTOCK-" + stockMovement.getId());
        stockMovement.setSupplier(supplier);
        product.setStockQuantity(product.getStockQuantity() + stockInDTO.getQuantity());
        product = productRepository.save(product);
        StockMovement saved_stockMovement = stockMovementRepository.save(stockMovement);
        return StockMovementMapper.toResponseDTO(saved_stockMovement);
    }

//    public void stockValidation(Integer quantity) {
//        if (quantity > stockMovementRepository.getCurrentStock()) {
//
//        }
//    }

    public StockMovementResponseDTO stockOut(StockInDTO stockInDTO) {
        Product product = productRepository.findById(stockInDTO.getProductId()).orElseThrow(() -> new ProductNotFoundException(stockInDTO.getProductId()));
        if(stockInDTO.getQuantity() > product.getStockQuantity()){
            throw new InsufficientStockException(stockInDTO.getProductId());
        }
        log.debug("Stock out validation successful");

        StockMovement stockMovement = StockMovementMapper.toEntity(stockInDTO, product);
        stockMovement.setMovementType(MovementType.OUT);
        stockMovement.setMovementDate(LocalDateTime.now());
        stockMovement.setReference("OUT-" + stockMovement.getId());
        stockMovement.setQuantity(stockInDTO.getQuantity());
        product.setStockQuantity(product.getStockQuantity() - stockInDTO.getQuantity());
        product = productRepository.save(product);
        StockMovement saved_stockMovement = stockMovementRepository.save(stockMovement);
        return StockMovementMapper.toResponseDTO(saved_stockMovement);
    }

    public void stockOrderOut(Product product, OrderItemDTO itemDTO, Long orderId) {
        if(itemDTO.getQuantity() > product.getStockQuantity()){
            throw new InsufficientStockException(product.getId());
        }
        log.debug("Stock out validation successful");
        StockMovement stockMovement = new StockMovement();
        stockMovement.setQuantity(itemDTO.getQuantity());
        stockMovement.setMovementType(MovementType.SALE);
        stockMovement.setReason("Customer order sale");
        stockMovement.setReference("ORDER-" + orderId);
        stockMovement.setMovementDate(LocalDateTime.now());
        stockMovement.setProduct(product);
//        int updated_stock = productRepository.reduceStock(product.getId(), itemDTO.getQuantity());
        product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
        product = productRepository.save(product);
        stockMovementRepository.save(stockMovement);
    }

    public void stockOrderOutFromCart(Product product, CartItem item, Long orderId) {
        if(item.getQuantity() > product.getStockQuantity()){
            throw new InsufficientStockException(product.getId());
        }
        log.debug("Stock out validation successful");
        StockMovement stockMovement = new StockMovement();
        stockMovement.setQuantity(item.getQuantity());
        stockMovement.setMovementType(MovementType.SALE);
        stockMovement.setReason("Customer order sale");
        stockMovement.setReference("ORDER-" + orderId);
        stockMovement.setMovementDate(LocalDateTime.now());
        stockMovement.setProduct(product);
//        int updated_stock = productRepository.reduceStock(product.getId(), itemDTO.getQuantity());
        product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
        product = productRepository.save(product);
        stockMovementRepository.save(stockMovement);
    }


    public String getCurrentStock(Long product_id) {
        Product product = productRepository.findById(product_id).orElseThrow(() -> new ProductNotFoundException(product_id));
        Integer current_stock = product.getStockQuantity();
        return "Current stock of product " + product.getName() + " : " + current_stock;
    }

    public List<StockMovementResponseDTO> getStockHistory(Long product_id) {
        if(!productRepository.existsById(product_id)) {
            throw new ProductNotFoundException(product_id);
        }
        return stockMovementRepository.findByProductIdOrderByMovementDateDesc(product_id)
                .stream()
                .map(StockMovementMapper::toResponseDTO)
                .toList();
    }

}
