import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

// Hands-on 1: Race Condition
// Tugas: Dua thread meng-increment sebuah shared counter (var c) sebanyak
// 100_000x masing-masing secara BERSAMAAN. Karena c++ bukan operasi atomik
// (baca-ubah-tulis, bisa saling menyela/interleave antar thread), hasil akhirnya
// SERING SALAH (bukan 200_000) — itulah race condition.
//
// Solusi: pakai AtomicInteger supaya increment jadi operasi atomik.

class Counter {
    private val c = AtomicInteger(0)

    fun increment() {
        c.incrementAndGet()
    }

    fun value(): Int {
        return c.get()
    }
}

fun main() {
    val counter = Counter()
    val iterasi = 100_000

    val t1 = thread {
        repeat(iterasi) { counter.increment() }
    }
    val t2 = thread {
        repeat(iterasi) { counter.increment() }
    }

    t1.join()
    t2.join()

    println("Hasil akhir: ${counter.value()} (seharusnya ${iterasi * 2})")
}
