"# mikrotik-automation01" 
MikroTik Automation Java Project

This project demonstrates how to interact with a MikroTik router using Java.
It allows you to:

Connect to a MikroTik router via API
List existing users
Create a new user if it doesn’t exist
Fetch basic system info (CPU load, uptime, router name, temperature if available)

⚙️ Requirements
Java 17+ (or compatible)
Maven
MikroTik router (any model with API enabled)
PC connected via Ethernet to the MikroTik router
Optional: CHR (Cloud Hosted Router) for local testing

📦 Setup
Clone the project or download the source code.
Build with Maven:
mvn clean install
Add tik4j dependency in pom.xml (already included in this project).

💻 Assign IP to PC (Ethernet)

To access the router from your PC:

Plug your PC into the MikroTik router via Ethernet.
Assign a static IP on the same subnet as the router (default MikroTik IP: 192.168.88.1).

On Windows (Command Prompt as Admin):

netsh interface ip set address "Ethernet" static 192.168.88.100 255.255.255.0 192.168.88.1
"Ethernet" → replace with your Ethernet interface name
192.168.88.100 → desired PC IP
255.255.255.0 → subnet mask
192.168.88.1 → router gateway

On Linux (Terminal):

sudo ip addr add 192.168.88.100/24 dev eth0
sudo ip route add default via 192.168.88.1
eth0 → your Ethernet interface name

🚀 Running the Project
Update the MikroTik credentials in AddUserExample.java:
String host = "192.168.88.1";
String username = "admin";
String password = "admin";
Run the main class:
mvn exec:java -Dexec.mainClass="com.example.AddUserExample"
Output will show:
Existing users
Status of creating the new user
Current users
Basic system info (CPU, uptime, router name, temperature if available)

🧰 Features
Add user: Only if the user doesn’t exist
Fetch users: Prints all current users
Fetch system info: Name, CPU load, uptime, temperature (if supported)
Asynchronous API handling: Uses ResultListener and CountDownLatch to wait for router responses

⚠️ Notes
temperature is only available on some hardware. RB951G-2HnD, for example, does not report a value.
Router API must be enabled (Winbox → IP → Services → API should be active).
Always test on a safe environment (like CHR) before production.





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
          | Receive creation status                           |
          |<------------------------------------------------|
          |                                                 |
          | Fetch system info (CPU, uptime, name, temp)     |
          |------------------------------------------------>|
          |                                                 |
          | Receive system info                               |
          |<------------------------------------------------|
          |                                                 |
          v
+--------------------+
| Console Output     |
| (Users & System)   |
+--------------------+
