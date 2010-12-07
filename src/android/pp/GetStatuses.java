package android.pp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class GetStatuses {

	public List<GetStatusesStruct> StatusesPairs = new ArrayList<GetStatusesStruct>();
	public byte [] preparePackage()
	{
		byte[] resztaPaczki;
		byte[] wynik;
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		int size = StatusesPairs.size();
		try
		{
			while(--size>0)
			{
				dos.writeInt(this.StatusesPairs.get(size).uin);
				dos.write(this.StatusesPairs.get(size).type);
			}
			resztaPaczki = baos.toByteArray();
	    	//wyliczyc this.dlugoscResztyPakietu
			int dlugoscResztyPakietu = Integer.reverseBytes(resztaPaczki.length);
			ByteArrayOutputStream paczkaWTabBajtow = new ByteArrayOutputStream();
			DataOutputStream dos2 = new DataOutputStream(paczkaWTabBajtow);
			dos2.writeInt(Integer.reverseBytes(Common.GG_NOTIFY_LAST));
			dos2.writeInt(dlugoscResztyPakietu);
			dos2.write(resztaPaczki);
			return wynik = paczkaWTabBajtow.toByteArray();
		}
		catch(Exception e)
		{
			Log.e("GetStatuses","Byte Package NOT Created!");
			return null;
		}
		
		
	}
}
