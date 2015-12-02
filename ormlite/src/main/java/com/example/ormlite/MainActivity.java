package com.example.ormlite;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.et_id)
    EditText idEt;

    @Bind(R.id.et_name)
    EditText nameEt;

   @Bind(R.id.et_token)
    EditText tokenEt;

    @Bind(R.id.tv_queryResult)
    TextView queryResultTv;

    private UserDao userDao;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userDao = new UserDao(this);
    }

    @OnClick(R.id.btn_add)
    public void add(View view) {
        User user = getUser();
        userDao.add(user);

        printAllUsers();

    }

    @OnClick(R.id.btn_delete)
    public void delete(View view) {
        User user = getUser();
        userDao.delete(user);

        printAllUsers();
    }

    @OnClick(R.id.btn_update)
    public void update(View view) {
        User user = getUser();
        userDao.update(user);

        printAllUsers();
    }

    @OnClick(R.id.btn_query)
    public void queryAll(View view) {
        printAllUsers();
    }



    private void printAllUsers() {
        List<User> users = userDao.getUsers();
        StringBuilder builder = new StringBuilder();
        if (null != users && users.size() > 0) {
            for (int i = 0; i < users.size(); i++) {
                String s = users.get(i).toString();
                L.d(s + "\n");
                builder.append(s + "\n");
            }
            queryResultTv.setText(builder.toString());
        }else {
            queryResultTv.setText("");
        }
    }

    private User getUser() {
        String id = idEt.getText().toString().trim();
        String name = nameEt.getText().toString().trim();
        String token = tokenEt.getText().toString().trim();

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setApp_token(token);
        return user;

    }

}
