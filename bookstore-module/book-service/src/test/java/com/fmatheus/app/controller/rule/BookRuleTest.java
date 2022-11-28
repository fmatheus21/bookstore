package com.fmatheus.app.controller.rule;

import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.controller.converter.BookConverter;
import com.fmatheus.app.controller.dto.response.BookDtoResponse;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.controller.enumerable.CurrencyEnum;
import com.fmatheus.app.controller.resource.proxy.CambiumResourceProxy;
import com.fmatheus.app.enumerable.MessageEnum;
import com.fmatheus.app.exception.BadRequestException;
import com.fmatheus.app.model.entity.Book;
import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.repository.CambiumRepository;
import com.fmatheus.app.model.service.BookService;
import com.fmatheus.app.rule.ResponseMessage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("Teste de regra de negócio de livro")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {BookRule.class})
@ExtendWith(SpringExtension.class)
class BookRuleTest {

    @Autowired
    private BookRule bookRule;

    @MockBean
    private BookConverter bookConverter;

    @MockBean
    private BookService bookService;

    @MockBean
    private CambiumRepository cambiumRepository;

    @MockBean
    private CambiumResourceProxy cambiumResourceProxy;

    @MockBean
    private ResponseMessage responseMessage;

    private Book book;

    private Optional<Book> optional;

    private ResponseEntity<CambiumDtoResponse> responseEntity;

    private BookDtoResponse bookDtoResponse;

    private Optional<Cambium> optionalCambium;

    @BeforeEach
    public void beforeEach() {
        openMocks(this);
        this.start();
    }

    /**
     * Metodo de teste: {@link BookRule#findBook(int, String)}
     */
    @Test
    @Order(1)
    @DisplayName("Sucesso ao encontrar o livro")
    void findBookSuccessTest() {

        when(this.bookService.findById(anyInt())).thenReturn(this.optional);
        var actualResult = this.bookService.findById(TestConstant.ID);
        assertSame(this.optional, actualResult);
        assertTrue(actualResult.isPresent());
        assertEquals(Book.class, actualResult.get().getClass());
        verify(this.bookService).findById(TestConstant.ID);

        when(this.cambiumResourceProxy.convertCurrency(any(), anyString(), anyString())).thenReturn(this.responseEntity);
        var actualResultProxy = this.cambiumResourceProxy.convertCurrency(TestConstant.AMOUNT, TestConstant.FROM_CURRENCY, TestConstant.TO_CURRENCY);
        assertSame(this.responseEntity, actualResultProxy);
        assertTrue(actualResultProxy.hasBody());
        assertNotNull(actualResultProxy.getBody());

        var bookMock = mock(Book.class);
        when(bookMock.getPrice()).thenReturn(actualResultProxy.getBody().getConvertedValue());
        when(bookMock.getCurrency()).thenReturn(CurrencyEnum.valueOf(actualResultProxy.getBody().getToCurrency()));

        when(this.bookConverter.converterToResponse(any())).thenReturn(this.bookDtoResponse);
        var actualConverter = this.bookConverter.converterToResponse(this.book);
        assertSame(this.bookDtoResponse, actualConverter);
        assertNotNull(actualConverter);
        assertEquals(BookDtoResponse.class, actualConverter.getClass());

        this.bookRule.findBook(TestConstant.ID, TestConstant.CURRENCY.name());

    }

    /**
     * Metodo de teste: {@link BookRule#findBook(int, String)}
     */
    @Test
    @Order(2)
    @DisplayName("Exceção se o id do livro não for encontrado")
    void findBookExceptionFindBookByIdTest() {
        when(this.bookService.findById(anyInt())).thenThrow(new BadRequestException(MessageEnum.ERROR_NOT_FOUND));
        try {
            this.bookRule.findBook(TestConstant.ID, TestConstant.CURRENCY.name());
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals(MessageEnum.ERROR_NOT_FOUND.getMessage(), ex.getMessage());
        }
    }

    /**
     * Metodo de teste: {@link BookRule#findBook(int, String)}
     */
    @Test
    @Order(3)
    @DisplayName("Excecao se o microservico retornar um erro")
    void findBookExceptionCambiumResourceProxyTest() {

        when(this.bookService.findById(anyInt())).thenReturn(this.optional);
        var actualResult = this.bookService.findById(TestConstant.ID);
        assertSame(this.optional, actualResult);
        assertTrue(actualResult.isPresent());
        assertEquals(Book.class, actualResult.get().getClass());
        verify(this.bookService).findById(TestConstant.ID);

        when(this.cambiumResourceProxy.convertCurrency(any(), anyString(), anyString())).thenThrow(new BadRequestException(MessageEnum.ERROR_CAMBIUM_NOT_CONVERTER));

        try {
            this.bookRule.findBook(TestConstant.ID, TestConstant.CURRENCY.name());
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals(MessageEnum.ERROR_CAMBIUM_NOT_CONVERTER.getMessage(), ex.getMessage());
        }

    }


