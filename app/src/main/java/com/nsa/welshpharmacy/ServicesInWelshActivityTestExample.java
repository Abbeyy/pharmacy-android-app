package com.nsa.welshpharmacy;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by c1502032 on 19/03/2018.
 */

public class ServicesInWelshActivityTestExample extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_test_example);
        ToggleButtonListAdapter adapter = new ToggleButtonListAdapter(this, getResources().getStringArray(R.array.services_in_welsh));
        ListView lv = (ListView) findViewById(R.id.services_list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private final String[] values = getBaseContext().getResources().getStringArray(R.array.services_in_welsh);

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), values[position] + " selected", Toast.LENGTH_LONG).show();
            }
        });
    }

    public class ToggleButtonListAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public ToggleButtonListAdapter(Context context, String[] values) {
            super(context, R.layout.activity_services_test_example, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.row_layout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.settings_text);
            ToggleButton toggleButton = (ToggleButton) rowView.findViewById(R.id.toggle_button);

            toggleButton.setOnClickListener(new View.OnClickListener() {
                private final String[] values = getContext().getResources().getStringArray(R.array.services_in_welsh);

                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), values[position] + " checked", Toast.LENGTH_LONG).show();
                }
            });

            textView.setText(values[position]);

            return rowView;
        }
    }

}
