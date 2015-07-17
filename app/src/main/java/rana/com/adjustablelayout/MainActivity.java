package rana.com.adjustablelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private AdjustableLayout adjustableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {
        adjustableLayout = (AdjustableLayout)findViewById(R.id.container);

    }

    public void onClickAddNewView(View view){
        View newView = LayoutInflater.from(this).inflate(R.layout.view_chip_text,null);
        adjustableLayout.addView(newView);
    }

}
