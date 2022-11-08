import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) {
        Path pathZip = null;
        String webPageZip = "https://drive.google.com/drive/folders/0B2iW2fOFs_uOOXZmYUxDYWdSYXc?resourcekey=0-JZ0LBFZ7ufbTkPmPnDoPng&usp=sharing";
        String downloadDir = "D:/GitHub/FilesCopy/data";

        try {
            pathZip = Files.createDirectory(Path.of(downloadDir));
            InputStream input = new URL(webPageZip).openStream();
            Files.copy(input, pathZip, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
