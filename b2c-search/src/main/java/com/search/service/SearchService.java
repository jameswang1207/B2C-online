package com.search.service;

import com.search.pojo.SearchResult;

public interface SearchService {
    public SearchResult search(String queryString, int page, int rows) throws Exception;
}