    /**
     * Metodo de teste: {@link BookRule#findBookFallbackAfterRetry(int, String)}
     */
    @Test
    @Order(4)
    @DisplayName("Sucesso se o FallBack for invocado")
    void findBookFallbackAfterRetrySuccessTest() {

        when(this.bookService.findById(anyInt())).thenReturn(this.optional);
        var actualResult = this.bookService.findById(TestConstant.ID);
        assertSame(this.optional, actualResult);
        assertTrue(actualResult.isPresent());
        assertEquals(Book.class, actualResult.get().getClass());
        verify(bookService).findById(anyInt());

        when(this.cambiumRepository.findByFromCurrencyAndToCurrency(anyString(), anyString())).thenReturn(this.optionalCambium);
        var actualcambiumResult = this.cambiumRepository.findByFromCurrencyAndToCurrency(TestConstant.FROM_CURRENCY, TestConstant.TO_CURRENCY);
        assertSame(this.optionalCambium, actualcambiumResult);
        assertTrue(actualcambiumResult.isPresent());
        assertEquals(Cambium.class, actualcambiumResult.get().getClass());
        verify(cambiumRepository).findByFromCurrencyAndToCurrency(anyString(), anyString());

        var converterValue = actualcambiumResult.get().getConversionFactor().multiply(actualResult.get().getPrice()).setScale(2, RoundingMode.CEILING);

        var bookMock = mock(Book.class);
        when(bookMock.getPrice()).thenReturn(converterValue);
        when(bookMock.getCurrency()).thenReturn(CurrencyEnum.valueOf(actualcambiumResult.get().getToCurrency()));

        when(bookConverter.converterToResponse(any())).thenReturn(this.bookDtoResponse);
        var actualConverter = this.bookConverter.converterToResponse(this.book);
        assertSame(this.bookDtoResponse, actualConverter);
        assertNotNull(actualConverter);
        assertEquals(BookDtoResponse.class, actualConverter.getClass());
        verify(bookConverter).converterToResponse(any());

        this.bookRule.findBookFallbackAfterRetry(TestConstant.ID, TestConstant.CURRENCY.name());
    }


    /**
     * Metodo de teste: {@link BookRule#findBook(int, String)}
     */
    @Test
    @Order(5)
    @DisplayName("Exceção se o id do livro não for encontrado no FallBack")
    void findBookFallbackAfterRetryExceptionFindIdBookTest() {
        when(this.bookService.findById(anyInt())).thenThrow(new BadRequestException(MessageEnum.ERROR_NOT_FOUND));
        assertThrows(BadRequestException.class, () -> this.bookService.findById(TestConstant.ID));

        try {
            this.bookRule.findBook(TestConstant.ID, TestConstant.CURRENCY.name());
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals(MessageEnum.ERROR_NOT_FOUND.getMessage(), ex.getMessage());
        }
    }


    private void start() {
        this.book = this.loadBook();
        this.optional = Optional.of(this.loadBook());
        this.responseEntity = ResponseEntity.ok(this.loadCambiumResponse());
        this.bookDtoResponse = this.loadBookDtoResponse();
        this.optionalCambium = Optional.of(this.loadCambium());
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

    private CambiumDtoResponse loadCambiumResponse() {
        return CambiumDtoResponse.builder()
                .id(TestConstant.ID)
                .fromCurrency(TestConstant.FROM_CURRENCY)
                .toCurrency(TestConstant.TO_CURRENCY)
                .convertedValue(TestConstant.CONVERTED_VALUE)
                .conversionFactor(TestConstant.CONVERSION_FACTOR)
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

    private Cambium loadCambium() {
        return Cambium.builder()
                .id(TestConstant.ID)
                .fromCurrency(TestConstant.FROM_CURRENCY)
                .toCurrency(TestConstant.TO_CURRENCY)
                .conversionFactor(TestConstant.CONVERSION_FACTOR)
                .convertedValue(TestConstant.CONVERTED_VALUE)
                .build();
    }
}

