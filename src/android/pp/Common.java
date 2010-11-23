package android.pp;

public class Common {
	
	/**
     * REQUESTS TO SERVER GG
    */
    static final int GG_USERLIST_REQUEST80 = 0x002f;
    static final int GG_LOGIN80 = 0x0031;
    static final int GG_LIST_EMPTY = 0x0012;
    static final int GG_SEND_MSG80 = 0x002d;
    static final int GG_NEW_STATUS80 = 0x0038;
    static final int GG_ADD_NOTIFY = 0x000d;
    
    /**
     * STATES 
     */
    static final int GG_STATUS_AVAIL = 0x0002;
    static final int GG_STATUS_AVAIL_DESCR = 0x0004;    
    static final int GG_STATUS_NOT_AVAIL = 0x0001;
    static final int GG_STATUS_NOT_AVAIL_DESCR = 0x0015;
    static final int GG_STATUS_INVISIBLE = 0x0014;
    static final int GG_STATUS_INVISIBLE_DESCR = 0x0016;
    /**
     * REPLIES FROM SERVER GG
    */
    static final int GG_WELCOME = 0x0001;
    static final int GG_USERLIST_REPLY80 = 0x0030;
    static final int GG_LOGIN_OK80 = 0x0035;
    static final int GG_LOGIN_FAILED = 0x0009;
    static final int GG_RECV_MSG80 = 0x002e;
    /**
     * CONTACT TYPES
     */
    static final byte GG_USER_OFFLINE = 0x01;	//U�ytkownik, dla kt�rego b�dziemy niedost�pni, ale mamy go w li�cie kontakt�w
    static final byte GG_USER_NORMAL = 0x03;		//Zwyk�y u�ytkownik dodany do listy kontakt�w
    static final byte GG_USER_BLOCKED = 0x04;	//U�ytkownik, kt�rego wiadomo�ci nie chcemy otrzymywa�

    /**
     * CLASS FLAGS
    */
    static final int FLAG_ACTIVITY_REGISTER = 10;
    static final int FLAG_CONTACTBOOK = 11;
    
    /**
     * IMPORT/EXPORT CONTACTBOOK TYPES
    */
    static final byte GG_USERLIST_GET=  0x02; 	//import listy
    static final byte GG_USERLIST_PUT= 0x00; 	//poczatek eksportu listy
    
    /**
     * Command to the service to register a client, receiving callbacks
     * from the service.  The Message's replyTo field must be a Messenger of
     * the client where callbacks should be sent.
     */
    static final int CLIENT_REGISTER = 1;

    /**
     * Command to the service to unregister a client, ot stop receiving callbacks
     * from the service.  The Message's replyTo field must be a Messenger of
     * the client as previously given with MSG_REGISTER_CLIENT.
     */
    static final int CLIENT_UNREGISTER = 2;
    
    /**
     * Command to service to login user.
    */
   static final int CLIENT_LOGIN = 3;
   /**
    * Command to service to download a contactbook from server.
   */
   static final int CLIENT_GET_CONTACTBOOK = 4;
   
   static final int CLIENT_START_INTENT_CONTACTBOOK = 5;
   static final int CLIENT_SET_CONTACTBOOK = 6;
   
   static final int CLIENT_SEND_MESSAGE = 7;
   static final int CLIENT_RECV_MESSAGE = 8;
   
   static final int CLIENT_CHANGE_STATUS = 9;
   
   static final int CLIENT_ADD_NEW_CONTACT = 10;
}
