package it.polimi.ingsw.view.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class TestScene {
    public Button button;
    public Label label;
    public TextField textField;
    public void initialize()
    {
        label.setText("");
    }
    public void buttonClicked(ActionEvent actionEvent)
    {
        label.setText(textField.getText());
    }
}
