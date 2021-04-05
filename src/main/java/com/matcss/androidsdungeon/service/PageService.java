package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.model.Page;
import com.matcss.androidsdungeon.repository.BookRepository;
import com.matcss.androidsdungeon.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Page> findAllPages() {
        return pageRepository.findAll();
    }

    public List<Page> findAllPagesByBookId(int id) {
        return pageRepository.findAllPageByBook_BookId(id);
    }

    public Page findPageById(int id) {
        return pageRepository.findPageByPageId(id);
    }

    public Page savePage(int bookId, Page obj) {
        Book book = bookRepository.findBookByBookId(bookId);
        if (book == null) return null;
        obj.setBook(book);

        return pageRepository.save(obj);
    }

    public Page updatePage(int id, Page obj) {
        Page page = findPageById(id);

        if (page == null)
            return null;

        page.setPageId(id);
        page.setPageImage(obj.getPageImage());
        page.setPageNumber(obj.getPageNumber());

        return pageRepository.save(page);
    }

    public Page deletePage(int id) {
        Page page = findPageById(id);
        if (page != null)
            pageRepository.delete(page);
        return page;
    }
}
