package Library;

public class User {

    private int id;
    private String name;
    private String email;
    private boolean blocked;
    private double debt;

    public User(int id, String name, String email, boolean blocked, double debt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.blocked = blocked;
        this.debt = debt;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isBlocked() { return blocked; }
    public double getDebt() { return debt; }

    public void addDebt(double amount) {
        this.debt += amount;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return name + " | dluh: " + debt + " Kč | blokován: " + blocked;
    }
}