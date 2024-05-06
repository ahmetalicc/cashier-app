package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.apache.log4j.Logger;
import org.sau.Toyota.Backend.kasiyerapp.Dao.SaleRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.SaleResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.*;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final SaleRepository saleRepository;

    private static final Logger logger = Logger.getLogger(ReportServiceImpl.class);

    @Override
    public List<SaleResponse> getAllOrders(int page, int size, String sortBy, String sortOrder, String filter) {
        Sort sort = Sort.by(sortBy).ascending();
        if ("desc".equals(sortOrder)) {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        int year;
        int month;
        int day;
        Page<Sale> sales;
        if (filter != null && !filter.isEmpty()) {
            LocalDateTime startDateTime;
            LocalDateTime endDateTime;
            if (filter.length() == 4) {
                year = Integer.parseInt(filter);
                startDateTime = LocalDateTime.of(year, 1, 1, 0, 0, 0);
                endDateTime = LocalDateTime.of(year, 12, 31, 23, 59, 59);
            } else if (filter.length() == 7) { // Yıl ve ay
                year = Integer.parseInt(filter.substring(0, 4));
                month = Integer.parseInt(filter.substring(5, 7));
                startDateTime = LocalDateTime.of(year, month, 1, 0, 0, 0);
                endDateTime = LocalDateTime.of(year, month, YearMonth.of(year, month).lengthOfMonth(), 23, 59, 59);
            } else if (filter.length() == 10) { // Yıl, ay ve gün
                year = Integer.parseInt(filter.substring(0, 4));
                month = Integer.parseInt(filter.substring(5, 7));
                day = Integer.parseInt(filter.substring(8, 10));
                startDateTime = LocalDateTime.of(year, month, day, 0, 0, 0);
                endDateTime = LocalDateTime.of(year, month, day, 23, 59, 59);
            } else {
                logger.info("Invalid filter format. Use yyyy for year, yyyy-MM for year and month, or yyyy-MM-dd for year, month, and day.");
                throw new IllegalArgumentException("Invalid filter format. Use yyyy for year, yyyy-MM for year and month, or yyyy-MM-dd for year, month, and day.");
            }
            sales = saleRepository.findSalesBySaleTimeBetween(startDateTime, endDateTime, pageable);
        }else {
            sales = saleRepository.findAll(pageable);
        }
        return sales.getContent().stream()
                .map(sale -> SaleResponse.Convert(sale, sale.getSoldProducts()))
                .collect(Collectors.toList());
    }

    @Override
    public SaleResponse getOneOrder(Long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("Sale not found with id(%s) that is provided:", id)));

        return SaleResponse.Convert(sale, sale.getSoldProducts());
    }

    @Override
    public byte[] getOrdersInPdfFormat(Long id) throws FileNotFoundException, JRException, SQLException {
        Sale sale = saleRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("Sale not found with id(%s) that is provided:", id)));
        List<Sale> saleList = Collections.singletonList(sale);
        InputStream mainReportStream = getClass().getResourceAsStream("/Sales_.jrxml");
        JasperReport mainReport = JasperCompileManager.compileReport(mainReportStream);

        InputStream subReportStream = getClass().getResourceAsStream("/products.jrxml");
        JasperReport subReport = JasperCompileManager.compileReport(subReportStream);
        JRSaver.saveObject(subReport, "products.jasper");

        Connection connection = getConnection();

        if (mainReport == null || subReport == null) {
            logger.info("Report not found.");
            throw new FileNotFoundException("Report not found.");
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("saleId", sale.getId());
        map.put("REPORT_CONNECTION", connection);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(saleList);
        JasperPrint report = JasperFillManager.fillReport(mainReport, map, dataSource);
        return JasperExportManager.exportReportToPdf(report);
    }

    public static Connection getConnection() throws SQLException {
        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/toyota";
        String username = "postgres";
        String password = "123456";

        // Establish the database connection
        return DriverManager.getConnection(url, username, password);
    }

}
