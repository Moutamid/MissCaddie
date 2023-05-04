package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.moutamid.misscaddie.Notifications.Client;
import com.moutamid.misscaddie.Notifications.Data;
import com.moutamid.misscaddie.Notifications.MyResponse;
import com.moutamid.misscaddie.Notifications.Sender;
import com.moutamid.misscaddie.Notifications.Token;
import com.moutamid.misscaddie.listners.APIService;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.api.payments.Currency;
import com.paypal.api.payments.Payout;
import com.paypal.api.payments.PayoutBatch;
import com.paypal.api.payments.PayoutItem;
import com.paypal.api.payments.PayoutSenderBatchHeader;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.PaymentButtonIntent;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.Order;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {

    private SharedPreferencesManager manager;
    private String clientId = "";
    private String clientSecret = "";
    private String uid;
    private String id;
    private double amount;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;

    PaymentButtonContainer button;
    String accessToken;
    Currency amountACur, amountBCur;
    String url = "https://api-m.paypal.com/v1/oauth2/token";
    private DatabaseReference requestsDb;
    private FirebaseAuth mAuth;
    private APIService apiService;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_pay);
        manager = new SharedPreferencesManager(PayActivity.this);
        requestsDb = FirebaseDatabase.getInstance().getReference().child("Requests");
        clientId = manager.retrieveString("clientId","");
        clientSecret = manager.retrieveString("clientSecret","");
        id = getIntent().getStringExtra("id");
        uid = getIntent().getStringExtra("user_id");
        amount = getIntent().getDoubleExtra("price",0.0);
        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        getAccessToken();
        button = findViewById(R.id.paypalButton);
        button.setup(
                new CreateOrder() {
                    @Override
                    public void create(@NotNull CreateOrderActions createOrderActions) {
                        ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                        purchaseUnits.add(
                                new PurchaseUnit.Builder()
                                        .amount(
                                                new Amount.Builder()
                                                        .currencyCode(CurrencyCode.USD)
                                                        .value(String.valueOf(amount))
                                                        .build()
                                        )
                                        .build()
                        );
                        Order order = new Order(
                                OrderIntent.CAPTURE,
                                new AppContext.Builder()
                                        .userAction(UserAction.PAY_NOW)
                                        .build(),
                                purchaseUnits
                        );
                        createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                    }
                },
                new OnApprove() {
                    @Override
                    public void onApprove(@NotNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                getCaddieDetails();
                                Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));
                            }
                        });
                    }
                }
        );
    }

    private void sendNotification(String uId, String message) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(uId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data("Caddie",user.getUid(), R.mipmap.ic_launcher, message,
                            "Request Update", uId);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if(response.code() == 200){
                                        if (response.body().success != 1){
                                            System.out.println("Failed to send notification!");
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getCaddieDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Caddie")
                .child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Model_Caddie model_caddie = snapshot.getValue(Model_Caddie.class);

                    double caddieHalf = amount*0.8;
                    //double clientHalf = amount*0.1;
                    try {
                        sendMoney(model_caddie.getEmail(),String.valueOf(caddieHalf));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    String encodeStringToBase64(){
        String input = clientId + ":" + clientSecret;
        String encodedString = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encodedString = Base64.getEncoder().encodeToString(input.getBytes());
        }
        return encodedString;
    }

    void getAccessToken() {
        String AUTH = encodeStringToBase64();
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");
        client.addHeader("Content-type", "application/x-www-form-urlencoded");
        client.addHeader("Authorization", "Basic " + AUTH);
        String jsonString = "grant_type=client_credentials";

        Log.d("RESPONSE",  "Auth : " + AUTH);

        HttpEntity entity = new StringEntity(jsonString, "utf-8");

        client.post(this, url, entity, "application/x-www-form-urlencoded",
                new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                Log.e("RESPONSE", ""+response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    accessToken = jobj.getString("access_token");

                    Log.d("RESPONSE",  "AccessToken : " + accessToken);
                    Log.d("RESPONSE",  response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendMoney(String email, String amount) throws IOException {
        URL url = new URL("https://api-m.paypal.com/v1/payments/payouts");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        String authValue = "Bearer " + accessToken;
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Authorization", authValue);

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        String timestamp = String.valueOf(System.currentTimeMillis());
        String senderbatchId = "Payouts_"+timestamp;
        writer.write("{ \"sender_batch_header\": " +
                "{ \"sender_batch_id\": \""+senderbatchId+"\", " +
                "\"email_subject\": \"You have a payout!\"," +
                " \"email_message\": \"You have received a payout! Thanks for using our service!\" }, " +
                "\"items\": " +
                "[ { " +
                "\"recipient_type\": \"EMAIL\", " +
                "\"amount\": " +
                "{ \"value\": \""+amount+"\"," +
                " \"currency\": \"USD\" " +
                "}, " +
                "\"note\": \"Thanks for your patronage!\"," +
                " \"sender_item_id\": \""+timestamp+"\"," +
                " \"receiver\": \""+email+"\"," +
                " \"recipient_wallet\": \"PAYPAL\" } " +
                "] " +
                "}");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        Log.d("Payout", response);
        Toast.makeText(PayActivity.this,"Payment Successfully transferred",Toast.LENGTH_SHORT).show();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("status_title","Accepted");
        requestsDb.child(id).updateChildren(hashMap);
        sendNotification(uid,"Your request has been accepted!");

        //Toast.makeText(PayActivity.this,response,Toast.LENGTH_SHORT).show();
    }

    private void sendPayouts() {
        amountACur = new Currency();
        amountACur.setValue("0.1");
        amountACur.setCurrency("USD");

        amountBCur = new Currency();
        amountBCur.setValue("0.1");
        amountBCur.setCurrency("USD");
        com.paypal.api.payments.Amount amountA = new com.paypal.api.payments.Amount();
        amountA.setCurrency("USD");
        amountA.setTotal("10");
        Transaction transactionA = new Transaction();;
        transactionA.setAmount(amountA);
        PayoutSenderBatchHeader senderBatchHeaderA = new PayoutSenderBatchHeader();
        senderBatchHeaderA.setEmailSubject("Payment from app");
        PayoutItem payoutItemA = new PayoutItem();
        payoutItemA.setRecipientType("Email");
        payoutItemA.setReceiver("buyer@misscaddie.com");
        payoutItemA.setAmount(amountACur);
        payoutItemA.setNote("Payment from app");
        Payout payoutA = new Payout();
        payoutA.setSenderBatchHeader(senderBatchHeaderA);
        payoutA.setItems(Collections.singletonList(payoutItemA));

        //APIContext apiContext = new APIContext(CONFIG_CLIENT_ID, CONFIG_CLIENT_SECRET, CONFIG_ENVIRONMENT);
        PayoutBatch batchA = null;
        try {
            batchA = payoutA.create(accessToken, null);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        Log.d("RESPONSE", "BatchA :"+batchA.toString());


    }
}