package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.qtechnetworks.ptplatform.Controller.adapters.ChatAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Chat.ChatIdResults;
import com.qtechnetworks.ptplatform.Model.Beans.Chat.ChatResults;
import com.qtechnetworks.ptplatform.Model.Beans.Chat.Datum;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import org.json.JSONException;
import org.json.JSONObject;
import io.socket.client.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import io.reactivex.disposables.Disposable;
import io.socket.client.IO;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;
import okhttp3.Call;
import okhttp3.OkHttpClient;

public class ChatSingleFragment extends Fragment implements CallBack {

    private RecyclerView chatRecyclerView;
    private ImageView sendIcon;
    public static EditText messageEditText;

    private io.socket.client.Socket mSocket;
    String chatID;

    int tag = 0, userID, arraySize=0;
    boolean firstTime = true;

    ArrayList<Datum> newChatResults = new ArrayList<>();
    ChatResults newChat = new ChatResults();

    ChatResults chatResults;
    ChatAdapter chatAdapter;

    public ChatSingleFragment() {

    }

    public ChatSingleFragment(int userID) {
        this.userID = userID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_single, container, false);

        initials(view);
        clicks();

        if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach")){
            createChat(String.valueOf(userID));
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee")){
            createChat(String.valueOf(PreferencesUtils.getCoach(getContext()).getId()));
        }

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        sendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectSocketSend(chatID, String.valueOf(PreferencesUtils.getUser(getContext()).getId()), messageEditText.getText().toString());
            }
        });


