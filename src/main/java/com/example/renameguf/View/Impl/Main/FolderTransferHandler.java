package com.example.renameguf.View.Impl.Main;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.Serial;

@Component
public class FolderTransferHandler extends TransferHandler {
    private FolderPanelImpl folderPanel;
    private String pathToFolder = "";

    @Serial
    private static final long serialVersionUID = 1L;

    void setFolderPanel(FolderPanelImpl folderPanel){
        this.folderPanel = folderPanel;
    }

    public boolean canImport(TransferHandler.TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        Transferable transferable = support.getTransferable();
        try {
            pathToFolder = transferable.getTransferData(DataFlavor.javaFileListFlavor).toString();
            pathToFolder = pathToFolder.substring(1, pathToFolder.length()-1);
            folderPanel.setText(pathToFolder);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }
    public String getPathToFolder(){
        return pathToFolder;
    }
}
