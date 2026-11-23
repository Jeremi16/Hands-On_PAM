// Hands-on 3: Dependency Declaration
// Latihan.kt di module ini butuh library "kotlinx-datetime" untuk mencetak
// tanggal hari ini. Gradle akan resolve library-nya dari mavenCentral()
// (sudah didefinisikan di root build.gradle.kts) dan menaruhnya di compile
// classpath module ini.

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
}
