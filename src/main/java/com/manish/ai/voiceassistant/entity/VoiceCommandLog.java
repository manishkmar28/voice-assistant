package com.manish.ai.voiceassistant.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "voice_command_log")
public class VoiceCommandLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "command_text", length = 200)
    private String commandText;

    @Column(name = "normalized_command", length = 200)
    private String normalizedCommand;

    @Column(name = "action_taken", length = 40)
    private String actionTaken;

    @Column(length = 30)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ====== MANUAL GETTERS & SETTERS ======

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public void setNormalizedCommand(String normalizedCommand) {
        this.normalizedCommand = normalizedCommand;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
