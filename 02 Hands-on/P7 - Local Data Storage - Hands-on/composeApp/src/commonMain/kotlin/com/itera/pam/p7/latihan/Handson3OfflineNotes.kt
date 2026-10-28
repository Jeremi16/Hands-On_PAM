package com.itera.pam.p7.latihan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.itera.pam.p7.db.AppDatabase

// Hands-on 3: Offline-First — Tampilkan & Hapus Catatan
// Pakai CatatanRepository dari Handson2Repository.kt untuk menampilkan daftar
// catatan dalam LazyColumn, form untuk menambah catatan baru, dan tombol hapus
// per item. Data tersimpan di SQLite, jadi tetap ada setelah aplikasi ditutup.

@Composable
fun Handson3Screen(database: AppDatabase) {
    val repository = remember { CatatanRepository(database) }
    val catatanList by repository.getAll().collectAsState(initial = emptyList())

    var judul by remember { mutableStateOf("") }
    var isi by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Hands-on 3: Offline Notes", fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = judul,
            onValueChange = { judul = it },
            label = { Text("Judul") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = isi,
            onValueChange = { isi = it },
            label = { Text("Isi") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                repository.tambah(judul, isi)
                judul = ""
                isi = ""
            },
            enabled = judul.isNotBlank()
        ) {
            Text("Tambah")
        }

        LazyColumn {
            items(catatanList, key = { it.id }) { catatan ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(catatan.judul, fontWeight = FontWeight.Bold)
                            Text(catatan.isi)
                        }
                        Button(onClick = { repository.hapus(catatan.id) }) {
                            Text("Hapus")
                        }
                    }
                }
            }
        }
    }
}
