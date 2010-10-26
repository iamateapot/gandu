package android.pp;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.*;

@Root(name="Contact")
public class CopyOfContact implements Comparable{
	
	//przedrostki A1, A2.. sa wymagane, poniewaz elementy
	//do pliku XML zapisywane sa wedlug kolejnosci alfabetycznej
	//nazw pol danej klasy.
	//Klient GG jest wrazliwy na kolejnosc pol, w przypadku
	//gdy elementy w pliku XML sa w innej kolejnosc to 
	//klient GG nie zaimportuje listy.
	@Element(name="Guid")
	public String AA1Guid;
	@Element(name="GGNumber")
	public String AA2GGNumber;
	@Element(name="ShowName")
	public String AA3ShowName;
	
	@Element(required=false, name="MobilePhone")
	public String AA4MobilePhone;
	@Element(required=false, name="HomePhone")
	public String AA5HomePhone;
	@Element(required=false, name="Email")
	public String AA6Email;
	@Element(required=false, name="WwwAddress")
	public String AA7WwwAddress;
	@Element(required=false, name="FirstName")
	public String AA8FirstName;
	@Element(required=false, name="LastName")
	public String AA9LastName;
	@Element(required=false, name="Gender")
	public String AB1Gender;
	@Element(required=false, name="Birth")
	public String AB2Birth;
	@Element(required=false, name="City")
	public String AB3City;
	@Element(required=false, name="Province")
	public String AB4Province;
	@Element(name="Groups")
	CopyOfContactGroups AB5Groups;
	@Element(required=false, name="CurrentAvatar")
	public String AB6CurrentAvatar;
	@Element(required=false, name="Avatars")
	CopyOfAvatars AB7Avatars;
	@Element(required=false, name="UserActivatedInMG")
	public Boolean AB8UserActivatedInMG;
	@Element(required=false, name="FlagBuddy")
	public Boolean AB9FlagBuddy;
	@Element(required=false, name="FlagNormal")
	public Boolean AC1FlagNormal;
	@Element(required=false, name="FlagFriend")
	public Boolean AC2FlagFriend;
	@Element(required=false, name="FlagIgnored")
	public Boolean AC3FlagIgnored;
	@Element(required=false, name="NickName")
	public String AC4NickName;	

	public CopyOfContact() {
		this.AB7Avatars = new CopyOfAvatars();
		this.AB7Avatars.Avatars = new ArrayList<String>();
		this.AB7Avatars.Avatars.add("avatar1");
		this.AB7Avatars.Avatars.add("avatar2");    
		this.AA3ShowName = "showName";
		this.AA1Guid = "Guid";
		this.AA2GGNumber = "123456";
		this.AB5Groups = new CopyOfContactGroups();
		this.AB5Groups.Groups = new ArrayList<String>();
		this.AB5Groups.Groups.add("grupa1");
		this.AB5Groups.Groups.add("grupa2");
	}
	
	@Override
	public int compareTo(Object another) {
		String thisVal = this.AA3ShowName;
		String anotherVal = ((CopyOfContact)another).AA3ShowName;
		//return thisVal.compareTo(anotherVal);
		return thisVal.compareToIgnoreCase(anotherVal);
	}
}
