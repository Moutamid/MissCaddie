package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.models.CreditCard;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.PayPalSeller;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalAdvancedPayment;
import com.paypal.android.MEP.PayPalPayment;
import com.paypal.android.MEP.PayPalReceiverDetails;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CardPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    double amount = 0.0;
    private String id;
    private List<PayPalSeller> payPalSellerList;
    private RelativeLayout mainLayout;
    private static final int RC_READ_HONE_STATE = 1;
    public static String IPN_URL = "http://example.com/paypalpaymenttest/index.php";
    // The PayPal environment to be used - can also be ENV_NONE and ENV_LIVE.
    private static final int environment = PayPal.ENV_LIVE;
    // The ID of your application that you received from PayPal. This is the default sandbox ID right now.
    //private static final String appID = "APP-80W284485P519543T APP-0UT88657EL135842B";
    private static final int request = 1;
    // The amount that will be paid.
    private static BigDecimal PAYMENT_AMOUNT = null;
    // An ID used to update a database should you so choose to use it.
    private static String PAYMENT_ID = null;
    // The recipient of the payment.
    private static String RECIPIENT_EMAIL = null;

    protected static final int INITIALIZE_SUCCESS = 0;
    protected static final int INITIALIZE_FAILURE = 1;

    // Rather than call findViewByID all the time just store commonly used views.
    TextView labelSimplePayment;
    LinearLayout layoutSimplePayment;
    CheckoutButton launchSimplePayment;
    LinearLayout paypalButtonWrapper;
    Button exitApp;
    TextView title;
    TextView info;
    TextView extra;

    public static String resultTitle;
    public static String resultInfo;
    public static String resultExtra;
    private String appID = "";
    private SharedPreferencesManager manager;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Initialization cannot be done on the main thread.
        Thread libraryInitializationThread = new Thread() {
            @Override
            public void run() {
                makePaypalPaymentButton();
                // The library is initialized so let's create our CheckoutButton and update the UI.
            }
        };
        libraryInitializationThread.start();
        setContentView(R.layout.activity_card_payment);
        manager = new SharedPreferencesManager(CardPaymentActivity.this);
        appID = manager.retrieveString("apiKey","");