//        chatRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//
//                if (firstTime){
//                    firstTime = false;
//                } else {
//                    getChat(chatID, arraySize);
//                }
//            }
//        });

    }

    private void initials(View view) {
        chatRecyclerView = view.findViewById(R.id.chat_recyclerview);
        sendIcon = view.findViewById(R.id.send_icon);
        messageEditText = view.findViewById(R.id.message);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private final TrustManager[] trustAllCerts= new TrustManager[] { new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[] {};
        }

        public void checkClientTrusted(X509Certificate[] chain,
                                       String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain,
                                       String authType) {
        }
    }};

    private void connectSocket(String chatID) {

        SSLContext mySSLContext = null;
        try {
            mySSLContext = SSLContext.getInstance("TLS");
            try {
                mySSLContext.init(null, trustAllCerts, null);
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        HostnameVerifier myHostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        TrustManager[] trustAllCerts= new TrustManager[] { new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        OkHttpClient okHttpClient =new OkHttpClient.Builder()
                .hostnameVerifier(myHostnameVerifier)
                .sslSocketFactory(mySSLContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                .build();

        try {
            IO.Options options = new IO.Options();
            options.transports = new String[] { WebSocket.NAME };

            options.callFactory = (Call.Factory) okHttpClient;
            options.webSocketFactory = (okhttp3.WebSocket.Factory) okHttpClient;

            IO.setDefaultOkHttpWebSocketFactory((okhttp3.WebSocket.Factory) okHttpClient);
            IO.setDefaultOkHttpCallFactory((Call.Factory) okHttpClient);

            mSocket = IO.socket("https://qtechnetworks.co:3001",options);
            mSocket.connect();

                mSocket.on(Socket.EVENT_CONNECT, new io.socket.emitter.Emitter.Listener() {
                    @Override
                    public void call(Object... args) {

                        JSONObject jsonObject = new JSONObject();

                        try {
                            jsonObject.accumulate("chat_id", chatID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mSocket.emit("join_room", jsonObject);
                        mSocket.on("receive_message", new io.socket.emitter.Emitter.Listener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void call(Object... args) {

                                Datum chatMessage = (Datum) MyApplication.getInstance().getGson().fromJson(Arrays.stream(args).findAny().toString().split("Optional")[1].replace("[", "").replace("]", ""), Datum.class);

                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {

                                        ArrayList<Datum> messages = chatResults.getData();
                                        messages.add(chatMessage);
                                        chatResults.setData(messages);
                                        chatAdapter = new ChatAdapter(getContext(), chatResults.getData());
                                        chatRecyclerView.setAdapter(chatAdapter);

//                                        chatAdapter.notifyAll();

                                    }
                                });
                            }
                        });
                    }

                }).on(Socket.EVENT_DISCONNECT, new io.socket.emitter.Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        String g = "";
                    }
                }).on("error", new io.socket.emitter.Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        String g = "";
                    }
                }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        String g = "";
                    }
                });



        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

    }

    private void connectSocketSend(String chatID, String senderID, String message) {

        SSLContext mySSLContext = null;
        try {
            mySSLContext = SSLContext.getInstance("TLS");
            try {
                mySSLContext.init(null, trustAllCerts, null);
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        HostnameVerifier myHostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        TrustManager[] trustAllCerts= new TrustManager[] { new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        OkHttpClient okHttpClient =new OkHttpClient.Builder()
                .hostnameVerifier(myHostnameVerifier)
                .sslSocketFactory(mySSLContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                .build();

        try {
            IO.Options options = new IO.Options();
            options.transports = new String[] { WebSocket.NAME };

            options.callFactory = (Call.Factory) okHttpClient;
            options.webSocketFactory = (okhttp3.WebSocket.Factory) okHttpClient;

            IO.setDefaultOkHttpWebSocketFactory((okhttp3.WebSocket.Factory) okHttpClient);
            IO.setDefaultOkHttpCallFactory((Call.Factory) okHttpClient);

            mSocket = IO.socket("https://qtechnetworks.co:3001",options);
            mSocket.connect();
                mSocket.on(Socket.EVENT_CONNECT, new io.socket.emitter.Emitter.Listener() {
                    @Override
                    public void call(Object... args) {

                        JSONObject jsonObject=new JSONObject();

                        try {
                            jsonObject.accumulate("chat_id", chatID);
                            jsonObject.accumulate("sender_id",senderID);
                            jsonObject.accumulate("message",message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                getChat(chatID, arraySize);
                            }
                        } );

                        mSocket.emit("send_message", jsonObject);
                        mSocket.disconnect();
                        connectSocket(chatID);

                    }

                }).on(Socket.EVENT_DISCONNECT, new io.socket.emitter.Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        String g = "";
                    }
                }).on("error", new io.socket.emitter.Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        String g = "";
                    }
                }).on(Socket.EVENT_CONNECT_ERROR, new io.socket.emitter.Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        String g = "";
                    }

                });

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

    }

    private void getChat (String id, int skip) {
        tag = AppConstants.Chat_TAG;

        HashMap<String,Object> params = new HashMap<>();

        params.put("skip", 0);

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().get(getContext(), AppConstants.Chat_URL+"/"+id+"/messages", AppConstants.Chat_TAG , ChatResults.class,params);

    }

    private void createChat(String receivedID) {

        tag = 2;

        HashMap <String,Object> params = new HashMap<>();

        params.put("received_id", receivedID);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.Chat_URL, 2 , ChatIdResults.class,params);
    }

    @Override
    public void onSubscribe(Disposable d) {  }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess) {

            messageEditText.getText().clear();

            switch (tag){
                case AppConstants.Chat_TAG:

                    chatResults = (ChatResults) result;

                    chatAdapter = new ChatAdapter(getContext(), chatResults.getData());
                    chatRecyclerView.setAdapter(chatAdapter);
                    break;

                case 2:
                    ChatIdResults chatIdResults = (ChatIdResults) result;
                    chatID = String.valueOf(chatIdResults.getData().getChatId());
                    break;

            }
        }
    }

    @Override
    public void onError (Throwable e) {}

    @Override
    public void onComplete() {

        try {
            if (tag == 2) {
                getChat(chatID, arraySize);
                connectSocket(chatID);
            }
        } catch (Exception e) {}
    }
}