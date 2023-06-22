package com.example.renameguf.View.Impl.Main;

import com.example.renameguf.View.Component.InputFieldsComponent;
import com.example.renameguf.View.PanelWithFields;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("Fields")
public class FieldsPanelImpl extends JPanel implements PanelWithFields {

    DocumentListener listenerFields = new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {
            updatePreview();
        }
        public void insertUpdate(DocumentEvent e) {
            updatePreview();
        }
        public void removeUpdate(DocumentEvent e) {
            updatePreview();
        }
        void updatePreview() {
            inputFieldsJTextFieldMap.get(InputFieldsComponent.Preview).setText("NNNNNN_" +
                    inputFieldsJTextFieldMap.get(InputFieldsComponent.CommandIdent).getText() + "_" +
                            inputFieldsJTextFieldMap.get(InputFieldsComponent.NumberTaskJira).getText() + "_v" +
                            inputFieldsJTextFieldMap.get(InputFieldsComponent.GufVersion).getText() +
                    "_YYYY_MM_DD_HH_mm_SS_Название");
        }
    };

    Map<InputFieldsComponent, JTextField> inputFieldsJTextFieldMap;

    public FieldsPanelImpl(){
        setLayout(new GridLayout(5, 2, 10, 10));
        inputFieldsJTextFieldMap = new HashMap<>();
        List<InputFieldsComponent> inputFieldsList = new ArrayList<>();
        inputFieldsList.add(InputFieldsComponent.CommandIdent);
        inputFieldsList.add(InputFieldsComponent.NumberTaskJira);
        inputFieldsList.add(InputFieldsComponent.GufVersion);
        inputFieldsList.add(InputFieldsComponent.Preview);
        inputFieldsList.add(InputFieldsComponent.LoginUserCheck);
        createLabelField(inputFieldsList);

    }
    @Override
    public Map<InputFieldsComponent, String> getFields() {
        Map<InputFieldsComponent, String> inputFieldsStringMap = new HashMap<>();
        for (Map.Entry<InputFieldsComponent, JTextField> entry : inputFieldsJTextFieldMap.entrySet()){
            inputFieldsStringMap.put(entry.getKey(), entry.getValue().getText());
        }
        return inputFieldsStringMap;
    }

    @Override
    public void clearField() {
        inputFieldsJTextFieldMap.forEach((key, value) -> value.setText(""));
    }

    private void createLabelField (List<InputFieldsComponent> inputFieldsList){
        for (InputFieldsComponent label : inputFieldsList){
            JTextField jTextField = new JTextField();
            if (label != InputFieldsComponent.Preview && label != InputFieldsComponent.PathToFolder) {
                jTextField.getDocument().addDocumentListener(listenerFields);
            }
            inputFieldsJTextFieldMap.put(label, jTextField);
            add(new JLabel(label.getValue()));
            add(jTextField);
        }
    }

}
