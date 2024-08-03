package logica;

import java.sql.Date;
import java.sql.Timestamp;

public class Filter {

    private Integer idUitlenen;
    private Timestamp datum;
    private Date tijdstip;
    private Integer ontleendAanId;
    private int ontleendDoorId;
    private int componentenId;
    private int aantalComponenten;
    private String voornaam;
    private String familienaam;
    private Long nummer;
    private int aantal_terugbrengingen;

    public Filter(Integer idUitlenen, Timestamp datum, Date tijdstip, Integer ontleendAanId, int ontleendDoorId,
                  int componentenId, int aantalComponenten,
                  String voornaam, String familienaam, Long nummer, int aantal_terugbrengingen) {
        this.idUitlenen = idUitlenen;
        this.datum = datum;
        this.tijdstip = tijdstip;
        this.ontleendAanId = ontleendAanId;
        this.ontleendDoorId = ontleendDoorId;

        this.componentenId = componentenId;
        this.aantalComponenten = aantalComponenten;
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.nummer = nummer;
        this.aantal_terugbrengingen = aantal_terugbrengingen;
    }

    public Integer getIdUitlenen() {
        return idUitlenen;
    }

    public Timestamp getDatum() {
        return datum;
    }

    public Date getTijdstip() {
        return tijdstip;
    }

    public Integer getOntleendAanId() {
        return ontleendAanId;
    }

    public int getOntleendDoorId() {
        return ontleendDoorId;
    }

    public int getComponentenId() {
        return componentenId;
    }

    public int getAantalComponenten() {
        return aantalComponenten;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public Long getNummer() {
        return nummer;
    }

    public int getAantal_terugbrengingen() {
        return aantal_terugbrengingen;
    }

}
