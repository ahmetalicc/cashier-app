package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.*;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.PdfResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.SaleResponse;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/getAllOrders")
    public DataResult<List<SaleResponse>> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "3") int size,
                                                       @RequestParam(defaultValue = "id") String sortBy,
                                                       @RequestParam(defaultValue = "asc") String sortOrder,
                                                       @RequestParam(required = false) String filter) {

            return new SuccessDataResult<>
                    (reportService.getAllOrders(page, size, sortBy, sortOrder, filter), "Data has been listed.");
    }

    @GetMapping("/getOneOrder/{id}")
    public DataResult<SaleResponse> getOneOrder(@PathVariable Long id){
        try {
            return new SuccessDataResult<>(reportService.getOneOrder(id), "Data has been listed.");
        }
        catch (NullPointerException e){
            return new ErrorDataResult<>(e.getMessage());
        }
    }

    @GetMapping("/getOrdersInPdfFormat/{id}")
    public DataResult<PdfResponse> getOrdersInPdfFormat(@PathVariable Long id)
            throws JRException, FileNotFoundException, NullPointerException {
        try {
            byte[] pdfBytes = reportService.getOrdersInPdfFormat(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "sales-report.pdf");
            headers.setContentLength(pdfBytes.length);
            PdfResponse pdfResponse = new PdfResponse(pdfBytes, headers);
            return new SuccessDataResult<>(pdfResponse, "Pdf has been created.");
        }
        catch (JRException | FileNotFoundException | NullPointerException | SQLException e){
            return new ErrorDataResult<>(e.getMessage());
        }
    }


}
