# Hotel Management System 🏨

Ez egy Java alapú, MySQL adatbázist használó, grafikus felhasználói felülettel (Swing) ellátott Hotel Management System, amely a szállodai működés legfontosabb folyamatait segíti elő: vendégkezelés, szobafoglalás, alkalmazotti adatok, járművek és szolgáltatások nyilvántartása.

## 🎯 Fő funkciók

- Vendégek felvétele, kijelentkezés
- Szobák elérhetőségének kezelése
- Alkalmazottak nyilvántartása (pl. csak menedzserek listázása)
- Sofőr- és járműadatok lekérdezése (pl. pickup szolgáltatás)
- Teljes adatbáziskapcsolat (MySQL) az adatok biztonságos és strukturált tárolásához

## 🛠 Technológiák

- **Java** (Swing - GUI)
- **MySQL** (JDBC kapcsolaton keresztül)
- **NetBeans**/IntelliJ támogatott
- `DbUtils` könyvtár a táblák egyszerű feltöltéséhez

## 🧩 Modulok

- `Reception.java`: Főmenü és navigáció a funkciók között
- `AddCustomer.java`, `Checkout.java`: Vendégmenedzsment
- `Pickup.java`: Sofőr- és autószolgáltatás szűrés
- `ManagerInfo.java`: Csak menedzserek listázása az alkalmazottak közül
- `Conn.java`: Adatbáziskapcsolat osztály, biztonságos zárással

# Hotel Management System 🏨

A Java-based Hotel Management System with a Swing GUI and MySQL database integration. It provides an intuitive interface for managing hotel operations such as guest check-ins/check-outs, room availability, employee records, and transportation services.

## 🎯 Key Features

- Add new guests, check out guests
- Manage room availability and status
- View employee records (e.g., filter only managers)
- Driver and car management for pickup services
- Fully connected to a structured MySQL backend via JDBC

## 🛠 Technologies Used

- **Java** (Swing for GUI)
- **MySQL** (with JDBC)
- **DbUtils** for populating tables
- Compatible with **NetBeans**, **IntelliJ**, or **Eclipse**

## 🧩 Main Modules

- `Reception.java`: Main dashboard with access to all features
- `AddCustomer.java`, `Checkout.java`: Guest management
- `Pickup.java`: Driver/car lookup based on availability or criteria
- `ManagerInfo.java`: Display only managers from the employee list
- `Conn.java`: Centralized, reusable database connection class with safe closing



﻿# hotelmanagement
![image](https://github.com/user-attachments/assets/4e3d4b6e-c333-4b12-a836-c1169ba86e7b)

# admin dashboard
![image](https://github.com/user-attachments/assets/83f85365-3fdb-4135-96fd-f069c64e0ece)

![image](https://github.com/user-attachments/assets/20de52a0-e2ef-4e58-bc87-d1bb2243b0e5)
