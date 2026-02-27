🔐 Secure Password Manager (Java Desktop App)

A desktop password manager designed to address the common problem of insecure credential storage.
This project combines secure engineering practices with user-focused design decisions to create a simple, privacy-first password management tool.

🎯 Product Vision
Many users store passwords in plain text files or notes apps, creating security risks.
This application aims to provide a lightweight offline alternative that prioritizes:
privacy (no cloud dependency)
simplicity of use
secure local storage
clear user onboarding

The goal was to build a tool that balances security, usability, and minimal setup friction.

🧠 Product Design Decisions
First-run onboarding flow with optional username to improve personalization
Encrypted local storage to protect data without requiring internet access
GUI-based interaction to reduce friction compared to CLI tools
Visible user session label to reinforce account ownership
Reset workflow allowing users to safely restart vault setup
These choices reflect a focus on user trust, clarity, and accessibility.

🚀 Core Features

🔑 Security & Authentication
Master password login with salted SHA-256 hashing
AES-encrypted credential storage
No plain-text password storage

💻 User Experience
First-time setup with guided onboarding
Simple credential entry interface
Table-based vault viewing for clarity
In-app delete workflow to manage stored data
Reset option for full vault re-initialization

📂 Storage & Reliability
Offline file-based persistence
Encrypted vault contents
Minimal configuration required

🛠️ Technology Stack
Java (object-oriented design)
Java Swing (desktop UI)
File I/O for persistence
SHA-256 hashing with salting
AES encryption using javax.crypto

▶️ Running the Application:
Download and unzip the repo
java -jar PasswordManager.jar

Or run from source:

javac *.java
java PasswordManager

📈 Product Roadmap (Future Enhancements)
Near-Term Improvements:
Password strength indicator
Search and filtering within vault
Clipboard copy feature

Mid-Term Opportunities:
Dark/light theme options
Database storage for scalability
Backup and restore workflow

Long-Term Vision:
Multi-user support
Secure sync across devices
Cross-platform installer packaging

🎯 What This Project Demonstrates
From an engineering perspective:
secure storage practices
encryption workflows
modular architecture

From a product perspective:
user-centric onboarding design
feature prioritization thinking
iterative UX improvements
roadmap planning mindset

📜 License
Educational and portfolio use.
If you are still reading, thank you🙂
