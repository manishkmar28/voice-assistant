package com.manish.ai.voiceassistant.service;

import org.springframework.stereotype.Service;

import com.manish.ai.voiceassistant.entity.VoiceCommandLog;
import com.manish.ai.voiceassistant.repository.VoiceCommandRepository;
import com.manish.ai.voiceassistant.util.SystemUtil;

@Service
public class CommandService {

    private final VoiceCommandRepository repository;

    public CommandService(VoiceCommandRepository repository) {
        this.repository = repository;
    }

    public String processCommand(String command) {

        String action = "UNKNOWN";
        String status = "FAILED";

        try {
            if (command == null || command.isBlank()) {
                status = "EMPTY_COMMAND";
                save(command, action, status);
                return "Empty command received";
            }

            command = command.toLowerCase().trim();

            // ================= OPEN COMMANDS =================
            if (command.contains("open notepad")) {
                SystemUtil.openNotepad();
                action = "OPEN_NOTEPAD";
                status = "SUCCESS";
            }
            else if (command.contains("open youtube")) {
                SystemUtil.openYouTube();
                action = "OPEN_YOUTUBE";
                status = "SUCCESS";
            }
            else if (command.contains("open chrome")) {
                SystemUtil.openChrome();
                action = "OPEN_CHROME";
                status = "SUCCESS";
            }
            else if (command.contains("open calculator")) {
                SystemUtil.openCalculator();
                action = "OPEN_CALCULATOR";
                status = "SUCCESS";
            }

            // ================= CLOSE / OFF COMMANDS =================
            else if (command.contains("close notepad") || command.contains("off notepad")) {
                SystemUtil.closeNotepad();
                action = "CLOSE_NOTEPAD";
                status = "SUCCESS";
            }
            else if (command.contains("close youtube") || command.contains("off youtube")) {
                SystemUtil.closeChrome(); // youtube chrome me hota hai
                action = "CLOSE_YOUTUBE";
                status = "SUCCESS";
            }
            else if (command.contains("close chrome") || command.contains("off chrome")) {
                SystemUtil.closeChrome();
                action = "CLOSE_CHROME";
                status = "SUCCESS";
            }
            else if (command.contains("close calculator") || command.contains("off calculator")) {
                SystemUtil.closeCalculator();
                action = "CLOSE_CALCULATOR";
                status = "SUCCESS";
            }

            else {
                action = "NO_MATCH";
                status = "NOT_SUPPORTED";
            }

        } catch (Exception e) {
            action = "EXCEPTION";
            status = "ERROR";
        }

        save(command, action, status);
        return action.replace("_", " ").toLowerCase();
    }

    // ================= DB SAVE =================
    private void save(String command, String action, String status) {
        VoiceCommandLog log = new VoiceCommandLog();
        log.setCommandText(command);
        log.setActionTaken(action);
        log.setStatus(status);
        repository.save(log);
    }
}
