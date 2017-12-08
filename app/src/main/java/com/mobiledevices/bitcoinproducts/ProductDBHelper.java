package com.mobiledevices.bitcoinproducts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onique on 2017-11-11.
 */

public class ProductDBHelper extends SQLiteOpenHelper{

    private static final String PRODUCTS_DB = "PRODUCTS.db";
    private static final String DELETE_TABLE = "DROP TABLE products";
    private static final String CREATE_TABLE = "  CREATE TABLE IF NOT EXISTS" +
            " products( productID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " name VARCHAR," +
            " description VARCHAR," +
            " price FLOAT)";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DELETE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    public ProductDBHelper(Context context){ super(context, PRODUCTS_DB, null, 1); }

    public List<Product> getAllProducts(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Product> productList = new ArrayList<>();

        String[] columns = new String[] {"productID", "name", "description", "price"};
        String[] selectionArguments = new String[] {};
        Cursor cursor = sqLiteDatabase.query("products", columns, "", selectionArguments ,"", "", "productID");

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            int productID = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            float price = cursor.getFloat(3);

            productList.add(new Product(productID, name, description, price));
            cursor.moveToNext();
        }
        cursor.close();
        return productList;
    }

    public void deleteProduct(int productID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("products", "productID=?", new String[] {Integer.toString(productID)});
    }

    public Product addNewProduct(String name, String description, float price){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("price", price);

        int productID = (int) sqLiteDatabase.insert("products", null, contentValues);
        Product product = new Product(productID, name, description, price);
        return product;
    }

}
