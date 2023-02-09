package com.moutamid.misscaddie.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.CardPaymentActivity;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.SharedPreferencesManager;
import com.moutamid.misscaddie.adapters.PaymentRequestesAdapter;
import com.moutamid.misscaddie.listners.ItemClickListener;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.PayPalSeller;
import com.moutamid.misscaddie.models.RequestsModel;
import com.moutamid.misscaddie.models.ServiceListModel;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalAdvancedPayment;
import com.paypal.android.MEP.PayPalReceiverDetails;
import com.stripe.Stripe;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;
import com.stripe.param.TransferCreateParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentRequestFragment extends Fragment {

    RecyclerView requestRC;
    private DatabaseReference db,db1;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ArrayList<RequestsModel> itemList;
    private SharedPreferencesManager manager;
    private String apiKey = "";
    private String pubKey = "";
    private String customerID,emphericalKey,clientSecret;
    private double amount = 0.0;
    private PaymentSheet paymentSheet;
    private String group;
    private String key = "";
    private ProgressDialog progressDialog;
    private List<PayPalSeller> payPalSellerList;
    private RelativeLayout mainLayout;
    private boolean mPaypalLibraryInit = false;
    private static final int RC_READ_HONE_STATE = 1;
    private static final int RC_PAYPAL_ADAPTIVE = 101;
    private static final String TAG = "paymentExample";
    /**
     * - Set to PayPalConfiguration.ENVIRONMENT_PRODUCTION to move real money.
     *
     * - Set to PayPalConfiguration.ENVIRONMENT_SANDBOX to use your test credentials
     * from https://developer.paypal.com
     *
     * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
     * without communicating to PayPal's servers.
     */
 /*   private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "AatLJX9RA1lOJ4nUq7jbvSHm-0nOvNSN-hmrmq-XKezn1zIgmI6b9qE2QRYc3ialy704agpDPFxlWnw6";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;


    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));*/

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.fragment_payment_request, container, false);
        requestRC = view.findViewById(R.id.recyclerView_golfer);
        mainLayout = view.findViewById(R.id.relativeLayout);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Requests");
        db1 = FirebaseDatabase.getInstance().getReference().child("BankAccounts");
        manager = new SharedPreferencesManager(getActivity());
        progressDialog = new ProgressDialog(requireContext());
        payPalSellerList = new ArrayList<>();
        //apiKey = manager.retrieveString("apiKey","");
      //  pubKey = manager.retrieveString("publisherKey","");
        itemList = new ArrayList<>();
        if (isAdded()) {
            getRequests();
            requestRC.setLayoutManager(new LinearLayoutManager(getActivity()));
            requestRC.setHasFixedSize(false);
         /*   Intent intent = new Intent(getActivity(), PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            getActivity().startService(intent);*/
         /*   PaymentConfiguration.init(requireActivity(), pubKey);
            paymentSheet = new PaymentSheet(this, new PaymentSheetResultCallback() {
            @Override
            public void onPaymentSheetResult(@NonNull PaymentSheetResult paymentSheetResult) {
                onPaymentResult(paymentSheetResult);
            }
            });
            ConfirmPaymentIntentParams confirmPaymentIntentParams = ConfirmPaymentIntentParams
                    .create(clientSecret);
            stripe.confirmPayment(this, confirmPaymentIntentParams);*/
        }
        return view;
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed){
            Toast.makeText(getActivity(), "Payment Successful", Toast.LENGTH_SHORT).show();
            transfers();

            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("status_title","Accepted");
            //hashMap.put("payment",false);
            db.child(key).updateChildren(hashMap);
        }
    }

    private void transfers() {
        Stripe.apiKey = apiKey;

        db1.child(group)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String accountId = snapshot.child("accountId").getValue().toString();
                            long half = (long) (amount*0.9);
                            TransferCreateParams secondTransferParams =
                                    TransferCreateParams.builder()
                                            .setAmount(half)
                                            .setCurrency("usd")
                                            .setDestination(accountId)
                                            .setTransferGroup(group)
                                            .build();


                            try {
                                Transfer secondTransfer = Transfer.create(secondTransferParams);
                                Toast.makeText(getActivity(), secondTransfer.getId(), Toast.LENGTH_SHORT).show();
                            } catch (StripeException e) {
                                e.printStackTrace();
                                Log.d("transfer",e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        TransferCreateParams secondTransferParams =
                TransferCreateParams.builder()
                        .setAmount((long) (amount*0.1))
                        .setCurrency("usd")
                        .setDestination("acct_1M6M97BLuTeRO57f")
                        .setTransferGroup(group)
                        .build();


        try {
            Transfer secondTransfer = Transfer.create(secondTransferParams);
            Toast.makeText(getActivity(), secondTransfer.getId(), Toast.LENGTH_SHORT).show();
        } catch (StripeException e) {
            e.printStackTrace();
            Log.d("transfer",e.getMessage());
        }
    }

    private void getRequests() {
        //Query query = db.orderByChild("payment").equalTo(false);
        Query query = db.orderByChild("status_title").equalTo("payment_request");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    itemList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        RequestsModel model = ds.getValue(RequestsModel.class);
                        if(model.getUserId().equals(user.getUid())) {
                            itemList.add(model);
                        }
                    }

                    PaymentRequestesAdapter adapter = new PaymentRequestesAdapter(itemList, getActivity());
                    requestRC.setAdapter(adapter);
                    adapter.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view) {
                            RequestsModel requestsModel = itemList.get(position);
                           // progressDialog.setMessage("Please Wait.....");
                            //progressDialog.show();
                            group = requestsModel.getCaddieId();
                            key = requestsModel.getId();
                            for (ServiceListModel services : requestsModel.getTableRows()){
                                amount += Double.parseDouble(services.getPrice());
                            }

                            Intent intent = new Intent(getActivity(), CardPaymentActivity.class);
                            intent.putExtra("price",amount);
                            intent.putExtra("id",group);
                            startActivity(intent);
//                            initLibrary();
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkout(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            customerID = jsonObject.getString("id");
                            //Toast.makeText(getActivity(), customerID, Toast.LENGTH_SHORT).show();

                            getEmphericalKey(customerID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer " + apiKey);

                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getEmphericalKey(String customerID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            emphericalKey = jsonObject.getString("id");
                          //  Toast.makeText(getActivity(), emphericalKey, Toast.LENGTH_SHORT).show();

                            getClientSecret(customerID,emphericalKey);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer " + apiKey);
                headers.put("Stripe-Version","2022-11-15");
                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("customer",customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getClientSecret(String customerID, String emphericalKey) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            clientSecret = jsonObject.getString("client_secret");
//                            Toast.makeText(getActivity(), clientSecret, Toast.LENGTH_SHORT).show();
                            paymentFlow();

                        } catch (JSONException e) {
                            e.printStackTrace();
                         //   Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer " + apiKey);
                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("customer",customerID);
                params.put("amount",amount + "00");
                params.put("currency","usd");
                params.put("automatic_payment_methods[enabled]","true");
                params.put("transfer_group",group);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void paymentFlow() {

        paymentSheet.presentWithPaymentIntent(clientSecret,new PaymentSheet.Configuration("MisCaddie",
                new PaymentSheet.CustomerConfiguration(
                        customerID,emphericalKey
                )));
        progressDialog.dismiss();
    }


    private void transferToCaddie(){

    /*    db1.child(group)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String accountId = snapshot.child("accountId").getValue().toString();
                            double half = amount*0.9;
                            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                    "https://api.stripe.com/v1/transfers",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String transfer = jsonObject.getString("id");
                                                Toast.makeText(getActivity(), transfer, Toast.LENGTH_SHORT).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> headers = new HashMap<>();
                                    headers.put("Authorization","Bearer " + apiKey);
                                    return headers;
                                }

                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("amount",String.valueOf(half));
                                    params.put("currency","usd");
                                    params.put("destination",accountId);
                                    params.put("transfer_group",group);
                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                            requestQueue.add(stringRequest);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
    }

    private void clientTransfer(){
        double half = amount*0.1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/transfers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String transfer = jsonObject.getString("id");
                            Toast.makeText(getActivity(), transfer, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer " + apiKey);
                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("amount",String.valueOf(half));
                params.put("currency","usd");
                params.put("destination","acct_1M8TcwPYTcLBOhet");
                params.put("transfer_group",group);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



    public void makePaypalPaymentButton() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED) {
            initLibrary();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.READ_PHONE_STATE,}, RC_READ_HONE_STATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_READ_HONE_STATE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initLibrary();
            }
        }
    }

    private void initLibrary() {
        try {
            PayPal pp = PayPal.getInstance();
            if (pp == null) {
                // This is the main initialization call that takes in your Context,
                // the Application ID, and the server you would like to connect to.
                pp = PayPal.initWithAppID(this.getActivity(), "APP-80W284485P519543T", PayPal.ENV_SANDBOX);

                // -- These are required settings.
                pp.setLanguage("en_US"); // Sets the language for the library.
                // --

                // -- These are a few of the optional settings.
                // Sets the fees payer. If there are fees for the transaction, this
                // person will pay for them. Possible values are FEEPAYER_SENDER,
                // FEEPAYER_PRIMARYRECEIVER, FEEPAYER_EACHRECEIVER, and
                // FEEPAYER_SECONDARYONLY.
                pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);
                // Set to true if the transaction will require shipping.
                pp.setShippingEnabled(true);
                // Dynamic Amount Calculation allows you to set tax and shipping
                // amounts based on the user's shipping address. Shipping must be
                // enabled for Dynamic Amount Calculation. This also requires you to
                // create a class that implements PaymentAdjuster and Serializable.
                pp.setDynamicAmountCalculationEnabled(false);
                // --

                Toast.makeText(getActivity(), " null", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Not null", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.d("error",e.getMessage());
        }
       /* CheckoutButton launchPayPalButton =
                ppObj.getCheckoutButton(getActivity(), PayPal.BUTTON_278x43,
                        CheckoutButton.TEXT_PAY);
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.bottomMargin = 10;
        launchPayPalButton.setLayoutParams(params);
        launchPayPalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mainLayout.addView(launchPayPalButton);*/
    }
    private void getCaddieDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Caddie")
                .child(group);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Model_Caddie model_caddie = snapshot.getValue(Model_Caddie.class);

                    int caddieHalf = (int) (amount*0.9);
                    int clientHalf = (int) (amount*0.1);
                    payPalSellerList.add(new PayPalSeller("sb-bbbnr21744640@business.example.com",clientHalf));
                    payPalSellerList.add(new PayPalSeller("digiconnect.tyler@gmail.com",caddieHalf));
                    extractPayPalinformation(payPalSellerList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private PayPalAdvancedPayment extractPayPalinformation(List<PayPalSeller> sellerList) {
        PayPalAdvancedPayment payPalAdvancedPayment = new PayPalAdvancedPayment();
        for (PayPalSeller seller : sellerList) {
            PayPalReceiverDetails payPalReceiverDetails = new PayPalReceiverDetails();
            BigDecimal st = new BigDecimal(seller.getAmount());
            //     st = st.setScale(2, RoundingMode.HALF_UP);
            payPalReceiverDetails.setSubtotal(st);
            payPalReceiverDetails.setRecipient(seller.getEmail());
            //   if (seller.isPrimary()) {
            payPalReceiverDetails.setIsPrimary(true);

            //     }
            payPalReceiverDetails.setPaymentType(PayPal.PAYMENT_TYPE_SERVICE);
            payPalAdvancedPayment.getReceivers().add(payPalReceiverDetails);
        }
        payPalAdvancedPayment.setCurrencyType("USD");
        payPalAdvancedPayment.setMemo("123");
        // payPalAdvancedPayment.setIpnUrl(new PaypalAdaptiveConfiguration().IPN_URL);
        return payPalAdvancedPayment;
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
                        //     redirectToOrderActivity(true);
                        break;
                    case Activity.RESULT_CANCELED:
                        this.getActivity().finish();
                        //    Toast.makeText(getContext(), getString(R.string.payment_cancelled), Toast.LENGTH_SHORT).show();
                        break;
                    case PayPalActivity.RESULT_FAILURE:
                        String errorMessage = intent.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
                        String id = intent.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
                        Log.d("paypal error", errorMessage + " " + id);
                        Log.d("intent", intent.getExtras().toString());
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        this.getActivity().finish();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        // Stop service when done
     //   getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }


}
