package logica;

public class Weerstanden extends Componenten {

    private double weerstandwaarde;
    private double tolerantie;
    private int banden;
    private Double maximaalvermogen;

    public Weerstanden(String producent, Double eenheidsprijs, int voorraad_aantal, double weerstandwaarde, double tolerantie, int banden, Double maximaalvermogen) {
        super(producent, eenheidsprijs, voorraad_aantal);
        this.weerstandwaarde = weerstandwaarde;
        this.tolerantie = tolerantie;
        this.banden = banden;
        this.maximaalvermogen = maximaalvermogen;
    }

    public Weerstanden(int id, String producent, Double eenheidsprijs, int voorraad_aantal, double weerstandwaarde, double tolerantie, int banden, Double maximaalvermogen) {
        super(id, producent, eenheidsprijs, voorraad_aantal);
        this.weerstandwaarde = weerstandwaarde;
        this.tolerantie = tolerantie;
        this.banden = banden;
        this.maximaalvermogen = maximaalvermogen;
    }

    public double getWeerstandwaarde() {
        return weerstandwaarde;
    }

    public double getTolerantie() {
        return tolerantie;
    }

    public int getBanden() {
        return banden;
    }

    public double getMaximaalvermogen() {
        return maximaalvermogen;
    }
}
