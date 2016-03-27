package com.spiretos.wearemote.communication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spiretos on 27/3/2016.
 */
public class Communicator implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    GoogleApiClient mGoogleApiClient;
    Context mContext;

    Node mNode;


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

        //Log.v("app", "sendMessage");

        Wearable.MessageApi.sendMessage(mGoogleApiClient, mNode.getId(), command, data).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>()
        {
            @Override
            public void onResult(MessageApi.SendMessageResult sendMessageResult)
            {
                //Log.v("app", "onResult");

                if (!sendMessageResult.getStatus().isSuccess())
                {
                    //Log.e("GoogleApi", "Failed to send message with status code: " + sendMessageResult.getStatus().getStatusCode());
                }
            }
        });
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
