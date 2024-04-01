package me.dri.core.entities;

import java.util.Optional;

public interface Button {

    void setIdButton(String idButton);

    String getIdButton();
    Optional<String> getCustomId();
    Optional<String> getLabel();

    void handle();
}
