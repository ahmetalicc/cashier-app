package org.sau.Toyota.Backend.kasiyerapp.Service.Abstract;

import net.sf.jasperreports.engine.JRException;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.SaleResponse;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface ReportService {
    List<SaleResponse> getAllOrders(int page, int size, String sortBy, String sortOrder, String filter);

    SaleResponse getOneOrder(Long id);

    byte[] getOrdersInPdfFormat(Long id) throws JRException, FileNotFoundException, SQLException;

}
