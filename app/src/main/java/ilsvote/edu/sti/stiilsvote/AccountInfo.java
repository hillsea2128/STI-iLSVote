package ilsvote.edu.sti.stiilsvote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AccountInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
