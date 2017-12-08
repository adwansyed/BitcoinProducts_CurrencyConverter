package com.mobiledevices.bitcoinproducts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddProductActivity extends AppCompatActivity {

    final ProductDBHelper productDBHelper = new ProductDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

    }

    public void addNewProduct(View view) {
        EditText newNameEditText = (EditText)findViewById(R.id.nameEditText);
        EditText newDescriptionEditText = (EditText)findViewById(R.id.descriptionEditText);
        EditText newPriceEditText = (EditText)findViewById(R.id.priceEditText);

        // add new product to database
        productDBHelper.addNewProduct(
                newNameEditText.getText().toString(),
                newDescriptionEditText.getText().toString(),
                Float.parseFloat(newPriceEditText.getText().toString()
        ));

        Intent i = new Intent(AddProductActivity.this, BrowseProductsActivity.class);
        finish();
        startActivity(i);
    }

    public void cancel(View view) {
        EditText newNameEditText = (EditText)findViewById(R.id.nameEditText);
        EditText newDescriptionEditText = (EditText)findViewById(R.id.descriptionEditText);
        EditText newPriceEditText = (EditText)findViewById(R.id.priceEditText);

        // clear fields
        newNameEditText.setText("");
        newDescriptionEditText.setText("");
        newPriceEditText.setText("");

        Intent i = new Intent(AddProductActivity.this, BrowseProductsActivity.class);
        finish();
        startActivity(i);
    }
}
