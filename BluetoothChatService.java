/*           BluetoothChatService               */
/* This file contains implementation for the Bluetooth connection request handling
 * in all cases */

package com.example.android.BluetoothChat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothChatService
{
  private static final boolean D = true;
  private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
  private static final String NAME = "BluetoothChat";
  public static final int STATE_CONNECTED = 3;
  public static final int STATE_CONNECTING = 2;
  public static final int STATE_LISTEN = 1;
  public static final int STATE_NONE = 0;
  private static final String TAG = "BluetoothChatService";
  private AcceptThread mAcceptThread;
  private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
  private ConnectThread mConnectThread;
  private ConnectedThread mConnectedThread;
  private final Handler mHandler;
  private int mState = 0;

  public BluetoothChatService(Context paramContext, Handler paramHandler)
  {
    this.mHandler = paramHandler;
  }

  private void connectionFailed()
  {
    setState(1);
    Message localMessage = this.mHandler.obtainMessage(5);
    Bundle localBundle = new Bundle();
    localBundle.putString("toast", "Unable to connect device");
    localMessage.setData(localBundle);
    this.mHandler.sendMessage(localMessage);
  }
  /* Function to handle connection lost */
  private void connectionLost()
  {
    setState(1);
    Message localMessage = this.mHandler.obtainMessage(5);
    Bundle localBundle = new Bundle();
    localBundle.putString("toast", "Device connection was lost");
    localMessage.setData(localBundle);
    this.mHandler.sendMessage(localMessage);
  }

  private void setState(int paramInt)
  {
    monitorenter;
    try
    {
      Log.d("BluetoothChatService", "setState() " + this.mState + " -> " + paramInt);
      this.mState = paramInt;
      this.mHandler.obtainMessage(1, paramInt, -1).sendToTarget();
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

  /* Function handling the connection of devices via bluetooth */

  public void connect(BluetoothDevice paramBluetoothDevice)
  {
    monitorenter;
    try
    {
      Log.d("BluetoothChatService", "connect to: " + paramBluetoothDevice);
      if ((this.mState == 2) && (this.mConnectThread != null))
      {
        this.mConnectThread.cancel();
        this.mConnectThread = null;
      }
      if (this.mConnectedThread != null)
      {
        this.mConnectedThread.cancel();
        this.mConnectedThread = null;
      }
      this.mConnectThread = new ConnectThread(paramBluetoothDevice);
      this.mConnectThread.start();
      setState(2);
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

  /* Function to handle message interchange after connection is done */

  public void connected(BluetoothSocket paramBluetoothSocket, BluetoothDevice paramBluetoothDevice)
  {
    monitorenter;
    try
    {
      Log.d("BluetoothChatService", "connected");
      if (this.mConnectThread != null)
      {
        this.mConnectThread.cancel();
        this.mConnectThread = null;
      }
      if (this.mConnectedThread != null)
      {
        this.mConnectedThread.cancel();
        this.mConnectedThread = null;
      }
      if (this.mAcceptThread != null)
      {
        this.mAcceptThread.cancel();
        this.mAcceptThread = null;
      }
      this.mConnectedThread = new ConnectedThread(paramBluetoothSocket);
      this.mConnectedThread.start();
      Message localMessage = this.mHandler.obtainMessage(4);
      Bundle localBundle = new Bundle();
      localBundle.putString("device_name", paramBluetoothDevice.getName());
      localMessage.setData(localBundle);
      this.mHandler.sendMessage(localMessage);
      setState(3);
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

  public int getState()
  {
    monitorenter;
    try
    {
      int i = this.mState;
      monitorexit;
      return i;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void start()
  {
    monitorenter;
    try
    {
      Log.d("BluetoothChatService", "start");
      if (this.mConnectThread != null)
      {
        this.mConnectThread.cancel();
        this.mConnectThread = null;
      }
      if (this.mConnectedThread != null)
      {
        this.mConnectedThread.cancel();
        this.mConnectedThread = null;
      }
      if (this.mAcceptThread == null)
      {
        this.mAcceptThread = new AcceptThread();
        this.mAcceptThread.start();
      }
      setState(1);
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

  public void stop()
  {
    monitorenter;
    try
    {
      Log.d("BluetoothChatService", "stop");
      if (this.mConnectThread != null)
      {
        this.mConnectThread.cancel();
        this.mConnectThread = null;
      }
      if (this.mConnectedThread != null)
      {
        this.mConnectedThread.cancel();
        this.mConnectedThread = null;
      }
      if (this.mAcceptThread != null)
      {
        this.mAcceptThread.cancel();
        this.mAcceptThread = null;
      }
      setState(0);
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

  public void write(byte[] paramArrayOfByte)
  {
    monitorenter;
    try
    {
      if (this.mState != 3)
      {
        monitorexit;
      }
      else
      {
        ConnectedThread localConnectedThread = this.mConnectedThread;
        monitorexit;
        localConnectedThread.write(paramArrayOfByte);
      }
    }
    finally
    {
      monitorexit;
    }
  }

  private class AcceptThread extends Thread
  {
    private final BluetoothServerSocket mmServerSocket;

    public AcceptThread()
    {
      Object localObject = null;
      try
      {
        BluetoothServerSocket localBluetoothServerSocket = 
		BluetoothChatService.this.mAdapter.listenUsingRfcommWithServiceRecord("BluetoothChat", BluetoothChatService.MY_UUID);
        localObject = localBluetoothServerSocket;
        this.mmServerSocket = localObject;
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("BluetoothChatService", "listen() failed", localIOException);
      }
    }

    public void cancel()
    {
      Log.d("BluetoothChatService", "cancel " + this);
      try
      {
        this.mmServerSocket.close();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("BluetoothChatService", "close() of server failed", localIOException);
      }
    } 
  }

  private class ConnectThread extends Thread
  {
    private final BluetoothDevice mmDevice;
    private final BluetoothSocket mmSocket;

    public ConnectThread(BluetoothDevice arg2)
    {
      Object localObject1;
      this.mmDevice = localObject1;
      Object localObject2 = null;
      try
      {
        BluetoothSocket localBluetoothSocket = 
		localObject1.createRfcommSocketToServiceRecord(BluetoothChatService.MY_UUID);
        localObject2 = localBluetoothSocket;
        this.mmSocket = localObject2;
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("BluetoothChatService", "create() failed", localIOException);
      }
    }

    public void cancel()
    {
      try
      {
        this.mmSocket.close();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("BluetoothChatService", "close() of connect socket failed", localIOException);
      }
    }

    public void run()
    {
      Log.i("BluetoothChatService", "BEGIN mConnectThread");
      setName("ConnectThread");
      BluetoothChatService.this.mAdapter.cancelDiscovery();
      try
      {
        this.mmSocket.connect();
      }
      catch (IOException localIOException1)
      {
        synchronized (BluetoothChatService.this)
        {
          BluetoothChatService.this.mConnectThread = null;
          BluetoothChatService.this.connected(this.mmSocket, this.mmDevice);
          while (true)
          {
            return;
            localIOException1 = localIOException1;
            BluetoothChatService.this.connectionFailed();
            try
            {
              this.mmSocket.close();
              BluetoothChatService.this.start();
            }
            catch (IOException localIOException2)
            {
              while (true)
                Log.e("BluetoothChatService", "unable to close() socket during connection failure", localIOException2);
            }
          }
        }
      }
    }
  }

  private class ConnectedThread extends Thread
  {
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final BluetoothSocket mmSocket;

    public ConnectedThread(BluetoothSocket arg2)
    {
      Log.d("BluetoothChatService", "create ConnectedThread");
      Object localObject1;
      this.mmSocket = localObject1;
      InputStream localInputStream = null;
      Object localObject2 = null;
      try
      {
        localInputStream = localObject1.getInputStream();
        OutputStream localOutputStream = localObject1.getOutputStream();
        localObject2 = localOutputStream;
        this.mmInStream = localInputStream;
        this.mmOutStream = localObject2;
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("BluetoothChatService", "temp sockets not created", localIOException);
      }
    }

    public void cancel()
    {
      try
      {
        this.mmSocket.close();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("BluetoothChatService", "close() of connect socket failed", localIOException);
      }
    }

    public void run()
    {
      Log.i("BluetoothChatService", "BEGIN mConnectedThread");
      byte[] arrayOfByte = new byte[1024];
      try
      {
        while (true)
        {
          int i = this.mmInStream.read(arrayOfByte);
          BluetoothChatService.this.mHandler.obtainMessage(2, i, -1, arrayOfByte).sendToTarget();
        }
      }
      catch (IOException localIOException)
      {
        Log.e("BluetoothChatService", "disconnected", localIOException);
        BluetoothChatService.this.connectionLost();
      }
    }

    public void write(byte[] paramArrayOfByte)
    {
      try
      {
        this.mmOutStream.write(paramArrayOfByte);
        BluetoothChatService.this.mHandler.obtainMessage(3, -1, -1, paramArrayOfByte).sendToTarget();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("BluetoothChatService", "Exception during write", localIOException);
      }
    }
  }
}

