package com.lab5.lab5;

import com.lab5.lab5.calculation.CalculationException;
import com.lab5.lab5.calculation.CalculationUtil;
import com.lab5.lab5.calculation.Operation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;

public class CalculatorController {
    @FXML
    private TextField textField;

    private boolean operationExecuted;

    @FXML
    protected void onDigit(ActionEvent e) {
        String str = ((Button)e.getSource()).getText();
        String con = textField.getText() + str;
        if (operationExecuted) {
            operationExecuted = false;
            con = str;
        }
        try {
            CalculationUtil.next(Double.parseDouble(con));
        } catch (NumberFormatException exception) {
            return;
        }
        textField.setText(con);
    }

    @FXML
    public void initialize() {
        textField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
               try {
                   if (keyEvent.getCharacter().equals("d") || keyEvent.getCharacter().equals("f")) {
                       throw new NumberFormatException();
                   }
                   if(operationExecuted) {
                       CalculationUtil.next(Double.parseDouble(keyEvent.getCharacter()));
                       operationExecuted = false;
                       textField.setText(keyEvent.getCharacter());
                   } else {
                       CalculationUtil.next(Double.parseDouble(textField.getText()));
                   }
               } catch (NumberFormatException e) {
                   ((TextInputControl) keyEvent.getSource()).deletePreviousChar();

                   Operation op = Operation.getOperation(keyEvent.getCharacter());
                   if (op != null) {
                       executeOperation(op);
                   }
               }
               textField.positionCaret(textField.getText().length());
            }
        });

        textField.textProperty().addListener(
                (observable,oldValue,newValue)-> {
                    if(newValue.length() > 16) textField.setText(newValue.substring(0,16));
                }
        );
        textField.setText("");
    }

    @FXML
    public void onRemove() {
        String t = textField.getText();
        if (t.length() != 0) {
            textField.setText(t.substring(0, t.length() - 1));
        }
    }

    @FXML
    protected void onDelete(ActionEvent e) {
        textField.setText("");
        CalculationUtil.clear();
    }

    @FXML
    protected void onSign(ActionEvent e) {
        if(textField.getText().length() != 0) {
            if (textField.getText().contains("-")) {
                textField.setText(textField.getText().substring(1));
            } else {
                textField.setText("-" + textField.getText());
            }
            CalculationUtil.next(Double.parseDouble(textField.getText()));
        }
    }

    @FXML
    protected void onOperation(ActionEvent e) {
        String str = ((Button)e.getSource()).getText();
        Operation op = Operation.getOperation(str);
        executeOperation(op);
    }

    private void executeOperation(Operation operation) {
        try {
            Double res = CalculationUtil.next(operation);
            textField.setText(res == null ? "" : res.toString());
            operationExecuted = true;
        } catch (CalculationException ex) {
            textField.setText(ex.getMessage());
        }
    }
}