package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.ContactNotFoundException;
import com.example.bookstoreecommerceapi.models.Contact;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public interface ContactService {

    ResponseObject getAllContacts();

    PaginationResponse getAllContactsPaginationAndSorting(int page, int size, String sort);

    ResponseObject getContactById(long id) throws ContactNotFoundException;

    ResponseObject addNewContact(Contact contact);

    ResponseObject updateContact(long id, Contact contact) throws ContactNotFoundException;

    ResponseObject updateContactPartially(long id, Map<String, Object> contact) throws ContactNotFoundException;

    ResponseObject deleteContact(long id);

}
