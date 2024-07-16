package org.sau.toyota.backend.reportservice.api;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.sau.toyota.backend.reportservice.core.results.*;
import org.sau.toyota.backend.reportservice.dto.response.PdfResponse;
import org.sau.toyota.backend.reportservice.dto.response.SaleResponse;
import org.sau.toyota.backend.reportservice.service.Abstract.ReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * REST controller for handling report-related operations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    /**
     * Retrieves a paginated list of all orders.
     *
     * @param page the page number to retrieve, defaults to 0
     * @param size the number of records per page, defaults to 3
     * @param sortBy the field to sort by, defaults to "id"
     * @param sortOrder the order to sort by (asc or desc), defaults to "asc"
     * @param filter optional filter for the orders
     * @return a DataResult object containing a list of SaleResponse objects
     */
    @GetMapping("/getAllOrders")
    public ResponseEntity<DataResult<List<SaleResponse>>> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "3") int size,
                                                       @RequestParam(defaultValue = "id") String sortBy,
                                                       @RequestParam(defaultValue = "asc") String sortOrder,
                                                       @RequestParam(required = false) String filter) {

            return ResponseEntity.ok(new SuccessDataResult<>
                    (reportService.getAllOrders(page, size, sortBy, sortOrder, filter), "Data has been listed successfully."));
    }
    /**
     * Retrieves a specific order by its ID.
     *
     * @param id the ID of the order to retrieve
     * @return a DataResult object containing a SaleResponse object
     */
    @GetMapping("/getOneOrder/{id}")
    public ResponseEntity<DataResult<SaleResponse>> getOneOrder(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessDataResult<>(reportService.getOneOrder(id), "Data has been listed successfully."));
        }
        catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDataResult<>(e.getMessage()));
        }
    }
    /**
     * Generates a PDF report for a specific order by its ID.
     *
     * This endpoint generates a PDF document for a specific order, identified by its ID,
     * and returns the PDF as a downloadable file. It uses JasperReports for generating
     * the PDF report and retrieves order details from the database.
     *
     * @param id the ID of the order to generate a PDF report for
     * @return a ResponseEntity containing the PDF report as a Resource
     * @throws JRException if there is an error generating the report with JasperReports
     * @throws SQLException if there is an error accessing the database
     * @throws FileNotFoundException if the JasperReports template file is not found
     */
    @GetMapping("/getOrdersInPdfFormat/{id}")
    public ResponseEntity<Resource> getOrdersInPdfFormat(@PathVariable Long id) throws JRException, SQLException, FileNotFoundException {

        byte[] pdfBytes = reportService.getOrdersInPdfFormat(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdfBytes.length);
        PdfResponse pdfResponse = new PdfResponse(pdfBytes, headers);
        byte[] decoder = pdfResponse.getPdfBytes();
        InputStream is = new ByteArrayInputStream(decoder);
        InputStreamResource resource = new InputStreamResource(is);

        headers.setContentType(MediaType.APPLICATION_PDF);

        ContentDisposition disposition = ContentDisposition.attachment().filename("sales-report.pdf").build();
        headers.setContentDisposition(disposition);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }


}
