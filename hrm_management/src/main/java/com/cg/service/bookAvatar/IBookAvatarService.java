package com.cg.service.bookAvatar;

import com.cg.model.Book;
import com.cg.model.BookAvatar;
import com.cg.service.IGeneralStringService;

import java.util.Optional;

public interface IBookAvatarService extends IGeneralStringService<BookAvatar> {

    Optional<BookAvatar> findByBook(Book book);
}
