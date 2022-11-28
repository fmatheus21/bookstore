package com.fmatheus.app.controller.converter.impl;

import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.controller.dto.request.BookDtoRequest;
import com.fmatheus.app.controller.dto.response.BookDtoResponse;
import com.fmatheus.app.model.entity.Book;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("Teste de conversão de entidade e DTO")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {BookConverterImpl.class})
@ExtendWith(SpringExtension.class)
class BookConverterImplTest {

    @Autowired
    private BookConverterImpl bookConverterImpl;

    @MockBean
    private ModelMapper modelMapper;

    private Book book;

    private BookDtoResponse bookDtoResponse;


    /**
     * Metodo de teste: {@link BookConverterImpl#converterToRequest(BookDtoRequest)}
     */
    @Test
    @Order(1)
    @DisplayName("Sucesso ao converter dto em entidade")
    void testConverterToRequest() {
        when(this.modelMapper.map(any(), any())).thenReturn(this.book);
        var actualResult = this.bookConverterImpl.converterToRequest(new BookDtoRequest());
        assertSame(this.book, actualResult);
        assertEquals(TestConstant.PRICE, actualResult.getPrice());
        verify(this.modelMapper).map(any(), any());
    }

    @BeforeEach
    public void beforeEach() {
        openMocks(this);
        this.start();
    }


    /**
     * Metodo de teste: {@link BookConverterImpl#converterToResponse(Book)}
     */
    @Test
    @Order(2)
    @DisplayName("Sucesso ao converter entidade em dto")
    void testConverterToResponse() {
        when(this.modelMapper.map(any(), any())).thenReturn(this.bookDtoResponse);
        var actualResult = this.bookConverterImpl.converterToResponse(this.book);
        assertSame(this.bookDtoResponse, actualResult);
        assertEquals(TestConstant.CURRENCY.name(), actualResult.getCurrency());
        verify(this.modelMapper).map(any(), any());
    }


    private void start() {
        this.book = this.loadBook();
        this.bookDtoResponse = this.loadBookDtoResponse();
    }


    private BookDtoRequest loadBookDtoRequest() {
        return BookDtoRequest.builder()
                .author(TestConstant.AUTHOR)
                .launchDate(TestConstant.LAUNCH_DATE)
                .price(TestConstant.PRICE)
                .title(TestConstant.TITLE)
                .currency(TestConstant.CURRENCY)
                .build();
    }

    private BookDtoResponse loadBookDtoResponse() {
        return BookDtoResponse.builder()
                .id(TestConstant.ID)
                .author(TestConstant.AUTHOR)
                .launchDate(TestConstant.LAUNCH_DATE)
                .price(TestConstant.PRICE)
                .title(TestConstant.TITLE)
                .currency(TestConstant.CURRENCY.name())
                .build();
    }

    private Book loadBook() {
        return Book.builder()
                .id(TestConstant.ID)
                .launchDate(TestConstant.LAUNCH_DATE)
                .author(TestConstant.AUTHOR)
                .price(TestConstant.PRICE)
                .title(TestConstant.TITLE)
                .currency(TestConstant.CURRENCY)
                .build();
    }
}

