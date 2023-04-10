package com.cg.service.book;


import com.cg.model.*;
import com.cg.model.dto.*;
import com.cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IBookService extends IGeneralService<Book> {

    Boolean existsByNameEquals(String name);
    BookResDTO create(BookReqDTO bookReqDTO);
    Optional<Book> findByBookAvatarId(String string);
    BookResDTO createWithAvatar(BookReqDTO bookReqDTO);
    BookResDTO createNoAvatar(BookDTO bookDTO);
    BookResDTO updateNoAvatar(BookDTO bookDTO, Optional<Book> bookOtional ,Long bookId);
    BookResDTO updateWithAvatar(BookReqDTO bookReqDTO, Long bookId);
    Page<BookAllInfoResDTO> findAllByKeyWordByPageByDeletedIsFalse(String keyWordQuery, Pageable pageable);

    Optional<BookAllInfoResDTO> findBookResDTOById(Long bookId);

    Page<BookAllInfoResDTO> findAllForOne(String keyWordQuery, Pageable pageable);

    Page<BookAllInfoResDTO> findAllForOne(Pageable pageable);
}
