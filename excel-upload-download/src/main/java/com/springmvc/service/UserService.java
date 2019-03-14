package com.springmvc.service;

import org.springframework.web.multipart.MultipartFile;

import com.springmvc.pojo.ResultObject;


public interface UserService {

    ResultObject uploadExcel(MultipartFile file);
    
    //使用easyexcel
    ResultObject uploadEasyExcel(MultipartFile file);

}
