package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.model.entity.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste de integração de livro")
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class BookServiceImplIntegrationTest {

    @Autowired
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    public void beforeEach() {
        this.start();
    }

    /**
     * Metodo de teste: {@link BookServiceImpl#save(Book)}
     */
    @Test
    @Order(1)
    @DisplayName("Sucesso ao salvar um novo livro")
    void saveTest() {
        var actualResult = this.bookService.save(this.book);
        assertNotNull(actualResult);
        assertEquals(Book.class, actualResult.getClass());

    }

    /**
     * Metodo de teste: {@link BookServiceImpl#save(Book)}
     */
    @Test
    @Order(2)
    @DisplayName("Sucesso ao atualizar um livro")
    void updateTest() {
        var actualConsult = this.bookService.findById(TestConstant.ID);
        assertTrue(actualConsult.isPresent());
        actualConsult.get().setAuthor("Anonymous Author");

        var actualResult = this.bookService.save(actualConsult.get());
        assertNotNull(actualResult);
        assertEquals(Book.class, actualResult.getClass());

    }


    /**
     * Metodo de teste: {@link BookServiceImpl#findAll()}
     */
    @Test
    @Order(3)
    @DisplayName("Sucesso na listagem de livros")
    void findAllTest() {
        var actualResult = this.bookService.findAll();
        Assertions.assertTrue(actualResult.size() > 0);
    }

    /**
     * Metodo de teste: {@link BookServiceImpl#findById(Integer)}
     */
    @Test
    @Order(4)
    @DisplayName("Sucesso na pesquisa de livro por id")
    void findByIdTest() {
        var actualResult = this.bookService.findById(TestConstant.ID);
        assertTrue(actualResult.isPresent());
        assertEquals(Book.class, actualResult.get().getClass());
    }

    /**
     * Metodo de teste: {@link BookServiceImpl#deleteById(Integer)}
     */
    @Test
    @Order(5)
    @DisplayName("Sucesso na exclusão de um livro")
    void deleteByIdTest() {
        this.bookService.deleteById(TestConstant.ID);
        var actualResult = this.bookService.findById(TestConstant.ID);
        assertTrue(actualResult.isEmpty());
    }


    private void start() {
        this.book = this.loadBook();
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

