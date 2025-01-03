package com.example.bookstoreecommerceapi.services.impl;

import com.example.bookstoreecommerceapi.dto.OrderDetailRequest;
import com.example.bookstoreecommerceapi.dto.OrderRequest;
import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
import com.example.bookstoreecommerceapi.exceptions.OrderNotFoundException;
import com.example.bookstoreecommerceapi.models.Book;
import com.example.bookstoreecommerceapi.models.Order;
import com.example.bookstoreecommerceapi.models.OrderDetail;
import com.example.bookstoreecommerceapi.models.TimeLineEntry;
import com.example.bookstoreecommerceapi.repositories.BookRepository;
import com.example.bookstoreecommerceapi.repositories.OrderDetailRepository;
import com.example.bookstoreecommerceapi.repositories.OrderRepository;
import com.example.bookstoreecommerceapi.repositories.TimeLineEntryRepository;
import com.example.bookstoreecommerceapi.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private TimeLineEntryRepository timeLineEntryRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseObject getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return new ResponseObject(HttpStatus.OK, "Thành công", orders);
    }

    @Override
    public PaginationResponse getAllOrdersPaginationAndSorting(int page, int size, String sort) {
        Sort.Direction direction = sort.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String property = direction == Sort.Direction.DESC ? sort.substring(1) : sort;
        Pageable pageable = PageRequest.of(page, size, direction, property);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        PaginationResponse paginationResponse =
                new PaginationResponse(orderPage.getTotalElements(), orderPage.getContent(), orderPage.getTotalPages(), orderPage.getNumber());
        return paginationResponse;
    }

    @Override
    public ResponseObject getChartData() {
        // Get the current year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        String status = "Hoàn tất";  // Status to filter

        // Fetch orders from the repository for the current year with status "Hoàn tất"
        List<Order> orders = orderRepository.findCompletedOrdersByCurrentYear(currentYear, status);

        // Aggregate data by month
        Map<String, List<Order>> ordersByMonth = orders.stream()
                .collect(Collectors.groupingBy(order -> new SimpleDateFormat("MM-yyyy").format(order.getDateCreatedOrdinal())));

        List<String> labels = new ArrayList<>();
        List<Long> totalOrders = new ArrayList<>();
        List<Long> totalPrices = new ArrayList<>();

        // Iterate over all months of the current year
        Calendar calendar = Calendar.getInstance();
        int income = 0;
        for (int month = 0; month <= currentMonth; month++) {
            calendar.set(currentYear, month, 1);
            String monthLabel = new SimpleDateFormat("MM-yyyy").format(calendar.getTime());

            if (ordersByMonth.containsKey(monthLabel)) {
                List<Order> monthlyOrders = ordersByMonth.get(monthLabel);

                labels.add(monthLabel.substring(0, 2));
                totalOrders.add((long) monthlyOrders.size());

                long totalPrice = 0;
                for (Order order : monthlyOrders) {
                    totalPrice += order.getTotalPrice();
                }
                income += totalPrice;
                totalPrices.add(totalPrice);
            } else {
                // If no orders for this month, add zero values
                labels.add(monthLabel.substring(0, 2));
                totalOrders.add(0L);
                totalPrices.add(0L);
            }
        }

        // Package data into a map
        Map<String, Object> chartData = new HashMap<>();
        chartData.put("labels", labels);
        chartData.put("totalOrders", totalOrders);
        chartData.put("totalPrices", totalPrices);
        chartData.put("income", income);

        return new ResponseObject(HttpStatus.OK, "Thành công", chartData);
    }

    @Override
    public ResponseObject getOrderById(long id) throws OrderNotFoundException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            return new ResponseObject(HttpStatus.OK, "Thành công", orderOptional.get());
        } else {
            throw new OrderNotFoundException("Đơn hàng không tồn tại");
        }
    }

    @Override
    public ResponseObject getOrderByEmail(String email) {
        List<Order> list = orderRepository.findByCustomerEmail(email);
        return new ResponseObject(HttpStatus.OK, "Thành công", list);
    }


    @Transactional
    @Override
    public ResponseObject addNewOrder(OrderRequest orderRequest) throws BookNotFoundException {
        Date currentDate = new Date();
        Order order = Order.builder()
                .customer(orderRequest.getCustomer())
                .status("Đang xử lý")
                .note(orderRequest.getNote())
                .shippingTax(orderRequest.getShippingTax())
                .dateCreated(currentDate)
                .build();
        Order savedOrder = orderRepository.save(order);
        TimeLineEntry timeLineEntry = TimeLineEntry.builder()
                .event(orderRequest.getCustomer().getName() + " đặt hàng trên shop")
                .dateCreated(currentDate)
                .order(savedOrder)
                .build();
        timeLineEntryRepository.save(timeLineEntry);
        if (orderRequest.getOrderDetailRequests() != null)
            for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetailRequests()) {
                Optional<Book> optionalBook = bookRepository.findById(orderDetailRequest.getBookId());
                if (!optionalBook.isPresent()) throw new BookNotFoundException("Sách không tồn tại");
                else {
                    OrderDetail orderDetail = OrderDetail.builder()
                            .book(optionalBook.get())
                            .order(order)
                            .quantity(orderDetailRequest.getQuantity())
                            .build();
                    orderDetailRepository.save(orderDetail);
                }
            }
        ResponseObject responseObject = new ResponseObject(HttpStatus.CREATED, "Thành công", savedOrder);
        return responseObject;
    }

    @Override
    public ResponseObject updateOrderPartially(long id, Map<String, Object> fields) throws OrderNotFoundException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order orderDB = orderOptional.get();
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Order.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, orderDB, value);
            });
            return new ResponseObject(HttpStatus.OK, "Thành công", orderRepository.save(orderDB));
        } else {
            throw new OrderNotFoundException("Đơn hàng không tồn tại");
        }
    }


}
