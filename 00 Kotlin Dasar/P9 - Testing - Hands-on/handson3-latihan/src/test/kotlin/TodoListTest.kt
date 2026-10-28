import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

// Hands-on 3: Test Lifecycle & Setup dengan @BeforeEach
// setUp() dijalankan ulang sebelum SETIAP test, jadi tiap test dapat
// TodoList baru yang kosong dan tidak saling memengaruhi.

class TodoListTest {

    private lateinit var todoList: TodoList

    @BeforeEach
    fun setUp() {
        todoList = TodoList()
    }

    @Test
    @DisplayName("TodoList baru harus kosong")
    fun testNewListIsEmpty() {
        assertTrue(todoList.isEmpty())
    }

    @Test
    @DisplayName("Menambah item menaikkan ukuran list")
    fun testAddIncreasesSize() {
        // Act
        todoList.add("Belajar Kotlin")

        // Assert
        assertEquals(1, todoList.size())
    }

    @Test
    @DisplayName("Menghapus item mengembalikan true dan mengurangi ukuran")
    fun testRemoveExistingItem() {
        // Arrange
        todoList.add("Belajar Kotlin")

        // Act
        val removed = todoList.remove("Belajar Kotlin")

        // Assert
        assertTrue(removed)
        assertEquals(0, todoList.size())
    }
}
