NavigationLab - Modul Praktikum 06
==================================

Cara membuka:
1. Ekstrak ZIP ini.
2. Buka Android Studio.
3. Pilih Open, lalu pilih folder NavigationLab_Modul06.
4. Tunggu Gradle Sync selesai. Jika diminta, gunakan JDK 17.
5. Jalankan aplikasi pada emulator atau perangkat Android.

Isi:
- Home, Detail, Profile, About
- Argument Navigation studentId
- Student model, in-memory data source, repository interface/implementation
- DetailUiState dan DetailViewModel
- DetailScreen sebagai UI yang menerima state dan callback

Catatan:
Project menggunakan Jetpack Compose, Material 3, Navigation Compose, dan AndroidX ViewModel.
Jika Android Studio meminta mengunduh Android SDK Platform 35 atau Gradle dependencies, izinkan proses tersebut.
\nPembaruan implementasi:\n- Home menampilkan daftar mahasiswa dari HomeViewModel melalui StudentRepository.\n- Pilih mahasiswa untuk membuka Detail berdasarkan ID.\n- Data lokal berisi lima mahasiswa.\n- Navigasi Profile, About, dan tombol Kembali tetap tersedia.\n