package com.example.renameguf.View.Impl.DibCheckView;

import com.example.renameguf.Model.GufDib;
import com.example.renameguf.View.DibCheckView;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class DibCheckViewImpl extends JFrame implements DibCheckView {

    public DibCheckViewImpl (){
        setSize(1200, 1200);
    }

    @Override
    public void showFiles(List<GufDib> fileList) {

        List<GufDib> sortListGuf = new ArrayList<>(fileList);

        sortListGuf.sort(new Comparator<GufDib>() {
            @Override
            public int compare(GufDib o1, GufDib o2) {
                int i = o1.getGuf().getAbsolutePath().compareTo(o2.getGuf().getAbsolutePath());
                if (i == 0) {
                    i = Integer.compare(o1.getNumberString(), o2.getNumberString());
                }
                return i;
            }
        });



        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
            tableModel.addColumn("Гуф");
            tableModel.addColumn("Строка");
            tableModel.addColumn("Что нашли");

        for (GufDib file : sortListGuf){
            Object[] data = new Object[3];
                data[0] = file.getGuf();
                data[1] = file.getNumberString();
                data[2] = file.getTrigger();
            tableModel.addRow(data);
        }
        JTable jTable = new JTable(tableModel);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(400);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setMouseListener(jTable);
        getContentPane().add(new JScrollPane(jTable));
        pack();
        setVisible(true);

    }

    private void setMouseListener (JTable jTable){
        jTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = jTable.getSelectedRow();
                    Object[] rowData = new Object[jTable.getColumnCount()];
                    for (int i = 0; i < rowData.length; i++) {
                        rowData[i] = jTable.getValueAt(row, i);
                    }
                    File selectedFile = (File)rowData[0];
                    try {
                        Desktop.getDesktop().open(selectedFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }

}
