package pr.tongson.train_diy_recycleview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mDiyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mDiyRecyclerView = (RecyclerView) findViewById(R.id.diy_recycler_view);
        mDiyRecyclerView.setAdapter(new RecyclerView.Adapter() {
            private void initView(View convertView, int position) {
                tvValue = (TextView) convertView.findViewById(R.id.tv_value);
                tvValue.setText("第" + position + "行");
            }

            private TextView tvValue;

            @Override
            public View onCreateViewHolder(int position, View convertView, ViewGroup parent) {
                if (position % 2 == 0) {
                    convertView = MainActivity.this.getLayoutInflater().inflate(R.layout.item_table2, parent, false);
                    initView(convertView, position);
                } else {
                    convertView = MainActivity.this.getLayoutInflater().inflate(R.layout.item_table, parent, false);
                    initView(convertView, position);
                }
                return convertView;
            }

            @Override
            public View onBinderViewHolder(int position, View convertView, ViewGroup parent) {
                initView(convertView, position);
                return convertView;
            }

            @Override
            public int getItemViewType(int row) {
                if (row % 2 == 0) {
                    return 0;
                } else {
                    return 1;
                }
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getCount() {
                return 30000;
            }
        });
    }
}
