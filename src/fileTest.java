import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class fileTest {

    private static final String TEST_FILE = "data/test_pegawai.txt";

    @Before
    public void setup() throws IOException {
        File file = new File(TEST_FILE);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE))) {
            writer.write("77311552;Wiwid Kusuma;20;Laki-laki;081132451337;wiwid@gmail.com;JL Karyawiguna no. 420;Marketing;96;8000000.0;images/wiwid.jpg\n");
            writer.write("77311551;Farhan Kurnia;20;Laki-laki;081132451337;farhan@gmail.com;JL Karyawiguna no. 69;Quality Assurance;96;1.0E7;images/farhan.jpg\n");
        }
    }

    @After
    public void hapus() {
        new File(TEST_FILE).delete();
    }

    @Test
    public void testLoadPegawai() {
        List<Pegawai> pegawaiList = FileHandler.loadPegawai(TEST_FILE);

        assertNotNull("Pegawai list should not be null", pegawaiList);
        assertEquals("Pegawai list size should match", 2, pegawaiList.size());

        Pegawai firstPegawai = pegawaiList.get(0);
        assertEquals("ID should match", "77311552", firstPegawai.getNik());
        assertEquals("Name should match", "Wiwid Kusuma", firstPegawai.getName());
        assertEquals("Age should match", 20, firstPegawai.getAge());
        assertEquals("Gender should match", "Laki-laki", firstPegawai.getGender());
        assertEquals("Salary should match", 8000000.0, firstPegawai.getSalary(), 0.01);
    }

    @Test
    public void testSavePegawai() {
        // Tes menyimpan data pegawai ke file
        List<Pegawai> pegawaiList = new ArrayList<>();
        pegawaiList.add(new Pegawai("103", "Alice Wonderland", 35, "Female", "0811222333", "alice@example.com", "789 Road", "Finance", 95, 3000000, "images/alice.jpg"));

        FileHandler.savePegawai(pegawaiList, TEST_FILE);

        // Muat ulang file untuk memastikan data tersimpan
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE))) {
            String line = reader.readLine();
            assertNotNull("File should contain data", line);
            assertTrue("File should contain Alice Wonderland", line.contains("Alice Wonderland"));
        } catch (IOException e) {
            fail("Failed to read file after saving");
        }
    }

    @Test
    public void testPegawaiToRow() {
        // Tes konversi Pegawai ke representasi baris
        Pegawai pegawai = new Pegawai("104", "Bob Marley", 45, "Male", "0811555444", "bob@example.com", "101 Lane", "Music", 70, 4000000, "images/bob.jpg");
        String[] row = pegawai.toRow();

        assertEquals("ID should match", "104", row[0]);
        assertEquals("Name should match", "Bob Marley", row[1]);
        assertEquals("Age should match", "45", row[2]);
        assertEquals("Gender should match", "Male", row[3]);
        assertEquals("Salary should match", "4000000.0", row[9]);
    }

    @Test
    public void testSalaryFormatted() {
        Pegawai pegawai = new Pegawai("123", "John Doe", 25, "Laki-laki", "08123456789", "john@example.com", "Jl. Mawar", "HR", 85, 10000000, "images/john.png");
        String formattedSalary = pegawai.getSalaryFormatted();
        assertEquals("Rp10.000.000,00", formattedSalary);
    }

    @Test
    public void testAuthenticate() {
        assertTrue("Authentication should succeed for valid credentials", GUI.authenticate("admin", "admin"));
        assertFalse("Authentication should fail for invalid credentials", GUI.authenticate("user", "wrongpassword"));
    }

    @Test
    public void testRupiahToDouble() {
        double result = GUI.RupiahToDouble("Rp10.000.000,00");
        assertEquals(10000000.0, result, 0.01);
    }
}
