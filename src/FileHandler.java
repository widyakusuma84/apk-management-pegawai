import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String FILE_NAME = "data/pegawai.txt";

    public static List<Pegawai> loadPegawai() {
        List<Pegawai> Pegawai = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                Pegawai.add(new Pegawai(
                        data[0], 
                        data[1], 
                        Integer.parseInt(data[2]), 
                        data[3], 
                        data[4], 
                        data[5], 
                        data[6], 
                        data[7], 
                        Integer.parseInt(data[8]), 
                        Double.parseDouble(data[9]), 
                        data[10] 
                ));
            }
        } catch (IOException e) {
            System.out.println("Error load Pegawai: " + e.getMessage());
        }
        return Pegawai;
    }

    public static void savePegawai(List<Pegawai> Pegawai) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Pegawai emp : Pegawai) {
                writer.write(String.join(";", emp.toRow()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error save Pegawai: " + e.getMessage());
        }
    }
}
