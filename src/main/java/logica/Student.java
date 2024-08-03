package logica;

public class Student extends Personen {

    private int id;

    public Student(String voornaam, String familienaam, long nummer) {
        super( voornaam, familienaam, nummer);
    }
    public Student(int id, String voornaam, String familienaam, long nummer) {
        super(id, voornaam, familienaam, nummer);
    }
}
