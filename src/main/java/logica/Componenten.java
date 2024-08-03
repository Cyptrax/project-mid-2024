package logica;

public class Componenten {
    private int id;
    private String producent;
    private double eenheidsprijs;
    private Integer voorraad_aantal;

    public Componenten(String producent, Double eenheidsprijs, Integer voorraad_aantal) {
        this.producent = producent;
        this.eenheidsprijs = eenheidsprijs;
        this.voorraad_aantal = voorraad_aantal;
    }

    public Componenten(int id, String producent, Double eenheidsprijs, Integer voorraad_aantal) {
        this.id = id;
        this.producent = producent;
        this.eenheidsprijs = eenheidsprijs;
        this.voorraad_aantal = voorraad_aantal;
    }

    public int getId() {
        return id;
    }

    public String getProducent()  {
        return producent;
    }

    public double getEenheidsprijs() {
        return eenheidsprijs;
    }

    public int getVoorraad_aantal() {
        return voorraad_aantal;
    }

}
