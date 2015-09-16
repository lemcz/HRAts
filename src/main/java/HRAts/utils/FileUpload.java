package HRAts.utils;

import HRAts.model.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUpload {

    private static final Logger logger = LoggerFactory
            .getLogger(FileUpload.class);

    public FileUpload() {
        super();
    }

    private File createFileOnServer(MultipartFile file, File usersDirectory, String name) throws IOException {
        byte[] bytes = file.getBytes();
        File serverFile = new File(usersDirectory.getAbsolutePath() + File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        logger.info("Server File Location = " + serverFile.getAbsolutePath());
        return serverFile;
    }

    private File createFileDirectory(String attachmentCategory, int usersIdDirectory) {

        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "UploadedFiles" + File.separator + attachmentCategory+ "Files" + File.separator + usersIdDirectory);

        dir.mkdirs();

        return dir;
    }

    public List<Attachment> uploadFiles(MultipartFile[] fileList, EntityTypeEnum entityType, int entityId) throws IOException {

        List<Attachment> attachmentList = new ArrayList<>();

        // Creating the directory to store file
        File usersDirectory = createFileDirectory(entityType.toString(), entityId);

        for (int i = 0; i < fileList.length; i++) {
            MultipartFile file = fileList[i];
            String name = file.getOriginalFilename();
            // Create the file on server
            File serverFile = createFileOnServer(file, usersDirectory, name);

            Attachment currentFile = new Attachment();
            currentFile.setName(name);
            currentFile.setFilePath(serverFile.getAbsolutePath());

            attachmentList.add(currentFile);

        }

        return attachmentList;
    }
}
