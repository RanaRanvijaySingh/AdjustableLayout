package rana.com.adjustablelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    String news = "A letter from a man to his mother flown out of Paris by hot air balloon during" +
            " the Prussian siege has turned up in Australia's National Archives, which said  " +
            "today that  it was keen to know the family's fate. The Franco-Prussian War saw the " +
            "Germans completely surround Paris for more than four months in 1870";
    List<String> stringList = new ArrayList<>();
    private AdjustableLayout adjustableLayout; //Custom class extending Linear layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {
        adjustableLayout = (AdjustableLayout)findViewById(R.id.container); //Custom layout file
        setStringList();
        addViewInALoop();
    }

    private void setStringList() {
        String[] strings = news.split(" ");
        stringList = new ArrayList<>(Arrays.asList(strings));
    }

    /**
     * Function to add view in a loop
     */
    private void addViewInALoop() {
        for (int i=0;i<stringList.size();i++){
            final View newView = LayoutInflater.from(this).inflate(R.layout.view_chip_text,null);
            TextView tvName = (TextView)newView.findViewById(R.id.tvName);
            tvName.setText(stringList.get(i));
            adjustableLayout.addingMultipleView(newView);
        }
        adjustableLayout.invalidateView();
    }
}
