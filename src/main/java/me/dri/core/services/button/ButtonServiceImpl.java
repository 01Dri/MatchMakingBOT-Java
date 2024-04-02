package me.dri.core.services.button;

import me.dri.core.entities.Button;
import me.dri.core.entities.ButtonQueues;
import me.dri.core.repositories.IButtonRepository;

public class ButtonServiceImpl implements  ButtonService{

    private final IButtonRepository buttonRepository;

    public ButtonServiceImpl(IButtonRepository buttonRepository) {
        this.buttonRepository = buttonRepository;
    }

    @Override
    public void saveButton(Button button) {
        this.buttonRepository.saveButton(button);

    }

    @Override
    public ButtonQueues findButtonCustomId(String customID) {
        Button button = this.buttonRepository.findButton(customID);
        return new ButtonQueues(button.getIdButton(), button.getCustomId(), button.getLabel());
    }

    @Override
    public void findAllButtons() {
        this.buttonRepository.findAllButton();
    }
}
