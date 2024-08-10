package com.in.cafe.serviceimpl;

import com.in.cafe.dao.BillDao;
import com.in.cafe.dao.CategoryDao;
import com.in.cafe.dao.ProductDao;
import com.in.cafe.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
         Map<String,Object> map = new HashMap<>();
         map.put("category",categoryDao.count());
         map.put("product",productDao.count());
         map.put("bill",billDao.count());
         return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
