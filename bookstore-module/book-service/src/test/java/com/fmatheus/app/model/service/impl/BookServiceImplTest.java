package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.model.entity.Book;
import com.fmatheus.app.model.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("Teste da classe de serviço de livro")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {BookServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @MockBean
    private BookRepository bookRepository;

    private Book book;

    private Optional<Book> optional;

    private List<Book> listBooks;

    @BeforeEach
    public void beforeEach() {
        openMocks(this);
        this.start();
    }

    /**
     * Metodo de teste: {@link BookServiceImpl#findAll()}
     */
    @Test
    @Order(1)
    @DisplayName("Sucesso na listagem de livros")
    void findAllTest() {
        when(this.bookRepository.findAll()).thenReturn(this.listBooks);
        var actualResult = this.bookServiceImpl.findAll();
        assertSame(this.listBooks, actualResult);
        assertFalse(actualResult.isEmpty());
        verify(this.bookRepository).findAll();
    }


    /**
     * Metodo de teste: {@link BookServiceImpl#findById(Integer)}
     */
    @Test
    @Order(2)
    @DisplayName("Sucesso na pesquisa de livro por id")
    void findByIdTest() {
        when(this.bookRepository.findById(anyInt())).thenReturn(this.optional);
        var actualResult = this.bookServiceImpl.findById(TestConstant.ID);
        assertSame(this.optional, actualResult);
        assertTrue(actualResult.isPresent());
        assertEquals(TestConstant.PRICE, actualResult.get().getPrice());
        assertEquals(Book.class, actualResult.get().getClass());
        verify(this.bookRepository).findById(anyInt());
    }

    /**
     * Metodo de teste: {@link BookServiceImpl#save(Book)}
     */
    @Test
    @Order(3)
    @DisplayName("Sucesso ao salvar um novo livro")
    void saveTest() {
        when(this.bookRepository.save(any())).thenReturn(this.book);
        var actualResult = this.bookServiceImpl.save(this.book);
        assertSame(this.book, actualResult);
        assertEquals(TestConstant.PRICE, actualResult.getPrice());
        assertEquals(Book.class, actualResult.getClass());
        verify(this.bookRepository).save(any());
    }

    /**
     * Metodo de teste: {@link BookServiceImpl#deleteById(Integer)}
     */
    @Test
    @Order(4)
    @DisplayName("Sucesso na exclusão de um livro")
    void deleteByIdTest() {
        doNothing().when(this.bookRepository).deleteById(anyInt());
        this.bookServiceImpl.deleteById(TestConstant.ID);
        verify(this.bookRepository).deleteById(anyInt());
        assertTrue(this.bookServiceImpl.findById(TestConstant.ID).isEmpty());
    }


    private void start() {
        this.book = this.loadBook();
        this.optional = Optional.of(this.book);
        this.listBooks = List.of(this.book);
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

