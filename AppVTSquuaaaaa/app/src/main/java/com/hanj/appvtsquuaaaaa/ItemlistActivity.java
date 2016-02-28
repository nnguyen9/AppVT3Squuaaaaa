package com.hanj.appvtsquuaaaaa;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.Toast;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Jinwoo on 2/27/2016.
 */
public class ItemlistActivity extends Activity implements View.OnClickListener {
    int numberOfPeople = 3;    // whatever number of items
    int numberOfItem = 4;
    int submitId;
    LinearLayout lo;
    ArrayList<RowItems> rowItemses;
    private DivitServer server;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        rowItemses = new ArrayList<RowItems>();

        server = DivitServerAccess.getServer();

        new GetAllUsersTask().execute();
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
            submitId = findId();
            btn.setId(submitId);
            btn.setOnClickListener(this);
            relativeLayout.addView(btn);
        }
        else {                                          // items btn
            LinearLayout slotContent = new LinearLayout(this);                  // Creating a Horizontal layout
            slotContent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            slotContent.setGravity(Gravity.CENTER_VERTICAL);
            slotContent.setOrientation(LinearLayout.HORIZONTAL);
            itemSlot.addView(slotContent);

            RowItems row = new RowItems();

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
            ArrayList<Button> buttons = new ArrayList<Button>();
            for (User user : LocalProfile.getOtherUsers()) {
                Button b = addItemToHorizontalList(user.getFirst_name().substring(0, 1) + user.getLast_name().substring(0, 1));
                slotContent.addView(b);
                buttons.add(b);
            }

            row.setName(item);
            row.setPrice(price);
            row.setMembers(buttons);

            rowItemses.add(row);
        }

        return itemSlot;
    }

    static int id = 1;

    // Returns a valid id that isn't in use
    public int findId(){
        View v = findViewById(id);
        while (v != null){
            v = findViewById(++id);
        }
        return id++;
    }

    private Button addItemToHorizontalList(String person){
        Button btn = new Button(this);
        btn.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        btn.offsetLeftAndRight(5);
        btn.setText(person);
        return btn;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == submitId) {
            ArrayList<Bill> bills = new ArrayList<Bill>();
            for (RowItems i : rowItemses) {
                Bill bill = new Bill();
                bill.setPrice(i.getPrice());
                bill.setDescription(i.getDescription());

                for (int x = 0; x < i.getMembers().size(); x++) {
                    if (i.getMembers().get(x).isSelected()) {
                        bill.setPart_phone(LocalProfile.getOtherUsers().get(x).getPhone());
                        break;
                    }
                }
                bills.add(bill);
            }

            JSONArray billsArray = new JSONArray();
            for (int i = 0; i < bills.size(); i++) {
                if (!bills.get(i).getPart_phone().equals("")) {
                    billsArray.put(bills.get(i).getJSONObject());
                }
            }

            new SubmitBillsTask(billsArray.toString()).execute();

        }
    }

    private class RowItems {
        String description;
        double price;
        List<Button> members;

        public RowItems() {

        }

        public String getDescription() {
            return description;
        }

        public void setName(String name) {
            this.description = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public List<Button> getMembers() {
            return members;
        }

        public void setMembers(List<Button> members) {
            this.members = members;
        }
    }

    private class SubmitBillsTask extends AsyncTask<Void, Void, String> {
        String jsonBills;

        public SubmitBillsTask(String bil) {
            jsonBills = bil;
        }

        @Override
        protected String doInBackground(Void... params) {
            Call<String> billCall = server.processBills(LocalProfile.getPhone(), jsonBills);
            String body = "";
            try {
                body = billCall.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return body;
        }

        @Override
        protected void onPostExecute(String body) {
            Toast.makeText(ItemlistActivity.this, body, Toast.LENGTH_LONG);
        }
    }

    private class GetAllUsersTask extends AsyncTask<Void, Void, List<User>> {

        @Override
        protected List<User> doInBackground(Void... params) {
            Call<List<User>> userCall = server.getUsers();
            List<User> users = null;
            try {
                users = userCall.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return users;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            if (users == null) {
                Toast.makeText(ItemlistActivity.this, "Error getting group!", Toast.LENGTH_LONG);
            } else {
                numberOfPeople = users.size() - 1;

                ArrayList<User> otherUsers = new ArrayList<User>();
                for (User u : users) {
                    if (!u.getPhone().equals(LocalProfile.getPhone())) {
                        otherUsers.add(u);
                    }
                }

                LocalProfile.setOtherUsers(otherUsers);

                ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
                lo = (LinearLayout) findViewById(R.id.linearLayout);
                sv.setBackgroundColor(0xFF00ceb6);

                for (int i = 0; i < numberOfItem; i++)               // adding i number of items on the list\
                    lo.addView(addItemToList(i, "Item" + i, 9.99));

                lo.addView(addItemToList(-1, "Submit", 9.99));      // adding a submit button

                setContentView(sv);
            }
        }
    }
}
