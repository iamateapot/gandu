/*
     * Pole features jest map� bitow� informuj�c� serwer, kt�re z funkcji protoko�u obs�ugujemy. 
     * Do minimalnej zgodno�ci z protoko�em Nowego Gadu-Gadu niezb�dna jest co najmniej warto�� 0x00000007.
     * Bit	Warto��		Znaczenie
     * 0	0x00000001	Rodzaj pakietu informuj�cego o zmianie stanu kontakt�w (patrz bit 2)
     * 					0 � GG_STATUS77, GG_NOTIFY_REPLY77
     * 					1 � GG_STATUS80BETA, GG_NOTIFY_REPLY80BETA
     * 1	0x00000002	Rodzaj pakietu z otrzymaj� wiadomo�ci�
     * 					0 � GG_RECV_MSG
     * 					1 � GG_RECV_MSG80
     * 2	0x00000004	Rodzaj pakietu informuj�cego o zmianie stanu kontakt�w (patrz bit 0)
     * 					0 � wybrany przez bit 0
     * 					1 � GG_STATUS80, GG_NOTIFY_REPLY80
     * 4	0x00000010	Klient obs�uguje statusy "nie przeszkadza�" i "poGGadaj ze mn�"
     * 5	0x00000020	Klient obs�uguje statusy graficzne i GG_STATUS_DESCR_MASK (patrz Zmiana stanu)
     * 6	0x00000040	Znaczenie nie jest znane, ale klient otrzyma w przypadku b��dnego has�a 
     * 					pakiet GG_LOGIN80_FAILED zamiast GG_LOGIN_FAILED
     * 7	0x00000100	Znaczenie nie jest znane, ale jest u�ywane przez nowe klienty
     * 9	0x00000200	Klient obs�uguje dodatkowe informacje o li�cie kontakt�w
     * 10	0x00000400	Klient wysy�a potwierdzenia odebrania wiadomo�ci
     * 13	0x00002000	Klient obs�uguje powiadomienia o pisaniu
     * 
     * Pole flags:
     * Mo�liwe flagi to:
     * Bit	Warto��	Znaczenie
     * 0	0x00000001	Nieznane, zawsze wyst�puje
     * 1	0x00000002	Klient obs�uguje wideorozmowy
     * 20	0x00100000	Klient mobilny (ikona telefonu kom�rkowego)
     * 23	0x00800000	Klient chce otrzymywa� linki od nieznajomych
     */

/*
 * Przyklad zamiany Stringa na ciag bajtow i odwrotnie:
	String napis = "napis";
	Charset cset = Charset.forName("UTF-8");
	byte[] tabbyte = napis.getBytes("UTF-8");
	ByteBuffer bbuf = ByteBuffer.wrap(tabbyte);
	CharBuffer cbuf = cset.decode(bbuf);
	String odzyskane = cbuf.toString();
*/

package android.pp;

import java.nio.ByteBuffer;

public class Logowanie {
	//#define GG_LOGIN80 0x0031
	int typKomunikatu = Integer.reverseBytes(0x0031);
	int dlugoscResztyPakietu;
	
	int uin;              				/* numer Gadu-Gadu */
    byte[] language = new byte[2];    	/* j�zyk: "pl" */
    byte hash_type;       				/* rodzaj funkcji skr�tu has�a */
    byte[] hash = new byte[64];        	/* skr�t has�a dope�niony \0 */
    int status;           				/* pocz�tkowy status po��czenia */
    int flags;            				/* pocz�tkowe flagi po��czenia */
    int features;         				/* opcje protoko�u (0x00000367)*/
    int local_ip = 0;         			/* lokalny adres po��cze� bezpo�rednich (nieu�ywany) */
    short local_port = 0;     			/* lokalny port po��cze� bezpo�rednich (nieu�ywany) */
    int external_ip = 0;      			/* zewn�trzny adres (nieu�ywany) */
    short external_port = 0;  			/* zewn�trzny port (nieu�ywany) */
    byte image_size;      				/* maksymalny rozmiar grafiki w KB */
    byte unknown1 = 0x64;  				/* 0x64 */
    int version_len;				/* d�ugo�� ci�gu z wersj� (0x23) */
    byte[] version;       				/* "Gadu-Gadu Client build 10.0.0.10450" (bez \0) */
    int description_size; 				/* rozmiar opisu */
    byte[] description;   				/* opis (nie musi wyst�pi�, bez \0) */
    
    public Logowanie(int ziarno, String haslo, int numerGG, int status, byte rozmiarGrafiki, String opis)
    {
    	try 
    	{
    		this.uin = Integer.reverseBytes(numerGG);
			this.language = "pl".getBytes("UTF-8");
			//Skrot hasla SHA1
			this.hash_type = 0x02;
			this.status = Integer.reverseBytes(status);
			//0x00000001	Nieznane, zawsze wyst�puje
			this.flags = Integer.reverseBytes(1);
			//Do minimalnej zgodno�ci z protoko�em Nowego Gadu-Gadu niezb�dna jest co najmniej warto�� 0x00000007
			this.features = Integer.reverseBytes(7);
			this.image_size = rozmiarGrafiki;
			this.version = "Gadu-Gadu Client build 10.0.0.10450".getBytes("UTF-8");
			//powinna byc 35 bajtow
			this.version_len = Integer.reverseBytes(this.version.length);
			this.description = opis.getBytes("UTF-8");
			this.description_size = Integer.reverseBytes(this.description.length);
			
		} catch (Exception e) 
		{
			;
		}	
    }
    
    public byte[] pobraniePaczkiBajtow()
    {
    	//wczesniej jeszcze trzeba wyliczyc skrot hasla w polaczeniu z ziarnem
    	//i wpisac otrzymana wartosc do pola this.hash (dopelnic \0)
    	//a nastepnie wyliczyc this.dlugoscResztyPakietu
    	//TO DO
    	ByteBuffer bb = ByteBuffer.allocate(10000);
    	bb.putInt(this.typKomunikatu);
    	bb.putInt(this.dlugoscResztyPakietu);
    	return bb.array();
    }
}
