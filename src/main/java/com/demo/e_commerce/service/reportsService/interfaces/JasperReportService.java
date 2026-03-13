package com.demo.e_commerce.service.reportsService.interfaces;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface JasperReportService {

    byte[] generateInvoice(Long orderId) throws JRException, IOException;
}
