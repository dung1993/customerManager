package com.cg.api;

import com.cg.exception.EmailExistsException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.*;
import com.cg.model.dto.*;
import com.cg.repository.BookRepository;
import com.cg.service.book.IBookService;
import com.cg.service.bookAvatar.IBookAvatarService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;


@RestController
@RequestMapping("/api/books")
public class BookAPI {

    @Autowired
    private IBookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private IBookAvatarService bookAvatarService;

    @Autowired
    private AppUtils appUtils;


    @GetMapping("{bookId}")
    public ResponseEntity<?> findBookById(@PathVariable Long bookId) {

        Optional<BookAllInfoResDTO> bookResDTO = bookService.findBookResDTOById(bookId);
        return new ResponseEntity<>(bookResDTO, HttpStatus.OK);

    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/delete/{bookId}")
    public ResponseEntity<Book> delete(@PathVariable Long bookId) {


        Optional<Book> bookOptional = bookService.findById(bookId);
        if (!bookOptional.isPresent()) {
            throw new ResourceNotFoundException("Book not found");
        }

        bookService.delete(bookOptional.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/update/{bookId}")
    public ResponseEntity<?> update(@PathVariable Long bookId, MultipartFile avatarFile, @Validated BookDTO bookDTO, BindingResult bindingResult ) {

        if(bindingResult.hasFieldErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Optional<Book> bookOptional = bookService.findById(bookId);
        if (!bookOptional.isPresent()) {
            throw new ResourceNotFoundException("Book not found");
        }

        if(bindingResult.hasFieldErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }

        BookResDTO bookResDTO;

        if(avatarFile == null){
            bookResDTO = bookService.updateNoAvatar(bookDTO, bookOptional, bookId);
        }
        else {

            String file = avatarFile.getContentType();
            assert file != null;
            file = file.substring(0, 5);

            if (!file.equals(EFileType.IMAGE.getValue())) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            long fileSize = avatarFile.getSize();

            if (fileSize > 512000) {
                return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
            }

            BookReqDTO bookReqDTO = bookDTO.toBookReqDTO(avatarFile);
            bookResDTO = bookService.updateWithAvatar(bookReqDTO, bookId);
        }

        return new ResponseEntity<>(bookResDTO, HttpStatus.CREATED);
    }

    @PostMapping("/kw={keyWord}&page={currentPageNumber}&sort={column},{orderBy}")
    public ResponseEntity<?> allForOne(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 5) Pageable pageable,
                                       @PathVariable String keyWord,
                                       @PathVariable int currentPageNumber,
                                       @PathVariable String column,
                                       @PathVariable String orderBy
                                       ) {
        int size = 5;

        if (orderBy.toLowerCase().equals("asc")){
            pageable = PageRequest.of(currentPageNumber, size, Sort.by(column).ascending());
        } else if (orderBy.toLowerCase().equals("desc")){
            pageable = PageRequest.of(currentPageNumber, size, Sort.by(column).descending());
        } else {
            pageable = PageRequest.of(currentPageNumber, size, Sort.by("id").descending());
        }

        String keyWordQuery = '%' + keyWord + '%';

        Page<BookAllInfoResDTO> bookAllInfoResDTOS = bookService.findAllForOne(keyWordQuery, pageable);

        if(bookAllInfoResDTOS.getNumberOfElements() == 0){
            pageable = PageRequest.of(currentPageNumber-1, size, Sort.by("id").descending());
            bookAllInfoResDTOS = bookService.findAllForOne(keyWordQuery, pageable);
        }

        return new ResponseEntity<>(bookAllInfoResDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(MultipartFile avatarFile,
                                    @Validated BookDTO bookDTO,
                                    BindingResult bindingResult
                                ){

        if(bindingResult.hasFieldErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Boolean existName = bookService.existsByNameEquals(bookDTO.getName());
        if (existName) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
//            throw new EmailExistsException("The name of book is exists");
        }

        if(avatarFile == null){
            BookResDTO bookResDTO = bookService.createNoAvatar(bookDTO);
            return new ResponseEntity<>(bookResDTO, HttpStatus.CREATED);
        }
        else {

            String file = avatarFile.getContentType();
            assert file != null;
            file = file.substring(0, 5);

            if (!file.equals(EFileType.IMAGE.getValue())) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            long fileSize = avatarFile.getSize();

            if (fileSize > 512000) {
                return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
            }

            BookReqDTO bookReqDTO = bookDTO.toBookReqDTO(avatarFile);
            BookResDTO bookResDTO = bookService.createWithAvatar(bookReqDTO);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }






}
