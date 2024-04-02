package me.dri.core.services.button;

import me.dri.core.entities.Button;
import me.dri.core.entities.ButtonQueues;

public interface ButtonService {

    void saveButton(Button button);

    ButtonQueues findButtonCustomId(String customID);

    void findAllButtons();
}
