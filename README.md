🔐 Secure Password Manager (Java GUI)

A desktop password manager built in Java demonstrating secure authentication, encrypted storage, and modular object-oriented design.
The application uses salted hashing for login security and AES encryption to protect stored credentials.

Features:
Master password authentication with salted SHA-256 hashing
AES encryption for secure vault storage
GUI interface built using Java Swing
Add, view, and delete credentials
Persistent file-based storage
Clean modular architecture separating UI, security, and storage logic

Technologies Used:
Java (OOP design)
Swing (GUI)
File I/O (BufferedReader, FileWriter)
SHA-256 hashing with salting
AES encryption using javax.crypto
Base64 encoding

Run executable jar
Download the jar from the Releases section and run:

$-  java -jar PasswordManager.jar

Learning Objectives:-
This project was built to practice:
Secure authentication design
Encryption workflows
File-based data persistence
Event-driven GUI programming
Clean object-oriented architecture

Future Improvements:
Password strength meter
Search and copy-to-clipboard feature
Dark mode UI
Database integration
Cross-platform packaging

License-
This project is for educational and portfolio purposes.
