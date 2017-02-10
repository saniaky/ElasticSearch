package com.saniaky.controller;

import com.saniaky.entity.Phone;
import com.saniaky.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author saniaky
 * @since 2/10/17
 */
@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/phones")
    public List<Phone> searchUserPhones(@RequestParam("username") String username,
                                        @RequestParam(required = false) boolean onlyOfficePhones) {

        return searchService.searchPhones(username, onlyOfficePhones);
    }

}
