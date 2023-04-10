package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.cg.model.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookReqDTO implements Validator  {
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

    @NotNull(message = "Avatar File cant not blank")
    private MultipartFile avatarFile;

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

    @Override
    public boolean supports(Class<?> clazz) {
        return BookReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookReqDTO bookReqDTO = (BookReqDTO) target;

        MultipartFile multipartFile = bookReqDTO.getAvatarFile();
        if (multipartFile == null || multipartFile.getSize() == 0) {
            errors.rejectValue("file", "file.null", "Vui lòng chọn tệp tin làm ảnh đại diện");
            return;
        }

        String file = multipartFile.getContentType();
        assert file != null;
        file = file.substring(0, 5);

        if (!file.equals(EFileType.IMAGE.getValue())) {
            errors.rejectValue("file", "file.type", "Vui lòng chọn tệp tin ảnh đại diện phải là JPG hoặc PNG");
            return;
        }

        long fileSize = multipartFile.getSize();

        if (fileSize > 512000) {
            errors.rejectValue("file", "file.size", "Vui lòng chọn tệp tin ảnh đại diện nhỏ hơn 500 KB");
        }

    }
}
