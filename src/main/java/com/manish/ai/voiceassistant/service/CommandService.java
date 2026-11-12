package com.manish.ai.voiceassistant.service;

import org.springframework.stereotype.Service;

import com.manish.ai.voiceassistant.entity.VoiceCommandLog;
import com.manish.ai.voiceassistant.repository.VoiceCommandRepository;
import com.manish.ai.voiceassistant.util.SystemUtil;
import java.util.Locale;

@Service
public class CommandService {

    private final VoiceCommandRepository repository;

    public CommandService(VoiceCommandRepository repository) {
        this.repository = repository;
    }

    public String processCommand(String command) {

        String originalCommand = command;
        String normalizedCommand = null;
        String action = "UNKNOWN";
        String status = "FAILED";

        try {
            if (command == null || command.isBlank()) {
                status = "EMPTY_COMMAND";
                save(originalCommand, "", action, status);
                return "Empty command received";
            }

            normalizedCommand = command.trim().toLowerCase(Locale.ROOT);
            command = normalizedCommand;

            switch (command) {
                case "open notepad" -> { SystemUtil.openNotepad(); action = "OPEN_NOTEPAD"; }
                case "open youtube" -> { SystemUtil.openYouTube(); action = "OPEN_YOUTUBE"; }
                case "open chrome" -> { SystemUtil.openChrome(); action = "OPEN_CHROME"; }
                case "open calculator" -> { SystemUtil.openCalculator(); action = "OPEN_CALCULATOR"; }

                case "open word", "open ms word", "open microsoft word" -> {
                    SystemUtil.openWord(); action = "OPEN_WORD";
                }
                case "open excel", "open microsoft excel" -> {
                    SystemUtil.openExcel(); action = "OPEN_EXCEL";
                }
                case "open powerpoint", "open power point", "open microsoft powerpoint", "open ppt" -> {
                    SystemUtil.openPowerPoint(); action = "OPEN_POWERPOINT";
                }
                case "open paint", "open microsoft paint" -> {
                    SystemUtil.openPaint(); action = "OPEN_PAINT";
                }
                case "open edge", "open microsoft edge" -> {
                    SystemUtil.openEdge(); action = "OPEN_EDGE";
                }
                case "open file explorer", "open explorer" -> {
                    SystemUtil.openFileExplorer(); action = "OPEN_FILE_EXPLORER";
                }

                case "close notepad", "off notepad" -> { SystemUtil.closeNotepad(); action = "CLOSE_NOTEPAD"; }
                case "close youtube", "off youtube" -> { SystemUtil.closeChrome(); action = "CLOSE_YOUTUBE"; }
                case "close chrome", "off chrome" -> { SystemUtil.closeChrome(); action = "CLOSE_CHROME"; }
                case "close calculator", "off calculator" -> { SystemUtil.closeCalculator(); action = "CLOSE_CALCULATOR"; }
                case "close word", "close ms word", "close microsoft word" -> {
                    SystemUtil.closeWord(); action = "CLOSE_WORD";
                }
                case "close excel", "close microsoft excel" -> {
                    SystemUtil.closeExcel(); action = "CLOSE_EXCEL";
                }
                case "close powerpoint", "close power point", "close microsoft powerpoint", "close ppt" -> {
                    SystemUtil.closePowerPoint(); action = "CLOSE_POWERPOINT";
                }
                case "close paint", "close microsoft paint" -> {
                    SystemUtil.closePaint(); action = "CLOSE_PAINT";
                }
                case "close edge", "close microsoft edge" -> {
                    SystemUtil.closeEdge(); action = "CLOSE_EDGE";
                }

                default -> { action = "NO_MATCH"; status = "NOT_SUPPORTED"; }
            }
            if (!action.equals("NO_MATCH")) {
                status = "SUCCESS";
            }

        } catch (Exception e) {
            action = "EXCEPTION";
            status = "ERROR";
        }

        save(originalCommand, normalizedCommand, action, status);
        return action.replace("_", " ").toLowerCase();
    }

    // ================= DB SAVE =================
    private void save(String originalCommand, String normalizedCommand, String action, String status) {
        VoiceCommandLog log = new VoiceCommandLog();
        log.setCommandText(originalCommand);
        log.setNormalizedCommand(normalizedCommand);
        log.setActionTaken(action);
        log.setStatus(status);
        repository.save(log);
    }
}
