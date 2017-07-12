package com.example.user.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import static android.system.Os.remove;

public class MainActivity extends AppCompatActivity {

    EditText loginid,password;
    Button submit;
    CheckBox checkBox1,checkBox2;
    String myLoginid, myPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox1 = (CheckBox)findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox)findViewById(R.id.checkBox2);

        checkBox1.setOnCheckedChangeListener(checkBoxOnCheckedChange1);
        checkBox2.setOnCheckedChangeListener(checkBoxOnCheckedChange2);

        findview();

    }

    public void init(){

        SharedPreferences setting = getSharedPreferences("login",MODE_PRIVATE);

        myLoginid = setting.getString("loginid", "");
        myPassword = setting.getString("password", "");

    }

    public void init2(){

        SharedPreferences setting = getSharedPreferences("history",MODE_PRIVATE);

        SharedPreferences.Editor editor = setting.edit();
        editor.remove("password");
        editor.commit();
    }

    public void findview(){
        loginid = (EditText)findViewById(R.id.loginid);
        password = (EditText)findViewById(R.id.password);
        submit = (Button)findViewById(R.id.submit);


        loginid.setText(myLoginid);
        password.setText(myPassword);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSubmit();
            }
        });
    }

    public void doSubmit(){


        if(loginid.getText().toString().equals("")){
            new AlertDialog.Builder(this)
                    .setTitle("登入失敗")
                    .setMessage("帳號不得為空")
                    .setPositiveButton("ok",null)
                    .show();

            loginid.setFocusableInTouchMode(true);
            loginid.requestFocus();

        }else if (password.getText().toString().equals("")){
            new AlertDialog.Builder(this)
                    .setTitle("登入失敗")
                    .setMessage("密碼不得為空")
                    .setPositiveButton("ok",null)
                    .show();

            password.setFocusableInTouchMode(true);
            password.requestFocus();

        }else {

            Intent intent = new Intent(this, CheckActivity.class);

            Bundle bag = new Bundle();
            bag.putString("loginid", loginid.getText().toString());
            bag.putString("password", password.getText().toString());

            intent.putExtras(bag);
            startActivity(intent);

        }
    }

    CompoundButton.OnCheckedChangeListener checkBoxOnCheckedChange1 =
            new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                { //buttonView 為目前觸發此事件的 CheckBox, isChecked 為此 CheckBox 目前的選取狀態
                    if(isChecked)//等於 buttonView.isChecked()
                    {
                        Toast.makeText(MainActivity.this,"已選取",Toast.LENGTH_SHORT).show();
                        checkBox1.setChecked(true); //預設為以勾選，false為預設取消勾選

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"已取消",Toast.LENGTH_SHORT).show();
                        checkBox1.setChecked(false); //預設為以勾選，false為預設取消勾選
                    }
                }
            };

    CompoundButton.OnCheckedChangeListener checkBoxOnCheckedChange2 =
            new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                { //buttonView 為目前觸發此事件的 CheckBox, isChecked 為此 CheckBox 目前的選取狀態
                    if(isChecked)//等於 buttonView.isChecked()
                    {
                        Toast.makeText(MainActivity.this,"已選取",Toast.LENGTH_SHORT).show();
                        checkBox2.setChecked(true); //預設為以勾選，false為預設取消勾選
                        init();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"已取消",Toast.LENGTH_SHORT).show();
                        checkBox2.setChecked(false); //預設為以勾選，false為預設取消勾選

                        init2();

                    }
                }
            };

}
