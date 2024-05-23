package com.croft.mailerbackend.models;

public class ModelRequest {
    String inputs;

    public ModelRequest(){}

    public ModelRequest(String inputs) {
        this.inputs = inputs;
    }

    public String getInputs() {
        return inputs;
    }

    public void setInputs(String inputs) {
        this.inputs = inputs;
    }
}
