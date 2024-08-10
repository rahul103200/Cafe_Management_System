package com.in.cafe.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/*@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600) */
@CrossOrigin("*")
@RequestMapping(path = "/dashboard")
public interface DashboardRest {

    @GetMapping(path = "/details")
    ResponseEntity<Map<String,Object>> getCount();
}
