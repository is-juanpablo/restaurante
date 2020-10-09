package models;

/**
 *
 * @author Juan Pablo
 */
public class Balance {

    private long balance;
    private int objetivo;
    private String dia;

    public Balance(long balance, int objetivo, String dia) {
        this.balance = balance;
        this.objetivo = objetivo;
        this.dia = dia;
    }

    public Balance() {
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public int getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(int objetivo) {
        this.objetivo = objetivo;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

}
