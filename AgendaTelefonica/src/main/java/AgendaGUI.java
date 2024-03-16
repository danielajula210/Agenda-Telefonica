//package p3ProiectFinal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;


/**
 * Clasa AgendaGUI reprezintă interfața grafică a unei agende telefonice.
 * Această clasă utilizează o instanță a clasei Agenda pentru a manipula și afișa contactele.
 * Interfața grafică conține o listă de contacte, precum și butoane pentru adăugare, ștergere, actualizare și căutare.
 */
public class AgendaGUI {

	/** Instanță a clasei Agenda pentru manipularea listei de contacte. */
    private Agenda agenda;
    /** Modelul implicit al listei pentru afișarea contactelor în interfața grafică. */
    private DefaultListModel<String> contactListModel;

    /**
     * Constructorul clasei AgendaGUI.
     * Inițializează instanța clasei Agenda și modelul listei de contacte pentru afișare.
     */
    public AgendaGUI() {
        agenda = new Agenda();
        contactListModel = new DefaultListModel<>();
        refreshListModel();
    }

    /**
     * Acestă metodă actualizează modelul listei de contacte pentru a reflecta ultimele modificări.
     */
    private void refreshListModel() {
        contactListModel.clear();
        for (Contact contact : agenda.getListaContacte()) {
            contactListModel.addElement(contact.toString());
        }
    }

    /**
     * Această metodă creează și afișează interfața grafică a agendei telefonice.
     * Interfața conține o listă cu contacte, butoane pentru operațiile de adăugare, ștergere, actualizare și căutare.
     */
    public void createAndShowGUI() {
        JFrame frame = new JFrame("Agenda Telefonica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        JList<String> contactList = new JList<>(contactListModel);
        JScrollPane scrollPane = new JScrollPane(contactList);

        JButton adaugaButton = new JButton("Adauga");
        JButton stergeButton = new JButton("Sterge");
        JButton actualizeazaButton = new JButton("Actualizeaza");

        adaugaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nume = JOptionPane.showInputDialog("Nume:");
                String telefon = JOptionPane.showInputDialog("Numar telefon:");
                String email = JOptionPane.showInputDialog("Adresa email:");

                agenda.adaugaContact(nume, telefon, email);
                refreshListModel();
            }
        });

        stergeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedContact = contactList.getSelectedValue();
                if (selectedContact != null) {
                    agenda.stergeContactByString(selectedContact);
                    refreshListModel();
                } else {
                    JOptionPane.showMessageDialog(frame, "Selectati un contact pentru a sterge.");
                }
            }
        });

        actualizeazaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedContact = contactList.getSelectedValue();
                if (selectedContact != null) {
                    String[] contactInfo = selectedContact.split(",");
                    if (contactInfo.length == 3) {
                        String nume = contactInfo[0].trim();
                        String numarTelefon = contactInfo[1].trim();
                        String adresaEmail = contactInfo[2].trim();
                        String numeNou = JOptionPane.showInputDialog("Nume nou:", nume);
                        String numarTelefonNou = JOptionPane.showInputDialog("Numar telefon nou:", numarTelefon);
                        String adresaEmailNoua = JOptionPane.showInputDialog("Adresa email noua:", adresaEmail);
                        agenda.actualizeazaContact(numarTelefon, numeNou, numarTelefonNou, adresaEmailNoua);
                        refreshListModel();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Selectati un contact pentru a actualiza.");
                }
            }
        });
        JButton cautaButton = new JButton("Cauta");

        cautaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cautare = JOptionPane.showInputDialog("Introduceti numele sau numarul de telefon pentru cautare:");
                if (cautare != null && !cautare.isEmpty()) {
                    String rezultatCautare = agenda.cautaContactByString(cautare);
                    JOptionPane.showMessageDialog(null, rezultatCautare, "Rezultate Cautare", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Introduceti un nume sau numar de telefon pentru cautare.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton adaugaLaFavoriteButton = new JButton("Adauga la Favorite");
        JButton afiseazaFavoriteButton = new JButton("Afiseaza Favorite");

        adaugaLaFavoriteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedContact = contactList.getSelectedValue();
                if (selectedContact != null) {
                    agenda.adaugaLaFavorite(getContactFromString(selectedContact));
                } else {
                    JOptionPane.showMessageDialog(frame, "Selectati un contact pentru a adauga la favorite.");
                }
            }
        });

        afiseazaFavoriteButton.addActionListener(new ActionListener() {
          @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Contact> contacteFavorite = agenda.getContacteFavorite();
                if (!contacteFavorite.isEmpty()) {
                    StringBuilder rezultateFavorite = new StringBuilder("Contacte Favorite:\n");
                    for (Contact contact : contacteFavorite) {
                        rezultateFavorite.append(contact.toString()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, rezultateFavorite.toString(), "Contacte Favorite", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Nu exista contacte favorite.", "Contacte Favorite", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        JButton stergeDeLaFavoriteButton = new JButton("Sterge de la Favorite");

        stergeDeLaFavoriteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFavoriteContact = contactList.getSelectedValue();
                if (selectedFavoriteContact != null) {
                    Contact favoriteContact = getContactFromFavoriteString(selectedFavoriteContact);
                    if (favoriteContact != null) {
                        agenda.stergeDeLaFavorite(favoriteContact);
                        refreshListModel(); // Actualizează modelul listei după ștergere
                    } else {
                        JOptionPane.showMessageDialog(frame, "Eroare la identificarea contactului.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Selectati un contact pentru a sterge de la favorite.");
                }
            }
        });

       
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(adaugaButton);
        buttonPanel.add(stergeButton);
        buttonPanel.add(actualizeazaButton);
        buttonPanel.add(cautaButton);
        buttonPanel.add(adaugaLaFavoriteButton);
        buttonPanel.add(afiseazaFavoriteButton);
        buttonPanel.add(stergeDeLaFavoriteButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    private Contact getContactFromString(String contactString) {
        String[] contactInfo = contactString.split(",");
        if (contactInfo.length == 3) {
            String nume = contactInfo[0].trim();
            String numarTelefon = contactInfo[1].trim();
            String adresaEmail = contactInfo[2].trim();
            return new Contact(nume, numarTelefon, adresaEmail);
        }
        return null;
    }
    
    private Contact getContactFromFavoriteString(String favoriteString) {
        String[] contactInfo = favoriteString.split(",");
        if (contactInfo.length == 3) {
            String nume = contactInfo[0].trim();
            String numarTelefon = contactInfo[1].trim();
            String adresaEmail = contactInfo[2].trim();
            return new Contact(nume, numarTelefon, adresaEmail);
        }
        return null;
    }

}
