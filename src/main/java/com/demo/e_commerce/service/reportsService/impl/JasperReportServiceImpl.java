package com.demo.e_commerce.service.reportsService.impl;

import com.demo.e_commerce.dto.reportsDto.InvoiceItemDTO;
import com.demo.e_commerce.model.OrderEntity;
import com.demo.e_commerce.model.OrderItemEntity;
import com.demo.e_commerce.repository.orderrepo.OrderRepository;
import com.demo.e_commerce.service.reportsService.interfaces.JasperReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperReportServiceImpl implements JasperReportService {

    @Autowired
    private OrderRepository orderRepository;

    public byte[] generateInvoice(Long orderId) throws JRException, IOException {
        // 1. Fetch Order from DB
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 2. Map Database Items to Jasper Fields
        List<InvoiceItemDTO> items = new ArrayList<>();
        for (OrderItemEntity item : order.getOrderItems()) {
            items.add(new InvoiceItemDTO(
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getPrice(),
                    item.getPrice()
            ));
        }

        // 3. THE FIX: Load the .jrxml template safely using InputStream!
        InputStream reportStream = new ClassPathResource("reports/invoice.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // 4. Create the Data Source
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);

        // 5. Set the Parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orderId", "ORD-" + order.getId());
        parameters.put("orderDate", order.getOrderDate().toString().substring(0, 10));
        parameters.put("totalAmount", "Rs. " + order.getTotalAmount());

        // 6. Fill and Export
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}