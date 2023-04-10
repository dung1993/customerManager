package com.cg.model.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.cg.model.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {

    private Long id;

    @NotBlank(message = "Name can not blank")
    private String name;

    @NotBlank(message = "Author Amount can not blank")
    private String author;

    @NotBlank(message = "Price can not blank")
    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "Price is not valid number")
    private String price;

    @NotBlank(message = "Quantity can not blank")
    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "Quantity is not valid number")
    private String quantity;

    public Book toBook(BookAvatar bookAvatar) {
        return new Book()
                .setId(id)
                .setName(name)
                .setAuthor(author)
                .setPrice(price)
                .setQuantity(quantity)
                .setBookAvatar(bookAvatar)
                ;
    }

    public BookResDTO toBookResDTO(BookAvatarDTO bookAvatarDTO) {
        return new BookResDTO()
                .setId(id)
                .setName(name)
                .setAuthor(author)
                .setPrice(price)
                .setQuantity(quantity)
                .setBookAvatar(bookAvatarDTO)
                ;
    }

    public BookReqDTO toBookReqDTO(MultipartFile multipartFile) {
        return new BookReqDTO()
                .setId(id)
                .setName(name)
                .setAuthor(author)
                .setPrice(price)
                .setQuantity(quantity)
                .setAvatarFile(multipartFile)
                ;
    }

}
