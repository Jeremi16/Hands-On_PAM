// Hands-on 2: Custom Gradle Task
// Jalankan: ./gradlew :handson2-latihan:printProjectInfo
//
// Konsep: tasks.register(...) mendaftarkan task baru secara LAZY (baru
// dikonfigurasi saat benar-benar dibutuhkan). doLast {} berisi aksi yang
// dijalankan pada EXECUTION phase, bukan saat Gradle mengevaluasi script
// (CONFIGURATION phase).

version = "1.0.0"

tasks.register("printProjectInfo") {
    group = "kotlin dasar"
    description = "Mencetak nama project, versi project, dan versi Gradle"

    doLast {
        println("Nama project: " + project.name)
        println("Versi project: " + project.version)
        println("Versi Gradle: " + gradle.gradleVersion)
    }
}
