package com.cloud.file.dao;

import com.cloud.file.entity.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IUserFilesDao {

    //根据用户id获取用户文件列表
    @Select("select * from cloudpan.t_files where username=#{username} AND isdelete=#{isdelete}")
    public List<Files> getAllUserFiles(@Param("username")String username, @Param("isdelete")Integer isdelete);

    //保存用户的文件记录
    @Insert("INSERT INTO cloudpan.t_files (oldFileName,newFileName,ext,path,size,type,isImg,downcounts,uploadTime,username,isdelete) VALUES (#{oldFileName},#{newFileName},#{ext},#{path},#{size},#{type},#{isImg},#{downcounts},#{uploadTime},#{username},#{isdelete})")
    void save(Files userfile);

    //根据文件ID获取文件信息
    @Select("select * from cloudpan.t_files where id=#{id}")
    Files findFileById(String id);

    //更新下载次数
    @Update("UPDATE cloudpan.t_files SET downcounts=#{downcounts} WHERE id=#{id}")
    void update(Files userFile);

    @Delete("DELETE FROM cloudpan.t_files WHERE id=#{id}")
    void deleteById(String id);

    //放入回收站
    @Update("UPDATE cloudpan.t_files SET isdelete=#{isdelete} WHERE id=#{id}")
    void updateDel(@Param("id")Integer id,@Param("isdelete")Integer isdelete);

    //search
    //AND username=#{username} AND isdelete=#{i}
    @Select("select * from cloudpan.t_files where oldFileName like #{text} AND username=#{username} AND isdelete=#{i}")
    List<Files> findFileByName(@Param("text")String text,@Param("username")String username,@Param("i")Integer i);


}
