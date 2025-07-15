# 🎤 VocalInk

VocalInk is a voice-to-text Android application that enables users to transcribe spoken input into text and save it for later reference. Built with modern Android development practices, it emphasizes Clean Architecture, reactive programming, and testability.

## ✨ Features

- 🎙️ Real-time voice recognition using Android's `SpeechRecognizer`
- 💾 Persistent storage of transcribed voice notes
- 📜 View and manage voice history
- 🧪 Unit-tested ViewModels and domain logic
- 🎨 Modern UI with Jetpack Compose
- 📦 Modular and maintainable architecture

---

## 🧱 Tech Stack

### 🖼️ UI
- **Jetpack Compose** – Declarative UI framework
- **Material 3** – Design system
- **Navigation Compose** – In-app navigation (including bottom sheet)
- **Accompanist Navigation Material** – Modal bottom sheet support
- **Custom UI Components** – `CocoText`, `CocoDropdown`, `VIError`, etc.

### 🧠 Architecture
- **MVVM** – Model-View-ViewModel architecture
- **Clean Architecture** – Separation of concerns (domain, data, presentation)
- **Hilt** – Dependency Injection
- **StateFlow & SharedFlow** – Reactive state management

### 🗂️ Data & Persistence
- **Room** – Local SQLite DB for storing voice notes
- **Repository Pattern** – Abstract data sources

### 🗣️ Voice Recognition
- **SpeechRecognizer API** – Built-in Android voice recognition
- **VoiceToTextManager (custom)** – Manages speech input lifecycle

### ⚙️ Background & Threading
- **Kotlin Coroutines** – Asynchronous programming
- **Dispatchers (with Qualifiers)** – Injected to control coroutine context

### 🧪 Testing
- **JUnit** – Unit testing
- **Turbine** – Flow testing
- **MockK** – Mocking dependencies
- **Hilt Testing** – Injecting test modules

---

## 🏗️ Modules
