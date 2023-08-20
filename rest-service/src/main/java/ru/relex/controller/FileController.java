package ru.relex.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.relex.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * обработка http запросов со стороны пользователя
 */
@Log4j
@RequestMapping("/file")
@RestController // в ответ вернёт RawDATA (json в body)
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-doc")
    public void getDoc(@RequestParam("id") String id, HttpServletResponse response) {
        //TODO для формирования badRequest добавить ControllerAdvice
        var doc = fileService.getDocument(id);
        if (doc == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType(MediaType.parseMediaType(doc.getMimeType()).toString());
        response.setHeader("Content-disposition", "attachment; filename=" + doc.getDocName());
        response.setStatus(HttpServletResponse.SC_OK);

        var binaryContent = doc.getBinaryContent();
        try {
            var out = response.getOutputStream();
            out.write(binaryContent.getFileAsArrayOfBytes());
            out.close();
        } catch (IOException e) {
            log.error(e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-photo")
    public void getPhoto(@RequestParam("id") String id, HttpServletResponse response) {
        //TODO для формирования badRequest добавить ControllerAdvice
        var photo = fileService.getPhoto(id);
        if (photo == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType(MediaType.IMAGE_JPEG.toString());
        response.setHeader("Content-disposition", "attachment;");
        response.setStatus(HttpServletResponse.SC_OK);

        var binaryContent = photo.getBinaryContent();
        try {
            var out = response.getOutputStream();
            out.write(binaryContent.getFileAsArrayOfBytes());
            out.close();
        } catch (IOException e) {
            log.error(e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    //спринговый билдер собирающий http ответ
                                   // описываем параметры get запроса

//    public ResponseEntity<?> getDoc(@RequestParam("id") String id ) {
//        var doc = fileService.getDocument(id);
//        if(doc == null){
//            return ResponseEntity.badRequest().build();
//        }
//        var binaryContent = doc.getBinaryContent();
//        var fileSystemResource = fileService.getFilSystemResorce(binaryContent);
//        if(fileSystemResource == null){
//            return ResponseEntity.internalServerError().build();
//        }
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(doc.getMimeType()))
//                .header("Content disposition", "attachment" + doc.getDocName())
//                .body(fileSystemResource);
//    }
}
