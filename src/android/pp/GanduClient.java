package android.pp;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;


public class GanduClient extends Activity {

	//
	public SharedPreferences.Editor editor;
	public SharedPreferences prefs;
	//
	
	/** Messenger for communicating with service. */
    Messenger mService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mIsBound;
	public Toast toastGandu;
	String[] ip = null;
	private Button connectPhones;
	private EditText ggNumberEdit;
	private EditText ggPasswordEdit;
	String mojNumer = "";
	
	public NotificationManager mNM;
	ProgressDialog mDialog1;
	private static final int DIALOG1_KEY = 0;
	static final private int NEW_ACCOUNT = 0;
	// ------------------> OnCreate()
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mNM.cancel(-1);
		prefs = getPreferences(0);
		editor = prefs.edit();
		
		ggNumberEdit = (EditText) findViewById(R.id.EditText01);
		//ggNumberEdit.setText("23543809");
		//ggNumberEdit.setText("31082603");
		
		ggPasswordEdit = (EditText) findViewById(R.id.EditText02);
		ggNumberEdit.setText(prefs.getString("mojNumer",""));
	    ggPasswordEdit.setText(prefs.getString("mojeHaslo",""));
		connectPhones = (Button) findViewById(R.id.Button01);
		connectPhones.setText("Zaloguj...");
		connectPhones.setOnClickListener(connectListener);
			
