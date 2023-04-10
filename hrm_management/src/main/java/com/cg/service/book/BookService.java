package com.cg.service.book;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.*;
import com.cg.repository.*;
import com.cg.service.uploadMedia.UploadService;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class BookService implements IBookService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookAvatarRepository bookAvatarRepository;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UploadUtils uploadUtils;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        book.setDeleted(true);
        bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Boolean existsByNameEquals(String name) {
        return bookRepository.existsByNameEquals(name);
    }

    @Override
    public Page<BookAllInfoResDTO> findAllForOne(String keyWordQuery, Pageable pageable){
        return bookRepository.findAllForOne(keyWordQuery, pageable);
    }

    @Override
    public Page<BookAllInfoResDTO> findAllForOne(Pageable pageable){
        return bookRepository.findAllForOne(pageable);
    }

    @Override
    public Page<BookAllInfoResDTO> findAllByKeyWordByPageByDeletedIsFalse(String keyWordQuery, Pageable pageable){
        return bookRepository.findAllByKeyWordByPageByDeletedIsFalse(keyWordQuery, pageable);
    }

    @Override
    public Optional<BookAllInfoResDTO> findBookResDTOById(Long bookId){
        return bookRepository.findBookResDTOById(bookId);
    }

    @Override
    public Optional<Book> findByBookAvatarId(String string){
        return bookRepository.findByBookAvatarId(string);
    }


    private void uploadAndSaveBookAvatar(MultipartFile file, BookAvatar bookAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(file, uploadUtils.buildImageUploadParams(bookAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            bookAvatar.setFileName(bookAvatar.getId() + "." + fileFormat);
            bookAvatar.setFileUrl(fileUrl);
            bookAvatar.setFileFolder(uploadUtils.IMAGE_UPLOAD_FOLDER);
            bookAvatar.setCloudId(bookAvatar.getFileFolder() + "/" + bookAvatar.getId());
            bookAvatarRepository.save(bookAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    @Override
    public BookResDTO create(BookReqDTO bookReqDTO) {

        BookAvatar bookAvatar = new BookAvatar();
        bookAvatarRepository.save(bookAvatar);
        uploadAndSaveBookAvatar(bookReqDTO.getAvatarFile(), bookAvatar);

        Book book = bookReqDTO.toBook(bookAvatar);
        book.setId(null);
        bookRepository.save(book);


        return new BookResDTO(book, bookAvatar);
    }

    @Override
    public BookResDTO createNoAvatar(BookDTO bookDTO) {

        BookAvatar bookAvatar = new BookAvatar();
        bookAvatar.setFileFolder("module_4_BookStoreManagerment");
        bookAvatar.setFileName("9492f497-eb9d-46bd-b132-fc82463200cb.png");
        bookAvatar.setCloudId("module_4_BookStoreManagerment/9492f497-eb9d-46bd-b132-fc82463200cb.png");
        bookAvatar.setFileUrl("https://res.cloudinary.com/cloudinarymen/image/upload/v1680248744/module_4_BookStoreManagerment/9492f497-eb9d-46bd-b132-fc82463200cb.png");

        bookAvatarRepository.save(bookAvatar);

        Book book = bookDTO.toBook(bookAvatar);

        book.setId(null);

        bookRepository.save(book);

        return new BookResDTO(book, bookAvatar);
    }

    @Override
    public BookResDTO createWithAvatar(BookReqDTO bookReqDTO) {

        BookAvatar bookAvatar = new BookAvatar();
        bookAvatarRepository.save(bookAvatar);
        uploadAndSaveBookAvatar(bookReqDTO.getAvatarFile(), bookAvatar);

        Book book = bookReqDTO.toBook(bookAvatar);
        book.setId(null);
        bookRepository.save(book);


        return new BookResDTO(book, bookAvatar);
    }

    @Override
    public BookResDTO updateNoAvatar(BookDTO bookDTO, Optional<Book> bookOptional, Long bookId) {

        BookAvatar bookAvatar = bookAvatarRepository.findById(bookOptional.get().getBookAvatar().getId()).get();
        Book book = bookDTO.toBook(bookAvatar);
        book.setId(bookId);
        bookRepository.save(book);

        return new BookResDTO(book, bookAvatar);
    }

    @Override
    public BookResDTO updateWithAvatar(BookReqDTO bookReqDTO ,Long bookId) {

        BookAvatar bookAvatar = new BookAvatar();
        bookAvatarRepository.save(bookAvatar);
        uploadAndSaveBookAvatar(bookReqDTO.getAvatarFile(), bookAvatar);

        Book book = bookReqDTO.toBook(bookAvatar);
        book.setId(bookId);
        bookRepository.save(book);

        return new BookResDTO(book, bookAvatar);
    }

}
