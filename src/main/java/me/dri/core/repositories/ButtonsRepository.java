package me.dri.core.repositories;

import me.dri.core.entities.Button;

import java.util.HashMap;

public class ButtonsRepository implements  IButtonRepository {

    private HashMap<String, Button> buttons = new HashMap<>();


    @Override
    public void saveButton(Button button) {
        this.buttons.put(button.getIdButton(), button);
    }
}
