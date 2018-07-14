package algorithm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import window.MainWindow;

/**
 *
 * @author arus2
 */
public class MainClass {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        Path dir = Paths.get("./checkpoints/");
        String path = "src/problem.params";
        boolean useCheckpoint = false;

        Optional<Path> lastFilePath = Files.list(dir) // all files
                .filter(f -> !Files.isDirectory(f)) // exclude directories
                .filter(f -> f.toFile().getName().startsWith("gp")) // exclude files with no matching name
                .max(Comparator.comparingLong(f -> f.toFile().lastModified()));  // take last modified
       
        if (lastFilePath.isPresent()) // if file exists
        {
           //not yet ready
           path = dir.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + lastFilePath.get().getFileName().toString();
           useCheckpoint = true;
        }

        //create window
        MainWindow window = null;
        try {
            window = new MainWindow();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        //run gp
        CustomEvolve.evolve(useCheckpoint, path);
    }
}
