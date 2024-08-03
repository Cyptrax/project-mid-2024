package logica;

public class Ic extends Componenten{
    double voedingsspanning;
    int aantalpinnen;
    String serieaanduiding;
    String typeaanduiding;

    public Ic(String producent, Double eenheidsprijs, int voorraad_aantal, double voedingsspanning, int aantalpinnen, String serieaanduiding, String typeaanduiding) {
        super(producent, eenheidsprijs, voorraad_aantal);
        this.voedingsspanning = voedingsspanning;
        this.aantalpinnen = aantalpinnen;
        this.serieaanduiding = serieaanduiding;
        this.typeaanduiding = typeaanduiding;
    }

    public Ic(int id, String producent, Double eenheidsprijs, int voorraad_aantal, double voedingsspanning, int aantalpinnen, String serieaanduiding, String typeaanduiding) {
        super(id, producent, eenheidsprijs, voorraad_aantal);
        this.voedingsspanning = voedingsspanning;
        this.aantalpinnen = aantalpinnen;
        this.serieaanduiding = serieaanduiding;
        this.typeaanduiding = typeaanduiding;
    }

    public double getVoedingsspanning() {
        return voedingsspanning;
    }

    public int getAantalpinnen() {
        return aantalpinnen;
    }

    public String getSerieaanduiding() {
        return serieaanduiding;
    }

    public String getTypeaanduiding() {
        return typeaanduiding;
    }
}
