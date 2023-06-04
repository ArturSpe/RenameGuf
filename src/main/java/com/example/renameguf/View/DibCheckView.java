package com.example.renameguf.View;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public interface DibCheckView {
    void showFiles (List<File> fileList);

}
