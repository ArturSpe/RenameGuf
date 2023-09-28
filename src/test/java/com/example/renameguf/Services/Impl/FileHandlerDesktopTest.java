package com.example.renameguf.Services.Impl;

import com.example.renameguf.Utils.Impl.FileProviderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.*;

@SpringBootTest(classes = {FileProviderImpl.class})
class FileHandlerDesktopTest {

    @Autowired
    FileProviderImpl fileProvider;

    Map<String, List<File>> testMap;
    List<File> testListFile;

    FolderWrapper folderWrapper;

    @Test
    void getMapFolderListFile() throws IOException {

        testMap = fileProvider.getMapFolderListFile(folderWrapper.getMainFolder().getRoot().getAbsolutePath(), true);

        Assertions.assertNotNull(testMap);
        Assertions.assertEquals(folderWrapper.countAllFolder, testMap.size());

        for (Map.Entry<String, List<File>> entry : testMap.entrySet()){
            Assertions.assertEquals(folderWrapper.countInnerFileInInnerFolder, entry.getValue().size());
        }


    }

    @Test
    void getListAllFile() throws IOException {

        testListFile = fileProvider.getFiles(folderWrapper.getMainFolder().getRoot().getAbsolutePath(),true );
        Assertions.assertEquals(folderWrapper.countAllFile, testListFile.size());
    }


    @BeforeEach
    public void initializing() throws IOException {

        this.folderWrapper = new FolderWrapper(3, 3);
        folderWrapper.createFolder();
        folderWrapper.createFiles();

    }

    private static class FolderWrapper {

        private final int countInnerFolder;

        private final int countAllFolder;
        private final int countInnerFileInInnerFolder;

        private int countAllFile;

        private List<File> innerFolderList;

        private final TemporaryFolder temporaryFolder;

        public FolderWrapper (int countInnerFolder, int countInnerFileInInnerFolder) throws IOException {
            this.countInnerFolder = countInnerFolder;
            this.countInnerFileInInnerFolder = countInnerFileInInnerFolder;
            countAllFolder = countInnerFolder + 1;
            countAllFile = 0;

            temporaryFolder = new TemporaryFolder();
            temporaryFolder.create();

            innerFolderList = new ArrayList<>();

        }

        public void createFolder () throws IOException {

            int i = 0;
            while (i != countInnerFolder){
                innerFolderList.add(temporaryFolder.newFolder());
                i++;
            }
        }

        public void createFiles () throws IOException {

            int fileInMainFolderCount = 0;

            while (fileInMainFolderCount != countInnerFileInInnerFolder){
                fileInMainFolderCount++;
                countAllFile++;
                temporaryFolder.newFile("innerFile" + countAllFile);
            }

            for (File innerFolder : innerFolderList){
                int i = 0;
                while (i != countInnerFileInInnerFolder){
                    countAllFile++;
                    i++;
                    File innerFile = new File(innerFolder, "innerFile" + countAllFile);
                    innerFile.createNewFile();
                }
            }
        }

        public TemporaryFolder getMainFolder (){
            return temporaryFolder;
        }

        public int getCountAllFile (){
            return countAllFile;
        }

    }


}