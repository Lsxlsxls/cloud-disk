package com.cloud.file.controller;

import com.cloud.file.entity.Files;
import com.cloud.file.service.impl.UserFilesServiceImpl;
import com.cloud.user.controller.UserController;
import com.cloud.user.entitys.Users;
import com.cloud.user.service.impl.UserServiceimpl;
import com.cloud.utils.BucketObjectUtil;
import com.cloud.utils.RenamePhoto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.obs.services.ObsClient;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.PutObjectResult;
import com.obs.services.model.TemporarySignatureRequest;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;
import javax.annotation.Resource;
import javax.mail.Multipart;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("file")
public class FileController {
    private static final Logger LOG = Logger.getLogger(UserController.class);

    private static final String endPoint = "obs.cn-north-4.myhuaweicloud.com";

    private static final String ak = "2TKMMZ4LQ2Y7OKBTPEDK";

    private static final String sk = "eCiXCJiDFA8epstVRTNKs8Jn1gDi1uHvgqTaRg4h";

    private static final String bucketName = "obs-skd-04";

    @Autowired
    private UserServiceimpl userService;
    @Autowired
    private UserFilesServiceImpl userFilesService;

    public ObsClient getInstance() {
        return new ObsClient(ak, sk, endPoint);
    }


    @RequestMapping("/personalPhoto_upload")
    @ResponseBody
    public String personalPhoto_upload(HttpSession session, String photo_name,
                                       @RequestParam("file") MultipartFile mfile) throws IllegalStateException, IOException {
        String realPath = session.getServletContext().getRealPath("/upload");
        System.out.println(realPath);
        String fileName = mfile.getOriginalFilename();
        String uuid_name = RenamePhoto.rename(fileName);
        String target = realPath + "/" + uuid_name;
        mfile.transferTo(new File(target));
//        System.out.println(uuid_name);
        return uuid_name;
    }

    /**
     * 展示所有文件信息
     */
    @RequestMapping(value = "/all_files")
    public String getAllFiles(Users user, HttpServletRequest req, HttpSession session){
        System.out.printf("查询所有文件记录....");
        LOG.info("all_file...   " + user.getUsername());
        req.getParameter("username");
        user = userService.login(user);
        System.out.println();

        if (user!=null && user.getUserid()> 0) {
            PageHelper.startPage(1,5);
            Integer i = 0;//0--未删除
            List<Files> allUserFiles = userFilesService.getAllUserFiles(user.getUsername(), i);

            PageInfo<Files> pageInfo = new PageInfo<>(allUserFiles);

            session.setAttribute("user", user);

            req.setAttribute("pageInfo", pageInfo);
            req.setAttribute("allUserFiles",allUserFiles);

            return "showAllfiles";
        }

        req.setAttribute("login_error", "用户名称或密码不正确，请重新输入");
        return "login";
    }


    /**
     * 上传文件处理 并保存文件信息到数据库中
     */
    @RequestMapping(value = "/upload")
//    @ResponseBody
    public String uploadFiles(HttpServletRequest req,@RequestParam("file") MultipartFile mfile, HttpSession session) throws IOException {
        //获取上传文件的用户name
        Users user = (Users) session.getAttribute("user");

        System.out.printf("上传用户" + user.getUsername());
        //获取文件的原始名称
        String oldFileName = mfile.getOriginalFilename();

        //获取文件的后缀
        String extension = "." + FilenameUtils.getExtension(mfile.getOriginalFilename());

        //生成新的文件名层
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss ").format(new Date()) + UUID.randomUUID().toString().replace("-","") + extension;

        //文件的大小
        long size = mfile.getSize();

        //文件的类型
        String type = mfile.getContentType();

//        //根据日期生成目录
//        String realPath = session.getServletContext().getRealPath("/files");
////                ResourceUtils.getURL("classpath").getPath()+"/static/files" ;
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        String dateDirPath = realPath + "/" + dateFormat;
//        File dateDir = new File(dateDirPath);
//        if(!dateDir.exists())
//            dateDir.mkdirs();
//
//
//        //处理文件上传
//        mfile.transferTo(new File(dateDir, newFileName));


        //将文件信息放入数据库中
        Files userfile = new Files();
        userfile.setOldFileName(oldFileName);
        userfile.setNewFileName(newFileName);
        userfile.setExt(extension);
        userfile.setSize(String.valueOf(size));
        userfile.setType(type);
        userfile.setPath("/files/" + dateFormat);
        userfile.setUsername(user.getUsername());
        userfile.setIsdelete(0);

        userFilesService.save(userfile);

        req.getParameter("username");
        List<Files> allUserFiles = userFilesService.getAllUserFiles(user.getUsername(), 0);


//        String s = session.getServletContext().getRealPath(userfile.getPath())+ "/" + oldFileName ;
//        System.out.println("upload_path -->      " + s);
        InputStream is = mfile.getInputStream();
//        InputStream is=new FileInputStream(new File(s));;
        String objectKey = oldFileName;


        // is 对象的流
        ObsClient obsClient = getInstance();
        obsClient.putObject(bucketName,objectKey,is);
//        boolean flag = obsClient.doesObjectExist(bucketName, objectKey);
//        PutObjectResult result = null;
//        // 根据业务需求，决定是否覆盖
//        if (flag) {
//            // 已存在的文件会被覆盖
//            System.out.println("fugai----"+flag);
//            result = obsClient.putObject(bucketName, objectKey, is);
//        }
        obsClient.close();
//        System.out.println(result.getStatusCode());

        PageHelper.startPage(1,5);

        PageInfo<Files> pageInfo = new PageInfo<>(allUserFiles);

        session.setAttribute("user", user);

        req.setAttribute("pageInfo", pageInfo);
        req.setAttribute("allUserFiles",allUserFiles);

        return "showAllfiles";
    }


