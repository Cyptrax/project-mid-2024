package logica;

public class SpecialeComponenten extends Componenten {
    String naam;
    String beschrijving;

    public SpecialeComponenten(String producent, Double eenheidsprijs, int voorraad_aantal, String naam, String beschrijving) {
        super(producent, eenheidsprijs, voorraad_aantal);
        this.naam = naam;
        this.beschrijving = beschrijving;
    }

    public SpecialeComponenten(int id, String producent, Double eenheidsprijs, int voorraad_aantal, String naam, String beschrijving) {
        super(id, producent, eenheidsprijs, voorraad_aantal);
        this.naam = naam;
        this.beschrijving = beschrijving;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }
}
