package com.search.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b2c.pojo.B2cResult;
import com.b2c.util.ExceptionUtil;
import com.search.pojo.SearchResult;
import com.search.service.SearchService;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;
    
    @RequestMapping(value="/query", method=RequestMethod.GET)
    @ResponseBody
    public B2cResult search(@RequestParam("q") String queryString, 
            @RequestParam(value="page", defaultValue="1") Integer page, 
            @RequestParam(value="rows", defaultValue="60") Integer rows) {
        //查询条件不能为空
        if (StringUtils.isEmpty(queryString)) {
            return B2cResult.build(400, "查询条件不能为空");
        }
        SearchResult searchResult = null;
        try {
            //配置乱码
            queryString= new String(queryString.getBytes("ISO-8859-1"),"utf-8");
            searchResult = searchService.search(queryString, page, rows);
        } catch (Exception e) {
            e.printStackTrace();
            return B2cResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return B2cResult.ok(searchResult);
    }
}
