# Navigation3 🧭

> **A next-generation Android application built with a custom 'Navigation 3' architecture in Jetpack Compose, featuring manual state management, a high-fidelity dark UI, and performant shimmer animations.**

![Kotlin](https://img.shields.io/badge/kotlin-2.0.0-blue.svg?logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-Material3-green.svg?logo=android)
![License](https://img.shields.io/badge/license-MIT-lightgrey.svg)

---

## 📱 Project Overview

**Nav3** is a showcase application that demonstrates how to build a robust, state-preserving navigation system in Jetpack Compose **without** relying on the standard `androidx.navigation` library.

It features a purely Kotlin-based `Navigator` class, manual backstack handling with `NavBackStack`, and a stunning "Matrix-Dark" aesthetic with custom shimmer loading effects and interactive floating components.

---

## ✨ Key Features

### 🛠️ Architecture: The "Nav3" System
* **Custom Navigation Stack:** A manual `Navigator` and `NavigationState` implementation that handles pushing/popping screens and maintaining history typesafely.
* **State Preservation:** Uses `MutableStateSerializer` and `rememberSerializable` to ensure navigation state (including backstacks) survives process death and configuration changes.
* **Type-Safe Destinations:** Fully strongly-typed navigation keys (`NavKey`) using sealed interfaces.

### 🎨 UI & UX
* **Floating Pill Bottom Bar:** A custom-designed, floating navigation bar with animated selection indicators and negative offsets for a compact, modern look.
* **Skeleton Shimmer Loading:** A custom `Modifier.shimmerEffect()` extension that creates a dynamic, high-performance loading state for list items.
* **Premium Dark Theme:** A carefully curated color palette (`DarkBg`, `CardBg`, `AppColors`) providing a sleek, modern dark mode experience.
* **Interactive Elements:** Custom `NoRipple` implementation to override default Material touches, replacing them with custom state-based animations (like the Spring-based FAB).

---

## 📸 Screenshots

<img width="250" height="550" alt="image" src="https://github.com/user-attachments/assets/74900df1-f7d1-41b2-9c5e-2c9689736317" />
<img width="250" height="550" alt="image" src="https://github.com/user-attachments/assets/64f99014-c393-402f-bd43-67460618cb44" />
<img width="250" height="550" alt="image" src="https://github.com/user-attachments/assets/9381150c-cb96-412b-9db0-32a2851fbbdb" />


---

## 💻 Tech Stack

* **Language:** [Kotlin](https://kotlinlang.org/)
* **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
* **Design System:** [Material 3](https://m3.material.io/)
* **Serialization:** [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) (for saving NavKeys)

---

## 🧩 Code Highlights

### The Nav3 Navigator
The core of the app uses a custom state holder to manage backstacks for each top-level route manually:

@Serializable
sealed interface NavKey : BaseNavKey {
    @Serializable data object Home : NavKey
    @Serializable data object Search : NavKey
    @Serializable data object Profile : NavKey
}

class Navigator(val state: NavigationState) {
    fun navigate(route: NavKey) {
        if (route in state.backStacks.keys) {
            state.topLevelRoute = route
        } else {
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }
}


---

## 🚀 Getting Started
- Clone the repository:git clone [https://github.com/yourusername/Nav3.git](https://github.com/yourusername/Nav3.git)
- Open in Android Studio: Ensure you are using the latest version (Ladybug or newer recommended).
- Sync Gradle: Download the required dependencies.
- Run the App: Select your emulator or physical device and press Run.

---

## 🤝 Contributing
Contributions, issues, and feature requests are welcome! Feel free to check the issues page.
- Fork the project
- Create your feature branch (git checkout -b feature/AmazingFeature)
- Commit your changes (git commit -m 'Add some AmazingFeature')
- Push to the branch (git push origin feature/AmazingFeature)
- Open a Pull Request


---

## 📄 License
Distributed under the MIT License. See LICENSE for more information.