    /**
     * 文件下载 download
     */
//    @RequestMapping(value = "/down")
//    public void down(String openStyle, String id, HttpSession session,HttpServletResponse response) throws IOException {
//
//        Files userFile = userFilesService.findFileById(id);
//
//        ObsClient obsClient = getInstance();
//
//
//        boolean flag = obsClient.doesObjectExist(bucketName, objectKey);
//        if (flag) {
//            ObsObject object = obsClient.getObject(bucketName, objectKey);
//            return object;
//        }
//        return null;
//    }
//
//    }

    /**
     * 获取预览链接
     */
    @RequestMapping(value = "/preview")
    public void preview(String openStyle, String id, HttpSession session) throws IOException {
        Files userfile = userFilesService.findFileById(id);

        ObsClient obsClient = getInstance();
        String objectKey = userfile.getOldFileName();
        // 300 有效时间
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, 300);
        request.setBucketName(bucketName);
        request.setObjectKey(objectKey);
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        obsClient.close();
        String url = response.getSignedUrl();

        System.out.println("url     -----" + url);
    }


        @RequestMapping(value = "/download")
    public void downloadFiles(String openStyle, String id, HttpSession session,HttpServletResponse response) throws IOException {
        //获取打开方式
        openStyle = (openStyle==null)?"attachment" : openStyle;

        //获取到文件信息
        Files userFile = userFilesService.findFileById(id);

        if("attachment".equals(openStyle)){

            //更新下载次数
            userFile.setDowncounts(userFile.getDowncounts()+1);
            userFilesService.update(userFile);
        }
        //根据文件信息中的名字 和 文件存储路径获取文件输入流
        String realPath = session.getServletContext().getRealPath(userFile.getPath());

        //附件下载
        response.setHeader("content-disposition",openStyle+";fileName="+URLEncoder.encode(userFile.getOldFileName(),"UTF-8"));

        //获取文件输入流
        FileInputStream is = new FileInputStream(new File(realPath, userFile.getNewFileName()));

        //获取文件输出流
        ServletOutputStream os = response.getOutputStream();

        System.out.println(is +  "   --     " +os);

        //文件拷贝
        IOUtils.copy(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);

    }

    /**
     *放入回收站
     */
    @RequestMapping(value = "/delete")
    public String fileDelete(String id, HttpServletRequest req, HttpSession session,HttpServletResponse response) throws IOException {

        Files userFile = userFilesService.findFileById(id);
        Integer i = 1;

        userFilesService.updateDel(userFile.getId(), i);

        req.getParameter("username");

        Users user = (Users) session.getAttribute("user");

        List<Files> allUserFiles = userFilesService.getAllUserFiles(user.getUsername(), 0);

        PageHelper.startPage(1,5);

        PageInfo<Files> pageInfo = new PageInfo<>(allUserFiles);

        session.setAttribute("user", user);

        req.setAttribute("pageInfo", pageInfo);
        req.setAttribute("allUserFiles",allUserFiles);

        return "showAllfiles";

    }
    /**
     * 删除文件信息
     */
    @RequestMapping(value = "/realdelete")
    public String realdelete(String id, HttpServletRequest req, HttpSession session,HttpServletResponse response) throws IOException {

        Files userFile = userFilesService.findFileById(id);

        //删除文件
        String realPath = session.getServletContext().getRealPath(userFile.getPath());
        System.out.println(realPath);
        File file = new File(realPath, userFile.getNewFileName());
        if(file.exists())
            file.delete();//立即删除

        //删除数据库中的记录
        userFilesService.deleteById(id);

        req.getParameter("username");

        Users user = (Users) session.getAttribute("user");

        List<Files> allUserFiles = userFilesService.getAllUserFiles(user.getUsername(), 1);

        session.setAttribute("user", user);

        req.setAttribute("allUserFiles",allUserFiles);

        //
        ObsClient obsClient = getInstance();
        String objectKey = userFile.getOldFileName();
        //判断删除的文件是否存在
        boolean flag = obsClient.doesObjectExist(bucketName, objectKey);
        if(flag){
            obsClient.deleteObject(bucketName,objectKey);
        }
        obsClient.close();
        return "showAllfiles";

    }


    /**
     * 回收站
     */
    @RequestMapping(value = "/recycleBin")
    public String recycleBin(String username, HttpServletRequest req, HttpSession session){
        System.out.printf("回收站....");
        LOG.info("all_file...   " + username);

        req.getParameter("username");
        System.out.println();
        Integer i = 1;
        List<Files> allUserFiles = userFilesService.getAllUserFiles(username,i);//删除

        Users user = userService.getUserByName(username);

        session.setAttribute("user", user);

        req.setAttribute("allUserFiles",allUserFiles);

        return "recycle";
    }


    /**
     * 移出回收站
     */
    @RequestMapping(value = "/recover")
    public String recover(String id, HttpServletRequest req, HttpSession session,HttpServletResponse response) {

        Files userFile = userFilesService.findFileById(id);
        Integer i = 0;
        userFilesService.updateDel(userFile.getId(), i);

        req.getParameter("username");

        Users user = (Users) session.getAttribute("user");

        List<Files> allUserFiles = userFilesService.getAllUserFiles(user.getUsername(), 0);

        session.setAttribute("user", user);

        req.setAttribute("allUserFiles",allUserFiles);

        return "showAllfiles";

    }

    @RequestMapping(value = "/getAllFiles")
    public String getAllFiles(Integer pageNum, Integer maxPage, String username, HttpServletRequest req, HttpSession session) {

        Users user = userService.getUserByName(username);
        System.out.println("----" + username);
        if (pageNum <= 1) {
            pageNum = 1; // 分页的逻辑判断 ,如果当前页小于1就显示第一页
        } else if (pageNum >= maxPage) {
            pageNum = maxPage;// 如果当前页大于最大页就显示最大页
        }

        PageHelper.startPage(pageNum, 5);
        Integer i = 0;

        System.out.println(user.getUsername());

        List<Files> allUserFiles = userFilesService.getAllUserFiles(user.getUsername(), i);

        PageInfo<Files> pageInfo = new PageInfo<>(allUserFiles);
        System.out.println(pageInfo.getPageNum());

        session.setAttribute("user", user);

        req.setAttribute("pageInfo", pageInfo);
        req.setAttribute("allUserFiles", allUserFiles);
        return "showAllfiles";
    }

    @RequestMapping(value = "/back")
    public String back(String username, HttpServletRequest req, HttpSession session) {

        req.getParameter("username");
        Users user = userService.getUserByName(username);
        Integer i = 0;
        List<Files> allUserFiles = userFilesService.getAllUserFiles(user.getUsername(), i);

        session.setAttribute("user", user);
        req.setAttribute("allUserFiles", allUserFiles);

        return "showAllfiles";
    }


    @RequestMapping(value = "/search")
    public String search(String text, String username, HttpServletRequest req, HttpSession session) {

        text="%"+text+"%";
        Integer i = 0;
        System.out.println("查询文本    " + text);

        Users user = userService.getUserByName(username);

        req.getParameter("username");

        List<Files> userFiles = userFilesService.findFileByName(text, username, i);

        System.out.println("size   --- " + userFiles.size());

        session.setAttribute("user", user);

        req.setAttribute("allUserFiles", userFiles);

        return "showAllfiles";
    }




    /**
     * 返回所有文件列表 json
     */
//    @RequestMapping(value = "/allfilesJson")
//    @ResponseBody
//    public List<Files> getAllFilesJson(HttpSession session){
//        Users user = (Users) session.getAttribute("user");
//        List<Files> allUserFiles = userFilesService.getAllUserFiles(user.getUsername());
//        return allUserFiles;
//    }



}
