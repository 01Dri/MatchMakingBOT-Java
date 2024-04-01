package me.dri.core.entities;

import java.util.Optional;

public class ButtonQueues implements  Button {

    private String idButton;
    private Optional<String> customId;
    private Optional<String> label;

    public ButtonQueues() {

    }

    public ButtonQueues(String idButton, Optional<String> customId, Optional<String> label) {
        this.idButton = idButton;
        this.customId = customId;
        this.label = label;
    }

    @Override
    public String getIdButton() {
        return idButton;
    }

    @Override
    public void setIdButton(String idButton) {
        this.idButton = idButton;
    }

    @Override
    public Optional<String> getCustomId() {
        return customId;
    }

    public void setCustomId(Optional<String> customId) {
        this.customId = customId;
    }

    @Override
    public Optional<String> getLabel() {
        return label;
    }

    @Override
    public void handle() {
    }

    public void setLabel(Optional<String> label) {
        this.label = label;
    }
}
