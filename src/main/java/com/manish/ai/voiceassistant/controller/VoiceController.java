package com.manish.ai.voiceassistant.controller;

import com.manish.ai.voiceassistant.service.VoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voice")
public class VoiceController {

	@Autowired
    private  VoiceService voiceService;

//    public VoiceController(VoiceService voiceService) {
//        this.voiceService = voiceService;
//    }

    @GetMapping("/test")
    public String test() {
        return "API OK";
    }

    @GetMapping("/command")
    public String command(@RequestParam String text) {
        return voiceService.execute(text);
    }
}
