# Chat App

A simple real-time chat application featuring public and private messaging, built with Node.js, Socket.IO, MongoDB on the backend and Kotlin Jetpack Compose with MVVM on the frontend.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Demo Video](#demo-video)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

---

## Overview

This project implements a chat application with both public channels and private messaging capabilities.  
- **Backend:** Node.js and Socket.IO power real-time communications with MongoDB used for message persistence.  
- **Frontend:** Android app built with Kotlin Jetpack Compose using an MVVM architecture and Socket.IO client for Android.

---

## Features

- **Real-time Messaging:** Instant chat updates for all users.
- **Public Chat Room:** Broadcast messages to all connected users.
- **Private Chat:** Initiate one-to-one private conversations by selecting a connected user.
- **User List:** Display connected socket IDs (excluding your own) to start private chats.
- **MongoDB Integration:** Persist public chat messages in a database.
- **Modern UI:** Clean and responsive interface built with Jetpack Compose.

---

## Tech Stack

**Backend:**
- Node.js
- Express
- Socket.IO
- MongoDB
- Mongoose

**Frontend:**
- Kotlin
- Android Jetpack Compose
- MVVM Architecture
- Socket.IO Client for Android

---

## Project Structure

```
my-chat-backend/
├── src/
│   ├── server.js         // Express & HTTP server initialization
│   ├── socket.js         // Socket.IO events & logic
│   ├── database.js       // MongoDB connection using Mongoose
│   └── models/
│       ├── ChatMessage.js  // Mongoose model for chat messages
│       └── Message.models.js  // Additional message model (if applicable)
├── package.json
└── README.md

my-chat-frontend/
└── app/
    ├── build.gradle        // App dependencies including Jetpack Compose and Socket.IO client
    └── src/
        └── main/
            ├── java/
            │   └── com/example/chatapp/
            │       ├── MainActivity.kt     // Navigation setup for the app
            │       ├── model/
            │       │   └── Message.kt        // Message data model with sender/receiver info
            │       ├── viewmodel/
            │       │   └── ChatViewModel.kt  // Socket management and state handling
            │       └── ui/
            │           ├── UsersScreen.kt    // List of connected users
            │           └── PrivateChatScreen.kt  // Private chat UI
            └── res/
                └── ...                     // UI resources, colors, themes, etc.
```

---

## Demo Video

Watch the demo video below for a walkthrough of the chat application:

https://github.com/user-attachments/assets/4b8d939b-8696-419c-a72d-830c0db8783a

*Replace `<VIDEO_ID>` with your YouTube video ID.*

---

## Installation

### Backend

1. **Clone the repository:**

   ```sh
   git clone <your-repo-url>
   cd my-chat-backend
   ```

2. **Install dependencies:**

   ```sh
   npm install
   ```

3. **Configure MongoDB:**
   - Ensure MongoDB is running locally at `mongodb://localhost:27017/chat-app`.
   - Modify `src/database.js` if needed.

4. **Start the backend server:**

   ```sh
   npm start
   ```

### Frontend

1. **Open the project in Android Studio (or your favorite IDE).**

2. **Configure the WebSocket URL:**
   - In `ChatViewModel.kt`, update the URL according to your device/emulator settings.
  
3. **Build and run the app:**
   - Use an Android emulator or physical device to test the application.

---

## Usage

- **Public Chat:**
  - Launch the app to join the public chat room.
  - Type and send messages to broadcast to all connected users.

- **Private Chat:**
  - Navigate to the Users list, which shows all connected sockets (excluding your own).
  - Tap on a user card to open a private chat screen.
  - Send private messages to that user.

- **Message Persistence:**
  - Public chat messages are stored in MongoDB.
  - You can extend the backend REST API to retrieve chat history if needed.

---

## License

This project is licensed under the MIT License. See the LICENSE file for details.

---

Happy Coding & Chatting!
```
