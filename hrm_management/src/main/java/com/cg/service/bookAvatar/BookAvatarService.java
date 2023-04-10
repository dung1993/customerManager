package com.cg.service.bookAvatar;

import com.cg.model.Book;
import com.cg.model.BookAvatar;
import com.cg.repository.BookAvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookAvatarService implements IBookAvatarService {

    @Autowired
    private BookAvatarRepository bookAvatarRepository;

    @Override
    public List<BookAvatar> findAll() {
        return bookAvatarRepository.findAll();
    }

    @Override
    public Optional<BookAvatar> findById(String id) {
        return bookAvatarRepository.findById(id);
    }

    @Override
    public Optional<BookAvatar> findByBook(Book book) {
        return bookAvatarRepository.findByBook(book);
    }

    @Override
    public Boolean existById(String id) {
        return null;
    }

    @Override
    public BookAvatar save(BookAvatar BookAvatar) {
        return bookAvatarRepository.save(BookAvatar);
    }

    @Override
    public void delete(BookAvatar BookAvatar) {

    }

    @Override
    public void deleteById(String id) {

    }
}
