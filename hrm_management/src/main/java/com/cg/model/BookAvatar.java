package com.cg.model;


import com.cg.model.dto.BookAvatarDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book_avatar")
public class BookAvatar {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_folder")
    private String fileFolder;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "cloud_id")
    private String cloudId;


    public BookAvatarDTO toBookAvatarDTO() {
        return new BookAvatarDTO()
                .setId(id)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setFileType(fileType)
                .setCloudId(cloudId)
                ;
    }
}
