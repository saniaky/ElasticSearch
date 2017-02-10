package com.saniaky.service;

import com.saniaky.entity.Phone;

import java.util.List;

/**
 * @author saniaky
 * @since 2/10/17
 */
public interface SearchService {

    List<Phone> searchPhones(String username, boolean onlyOfficePhones);

}
