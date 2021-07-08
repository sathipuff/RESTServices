package com.sathi.pi.model;


public class RemoteControlRequest {
    private String command;
    private String component;

    public RemoteControlRequest(){}

    public RemoteControlRequest(String command, String component) {
        this.command = command;
        this.component = component;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
