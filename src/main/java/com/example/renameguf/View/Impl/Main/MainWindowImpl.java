package com.example.renameguf.View.Impl.Main;

import com.example.renameguf.View.*;
import com.example.renameguf.View.Component.InputFieldsComponent;
import com.example.renameguf.View.Component.PanelComponent;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MainWindowImpl extends JFrame implements MainWindow<String> {

    private final ApplicationContext applicationContext;
    private final JPanel mainPanel;
    private final Map<PanelComponent, PanelWithFields> componentMap;

    public MainWindowImpl(JPanel jPanel, ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
        this.mainPanel = jPanel;

        setTitle("Гуф");
        setIconImage(new ImageIcon("src/main/resources/Icon/Guf.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 500);
        setLocationRelativeTo(null);
        componentMap = creatingComponentMap(applicationContext.getBeansOfType(PanelWithFields.class));
        setContentPane(mainPanel);
        setPanels(applicationContext.getBeansOfType(java.awt.Component.class));

    }

    @Override
    public Map<InputFieldsComponent, String> getValueFields() {

        return componentMap
                .values()
                .stream()
                .flatMap(fc -> fc.getFields().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

    }

    @Override
    public void clearField() {
        componentMap.forEach((key, value) -> value.clearField());
    }

    private void setPanels(Map<String, java.awt.Component> panelMap) {
        for (Map.Entry<String, java.awt.Component> panel : panelMap.entrySet()) {
            if (panel.getValue() instanceof GufRenamePanel) {
                mainPanel.add(panel.getValue(),
                        PanelComponent.getLocation(PanelComponent.valueOf(panel.getKey())));
            }
        }
    }

    private Map<PanelComponent, PanelWithFields> creatingComponentMap(Map<String, PanelWithFields> panelMap) {
        Map<PanelComponent, PanelWithFields> fieldsMap = new HashMap<>();
        for (Map.Entry<String, PanelWithFields> panel : panelMap.entrySet()) {
            if (panel.getValue() != null) {
                fieldsMap.put(PanelComponent.valueOf(panel.getKey()), panel.getValue());
            }
        }
        return fieldsMap;
    }

    @Override
    public void blockButton() {
        applicationContext.getBean("Button", ButtonPanelImpl.class).lockButtons();
    }
    @Override
    public void unlockButton() {
        applicationContext.getBean("Button", ButtonPanelImpl.class).unlockButtons();
    }

    @PostConstruct
    public void Start () {
        setVisible(true);
    }
}
