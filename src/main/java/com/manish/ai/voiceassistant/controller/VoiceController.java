package com.manish.ai.voiceassistant.controller;

import com.manish.ai.voiceassistant.service.CommandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voice")
public class VoiceController {

    private final CommandService commandService;

    public VoiceController(CommandService commandService) {
        this.commandService = commandService;
    }

    @GetMapping("/test")
    public String test() {
        return "API OK";
    }

    @PostMapping("/command")
    public String command(@Valid @RequestBody CommandRequest request) {
        return commandService.processCommand(request.text());
    }

    public record CommandRequest(@NotBlank @Size(max = 200) String text) { }
}
