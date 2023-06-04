package com.example.renameguf.View.Impl.DibCheckView;

import com.example.renameguf.View.DibCheckView;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class DibCheckViewImpl extends JFrame implements DibCheckView {

    public DibCheckViewImpl (){
        setSize(800, 600);
    }

    @Override
    public void showFiles(List<File> fileList) {
        DefaultListModel<File> fileDefaultListModel = new DefaultListModel<>();
        for (File file : fileList){
            fileDefaultListModel.addElement(file);
        }
        JList<File> fileJList = new JList<>(fileDefaultListModel);
        fileJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setMouseListener(fileJList);
        getContentPane().add(new JScrollPane(fileJList));
        pack();
        setVisible(true);

    }

    private void setMouseListener (JList<File> list){
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    File selectedLink = list.getSelectedValue();
                    if (selectedLink != null) {
                        try {
                            Desktop.getDesktop().browse(selectedLink.toURI());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }

}
