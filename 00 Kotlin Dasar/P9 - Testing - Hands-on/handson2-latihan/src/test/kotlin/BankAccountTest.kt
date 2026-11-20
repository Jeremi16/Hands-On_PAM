import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

// Hands-on 2: Testing Exception
// Konsep: assertThrows<TipeException> { ... } dipakai untuk memastikan
// sebuah blok kode MELEMPAR exception tertentu.

class BankAccountTest {

    @Test
    @DisplayName("Deposit menambah saldo dengan benar")
    fun testDeposit() {
        // Arrange
        val account = BankAccount(100.0)

        // Act
        account.deposit(50.0)

        // Assert
        assertEquals(150.0, account.balance)
    }

    @Test
    @DisplayName("Withdraw mengurangi saldo saat saldo cukup")
    fun testWithdrawSufficientBalance() {
        // Arrange
        val account = BankAccount(100.0)

        // Act
        account.withdraw(40.0)

        // Assert
        assertEquals(60.0, account.balance)
    }

    @Test
    @DisplayName("Withdraw melempar InsufficientFundsException saat saldo tidak cukup")
    fun testWithdrawInsufficientBalance() {
        // Arrange
        val account = BankAccount(50.0)

        // Act + Assert
        assertThrows<InsufficientFundsException> {
            account.withdraw(100.0)
        }
        // saldo tidak boleh berubah kalau gagal
        assertEquals(50.0, account.balance)
    }
}
