package org.sau.toyota.backend.reportservice.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * PdfResponse is a Data Transfer Object (DTO) used to encapsulate the response data for PDF files.
 *
 * <p>
 * This class includes the PDF file as a byte array and the associated HTTP headers.
 * It is primarily used in scenarios where PDF files need to be sent as part of the HTTP response.
 * </p>
 * @see HttpHeaders
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfResponse {
    /**
     * The byte array representation of the PDF file.
     */
    private byte[] pdfBytes;
    /**
     * The HTTP headers associated with the PDF file response.
     */
    private HttpHeaders headers;

}
