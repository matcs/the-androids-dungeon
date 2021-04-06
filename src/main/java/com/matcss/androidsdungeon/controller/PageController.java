package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.model.Page;
import com.matcss.androidsdungeon.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pages")
public class PageController {

    private final PageService pageService;

    @Autowired
    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Page>> getAllPages() {
        List<Page> pages = pageService.findAllPages();

        return pages != null ? ResponseEntity.ok(pages) : ResponseEntity.noContent().build();
    }

    @GetMapping("/book")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<Page>> getAllPageFromBook(@RequestParam("bookId") int bookId) {
        List<Page> pages = pageService.findAllPagesByBookId(bookId);

        return pages != null ? ResponseEntity.ok(pages) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{pageId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Page> getPageById(@PathVariable("pageId") int id) {
        Page page = pageService.findPageById(id);

        byte[] decompressedPage = new Page().decompressBytes(page.getPageImage());
        page.setPageImage(decompressedPage);

        return ResponseEntity.ok(page);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<Page> createPage(@RequestPart("pageNumber") int pageNumber,
                                           @RequestPart("file") MultipartFile file,
                                           @RequestParam("bookId") int bookId) throws IOException {
        byte[] image = new Page().compressBytes(file.getBytes());
        Page page = new Page(pageNumber, image, new Book(bookId));

        Page createdPage = pageService.savePage(bookId, page);

        return createdPage != null ? ResponseEntity.created(URI.create("/pages/" + createdPage.getPageId())).body(createdPage) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{pageId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<Page> updatePage(@PathVariable("pageId") int id,
                                           @RequestPart("pageNumber") int pageNumber,
                                           @RequestPart("file") MultipartFile file) throws IOException {
        Page pageBody = new Page(file.getBytes(), pageNumber);
        Page page = pageService.updatePage(id, pageBody);

        return page != null ? ResponseEntity.accepted().body(page) : ResponseEntity.badRequest().build();
    }

}
