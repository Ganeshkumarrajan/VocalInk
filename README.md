# 🎙️ VocalInk

**VocalInk** is a modern Android app that lets users convert voice to text and store it locally.  
Built with **Jetpack Compose**, **Kotlin Flow**, **Clean Architecture**, and **Hilt**, it's designed to be testable, scalable, and developer-friendly.

---

## ✨ Features

- 🎤 Real-time voice-to-text transcription
- ⏱️ Countdown timer while recording
- 🗂️ Voice history screen with timestamps
- 💾 Local persistence using Room
- 🧪 Modular and testable architecture
- 💉 Dependency Injection via Hilt
- ⚙️ Jetpack Compose UI with preview support

---

## 🧩 Modules Overview

| Module                     | Description |
|---------------------------|-------------|
| `app`                     | App entrypoint and navigation |
| `feature/voicetotext`     | Voice recognition feature |
| `feature/voicehistory`    | Voice history display feature |
| `data/voice`              | Local storage with Room |
| `data/timer`              | Countdown timer using Flow |
| `domain/voice`            | Voice use cases & models |
| `core/ui`                 | Shared UI components (buttons, cards, etc.) |
| `core/utils`              | Utilities like formatting and error mapping |

---

## 📐 Architecture

Follows **Clean Architecture** with modular boundaries:

```text
View (Compose)
↓
ViewModel (StateFlow)
↓
UseCase (Domain)
↓
Repository (Data)
↓
Local (Room) / Manager
