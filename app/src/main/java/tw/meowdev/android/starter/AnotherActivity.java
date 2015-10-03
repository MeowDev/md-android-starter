package tw.meowdev.android.starter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tw.meowdev.android.starter.app.BusProvider;

public class AnotherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button = (Button)findViewById(R.id.buttonChange);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                BusProvider.get().post("Cat Xiao");
            }
        });
    }
}