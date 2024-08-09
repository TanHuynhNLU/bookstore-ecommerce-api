package com.example.bookstoreecommerceapi.services.impl;

import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.ContactNotFoundException;
import com.example.bookstoreecommerceapi.models.Book;
import com.example.bookstoreecommerceapi.models.Contact;
import com.example.bookstoreecommerceapi.repositories.ContactRepository;
import com.example.bookstoreecommerceapi.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public ResponseObject getAllContacts() {
        return new ResponseObject(HttpStatus.OK, "Thành công", contactRepository.findAll());
    }

    @Override
    public PaginationResponse getAllContactsPaginationAndSorting(int page, int size, String sort) {
        Sort.Direction direction = sort.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String property = direction == Sort.Direction.DESC ? sort.substring(1) : sort;
        Pageable pageable = PageRequest.of(page, size, direction, property);
        Page<Contact> bookPage = contactRepository.findAll(pageable);
        PaginationResponse paginationResponse =
                new PaginationResponse(bookPage.getTotalElements(), bookPage.getContent(), bookPage.getTotalPages(), bookPage.getNumber());
        return paginationResponse;
    }

    @Override
    public ResponseObject getContactById(long id) throws ContactNotFoundException {
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if (contactOptional.isPresent()) {
            return new ResponseObject(HttpStatus.OK, "Thành công", contactOptional.get());
        } else {
            throw new ContactNotFoundException("Không tìm thấy liên hệ");
        }
    }

    @Override
    public ResponseObject addNewContact(Contact contact) {
        contact.setCreatedDate(new Date());
        contact.setStatus("Chưa phản hồi");
        return new ResponseObject(HttpStatus.CREATED, "Thêm liên hệ thành công", contactRepository.save(contact));
    }

    @Override
    public ResponseObject updateContact(long id, Contact contact) throws ContactNotFoundException {
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if (contactOptional.isPresent()) {
            Contact updatedContact = contactOptional.get();
            updatedContact.setName(contact.getName());
            updatedContact.setEmail(contact.getEmail());
            updatedContact.setTitle(contact.getTitle());
            updatedContact.setContent(contact.getContent());
            updatedContact.setRepliedDate(new Date());
            return new ResponseObject(HttpStatus.OK, "Cập nhật thành công", contactRepository.save(updatedContact));
        } else {
            throw new ContactNotFoundException("Không tìm thấy liên hệ");
        }
    }

    @Override
    public ResponseObject updateContactPartially(long id, Map<String, Object> fields) throws ContactNotFoundException {
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if (contactOptional.isPresent()) {
            Contact updatedContact = contactOptional.get();
            updatedContact.setRepliedDate(new Date());
            updatedContact.setStatus("Đã phản hồi");
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Contact.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, updatedContact, value);
            });
            return new ResponseObject(HttpStatus.OK, "Cập nhật thành công", contactRepository.save(updatedContact));
        } else {
            throw new ContactNotFoundException("Không tìm thấy liên hệ");
        }
    }

    @Override
    public ResponseObject deleteContact(long id) {
        contactRepository.deleteById(id);
        return new ResponseObject(HttpStatus.OK, "Thành công", id);
    }


}
