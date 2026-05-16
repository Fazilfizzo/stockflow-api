package com.fizoind.stockflow_api.order.service;

import com.fizoind.stockflow_api.authentication.CustomUserDetails;
import com.fizoind.stockflow_api.customer.entity.Customer;
import com.fizoind.stockflow_api.customer.exception.CustomerNotFoundException;
import com.fizoind.stockflow_api.customer.repository.CustomerRepository;
import com.fizoind.stockflow_api.order.dto.OrderCreateDTO;
import com.fizoind.stockflow_api.order.dto.OrderResponseDTO;
import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.fizoind.stockflow_api.order.entity.OrderStatus;
import com.fizoind.stockflow_api.order.exception.OrderNotFoundException;
import com.fizoind.stockflow_api.order.mapper.OrderMapper;
import com.fizoind.stockflow_api.order.repository.CustomerOrderRepository;
import com.fizoind.stockflow_api.order.utils.OrderUtils;
import com.fizoind.stockflow_api.orderItem.dto.OrderItemDTO;
import com.fizoind.stockflow_api.orderItem.entity.OrderItem;
import com.fizoind.stockflow_api.orderItem.repository.OrderItemRepository;
import com.fizoind.stockflow_api.product.entity.Product;
import com.fizoind.stockflow_api.product.entity.ProductStatus;
import com.fizoind.stockflow_api.product.exception.InactiveProductException;
import com.fizoind.stockflow_api.product.exception.ProductNotFoundException;
import com.fizoind.stockflow_api.product.repository.ProductRepository;
import com.fizoind.stockflow_api.stockmovement.entity.MovementType;
import com.fizoind.stockflow_api.stockmovement.entity.StockMovement;
import com.fizoind.stockflow_api.stockmovement.exception.InsufficientStockException;
import com.fizoind.stockflow_api.stockmovement.repository.StockMovementRepository;
import com.fizoind.stockflow_api.stockmovement.service.StockMovementService;
import com.fizoind.stockflow_api.supplier.entity.SupplierStatus;
import com.fizoind.stockflow_api.supplier.exception.InactiveSupplierException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;
    private final StockMovementService stockMovementService;

    public OrderService(CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, StockMovementRepository stockMovementRepository, StockMovementService stockMovementService) {
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.stockMovementService = stockMovementService;
    }

    @Transactional
    public void createCustomerOrder(OrderCreateDTO dto) {

        Long customerId = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser().getId();

        log.info("Starting to create order..............");
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        log.debug("Customer with id {} exists..........", customerId);

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomer(customer);
        customerOrder.setStatus(OrderStatus.PENDING);
        customerOrder.setOrderDate(LocalDateTime.now());

        customerOrder = customerOrderRepository.save(customerOrder);

        BigDecimal total = BigDecimal.ZERO;

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId()).orElseThrow(() -> new ProductNotFoundException(itemDTO.getProductId()));

            if (product.getStatus().equals(ProductStatus.INACTIVE)) {
                throw new InactiveProductException("Product is INACTIVE");
            }

            if (product.getSupplier().getStatus().equals(SupplierStatus.INACTIVE)) {
                throw new InactiveSupplierException("Supplier is INACTIVE");
            }

            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new InsufficientStockException(itemDTO.getProductId());
            }

            OrderItem item = new OrderItem();
            item.setOrder(customerOrder);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPriceAtOrderTime(product.getPrice());

            BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            item.setSubTotal(subTotal);

            total = total.add(subTotal);
            orderItems.add(item);

            // save OrderItem
            item = orderItemRepository.save(item);

            // update stock, come here, stockMovement
            stockMovementService.stockOrderOut(product, itemDTO, customerOrder.getId());

        }
        customerOrder.setOrderItems(orderItems);
        customerOrder.setTotalAmount(total);

        // save again (updates + cascades OrderItems)
        customerOrderRepository.save(customerOrder);
    }

    public OrderResponseDTO getCustomerOrder(Long orderId) {

        Long customerId = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser().getId();


        CustomerOrder customerOrder = customerOrderRepository.findByIdAndCustomerId(orderId, customerId).orElseThrow(() -> new RuntimeException("Order Not Found"));
        if (!customerOrder.getCustomer().getId().equals(customerId)) {
            throw new RuntimeException("Unauthorized!!!!!!!!!!!!!!!!!!!!!");
        }
        return OrderMapper.toOrderResponseDto(customerOrder);
    }

    public List<OrderResponseDTO> getCustomerOrders() {

        Long customerId = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser().getId();

        System.out.println(customerId);

        if (!customerOrderRepository.existsById(customerId)) {
         log.debug("Customer with id {} does not exist", customerId);
        }

       return customerOrderRepository.findByCustomerId(customerId)
               .stream()
               .map(OrderMapper::toOrderResponseDto)
               .toList();
    }

    public List<OrderResponseDTO> getAllOrders() {
        return customerOrderRepository.findAll()
                .stream()
                .map(OrderMapper::toOrderResponseDto)
                .toList();
    }

    public List<OrderResponseDTO> getCustomerOrdersByStatus(Long customerId, OrderStatus status) {

//        Long customerId = ((CustomUserDetails) SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getPrincipal())
//                .getUser().getId();

        return customerOrderRepository.findByCustomerIdAndStatus(customerId, status)
                .stream()
                .map(OrderMapper::toOrderResponseDto)
                .toList();
    }

    public List<OrderResponseDTO> getOrdersByStatus(OrderStatus status) {
        return customerOrderRepository.findByStatus(status)
                .stream()
                .map(OrderMapper::toOrderResponseDto)
                .toList();
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus) {
       CustomerOrder customerOrder = customerOrderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found!!!!!!!!!!!"));

        if (!OrderUtils.isValiTransition(customerOrder.getStatus(), newStatus)) {
            throw new RuntimeException("Invalid status transition");
        }

        customerOrder.setStatus(newStatus);
        customerOrder = customerOrderRepository.save(customerOrder);

        return OrderMapper.toOrderResponseDto(customerOrder);
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Long customerId = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser().getId();

        if (!customerOrderRepository.existsByIdAndCustomer_User_Id(orderId, customerId)) {
            throw new OrderNotFoundException(orderId);
        }

        CustomerOrder customerOrder = customerOrderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        if (customerOrder.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders are cancelled");
        }

        for (OrderItem item : customerOrder.getOrderItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            product = productRepository.save(product);
            StockMovement stockMovement = new StockMovement();
            stockMovement.setProduct(product);
            stockMovement.setQuantity(item.getQuantity());
            stockMovement.setMovementType(MovementType.CANCEL);
            stockMovement.setReason("order is cancelled.");
            stockMovement.setReference("CANCEL-ORDER-" + orderId);
            stockMovement.setMovementDate(LocalDateTime.now());
            stockMovementRepository.save(stockMovement);
        }
       customerOrder.setStatus(OrderStatus.CANCELLED);
    }
}
