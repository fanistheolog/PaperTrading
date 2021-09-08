package com.example.papertest;


import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.DropBoxManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.jsoup.helper.StringUtil.isNumeric;


public class MainActivity extends Activity {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String SHARENUMBER = "SHARENUMBER";
    public static final String SHARENUMBER2 = "SHARENUMBER2";
    public static final String SHARENUMBER3 = "SHARENUMBER3";
    public static final String BUYINGPOWER = "BUYINGPOWER";
    public static final String BASE_URL = "https://financialmodelingprep.com/api/v3/quote-short/TSLA?apikey=";
    private YahooFinanceAPI yahooFinanceAPI;
    List<Entry> entryList = new ArrayList<>();
    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;
    ArrayList lineEntries;
double teslaprice = 0;
double appleprice = 0;
double googleprice = 0;
int selectedCoin = 3;
double myMoney = 0;
double buyingPower = 10000.0;
int  firsttime = 0;
double shareNumber = 0;
double shareNumber2 = 0;
double shareNumber3 = 0;
double amount = 0;
double wanted = 0;

    DecimalFormat df = new DecimalFormat("0.00");
    ArrayList<String> allshares = new ArrayList<String>();
    ArrayList<String> allsharesaction = new ArrayList<String>();
    ArrayList<String> companyShares = new ArrayList<String>();
    ArrayList<Double> priceShares = new ArrayList<>();
String timeperiod = "5min";

    private UiModeManager uiModeManager;
    public void NightModeON(View view){
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
    }

