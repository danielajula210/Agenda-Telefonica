//package p3ProiectFinal;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


/**
 * Clasa Agenda reprezintă o agendă telefonică ce conține o listă de contacte.
 * Agenda permite operații precum adăugarea, ștergerea, actualizarea și căutarea de contacte.
 * Contactele sunt salvate într-un fișier 
 */

public class Agenda {
	/** Calea către fișierul în care sunt stocate contactele. */
    private static final String FISIER_CONTACTE = "src/main/resources/contacte.txt";
    /** Lista de contacte din agenda telefonică. */
    private ArrayList<Contact> listaContacte;
    
    private static final String FISIER_FAVORITE = "src/main/resources/favorite.txt";
    private ArrayList<Contact> contacteFavorite;

	/**
	 * Constructor implicit al clasei agenda
	 * Creeaza o lista de contacte
	 */
    public Agenda() {
        listaContacte = new ArrayList<>();
        contacteFavorite = new ArrayList<>();
        citesteContacte();
        citesteFavorite();
    }
    
    /**
     * Metoda care salveaza contactele in fisier
     */
    public void salveazaContacte() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FISIER_CONTACTE))) {
            for (Contact contact : listaContacte) {
                bw.write(contact.toString());
                bw.newLine();
            }
            
        } catch (IOException e) {
            System.out.println("Eroare la salvarea contactelor: " + e.getMessage());
        }
    }
	/**
	 * Metoda de citire a contactelor din fisier
	 */
    private void citesteContacte() {
        try (BufferedReader br = new BufferedReader(new FileReader(FISIER_CONTACTE))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                String[] split = linie.split(",");
                String nume = split[0].trim();
                String numarTelefon = split[1].trim();
                String adresaEmail = split[2].trim();

                Contact contact = new Contact(nume, numarTelefon, adresaEmail);
                listaContacte.add(contact);
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea contactelor: " + e.getMessage());
        }
    }

    /**
     * Obține lista de contacte sub formă de array.
     * @return Un array de obiecte de tip Contact.
     */
    public Contact[] getListaContacte() {
        Contact[] contacteArray = new Contact[listaContacte.size()];
        listaContacte.toArray(contacteArray);
        return contacteArray;
    }
    
    /**
     * Adaugă un contact nou în agenda și salvează lista actualizată în fișier.
     * @param nume           Numele noului contact.
     * @param numarTelefon   Numărul de telefon al noului contact.
     * @param adresaEmail    Adresa de email a noului contact.
     */
    public void adaugaContact(String nume, String numarTelefon, String adresaEmail) {
        Contact contact = new Contact(nume, numarTelefon, adresaEmail);
        listaContacte.add(contact);
        salveazaContacte();
    }

    /**
     * Șterge un contact specificat prin șirul de informații și salvează lista actualizată în fișier.
     * @param contactString  Șirul de informații al contactului de șters.
     */
    public void stergeContactByString(String contactString) {
        String[] contactInfo = contactString.split(",");
        if (contactInfo.length == 3) {
            String nume = contactInfo[0].trim();
            String numarTelefon = contactInfo[1].trim();
            String adresaEmail = contactInfo[2].trim();
            Iterator<Contact> iterator = listaContacte.iterator();
            while (iterator.hasNext()) {
                Contact contact = iterator.next();
                if (contact.getNume().equals(nume) &&
                    contact.getNumarTelefon().equals(numarTelefon) &&
                    contact.getAdresaEmail().equals(adresaEmail)) {
                    iterator.remove();
                    salveazaContacte();
                    return;
                }
            }
        }
        System.out.println("Contactul nu a fost găsit sau șirul este incorect formatat.");
    }
    
    /**
     * Actualizează informațiile unui contact specificat prin numărul de telefon și salvează lista actualizată în fișier.
     * @param numarTelefon Numărul de telefon al contactului de actualizat.
     * @param numeNou Noul nume al contactului.
     * @param numarTelefonNou Noul număr de telefon al contactului.
     * @param adresaEmailNoua Noua adresă de email a contactului.
     */
    public void actualizeazaContact(String numarTelefon, String numeNou, String numarTelefonNou, String adresaEmailNoua) {
        for (Contact contact : listaContacte) {
            if (contact.getNumarTelefon().equals(numarTelefon)) {
                contact.setNume(numeNou);
                contact.setNumarTelefon(numarTelefonNou);
                contact.setAdresaEmail(adresaEmailNoua);
                salveazaContacte();
                
                return;
            }
        }

        System.out.println("Contactul cu numarul " + numarTelefon + " nu a fost gasit.");
    }
    
    /**
     * Caută un contact după nume sau număr de telefon și returnează rezultatele sub formă de șir de caractere.
     * @param cautare Numele sau numărul de telefon cătat.
     * @return Un șir de caractere cu rezultatele căutării.
     */
    public String cautaContactByString(String cautare) {
        StringBuilder rezultateCautare = new StringBuilder();
        boolean contactGasit = false;

        for (Contact contact : listaContacte) {
            if (contact.getNume().equalsIgnoreCase(cautare) || contact.getNumarTelefon().equals(cautare)) {
                rezultateCautare.append("Contact gasit: ").append(contact).append("\n");
                contactGasit = true;
            }
        }

        if (!contactGasit) {
            rezultateCautare.append("Niciun contact gasit pentru: ").append(cautare);
        }

        return rezultateCautare.toString();
    }
    
    /**Metoda care adauga contacte la favorite*/
    public void adaugaLaFavorite(Contact contact) {
        contacteFavorite.add(contact);
        salveazaFavorite();
    }

    public ArrayList<Contact> getContacteFavorite() {
        return new ArrayList<>(contacteFavorite);
    }

    /**Se citesc contactele din fisierul de contacte favorite*/
    private void citesteFavorite() {
        contacteFavorite = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FISIER_FAVORITE))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                String[] split = linie.split(",");
                String nume = split[0].trim();
                String numarTelefon = split[1].trim();
                String adresaEmail = split[2].trim();

                Contact contact = new Contact(nume, numarTelefon, adresaEmail);
                contacteFavorite.add(contact);
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea contactelor favorite: " + e.getMessage());
        }
    }

    /**Metoda care salveaza contactele favorite în fisierul de contacte favorite*/
    private void salveazaFavorite() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FISIER_FAVORITE))) {
            for (Contact contact : contacteFavorite) {
                bw.write(contact.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Eroare la salvarea contactelor favorite: " + e.getMessage());
        }
    }
    
    /**Metoda care sterge contactele de la favorite*/
    public void stergeDeLaFavorite(Contact contact) {
        Iterator<Contact> iterator = contacteFavorite.iterator();
        while (iterator.hasNext()) {
            Contact favContact = iterator.next();
            if (favContact.equals(contact)) {
                iterator.remove();
                salveazaFavorite();
                System.out.println("Contactul a fost șters de la favorite: " + contact);
                return;
            }
        }
    }
    
  
}
