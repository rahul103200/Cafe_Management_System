package com.in.cafe.serviceimpl;

import com.google.common.base.Strings;
import com.in.cafe.JWT.JwtAuthencticationFilter;
import com.in.cafe.POJO.Category;
import com.in.cafe.constants.CafeConstants;
import com.in.cafe.dao.CategoryDao;
import com.in.cafe.service.CategoryService;
import com.in.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtAuthencticationFilter jwtAuthencticationFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            if(jwtAuthencticationFilter.isAdmin()){
                if(validateCategoryMap(requestMap,false)){
                   categoryDao.save(getCategoryFromMap(requestMap,false));
                   return CafeUtils.getResponseEntity("Category Added Successfuly",HttpStatus.OK);
                }
            }else{
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
         return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean ValidateId) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && ValidateId){
                return true;
            }else if(!ValidateId){
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String,String> requestMap,Boolean isAdd){
        Category category = new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filerValue) {
         try{
              if(!Strings.isNullOrEmpty(filerValue) && filerValue.equalsIgnoreCase("true")){
                  return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK);
              }
              return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
         } catch(Exception ex){
             ex.printStackTrace();
         }
         return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
         try{
             if(jwtAuthencticationFilter.isAdmin()){
                 if(validateCategoryMap(requestMap,true)){
                     Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                     if(!optional.isEmpty()){
                         categoryDao.save(getCategoryFromMap(requestMap,true));
                         return CafeUtils.getResponseEntity("Category Updated Successfully",HttpStatus.OK);
                     }else{
                         return CafeUtils.getResponseEntity("Category id does not exist",HttpStatus.OK);
                     }
                 }
               return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
             }else{
               return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
             }
         } catch(Exception ex){
             ex.printStackTrace();
         }
         return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
