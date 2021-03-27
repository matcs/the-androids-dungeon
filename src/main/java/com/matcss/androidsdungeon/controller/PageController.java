package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.model.Page;
import com.matcss.androidsdungeon.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/pages")
public class PageController {

    private final PageService pageService;

    @Autowired
    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<Page>> getAllPages() {
        return ResponseEntity.ok(pageService.findAll());
    }


    @GetMapping("/book")
    public ResponseEntity<List<Page>> getAllPageFromBook(@RequestParam("bookId") int bookId) {
        List<Page> pages = pageService.findAllPagesByBookId(bookId);
        return ResponseEntity.ok(pages);
    }


    @GetMapping("/{pageId}")
    public ResponseEntity<Page> getPageById(@PathVariable("pageId") int id) {
        Page page = pageService.findById(id);

        if (page == null)
            return ResponseEntity.notFound().build();

        byte[] decompressedPage = new Page().decompressBytes(page.getPageImage());
        page.setPageImage(decompressedPage);

        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<Page> createPage(@RequestPart("pageNumber") int pageNumber,
                                           @RequestPart("file") MultipartFile file,
                                           @RequestParam("bookId") int bookId) throws IOException {
        byte[] image = new Page().compressBytes(file.getBytes());
        Page page = new Page(pageNumber, image, new Book(bookId));

        Page createdPage = pageService.create(bookId,page);

        return createdPage != null ? ResponseEntity.created(URI.create("/pages/" + createdPage.getPageId())).body(createdPage) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{pageId}")
    public ResponseEntity<Page> updatePage(@PathVariable("pageId") int id,
                                           @RequestPart("pageNumber") int pageNumber,
                                           @RequestPart("file") MultipartFile file) throws IOException {
        Page pageBody = new Page(file.getBytes(), pageNumber);
        Page page = pageService.update(id, pageBody);
        return page != null ? ResponseEntity.accepted().body(page) : ResponseEntity.badRequest().build();
    }

}
