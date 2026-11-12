# EchoMind AI Voice Assistant

EchoMind is a Windows desktop voice assistant with a Spring Boot service, a Python microphone client, and a browser dashboard. It can open and close a fixed set of desktop apps and open YouTube.

## Requirements

- Windows 10 or 11
- Java 21
- Python 3.10 or newer
- MySQL 8 or newer
- A working microphone and internet access for Google speech recognition

## Setup

1. Create the database in MySQL:

   ```sql
   CREATE DATABASE voice_ai;
   ```

2. In PowerShell, set the database credentials for the current terminal. The default username is `root`; set a password instead of storing it in this repository:

   ```powershell
   $env:DB_USERNAME = "root"
   $env:DB_PASSWORD = "your-local-mysql-password"
   ```

   To use another database, set `DB_URL` as well. For example: `$env:DB_URL = "jdbc:mysql://localhost:3306/voice_ai?serverTimezone=UTC"`.

3. Start the Spring Boot service from the repository root:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

4. In a second PowerShell terminal, create a virtual environment and install the Python client dependencies:

   ```powershell
   py -m venv .venv
   .\.venv\Scripts\Activate.ps1
   py -m pip install -r python\requirements.txt
   py python\voice_client.py
   ```

   The first run may prompt for microphone access. Say “Hey Friday” followed by a command.

5. Open [http://localhost:8080](http://localhost:8080) to use the dashboard. Browser voice input works in Chrome or Edge; typed commands work in other browsers.

## Supported commands

- `open youtube`, `open chrome`, `open edge`, `open file explorer`
- `open notepad`, `open calculator`, `open paint`
- `open word`, `open excel`, `open powerpoint`
- `close` followed by a supported app name

The Python microphone client uses Google speech recognition and therefore needs internet access. The dashboard sends commands to the local service. The service binds to `127.0.0.1` by default and launches or closes apps on the computer running it.

## Project layout

```text
python/                         Python microphone client and requirements
src/main/java/                  Spring Boot API and Windows desktop actions
src/main/resources/static/      Browser dashboard
src/main/resources/application.properties
```
