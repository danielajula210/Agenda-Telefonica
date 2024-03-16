import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AgendaTest {

    private Agenda agenda;
    
    @Test
    public void testCautaContact() {
        String rezultatCautare = agenda.cautaContactByString("Popa Cornel");
        assertTrue(rezultatCautare.contains("Popa Cornel"));
        assertFalse(rezultatCautare.contains("Tinu Mirabela"));
    }
    @Test
    public void testAdaugaContact() {
        Contact[] contacte = agenda.getListaContacte();
        assertEquals(6, contacte.length);
    }

    @Test
    public void testStergeContact() {
        agenda.stergeContactByString("Alexandra Roman,0736984569,alexandraroman@yahoo.com");
        Contact[] contacte = agenda.getListaContacte();
        assertEquals(5, contacte.length);
    }
    
}
