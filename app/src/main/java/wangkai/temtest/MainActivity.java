package wangkai.temtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    @Bind(R.id.progressBar2)
    ProgressBar mProgressBar;

    @Bind(R.id.spinner)
    Spinner mSpinner;
    @Bind(R.id.btn_test_finish_activity)
    Button finishBtn;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                finishActivity(122);
            }

            return false;
        }

    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int integer = getResources().getInteger(android.R.integer.config_shortAnimTime);
//        mProgressBar.animate()
//                .alpha(0f)
//                .setDuration(2000)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        Log.d(TAG, "onAnimationEnd() returned: ");
//                    }
//                }).start();

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivityForResult(intent, 122);
                mHandler.sendEmptyMessageDelayed(1, 30000);
            }
        });
    }

    @OnClick(R.id.btn_viewPager_trans)
    public void goViewPagerActivity() {
        startActivity(new Intent(this, ViewPagerActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        CharSequence title = item.getTitle();
        Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("way", requestCode + "");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finishActivityFromChild(Activity child, int requestCode) {
        Log.d("way", "finishActivityFromChild: " + requestCode + "");
        super.finishActivityFromChild(child, requestCode);
    }
}
