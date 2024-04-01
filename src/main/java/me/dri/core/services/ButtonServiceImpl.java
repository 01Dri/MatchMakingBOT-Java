package me.dri.core.services;

import me.dri.core.entities.Button;
import me.dri.core.repositories.IButtonRepository;

public class ButtonServiceImpl implements  ButtonService{

    private final IButtonRepository buttonRepository;

    public ButtonServiceImpl(IButtonRepository buttonRepository) {
        this.buttonRepository = buttonRepository;
    }

    @Override
    public void saveButton(Button button) {

    }
}