//        mainLayout = findViewById(R.id.paypalButton);
        info = (TextView)findViewById(R.id.info);

        layoutSimplePayment = (LinearLayout)findViewById(R.id.layoutSimplePayment);
        labelSimplePayment = (TextView)findViewById(R.id.labelSimplePayment);
        title = (TextView)findViewById(R.id.title);
        info = (TextView)findViewById(R.id.info);
        extra = (TextView)findViewById(R.id.extra);
        exitApp = (Button)findViewById(R.id.exitApp);
        paypalButtonWrapper = (LinearLayout)findViewById(R.id.paypalButtonWrapper);
        amount = getIntent().getDoubleExtra("price",0.0);
        id = getIntent().getStringExtra("id");
        payPalSellerList = new ArrayList<>();
    }

    private Handler hRefresh = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INITIALIZE_SUCCESS:
                    setupButtons();
                    break;
                case INITIALIZE_FAILURE:
                    showFailure();
                    break;
            }
        }
    };

    public void makePaypalPaymentButton() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) ==
                PackageManager.PERMISSION_GRANTED
        )  {
            initLibrary();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE}, RC_READ_HONE_STATE);
        }
    }

    /*private void getAppId() {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://api.sandbox.paypal.com/v1/oauth2/token");

        try {
            String text="CLIENT ID"+":"+"SECRET ID";
            byte[] data = text.getBytes("UTF-8");
            String base64 = Base64.encodeToString(data, Base64.NO_WRAP);

            httppost.addHeader("content-type", "application/x-www-form-urlencoded");
            httppost.addHeader("Authorization", "Basic " + base64);

            StringEntity se=new StringEntity("grant_type=client_credentials");
            httppost.setEntity(se);

// Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String responseContent = EntityUtils.toString(response.getEntity());
            Log.d("Response", responseContent );

        } catch (ClientProtocolException e) {
// TODO Auto-generated catch block
        } catch (IOException e) {
// TODO Auto-generated catch block
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_READ_HONE_STATE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initLibrary();
            }
        }
    }
    public void setupButtons() {
        PayPal pp = PayPal.getInstance();

        // Get the CheckoutButton. There are five different sizes.
        // The text on the button can either be of type TEXT_PAY or TEXT_DONATE.
        launchSimplePayment = pp.getCheckoutButton(this, PayPal.BUTTON_194x37, CheckoutButton.TEXT_PAY);
        // You'll need to have an OnClickListener for the CheckoutButton.
        // For this application, PayActivity implements OnClickListener and we
        // have the onClick() method below.
        launchSimplePayment.setOnClickListener(this);
        // The CheckoutButton is an android LinearLayout so we can add it to our display like any other View.
        paypalButtonWrapper.addView(launchSimplePayment);
        // Show our labels
        labelSimplePayment.setVisibility(View.VISIBLE);
        info.setText("");
        info.setVisibility(View.GONE);
    }

    public void showFailure() {
        title.setText("FAILURE");
        info.setText(R.string.pp_initialization_failure);
        title.setVisibility(View.VISIBLE);
        info.setVisibility(View.VISIBLE);
    }

    private void initLibrary() {
        // Only need to initialize the library once.
        try {
            PayPal pp = PayPal.getInstance();
            if (pp == null) {
                // Initialize PayPal

                pp = PayPal.initWithAppID(this, appID, environment);
//                pp = PayPal.initWithAppID(getApplicationContext(), appID, environment);
                // Sender will pay fees. By default this is the receiver.
                // It's free within the U.S. to send money to family and friends when you use only your PayPal
                // balance or bank account, or a combination of their PayPal balance and bank account.
                // So this really shouldn't come into play in our app.
                pp.setLanguage("en_US");
                pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);
                // Shipping for Peer to Peer payments is pointless.
                pp.setShippingEnabled(false);
                //   Toast.makeText(this, "Abc", Toast.LENGTH_SHORT).show();
            }

            if (pp.isLibraryInitialized()) {
                hRefresh.sendEmptyMessage(INITIALIZE_SUCCESS);
            } else {
                hRefresh.sendEmptyMessage(INITIALIZE_FAILURE);
            }
        }catch (Exception e){
            e.printStackTrace();
        //    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private PayPalAdvancedPayment extractPayPalinformation(List<PayPalSeller> sellerList) {
        PayPalAdvancedPayment payPalAdvancedPayment = new PayPalAdvancedPayment();
        for (PayPalSeller seller : sellerList) {
            PayPalReceiverDetails payPalReceiverDetails = new PayPalReceiverDetails();
            BigDecimal st = new BigDecimal(seller.getAmount());
            //     st = st.setScale(2, RoundingMode.HALF_UP);
            payPalReceiverDetails.setSubtotal(st);
            payPalReceiverDetails.setRecipient(seller.getEmail());
            payPalReceiverDetails.setPaymentType(PayPal.PAY_TYPE_PARALLEL);
            payPalAdvancedPayment.getReceivers().add(payPalReceiverDetails);
        }
        payPalAdvancedPayment.setCurrencyType("USD");
        //payPalAdvancedPayment.setMemo("123");
        payPalAdvancedPayment.setIpnUrl(IPN_URL);
        return payPalAdvancedPayment;
    }


    /*public static class PaypalAdaptiveConfiguration {
        public static final int ENVIRONMENT = PayPal.ENV_NONE;
        public static String APP_ID = "APP-80W284485P519543T";
        public static String IPN_URL = "http://example.com/paypalpaymenttest/index.php";
    }
    public void initLibrary() {
        PayPal pp = PayPal.getInstance();
        if (pp == null) {
            // This is the main initialization call that takes in your Context,
            // the Application ID, and the server you would like to connect to.
            pp = PayPal.initWithAppID(getApplicationContext(), PaypalAdaptiveConfiguration.APP_ID,
                    PaypalAdaptiveConfiguration.ENVIRONMENT);

            pp.setLanguage("en_US"); // Sets the language for the library.
            pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);
            pp.setShippingEnabled(false);
            pp.setDynamicAmountCalculationEnabled(false);
            mPaypalLibraryInit = true;
        }
        if (mPaypalLibraryInit) {
            showPayPalButton();
        } else {
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Loading PayPal Payment Library");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            Thread newThread = new Thread(this::checkForPayPalLibraryInit);
            newThread.start();
        }
    }

    private void showPayPalButton() {
        PayPal pp = PayPal.getInstance();
        CheckoutButton launchPayPalButton = pp.getCheckoutButton(this, PayPal.BUTTON_278x43,
                CheckoutButton.TEXT_PAY);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.
                LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
       // params.topMargin = 30;
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        launchPayPalButton.setLayoutParams(params);
        launchPayPalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog = ProgressDialog.show( CardPaymentActivity.this, null, "Loading...", true);
                mProgressDialog.setCanceledOnTouchOutside(false);
                try {
                    getCaddieDetails();
                    mProgressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //mPaypalCheckoutButtonView.setVisibility(View.GONE);
        mainLayout.addView(launchPayPalButton);
        //mPaypalCheckoutButtonView.performClick();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        mProgressDialog.dismiss();
    }*/

    private void getCaddieDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Caddie")
                .child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Model_Caddie model_caddie = snapshot.getValue(Model_Caddie.class);

                    double caddieHalf = amount*0.9;
                    double clientHalf = amount*0.1;
                    payPalSellerList.add(new PayPalSeller("digiconnect.tyler@gmail.com",
                            clientHalf));
                    payPalSellerList.add(new PayPalSeller(model_caddie.getEmail(),caddieHalf));

                    PayPalAdvancedPayment payPalAdvancedPayment = extractPayPalinformation(payPalSellerList);

                    Intent checkoutIntent = PayPal.getInstance().checkout(payPalAdvancedPayment, CardPaymentActivity.this,
                            new ResultDelegate(PAYMENT_ID));
                    // Use the android's startActivityForResult() and pass in our Intent. This will start the library.
                    startActivityForResult(checkoutIntent, request);
                    /*Intent checkoutIntent = PayPal.getInstance().checkout(payPalAdvancedPayment,
                            CardPaymentActivity.this);
                    startActivityForResult(checkoutIntent, RC_PAYPAL_ADAPTIVE);*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*private void checkForPayPalLibraryInit() {
        while (!mPaypalLibraryInit) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                DialogInterface.OnClickListener positiveButtonOnClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                };
            }
        }
        runOnUiThread(this::showPayPalButton);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            if (requestCode == RC_PAYPAL_ADAPTIVE) {
                Log.d("TAG", "onActivityResult:requestCode " + requestCode);
                Log.d("TAG", "onActivityResult:resultCode " + resultCode);
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        String payKey = intent.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
                        String data = intent.getStringExtra(PayPalActivity.EXTRA_PAYMENT_STATUS);
                        String payInfo = intent.getStringExtra(PayPalActivity.EXTRA_PAYMENT_INFO);
                        Log.d("info",payInfo);
                        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
                  //      redirectToOrderActivity(true);
                        break;
                    case Activity.RESULT_CANCELED:
                        this.finish();
                        Toast.makeText(getApplicationContext(), "Payment Cancelled", Toast.LENGTH_SHORT).show();
                        break;
                    case PayPalActivity.RESULT_FAILURE:
                        String errorMessage = intent.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
                        String id = intent.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
                        Log.d("paypal error", errorMessage + " " + id);
                        Log.d("intent", intent.getExtras().toString());
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        this.finish();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }*/
    @Override
    public void onClick(View v) {
        // Click handler handles both the PayPal button and the exitApp button
        if (v == launchSimplePayment) {
            getCaddieDetails();
        } else if (v == exitApp) {
            Intent in = new Intent();
            in.putExtra("payment", "unpaid");
            // Setting the Requestcode 1.
            setResult(1, in);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != request) {
            return;
        }
        if (resultTitle == "SUCCESS") {
            Intent in = new Intent();
            in.putExtra("payment", "paid");
            setResult(22, in);

        } else if (resultTitle == "FAILURE") {
            Intent in = new Intent();
            in.putExtra("payment", "unpaid");
            setResult(22, in);
        } else if (resultTitle == "CANCELED") {
            Intent in = new Intent();
            in.putExtra("payment", "unpaid");
            setResult(22, in);
        }

        launchSimplePayment.updateButton();

        title.setText(resultTitle);
        title.setVisibility(View.VISIBLE);
        info.setText(resultInfo);
        info.setVisibility(View.VISIBLE);
        extra.setText(resultExtra);
        extra.setVisibility(View.VISIBLE);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CardPaymentActivity.this,Dashboard_Golfer.class));
        finish();
        Animatoo.animateZoom(CardPaymentActivity.this);
    }
}