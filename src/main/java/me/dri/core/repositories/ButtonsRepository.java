package me.dri.core.repositories;

import me.dri.core.entities.Button;

import java.util.HashMap;

public class ButtonsRepository implements  IButtonRepository {

    private HashMap<String, Button> buttons = new HashMap<>();


    @Override
    public void saveButton(Button button) {
        this.buttons.put(button.getIdButton(), button);
    }

    @Override
    public Button findButton(String customId) {
        for (Button button : buttons.values()) {
            System.out.println(button.getCustomId());
            if (button.getIdButton().equals(customId)) {
                return button;
            }
        }
        return null;
    }

    @Override
    public void findAllButton() {
        this.buttons.values().forEach(b -> System.out.println(b.getCustomId()));
    }
}
