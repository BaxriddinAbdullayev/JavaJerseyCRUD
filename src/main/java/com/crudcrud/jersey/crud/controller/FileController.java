package com.crudcrud.jersey.crud.controller;

import com.crudcrud.jersey.crud.annotations.PublicAll;
import com.crudcrud.jersey.crud.domain.model.Result;
import com.crudcrud.jersey.crud.exceptions.FileIOException;
import com.crudcrud.jersey.crud.utils.Localization;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/file")
@PublicAll
public class FileController {

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2 MB

    private static final String UPLOAD_PATH = "/Users/bahriddin/Desktop/windows works/upload/upload_files/";

    private static final String FILE_DIRECTORY = "/Users/bahriddin/Desktop/windows works/upload/download_file";

    @Inject
    private Localization localization;


    @POST
    @Path("/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    public Result<String> uploadPdfFile(@FormDataParam("file") InputStream fileInputStream,
                                        @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception {

        if(fileInputStream==null || fileMetaData==null ||fileMetaData.getFileName().isEmpty()){
            throw new FileIOException(localization.getMessage("exeption.file-empty"));
        }

        try {
            int read = 0;
            int totalRead=0;
            byte[] bytes = new byte[8*1024];

            OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + fileMetaData.getFileName()));
            while ((read = fileInputStream.read(bytes)) != -1) {
                totalRead+=read;
                if(totalRead>MAX_FILE_SIZE){
                    out.close();
                    throw new FileIOException(localization.getMessage("exeption.file-too-long"));
                }
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new FileIOException(localization.getMessage("exeption.uploading-file"));
        }
        return new Result<>(0,
                 localization.getMessage("success.uploaded"),
                null);
    }

    @GET
    @Path("/download/{fileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("fileName") String fileName) {
        File file = new File(FILE_DIRECTORY + File.separator + fileName);
        if (!file.exists()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Fayl topilmadi: " + fileName)
                    .build();
        }

        try {
            InputStream fileInputStream = new FileInputStream(file);

            StreamingOutput output = new StreamingOutput() {
                @Override
                public void write(OutputStream outputStream) throws IOException {

                    try (InputStream in = fileInputStream) {
                        byte[] buffer = new byte[8*1024];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }

                }
            };
            return Response.ok(output)
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Faylni yuklashda xato yuz berdi.")
                    .build();
        }
    }

//    @GET
//    @Path("/view/{fileName}")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    public Response viewFile(@PathParam("fileName") String fileName) {
//        File file = new File(FILE_DIRECTORY + File.separator + fileName);
//        if (!file.exists()) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("Fayl topilmadi: " + fileName)
//                    .build();
//        }
//
//        String mimeType;
//        try {
//            java.nio.file.Path path= Paths.get(file.getAbsolutePath());
//            mimeType = Files.probeContentType(path);
//        } catch (IOException e) {
//            mimeType = "application/octet-stream"; // Standart MIME turi
//        }
//
//        String mediaType = getMediaType(fileName);
//        return Response.ok(file,mediaType)
//                .header("Content-Disposition", "inline; filename=\"" + fileName + "\"")
//                .build();
//    }

    @GET
    @Path("/view/{fileName}-demo")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response viewFile(@PathParam("fileName") String fileName) {
        File file = new File(FILE_DIRECTORY + File.separator + fileName);
        if (!file.exists()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Fayl topilmadi: " + fileName)
                    .build();
        }

        String mediaType = getMediaType(fileName);
        return Response.ok(file,mediaType)
                .header("Content-Disposition", "inline; filename=\"" + fileName + "\"")
                .build();
    }

    private String getMediaType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".pdf")) {
            return "application/pdf";
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

}
