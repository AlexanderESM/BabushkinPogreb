package ru.relex.service.impl;

import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import ru.relex.dao.AppDocumentDAO;
import ru.relex.dao.AppPhotoDAO;
import ru.relex.entity.AppDocument;
import ru.relex.entity.AppPhoto;
import ru.relex.entity.BinaryContent;
import ru.relex.service.FileService;
import ru.relex.utils.CryptoTool;

import java.io.File;
import java.io.IOException;

@Log4j
@Service // создаёт спринговый бин
public class FileServiceImpl implements FileService {
    private final AppDocumentDAO appDocumentDAO;
    private final AppPhotoDAO appPhotoDAO;
    private final CryptoTool cryptoTool;

    public FileServiceImpl(AppDocumentDAO appDocumentDAO,
                           AppPhotoDAO appPhotoDAO,
                           CryptoTool cryptoTool
    ) {
        this.appDocumentDAO = appDocumentDAO;
        this.appPhotoDAO = appPhotoDAO;
        this.cryptoTool = cryptoTool;
    }


    @Override
    public AppDocument getDocument(String hash) {
        var id = cryptoTool.idOf(hash);
        if (id == null) {
            return null;
        }
        return appDocumentDAO.findById(id).orElse(null);
    }

    @Override
    public AppPhoto getPhoto(String hash) {
        var id = cryptoTool.idOf(hash);
        if (id == null) {
            return null;
        }
        return appPhotoDAO.findById(id).orElse(null);
    }

//    @Override
//    public AppDocument getDocument(String docID) {
//        //TODO
//        var id = Long.parseLong(docID);
//        return appDocumentDAO.findById(id).orElse(null);
//    }
//
//    @Override
//    public AppPhoto getPhoto(String photoId) {
//        var id  = Long.parseLong(photoId);
//        return appPhotoDAO.findById(id).orElse(null);
//    }

    /**
     * массив байд из БД преобразуем в объект класс FileSystemResource
     * который можно отправить в теле ответа пользователю и браузер скачает файл
     * @param binaryContent
     * @return
     */
    @Override
    public FileSystemResource getFilSystemResorce(BinaryContent binaryContent) {
        // созда1м временный файл с расширением bin
        try {
            File temp = File.createTempFile("tempFile","bin");
            temp.deleteOnExit(); // удаляет файл из памяти при завершении работы приложения
            // записываем массив байт в объект файла
            FileUtils.writeByteArrayToFile(temp, binaryContent.getFileAsArrayOfBytes());
            return new FileSystemResource(temp);
        }catch (IOException e){
            log.error(e);
        }
        return null;
    }
}
