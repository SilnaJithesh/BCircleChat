
/*             DeviceListActivity          */
/* This file contains implementation of Device descovery for 
 * Bluetooth Connection */

package com.example.android.BluetoothChat;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.Iterator;
import java.util.Set;

public class DeviceListActivity extends Activity
{
  private static final boolean D = true;
  public static String EXTRA_DEVICE_ADDRESS = "device_address";
  private static final String TAG = "DeviceListActivity";
  private BluetoothAdapter mBtAdapter;
  private AdapterView.OnItemClickListener mDeviceClickListener = new DeviceListActivity.1(this);
  private ArrayAdapter<String> mNewDevicesArrayAdapter;
  private ArrayAdapter<String> mPairedDevicesArrayAdapter;
  private final BroadcastReceiver mReceiver = new DeviceListActivity.2(this);

  private void doDiscovery()
  {
    Log.d("DeviceListActivity", "doDiscovery()");
    setProgressBarIndeterminateVisibility(true);
    setTitle(2130968583);
    findViewById(2131099652).setVisibility(0);
    if (this.mBtAdapter.isDiscovering())
      this.mBtAdapter.cancelDiscovery();
    this.mBtAdapter.startDiscovery();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(5);
    setContentView(2130903041);
    setResult(0);
    ((Button)findViewById(2131099654)).setOnClickListener(new DeviceListActivity.3(this));
    this.mPairedDevicesArrayAdapter = new ArrayAdapter(this, 2130903042);
    this.mNewDevicesArrayAdapter = new ArrayAdapter(this, 2130903042);
    ListView localListView1 = (ListView)findViewById(2131099651);
    localListView1.setAdapter(this.mPairedDevicesArrayAdapter);
    localListView1.setOnItemClickListener(this.mDeviceClickListener);
    ListView localListView2 = (ListView)findViewById(2131099653);
    localListView2.setAdapter(this.mNewDevicesArrayAdapter);
    localListView2.setOnItemClickListener(this.mDeviceClickListener);
    IntentFilter localIntentFilter1 = new IntentFilter("android.bluetooth.device.action.FOUND");
    registerReceiver(this.mReceiver, localIntentFilter1);
    IntentFilter localIntentFilter2 = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
    registerReceiver(this.mReceiver, localIntentFilter2);
    this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    Set localSet = this.mBtAdapter.getBondedDevices();
    Iterator localIterator;
    if (localSet.size() > 0)
    {
      findViewById(2131099650).setVisibility(0);
      localIterator = localSet.iterator();
      if (localIterator.hasNext());
    }
    while (true)
    {
      return;
      BluetoothDevice localBluetoothDevice = (BluetoothDevice)localIterator.next();
      this.mPairedDevicesArrayAdapter.add(localBluetoothDevice.getName() + "\n" + localBluetoothDevice.getAddress());
      break;
      String str = getResources().getText(2130968585).toString();
      this.mPairedDevicesArrayAdapter.add(str);
    }
  }

  protected void onDestroy()
  {
    super.onDestroy();
    if (this.mBtAdapter != null)
      this.mBtAdapter.cancelDiscovery();
    unregisterReceiver(this.mReceiver);
  }
}

 