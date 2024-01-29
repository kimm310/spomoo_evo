package com.example.spomoo.mainscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spomoo.DigitSpan.DigitSpanData;
import com.example.spomoo.R;
import com.example.spomoo.databinding.FragmentMainDataDigitSpanBinding;
import com.example.spomoo.utility.ConstantsBroadcast;
import com.example.spomoo.utility.LocalDatabaseManager;
import com.example.spomoo.utility.SharedPrefManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class Main_Data_Digit_Span_Fragment extends Fragment {

    private FragmentMainDataDigitSpanBinding binding;  //layout binding
    private LocalDatabaseManager db;    //local SQL database
    private SharedPrefManager sharedPrefManager;    //contains inputted date
    private ListView listView;  //listview holding items
    private MaterialTextView nodataText;    //textview to show if no data is available
    DigitSpanAdapter digitSpanAdapter;    //adapter for listview items
    private ArrayList<DigitSpanData> data;  //contains sport data of inputted date
    private Context context;    //cache context to fix errors
    private LayoutInflater inflater;    //cache LayoutInflater

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainDataDigitSpanBinding.inflate(inflater, container, false);

        //cache components
        db = new LocalDatabaseManager(getContext());
        listView = binding.mainDataDigitSpanListview;
        if(getContext() != null) context = getContext();
        if(inflater != null) this.inflater = inflater;
        sharedPrefManager = SharedPrefManager.getInstance(context);
        nodataText = binding.mainDataDigitSpanNodataText;

        //set nodataText invisible at start
        nodataText.setVisibility(View.GONE);

        //create listview containing items based on inputted date of Main_Data_Fragment
        createList();

        //when long clicking an item, show dialog to ask if selected data should be deleted
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            //show dialog to ask for deletion of data
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle(R.string.main_data_dialog_delete_heading)
                    .setMessage(R.string.main_data_dialog_delete_content)
                    .setCancelable(true)
                    .setIcon(R.drawable.baseline_delete_24)
                    .setNegativeButton(R.string.main_data_dialog_delete_no, (dialog, which) -> {
                    })
                    .setPositiveButton(R.string.main_data_dialog_delete_yes, (dialog, which) -> {
                        //delete data
                        db.deleteDigitSpanData(String.valueOf(data.get(position).getDigitSpanId()));
                        data.remove(position);
                        digitSpanAdapter.remove(digitSpanAdapter.getItem(position));
                        digitSpanAdapter.notifyDataSetChanged();
                    })
                    .show();
            return true;
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //creates the listview items based on the inputted date of Main_Data_Fragment which is received via broadcast
    private void createList(){
        //create listview from saved date
        createListHelper(sharedPrefManager.getString(SharedPrefManager.KEY_DATA_DATE));

        //setup broadcast receiver to receive inputted date
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantsBroadcast.BROADCAST_DATA_DATE_INPUT);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                createListHelper(intent.getStringExtra(ConstantsBroadcast.BROADCAST_DATA_DATE_INPUT_VALUE));
            }
        };
        getContext().registerReceiver(broadcastReceiver, intentFilter);
    }

    //setup listview based on inputted date
    private void createListHelper(String date){
        //if no date has been inputted, do nothing
        if(date.isEmpty())
            return;

        //get data of inputted date
        data = db.readDigitSpanData(date);
        //if no data available
        if(data.size() == 0){
            nodataText.setText(context.getString(R.string.main_data_no_data));
            nodataText.setVisibility(View.VISIBLE);
            digitSpanAdapter = new DigitSpanAdapter(context, data, new ArrayList<String>(), inflater);
            listView.setAdapter(digitSpanAdapter);
            return;
        }

        //set nodataText invisible
        nodataText.setText("");
        nodataText.setVisibility(View.GONE);

        //sort data by the start time
        data.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));

        //create listview items
        ArrayList<String> dummy = new ArrayList<>();
        for (int ind = 0; ind < data.size(); ind++)
            dummy.add("");
        digitSpanAdapter = new DigitSpanAdapter(context, data, dummy, inflater);
        listView.setAdapter(digitSpanAdapter);
    }

    //custom adapter for listview items
    class DigitSpanAdapter extends ArrayAdapter<String> {

        LayoutInflater layoutInflater;
        ArrayList<DigitSpanData> digitSpans;

        DigitSpanAdapter(Context context, ArrayList<DigitSpanData> digitSpans, ArrayList<String> test, LayoutInflater layoutInflater) {
            super(context, R.layout.listview_item_digit_span, R.id.listview_digit_span_title_text, test);
            this.layoutInflater = layoutInflater;
            this.digitSpans = digitSpans;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //get all UI elements
            View row = this.layoutInflater.inflate(R.layout.listview_item_digit_span, parent, false);
            MaterialTextView time = row.findViewById(R.id.listview_digit_span_title_text);
            MaterialTextView max_sequence = row.findViewById(R.id.listview_digit_span_sequence_text);
            MaterialTextView total = row.findViewById(R.id.listview_digit_span_total_text);
            MaterialTextView correct = row.findViewById(R.id.listview_digit_span_correct_text);

            //load texts
            time.setText(digitSpans.get(position).getTime());
            max_sequence.setText(context.getString(R.string.main_data_tab_digit_span_max_sequence_length) + ": " + digitSpans.get(position).getMax_sequence_length());
            total.setText(context.getString(R.string.main_data_tab_digit_span_total_tasks) + ": " + digitSpans.get(position).getTotal_tasks());
            correct.setText(getString(R.string.main_data_tab_digit_span_correct_tasks) + ": " + digitSpans.get(position).getCorrect_tasks());

            return row;
        }
    }
}
