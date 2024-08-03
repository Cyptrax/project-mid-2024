package data;

import logica.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class Datalaag {
    Random random = new Random();
    private final String dbUrl = "jdbc:mysql://localhost:3306/";
    private final String login = "eloict";
    private final String pass = "Azerty123!";
    private final Connection con;
    private ArrayList<String> colorCode = new ArrayList<>();
    private HashMap<String, Color> colorMap = new HashMap<>();

    public Datalaag(String dbName) throws SQLException {
        this.con = getConnection(dbName);
        this.con.setAutoCommit(false);
    }

    private Connection getConnection(String dbName) throws SQLException {
        Connection conn = DriverManager.getConnection(dbUrl + dbName + "?serverTimezone=UTC&allowMultiQueries=true", login, pass);
        return conn;
    }

    public void closeConnection() throws SQLException {
        this.con.close();
    }

    // --------------------------------- GEEFPERSONEN ------------------------------------------------ //

    public List<Personen> geefPersonen() throws SQLException {
        Statement stmt = null;
        List<Personen> personen = new ArrayList<Personen>();
        String querry = "select * from personen";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(querry);
            while (rs.next()) {
                int id = rs.getInt("idpersonen");
                String voornaam = rs.getString("voornaam");
                String familienaam = rs.getString("familienaam");
                Long nummer = rs.getLong("nummer");
                Personen persoon = new Personen(id, voornaam, familienaam, nummer);
                personen.add(persoon);
            }

        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return personen;
    }

    public List<Docent> geefDocent() throws SQLException {
        Statement stmt = null;
        List<Docent> docenten = new ArrayList<>();
        String querry = "select * from docenten INNER JOIN personen on idpersonen = docenten_id";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(querry);
            while (rs.next()) {
                int id = rs.getInt("docenten_id");
//                System.out.println(id);
                String voornaam = rs.getString("voornaam");
                String familienaam = rs.getString("familienaam");
                Long nummer = rs.getLong("nummer");
                String vak = rs.getString("vakgebied");
                Date begin = rs.getDate("beginjaar");
                Docent docent = new Docent(id, voornaam, familienaam, nummer, vak, begin);
//                System.out.println(docent.getId());
                docenten.add(docent);
            }

        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return docenten;
    }

    public long getNummmers() throws SQLException{
        Statement stmt = null;
        long randomNumber = 0;
        int count = 0;
        List<Long> personen = new ArrayList<>();
        String querry = "select nummer from personen";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(querry);
            while (rs.next()) {
                personen.add(rs.getLong(1));
            }
            do {
                long min = 1000000000L;
                long max = 9999999999L;
                randomNumber = min + (long)(random.nextDouble() * (max - min + 1));

            } while (personen.contains(randomNumber));

        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return randomNumber;
    }

    // --------------------------------- MAAKDOCENTEN ------------------------------------------------ //

    public int maakDocentAan(Docent D) throws SQLException {
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        PreparedStatement docentStmt = null;
        ResultSet rs = null;
        int docentId = -1;

        try {
            String checkQuery = "SELECT idpersonen FROM personen WHERE voornaam = ? AND familienaam = ? AND nummer = ?";
            checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setString(1, D.getVoornaam());
            checkStmt.setString(2, D.getfamilienaam());
            checkStmt.setLong(3, D.getNummer());

            rs = checkStmt.executeQuery();

            if (rs.next()) {
                docentId = rs.getInt("idpersonen");
            } else {
                String insertQuery = "INSERT INTO personen (voornaam, familienaam, nummer) VALUES (?, ?, ?)";
                String insertQuery2 = "INSERT INTO docenten (docenten_id, vakgebied, beginjaar) VALUES (?, ?, ?)";
                insertStmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setString(1, D.getVoornaam());
                insertStmt.setString(2, D.getfamilienaam());
                insertStmt.setLong(3, D.getNummer());
                int affectedRows = insertStmt.executeUpdate();

                if (affectedRows > 0) {
                    rs = insertStmt.getGeneratedKeys();
                    if (rs.next()) {
                        docentId = rs.getInt(1);

                        try {
                            docentStmt = con.prepareStatement(insertQuery2);
                            docentStmt.setInt(1, docentId);
                            docentStmt.setString(2, D.getVakgebied());
                            docentStmt.setDate(3, new Date(D.getBeginjaar().getTime()));
                            docentStmt.executeUpdate();
                        } catch (SQLException e) {
                            if (con != null) {
                                con.rollback();
                            }
                            throw new RuntimeException(e);
                        }

                    } else {
                        if (con != null) {
                            con.rollback();
                        }
                    }
                }
                con.commit();
            }

        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (checkStmt != null) {
                checkStmt.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (docentStmt != null) {
                docentStmt.close();
            }
        }
        return docentId;
    }

    // --------------------------------- MAAKSTUDENTENAAN ------------------------------------------------ //

    public int maakStudentAan(Student S) throws SQLException {
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        PreparedStatement docentStmt = null;
        ResultSet rs = null;
        int studentenId = -1;

        try {
            String checkQuery = "SELECT idpersonen FROM personen WHERE voornaam = ? AND familienaam = ? AND nummer = ?";
            checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setString(1, S.getVoornaam());
            checkStmt.setString(2, S.getfamilienaam());
            checkStmt.setLong(3, S.getNummer());

            rs = checkStmt.executeQuery();

            if (rs.next()) {
                studentenId = rs.getInt("idpersonen");
            } else {
                String insertQuery = "INSERT INTO personen (voornaam, familienaam, nummer) VALUES (?, ?, ?)";
                String insertQuery2 = "INSERT INTO studenten (studenten_id) VALUES (?)";
                insertStmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setString(1, S.getVoornaam());
                insertStmt.setString(2, S.getfamilienaam());
                insertStmt.setLong(3, S.getNummer());
                int affectedRows = insertStmt.executeUpdate();

                if (affectedRows > 0) {
                    rs = insertStmt.getGeneratedKeys();
                    if (rs.next()) {
                        studentenId = rs.getInt(1);

                        try {
                            docentStmt = con.prepareStatement(insertQuery2);
                            docentStmt.setInt(1, studentenId);
                            docentStmt.executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        if (con != null) {
                            con.rollback();
                        }
                    }
                }
                con.commit();
            }

        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (checkStmt != null) {
                checkStmt.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (docentStmt != null) {
                docentStmt.close();
            }
        }
        return studentenId;
    }

    // --------------------------------- COMPONENTEN LOADER ------------------------------------------------ //

    public String loadComponentsFromCSV(String filePath) {
        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
        String line;
        String[] values = new String[0];

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            line = br.readLine();
            values = line.split(";");
            for (String s : values) {
                if (s.equals("weerstandswaarde") || s.equals("tolerantie") || s.equals("aantal_banden") || s.equals("vermogen")) {
//                    System.out.println("TRUE");
                    int producentIndex = -1;
                    int eenheidsprijsIndex = -1;
                    int voorraadIndex = -1;
                    int weerstandswaardeIndex = -1;
                    int tolerantieIndex = -1;
                    int bandenIndex = -1;
                    int vermogenIndex = -1;
                    for (int i = 0; i < values.length; i++) {
                        switch (values[i]) {
                            case "producent":
                                producentIndex = i;
                                break;
                            case "eenheidsprijs":
                                eenheidsprijsIndex = i;
                                break;
                            case "voorraad_aantal":
                                voorraadIndex = i;
                                break;
                            case "weerstandswaarde":
                                weerstandswaardeIndex = i;
                                break;
                            case "tolerantie":
                                tolerantieIndex = i;
                                break;
                            case "aantal_banden":
                                bandenIndex = i;
                                break;
                            case "vermogen":
                                vermogenIndex = i;
                                break;
                        }
                    }

                    while ((line = br.readLine()) != null) {
                        String producent;
                        Double eenheid;
                        Double vermogen;
                        String[] value = line.split(";");
                            if (value[producentIndex] == "") {
                                producent = null;
                            } else {
                                producent = value[producentIndex];
                            }
                            if (value[eenheidsprijsIndex] == "") {
                                eenheid = null;
                            } else {
                                eenheid = Double.valueOf(value[eenheidsprijsIndex]);
                            }
                            if (value[vermogenIndex] == "") {
                                vermogen = null;
                            } else {
                                vermogen = Double.parseDouble(value[vermogenIndex]);
                            }
//                        System.out.println(producent);
//                        System.out.println(eenheid);
//                        System.out.println(Integer.parseInt(value[voorraadIndex]));
//                        System.out.println(Double.parseDouble(value[weerstandswaardeIndex]));
//                        System.out.println(Double.parseDouble(value[tolerantieIndex]));
//                        System.out.println(Integer.parseInt(value[bandenIndex]));
//                        System.out.println(vermogen);
                        Weerstanden w = new Weerstanden(producent, eenheid, Integer.parseInt(value[voorraadIndex]), Double.parseDouble(value[weerstandswaardeIndex]), Double.parseDouble(value[tolerantieIndex]), Integer.parseInt(value[bandenIndex]), vermogen);
                        this.weerstandenComponentenCSV(w);
                    }
                    break;
                } else if (s.equals("serie") || s.equals("type") || s.equals("aantal_pinnen") || s.equals("vcc")) {
//                    System.out.println("TRUE");
                    int producentIndex = -1;
                    int eenheidsprijsIndex = -1;
                    int voorraadIndex = -1;
                    int serieIndex = -1;
                    int typeIndex = -1;
                    int aantal_pinnenIndex = -1;
                    int vccIndex = -1;
                    for (int i = 0; i < values.length; i++) {
                        switch (values[i]) {
                            case "producent":
                                producentIndex = i;
                                break;
                            case "eenheidsprijs":
                                eenheidsprijsIndex = i;
                                break;
                            case "voorraad_aantal":
                                voorraadIndex = i;
                                break;
                            case "serie":
                                serieIndex = i;
                                break;
                            case "type":
                                typeIndex = i;
                                break;
                            case "aantal_pinnen":
                                aantal_pinnenIndex = i;
                                break;
                            case "vcc":
                                vccIndex = i;
                                break;
                        }
                    }

                    while ((line = br.readLine()) != null) {
                        String producent;
                        Double eenheid;
                        String[] value = line.split(";");
                        if (value[producentIndex] == "") {
                            producent = null;
                        } else {
                            producent = value[producentIndex];
                        }
                        if (value[eenheidsprijsIndex] == "") {
                            eenheid = null;
                        } else {
                            eenheid = Double.valueOf(value[eenheidsprijsIndex]);
                        }
//                        System.out.println(producent);
//                        System.out.println(eenheid);
//                        System.out.println(Integer.parseInt(value[voorraadIndex]));
//                        System.out.println(value[serieIndex]);
//                        System.out.println(value[typeIndex]);
//                        System.out.println(Integer.parseInt(value[aantal_pinnenIndex]));
//                        System.out.println(Double.parseDouble(value[vccIndex]));
                        Ic ic = new Ic(producent, eenheid, Integer.parseInt(value[voorraadIndex]), Double.parseDouble(value[vccIndex]), Integer.parseInt(value[aantal_pinnenIndex]), value[serieIndex], value[typeIndex]);
                        this.icsComponentenCSV(ic);
                    }
                    break;
                } else if (s.equals("naam") || s.equals("beschrijving")) {
//                    System.out.println("TRUE 3");
                    int producentIndex = -1;
                    int eenheidsprijsIndex = -1;
                    int voorraadIndex = -1;
                    int naamIndex = -1;
                    int beschrijvingIndex = -1;

                    for (int i = 0; i < values.length; i++) {
                        switch (values[i]) {
                            case "producent":
                                producentIndex = i;
                                break;
                            case "eenheidsprijs":
                                eenheidsprijsIndex = i;
                                break;
                            case "voorraad_aantal":
                                voorraadIndex = i;
                                break;
                            case "naam":
                                naamIndex = i;
                                break;
                            case "beschrijving":
                                beschrijvingIndex = i;
                                break;
                        }
                    }

                    while ((line = br.readLine()) != null) {
                        String producent;
                        Double eenheid;
                        String beschrijving;
                        String[] value = line.split(";");
                        if (producentIndex == -1) {
                            producent = null;
                        } else {
                            producent = value[producentIndex];
                        }
                        if (eenheidsprijsIndex == -1) {
                            eenheid = null;
                        } else {
                            eenheid = Double.valueOf(value[eenheidsprijsIndex]);
                        }
                        if (beschrijvingIndex == -1) {
                            beschrijving = null;
                        } else {
                            beschrijving = (value[beschrijvingIndex]);
                        }
//                        System.out.println(producent);
//                        System.out.println(eenheid);
//                        System.out.println(Integer.parseInt(value[voorraadIndex]));
//                        System.out.println(value[naamIndex]);
//                        System.out.println(beschrijving);

                        SpecialeComponenten special = new SpecialeComponenten(producent, eenheid, Integer.parseInt(value[voorraadIndex]), value[naamIndex], beschrijving);
                        this.specialeComponentenCSV(special);

                    }
                    break;
                }
            }




        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "test";
    }

    // --------------------------------- weerstanden LOADER ------------------------------------------------ //

    private void weerstandenComponentenCSV(Weerstanden W) throws SQLException {
        PreparedStatement checkStmt = null;
        PreparedStatement instStmt = null;
        String line;
        ResultSet rs = null;
        int componentid;
            String insertComponentSQL = "INSERT INTO componenten (producent, eenheidsprijs, voorraad_aantal) VALUES (?, ?, ?)";
            String insertIcSQL = "INSERT INTO weerstanden (componenten_idcomponenten, weerstandwaarde, tolerantie, banden, maximaalvermogen) VALUES (?, ?, ?, ?, ?)";

                try {
                    checkStmt = con.prepareStatement(insertComponentSQL, PreparedStatement.RETURN_GENERATED_KEYS);
                    checkStmt.setString(1, W.getProducent());
                    checkStmt.setDouble(2, W.getEenheidsprijs());
                    checkStmt.setDouble(3, W.getVoorraad_aantal());
                    int affectedRows = checkStmt.executeUpdate();

                    if (affectedRows > 0) {
                        rs = checkStmt.getGeneratedKeys();
                        if (rs.next()) {
                            componentid = rs.getInt(1);

                            try {
                                instStmt = con.prepareStatement(insertIcSQL);
                                instStmt.setInt(1, componentid);
                                instStmt.setDouble(2, W.getWeerstandwaarde());
                                instStmt.setDouble(3, W.getTolerantie());
                                instStmt.setInt(4, W.getBanden());
                                instStmt.setDouble(5, W.getMaximaalvermogen());
                                instStmt.executeUpdate();

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                    con.commit();
                } catch (Exception e) {
                    if (con != null) {
                        con.rollback();
                    }
                    throw new RuntimeException(e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (checkStmt != null) {
                        checkStmt.close();
                    }
                    if (instStmt != null) {
                        instStmt.close();
                    }
                }
    }

    // --------------------------------- ICS LOADER ------------------------------------------------ //

    private void icsComponentenCSV(Ic i) throws SQLException {
        PreparedStatement checkStmt = null;
        PreparedStatement instStmt = null;
        String line;
        ResultSet rs = null;
        int componentid;
        String insertComponentSQL = "INSERT INTO componenten (producent, eenheidsprijs, voorraad_aantal) VALUES (?, ?, ?)";
        String insertIcSQL = "INSERT INTO ics (componenten_idcomponenten, voedingsspanning, aantalpinnen, serieaanduiding, typeaanduiding) VALUES (?, ?, ?, ?, ?)";

        try {
            checkStmt = con.prepareStatement(insertComponentSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            checkStmt.setString(1, i.getProducent());
            checkStmt.setDouble(2, i.getEenheidsprijs());
            checkStmt.setDouble(3, i.getVoorraad_aantal());
            int affectedRows = checkStmt.executeUpdate();
            if (affectedRows > 0) {
                rs = checkStmt.getGeneratedKeys();
                if (rs.next()) {
                    componentid = rs.getInt(1);

                    try {
                        instStmt = con.prepareStatement(insertIcSQL);
                        instStmt.setInt(1, componentid);
                        instStmt.setDouble(2, i.getVoedingsspanning());
                        instStmt.setInt(3, i.getAantalpinnen());
                        instStmt.setString(4, i.getSerieaanduiding());
                        instStmt.setString(5, i.getTypeaanduiding());
                        instStmt.executeUpdate();

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (checkStmt != null) {
                checkStmt.close();
            }
            if (instStmt != null) {
                instStmt.close();
            }
        }
    }

    // --------------------------------- Speciale componenten LOADER ------------------------------------------------ //

    private void specialeComponentenCSV(SpecialeComponenten s) throws SQLException {
        PreparedStatement checkStmt = null;
        PreparedStatement instStmt = null;
        String line;
        ResultSet rs = null;
        int componentid;
        String insertComponentSQL = "INSERT INTO componenten (producent, eenheidsprijs, voorraad_aantal) VALUES (?, ?, ?)";
        String insertSpecialComponentSQL = "INSERT INTO specialecomponenten (componenten_idcomponenten, naam, beschrijving) VALUES (?, ?, ?)";

        try {
            checkStmt = con.prepareStatement(insertComponentSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            checkStmt.setString(1, s.getProducent());
            checkStmt.setDouble(2, s.getEenheidsprijs());
            checkStmt.setDouble(3, s.getVoorraad_aantal());
            int affectedRows = checkStmt.executeUpdate();
            if (affectedRows > 0) {
                rs = checkStmt.getGeneratedKeys();
                if (rs.next()) {
                    componentid = rs.getInt(1);

                    try {
                        instStmt = con.prepareStatement(insertSpecialComponentSQL);
                        instStmt.setInt(1, componentid);
                        instStmt.setString(2, s.getNaam());
                        instStmt.setString(3, s.getBeschrijving());
                        instStmt.executeUpdate();

                    } catch (SQLException e) {
                        if (con != null) {
                            con.rollback();
                        }
                        throw new RuntimeException(e);
                    }

                }
            }
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw new RuntimeException(e);
        } if (rs != null) {
            rs.close();
        }
        if (checkStmt != null) {
            checkStmt.close();
        }
        if (instStmt != null) {
            instStmt.close();
        }
    }

    // --------------------------------- GeefWeerstanden ------------------------------------------------ //

    public List<Weerstanden> geefWeerstanden() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<Weerstanden> weerstandenlijst = new ArrayList<>();
        String Querry = "SELECT * FROM mydb.weerstanden INNER JOIN componenten On componenten_idcomponenten = idcomponenten";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(Querry);
            while (rs.next()) {
                int id = rs.getInt("componenten_idcomponenten");
                double weerstandwaarde = rs.getDouble("weerstandwaarde");
                double tolerantie = rs.getDouble("tolerantie");
                int banden = rs.getInt("banden");
                Double maximaalvermogen = rs.getDouble("maximaalvermogen");
                String producent = rs.getString("producent");
                Double eenheidsprijs = rs.getDouble("eenheidsprijs");
                int voorraad = rs.getInt("voorraad_aantal");
                Weerstanden w = new Weerstanden(id, producent, eenheidsprijs, voorraad, weerstandwaarde, tolerantie, banden, maximaalvermogen);
                weerstandenlijst.add(w);
            }
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return weerstandenlijst;
    }

    // --------------------------------- Geef ics ------------------------------------------------ //

    public List<Ic> geefIcs() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<Ic> iclijst = new ArrayList<>();
        String Querry = "SELECT * FROM mydb.ics INNER JOIN componenten On componenten_idcomponenten = idcomponenten";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(Querry);
            while (rs.next()) {
                int id = rs.getInt("componenten_idcomponenten");
                double voedingsspanning = rs.getDouble("voedingsspanning");
                int aantalpinnen = rs.getInt("aantalpinnen");
                String serie = rs.getString("serieaanduiding");
                String type = rs.getString("typeaanduiding");
                String producent = rs.getString("producent");
                Double eenheidsprijs = rs.getDouble("eenheidsprijs");
                int voorraad = rs.getInt("voorraad_aantal");
                Ic ic = new Ic(id, producent, eenheidsprijs, voorraad, voedingsspanning, aantalpinnen, serie, type);
                iclijst.add(ic);
            }
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }

        }
        return iclijst;
    }

    // --------------------------------- Geef speciale componenten ------------------------------------------------ //

    public List<SpecialeComponenten> geefSpecialeComponenten() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<SpecialeComponenten> specialLijst = new ArrayList<>();
        String Querry = "SELECT * FROM mydb.specialecomponenten INNER JOIN componenten On componenten_idcomponenten = idcomponenten";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(Querry);
            while (rs.next()) {
                int id = rs.getInt("componenten_idcomponenten");
                String producent = rs.getString("producent");
                Double eenheidsprijs = rs.getDouble("eenheidsprijs");
                int voorraad = rs.getInt("voorraad_aantal");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");

                SpecialeComponenten special = new SpecialeComponenten(id, producent, eenheidsprijs, voorraad,naam, beschrijving);
                specialLijst.add(special);
            }
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }

        }
        return specialLijst;
    }

    // --------------------------------- Zoek aantal componenten via id ------------------------------------------------ //

    public Integer geefAantal(Integer id, Integer aantalwil) throws SQLException {
        PreparedStatement pstmt = null;
        Integer aantalindb = 0;
        Integer aantal = 0;
        String query = "SELECT voorraad_aantal FROM componenten WHERE idcomponenten = ?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                aantalindb = rs.getInt("voorraad_aantal");
                aantal = aantalindb - aantalwil;
            } else {
                aantalindb = 0;
            }
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw new SQLException(e);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
//        System.out.println(aantal);
        return aantal;
    }

    // --------------------------------- Aantal veranderen ------------------------------------------------ //

    public void changeAantal(Integer id, Integer aantalwil) throws SQLException {
        PreparedStatement pstmt = null;
        String query = "UPDATE componenten SET voorraad_aantal = ? WHERE idcomponenten = ?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, aantalwil);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw new SQLException(e);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }

    }

    // --------------------------------- Uitlenen Componenten ------------------------------------------------ //

    public Integer uitlenenComponenten(Uitlening u) throws SQLException {
        PreparedStatement checkStmt = null;
        PreparedStatement instStmt = null;
        String line;
        ResultSet rs = null;
        int uitleenId = -1;
        String insertComponentSQL = "INSERT INTO uitlenen (datum, tijdstip, ontleendaan_id, ontleenddoor_id) VALUES (?, ?, ?, ?)";
        String insertIcSQL = "INSERT INTO uitlenen_has_componenten (uitlenen_iduitlenen, componenten_idcomponenten, aantalcomponenten) VALUES (?, ?, ?)";

        try {
            checkStmt = con.prepareStatement(insertComponentSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            checkStmt.setTimestamp(1, u.getDatum());
            checkStmt.setDate(2, u.getTerugbrengdatum());
            checkStmt.setInt(3, u.getLenerId());
            checkStmt.setInt(4, u.getOntlenerId());
            int affectedRows = checkStmt.executeUpdate();

            if (affectedRows > 0) {
                rs = checkStmt.getGeneratedKeys();
                if (rs.next()) {
                    uitleenId = rs.getInt(1);

                    try {
                        ArrayList<Integer> componentenLijst = u.getComponentsid();
                        ArrayList<Integer> aantalLijst = u.getAantal();
                        for (int i = 0; i < componentenLijst.toArray().length; i++) {
                            instStmt = con.prepareStatement(insertIcSQL);
                            this.changeAantal(componentenLijst.get(i), this.geefAantal(componentenLijst.get(i),aantalLijst.get(i)));
                            instStmt.setInt(1, uitleenId);
                            instStmt.setInt(2, componentenLijst.get(i));
                            instStmt.setInt(3, aantalLijst.get(i));
                            instStmt.executeUpdate();
                        }

                    } catch (SQLException e) {
                        if (con != null) {
                            con.rollback();
                        }
                        throw new RuntimeException(e);
                    }

                }
            }
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (instStmt != null) {
                instStmt.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (checkStmt != null) {
                checkStmt.close();
            }

        }
        return uitleenId;
    }

    // --------------------------------- Notities aanmaken ------------------------------------------------ //

    public void addNotitie(Notities n) throws SQLException {
        PreparedStatement stmt = null;
        String querry = "INSERT INTO notities (notities, tijdstip, uitlenen_iduitlenen) VALUES (?, ?, ?)";

        try {
            stmt = con.prepareStatement(querry);
            stmt.setString(1, n.getNote());
            stmt.setTimestamp(2, n.getTimestamp());
            stmt.setInt(3, n.getLendingId());
            stmt.executeUpdate();
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    // --------------------------------- filteren op uitlener ------------------------------------------------ //

    public List<Filter> filterenUitlenen(int filter, int id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Filter> filterlijst = new ArrayList<>();

        String querry1 = "select * from mydb.uitlenen \n" +
                "INNER JOIN docenten on ontleenddoor_id = docenten_id \n" +
                "INNER JOIN uitlenen_has_componenten on uitlenen_iduitlenen = iduitlenen\n" +
                "INNER JOIN personen on docenten_id = idpersonen\n" +
                "WHERE ontleenddoor_id = ?\n" +
                "ORDER BY docenten_id;";

        String querry2 = "select * from mydb.uitlenen \n" +
                "INNER JOIN studenten on ontleendaan_id = studenten_id \n" +
                "INNER JOIN uitlenen_has_componenten on uitlenen_iduitlenen = iduitlenen\n" +
                "INNER JOIN personen on studenten_id = idpersonen\n" +
                "WHERE ontleendaan_id = ? \n" +
                "ORDER BY studenten_id;";

        try {
            if (filter == 1) {
                pstmt = con.prepareStatement(querry1);
//                System.out.println(1);
            } else if (filter == 2) {
                pstmt = con.prepareStatement(querry2);
//                System.out.println(2);
            }

            if (pstmt != null) {
                pstmt.setInt(1, id);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    Integer idUitlenen = rs.getInt("iduitlenen");
                    Timestamp datum = rs.getTimestamp("datum");
                    Date tijdstip = rs.getDate("tijdstip");
                    Integer ontleendAanId = rs.getInt("ontleendaan_id");
                    int ontleendDoorId = rs.getInt("ontleenddoor_id");
                    int componentenId = rs.getInt("componenten_idcomponenten");
                    int aantalComponenten = rs.getInt("aantalcomponenten");
                    String voornaam = rs.getString("voornaam");
                    String familienaam = rs.getString("familienaam");
                    Long nummer = rs.getLong("nummer");
                    int terugbreng = rs.getInt("aantal_terugbrengingen");

                    Filter f = new Filter(idUitlenen, datum, tijdstip, ontleendAanId, ontleendDoorId,
                            componentenId, aantalComponenten, voornaam, familienaam, nummer, terugbreng);

                    filterlijst.add(f);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return filterlijst;
    }

    // --------------------------------- TerugBrengen Componenten ------------------------------------------------ //

    public void brengComponentenTerug(int uitlenenId, int componentenId, int aantal, Timestamp datum) throws SQLException {
        PreparedStatement stmt = null;
        String querry = "UPDATE uitlenen_has_componenten\n" +
                "SET aantal_terugbrengingen = ?, terugbrengdatum = ?\n" +
                "WHERE uitlenen_iduitlenen = ? AND componenten_idcomponenten = ?";

        try {
            stmt = con.prepareStatement(querry);
            stmt.setInt(1, aantal);
            stmt.setTimestamp(2, datum);
            stmt.setInt(3, uitlenenId);
            stmt.setInt(4, componentenId);
            stmt.executeUpdate();
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public ArrayList<String> wwToKleur(double ww, double tol) {
        this.setuparraylist();
        long integerPart = (long) ww;
        int count = 0;
        ArrayList<String> kleuren = new ArrayList<>();
        double decimalPart = ww - integerPart;
        String wwstring = String.valueOf((integerPart));
        for (int i = 0; i < wwstring.length(); i++) {
            int cijfer = wwstring.charAt(i) - 48;
//            System.out.println(cijfer);
            if (cijfer == 0) {
                break;
            }
            kleuren.add(getKeyByValue(wwstring.charAt(i) - 48));

        }

        if (ww > 0 && ww < 10 && decimalPart == 0) {
            kleuren.add(0, "black");
            kleuren.add("black");
        }

        if (wwstring.length() == 2) {
            kleuren.add("black");
        }

        if (decimalPart * 10 > 0) {
            kleuren.add(getKeyByValue((int) (decimalPart*10)));
        }

        if (integerPart > 0 && integerPart < 10 && decimalPart * 10 > 0 && decimalPart * 10 < 10) {
            kleuren.add("gold");
        }

        for(int i = 2; i < wwstring.length(); i++) {
            int cijfer = wwstring.charAt(i) - 48;
//            System.out.println(cijfer);
            if(cijfer == 0)
                count++;
        }

        if (count > 0) {
            kleuren.add(getKeyByValue(count));
        }

        if (kleuren.size() == 2) {
            kleuren.add(1, "black");
        }

//        System.out.println(count);

        int toldec = (int) (tol * 100);

        switch (toldec) {
            case 100:
                kleuren.add("brown");
                break;
            case 200:
                kleuren.add("red");
                break;
            case 50:
                kleuren.add("green");
                break;
            case 25:
                kleuren.add("blue");
                break;
            case 10:
                kleuren.add("violet");
                break;
            case 5:
                kleuren.add("orange");
                break;
            case 500:
                kleuren.add("gold");
                break;
            case 1000:
                kleuren.add("silver");
                break;
            case 1:
                kleuren.add("gray");
                break;

        }

//        for (String s: kleuren) {
//            System.out.println(s);
//        }

        return kleuren;
    }

    public String getKeyByValue(int value) {
        return colorCode.get(value);
    }

    public void setuparraylist() {
        colorCode.add("black");
        colorCode.add("brown");
        colorCode.add("red");
        colorCode.add("orange");
        colorCode.add("yellow");
        colorCode.add("green");
        colorCode.add("blue");
        colorCode.add("purple");
        colorCode.add("gray");
        colorCode.add("white");
    }

    public Color getColorByName(String colorName) {
        switch (colorName.toLowerCase()) {
            case "black": return Color.BLACK;
            case "brown": return new Color(139, 69, 19); // Brown color
            case "red": return Color.RED;
            case "orange": return Color.ORANGE;
            case "yellow": return Color.YELLOW;
            case "green": return Color.GREEN;
            case "blue": return Color.BLUE;
            case "purple": return new Color(128, 0, 128); // Purple color
            case "gray": return Color.GRAY;
            case "white": return Color.WHITE;
            case "gold": return new Color(255, 215, 0); // Gold color
            case "silver": return new Color(192, 192, 192); // Silver color
            default: return Color.BLACK; // Default color
        }
    }

    // --------------------------------- MAIN TESTER ------------------------------------------------ //

    public static void main(String[] args) {
        try {
            Datalaag datalaag = new Datalaag("mydb");
//            LocalDate startjaar = LocalDate.of(2005, 6, 21);
//            long nummer = datalaag.getNummmers();
//            Docent doc = new Docent("Bart", "degeest", nummer , "ICT", new Date(2005-06-21));
//            datalaag.maakDocentAan(doc);
//            nummer = datalaag.getNummmers();
//            Student stud = new Student("Nathan", "Hommez", nummer);
//            datalaag.maakStudentAan(stud);
//            List<Personen> personenlijst = datalaag.geefPersonen();
//            for (Personen p: personenlijst) {
//                System.out.println(p.getfamilienaam() + p.getVoornaam());
//            }
//            System.out.println(nummer);
//            datalaag.loadComponentsFromCSV("C:\\Odisee\\DB-management\\2324-db-project\\src\\main\\resources\\files\\weerstanden.csv");
//            ArrayList<Integer> compId = new ArrayList<>();
//            compId.add(1);
//            compId.add(2);
//            ArrayList<Integer> aantal = new ArrayList<>();
//            aantal.add(2);
//            aantal.add(2);
//            LocalDateTime now = LocalDateTime.now();
//            Timestamp timestamp = Timestamp.valueOf(now);
//            System.out.println(timestamp);
//            Date date = null;
//            date = Date.valueOf(("2024-06-21"));
//            System.out.println(date);
//            Uitlening u = new Uitlening(timestamp, date, 2, 1, compId, aantal);
//            datalaag.uitlenenComponenten(u);
//            System.out.println(datalaag.geefAantal(1, 35));
//            datalaag.filterenUitlenen(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
