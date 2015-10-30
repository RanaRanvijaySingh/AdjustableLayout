package rana.com.adjustablelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText etEnterName;
    private AdjustableLayout adjustableLayout; //Custom class extending Linear layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {
        etEnterName = (EditText)findViewById(R.id.etEnterName);
        adjustableLayout = (AdjustableLayout)findViewById(R.id.container); //Custom layout file
        addViewInALoop();
    }

    public void onClickAddNewView(View view){
        /**
         * User only one function at a time to view different demo.
         */
//        addChipsView();
//        addRandomView();
//        addButtons();
    }

    /**
     * Adding buttons
     */
    private void addButtons() {
        Button button = new Button(this);
        button.setText("Button");
        adjustableLayout.addView(button);
    }

    /**
     * Using view_images layout
     */
    private void addRandomView() {
        String name = etEnterName.getText().toString();
        if (!TextUtils.isEmpty(name)){
          final View newView = LayoutInflater.from(this).inflate(R.layout.view_images,null);
            TextView tvNumber = (TextView)newView.findViewById(R.id.tvNumber);
            tvNumber.setText(name);
            ImageView ivRemove = (ImageView)newView.findViewById(R.id.ivRemove);
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adjustableLayout.removeView(newView);
                }
            });
            tvNumber.setText(name);
            adjustableLayout.addView(newView);
        }else {
            Toast.makeText(this,"Enter some text",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Using view_chip_text layout
     */
    private void addChipsView() {
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

    /**
     * Function to add view in a loop
     */
    private void addViewInALoop() {
        for (int i=0;i<=6;i++){
            final View newView = LayoutInflater.from(this).inflate(R.layout.view_chip_text,null);
            TextView tvName = (TextView)newView.findViewById(R.id.tvName);
            tvName.setText("name-"+i);
            adjustableLayout.addView(newView);
        }
        adjustableLayout.invalidateView();
    }
}
