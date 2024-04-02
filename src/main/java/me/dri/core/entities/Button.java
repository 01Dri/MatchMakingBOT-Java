package me.dri.core.entities;

import java.util.Optional;

public interface Button {

    void setIdButton(String idButton);

    String getIdButton();
    String getCustomId();
    String getLabel();

    void handle();
}
