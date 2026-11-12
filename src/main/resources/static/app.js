const form = document.querySelector("#command-form");
const input = document.querySelector("#command-input");
const status = document.querySelector("#input-status");
const caption = document.querySelector("#assistant-caption");
const micButton = document.querySelector("#mic-button");
const connectionDot = document.querySelector("#connection-dot");
const connectionLabel = document.querySelector("#connection-label");

function showStatus(message, isError = false) {
  status.textContent = message;
  status.classList.toggle("error", isError);
  caption.textContent = message;
}

async function sendCommand(command) {
  const text = command.trim();
  if (!text) {
    input.focus();
    return;
  }

  input.value = text;
  showStatus("Sending command…");
  document.querySelectorAll(".action-card, .send-button, .mic-button").forEach((button) => {
    button.disabled = true;
  });

  try {
    const response = await fetch("/voice/command", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ text })
    });
    const reply = (await response.text()).trim();
    if (!response.ok) {
      throw new Error(reply || `Request failed (${response.status})`);
    }
    showStatus(reply || "Command complete");
    connectionDot.classList.remove("offline");
    connectionLabel.textContent = "Assistant connected";
  } catch (error) {
    showStatus(error.message || "Could not reach the assistant", true);
    connectionDot.classList.add("offline");
    connectionLabel.textContent = "Assistant unavailable";
  } finally {
    document.querySelectorAll(".action-card, .send-button, .mic-button").forEach((button) => {
      button.disabled = false;
    });
  }
}

form.addEventListener("submit", (event) => {
  event.preventDefault();
  sendCommand(input.value);
});

document.querySelectorAll(".action-card").forEach((button) => {
  button.addEventListener("click", () => sendCommand(button.dataset.command));
});

const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
if (!SpeechRecognition) {
  micButton.title = "Voice input is not supported by this browser";
  micButton.setAttribute("aria-label", "Voice input is not supported by this browser");
  micButton.addEventListener("click", () => showStatus("Voice input works in Chrome or Edge; you can type a command instead.", true));
} else {
  const recognition = new SpeechRecognition();
  recognition.lang = "en-IN";
  recognition.interimResults = false;
  recognition.maxAlternatives = 1;
  let listening = false;

  micButton.addEventListener("click", () => {
    if (listening) {
      recognition.stop();
      return;
    }
    try {
      recognition.start();
    } catch {
      showStatus("Voice input is already starting. Try again in a moment.", true);
    }
  });

  recognition.onstart = () => {
    listening = true;
    micButton.classList.add("listening");
    micButton.setAttribute("aria-label", "Stop voice input");
    showStatus("Listening… say a command");
  };

  recognition.onresult = (event) => {
    const spokenText = event.results[0][0].transcript.trim();
    input.value = spokenText;
    sendCommand(spokenText);
  };

  recognition.onerror = (event) => {
    const message = event.error === "not-allowed"
      ? "Allow microphone access in your browser to use voice input."
      : "I couldn’t hear that. Try again or type a command.";
    showStatus(message, true);
  };

  recognition.onend = () => {
    listening = false;
    micButton.classList.remove("listening");
    micButton.setAttribute("aria-label", "Start voice input");
  };
}

fetch("/voice/test")
  .then((response) => {
    if (!response.ok) throw new Error("offline");
    connectionDot.classList.remove("offline");
    connectionLabel.textContent = "Assistant connected";
  })
  .catch(() => {
    connectionDot.classList.add("offline");
    connectionLabel.textContent = "Assistant unavailable";
  });

document.querySelector("#today-label").textContent = new Intl.DateTimeFormat(undefined, {
  weekday: "short", month: "short", day: "numeric"
}).format(new Date());
