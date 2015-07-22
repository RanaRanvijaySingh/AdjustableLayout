package rana.com.adjustablelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText etEnterName;
    private AdjustableLayout adjustableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {
        etEnterName = (EditText)findViewById(R.id.etEnterName);
        adjustableLayout = (AdjustableLayout)findViewById(R.id.container);
    }

    public void onClickAddNewView(View view){
        String name = etEnterName.getText().toString();
        if (!TextUtils.isEmpty(name)){
            final View newView = LayoutInflater.from(this).inflate(R.layout.view_chip_text,null);
            TextView tvName = (TextView)newView.findViewById(R.id.tvName);
            ImageView ivRemove = (ImageView)newView.findViewById(R.id.ivRemove);
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adjustableLayout.removeView(newView);
                }
            });
            tvName.setText(name);
            adjustableLayout.addView(newView);
        }else {
            Toast.makeText(this,"Enter some text",Toast.LENGTH_SHORT).show();
        }
    }
}
