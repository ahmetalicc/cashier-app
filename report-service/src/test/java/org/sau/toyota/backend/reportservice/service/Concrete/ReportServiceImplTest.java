package org.sau.toyota.backend.reportservice.service.Concrete;

import net.sf.jasperreports.engine.JRException;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sau.toyota.backend.reportservice.dao.SaleRepository;
import org.sau.toyota.backend.reportservice.dto.response.SaleResponse;
import org.sau.toyota.backend.reportservice.entity.Sale;
import org.sau.toyota.backend.reportservice.fixture.SaleFixture;
import org.springframework.data.domain.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private SaleRepository saleRepository;
    @Mock
    private Logger logger;
    @InjectMocks
    private ReportServiceImpl reportService;

    @DisplayName(" The test when call with page size sortBy and sortOrder parameters that should return list of SaleResponse with pagination")
    @Test
    void getAllOrders_whenCallWithoutFilter_ShouldReturnListOfSaleResponseWithPagination(){
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortOrder = "asc";

        SaleFixture saleFixture = new SaleFixture();
        List<Sale> saleList = saleFixture.createSaleList();
        Page<Sale> pagedSales = new PageImpl<>(saleList);
        when(saleRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy).ascending())))
                .thenReturn(pagedSales);

        // Act
        List<SaleResponse> actual = reportService.getAllOrders(page, size, sortBy, sortOrder, null);
        List<SaleResponse> expected = pagedSales.getContent().stream()
                .map(sale -> SaleResponse.Convert(sale, sale.getSoldProducts()))
                .collect(Collectors.toList());

        // Assert
        assertEquals(actual, expected);
        verify(saleRepository, times(1)).findAll(any(Pageable.class));
    }

    @DisplayName("The test when filter by year that should return Sales of entered year as list of SaleResponse with pagination")
    @Test
    void getAllOrders_whenFilterByYear_ShouldReturnSalesOfEnteredYearAsListOfSaleResponseWithPagination() {
        SaleFixture saleFixture = new SaleFixture();
        List<Sale> salesIn2022 = saleFixture.createSaleList();
        Page<Sale> pagedSales = new PageImpl<>(salesIn2022);

        when(saleRepository.findSalesBySaleTimeBetween(any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(pagedSales);

        List<SaleResponse> actual = reportService.getAllOrders(0, 10, "name", "asc", "2022");
        List<SaleResponse> expected = pagedSales.getContent().stream()
                .map(sale -> SaleResponse.Convert(sale, sale.getSoldProducts()))
                .toList();

        assertEquals(actual, expected);
        verify(saleRepository, times(1)).
                findSalesBySaleTimeBetween(any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class));

    }

    @DisplayName("The test when filter by year and month that should return Sales of entered year and month as list of SaleResponse with pagination")
    @Test
    void getAllOrders_whenFilterByYearAndMonth_ShouldReturnSalesOfEnteredYearMonthAsListOfSaleResponseWithPagination(){
        SaleFixture saleFixture = new SaleFixture();
        List<Sale> salesInJan2022 = saleFixture.createSaleList();
        Page<Sale> pagedSales = new PageImpl<>(salesInJan2022);

        when(saleRepository.findSalesBySaleTimeBetween(any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(pagedSales);

        List<SaleResponse> actual = reportService.getAllOrders(0, 10, "name", "asc", "2022-01");
        List<SaleResponse> expected = pagedSales.getContent().stream()
                .map(sale -> SaleResponse.Convert(sale, sale.getSoldProducts()))
                .toList();

        assertEquals(actual, expected);
        verify(saleRepository, times(1)).
                findSalesBySaleTimeBetween(any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class));
    }

    @DisplayName("The test when filter by year month and day that should return Sales of entered year month day's as list of SaleResponse with pagination")
    @Test
    void getAllOrders_whenFilterByYearMonthDay_ShouldReturnSalesOfEnteredYearMonthDayAsListOfSaleResponseWithPagination(){
        SaleFixture saleFixture = new SaleFixture();
        List<Sale> salesInJan1_2022 = saleFixture.createSaleList();
        Page<Sale> pagedSales = new PageImpl<>(salesInJan1_2022);

        when(saleRepository.findSalesBySaleTimeBetween(any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(pagedSales);

        List<SaleResponse> actual = reportService.getAllOrders(0, 10, "name", "asc", "2022-01-01");
        List<SaleResponse> expected = pagedSales.getContent().stream()
                .map(sale -> SaleResponse.Convert(sale, sale.getSoldProducts()))
                .toList();

        assertEquals(actual, expected);
        verify(saleRepository, times(1)).
                findSalesBySaleTimeBetween(any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class));
    }

    @DisplayName("The test when filter is in invalid format that should throw Illegal Argument Exception")
    @Test
    void getAllOrders_whenFilterIsInInvalidFormat_ShouldThrowIllegalArgumentException(){
        assertThatThrownBy(()-> reportService.getAllOrders(0, 3, "name", "asc", "invalidFormat"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid filter format. Use yyyy for year, yyyy-MM for year and month, or yyyy-MM-dd for year, month, and day.");
    }

    @DisplayName("The test when call without filter that should sorts Orders in descending order when sort order is descending")
    @Test
    void getAllOrders_whenCallWithoutFilter_ShouldSortsSalesDescending_whenSortOrderIsDescending(){
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortOrder = "desc";

        SaleFixture saleFixture = new SaleFixture();
        List<Sale> saleList = saleFixture.createSaleList();
        Page<Sale> pagedSales = new PageImpl<>(saleList);
        when(saleRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy).descending())))
                .thenReturn(pagedSales);

        // Act
        List<SaleResponse> actual = reportService.getAllOrders(page, size, sortBy, sortOrder, null);
        List<SaleResponse> expected = pagedSales.getContent().stream()
                .map(sale -> SaleResponse.Convert(sale, sale.getSoldProducts()))
                .collect(Collectors.toList());

        // Assert
        assertEquals(actual, expected);
        verify(saleRepository, times(1)).findAll(any(Pageable.class));
    }

    @DisplayName("The test when call with id parameter that should return SaleResponse if Order exist with given id")
    @Test
    void getOneOrder_whenCallWithIdParameter_ShouldReturnSaleResponse_IfSaleIsNotExistWithGivenId(){
        SaleFixture saleFixture = new SaleFixture();
        Sale sale = saleFixture.createSale();
        Long id = sale.getId();

        when(saleRepository.findById(id)).thenReturn(Optional.of(sale));

        SaleResponse actual = reportService.getOneOrder(id);
        SaleResponse expected = SaleResponse.Convert(sale, sale.getSoldProducts());

        assertEquals(actual, expected);
        assertNotNull(actual);
        verify(saleRepository, times(1)).findById(id);
    }

    @DisplayName("The test that should throw Null Pointer Exception if Sale is not exist with given id")
    @Test
    void getOneOrder_ShouldThrowNullPointerException_IfSaleIsNotExistWithGivenId(){
        SaleFixture saleFixture = new SaleFixture();
        Sale sale = saleFixture.createSale();
        Long id = sale.getId();

        when(saleRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> reportService.getOneOrder(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Sale not found with id(%s) that is provided:", id));

        verify(saleRepository, times(1)).findById(id);
    }

    @DisplayName("The test when Sale exist with given id that should generate Orders in PDF format")
    @Test
    void getOrdersInPdfFormat_whenSaleExistWithGivenId_ShouldGenerateOrdersInPdfFormat() throws JRException, SQLException, FileNotFoundException {
        SaleFixture saleFixture = new SaleFixture();
        Sale sale = saleFixture.createSale();
        Long id = sale.getId();
        when(saleRepository.findById(id)).thenReturn(Optional.of(sale));

        byte[] pdfData = reportService.getOrdersInPdfFormat(id);

        assertNotNull(pdfData);
        assertTrue(pdfData.length > 0);
        verify(saleRepository, times(1)).findById(id);
    }

    @DisplayName("The test that should throw Null Pointer Exception if Sale is not exist with given id")
    @Test
    void getOneOrder_whenSaleIsNotExistWithGivenId_ShouldThrowNullPointerException(){
        SaleFixture saleFixture = new SaleFixture();
        Sale sale = saleFixture.createSale();
        Long id = sale.getId();

        when(saleRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> reportService.getOrdersInPdfFormat(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Sale not found with id(%s) that is provided:", id));

        verify(saleRepository, times(1)).findById(id);
    }


}
