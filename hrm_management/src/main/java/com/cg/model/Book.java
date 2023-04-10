package com.cg.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
@Where(clause = "deleted = false")
public class Book extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name can not blank")
    private String name;

    @Column(name = "author")
    @NotBlank(message = "Author can not blank")
    private String author;

    @Column(name = "price")
    @NotBlank(message = "Price can not blank")
    private String price;

    @Column(name = "quantity")
    @NotBlank(message = "Quantity can not blank")
    private String quantity;

    @OneToOne
    @JoinColumn(name = "book_avatar_id", referencedColumnName = "id", nullable = false)
    private BookAvatar bookAvatar;


}
