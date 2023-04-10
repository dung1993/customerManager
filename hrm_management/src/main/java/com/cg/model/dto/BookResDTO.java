package com.cg.model.dto;

import com.cg.model.Book;
import com.cg.model.BookAvatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookResDTO {

    private Long id;
    private String name;
    private String author;
    private String price;
    private String quantity;

    private BookAvatarDTO bookAvatar;

    public BookResDTO(Book book, BookAvatar bookAvatar){
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.price = book.getPrice();
        this.quantity = book.getQuantity();
        this.bookAvatar = bookAvatar.toBookAvatarDTO();
    }
}
