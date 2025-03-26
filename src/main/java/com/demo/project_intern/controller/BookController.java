package com.demo.project_intern.controller;

import com.demo.project_intern.config.Translator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base-path}/book")
@Slf4j
@Tag(name = "Book Controller")
public class BookController {
    @GetMapping("")
    @Operation(method = "GET", summary = "Add Book", description = "API Create New Book")
    public String createUser() {
        return Translator.toLocale("book.add.success");
    }
}