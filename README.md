# Safai Setu — Smart Cleanliness Management System

**Safai Setu** is a JavaFX-based desktop application designed to manage cleanliness-related operations in cities.  
It connects to a MySQL database to record complaints, cleanliness reports, garbage bin statuses, and worker details, enabling municipal authorities to monitor and improve waste management efficiency.

---

## 🚀 Features

- **Complaint Management**
  - Register and view cleanliness-related complaints
  - Track complaint status
- **Cleanliness Reports**
  - Record inspection details for different city areas
  - Maintain cleanliness score and remarks
- **Dustbin Management**
  - Track bin locations, capacity, and status
  - View garbage collection logs
- **Worker Management**
  - Register sanitation workers
  - Assign tasks and view work history
- **Garbage Collection Logs**
  - Record bin ID, worker ID, collection date, bin status, and notes
- **User-Friendly JavaFX GUI**
  - Modern, clean design with intuitive navigation
  - Input panels with buttons for all components

---

## 🛠️ Technology Stack

- **JavaFX** — GUI development
- **Java 17+** — Core programming language
- **MySQL** — Relational database for storing data
- **JDBC** — Java Database Connectivity for MySQL
- **Maven** — Dependency management

---

## 📦 Installation & Setup

### Prerequisites
- **Java 17** or later
- **MySQL** installed and running
- **Maven** (or Gradle) installed

### Database Setup
1. Create a MySQL database named `safai_setu`.
2. Import the provided `safai_setu.sql` schema into MySQL.
3. Update your database credentials in `App.java`:
   ```java
   String url = "jdbc:mysql://localhost:3306/safai_setu";
   String user = "root";
   String password = "yourpassword";
