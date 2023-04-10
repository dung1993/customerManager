package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookAllInfoResDTO {

    private Long id;
    private String name;
    private String author;
    private String price;
    private String quantity;

    private String avatarId;
    private String fileFolder;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String cloudId;

}
