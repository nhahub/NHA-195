# Korean Beauty App - Mobile Automation Testing

## 🎯 Project Overview
This project performs automated testing for the Korean Beauty App on Android using:
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

## 🚀 How to Run the Project

### 1️⃣ Start Appium Server
```bash
appium
```

### 2️⃣ Connect Device
Verify device or emulator is connected:
```bash
adb devices
```
The device should appear with UDID: `qklz7tjfa67xhacu`

### 3️⃣ Execute Tests

#### Run all tests:
```bash
mvn clean test
```

#### Run specific test suites:
```bash
# Run Smoke Tests only
mvn clean test -Psmoke

# Run Regression Tests only
mvn clean test -Pregression
```

---

## 📊 Viewing Reports

### Method 1: Automatic (Opens Automatically)
After test execution completes:
1. ✅ Old results are cleaned automatically
2. ✅ New report is generated
3. ✅ Report opens in browser automatically

### Method 2: Manual
```bash
# Generate the report
allure generate target/allure-results --clean -o target/allure-report

# Open the report
allure open target/allure-report
```

---

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

## ✨ Key Features

### 1. **Enhanced BaseTest**
- ✅ Uses `ThreadLocal<AndroidDriver>` instead of static driver for thread safety
- ✅ Includes `@BeforeSuite` and `@AfterSuite` annotations
- ✅ Automatic cleanup of old test results
- ✅ Automatic Allure report generation after execution

### 2. **Custom TestListener**
- ✅ Enhanced logging with emojis and formatting
- ✅ Tracks execution time for each test
- ✅ Adds detailed information to Allure reports
- ✅ Full stack trace on test failures

### 3. **Improved testng.xml**
- ✅ Custom listeners configured
- ✅ Better test organization
- ✅ Support for parallel execution (currently disabled)

### 4. **Enhanced pom.xml**
- ✅ Test profiles (smoke, regression)
- ✅ Clean plugin for automatic cleanup
- ✅ Optimized configurations

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

## 🐛 Troubleshooting

### Issue: Appium Server Not Running
```bash
# Start Appium
appium

# Or with debug logging
appium --log-level debug
```

### Issue: Device Not Connected
```bash
# Check connected devices
adb devices

# Restart ADB
adb kill-server
adb start-server
```

### Issue: Driver Crashes
The code includes automatic driver restart functionality when crashes occur.

### Issue: Socket Hang Up Error
The code implements retry mechanism (3 attempts) for text input operations to handle socket hang up errors gracefully.

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

## 📞 Support

If you encounter any issues:
1. Verify Appium Server is running
2. Check device connection with `adb devices`
3. Review console logs for errors
4. Check Allure report for detailed test execution information
5. Verify all dependencies are properly installed

---

## 📝 Important Notes

- The driver uses `noReset: true` to maintain app state between tests
- Screenshots are automatically captured on test failures
- Allure reports open automatically in browser after test execution
- The framework includes retry mechanism for flaky operations
- Text input operations use safe clearing methods to avoid socket hang up errors

---

## 🎓 Best Practices

- Always start Appium Server before running tests
- Keep device/emulator awake during test execution
- Review Allure reports after each test run
- Use appropriate waits instead of hard-coded sleeps where possible
- Follow Page Object Model pattern for maintainability

---

## 📄 License

This project is for educational and testing purposes.

---

**Good Luck! 🚀**