package com.mobiledevices.bitcoinproducts;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BrowseProductsActivity extends AppCompatActivity {

    private String BITCOIN_CONVERTER_URL = "https://blockchain.info/tobtc?currency=CAD&value=";
    private int PRODUCT_ID;
    private ProductDBHelper productDBHelper;
    private ArrayList<Product> productArrayList;
    private Button prev;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_products);

        productDBHelper = new ProductDBHelper(this);
        productArrayList = new ArrayList<>(productDBHelper.getAllProducts());
        PRODUCT_ID = 0;

        Log.i("PRODUCT_ID", Integer.toString(PRODUCT_ID));
        next = (Button)findViewById(R.id.nextButton);
        prev = (Button)findViewById(R.id.nextButton);
        prev.setEnabled(false);
        next.setEnabled(true);

        if(productArrayList.size() == 0){
            productDBHelper.addNewProduct("Apples", "Candied", 10);
            productDBHelper.addNewProduct("Basketball", "Spalding", 50);
            productDBHelper.addNewProduct("Bread", "Whole Wheat", 2);
            productDBHelper.addNewProduct("Football", "Nike", 2);
            Toast.makeText(getApplicationContext(), "Populated Products", Toast.LENGTH_LONG).show();
        }

        productArrayList = new ArrayList<>(productDBHelper.getAllProducts());
        showProduct(productArrayList.get(PRODUCT_ID));
        Log.i("PRODUCT_ID", Integer.toString(PRODUCT_ID));
    }

    public void showProduct(Product product){

        String showName = product.getName();
        String showDescription = product.getDescription();
        float showPrice = product.getPRICE();
        float getPriceBitcoin = convertToBitcoin(showPrice);

        TextView nameTextView2 = (TextView)findViewById(R.id.nameTextView2);
        nameTextView2.setText(showName);

        TextView descriptionTextView2 = (TextView)findViewById(R.id.descriptionTextView2);
        descriptionTextView2.setText(showDescription);

        TextView priceTextView2 = (TextView)findViewById(R.id.priceDollarsTextView2);
        priceTextView2.setText(Float.toString(showPrice));

        TextView priceBitcoinTextView2 = (TextView)findViewById(R.id.priceBitcoinTextView2);
        priceBitcoinTextView2.setText(Float.toString(getPriceBitcoin));
    }

    public void nextButton(View view) {
        PRODUCT_ID++;
        prev.setEnabled(true);

        showProduct(productArrayList.get(PRODUCT_ID));
        if(PRODUCT_ID == productArrayList.size() - 1){ next.setEnabled(false); }
        Log.i("PRODUCT_ID", Integer.toString(PRODUCT_ID));
    }

    public void prevButton(View view) {
        PRODUCT_ID--;
        next.setEnabled(true);

        showProduct(productArrayList.get(PRODUCT_ID));
        if(PRODUCT_ID == 0){ prev.setEnabled(false); }
        Log.i("PRODUCT_ID", Integer.toString(PRODUCT_ID));
    }

    public void deleteButton(View view) {
        PRODUCT_ID -= 1;
        productDBHelper.deleteProduct(productArrayList.get(PRODUCT_ID).getPRODUCT_ID());
        productArrayList = new ArrayList<>(productDBHelper.getAllProducts());

        showProduct(productArrayList.get(PRODUCT_ID));
        if (PRODUCT_ID == 0){ prev.setEnabled(false); }
        Log.i("PRODUCT_ID", Integer.toString(PRODUCT_ID));
    }

    class BitcoinRateDownloader extends AsyncTask<String, Void, Float> {

        public Float doInBackground(String... urls) {

            URL url;
            HttpURLConnection urlConnection = null;

            float priceBitcoin = 0;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                int result = urlConnection.getResponseCode();

                if (result == HttpURLConnection.HTTP_OK) {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        priceBitcoin = Float.parseFloat(line);
                        Log.i("line: ", line);
                    }
                }
            } catch (Exception e) {
                Log.i("URL: ", "FAILED");
                return priceBitcoin;
            }
            return priceBitcoin;
        }
    }

    public float convertToBitcoin(float price){

        BitcoinRateDownloader bitcoinRateDownloader = new BitcoinRateDownloader();
        float priceBitcoin = 0;
        BITCOIN_CONVERTER_URL += price;

        try {
            priceBitcoin = bitcoinRateDownloader.execute(BITCOIN_CONVERTER_URL).get();
        }
        catch (InterruptedException e) { e.printStackTrace(); }
        catch (ExecutionException e) { e.printStackTrace(); }

        // return URL to original value for re-usability
        BITCOIN_CONVERTER_URL = "https://blockchain.info/tobtc?currency=CAD&value=";

        return priceBitcoin;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMenuItem:
                Intent intent = new Intent(BrowseProductsActivity.this, AddProductActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
