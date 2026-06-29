package com.manish.ai.voiceassistant.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class SystemUtil {

    // ================= OPEN =================

    public static void openYouTube() {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("https://www.youtube.com"));
            } else {
                Runtime.getRuntime().exec("cmd /c start https://www.youtube.com");
            }
        } catch (Exception e) {
            throw new RuntimeException("YouTube open failed", e);
        }
    }

    public static void openChrome() {
        try {
            String chromePath =
                "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";

            Runtime.getRuntime().exec(
                "cmd /c start \"\" \"" + chromePath + "\""
            );
        } catch (IOException e) {
            throw new RuntimeException("Chrome open failed", e);
        }
    }

    public static void openCalculator() {
        try {
            Runtime.getRuntime().exec("calc");
        } catch (IOException e) {
            throw new RuntimeException("Calculator open failed", e);
        }
    }

    public static void openNotepad() {
        try {
            Runtime.getRuntime().exec("notepad");
        } catch (IOException e) {
            throw new RuntimeException("Notepad open failed", e);
        }
    }

    // ================= CLOSE =================

    public static void closeCalculator() {
        try {
            Runtime.getRuntime().exec("taskkill /IM calc.exe /F");
        } catch (IOException e) {
            throw new RuntimeException("Calculator close failed", e);
        }
    }

    public static void closeChrome() {
        try {
            Runtime.getRuntime().exec("taskkill /IM chrome.exe /F");
        } catch (IOException e) {
            throw new RuntimeException("Chrome close failed", e);
        }
    }

    public static void closeNotepad() {
        try {
            Runtime.getRuntime().exec("taskkill /IM notepad.exe /F");
        } catch (IOException e) {
            throw new RuntimeException("Notepad close failed", e);
        }
    }
}
