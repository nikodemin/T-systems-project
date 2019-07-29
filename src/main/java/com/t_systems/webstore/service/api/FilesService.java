package com.t_systems.webstore.service.api;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;

public interface FilesService {

    List<String> saveUploadedFiles(CommonsMultipartFile[] files) throws IOException;
}
