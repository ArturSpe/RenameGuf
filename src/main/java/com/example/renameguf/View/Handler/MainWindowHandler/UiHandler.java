package com.example.renameguf.View.Handler.MainWindowHandler;

import com.example.renameguf.Controllers.MainWindowController;
import com.example.renameguf.Model.FieldsGuf;
import com.example.renameguf.Model.GufDib;
import com.example.renameguf.View.Component.InputFieldsComponent;
import com.example.renameguf.View.DibCheckView;
import com.example.renameguf.View.Impl.Main.ProgressBarFrame;
import com.example.renameguf.View.MainWindow;
import com.example.renameguf.View.ProgressFrame;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Component
public class UiHandler {

    private final ProgressFrame progressFrame;
    private final MainWindowController mainWindowController;
    private final MainWindow<String> mainWindow;
    private final DibCheckView dibCheckView;
    private final ExecutorService executorService;

    public UiHandler(MainWindowController mainWindowController,
                     MainWindow<String> mainWindow,
                     DibCheckView dibCheckView,
                     @Qualifier("ServiceTask") ExecutorService executorService){
        this.progressFrame = new ProgressBarFrame(new Runnable() {
            @Override
            public void run() {
                closeProcessBar();
            }
        });

        this.mainWindow = mainWindow;
        this.mainWindowController = mainWindowController;
        this.dibCheckView = dibCheckView;
        this.executorService = executorService;
    }

    @EventListener(Event.ClickOnRenameEvent.class)
    public void clickOnRenameEvent() {
        Map<InputFieldsComponent, String> inputFieldsComponentStringMap = mainWindow.getValueFields();
        FieldsGuf fieldsGuf = FieldsGuf.builder()
                .pathToFolder(inputFieldsComponentStringMap.get(InputFieldsComponent.PathToFolder))
                .identCommand(inputFieldsComponentStringMap.get(InputFieldsComponent.CommandIdent))
                .numberTaskJira(inputFieldsComponentStringMap.get(InputFieldsComponent.NumberTaskJira))
                .gufVersion(inputFieldsComponentStringMap.get(InputFieldsComponent.GufVersion))
                .build();
        mainWindowController.renameGuf(fieldsGuf);
    }

    @EventListener(Event.ClickOnDIBEvent.class)
    public void clickOnDIBEvent(){
        executorService.submit(() -> {
            FieldsGuf fieldsGuf = FieldsGuf.builder()
                    .createProgress((progressFrame::createBar))
                    .updateBar(progressFrame::updateBar)
                    .pathToFolder(mainWindow.getValueFields().get(InputFieldsComponent.PathToFolder))
                    .loginUser(mainWindow.getValueFields().get(InputFieldsComponent.LoginUserCheck))
                    .build();
            progressFrame.closeBar();
            showResult(mainWindowController.checkDIB(fieldsGuf));
        });

    }
    @EventListener(Event.ClickOnEmailEvent.class)
    public void clickOnEmailEvent() {
        mainWindow.blockButton();
        executorService.submit(() -> {
            try {
                showResult(mainWindowController.checkEmail(FieldsGuf.builder()
                        .createProgress((progressFrame::createBar))
                        .updateBar(progressFrame::updateBar)
                        .pathToFolder(mainWindow.getValueFields().get(InputFieldsComponent.PathToFolder))
                        .build()));
                progressFrame.closeBar();
            } catch (IOException | URISyntaxException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @EventListener(Event.ClickOnLoginsEvent.class)
    public void clickOnLoginsEvent() {

        mainWindow.blockButton();
        executorService.submit(() -> {
            try {
                showResult(mainWindowController.checkLogins(FieldsGuf.builder()
                        .createProgress((progressFrame::createBar))
                        .updateBar(progressFrame::updateBar)
                        .pathToFolder(mainWindow.getValueFields().get(InputFieldsComponent.PathToFolder))
                        .build()));
                progressFrame.closeBar();
            } catch (IOException | URISyntaxException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @EventListener(Event.ClickOnTokenButton.class)
    public void clickOnTokensButton() {
        System.out.println(1);
        mainWindow.blockButton();
        executorService.submit(() -> {
            try {
                showResult(mainWindowController.checkTokens(FieldsGuf.builder()
                        .createProgress((progressFrame::createBar))
                        .updateBar(progressFrame::updateBar)
                        .pathToFolder(mainWindow.getValueFields().get(InputFieldsComponent.PathToFolder))
                        .build()));
                progressFrame.closeBar();
            } catch (IOException | URISyntaxException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @EventListener(Event.ClickOnMissButton.class)
    public void clickOnMissButton() {
        mainWindow.blockButton();
        executorService.submit(() -> {
            try {
                showResult(mainWindowController.checkOrder(FieldsGuf.builder()
                        .createProgress((progressFrame::createBar))
                        .updateBar(progressFrame::updateBar)
                        .pathToFolder(mainWindow.getValueFields().get(InputFieldsComponent.PathToFolder))
                        .build()));
                progressFrame.closeBar();
            } catch (IOException | URISyntaxException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @EventListener(Event.ClickOnEgorButton.class)
    public void clickOnEgorButton() throws URISyntaxException, IOException {
        mainWindow.blockButton();
        mainWindowController.createCheckList(FieldsGuf.builder()
                .createProgress((progressFrame::createBar))
                .updateBar(progressFrame::updateBar)
                .pathToFolder(mainWindow.getValueFields().get(InputFieldsComponent.PathToFolder))
                .build());
        mainWindow.unlockButton();

    }

    public void closeProcessBar() {
        mainWindow.unlockButton();
        progressFrame.closeBar();
        executorService.shutdownNow();
    }

    private void showResult (List<GufDib> gufDibList){
        dibCheckView.showFiles(gufDibList);
        mainWindow.unlockButton();
    }

}
