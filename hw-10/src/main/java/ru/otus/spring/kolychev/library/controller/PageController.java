package ru.otus.spring.kolychev.library.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.kolychev.library.controller.dto.BookDto;
import ru.otus.spring.kolychev.library.model.Book;
import ru.otus.spring.kolychev.library.service.BookService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class PageController {

    private final BookService bookService;

    @GetMapping("/")
    public String bookPage(Model model) {
        return "book";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("newBook", new BookDto());
        return "create";
    }

    @Validated
    @PostMapping("/create")
    public String createBook(@Valid @ModelAttribute("newBook") BookDto book,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        bookService.create(book.toDomainObject());
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") String id, Model model) {
        Book book = bookService.readById(id);
        model.addAttribute("book", BookDto.fromDomainObject(book));
        return "edit";
    }

    @Validated
    @PostMapping("/edit")
    public String updateBook(@Valid @ModelAttribute("book") BookDto book,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        bookService.update(book.toDomainObject());
        return "redirect:/";
    }
}
