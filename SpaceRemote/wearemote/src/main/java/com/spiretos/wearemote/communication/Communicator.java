package com.spiretos.wearemote.communication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spiretos on 27/3/2016.
 */
public class Communicator implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    public static final int TYPE_MESSAGE_API = 0;
    public static final int TYPE_CHANNEL_API = 1;

    public static final String MESSAGE_START_ACTIVITY="MESSAGE_START_ACTIVITY";
    public static final String MESSAGE_FINISH_ACTIVITY="MESSAGE_FINISH_ACTIVITY";

    public static final String BROADCAST_SENSOR_ACTIVITY_COMMAND="com.spiretos.wearemote.SENSOR_ACTIVITY_COMMAND";


    GoogleApiClient mGoogleApiClient;
    Context mContext;

    Node mNode;
    private OutputStream mOutputStream;

    boolean channelCreated;



    public Communicator(Context context)
    {
        mContext = context;
    }

    public void connect()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        Log.v("app", "onConnected");

        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>()
        {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult)
            {
                int nodesSize = getConnectedNodesResult.getNodes().size();

                Log.v("app", "getConnectedNodes: " + nodesSize);

                if (nodesSize > 0)
                {
                    mNode = getConnectedNodesResult.getNodes().get(0);
                    sendConnected();

                    //createChannel();
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }


    public void sendMessage(String command)
    {
        sendMessage(command, new byte[0]);
    }

    public void sendMessage(String command, byte[] data)
    {
        if (mNode == null)
            return;

        Wearable.MessageApi.sendMessage(mGoogleApiClient, mNode.getId(), command, data).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>()
        {
            @Override
            public void onResult(MessageApi.SendMessageResult sendMessageResult)
            {
                /*if (!sendMessageResult.getStatus().isSuccess())
                {

                }*/
            }
        });
    }



    private void createChannel()
    {
        Wearable.ChannelApi.openChannel(mGoogleApiClient, mNode.getId(), "data_channel").setResultCallback(new ResultCallback<ChannelApi.OpenChannelResult>()
        {
            @Override
            public void onResult(@NonNull ChannelApi.OpenChannelResult openChannelResult)
            {
                Channel channel = openChannelResult.getChannel();
                channel.getOutputStream(mGoogleApiClient).setResultCallback(new ResultCallback<Channel.GetOutputStreamResult>()
                {
                    @Override
                    public void onResult(@NonNull Channel.GetOutputStreamResult getOutputStreamResult)
                    {
                        mOutputStream = getOutputStreamResult.getOutputStream();
                        Log.v("channel", "created");
                        channelCreated=true;
                    }
                });
            }
        });
    }

    public void writeData(String command, byte[] data)
    {
        try
        {
            if (mOutputStream!=null)
            {
                mOutputStream.write(data);
                //Log.v("data","wear sent");
            }
        }
        catch (IOException e)
        {
            //e.printStackTrace();
        }
    }


    public boolean isChannelCreated()
    {
        return channelCreated;
    }






    // EVENTS

    public void setCommunicationListener(CommunicationListener listener)
    {
        addListener(listener);
    }

    public void removeCommunicationListener(CommunicationListener listener)
    {
        removeListener(listener);
    }

    private void sendConnected()
    {
        for (CommunicationListener l : mListeners)
        {
            l.onConnected();
        }
    }

    public interface CommunicationListener
    {
        public void onConnected();
    }

    List<CommunicationListener> mListeners = new ArrayList<CommunicationListener>();

    private void addListener(CommunicationListener toAdd)
    {
        mListeners.add(toAdd);
    }

    public void removeListener(CommunicationListener toRemove)
    {
        mListeners.remove(toRemove);
    }

}
