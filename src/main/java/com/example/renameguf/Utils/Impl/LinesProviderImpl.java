package com.example.renameguf.Utils.Impl;

import com.example.renameguf.Utils.LinesProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Component
public class LinesProviderImpl implements LinesProvider {

    ClassLoader classLoader = LinesProviderImpl.class.getClassLoader();

    @Override
    public List<String> getLinesFromTxt(String name) {
        List<String> listDomain = null;

        try {
            listDomain = Files.lines(Paths.get(Objects.requireNonNull(classLoader.getResource(name)).toURI())).toList();
        }catch (URISyntaxException | IOException exception){
            exception.printStackTrace();
        }

//        File jarFile = new File(GufHandlerImpl.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//        File domainsFile = new File(jarFile.getParentFile().getAbsolutePath() + File.separator + name);
//        try (BufferedReader reader = new BufferedReader(new FileReader(domainsFile))) {
//            listDomain = reader.lines().collect(Collectors.toList());
//        } catch (IOException e) {
//            e.printStackTrace();
//            listDomain = new ArrayList<>();
//        }

        return listDomain;
    }
}
