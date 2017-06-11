
/*              Bluetooth Chat                 */
/* This file contains implementation for chat messaging */

package com.example.android.BluetoothChat;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class BluetoothChat extends Activity
{
  private static final boolean D = true;
  public static final String DEVICE_NAME = "device_name";
  public static final int MESSAGE_DEVICE_NAME = 4;
  public static final int MESSAGE_READ = 2;
  public static final int MESSAGE_STATE_CHANGE = 1;
  public static final int MESSAGE_TOAST = 5;
  public static final int MESSAGE_WRITE = 3;
  private static final int REQUEST_CONNECT_DEVICE = 1;
  private static final int REQUEST_ENABLE_BT = 2;
  private static final String TAG = "BluetoothChat";
  public static final String TOAST = "toast";
  private BluetoothAdapter mBluetoothAdapter = null;
  private BluetoothChatService mChatService = null;
  private String mConnectedDeviceName = null;
  private ArrayAdapter<String> mConversationArrayAdapter;
  private ListView mConversationView;
  private final Handler mHandler = new BluetoothChat.2(this);
  private EditText mOutEditText;
  private StringBuffer mOutStringBuffer;
  private Button mSendButton;
  private TextView mTitle;
  private TextView.OnEditorActionListener mWriteListener = new BluetoothChat.1(this);

  /* Check for Devices where bluetooth is On */

  private void ensureDiscoverable()
  {
    Log.d("BluetoothChat", "ensure discoverable");
    if (this.mBluetoothAdapter.getScanMode() != 23)
    {
      Intent localIntent = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
      localIntent.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", 300);
      startActivity(localIntent);
    }
  }

  /* Function that handles sending of messages */

  private void sendMessage(String paramString)
  {
    if (this.mChatService.getState() != 3)
      Toast.makeText(this, 2130968578, 0).show();
    while (true)
    {
      return;
      if (paramString.length() > 0)
      {
        byte[] arrayOfByte = paramString.getBytes();
        this.mChatService.write(arrayOfByte);
        this.mOutStringBuffer.setLength(0);
        this.mOutEditText.setText(this.mOutStringBuffer);
        continue;
      }
    }
  }

  private void setupChat()
  {
    Log.d("BluetoothChat", "setupChat()");
    this.mConversationArrayAdapter = new ArrayAdapter(this, 2130903044);
    this.mConversationView = ((ListView)findViewById(2131099655));
    this.mConversationView.setAdapter(this.mConversationArrayAdapter);
    this.mOutEditText = ((EditText)findViewById(2131099656));
    this.mOutEditText.setOnEditorActionListener(this.mWriteListener);
    this.mSendButton = ((Button)findViewById(2131099657));
    this.mSendButton.setOnClickListener(new BluetoothChat.3(this));
    this.mChatService = new BluetoothChatService(this, this.mHandler);
    this.mOutStringBuffer = new StringBuffer("");
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    Log.d("BluetoothChat", "onActivityResult " + paramInt2);
    switch (paramInt1)
    {
    default:
    case 1:
    case 2:
    }
    while (true)
    {
      return;
      if (paramInt2 != -1)
        continue;
      String str = paramIntent.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
      BluetoothDevice localBluetoothDevice = this.mBluetoothAdapter.getRemoteDevice(str);
      this.mChatService.connect(localBluetoothDevice);
      continue;
      if (paramInt2 == -1)
      {
        setupChat();
        continue;
      }
      Log.d("BluetoothChat", "BT not enabled");
      Toast.makeText(this, 2130968579, 0).show();
      finish();
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.e("BluetoothChat", "+++ ON CREATE +++");
    requestWindowFeature(7);
    setContentView(2130903043);
    getWindow().setFeatureInt(7, 2130903040);
    this.mTitle = ((TextView)findViewById(2131099648));
    this.mTitle.setText(2130968576);
    this.mTitle = ((TextView)findViewById(2131099649));
    this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (this.mBluetoothAdapter == null)
    {
      Toast.makeText(this, "Bluetooth is not available", 1).show();
      finish();
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131034112, paramMenu);
    return true;
  }

  public void onDestroy()
  {
    super.onDestroy();
    if (this.mChatService != null)
      this.mChatService.stop();
    Log.e("BluetoothChat", "--- ON DESTROY ---");
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    int i;
    switch (paramMenuItem.getItemId())
    {
    default:
      i = 0;
    case 2131099658:
    case 2131099659:
    }
    while (true)
    {
      return i;
      startActivityForResult(new Intent(this, DeviceListActivity.class), 1);
      i = 1;
      continue;
      ensureDiscoverable();
      i = 1;
    }
  }

  public void onPause()
  {
    monitorenter;
    try
    {
      super.onPause();
      Log.e("BluetoothChat", "- ON PAUSE -");
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void onResume()
  {
    monitorenter;
    try
    {
      super.onResume();
      Log.e("BluetoothChat", "+ ON RESUME +");
      if ((this.mChatService != null) && (this.mChatService.getState() == 0))
        this.mChatService.start();
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void onStart()
  {
    super.onStart();
    Log.e("BluetoothChat", "++ ON START ++");
    if (!this.mBluetoothAdapter.isEnabled())
      startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 2);
    while (true)
    {
      return;
      if (this.mChatService == null)
      {
        setupChat();
        continue;
      }
    }
  }

  public void onStop()
  {
    super.onStop();
    Log.e("BluetoothChat", "-- ON STOP --");
  }
}