		//uruchomienie serwisu Gandu
		startService(new Intent("android.pp.GanduS"));
		//zbindowanie aktywnosci do serwisu
		//doBindService();
		Intent intent = new Intent(getApplicationContext(), GanduService.class);
		getApplicationContext().bindService(intent,mConnection,1);
		mIsBound = true;
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (mIsBound) {
            // If we have received the service, and hence registered with
            // it, then now is the time to unregister.
            if (mService != null) {
                try {
                    Message msg = Message.obtain(null,
                    		Common.CLIENT_UNREGISTER);
                    msg.replyTo = mMessenger;
                    mService.send(msg);
                  
                } catch (RemoteException e) {
                    // There is nothing special we need to do if the service
                    // has crashed.
                }
            }
        }
		super.onDestroy();
		prefs = getPreferences(0);
		editor = prefs.edit();
		editor.remove("text");
		editor.commit();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub		
		super.onStop();
		try
		{
			if(mDialog1 != null)
				mDialog1.dismiss();
		}catch(Exception ex)
		{
			;
		}
	}
	
	/**
     * This method is called when the sending activity has finished, with the
     * result it supplied.
     * 
     * @param requestCode The original request code as given to
     *                    startActivity().
     * @param resultCode From sending activity as per setResult().
     * @param data From sending activity as per setResult().
     */
    @Override
	protected void onActivityResult(int requestCode, int resultCode,
		Intent data) {
        // You can use the requestCode to select between multiple child
        // activities you may have started.  Here there is only one thing
        // we launch.
        if (requestCode == NEW_ACCOUNT) {

            // This is a standard resultCode that is sent back if the
            // activity doesn't supply an explicit result.  It will also
            // be returned if the activity failed to launch.
            if (resultCode == RESULT_CANCELED) {
                Log.i("NewContactResult", "RESULT_CANCELED");

            // Our protocol with the sending activity is that it will send
            // text in 'data' as its result.
            }
            //RESULT_OK
            else 
            {
            	Log.i("NewContactResult", Integer.toString(resultCode));
                if (data != null) {
                    //text.append(data.getAction());
                	String new_numerGG = data.getStringExtra("numerGG");
                	String new_haslo = data.getStringExtra("haslo");
                	String new_email = data.getStringExtra("email");
                	Toast.makeText(getApplicationContext(), "Numer: "+new_numerGG
                			+"\nEmail: "+new_email
                			+"\nHaslo: "+new_haslo, Toast.LENGTH_LONG).show();
                	ggNumberEdit.setText(new_numerGG);
                	ggPasswordEdit.setText(new_haslo);
                }
            }
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.ganduclientmenu, menu);
		return true;
	}
	
	//zdarzenia zwiazane z wyborem opcji menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId())
		{
			case R.id.RegisterAccount01:	
				//Long nowa = new Long("4294967296") - 111;
				//Toast.makeText(getApplicationContext(), ""+nowa, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this.getApplicationContext(), RegisterAccount.class);
				startActivityForResult(intent,NEW_ACCOUNT);
				return true;
			//Moreitemsgohere(ifany)...
		}
		return false;
	}


	private OnClickListener connectListener = new OnClickListener() {
		public void onClick(View v) {
			showDialog(DIALOG1_KEY);
			mojNumer = ggNumberEdit.getText().toString();
			editor.putString("mojNumer", ggNumberEdit.getText().toString());
			editor.putString("mojeHaslo", ggPasswordEdit.getText().toString());
			editor.commit();
			Message msg = Message.obtain(null,Common.CLIENT_LOGIN, 0, 0);
			msg.replyTo = mMessenger;
			Bundle wysylany = new Bundle();
			//wysylany.putString("numerGG", ggNumberEdit.getText().toString());
			wysylany.putString("numerGG", mojNumer);
			wysylany.putString("hasloGG" , ggPasswordEdit.getText().toString());
			msg.setData(wysylany);
			
			try
			{
				mService.send(msg);
				
			}catch(Exception excccc)
			{
				Log.e("Blad","Blad!!!!\n"+excccc.getMessage());
			}
		}
	};
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG1_KEY: {
            	mDialog1 = new ProgressDialog(this);
            	mDialog1.setTitle("Logowanie");
            	mDialog1.setMessage("Prosze czekac...");
            	mDialog1.setIndeterminate(true);
            	//mDialog1.setCancelable(false);
            	mDialog1.setCancelable(true);
                return mDialog1;
            }
        }
        return null;
    }
	
  
	//Funkcje potrzebne do zestawienia polaczenia aktywnosci z serwisem Gandu
	/**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
        	switch (msg.what) {
                case Common.CLIENT_START_INTENT_CONTACTBOOK:
                	Log.i("GanduClient","Odebralem"+msg.what);
                	Intent intent = new Intent(getApplicationContext(),ContactBook.class);
                	intent.putExtra("mojNumer", mojNumer);
					try{
                		startActivity(intent);
                		finish();
                	}catch(Exception  e)
                	{
                		Log.e("GanduClient",""+e.getMessage());
                	}
                	break;
                case Common.FLAG_ACTIVITY_REGISTER:
                	Log.i("GanduClient","Zarejestrowany przez serwis.");
                	break;
                case Common.CLIENT_LOGIN_FAILED:
                	mDialog1.cancel();
                	Toast.makeText(getApplicationContext(), "Sprawd� poprawno�� numeru GG oraz has�a", Toast.LENGTH_SHORT).show();
                	break;
                case Common.CLIENT_INITIALIZE_FAILED:
                	mDialog1.cancel();
                	Toast.makeText(getApplicationContext(), "Nieudana pr�ba po��czenia.\nSprawd� po��czenie z internetem.", Toast.LENGTH_LONG).show();
                	break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            mService = new Messenger(service);
            //mCallbackText.setText("Attached.");

            // We want to monitor the service for as long as we are
            // connected to it.
            try {
                Message msg = Message.obtain(null,
                        Common.CLIENT_REGISTER);
                msg.replyTo = mMessenger;
                mService.send(msg);
            } catch (RemoteException e) {
                // In this case the service has crashed before we could even
                // do anything with it; we can count on soon being
                // disconnected (and then reconnected if it can be restarted)
                // so there is no need to do anything here.
            }

            // As part of the sample, tell the user what happened.
            //Toast.makeText(Binding.this, R.string.remote_service_connected,
            //Toast.makeText(GanduClient.this, "remote_service_connected",
            //        Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            //mCallbackText.setText("Disconnected.");

            // As part of the sample, tell the user what happened.
            //Toast.makeText(Binding.this, R.string.remote_service_disconnected,
            //Toast.makeText(GanduClient.this, "remote_service_disconnected",
            //        Toast.LENGTH_SHORT).show();
        }
    };

    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because there is no reason to be able to let other
        // applications replace our component.
        bindService(new Intent(GanduClient.this, 
                GanduService.class), mConnection, Context.BIND_AUTO_CREATE);
        
        mIsBound = true;
        //mCallbackText.setText("Binding.");
    }

    void doUnbindService() {
        if (mIsBound) {
            // If we have received the service, and hence registered with
            // it, then now is the time to unregister.
            if (mService != null) {
                try {
                    Message msg = Message.obtain(null,
                            Common.CLIENT_UNREGISTER);
                    msg.replyTo = mMessenger;
                    mService.send(msg);
                  
                } catch (RemoteException e) {
                    // There is nothing special we need to do if the service
                    // has crashed.
                }
            }

            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
            //mCallbackText.setText("Unbinding.");
        }
    }
}
