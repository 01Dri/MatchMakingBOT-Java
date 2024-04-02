package me.dri.core.entities;

public class ButtonQueues implements  Button {

    private String idButton;
    private String customId;
    private String label;

    public ButtonQueues() {

    }

    public ButtonQueues(String idButton, String customId, String label) {
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
    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void handle() {

    }

    public void setLabel(String label) {
        this.label = label;
    }
}
