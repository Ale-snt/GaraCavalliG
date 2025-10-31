import java.util.*;

// Classe Cavallo che estende Thread
class Cavallo extends Thread {
    private final String nome;       
    private final int percorso;     
    private int posizione = 0;       

    // Lista condivisa tra tutti i cavalli per salvare l'ordine d'arrivo
    private static final List<String> classifica = Collections.synchronizedList(new ArrayList<>());

    public Cavallo(String nome, int percorso) {
        this.nome = nome;
        this.percorso = percorso;
    }

    @Override
    public void run() {
        while (posizione < percorso) {
            posizione += (int)(Math.random() * 10) + 1; // avanza da 1 a 10 metri
            System.out.println(nome + " ha percorso " + posizione + " metri");

            try {
                Thread.sleep((int)(Math.random() * 400) + 100); // pausa casuale
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Se il cavallo ha finito il percorso, lo aggiungiamo in classifica
            if (posizione >= percorso && !classifica.contains(nome)) {
                classifica.add(nome);
                System.out.println("\n🏁 " + nome + " ha tagliato il traguardo!");
            }
        }
    }

    // Metodo statico per accedere alla classifica finale
    public static List<String> getClassifica() {
        return classifica;
    }
}

public class GaraCavalli {
    public static void main(String[] args) {
        Scanner tastiera = new Scanner(System.in);
        System.out.print("Inserisci la lunghezza del percorso in metri: ");
        int lunghezza = tastiera.nextInt();

        // Creiamo i cavalli
        Cavallo c1 = new Cavallo("Ale", lunghezza);
        Cavallo c2 = new Cavallo("Alexa", lunghezza);
        Cavallo c3 = new Cavallo("Toro", lunghezza);
        Cavallo c4 = new Cavallo("Giulia", lunghezza);
        Cavallo c5 = new Cavallo("Canto", lunghezza);

        // Avviamo la gara
        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();

        // Aspettiamo che tutti finiscano
        try {
            c1.join();
            c2.join();
            c3.join();
            c4.join();
            c5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Stampiamo la classifica finale (dal primo all’ultimo)
        System.out.println("\n=== 🏆 CLASSIFICA FINALE 🏆 ===");
        List<String> classifica = Cavallo.getClassifica();
        for (int i = 0; i < classifica.size(); i++) {
            System.out.println((i + 1) + "° posto: " + classifica.get(i));
        }

        tastiera.close();
    }
}
