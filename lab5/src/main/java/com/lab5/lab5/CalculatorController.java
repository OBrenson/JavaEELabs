package com.lab5.lab5;

import com.lab5.lab5.calculation.CalculationException;
import com.lab5.lab5.calculation.CalculationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController {
    @FXML
    private TextField textField;

    @FXML
    protected void onDigit(ActionEvent e) {
        String str = ((Button)e.getSource()).getText();
        String preparedRes;
        try {
            Double res = CalculationUtil.next(Double.valueOf(str));
            preparedRes = res.toString();
        } catch (CalculationException exception) {
            preparedRes = exception.getMessage();
        }
        textField.setText(preparedRes);
    }

    @FXML
    protected void onOperation(ActionEvent e) {
        String str = ((Button)e.getSource()).getText();
    }
}