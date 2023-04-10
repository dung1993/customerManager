package com.cg.repository;

import com.cg.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.model.Book;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Boolean existsByNameEquals(String name);



    @Query("SELECT NEW com.cg.model.dto.BookAllInfoResDTO (" +
                "b.id, " +
                "b.name, " +
                "b.author, " +
                "b.price, " +
                "b.quantity, " +
                "ba.id," +
                "ba.fileFolder, " +
                "ba.fileName, " +
                "ba.fileUrl, " +
                "ba.cloudId," +
                "ba.fileType" +
            ") " +
            "FROM Book AS b " +
            "LEFT JOIN BookAvatar AS ba " +
            "ON b.bookAvatar = ba " +
            "WHERE (b.name LIKE :keyWordQuery" +
            " or b.author LIKE :keyWordQuery" +
            " or b.price LIKE :keyWordQuery" +
            " or b.quantity LIKE :keyWordQuery" +
            ")"
    )
    Page<BookAllInfoResDTO> findAllByKeyWordByPageByDeletedIsFalse(@Param("keyWordQuery")String keyWordQuery, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.BookAllInfoResDTO (" +
                "b.id, " +
                "b.name, " +
                "b.author, " +
                "b.price, " +
                "b.quantity, " +
                "ba.id," +
                "ba.fileFolder, " +
                "ba.fileName, " +
                "ba.fileUrl, " +
                "ba.cloudId," +
                "ba.fileType" +
            ") " +
            "FROM Book AS b " +
            "LEFT JOIN BookAvatar AS ba " +
            "ON b.bookAvatar = ba " +
            "WHERE (b.name LIKE :keyWordQuery" +
            " or b.author LIKE :keyWordQuery" +
            " or b.price LIKE :keyWordQuery" +
            " or b.quantity LIKE :keyWordQuery" +
            ")"
    )
    Page<BookAllInfoResDTO> findAllForOne(@Param("keyWordQuery")String keyWordQuery, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.BookAllInfoResDTO (" +
                "b.id, " +
                "b.name, " +
                "b.author, " +
                "b.price, " +
                "b.quantity, " +
                "ba.id," +
                "ba.fileFolder, " +
                "ba.fileName, " +
                "ba.fileUrl, " +
                "ba.cloudId," +
                "ba.fileType" +
            ") " +
            "FROM Book AS b " +
            "LEFT JOIN BookAvatar AS ba " +
            "ON b.bookAvatar = ba "
    )
    Page<BookAllInfoResDTO> findAllForOne(Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.BookAllInfoResDTO (" +
                "b.id, " +
                "b.name, " +
                "b.author, " +
                "b.price, " +
                "b.quantity, " +
                "ba.id," +
                "ba.fileFolder, " +
                "ba.fileName, " +
                "ba.fileUrl, " +
                "ba.cloudId," +
                "ba.fileType" +
            ") " +
            "FROM Book AS b " +
            "LEFT JOIN BookAvatar AS ba " +
            "ON b.bookAvatar = ba " +
            "WHERE b.id = :bookId"
    )
    Optional<BookAllInfoResDTO> findBookResDTOById(@Param("bookId")Long bookId);

    @Query("SELECT NEW com.cg.model.Book (" +
                "b.id, " +
                "b.name, " +
                "b.author, " +
                "b.price, " +
                "b.quantity, " +
                "b.bookAvatar" +
            ") " +
            "FROM Book AS b " +
            "WHERE b.bookAvatar.id = :string "
    )
    Optional<Book> findByBookAvatarId(@Param("string")String string);


}
