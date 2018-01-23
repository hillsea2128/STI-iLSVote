package ilsvote.edu.sti.stiilsvote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_one);
        SharedPreferences settings = getSharedPreferences("default_pref", 0);
        boolean RememberedLogin = settings.getBoolean("REMEMBER_LOGIN", false);

        if (RememberedLogin) {
            String student_name = settings.getString("USERNAME","");
            String student_code = settings.getString("USERCODE","");
            String student_no = settings.getString("USERNO","");
            String student_sect = settings.getString("USERSECTION","");
            Integer student_votes = settings.getInt("USERTOKENS",0);
            Intent intent = new Intent(this, ContestantsListActivity.class);
            intent.putExtra("STUDNAME",student_name);
            intent.putExtra("STUDCODE",student_code);
            intent.putExtra("STUDNO",student_no);
            intent.putExtra("STUDSECT",student_sect);
            intent.putExtra("STUDTOKENS",student_votes);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        finish();
    }
}
