/**
 * Amitha_Murali, 001643826, murali.a@husky.neu.edu
 * Jyoti Sharma, 001643410, sharma.j@husky.neu.edu
 * Surabhi Patil, 001251860, patil.sur@husky.neu.edu
 **/

package com.csye6225.demo.controllers;

import com.amazonaws.HttpMethod;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.csye6225.demo.dao.FileAttachmentRepository;
import com.csye6225.demo.dao.TaskRepository;
import com.csye6225.demo.entities.FileAttachment;
import com.csye6225.demo.entities.Task;
import com.csye6225.demo.helpers.Helper;
import com.csye6225.demo.service.S3ServicesImpl;
import com.google.gson.JsonObject;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;

@Controller    // This means that this class is a Controller

public class FileAttachmentController {

    @Autowired
    private Helper helper;

    @Autowired
    // This means to get the bean called userRepository which is auto-generated by Spring, we will use it to handle the data
    private FileAttachmentRepository fileAttachmentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AmazonS3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Autowired
    private S3ServicesImpl s3ServiceImpl;

    @RequestMapping(value="/tasks/{id}/attachments",method= RequestMethod.GET,produces="application/json")
    public @ResponseBody String getAttachedFilesForTask (HttpServletRequest request, HttpServletResponse response){

        JsonObject jsonObject = new JsonObject();

        //Write add file code here

        String header = request.getHeader("Authorization");
        String taskId = request.getRequestURI().split("/")[2];
        if(header != null) {

            int userID = helper.GetUserDetails(header);

            if(userID > -1) {

                if(taskId !="") {
                    Task task = taskRepository.findOne(taskId);
                    if(task.getUserId() == userID) {

                        ArrayList<String> fileList = helper.getFileList( taskId );

                        if (fileList == null) {
                            jsonObject.addProperty( "message", "No file exists." );
                            return jsonObject.toString();
                        } else {
                            for(String filePath : fileList)
                            {jsonObject.addProperty( "message", "File attached to the task " + filePath );}
                            return jsonObject.toString();
                        }
                    }
                    else {
                        response.setStatus( HttpServletResponse.SC_FORBIDDEN );
                        jsonObject.addProperty( "message", "You are not allowed to perform this activity." );
                        return jsonObject.toString();
                    }
                }
                response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
                jsonObject.addProperty("message", "Invalid Task Id.");
                return jsonObject.toString();
            }
        }

        else{
            response.setStatus( HttpServletResponse.SC_UNAUTHORIZED);
            jsonObject.addProperty("message", "You are not authorized to perform this activity");
        }
        return jsonObject.toString();

    }

