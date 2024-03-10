package org.sau.Toyota.Backend.kasiyerapp.Dto.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfResponse {

    private byte[] pdfBytes;
    private HttpHeaders headers;

}
