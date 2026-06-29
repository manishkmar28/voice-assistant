package com.manish.ai.voiceassistant.service;

import org.springframework.stereotype.Service;

@Service
public class VoiceService {

    public String execute(String command) {
        try {
            command = command.toLowerCase().trim();

            if (command.contains("notepad")) {
                Runtime.getRuntime().exec("notepad");
                return "Notepad opened";
            }
            if (command.contains("chrome")) {
                Runtime.getRuntime().exec("cmd /c start chrome");
                return "Chrome opened";
            }
            if (command.contains("youtube")) {
                Runtime.getRuntime().exec("cmd /c start https://www.youtube.com");
                return "YouTube opened";
            }
            if (command.contains("calculator")) {
                Runtime.getRuntime().exec("calc");
                return "Calculator opened";
            }

            return "Command not supported";

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
