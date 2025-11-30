# Korean Beauty App - Mobile Automation Testing

## 🎯 Project Overview
This project performs automated testing for the Korean Beauty App on Android (unpublished App) using:
- **Appium** - For mobile app automation
- **TestNG** - For test management and execution
- **Allure** - For comprehensive test reporting

---

## 📋 Prerequisites

Before running the project, ensure you have installed:

1. ✅ **Java JDK 11** or higher
2. ✅ **Maven** for project management
3. ✅ **Android SDK** and **ADB**
4. ✅ **Appium Server** (must be running on `http://127.0.0.1:4723`)
5. ✅ **Allure Command Line** for reporting

### Installing Allure:
```bash
# Windows (using Scoop)
scoop install allure

# Or using npm
npm install -g allure-commandline
```

---


## 📊 Viewing Reports

###Automatic (Opens Automatically)
After test execution completes:
1. ✅ Old results are cleaned automatically
2. ✅ New report is generated
3. ✅ Report opens in browser automatically

## 📁 Project Structure

```
Automation Korean app/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       │── org/
│   │       │    ├── BaseTest.java          
│   │       │    ├── listeners/
│   │       │        └── TestListener.java  
│   │       ├── pages/                     
│   │       │   ├── HomePage.java
│   │       │   ├── ProfilePage.java
│   │       │   ├── AddAddressPage.java
│   │       │   ├── ProductPage.java
│   │       │   └── CartPage.java
│   
│   ├── test/
│   │    ├── java/
│   │    │   └── tests/                    
│   │    │       ├── HomePageTest.java
│   │    │       ├── ProfileNavigationTest.java
│   │    │       ├── RewardsProgramTest.java
│   │    │       ├── AddAddressTest.java
│   │    │       ├── ProductSearchTest.java
│   │    
│   ├── resources/                    
│   │    ├── allure.properties
│   │    ├── categories.json
│   │    ├── environment.properties 
│       
│
├── target/
│   ├── allure-results/     
│   └── allure-report/     
│
├── testng.xml              
├── pom.xml              
└── README.md             
```


---

## 🎨 Console Output

The console output is now cleaner and more informative:

```
╔════════════════════════════════════════════════════════════╗
║          TEST STARTED: Korean App Test Suite
║          Start Time: 2024-01-15 10:30:00
╚════════════════════════════════════════════════════════════╝

┌─────────────────────────────────────────────────────────┐
│ TEST STARTED: testAppOpenedSuccessfully
│  Class: tests.HomePageTest
│  Time: 2024-01-15 10:30:05
└─────────────────────────────────────────────────────────┘

Driver initialized successfully
App opened successfully in home page

┌─────────────────────────────────────────────────────────┐
│ TEST PASSED: testAppOpenedSuccessfully
│    Duration: 5 sec 234 ms
└─────────────────────────────────────────────────────────┘
```

---
## 📊 Test Execution Flow

1. **BeforeSuite**: Cleans old Allure results
2. **BeforeClass**: Sets up test class
3. **BeforeMethod**: Initializes or checks driver health
4. **Test Execution**: Runs individual test methods
5. **AfterMethod**: Captures screenshot on failure
6. **AfterClass**: Cleans up test class
7. **AfterSuite**: Closes driver and generates Allure report

---

## 🔧 Configuration

### Device Configuration
Edit `BaseTest.java` to change device settings:
```java
options.setDeviceName("Android Device");
options.setUdid("qklz7tjfa67xhacu");  // Change to your device UDID
```

### Appium Server URL
Default: `http://127.0.0.1:4723`  
Change in `BaseTest.java` if needed.

### Timeouts
Configured in `BaseTest.java`:
- Implicit Wait: 10 seconds
- Command Timeout: 300 seconds
- Server Install Timeout: 60 seconds
---

---

## Creators

- Moustafa Hesham Mahmoud
- Ahmed Mohamed Moussa
- Eyad Hussein Mohamed
- Yamen Ahmed Fathy

---

## 📄 License

This project is for educational and testing purposes.

---

**Good Luck! 🚀**
