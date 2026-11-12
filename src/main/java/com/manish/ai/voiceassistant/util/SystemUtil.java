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
            new ProcessBuilder("cmd.exe", "/c", "start", "", "chrome").start();
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

    public static void openWord() {
        launchWindowsApp("winword", "Microsoft Word");
    }

    public static void openExcel() {
        launchWindowsApp("excel", "Microsoft Excel");
    }

    public static void openPowerPoint() {
        launchWindowsApp("powerpnt", "Microsoft PowerPoint");
    }

    public static void openPaint() {
        launchWindowsApp("mspaint", "Paint");
    }

    public static void openEdge() {
        launchWindowsApp("msedge", "Microsoft Edge");
    }

    public static void openFileExplorer() {
        launchWindowsApp("explorer", "File Explorer");
    }

    // ================= CLOSE =================

    public static void closeCalculator() {
        try {
            new ProcessBuilder("taskkill", "/IM", "calc.exe", "/F").start();
        } catch (IOException e) {
            throw new RuntimeException("Calculator close failed", e);
        }
    }

    public static void closeChrome() {
        try {
            new ProcessBuilder("taskkill", "/IM", "chrome.exe", "/F").start();
        } catch (IOException e) {
            throw new RuntimeException("Chrome close failed", e);
        }
    }

    public static void closeNotepad() {
        try {
            new ProcessBuilder("taskkill", "/IM", "notepad.exe", "/F").start();
        } catch (IOException e) {
            throw new RuntimeException("Notepad close failed", e);
        }
    }

    public static void closeWord() {
        closeWindowsApp("WINWORD.EXE", "Microsoft Word");
    }

    public static void closeExcel() {
        closeWindowsApp("EXCEL.EXE", "Microsoft Excel");
    }

    public static void closePowerPoint() {
        closeWindowsApp("POWERPNT.EXE", "Microsoft PowerPoint");
    }

    public static void closePaint() {
        closeWindowsApp("mspaint.exe", "Paint");
    }

    public static void closeEdge() {
        closeWindowsApp("msedge.exe", "Microsoft Edge");
    }

    private static void launchWindowsApp(String appCommand, String appName) {
        try {
            new ProcessBuilder("cmd.exe", "/c", "start", "", appCommand).start();
        } catch (IOException e) {
            throw new RuntimeException(appName + " open failed", e);
        }
    }

    private static void closeWindowsApp(String imageName, String appName) {
        try {
            new ProcessBuilder("taskkill", "/IM", imageName, "/F").start();
        } catch (IOException e) {
            throw new RuntimeException(appName + " close failed", e);
        }
    }
}
