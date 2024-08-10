package com.in.cafe.serviceimpl;

import com.in.cafe.JWT.JwtAuthencticationFilter;
import com.in.cafe.POJO.Category;
import com.in.cafe.POJO.Product;
import com.in.cafe.constants.CafeConstants;
import com.in.cafe.dao.ProductDao;
import com.in.cafe.service.ProductService;
import com.in.cafe.utils.CafeUtils;
import com.in.cafe.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtAuthencticationFilter jwtAuthencticationFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
            if(jwtAuthencticationFilter.isAdmin()){
                if(ValidateProductMap(requestMap,false)){
                  productDao.save(getProductFromMap(requestMap,false));
                  return CafeUtils.getResponseEntity("Product Added Successfully",HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }else{
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean ValidateProductMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name") && requestMap.containsKey("price")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean IsAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if(IsAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
         try{
            return new ResponseEntity<>(productDao.getAllProduct(),HttpStatus.OK);
         } catch (Exception ex){
             ex.printStackTrace();
         }
         return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            if(jwtAuthencticationFilter.isAdmin()){
                if(ValidateProductMap(requestMap,true)){
                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        Product product = getProductFromMap(requestMap,true);
                        product.setStatus(optional.get().getStatus());
                        productDao.save(product);
                        return CafeUtils.getResponseEntity("Product Updated Successfully",HttpStatus.OK);
                    }else {
                        return CafeUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
                    }
                }else{
                    return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            if(jwtAuthencticationFilter.isAdmin()){
                Optional optional = productDao.findById(id);
                if(!optional.isEmpty()){
                  productDao.deleteById(id);
                    return CafeUtils.getResponseEntity("Product Deleted Successfully.",HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Product id does not exit.",HttpStatus.OK);
            } else{
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            if(jwtAuthencticationFilter.isAdmin()){
                Optional optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                 if(!optional.isEmpty()){
                     productDao.updateProductStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                     return CafeUtils.getResponseEntity("Product Status Updated Successfully",HttpStatus.OK);
                 }
                 return CafeUtils.getResponseEntity("Product id does not exist",HttpStatus.OK);
            } else {
                CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try{
           return new ResponseEntity<>(productDao.getProductByCategory(id),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try{
           return new ResponseEntity<>(productDao.getProductById(id),HttpStatus.OK);
        } catch (Exception ex){
             ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
