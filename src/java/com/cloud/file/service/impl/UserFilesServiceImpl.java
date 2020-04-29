package com.cloud.file.service.impl;

import com.cloud.file.dao.IUserFilesDao;
import com.cloud.file.entity.Files;
import com.cloud.file.service.IUserFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserFilesServiceImpl implements IUserFilesService {
    @Autowired
    private IUserFilesDao filesDao;

    @Override
    public List<Files> getAllUserFiles(String username, Integer isdelete) {

        return filesDao.getAllUserFiles(username, isdelete);
    }

    @Override
    public void save(Files userfile) {
//        userfile.setIsImg();
        String image = userfile.getType().startsWith("image") ? "是" : "否";
        userfile.setIsImg(image);
        userfile.setDowncounts(0);
        userfile.setUploadTime(new Date());
        filesDao.save(userfile);
    }

    @Override
    public Files findFileById(String id) {
        return filesDao.findFileById(id);
    }

    @Override
    public void update(Files userFile) {
        filesDao.update(userFile);
    }

    @Override
    public void deleteById(String id) {
        filesDao.deleteById(id);
    }

    @Override
    public void updateDel(Integer id,Integer i) {
        filesDao.updateDel(id, i);
    }

    @Override
    public List<Files> findFileByName(String text, String username, Integer i) {
        System.out.println(text);
        System.out.println(username);
        System.out.println(i);
        return filesDao.findFileByName(text,username,i);
    }


}
