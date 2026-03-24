---

# ⚙️ MikroTik Automation (Java)

This project demonstrates how to interact with a MikroTik router using Java via API.

It provides basic automation features such as user management and system monitoring.

---

## 🚀 Features

* 🔐 Connect to a MikroTik router via API
* 👥 List existing users
* ➕ Create a new user (only if it doesn’t already exist)
* 📊 Fetch system information:

  * CPU load
  * Uptime
  * Router identity (name)
  * Temperature *(if supported)*
* ⚡ Asynchronous API handling using `ResultListener` and `CountDownLatch`

---

## ⚙️ Requirements

* Java 17+
* Maven
* MikroTik router (API enabled)
* PC connected via Ethernet
* *(Optional)* CHR (Cloud Hosted Router) for local testing

---

## 📦 Setup

### 1. Clone or Download

```bash
git clone (https://github.com/andrewmugisa/mikrotik-automation01.git)
cd mikrotik-automation01
```

### 2. Build Project

```bash
mvn clean install
```

### 3. Dependencies

This project uses **tik4j** for MikroTik API interaction (already included in `pom.xml`).

---

## 💻 Network Configuration

To connect to the MikroTik router, your PC must be on the same subnet.

**Default MikroTik IP:** `192.168.88.1`

---

### 🪟 Windows

Run Command Prompt as Administrator:

```bash
netsh interface ip set address "Ethernet" static 192.168.88.100 255.255.255.0 192.168.88.1
```

> Replace `"Ethernet"` with your actual interface name if different.

---

### 🐧 Linux

```bash
sudo ip addr add 192.168.88.100/24 dev eth0
sudo ip route add default via 192.168.88.1
```

> Replace `eth0` with your actual interface name.

---

## 🔧 Configuration

Update credentials in:

```
AddUserExample.java
```

```java
String host = "192.168.88.1";
String username = "admin";
String password = "admin";
```

---

## ▶️ Run the Project

```bash
mvn exec:java -Dexec.mainClass="com.example.AddUserExample"
```

---

## 📤 Expected Output

The console will display:

* Existing users
* Result of user creation
* Updated user list
* System information:

  * CPU load
  * Uptime
  * Router name
  * Temperature *(if available)*

---

## ⚠️ Notes

* 🌡️ Temperature is hardware-dependent

  * Example: `RB951G-2HnD` does **not** report temperature

* 🔌 Ensure API is enabled:

  * Winbox → **IP → Services → API**

* 🧪 Always test in a safe environment (e.g., CHR) before production use

---

## 🧭 How It Works

```
+--------------------+        Connect via API         +--------------------+
|                    | ----------------------------> |                    |
| Java Application   |                               |  MikroTik Router   |
| (AddUserExample)   | <---------------------------- |  (API Enabled)     |
|                    |       Auth Response          |                    |
+---------+----------+                               +---------+----------+
          |                                                 |
          | Request existing users                           |
          |------------------------------------------------>|
          |                                                 |
          | Receive list of users                            |
          |<------------------------------------------------|
          |                                                 |
          | Check if new user exists                         |
          |                                                 |
          | Create new user if needed                        |
          |------------------------------------------------>|
          |                                                 |
          | Receive creation status                          |
          |<------------------------------------------------|
          |                                                 |
          | Fetch system info (CPU, uptime, name, temp)     |
          |------------------------------------------------>|
          |                                                 |
          | Receive system info                             |
          |<------------------------------------------------|
          |                                                 |
          v
+--------------------+
| Console Output     |
| (Users & System)   |
+--------------------+
```

---

## 📄 License

This project is open-source and free to use, modify, and distribute.

---

## 👤 Author

**Andrew**

---
