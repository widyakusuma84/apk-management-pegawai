import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static List<Pegawai> loadPegawai(String path) {
        List<Pegawai> Pegawai = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                Pegawai.add(new Pegawai(
                        data[0], // id
                        data[1], // nama
                        Integer.parseInt(data[2]), // umur
                        data[3], // kelamin
                        data[4], // no hp
                        data[5], // email
                        data[6], // alamat
                        data[7], // departemen
                        Integer.parseInt(data[8]), // score
                        Double.parseDouble(data[9]), // gaji
                        data[10] // lokasi gambar
                ));
            }
        } catch (IOException e) {
            System.out.println("Error load Pegawai: " + e.getMessage());
        }
        return Pegawai;
    }

    public static void savePegawai(List<Pegawai> Pegawai, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Pegawai emp : Pegawai) {
                writer.write(String.join(";", emp.toRow()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error save Pegawai: " + e.getMessage());
        }
    }
}
