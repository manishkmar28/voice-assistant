import speech_recognition as sr
import requests
import pyttsx3
import sys
import time

for stream in (sys.stdout, sys.stderr):
    if hasattr(stream, "reconfigure"):
        stream.reconfigure(encoding="utf-8", errors="replace")

# ===============================
# CONFIG
# ===============================
API_URL = "http://localhost:8080/voice/command"

# 👉 MULTI WAKE-UP LANGUAGE (Natural)
WAKE_WORDS = [
    "hey friday",
    "hello friday",
    "hi friday",
    "friday",
    "sun friday",
    "are friday",
    "hey fri day",
    "hey freddy"
]

# ===============================
# TEXT TO SPEECH (WINDOWS)
# ===============================
engine = pyttsx3.init("sapi5")
voices = engine.getProperty("voices")

engine.setProperty("voice", voices[0].id)   # male voice
engine.setProperty("rate", 165)
engine.setProperty("volume", 1.0)

def speak(text):
    print("🔊 AI बोल रहा है:", text)
    engine.stop()
    engine.say(text)
    engine.runAndWait()

# ===============================
# SPEECH RECOGNITION
# ===============================
recognizer = sr.Recognizer()
mic = sr.Microphone()
http = requests.Session()
last_command = None
last_command_at = 0.0
DUPLICATE_COMMAND_WINDOW = 4.0

# Calibrate once at startup; doing this on every loop adds latency.
with mic as source:
    recognizer.adjust_for_ambient_noise(source, duration=0.5)

print("🎙️ Friday AI Assistant started")
speak("मैं तैयार हूँ, बोलिए Friday")

# ===============================
# MAIN LOOP
# ===============================
while True:
    try:
        with mic as source:
            print("\n🎧 Listening...")
            audio = recognizer.listen(source, timeout=1, phrase_time_limit=6)

        # Hindi + English mixed
        text = recognizer.recognize_google(audio, language="en-IN")
        print("🗣️ You said:", text)

        text_lower = text.lower()

        # ===============================
        # WAKE-UP LANGUAGE DETECTION
        # ===============================
        wake_detected = False
        command = ""

        for word in sorted(WAKE_WORDS, key=len, reverse=True):
            if word in text_lower:
                wake_detected = True
                command = text_lower.replace(word, "").strip()
                break

        if not wake_detected:
            print("⏭️ Wake language not detected")
            continue

        # ===============================
        # NO COMMAND AFTER WAKE WORD
        # ===============================
        if command == "":
            speak("हाँ Friday, बताइए क्या करना है")
            continue

        normalized_command = " ".join(command.lower().split()).strip(" .,!?;:")
        now = time.monotonic()
        if normalized_command == last_command and now - last_command_at < DUPLICATE_COMMAND_WINDOW:
            print("⏭️ Duplicate command ignored")
            continue

        print("📤 Command:", command)
        last_command = normalized_command
        last_command_at = now

        # ===============================
        # SEND TO SPRING BOOT
        # ===============================
        response = http.post(
            API_URL,
            json={"text": command},
            timeout=5
        )
        response.raise_for_status()

        reply = response.text
        print("🤖 AI Response:", reply)

        speak(reply)

    except sr.UnknownValueError:
        print("⏭️ No clear speech detected")

    except sr.WaitTimeoutError:
        continue

    except requests.exceptions.ConnectionError:
        speak("सर्वर चालू नहीं है")

    except KeyboardInterrupt:
        speak("ठीक है, बंद हो रहा हूँ")
        print("\n🛑 Assistant stopped")
        break

    except Exception as e:
        print("❌ Error:", e)
        speak("कुछ तकनीकी समस्या आ गई है")
