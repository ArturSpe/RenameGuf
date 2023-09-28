package com.example.renameguf.Utils.Impl;

import com.example.renameguf.Model.GufDib;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.zip.ZipFile;

@Builder
@AllArgsConstructor
public class GufCheckEmailProcessor implements Runnable{

    private final String searchWord;
    private final List<File> listGuf;
    private final Set<GufDib> listDibGuf;
    private final CountDownLatch countDownLatch;
    private final Runnable updateBar;

    @Override
    public void run() {
        for (File file : listGuf) {
            if (file.getName().endsWith(".guf")) {
                try (ZipFile gufFile = new ZipFile(file)){
                    gufFile.stream().forEach(zipEntry -> {
                        try {
                            BufferedReader buffer = new BufferedReader(new InputStreamReader(gufFile.getInputStream(zipEntry)));
                            String line;
                            int i = 0;
                            while ((line = buffer.readLine()) != null) {
                                if (line.contains(searchWord)) {
                                    listDibGuf.add(
                                            GufDib.builder()
                                            .guf(file)
                                            .numberString(i)
                                            .trigger(searchWord)
                                            .build()
                                    );
                                }
                                i++;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                updateBar.run();
            }
        }
        countDownLatch.countDown();
    }
}
