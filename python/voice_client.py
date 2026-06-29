import speech_recognition as sr
import requests
import pyttsx3
import time

# ===============================
# CONFIG
# ===============================
API_URL = "http://localhost:8080/voice/command"

# 👉 MULTI WAKE-UP LANGUAGE (Natural)
WAKE_WORDS = [
    "hey raj",
    "hello raj",
    "hi raj",
    "raj",
    "sun raj",
    "are raj",
    "hey raaj",
    "hey rage"
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

print("🎙️ Raj AI Assistant started")
speak("मैं तैयार हूँ, बोलिए राज")

# ===============================
# MAIN LOOP
# ===============================
while True:
    try:
        with mic as source:
            recognizer.adjust_for_ambient_noise(source, duration=0.5)
            print("\n🎧 Listening...")
            audio = recognizer.listen(source)

        # Hindi + English mixed
        text = recognizer.recognize_google(audio, language="en-IN")
        print("🗣️ You said:", text)

        text_lower = text.lower()

        # ===============================
        # WAKE-UP LANGUAGE DETECTION
        # ===============================
        wake_detected = False
        command = ""

        for word in WAKE_WORDS:
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
            speak("हाँ राज, बताइए क्या करना है")
            continue

        print("📤 Command:", command)

        # ===============================
        # SEND TO SPRING BOOT
        # ===============================
        response = requests.get(
            API_URL,
            params={"text": command},
            timeout=5
        )

        reply = response.text
        print("🤖 AI Response:", reply)

        speak(reply)
        time.sleep(0.4)

    except sr.UnknownValueError:
        speak("माफ़ कीजिए, मैं समझ नहीं पाया")

    except requests.exceptions.ConnectionError:
        speak("सर्वर चालू नहीं है")

    except KeyboardInterrupt:
        speak("ठीक है, बंद हो रहा हूँ")
        print("\n🛑 Assistant stopped")
        break

    except Exception as e:
        print("❌ Error:", e)
        speak("कुछ तकनीकी समस्या आ गई है")
