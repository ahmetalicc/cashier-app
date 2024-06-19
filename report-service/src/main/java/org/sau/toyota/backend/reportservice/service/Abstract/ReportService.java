package org.sau.toyota.backend.reportservice.service.Abstract;

import net.sf.jasperreports.engine.JRException;
import org.sau.toyota.backend.reportservice.dto.response.SaleResponse;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * This interface defines the contract for generating sales reports in the application.
 */
public interface ReportService {
    /**
     * Retrieves a list of sales orders based on pagination, sorting, and filtering criteria.
     *
     * @param page     Page number
     * @param size     Number of items per page
     * @param sortBy   Sort field
     * @param sortOrder Sort order (asc or desc)
     * @param filter   Filter criteria for date range (optional)
     * @return List of {@link SaleResponse} objects representing sales orders
     */
    List<SaleResponse> getAllOrders(int page, int size, String sortBy, String sortOrder, String filter);
    /**
     * Retrieves a single sales order by its ID.
     *
     * @param id ID of the sales order
     * @return {@link SaleResponse} object representing the sales order
     */
    SaleResponse getOneOrder(Long id);
    /**
     * Generates a PDF format report for a specific sales order.
     *
     * @param id ID of the sales order
     * @return Byte array representing the PDF report
     * @throws FileNotFoundException If the report file is not found
     * @throws JRException           If an error occurs during JasperReports operations
     * @throws SQLException          If a SQL exception occurs
     */
    byte[] getOrdersInPdfFormat(Long id) throws JRException, FileNotFoundException, SQLException;

}
