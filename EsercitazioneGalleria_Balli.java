import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

public class EsercitazioneGalleria_Balli extends JFrame {
    private JLabel etichettaImmagine;
    private JButton bottonePrecedente;
    private JButton bottoneSuccessiva;
    private JButton bottoneCarica;
    private JLabel etichettaStato;


    private File[] immagini;
    private int indiceCorrente = 0;

    public EsercitazioneGalleria_Balli() {
        
        setTitle("Galleria Immagini Java");

        setSize(800, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        etichettaImmagine = new JLabel("Nessuna immagine caricata", SwingConstants.CENTER);
        etichettaImmagine.setBackground(Color.DARK_GRAY);

        etichettaImmagine.setOpaque(true);

        etichettaImmagine.setForeground(Color.WHITE);

        add(etichettaImmagine, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        bottoneCarica = new JButton("Carica Cartella");
        bottonePrecedente = new JButton("<< Precedente");
        bottoneSuccessiva = new JButton("Successiva >>");
        
        bottonePrecedente.setEnabled(false);
        bottoneSuccessiva.setEnabled(false);

        controlPanel.add(bottoneCarica);
        controlPanel.add(bottonePrecedente);
        controlPanel.add(bottoneSuccessiva);

        add(controlPanel, BorderLayout.SOUTH);

        etichettaStato = new JLabel("Seleziona una cartella per iniziare", SwingConstants.CENTER);
        add(etichettaStato, BorderLayout.NORTH);

        bottoneCarica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                chooseDirectory();
            }
        });

        bottonePrecedente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                if (immagini != null && immagini.length > 0) {
                    indiceCorrente--;
                    if (indiceCorrente < 0) {
                        indiceCorrente = immagini.length - 1; // Loop all'ultima immagine
                    }
                    showImage();
                }
            }
        });

        // Bottone Successiva
        bottoneSuccessiva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                if (immagini != null && immagini.length > 0) {
                    indiceCorrente++;
                    if (indiceCorrente >= immagini.length) {
                        indiceCorrente = 0; // Loop alla prima immagine
                    }
                    showImage();
                }
            }
        });
    }

    private void chooseDirectory() {
        JFileChooser selettoreFile = new JFileChooser();
        selettoreFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int risultato = selettoreFile.showOpenDialog(this);

        if (risultato == JFileChooser.APPROVE_OPTION) {
            File cartellaSelezionata = selettoreFile.getSelectedFile();
            loadImagesFromDir(cartellaSelezionata);
        }
    }

    private void loadImagesFromDir(File cartella) {
        FilenameFilter filtroImmagini = new FilenameFilter() {
            @Override
            public boolean accept(File cartellaLocale, String nomeFile) {

                String nomeMinuscolo = nomeFile.toLowerCase();

                return nomeMinuscolo.endsWith(".jpg") || nomeMinuscolo.endsWith(".jpeg") || nomeMinuscolo.endsWith(".png") || nomeMinuscolo.endsWith(".gif");
            }
        };

        immagini = cartella.listFiles(filtroImmagini);

        if (immagini != null && immagini.length > 0) {
            indiceCorrente = 0;

            bottonePrecedente.setEnabled(true);

            bottoneSuccessiva.setEnabled(true);

            showImage();
        } else {
            etichettaStato.setText("Nessuna immagine trovata in questa cartella.");
            etichettaImmagine.setIcon(null);

            etichettaImmagine.setText("Nessuna immagine");

            bottonePrecedente.setEnabled(false);

            bottoneSuccessiva.setEnabled(false);
        }
    }

    private void showImage() {
        if (immagini == null || immagini.length == 0) return;

        File fileImmagine = immagini[indiceCorrente];
        etichettaStato.setText("Immagine " + (indiceCorrente + 1) + " di " + immagini.length + " - " + fileImmagine.getName());

        ImageIcon icona = new ImageIcon(fileImmagine.getPath());

        Image immagine = icona.getImage();
        
        int larghezza = etichettaImmagine.getWidth();
        int altezza = etichettaImmagine.getHeight();
        if (larghezza == 0) larghezza = 800;
        if (altezza == 0) altezza = 500;

        Image immagineRidimensionata = immagine.getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
        
        etichettaImmagine.setIcon(new ImageIcon(immagineRidimensionata));
        etichettaImmagine.setText("");
   }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EsercitazioneGalleria_Balli().setVisible(true);
            }
        });
    }
}