    @RequestMapping(value="/tasks/{id}/attachments",method= RequestMethod.POST,produces="application/json")
    public @ResponseBody String attachFileToTask (HttpServletRequest request, HttpServletResponse response, @RequestParam ("file")MultipartFile theFile){

        System.out.println("I am here");

        JsonObject jsonObject = new JsonObject();

        //Write add file code here

        // location to store file uploaded
        final String UPLOAD_DIRECTORY = "/home/surabhi/Documents/NSCC/Assignment 6 file upload";

        // upload settings
        final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
        final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
        final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

        String filePath = "";
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // constructs the directory path to store upload file
        // this path is relative to application's directory
        //String uploadPath = getServletContext().getRealPath("")+ File.separator + UPLOAD_DIRECTORY;

        // creates the directory if it does not exist
        //File uploadDir = new File("/home/surabhi/Documents/NSCC/Assignment 6 file upload");
        String uploadsDir = "/uploads/";
        String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
        if (!new File(realPathtoUploads).exists()) {
            new File(realPathtoUploads).mkdir();
        }

        try {
            // parses the request's content to extract file data


            String orgName = theFile.getOriginalFilename();
            String filePath2 = realPathtoUploads + orgName;

            File dest = new File(filePath2);
            theFile.transferTo(dest);
            String key = Instant.now().getEpochSecond() + "_" + dest.getName();
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            generatePresignedUrlRequest.setExpiration(DateTime.now().plusDays(4).toDate());

            URL signedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            filePath = "/home/surabhi/Documents/NSCC/Assignment 6 file upload" + File.separator + theFile.getOriginalFilename();
            //File storeFile = new File(signedUrl.toString());


                        // saves the file on disk
                        //item.write(storeFile);
            //theFile.transferTo( storeFile );
            System.out.println("reached here");
            s3ServiceImpl.uploadFile(key, dest);

                        request.setAttribute("message",
                                "Upload has been done successfully!");
                   // }
               // }
           // }
       } catch (Exception ex) {
            request.setAttribute("message",
                    "There was an error: " + ex.getMessage());
            System.out.println(ex.getMessage());
        }

        String header = request.getHeader("Authorization");
        String taskId = request.getRequestURI().split("/")[2];
        if(header != null) {

            int userID = helper.GetUserDetails(header);

            if(userID > -1) {

                if(taskId != "") {

                    Task task = taskRepository.findOne( taskId );

                    System.out.println("My path" + filePath);
                    if(task.getUserId() == userID) {

                        FileAttachment file = new FileAttachment();
                        file.setPath( filePath );
                        file.setTaskId( taskId );
                        file.setUserId( userID );
                        fileAttachmentRepository.save( file );

                        jsonObject.addProperty( "message", "File has been uploaded successfully for the User task." );
                        jsonObject.addProperty( "taskId", file.getTaskId());
                        jsonObject.addProperty( "fileId", file.getId());
                        jsonObject.addProperty( "filePath", file.getPath() );
                        return jsonObject.toString();
                    }
                    else {
                        response.setStatus( HttpServletResponse.SC_FORBIDDEN );
                        jsonObject.addProperty( "message", "You are not allowed to perform this activity." );
                        return jsonObject.toString();
                    }
                }
                jsonObject.addProperty("message", "Invalid Task Id.");
                return jsonObject.toString();
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            jsonObject.addProperty("message", "You are not authorized to perform this activity");
            return jsonObject.toString();
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        jsonObject.addProperty("message", "You are not authorized to perform this activity");
        return jsonObject.toString();

    }

    @RequestMapping(value="/tasks/{id}/attachments/{idAttachments}",method= RequestMethod.DELETE,produces="application/json")
    public @ResponseBody String deleteFile (HttpServletRequest request, HttpServletResponse response) {

        JsonObject jsonObject = new JsonObject();

        //Write delete file code here

        String header = request.getHeader("Authorization");
        String taskId = request.getRequestURI().split("/")[2];
        String fileAttachmentId = request.getRequestURI().split( "/" )[4] ;
        if(header != null) {

            int userID = helper.GetUserDetails(header);

            if(userID > -1) {

                if(taskId != "") {
                    if(fileAttachmentId !="") {

                       FileAttachment file = fileAttachmentRepository.findOne(fileAttachmentId ) ;

                       if(file != null) {
                           Task task = taskRepository.findOne( taskId );
                           if(task.getUserId() == userID){
                           if (file.getTaskId().equals(taskId)) {
                               if (file.getUserId() == userID) {
                                   FileAttachment delFile = fileAttachmentRepository.findOne(file.getId());
                                   fileAttachmentRepository.delete( delFile );
                                   jsonObject.addProperty( "message", "File has been deleted successfully for the User task." );
                                   return jsonObject.toString();
                               } else {
                                   response.setStatus( HttpServletResponse.SC_FORBIDDEN );
                                   jsonObject.addProperty( "message", "You are not allowed to perform this activity." );
                                   return jsonObject.toString();
                               }
                           }
                       }
                           else {
                               response.setStatus( HttpServletResponse.SC_FORBIDDEN );
                               jsonObject.addProperty( "message", "You are not allowed to perform this activity." );
                               return jsonObject.toString();
                           }
                       }

                    }
                    jsonObject.addProperty("message", "Invalid file Id.");
                    return jsonObject.toString();
                }
                jsonObject.addProperty("message", "Invalid Task Id.");
                return jsonObject.toString();
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        jsonObject.addProperty("message", "You are not authorized to perform this activity");
        return jsonObject.toString();

    }

}