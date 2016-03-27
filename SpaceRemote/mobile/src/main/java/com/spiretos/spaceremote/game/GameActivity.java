package com.spiretos.spaceremote.game;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.spiretos.spaceremote.R;

public class GameActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    public static final String START_ACTIVITY_PATH = "/start/MainActivity";


    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Log.v("app","create");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        Log.v("app","onConnected");

        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>()
        {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult)
            {
                Log.v("app","getConnectedNodes: "+getConnectedNodesResult.getNodes().size());

                for (Node node : getConnectedNodesResult.getNodes())
                {
                    sendMessage(node.getId());
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


    private void sendMessage(String node)
    {
        Log.v("app","sendMessage");

        Wearable.MessageApi.sendMessage(mGoogleApiClient, node, START_ACTIVITY_PATH, new byte[0]).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>()
        {
            @Override
            public void onResult(MessageApi.SendMessageResult sendMessageResult)
            {
                Log.v("app","onResult");

                if (!sendMessageResult.getStatus().isSuccess())
                {
                    Log.e("GoogleApi", "Failed to send message with status code: "
                            + sendMessageResult.getStatus().getStatusCode());
                }
            }
        });
    }
}
