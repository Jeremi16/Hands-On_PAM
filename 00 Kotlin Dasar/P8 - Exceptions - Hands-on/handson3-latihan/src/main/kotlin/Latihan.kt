// Hands-on 3: Kotlin Sugar - try sebagai expression, require(), error(), multi-catch
// Tugas: Buat parser tiket sederhana dari format teks "nama/umur",
// misalnya "Budi/25", menggunakan gaya penanganan error khas Kotlin.

data class Tiket(val nama: String, val umur: Int)

fun parseTiket(input: String): Tiket {
    val bagian = input.split("/")

    require(bagian.size == 2) { "Format harus 'nama/umur', diterima: $input" }

    val nama = bagian[0]

    // try dipakai sebagai expression, hasilnya langsung masuk ke umur
    val umur: Int = try {
        bagian[1].toInt()
    } catch (e: NumberFormatException) {
        error("Umur tidak valid: ${bagian[1]}")
    }

    require(umur >= 0) { "Umur tidak boleh negatif: $umur" }
    require(nama.isNotBlank()) { "Nama tidak boleh kosong, diterima: $input" }

    return Tiket(nama, umur)
}

fun main() {
    val inputs = listOf("Budi/25", "Siti/17", "format-salah", "Andi/bukan-angka", "/30")

    for (input in inputs) {
        try {
            println(parseTiket(input))
        } catch (e: IllegalArgumentException) {
            println("Input salah -> ${e.message}")
        } catch (e: IllegalStateException) {
            println("Data tidak valid -> ${e.message}")
        }
    }
}
