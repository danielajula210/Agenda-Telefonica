//package p3ProiectFinal;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clasa Contact modelează contact telefonic care apare intr-o agendă telefonică
 * contine un nume, un numar de telefon si o adresa de email
 */

public class Contact {
    private String nume;
    private String numarTelefon;
    private String adresaEmail;
    
	/**
	 * Constructor cu parametrii 
	 * @param nume aceasta variabila reprezinta numele contactului
	 * @param numarTelefon aceasta variabila reprezinta numarul de telefon al contactului
	 * @param adresaEmail aceasta variabila reprezinta adresa de email al contactului
	 */

    public Contact(String nume, String numarTelefon, String adresaEmail) {
        this.nume = nume;
        this.numarTelefon = numarTelefon;
        this.adresaEmail = adresaEmail;
    }
    
    
    /**
     * Returnează numele contactului.
     * @return Numele contactului.
     */

    public String getNume() {
        return nume;
    }
    
    /**
     * Returnează numărul de telefon
     * @return numărul de telefon
     */
    public String getNumarTelefon() {
        return numarTelefon;
    }
    /**
     * Returnează adresa de email
     * @return adresa de email
     */
    public String getAdresaEmail() {
        return adresaEmail;
    }

    @Override
    public String toString() {
        return nume + "," + numarTelefon + "," + adresaEmail;
    }
    
    /**
     * Seteaza o valoare noua a numelui
     * @param nume Noul nume al contactului
     */
    public void setNume(String nume) {
        this.nume = nume;
    }
    /**
     * Seteaza o valoare noua a numarului de telefon
     * @param numarTelefon Noul număr de telefon al contactului
     */
    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }
    /**
     * Seteaza o valoare noua a adresei de email
     * @param adresaEmail noua adresă de email a contactului
     */
    public void setAdresaEmail(String adresaEmail) {
        this.adresaEmail = adresaEmail;
    }


}