    public void NightModeOFF(View view){
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView money = (TextView)findViewById(R.id.money);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageView teslaimageView = (ImageView)findViewById(R.id.teslaimageView);
        ImageView appleimageView = (ImageView)findViewById(R.id.applemageView);
        ImageView googleimageView = (ImageView)findViewById(R.id.googleimageView);
        ImageView refreshImage = (ImageView)findViewById(R.id.refreshImage);
        ImageView historyImage = (ImageView)findViewById(R.id.historyImage);
        ImageView stockPickImage = (ImageView)findViewById(R.id.stockPickImage);
        TextView buyingPowerTextView = (TextView)findViewById(R.id.powerTextView);
        TextView stockpriceTextView = (TextView)findViewById(R.id.stockprice);
        TextView amountBuying = (TextView)findViewById(R.id.textView5);
        TextView companyName = (TextView)findViewById(R.id.companyName);
        TextView sharesOwened = (TextView)findViewById(R.id.sharesOwened);
        TextView oneMTextView = (TextView)findViewById(R.id.oneMTextView);
        TextView fiveMTextView = (TextView)findViewById(R.id.fiveMTextView);
        TextView fiftheenMTextView = (TextView)findViewById(R.id.fifteenMTextView);
        TextView thirtyMTextView = (TextView)findViewById(R.id.thirtyMTextView);
        TextView oneHTextView = (TextView)findViewById(R.id.oneHTextView);
        TextView fourHTextView = (TextView)findViewById(R.id.fourHTextView);
        TextView oneLTextView = (TextView)findViewById(R.id.oneLTextView);
        TextView twoLTextView = (TextView)findViewById(R.id.twoLTextView);
        TextView fiveLTextView = (TextView)findViewById(R.id.fiveLTextView);
        TextView tenLTextView = (TextView)findViewById(R.id.tenLTextView);
        TextView twentyLTextView = (TextView)findViewById(R.id.twentyLTextView);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        CardView teslacard =(CardView)findViewById(R.id.teslacard);
        CardView applecard =(CardView)findViewById(R.id.applecard);
        CardView googlecard =(CardView)findViewById(R.id.googlecard);
        CardView facebookcard =(CardView)findViewById(R.id.facebookcard);
        CardView microsoftcard =(CardView)findViewById(R.id.microsoftcard);
        CardView addidascard =(CardView)findViewById(R.id.addidascard);
        CardView cocacard =(CardView)findViewById(R.id.cocacard);
        CardView fordcard =(CardView)findViewById(R.id.fordcard);
        CardView mastercardcard =(CardView)findViewById(R.id.mastercardcard);
        CardView mcdonaladscard =(CardView)findViewById(R.id.mcdonaladscard);
        CardView nikecard =(CardView)findViewById(R.id.nikecard);
        CardView nvdiacard =(CardView)findViewById(R.id.nvdiacard);
        CardView shellcard =(CardView)findViewById(R.id.shellcard);
        CardView starbuckscard =(CardView)findViewById(R.id.starbuckscard);
        CardView twittercard =(CardView)findViewById(R.id.twittercard);
        CardView ubercard =(CardView)findViewById(R.id.ubercard);
        CardView disneycard =(CardView)findViewById(R.id.disneycard);
        CardView xiaomicard =(CardView)findViewById(R.id.xiaomicard);
        CardView btccard =(CardView)findViewById(R.id.btccard);
        CardView ethcard =(CardView)findViewById(R.id.ethcard);
        CardView adacard =(CardView)findViewById(R.id.adacard);
        CardView xrpcard =(CardView)findViewById(R.id.xrpcard);
        CardView xlmcard =(CardView)findViewById(R.id.xlmcard);
        CardView dogecard =(CardView)findViewById(R.id.dogecard);
        CardView bnbcard =(CardView)findViewById(R.id.bnbcard);
        CardView ltccard =(CardView)findViewById(R.id.ltccard);
        FrameLayout whatStocks =(FrameLayout)findViewById(R.id.whatStocks);
LinearLayout timezone = (LinearLayout)findViewById(R.id.timezone);
        if (sharedpreferences.contains(SHARENUMBER)) {
            String text = sharedpreferences.getString(SHARENUMBER, "");
            shareNumber  = Double.parseDouble(text);
            sharesOwened.setText("" +df.format(shareNumber) + " Shares");

        }
        if (sharedpreferences.contains(SHARENUMBER2)) {
            String text2 = sharedpreferences.getString(SHARENUMBER2, "");
            shareNumber2  = Double.parseDouble(text2);

            sharesOwened.setText("" +df.format(shareNumber2) + " Shares");


        }
        if (sharedpreferences.contains(SHARENUMBER3)) {
            String text3 = sharedpreferences.getString(SHARENUMBER3, "");
            shareNumber3  = Double.parseDouble(text3);
            sharesOwened.setText("" +df.format(shareNumber3) + " Shares");


        } 
        if (sharedpreferences.contains(BUYINGPOWER)) {
            String text4 = sharedpreferences.getString(BUYINGPOWER, "");
            buyingPower  = Double.parseDouble(text4);
            buyingPowerTextView.setText("Buying Power: " + df.format(buyingPower));


        }
        myMoney = (shareNumber* teslaprice) + (shareNumber2 *appleprice) + (shareNumber3 *googleprice) + buyingPower;
        money.setText("" + df.format(myMoney));
        int neotimerr = 1;
        new CountDownTimer((neotimerr + 1) * 1000, 1000) // Wait 5 secs, tick every 1 sec
        {
            @Override
            public final void onTick(final long millisUntilFinished) {
            }

            @Override
            public final void onFinish() {
                SharedPreferences.Editor editor = sharedpreferences.edit();


                String sharenumberToString = String.valueOf(shareNumber);
                String sharenumberToString2 = String.valueOf(shareNumber2);
                String sharenumberToString3 = String.valueOf(shareNumber3);
                String buyingPowerToString = String.valueOf(buyingPower);
                editor.putString( SHARENUMBER, sharenumberToString);
                editor.putString( SHARENUMBER2, sharenumberToString2);
                editor.putString( SHARENUMBER3, sharenumberToString3);
                editor.putString( BUYINGPOWER, buyingPowerToString);

                editor.apply();
                this.start();
            }
        }.start();



        TextView powerTextView = (TextView)findViewById(R.id.powerTextView);
        ScrollView scrollViewHistory = (ScrollView)findViewById(R.id.scrollViewHistory);
        MaterialSpinner spinnere = (MaterialSpinner) findViewById(R.id.spnFirstCountry);
        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        spinnere.setItems("1M", "5M", "15M","30M","1H","4H");
        spinnere.setBackgroundColor(Color.parseColor("#383838"));
        oneMTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneMTextView.setTextColor(Color.parseColor("#b9ddd9"));
                fiveMTextView.setTextColor(Color.parseColor("#008175"));
                fiftheenMTextView.setTextColor(Color.parseColor("#008175"));
                thirtyMTextView.setTextColor(Color.parseColor("#008175"));
                oneHTextView.setTextColor(Color.parseColor("#008175"));
                fourHTextView.setTextColor(Color.parseColor("#008175"));


                timeperiod = "1min";
                if (selectedCoin == 1) {
                    getStockData();
                }
                if (selectedCoin == 2) {
                    getStockData2();
                }
                if (selectedCoin == 3) {
                    getStockData3();
                }
            }
        });
        fiveMTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeperiod = "5min";
                oneMTextView.setTextColor(Color.parseColor("#008175"));
                fiveMTextView.setTextColor(Color.parseColor("#b9ddd9"));
                fiftheenMTextView.setTextColor(Color.parseColor("#008175"));
                thirtyMTextView.setTextColor(Color.parseColor("#008175"));
                oneHTextView.setTextColor(Color.parseColor("#008175"));
                fourHTextView.setTextColor(Color.parseColor("#008175"));

                if (selectedCoin == 1) {
                    getStockData();
                }
                if (selectedCoin == 2) {
                    getStockData2();
                }
                if (selectedCoin == 3) {
                    getStockData3();
                }
            }
        });
        fiftheenMTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeperiod = "15min";
                oneMTextView.setTextColor(Color.parseColor("#008175"));
                fiveMTextView.setTextColor(Color.parseColor("#008175"));
                fiftheenMTextView.setTextColor(Color.parseColor("#b9ddd9"));
                thirtyMTextView.setTextColor(Color.parseColor("#008175"));
                oneHTextView.setTextColor(Color.parseColor("#008175"));
                fourHTextView.setTextColor(Color.parseColor("#008175"));
                if (selectedCoin == 1) {
                    getStockData();
                }
                if (selectedCoin == 2) {
                    getStockData2();
                }
                if (selectedCoin == 3) {
                    getStockData3();
                }
            }
        });
        thirtyMTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeperiod = "30min";
                oneMTextView.setTextColor(Color.parseColor("#008175"));
                fiveMTextView.setTextColor(Color.parseColor("#008175"));
                fiftheenMTextView.setTextColor(Color.parseColor("#008175"));
                thirtyMTextView.setTextColor(Color.parseColor("#b9ddd9"));
                oneHTextView.setTextColor(Color.parseColor("#008175"));
                fourHTextView.setTextColor(Color.parseColor("#008175"));
                if (selectedCoin == 1) {
                    getStockData();
                }
                if (selectedCoin == 2) {
                    getStockData2();
                }
                if (selectedCoin == 3) {
                    getStockData3();
                }
            }
        });
        oneHTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneMTextView.setTextColor(Color.parseColor("#008175"));
                fiveMTextView.setTextColor(Color.parseColor("#008175"));
                fiftheenMTextView.setTextColor(Color.parseColor("#008175"));
                thirtyMTextView.setTextColor(Color.parseColor("#008175"));
                oneHTextView.setTextColor(Color.parseColor("#b9ddd9"));
                fourHTextView.setTextColor(Color.parseColor("#008175"));
                timeperiod = "1hour";
                if (selectedCoin == 1) {
                    getStockData();
                }
                if (selectedCoin == 2) {
                    getStockData2();
                }
                if (selectedCoin == 3) {
                    getStockData3();
                }
            }
        });
        fourHTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeperiod = "4hour";
                oneMTextView.setTextColor(Color.parseColor("#008175"));
                fiveMTextView.setTextColor(Color.parseColor("#008175"));
                fiftheenMTextView.setTextColor(Color.parseColor("#008175"));
                thirtyMTextView.setTextColor(Color.parseColor("#008175"));
                oneHTextView.setTextColor(Color.parseColor("#008175"));
                fourHTextView.setTextColor(Color.parseColor("#b9ddd9"));
                if (selectedCoin == 1) {
                    getStockData();
                }
                if (selectedCoin == 2) {
                    getStockData2();
                }
                if (selectedCoin == 3) {
                    getStockData3();
                }
            }
        });
        oneLTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneLTextView.setTextColor(Color.parseColor("#b9ddd9"));
                twoLTextView.setTextColor(Color.parseColor("#008175"));
                fiveLTextView.setTextColor(Color.parseColor("#008175"));
                tenLTextView.setTextColor(Color.parseColor("#008175"));
                twentyLTextView.setTextColor(Color.parseColor("#008175"));



                timeperiod = "1min";
                if (selectedCoin == 1) {
                    getStockData();
                }
                if (selectedCoin == 2) {
                    getStockData2();
                }
                if (selectedCoin == 3) {
                    getStockData3();
                }
            }
        });


        EditText editTextNumber = (EditText)findViewById(R.id.editTextNumber);
        CardView sellButton = (CardView)findViewById(R.id.button);
        CardView buyButton = (CardView)findViewById(R.id.button2);
        LinearLayout field =(LinearLayout)findViewById(R.id.field);
        LinearLayout fieldHistory =(LinearLayout)findViewById(R.id.fieldHistory);
        getStockData3();



        refreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*tesla api 250 per day only. https://financialmodelingprep.com/api/v3/quote-short/TSLA?apikey=00b91ceac4f7f02ade54e246057fa0b5*/
                new DownloadTask().execute("https://financialmodelingprep.com/api/v3/quote-short/TSLA?apikey=00b91ceac4f7f02ade54e246057fa0b5");
                new DownloadTask().execute("https://financialmodelingprep.com/api/v3/quote-short/AAPL?apikey=demo");
                new DownloadTask().execute("https://financialmodelingprep.com/api/v3/quote-short/GOOG?apikey=00b91ceac4f7f02ade54e246057fa0b5");
                myMoney = (shareNumber* teslaprice) + (shareNumber2 *appleprice) + (shareNumber3 *googleprice) + buyingPower;
                money.setText("" + myMoney);
                if (selectedCoin == 1){
                    stockpriceTextView.setText("1 SHARE = " + teslaprice);
                }
                if (selectedCoin == 2){
                    stockpriceTextView.setText("1 SHARE = " + appleprice);
                }
                if (selectedCoin == 3){
                    stockpriceTextView.setText("1 SHARE = " + googleprice);
                }
            }
        });

        editTextNumber.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (editTextNumber.getText().toString().isEmpty()){
                    amountBuying.setVisibility(View.INVISIBLE);
                } else if(editTextNumber.getText().toString().equals(".")){

                } else if (Double.parseDouble(editTextNumber.getText().toString()) > 0){
                    amountBuying.setVisibility(View.VISIBLE);
                    if (selectedCoin == 1) {
                        amountBuying.setText("Price You Will Pay: " + df.format((teslaprice * Double.parseDouble(editTextNumber.getText().toString()))));
                    }
                    if (selectedCoin == 2) {
                        amountBuying.setText("Price You Will Pay: " + df.format((appleprice * Double.parseDouble(editTextNumber.getText().toString()))));
                    }
                    if (selectedCoin == 3) {
                        amountBuying.setText("Price You Will Pay: " + df.format((googleprice * Double.parseDouble(editTextNumber.getText().toString()))));
                    }
                }
            }
        });


        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNumber.getText().toString().equals("")){
                } else{

                    amount = Double.parseDouble(editTextNumber.getText().toString());
                    if (selectedCoin == 1){
                        wanted = amount*teslaprice;
                    }
                    if (selectedCoin == 2){
                        wanted = amount*appleprice;
                    }
                    if (selectedCoin == 3){
                        wanted = amount*googleprice;
                    }
                    if(wanted<=buyingPower){
                        buyingPower = buyingPower - wanted;
                        buyingPowerTextView.setText("Buying Power: " + df.format(buyingPower));
                        if (selectedCoin == 1){
                            shareNumber = shareNumber + amount;
                            sharesOwened.setText("" +df.format(shareNumber) + " Shares Owned");
                        }
                        if (selectedCoin == 2){
                            shareNumber2 = shareNumber2 + amount;
                            sharesOwened.setText("" +df.format(shareNumber2) + " Shares Owned");
                        }
                        if (selectedCoin == 3){
                            shareNumber3 = shareNumber3 + amount;
                            sharesOwened.setText("" +df.format(shareNumber3) + " Shares Owned");
                        }



                        myMoney = (shareNumber* teslaprice) + (shareNumber2 *appleprice) + (shareNumber3 *googleprice) + buyingPower;
                        money.setText("" + df.format(myMoney));
                        allshares.add(editTextNumber.getText().toString());
                        allsharesaction.add("buy");
                        if (selectedCoin == 1){
                            companyShares.add("Tesla");
                            priceShares.add(teslaprice);

                        }
                        if (selectedCoin == 2){
                            companyShares.add("Apple");
                            priceShares.add(appleprice);
                        }
                        if (selectedCoin == 3){
                            companyShares.add("Google");
                            priceShares.add(googleprice);
                        }

                        //this is
                        fieldHistory.removeAllViews();

                        for(int i=0; i<allshares.size(); i++) {
                            TextView text = new TextView(getApplicationContext());
                            text.setTextSize(18);
                            text.setTextColor(Color.parseColor("#ffffff"));
                            if(allsharesaction.get(i).equals("sell")){
                                text.setText(allshares.get(i)+ " SELL ORDER "+companyShares.get(i) + " AT " + priceShares.get(i));
                            }else{
                                text.setText(allshares.get(i)+ " BUY ORDER "+companyShares.get(i) + " AT " + priceShares.get(i));

                            }
                            fieldHistory.addView(text);
                            // Do something with the value
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "insufficient balance ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        sellButton.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           if (editTextNumber.getText().toString().equals("")) {
                                           } else {

                                               amount = Double.parseDouble(editTextNumber.getText().toString());
                                               if (selectedCoin == 1) {
                                                   if (amount <= shareNumber) {
                                                       if (selectedCoin == 1) {
                                                           shareNumber = shareNumber - amount;
                                                           buyingPower = (amount*teslaprice) + buyingPower;
                                                           sharesOwened.setText("" +df.format(shareNumber) + " Shares Owned");
                                                       }
                                                       if (selectedCoin == 2) {
                                                           shareNumber2 = shareNumber2 - amount;
                                                           buyingPower = (amount*appleprice) + buyingPower;
                                                           sharesOwened.setText("" +df.format(shareNumber2) + " Shares Owned");
                                                       }
                                                       if (selectedCoin == 3) {
                                                           shareNumber3 = shareNumber3 - amount;
                                                           buyingPower = (amount*googleprice) + buyingPower;
                                                           sharesOwened.setText("" +df.format(shareNumber3) + " Shares Owned");
                                                       }
                                                       buyingPowerTextView.setText("Buying Power: " + df.format(buyingPower));



                                                       myMoney = (shareNumber* teslaprice) + (shareNumber2 *appleprice) + (shareNumber3 *googleprice) + buyingPower;
                                                       money.setText("" + df.format(myMoney));
                                                       allshares.add(editTextNumber.getText().toString());
                                                       allsharesaction.add("sell");
                                                       if (selectedCoin == 1){
                                                           companyShares.add("Tesla");
                                                           priceShares.add(teslaprice);

                                                       }
                                                       if (selectedCoin == 2){
                                                           companyShares.add("Apple");
                                                           priceShares.add(appleprice);
                                                       }
                                                       if (selectedCoin == 3){
                                                           companyShares.add("Google");
                                                           priceShares.add(googleprice);
                                                       }
                                                       //this is
                                                       fieldHistory.removeAllViews();

                                                       for (int i = 0; i < allshares.size(); i++) {
                                                           TextView text = new TextView(getApplicationContext());
                                                           text.setTextSize(18);
                                                           text.setTextColor(Color.parseColor("#ffffff"));
                                                           if (allsharesaction.get(i).equals("sell")) {
                                                               text.setText(allshares.get(i) + " SELL ORDER " + companyShares.get(i) + " @ " + priceShares.get(i));
                                                           } else {
                                                               text.setText(allshares.get(i) + " BUY ORDER " + companyShares.get(i) + " @ " + priceShares.get(i));

                                                           }
                                                           fieldHistory.addView(text);
                                                           // Do something with the value
                                                       }
                                                   } else {
                                                       Toast.makeText(getApplicationContext(), "insufficient balance ", Toast.LENGTH_SHORT).show();
                                                   }
                                               }
                                                   if (selectedCoin == 2) {
                                                       if (amount <= shareNumber2) {
                                                           if (selectedCoin == 1) {
                                                               shareNumber = shareNumber - amount;
                                                               buyingPower = (amount*teslaprice) + buyingPower;
                                                               sharesOwened.setText("" +df.format(shareNumber) + " Shares Owned");
                                                           }
                                                           if (selectedCoin == 2) {
                                                               shareNumber2 = shareNumber2 - amount;
                                                               buyingPower = (amount*appleprice) + buyingPower;
                                                               sharesOwened.setText("" +df.format(shareNumber2) + " Shares Owned");
                                                           }
                                                           if (selectedCoin == 3) {
                                                               shareNumber3 = shareNumber3 - amount;
                                                               buyingPower = (amount*googleprice) + buyingPower;
                                                               sharesOwened.setText("" +df.format(shareNumber3) + " Shares Owned");
                                                           }
                                                           buyingPowerTextView.setText("Buying Power: " + df.format(buyingPower));


                                                           myMoney = (shareNumber* teslaprice) + (shareNumber2 *appleprice) + (shareNumber3 *googleprice) + buyingPower;
                                                           money.setText("" + df.format(myMoney));

                                                           allshares.add(editTextNumber.getText().toString());
                                                           allsharesaction.add("sell");
                                                           if (selectedCoin == 1){
                                                               companyShares.add("Tesla");
                                                               priceShares.add(teslaprice);

                                                           }
                                                           if (selectedCoin == 2){
                                                               companyShares.add("Apple");
                                                               priceShares.add(appleprice);
                                                           }
                                                           if (selectedCoin == 3){
                                                               companyShares.add("Google");
                                                               priceShares.add(googleprice);
                                                           }
                                                           //this is
                                                           fieldHistory.removeAllViews();

                                                           for (int i = 0; i < allshares.size(); i++) {
                                                               TextView text = new TextView(getApplicationContext());
                                                               text.setTextSize(18);
                                                               text.setTextColor(Color.parseColor("#ffffff"));
                                                               if (allsharesaction.get(i).equals("sell")) {
                                                                   text.setText(allshares.get(i) + " SELL ORDER " + companyShares.get(i) + " AT " + priceShares.get(i));
                                                               } else {
                                                                   text.setText(allshares.get(i) + " BUY ORDER " + companyShares.get(i) + " AT " + priceShares.get(i));

                                                               }
                                                               fieldHistory.addView(text);
                                                               // Do something with the value
                                                           }
                                                       } else {
                                                           Toast.makeText(getApplicationContext(), "insufficient balance ", Toast.LENGTH_SHORT).show();
                                                       }
                                                   }
                                               if (selectedCoin == 3) {
                                                   if (amount <= shareNumber3) {
                                                       if (selectedCoin == 1) {
                                                           shareNumber = shareNumber - amount;
                                                           buyingPower = (amount*teslaprice) + buyingPower;
                                                           sharesOwened.setText("" +df.format(shareNumber) + " Shares Owned");
                                                       }
                                                       if (selectedCoin == 2) {
                                                           shareNumber2 = shareNumber2 - amount;
                                                           buyingPower = (amount*appleprice) + buyingPower;
                                                           sharesOwened.setText("" +df.format(shareNumber2) + " Shares Owned");
                                                       }
                                                       if (selectedCoin == 3) {
                                                           shareNumber3 = shareNumber3 - amount;
                                                           buyingPower = (amount*googleprice) + buyingPower;
                                                           sharesOwened.setText("" +df.format(shareNumber3) + " Shares Owned");
                                                       }
                                                       buyingPowerTextView.setText("Buying Power: " + df.format(buyingPower));


                                                       myMoney = (shareNumber* teslaprice) + (shareNumber2 *appleprice) + (shareNumber3 *googleprice) + buyingPower;
                                                       money.setText("" + df.format(myMoney));

                                                       allshares.add(editTextNumber.getText().toString());
                                                       allsharesaction.add("sell");
                                                       if (selectedCoin == 1){
                                                           companyShares.add("Tesla");
                                                           priceShares.add(teslaprice);

                                                       }
                                                       if (selectedCoin == 2){
                                                           companyShares.add("Apple");
                                                           priceShares.add(appleprice);
                                                       }
                                                       if (selectedCoin == 3){
                                                           companyShares.add("Google");
                                                           priceShares.add(googleprice);
                                                       }
                                                       //this is
                                                       fieldHistory.removeAllViews();

                                                       for (int i = 0; i < allshares.size(); i++) {
                                                           TextView text = new TextView(getApplicationContext());
                                                           text.setTextSize(18);
                                                           text.setTextColor(Color.parseColor("#ffffff"));
                                                           if (allsharesaction.get(i).equals("sell")) {
                                                               text.setText(allshares.get(i) + " SELL ORDER " + companyShares.get(i) + " AT " + priceShares.get(i));
                                                           } else {
                                                               text.setText(allshares.get(i) + " BUY ORDER " + companyShares.get(i) + " AT " + priceShares.get(i));

                                                           }
                                                           fieldHistory.addView(text);
                                                           // Do something with the value
                                                       }
                                                   } else {
                                                       Toast.makeText(getApplicationContext(), "insufficient balance ", Toast.LENGTH_SHORT).show();
                                                   }
                                               }



                                           }
                                       }
                                   });

        teslaimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCoin =1;
                lineChart.setVisibility(View.VISIBLE);
                stockpriceTextView.setVisibility(View.VISIBLE);
                companyName.setVisibility(View.VISIBLE);
                sharesOwened.setVisibility(View.VISIBLE);
                scrollViewHistory.setVisibility(View.GONE);
                fieldHistory.setVisibility(View.GONE);
                scrollViewHistory.setVisibility(View.GONE);
                whatStocks.setVisibility(View.GONE);
                timezone.setVisibility(View.VISIBLE);
                getStockData();
                stockpriceTextView.setText("1 SHARE = " + teslaprice);
                companyName.setText("TESLA");
                sharesOwened.setText("" +df.format(shareNumber) + " Shares Owned");
                if (editTextNumber.getText().toString().equals("")) {
                    amountBuying.setText("Price You Will Pay: " +df.format((teslaprice*0)));
                }else{
                    if (Double.parseDouble(editTextNumber.getText().toString()) > 0) {
                        amountBuying.setText("Price You Will Pay: " + df.format((teslaprice * Double.parseDouble(editTextNumber.getText().toString()))));
                    }
                }
            }
        });
        appleimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCoin =2;
                lineChart.setVisibility(View.VISIBLE);
                stockpriceTextView.setVisibility(View.VISIBLE);
                companyName.setVisibility(View.VISIBLE);
                sharesOwened.setVisibility(View.VISIBLE);
                fieldHistory.setVisibility(View.GONE);
                scrollViewHistory.setVisibility(View.GONE);
                whatStocks.setVisibility(View.GONE);
                timezone.setVisibility(View.VISIBLE);
                getStockData2();
                companyName.setText("APPLE");

                stockpriceTextView.setText("1 SHARE = " + appleprice);
                sharesOwened.setText("" +df.format(shareNumber2) + " Shares Owned");
                if (editTextNumber.getText().toString().equals("")) {
                    amountBuying.setText("Price You Will Pay: " +df.format((appleprice*0)));
                }else{
                    if (Double.parseDouble(editTextNumber.getText().toString()) > 0) {
                        amountBuying.setText("Price You Will Pay: " + df.format((appleprice * Double.parseDouble(editTextNumber.getText().toString()))));
                    }
                }
            }
        });
        googleimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCoin =3;
                lineChart.setVisibility(View.VISIBLE);
                stockpriceTextView.setVisibility(View.VISIBLE);
                companyName.setVisibility(View.VISIBLE);
                sharesOwened.setVisibility(View.VISIBLE);
                fieldHistory.setVisibility(View.GONE);
                scrollViewHistory.setVisibility(View.GONE);
                whatStocks.setVisibility(View.GONE);
                timezone.setVisibility(View.VISIBLE);
                getStockData3();
                companyName.setText("GOOGLE");
                stockpriceTextView.setText("1 SHARE = " + googleprice);
                sharesOwened.setText("" +df.format(shareNumber3) + " Shares Owned");
                if (editTextNumber.getText().toString().equals("")) {
                    amountBuying.setText("Price You Will Pay: " +df.format((googleprice*0)));
                }else{
                    if (Double.parseDouble(editTextNumber.getText().toString()) > 0) {
                        amountBuying.setText("Price You Will Pay: " + df.format((googleprice * Double.parseDouble(editTextNumber.getText().toString()))));
                    }
                }
            }
        });

        historyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineChart.setVisibility(View.GONE);
                stockpriceTextView.setVisibility(View.GONE);
                companyName.setVisibility(View.GONE);
                sharesOwened.setVisibility(View.GONE);
                fieldHistory.setVisibility(View.VISIBLE);
                scrollViewHistory.setVisibility(View.VISIBLE);
                whatStocks.setVisibility(View.GONE);
                timezone.setVisibility(View.VISIBLE);


            }
        });
        stockPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineChart.setVisibility(View.GONE);
                stockpriceTextView.setVisibility(View.GONE);
                companyName.setVisibility(View.GONE);
                sharesOwened.setVisibility(View.GONE);
                fieldHistory.setVisibility(View.GONE);
                timezone.setVisibility(View.GONE);
                whatStocks.setVisibility(View.VISIBLE);


            }
        });

        new DownloadTask().execute("https://financialmodelingprep.com/api/v3/quote-short/TSLA?apikey=00b91ceac4f7f02ade54e246057fa0b5");

        new DownloadTask().execute("https://financialmodelingprep.com/api/v3/quote-short/AAPL?apikey=demo");
        new DownloadTask().execute("https://financialmodelingprep.com/api/v3/quote-short/GOOG?apikey=00b91ceac4f7f02ade54e246057fa0b5");



    }


    private void getStockData() {
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://financialmodelingprep.com/api/v3/historical-chart/" + timeperiod +"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YahooFinanceAPI jsonPlaceHolderApi = retrofit.create(YahooFinanceAPI.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {

                    return;
                }
                lineEntries = new ArrayList<>();

                List<Post> posts = response.body();
                entryList.clear();
                int i = 1;
                lineChart = findViewById(R.id.lineChart);
                ArrayList<Entry> pricesHigh = new ArrayList<>();
                ArrayList<Entry> pricesLow = new ArrayList<>();
                ArrayList<Entry> pricesClose = new ArrayList<>();
                pricesHigh.clear();
                pricesLow.clear();
                pricesClose.clear();

                for (Post post : posts) {
                    String lowi =String.valueOf(post.getId());
                    String highi =String.valueOf(post.getTitle());
                    String timei =String.valueOf(post.getTime());
                    timei = timei.replace("\"", "");
                    timei = timei.replace("-", "");
                    timei = timei.replace(":", "");
                    timei = timei.replace(":", "");
                    Float pipi = Float.parseFloat("113000");
                    Float pipi2 = Float.parseFloat("112500");
                    Date currentTime = Calendar.getInstance().getTime();
                    Calendar c = Calendar.getInstance();
                    System.out.println("Current dateTime => " + c.getTime());
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    System.out.println("Format dateTime => " + formattedDate);
                    long endTime = System.currentTimeMillis() / 1000;
                    long startTime = endTime - (60 * 5);
                    String frequency = "5m";

                    entryList.add(new Entry((i+4),Float.parseFloat(highi)));


                    String content = "";
                    content += "Number: " + i + "\n";
                    i++;
                    content += "Open: " + post.getUserId() + "\n";
                    content += "Low: " + post.getId() + "\n";
                    content += "High: " + post.getTitle() + "\n";
                    content += "Close: " + post.getText() + "\n\n";

                }




                LineDataSet lineDataSet = new LineDataSet(entryList,"");
                LimitLine ll = new LimitLine(140f);
                lineDataSet.setColors(Color.parseColor("#ffffff"));
                lineDataSet.setDrawCircleHole(false);
                lineDataSet.setDrawCircles(false);
                lineDataSet.setDrawValues(false);
                lineDataSet.setLineWidth(3f);
                lineChart.getXAxis().setDrawGridLines(false);
                lineChart.getXAxis().setDrawGridLines(false);
                lineDataSet.setFillAlpha(110);
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setDrawVerticalHighlightIndicator(false);
                lineChart.getDescription().setEnabled(false);
                lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);
                lineChart.setDrawBorders(false);
                lineChart.moveViewToX(20);
                lineChart.fitScreen();
                lineChart.getXAxis().setDrawGridLines(false);
                lineChart.getAxisLeft().setDrawGridLines(false);
                lineChart.getAxisRight().setDrawGridLines(false);
                lineChart.getXAxis().setDrawLabels(false);
                // remove axis
                YAxis leftAxis = lineChart.getAxisLeft();
                leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
                YAxis rightAxis = lineChart.getAxisRight();
                lineDataSet.setValueTextColor(getResources().getColor(android.R.color.white));
                rightAxis.setTextColor(getResources().getColor(android.R.color.white));
                leftAxis.setTextColor(getResources().getColor(android.R.color.white));


                rightAxis.setEnabled(false);

                XAxis xAxis = lineChart.getXAxis();
                xAxis.setEnabled(false);

                // hide legend
                Legend legend = lineChart.getLegend();
                lineChart.getLegend().setEnabled(false);
                lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                lineChart.setDrawGridBackground(false);
                lineChart.setVisibleXRangeMaximum(15);
                lineDataSet.setDrawFilled(true);
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.graph_gradient);
                lineDataSet.setFillDrawable(drawable);
                lineChart.setVisibleYRangeMaximum(30, YAxis.AxisDependency.LEFT);
                lineChart.invalidate();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }

    private void getStockData2() {
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://financialmodelingprep.com/api/v3/historical-chart/" + timeperiod +"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YahooFinanceAPI2 jsonPlaceHolderApi = retrofit.create(YahooFinanceAPI2.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {

                    return;
                }
                lineEntries = new ArrayList<>();

                List<Post> posts = response.body();
                entryList.clear();
                int i = 1;
                lineChart = findViewById(R.id.lineChart);
                ArrayList<Entry> pricesHigh = new ArrayList<>();
                ArrayList<Entry> pricesLow = new ArrayList<>();
                ArrayList<Entry> pricesClose = new ArrayList<>();
                pricesHigh.clear();
                pricesLow.clear();
                pricesClose.clear();

                for (Post post : posts) {
                    String lowi =String.valueOf(post.getId());
                    String highi =String.valueOf(post.getTitle());
                    String timei =String.valueOf(post.getTime());
                    timei = timei.replace("\"", "");
                    timei = timei.replace("-", "");
                    timei = timei.replace(":", "");
                    timei = timei.replace(":", "");
                    Float pipi = Float.parseFloat("113000");
                    Float pipi2 = Float.parseFloat("112500");
                    Date currentTime = Calendar.getInstance().getTime();
                    Calendar c = Calendar.getInstance();
                    System.out.println("Current dateTime => " + c.getTime());
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    System.out.println("Format dateTime => " + formattedDate);
                    long endTime = System.currentTimeMillis() / 1000;
                    long startTime = endTime - (60 * 5);
                    String frequency = "5m";

                    entryList.add(new Entry((i+4),Float.parseFloat(highi)));


                    String content = "";
                    content += "Number: " + i + "\n";
                    i++;
                    content += "Open: " + post.getUserId() + "\n";
                    content += "Low: " + post.getId() + "\n";
                    content += "High: " + post.getTitle() + "\n";
                    content += "Close: " + post.getText() + "\n\n";

                }


                LineDataSet lineDataSet = new LineDataSet(entryList,"");
                LimitLine ll = new LimitLine(140f);
                lineDataSet.setColors(Color.parseColor("#ffffff"));
                lineDataSet.setDrawCircleHole(false);
                lineDataSet.setDrawCircles(false);
                lineDataSet.setDrawValues(false);
                lineDataSet.setLineWidth(3f);
                lineChart.getXAxis().setDrawGridLines(false);
                lineChart.getXAxis().setDrawGridLines(false);
                lineDataSet.setFillAlpha(110);
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setDrawVerticalHighlightIndicator(false);
                lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);
                lineChart.setDrawBorders(false);
                lineChart.moveViewToX(20);
                lineChart.fitScreen();
                lineChart.getXAxis().setDrawGridLines(false);
                lineChart.getAxisLeft().setDrawGridLines(false);
                lineChart.getAxisRight().setDrawGridLines(false);
                lineChart.getXAxis().setDrawLabels(false);
                lineChart.getDescription().setEnabled(false);


                // remove axis
                YAxis leftAxis = lineChart.getAxisLeft();
                leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
                YAxis rightAxis = lineChart.getAxisRight();
                rightAxis.setEnabled(false);
                lineDataSet.setValueTextColor(getResources().getColor(android.R.color.white));
                rightAxis.setTextColor(getResources().getColor(android.R.color.white));
                leftAxis.setTextColor(getResources().getColor(android.R.color.white));

                XAxis xAxis = lineChart.getXAxis();
                xAxis.setEnabled(false);

                // hide legend
                Legend legend = lineChart.getLegend();
                lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                lineChart.setDrawGridBackground(false);
                lineChart.setVisibleXRangeMaximum(15);
                lineDataSet.setDrawFilled(true);
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.graph_gradient);
                lineDataSet.setFillDrawable(drawable);
                lineChart.setVisibleYRangeMaximum(4, YAxis.AxisDependency.LEFT);
                lineChart.invalidate();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
    private void getStockData3() {
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://financialmodelingprep.com/api/v3/historical-chart/" + timeperiod +"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YahooFinanceAPI3 jsonPlaceHolderApi = retrofit.create(YahooFinanceAPI3.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {

                    return;
                }
                lineEntries = new ArrayList<>();

                List<Post> posts = response.body();
                entryList.clear();
                int i = 1;
                lineChart = findViewById(R.id.lineChart);
                ArrayList<Entry> pricesHigh = new ArrayList<>();
                ArrayList<Entry> pricesLow = new ArrayList<>();
                ArrayList<Entry> pricesClose = new ArrayList<>();
                pricesHigh.clear();
                pricesLow.clear();
                pricesClose.clear();

                for (Post post : posts) {
                    String lowi =String.valueOf(post.getId());
                    String highi =String.valueOf(post.getTitle());
                    String timei =String.valueOf(post.getTime());
                    timei = timei.replace("\"", "");
                    timei = timei.replace("-", "");
                    timei = timei.replace(":", "");
                    timei = timei.replace(":", "");
                    Float pipi = Float.parseFloat("113000");
                    Float pipi2 = Float.parseFloat("112500");
                    Date currentTime = Calendar.getInstance().getTime();
                    Calendar c = Calendar.getInstance();
                    System.out.println("Current dateTime => " + c.getTime());
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    System.out.println("Format dateTime => " + formattedDate);
                    long endTime = System.currentTimeMillis() / 1000;
                    long startTime = endTime - (60 * 5);
                    String frequency = "5m";

                    entryList.add(new Entry((i+4),Float.parseFloat(highi)));


                    String content = "";
                    content += "Number: " + i + "\n";
                    i++;
                    content += "Open: " + post.getUserId() + "\n";
                    content += "Low: " + post.getId() + "\n";
                    content += "High: " + post.getTitle() + "\n";
                    content += "Close: " + post.getText() + "\n\n";

                }



                LineDataSet lineDataSet = new LineDataSet(entryList,"");
                LimitLine ll = new LimitLine(140f);
                lineDataSet.setColors(Color.parseColor("#ffffff"));
                lineDataSet.setDrawCircleHole(false);
                lineDataSet.setDrawCircles(false);
                lineDataSet.setDrawValues(false);
                lineDataSet.setLineWidth(3f);
                lineChart.getXAxis().setDrawGridLines(false);
                lineChart.getXAxis().setDrawGridLines(false);
                lineDataSet.setFillAlpha(110);
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setDrawVerticalHighlightIndicator(false);
                lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);
                lineChart.getDescription().setEnabled(false);
                lineChart.setDrawBorders(false);
                lineChart.moveViewToX(20);
                lineChart.fitScreen();
                lineChart.getXAxis().setDrawGridLines(false);
                lineChart.getAxisLeft().setDrawGridLines(false);
                lineChart.getAxisRight().setDrawGridLines(false);
                lineChart.getXAxis().setDrawLabels(false);
                // remove axis
                YAxis leftAxis = lineChart.getAxisLeft();
                leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
                YAxis rightAxis = lineChart.getAxisRight();
                rightAxis.setEnabled(false);
                lineDataSet.setValueTextColor(getResources().getColor(android.R.color.white));
                rightAxis.setTextColor(getResources().getColor(android.R.color.white));
                leftAxis.setTextColor(getResources().getColor(android.R.color.white));

                XAxis xAxis = lineChart.getXAxis();
                xAxis.setEnabled(false);

                // hide legend
                Legend legend = lineChart.getLegend();

                legend.setEnabled(false);
                lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                lineChart.setDrawGridBackground(false);
                lineChart.setVisibleXRangeMaximum(15);
                lineDataSet.setDrawFilled(true);
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.graph_gradient);
                lineDataSet.setFillDrawable(drawable);
                lineChart.setVisibleYRangeMaximum(85, YAxis.AxisDependency.LEFT);
                lineChart.invalidate();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }


    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //do your request in here so that you don't interrupt the UI thread
            try {
                return downloadContent(params[0]);
            } catch (IOException e) {
                return "Unable to retrieve data. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String kwstas = result;
            kwstas =  kwstas.substring(1, kwstas.length() - 1);

            try {

                JSONObject jsonObject = new JSONObject(kwstas);
                int x = jsonObject.length();
                String coinName = jsonObject.getString("symbol");
                if (coinName.contains("TSLA")){
                    String weatherInfo = jsonObject.getString("price");
                    teslaprice = Double.parseDouble(weatherInfo);
                    TextView stockpriceTextView = (TextView)findViewById(R.id.stockprice);
                    TextView money = (TextView)findViewById(R.id.money);
                    stockpriceTextView.setText("1 SHARE = " + teslaprice);
                    myMoney = (shareNumber* teslaprice) + (shareNumber2 *appleprice) + (shareNumber3 *googleprice) + buyingPower;
                    money.setText("$" + df.format(myMoney));
                    JSONArray arr = new JSONArray(weatherInfo);

                }
                if (coinName.contains("AAPL")){
                    String weatherInfo = jsonObject.getString("price");
                    appleprice = Double.parseDouble(weatherInfo);
                    TextView stockpriceTextView = (TextView)findViewById(R.id.stockprice);
                    TextView money = (TextView)findViewById(R.id.money);
                    stockpriceTextView.setText("1 SHARE = " + appleprice);
                    myMoney = (shareNumber* teslaprice) + (shareNumber2 *appleprice) + (shareNumber3 *googleprice) + buyingPower;
                    money.setText("$" + df.format(myMoney));
                    JSONArray arr = new JSONArray(weatherInfo);
                }
                if (coinName.contains("GOOG")){
                    String weatherInfo = jsonObject.getString("price");
                    googleprice = Double.parseDouble(weatherInfo);
                    TextView stockpriceTextView = (TextView)findViewById(R.id.stockprice);
                    TextView money = (TextView)findViewById(R.id.money);
                    stockpriceTextView.setText("1 SHARE = " + googleprice);
                    myMoney = (shareNumber* teslaprice) + (shareNumber2 *appleprice) + (shareNumber3 *googleprice) + buyingPower;
                    money.setText("$" + df.format(myMoney));
                    JSONArray arr = new JSONArray(weatherInfo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String downloadContent(String myurl) throws IOException {
        InputStream is = null;
        int length = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = convertInputStreamToString(is, length);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }
}
