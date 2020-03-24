package pr.tongson.train_materialdesign;

import androidx.appcompat.app.AppCompatActivity;
import pr.tongson.train_materialdesign.coordinator.ItemFragment;
import pr.tongson.train_materialdesign.coordinator.dummy.DummyContent;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, ItemFragment.newInstance(1)).commitNow();
        }

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        if(item.getClz() instanceof  Class<?>){
            Intent intent = new Intent(this, (Class<?>) item.getClz());
            startActivity(intent);
        }
    }
}
