package com.manish.ai.voiceassistant.repository;

import com.manish.ai.voiceassistant.entity.VoiceCommandLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoiceCommandRepository
        extends JpaRepository<VoiceCommandLog, Long> {

    
}
