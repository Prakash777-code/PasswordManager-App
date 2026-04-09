# 🔐 VaultX – Secure Vault App

VaultX is a secure Android application that allows users to store and manage sensitive data like passwords locally on their device.

The app is built using modern Android architecture with strong focus on security, performance, and clean UI.

---

## 🚀 Features

- Fully offline (no internet required)
- AES/GCM encryption using Android Keystore
- Biometric Authentication (Fingerprint / Device Credential)
- Password strength checker with real-time feedback
- Add, update, and delete passwords
- Copy email/password to clipboard
- Search functionality with live filtering
- Staggered grid layout for better UI
- Toggle password visibility using eye icon (Show/Hide feature)
- Encrypted data storage using Room Database
- Smooth UI updates using DiffUtil
- Toast messages to notify user

---

## 🛠️ Tech Stack

### 🔹 Core
- Kotlin
- Android SDK

### 🔹 Architecture
- MVVM (Model-View-ViewModel)
- Repository Pattern

### 🔹 Database
- Room Database

### 🔹 UI & Design
- XML (Android Views)
- RecyclerView
- StaggeredGridLayoutManager (Grid UI)
- ConstraintLayout
- Material Components

### 🔹 Jetpack Components
- LiveData
- ViewModel
- Activity Result API

### 🔹 Asynchronous Handling
- Kotlin Coroutines (viewModelScope)

### 🔹 Security
- AES/GCM/NoPadding Encryption
- Android Keystore System
- BiometricPrompt API

### 🔹 Android Components Used
- Activities
- Intents
- RecyclerView Adapter
- DiffUtil
- Toast

### 🔹 System Services
- Clipboard Manager

### 🔹 Tools & Build
- Android Studio
- Gradle
- Git & GitHub

---

## 📱 How It Works

- User authentication is handled using BiometricPrompt before accessing the app
- Password data is stored in Room Database
- Sensitive data is encrypted using AES (GCM mode) via Android Keystore
- RecyclerView with DiffUtil ensures efficient UI updates
- LiveData + ViewModel manage UI state and lifecycle
- Search is implemented using LiveData switchMap for real-time filtering
- Coroutines handle background database operations

---

## 📂 Project Structure

```
VaultX/
│── app/
│   ├── Ui/
│   ├── ViewModel/
│   ├── Repository/
│   ├── Local/
│   ├── Adapter/
│   ├── Models/
│   ├── security/
│   ├── Util/
│   ├── res/
│   └── AndroidManifest.xml
│── gradle/
│── build.gradle
```

---

## 🔐 Security

- AES encryption with GCM mode
- Keys stored securely in Android Keystore
- Biometric authentication required for access
- No network usage (fully offline)
- Sensitive data never stored in plain text  