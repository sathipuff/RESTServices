package com.sathi.pi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class BashCommandExecutor {
    private static final Logger log = LoggerFactory.getLogger(BashCommandExecutor.class);
    @Value("${remotectrl.python.alias}")
    private String pythonAlias;

    @Value("${remotectrl.python.script.path}")
    private String remoteControlScriptPath;

    @PostConstruct
    public void init(){
        log.info("Using Python alias : {}", pythonAlias);
        log.info("Using RemoteControl script at : {}", remoteControlScriptPath);
    }

    public int executePython3Command(String component, String cmd){
        int exit = 0;
        try {
            Process pb = new ProcessBuilder(pythonAlias, remoteControlScriptPath, component, cmd).start();
            exit =  pb.waitFor();
        } catch (IOException | InterruptedException e) {
            log.error("Error executing process ", e);
        }
            log.info("Exit code : {}", exit);
        return exit;
    }
}
