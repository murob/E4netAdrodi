package net.e4net.firstapp.Acitivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.e4net.firstapp.Entities.ListItem;
import net.e4net.firstapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    ListView basicLV;
    
    List<ListItem> listContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        basicLV = findViewById(R.id.basicLV);
        
        listContents = new ArrayList<>();
        listContents.add(new ListItem("제목입니다", "내용입니다."));
        listContents.add(new ListItem("스우파", "노제"));
        listContents.add(new ListItem("카카오", "레오"));
        listContents.add(new ListItem("테란", "마린"));
        listContents.add(new ListItem("라인", "라이언"));

        CustomAdapter customAdapter = new CustomAdapter(listContents);
        basicLV.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter {

        List<ListItem> list;

        public CustomAdapter(List<ListItem> list){
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.basic_lv_elem, null);

            TextView titleTV = convertView.findViewById(R.id.lvElemTitle);
            TextView valueTV = convertView.findViewById(R.id.lvElemValue);

            titleTV.setText(list.get(position).getTitle());
            valueTV.setText(list.get(position).getValue());

            return convertView;
        }
    }
}