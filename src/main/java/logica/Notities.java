package logica;

import java.sql.Date;
import java.sql.Timestamp;

public class Notities {
    private int id;
    private String note;
    private Timestamp timestamp;
    private int lendingId;

    public Notities( String note, Timestamp timestamp, int lendingId) {
        this.note = note;
        this.timestamp = timestamp;
        this.lendingId = lendingId;
    }

    public String getNote() {
        return note;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getLendingId() {
        return lendingId;
    }
}
