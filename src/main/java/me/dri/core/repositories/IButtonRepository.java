package me.dri.core.repositories;

import me.dri.core.entities.Button;
import me.dri.core.entities.ButtonQueues;

public interface IButtonRepository {

    void saveButton(Button button);

    Button findButton(String customId);

    void findAllButton();

}
