// Hands-on 1: Inline Function & Reified Generics
//
// Konteks: JVM menerapkan TYPE ERASURE pada generics — informasi tipe generik
// (mis. List<String> vs List<Int>) DIHAPUS saat dikompilasi menjadi bytecode
// .class, sehingga pada saat runtime JVM hanya melihat "List" biasa. Karena
// itu, fungsi generik biasa TIDAK BISA melakukan pengecekan `value is T`.
//
// Kotlin punya solusi: kombinasi `inline` + `reified`. Fungsi inline "disalin"
// (di-inline) langsung ke tempat pemanggilannya oleh compiler backend Kotlin
// SEBELUM menjadi bytecode, sehingga tipe T yang konkret (mis. String) ikut
// tersalin dan tidak pernah "dihapus" oleh JVM.

inline fun <reified T> isType(value: Any): Boolean {
    return value is T
}

fun main() {
    println(isType<String>("Hello"))   // harus mencetak: true
    println(isType<Int>("Hello"))      // harus mencetak: false
    println(isType<String>(123))       // harus mencetak: false

    // Jawaban diskusi:
    // Tanpa inline + reified, T hanya ada di level source code. Saat dikompilasi
    // ke bytecode JVM, T dihapus (type erasure) dan diganti jadi Object, jadi
    // runtime tidak tahu T itu String atau Int -> `value is T` tidak bisa dicek
    // dan compiler menolaknya ("Cannot check for instance of erased type: T").
    // Dengan inline, isi fungsi disalin ke setiap tempat pemanggilan, dan reified
    // membuat T diganti tipe konkretnya (mis. String) di salinan itu. Hasilnya
    // bytecode berisi `instanceof String` biasa, jadi pengecekan bisa jalan.
}
