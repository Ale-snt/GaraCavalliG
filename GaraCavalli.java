import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;

class Cavallo extends Thread {
    private String nome;
    private int percorso;
    private int posizione = 0;
    private static List<String> classifica = new ArrayList<>();
    
    public Cavallo(String nome, int percorso) {
        this.nome = nome;
        this.percorso = percorso;
    }

    public void run() {
        Random random = new Random();
        
        while (posizione < percorso) {
            posizione += random.nextInt(10) + 1;
            System.out.println(nome + " è a " + posizione + " metri");
            
            if (posizione >= percorso) {
                System.out.println(nome + " ha finito!");
                classifica.add(nome);
                break;
            }
            
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        }
    }
    
    public static List<String> getClassifica() {
        return classifica;
    }
}

public class GaraCavalli {
    public static void main(String[] args) throws InterruptedException, IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Scegli dove salvare la classifica");
        int scelta = chooser.showSaveDialog(null);

        if (scelta != JFileChooser.APPROVE_OPTION) {
            System.out.println("Hai annullato. Addio!");
            return;
        }

        String percorsoFile = chooser.getSelectedFile().getAbsolutePath();
        System.out.println("Salvo in: " + percorsoFile);

        int lunghezza = 50;
        ArrayList<Cavallo> cavalli = new ArrayList<>();
        cavalli.add(new Cavallo("Marco", lunghezza));
        cavalli.add(new Cavallo("Luca", lunghezza));
        cavalli.add(new Cavallo("Anna", lunghezza));
        cavalli.add(new Cavallo("Giulia", lunghezza));

        for (Cavallo c : cavalli) c.start();
        for (Cavallo c : cavalli) c.join();
        
        List<String> classifica = Cavallo.getClassifica();

        try (FileWriter writer = new FileWriter(percorsoFile)) {
            writer.write("Classifica finale:\n");
            for (int i = 0; i < classifica.size(); i++) {
                writer.write((i + 1) + ". " + classifica.get(i) + "\n");
            }
        }

        System.out.println("\nClassifica salvata nel file!");
    }
}
