import java.util.Properties

// Hands-on 1: Resource & Packaging
// Tugas: Baca file app.properties yang ada di src/main/resources melalui
// classpath (bukan lewat File API biasa), lalu tampilkan isinya.
//
// Ini menunjukkan salah satu tugas utama build system: mem-package resource
// non-kode (properties, gambar, config) ke classpath yang sama dengan
// hasil kompilasi .class, sehingga aplikasi bisa membacanya di runtime
// tanpa peduli lokasi file aslinya di disk.

fun main() {
    val inputStream = object {}.javaClass.classLoader.getResourceAsStream("app.properties")
        ?: error("app.properties tidak ditemukan di classpath")

    val properties = Properties()
    inputStream.use { properties.load(it) }

    println("Nama aplikasi: ${properties.getProperty("app.name")}")
    println("Versi: ${properties.getProperty("app.version")}")
}
