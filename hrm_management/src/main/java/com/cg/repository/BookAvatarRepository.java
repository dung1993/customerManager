package com.cg.repository;

import com.cg.model.BookAvatar;
import com.cg.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

@Repository
public interface BookAvatarRepository extends JpaRepository<BookAvatar, String> {

    @Query("SELECT ba FROM BookAvatar AS ba JOIN Book AS bk ON bk.bookAvatar = ba AND bk.bookAvatar = :book")
    Optional<BookAvatar> findByBook(@Param("book") Book book);
}
