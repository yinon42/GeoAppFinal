

---

# **GeoAppFinal**
GeoAppFinal is an Android application designed for personalized music management and smart recommendations.

For a demo video, go to:  
🔗 **[Demo Video](https://drive.google.com/file/d/15vwHjYZyDG2kJr6KqXmf5UbGhLe8SGzg/view?usp=sharing)**  

---

## **📌 Key Features**
✅ **Create and manage personalized playlists**  
✅ **Recommendations based on personal taste**  
✅ **Social interaction and playlist sharing**  
✅ **Intuitive user interface**  

---

## **⚙️ System Requirements**
- Android Studio (latest recommended version)
- Java installed
- Compatible Android version (check `build.gradle.kts` file)

---

## **🚀 Installation and Running**
1. Clone the project to your computer:
   ```bash
   git clone https://github.com/your-repo/GeoAppFinal.git
   ```
2. Open the project in Android Studio.
3. Ensure all dependencies are installed via Gradle.
4. Run the application on an emulator or physical device.

---

## **📁 Project Structure**
```
app/               # Main application source code
gradle/            # Gradle build files
settings.gradle.kts # Gradle project settings file
build.gradle.kts   # Project configuration file
```

---

## **🌍 Country View Handling**
GeoAppFinal determines the correct `View` based on the country detected from the **database (DB)**.

### **🔹 If a country has a built-in View:**
- The system automatically directs the user to the relevant `Activity` for that country.
- Example:  
  - **Israel** → Directs to `NorthIsraelRestaurantPage`, `CenterIsraelRestaurantPage`, or `SouthIsraelRestaurantPage`.  
  - **Sri Lanka** → Directs to `SriLankaRestaurantPage`.

### **🔹 If a country is in the database but has no built-in View:**
- The system **displays a map** of the country with a marker on the detected location.
- The user is presented with a **generic map interface** (`GenericCountryPage`).
- This allows support for additional countries **without needing a dedicated UI for each one**.

---

## **🤝 Contribution**
If you want to improve the project, feel free to submit a **Pull Request** or open an **issue** in the repository.

---

## **📜 License**
This project is licensed under the **MIT License**. You can find the details in the `LICENSE` file.

---

### **💡 Example Workflow**
1. The app detects the user's country based on **GPS coordinates** or **manual selection**.
2. The system **checks if the country exists in the DB**:
   - ✅ **If a specific View exists**, the app loads the corresponding `Activity`.
   - 🌍 **If no specific View exists**, the app loads `GenericCountryPage`, which shows a map of the country.

---

🚀 **This approach allows seamless expansion to more countries without requiring UI development for each one!** 🎵🌍

![image](https://github.com/user-attachments/assets/6c115f20-ccef-4006-a2ed-eaf405fa2e24) ![image](https://github.com/user-attachments/assets/6eddd215-2a76-4377-b539-11f47e5fe33a) ![image](https://github.com/user-attachments/assets/e1e132ab-4673-4145-808c-df7c940007bb)





