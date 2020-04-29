package com.cloud.file.service;

import com.cloud.file.entity.Files;

import java.util.List;

public interface IUserFilesService {

    List<Files> getAllUserFiles(String username, Integer isdelete);

    void save(Files userfile);

    Files findFileById(String id);

    void update(Files userFile);

    void deleteById(String id);

    void updateDel(Integer id,Integer i);

    List<Files> findFileByName(String text, String username, Integer i);

}
