package com.hanj.appvtsquuaaaaa;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Jinwoo on 2/27/2016.
 */
public class ItemlistActivity extends Activity {
    int numberOfPeople = 3;    // whatever number of items
    int numberOfItem = 4;
    LinearLayout lo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        lo = (LinearLayout) findViewById(R.id.linearLayout);
        sv.setBackgroundColor(0xFF00ceb6);

        for (int i = 0; i < numberOfItem; i++)               // adding i number of items on the list\
            lo.addView(addItemToList(i, "Item" + i, 9.99));

        lo.addView(addItemToList(-1, "Submit", 9.99));      // adding a submit button

        setContentView(sv);
    }

    private HorizontalScrollView addItemToList(int n, String item, double price){
        HorizontalScrollView itemSlot = new HorizontalScrollView(this);     // Creating Horizontal scroll view
        itemSlot.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));



        if(n == -1) {                                   // submit btn
            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.setVerticalGravity(Gravity.CENTER_HORIZONTAL);
            relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            itemSlot.addView(relativeLayout);

            Button btn = new Button(this);
            btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            btn.setBackgroundColor(Color.GREEN);
            btn.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            btn.setText(item);
            relativeLayout.addView(btn);
        }
        else {                                          // items btn
            LinearLayout slotContent = new LinearLayout(this);                  // Creating a Horizontal layout
            slotContent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            slotContent.setGravity(Gravity.CENTER_VERTICAL);
            slotContent.setOrientation(LinearLayout.HORIZONTAL);
            itemSlot.addView(slotContent);


            TextView itemName = new TextView(this);
            itemName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            itemName.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            itemName.setText(item + ": ");

            TextView itemPrice = new TextView(this);
            itemPrice.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            itemPrice.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            itemPrice.setText("$" + price);

            slotContent.addView(itemName);                         // adding item name and price
            slotContent.addView(itemPrice);
            for (int count = 0; count < numberOfPeople; count++)               // adding i number of people buttons onto the list
                slotContent.addView(addItemToHorizontalList("JW"));
        }

        return itemSlot;
    }

    private Button addItemToHorizontalList(String person){
        Button btn = new Button(this);
        btn.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        btn.offsetLeftAndRight(5);
        btn.setText(person);
        return btn;
    }
}
