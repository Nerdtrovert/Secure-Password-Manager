🔐 Secure Password Manager (Java Desktop App)






A lightweight offline password manager built in Java demonstrating secure credential storage, encryption workflows, and user-focused product design.

This project was built as part of my learning journey in secure software development and product design, focusing on how real-world applications balance security, usability, and simplicity.

⭐ Highlights

🔒 AES-encrypted credential vault

🔑 Salted SHA-256 master password authentication

🖥️ Desktop GUI built using Java Swing

👁️ Password visibility toggle during credential entry

📋 Copy password to clipboard directly from vault

🗑️ Delete credentials from vault

🧹 Reset vault feature for secure re-initialization

👤 First-run onboarding with optional username

🎬 Quick Demo

Example vault interface:

Users can:

Add credentials

View encrypted vault entries

Copy passwords to clipboard

Delete credentials

Reset the vault securely

🎯 Product Vision

Many users store passwords in plain text files, spreadsheets, or notes apps, creating significant security risks.

This application aims to provide a simple offline alternative that prioritizes:

🔐 Secure local storage

🖥️ Simple desktop interface

⚡ Minimal setup friction

👤 Clear user ownership

The goal is to balance security, usability, and accessibility while keeping the application lightweight.

🧠 Product Design Decisions

Key UX and product decisions implemented:

First-run onboarding flow with optional username

Encrypted local storage to protect credentials

GUI-based interaction instead of command line

Visible user session label

Vault reset workflow for safe reinitialization

Password visibility toggle for better usability

Clipboard copy feature for faster password usage

These design choices prioritize clarity, trust, and accessibility.

🚀 Core Features
🔑 Security & Authentication

Master password login using salted SHA-256 hashing

AES encrypted vault storage

Passwords never stored in plain text

Fully offline credential management

💻 User Experience

Guided first-time onboarding

Optional username personalization

Clean credential entry interface

Show / Hide password toggle

Table-based vault viewer

Copy password to clipboard

Delete credentials

Reset vault option

📂 Storage & Reliability

File-based encrypted vault

Persistent local storage

Lightweight architecture

No external dependencies

🛠️ Technology Stack

Java (Object-Oriented Programming)

Java Swing for GUI

File I/O for persistence

SHA-256 hashing with salting

AES encryption (javax.crypto)

Base64 encoding

📁 Project Structure
PasswordManager/
│
├── PasswordManager.java   # Application entry point
├── LoginFrame.java        # Login and onboarding UI
├── MainFrame.java         # Main vault manager interface
├── Vault.java             # Credential storage and file operations
├── MasterPassword.java    # Authentication and hashing logic
├── CryptoUtils.java       # AES encryption and decryption
│
├── vault.txt              # Encrypted credential storage
├── master.hash            # Stored master password hash
│
├── screenshots/
│   └── vaultcontents.png  # Example vault UI
│
└── README.md
▶️ Running the Application
Run the executable JAR
java -jar PasswordManager.jar
Run from source
javac *.java
java PasswordManager
🧩 Key Concepts Demonstrated

Secure password hashing with salting

AES encryption and decryption workflows

File-based data persistence

Desktop GUI design with Java Swing

Event-driven programming

Clipboard integration

Modular object-oriented architecture

Basic product design thinking

📈 Product Roadmap (Future Enhancements)
Near-Term Improvements

Password strength indicator

Vault search and filtering

Credential editing

Mid-Term Opportunities

Dark / light theme toggle

Database-backed storage

Backup and restore workflow

Long-Term Vision

Multi-user support

Secure device synchronization

Cross-platform installers

🎯 What This Project Demonstrates
Engineering Perspective

Secure credential storage

Encryption workflows

Desktop GUI development

Modular architecture

Product Perspective

User-centric onboarding design

Feature prioritization thinking

Iterative UX improvements

Product roadmap planning

📜 License

Educational and portfolio use.

If you are still reading — thank you 🙂