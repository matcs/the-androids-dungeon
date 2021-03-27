package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.interfaces.CRUDServices;
import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.model.Page;
import com.matcss.androidsdungeon.repository.BookRepository;
import com.matcss.androidsdungeon.repository.PageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PageService implements CRUDServices<Page> {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Page> findAll() {
        return pageRepository.findAll();
    }

    public List<Page> findAllPagesByBookId(int id){
        return pageRepository.findAllPageByBook_BookId(id);
    }

    @Override
    public Page findById(int id) {
        return pageRepository.findPageByPageId(id);
    }

    public Page create(int bookId, Page obj) {
        Book book = bookRepository.findBookByBookId(bookId);
        if(book == null) return null;
        obj.setBook(book);

        return pageRepository.save(obj);
    }

    @Override
    public Page create(Page obj) {
        return pageRepository.save(obj);
    }

    @Override
    public Page update(int id, Page obj) {
        Page page = findById(id);

        if (page == null)
            return null;

        page.setPageId(id);
        page.setPageImage(obj.getPageImage());
        page.setPageNumber(obj.getPageNumber());

        return pageRepository.save(page);
    }

    @Override
    public Page delete(int id) {
        Page page = findById(id);
        if(page != null)
            pageRepository.delete(page);
        return page;
    }
}
