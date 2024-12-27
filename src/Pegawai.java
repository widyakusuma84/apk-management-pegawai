import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Pegawai {
    private String nik;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String department;
    private int score;
    private double salary;
    private String imagePath;

    public Pegawai(String nik, String name, int age, String gender, String phone, String email, String address, String department, int score, double salary, String imagePath) {
        this.nik = nik;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.department = department;
        this.score = score;
        this.salary = salary;
        this.imagePath = imagePath;
    }

    public String[] toRow() {
        return new String[] { nik, name, String.valueOf(age), gender, phone, email, address, department, String.valueOf(score), String.valueOf(salary), imagePath };
    }

    public String getNik() { return nik; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getDepartment() { return department; }
    public int getScore() { return score; }
    public double getSalary() { return salary; }
    public String getSalaryFormatted() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(salary);
    }
    public String getImagePath() { return imagePath; }
}
