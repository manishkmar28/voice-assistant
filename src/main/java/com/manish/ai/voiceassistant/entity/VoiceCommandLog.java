package com.manish.ai.voiceassistant.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "voice_command_log")
public class VoiceCommandLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "command_text")
    private String commandText;

    @Column(name = "action_taken")
    private String actionTaken;

    private String status;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ====== MANUAL GETTERS & SETTERS ======

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
