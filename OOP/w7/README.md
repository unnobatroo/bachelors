### Java keywords

| Keyword | Category | What it does | Real-World Analogy      |
| :--- | :--- | :--- |:------------------------|
| `public` | Access | Visible to **everyone** in the project. | A public park.          |
| `private` | Access | Visible **only** within the same class. | Your personal diary.    |
| `protected` | Access | Visible to the package and **subclasses**. | A family heirloom.      |
| `static` | Behavior | Belongs to the **class itself**, not an object. | The name of the school. |
| `final` | Behavior | **Cannot be changed** (constant or un-inheritable). | A birth date.           |

```java
public class BankAccount {
    // PRIVATE: Only this class can touch the balance. 
    // This prevents "hacking" or accidental bugs.
    private double balance;

    // STATIC & FINAL: Every account shares this interest rate, 
    // and 'final' means it cannot be modified later.
    public static final double INTEREST_RATE = 0.02;

    // PUBLIC: Anyone can call this method to check their own funds.
    public double getBalance() {
        return this.balance;
    }

    // PROTECTED: Only this bank or specialized "Premium" 
    // subclasses can apply internal fees.
    protected void applyInternalFee(double fee) {
        this.balance -= fee;
    }
}
```