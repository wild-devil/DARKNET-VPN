# DARKNET Android UI

A responsive, dark cyberpunk Android interface prototype.

## Included
- Responsive native Android layouts (no fixed 400x1320 canvas)
- Persian / English UI toggle
- Country catalog (not live VPN endpoints)
- Local CDN profile storage for authorized CDN/origin configurations
- Local DNS preference storage
- Network Lab status, event log, settings and About pages

## Important
This build is UI and local configuration only. It does **not** establish a VPN connection.
Psiphon engine integration is intentionally a separate next phase. CDN profile storage does
not implement CDN fronting or bypass network restrictions.

## Build
Open this folder in Android Studio, allow Gradle sync, then Build > Build APK(s).
Requirements: JDK 17, Android Gradle Plugin 8.5.2, Android SDK Platform 35.
