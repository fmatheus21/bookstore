package com.fmatheus.app.controller.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.controller.dto.response.BookDtoResponse;
import com.fmatheus.app.controller.rule.BookRule;
import com.fmatheus.app.enumerable.MessageEnum;
import com.fmatheus.app.exception.BadRequestException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Teste da recursos de livro")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {BookResource.class})
@ExtendWith(SpringExtension.class)
class BookResourceTest {

    @Autowired
    private BookResource bookResource;

    @MockBean
    private BookRule bookRule;

    private ObjectMapper mapper;

    private BookDtoResponse bookDtoResponse;

    @BeforeEach
    public void beforeEach() {
        openMocks(this);
        this.start();
    }

    /**
     * Metodo de teste: {@link BookResource#findBook(int, String)}
     */
    @Test
    @Order(1)
    @DisplayName("Sucesso na consulta de livro")
    void findBookTest() throws Exception {
        when(this.bookRule.findBook(anyInt(), anyString())).thenReturn(this.bookDtoResponse);

        String response = this.mapper.writeValueAsString(this.bookDtoResponse);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(ResourceConstant.BOOK_SERVICE + ResourceConstant.ID + ResourceConstant.CURRENCY, TestConstant.ID,
                TestConstant.TO_CURRENCY);
        MockMvcBuilders.standaloneSetup(this.bookResource)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestConstant.CONTENT_TYPE_JSON))
                .andExpect(content().string(response));
    }

    /**
     * Metodo de teste: {@link BookResource#findBookFallbackAfterRetry(int, String, Exception)}
     */
    @Test
    @Order(2)
    @DisplayName("Sucesso na consulta de livro Fallback")
    void testFindBookFallbackAfterRetry() {
        when(this.bookRule.findBookFallbackAfterRetry(anyInt(), anyString())).thenReturn(new BookDtoResponse());
        var actualFallbackResult = this.bookResource.findBookFallbackAfterRetry(TestConstant.ID, TestConstant.TO_CURRENCY, new BadRequestException(MessageEnum.ERROR_NOT_FOUND));
        assertTrue(actualFallbackResult.hasBody());
        assertTrue(actualFallbackResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualFallbackResult.getStatusCode());
        verify(this.bookRule).findBookFallbackAfterRetry(anyInt(), anyString());
    }

    private void start() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.bookDtoResponse = this.loadBookDtoResponse();
    }

    private BookDtoResponse loadBookDtoResponse() {
        return BookDtoResponse.builder()
                .id(TestConstant.ID)
                .author(TestConstant.AUTHOR)
                .launchDate(LocalDateTime.now())
                .price(TestConstant.PRICE)
                .title(TestConstant.TITLE)
                .currency(TestConstant.FROM_CURRENCY)
                .build();
    }
}

