package com.ali.pf_monthly_task.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ali.pf_monthly_task.R;
import com.ali.pf_monthly_task.utils.Singleton;
import com.ali.pf_monthly_task.databinding.ActivitySignInBinding;
import com.ali.pf_monthly_task.models.Login;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    Singleton singleton = Singleton.getInstance();
    ArrayList<Login> usersList = singleton.usersList;
    CheckBox rememberme;
    Boolean chk = false;
    boolean userFound = false;
    private KProgressHUD hud;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String email, password;
    ImageButton ibEye;
    boolean isEye = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        bindViews();
        signIn();

    }

    private void signIn() {
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    kProgressHud();
                    callUser();
                    if(rememberme.isChecked()) {
                        editor.putString("userEmail", email);
                        editor.putString("userPassword", password);
                        editor.putBoolean("Remembermeto", true);
                        editor.apply();
                    } else {
                        editor.putBoolean("Remembermeto", false);
                        editor.apply();
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "Enter valid Email / Password!", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private boolean checkValidation() {
        email = binding.etEmail.getText().toString();
        password = binding.etPassword.getText().toString();
        if (email.isEmpty()) {
            binding.etEmail.setError("Enter your Email!");
            //    progressDialog.dismiss();
            return false;
        } else {
            if (!email.contains("@") && !email.contains(".com")) {
                binding.etEmail.setError("Enter valid Email!");
                return false;
            }
        }
        if (password.isEmpty()) {
            binding.etPassword.setError("Enter your Password!");
            //   progressDialog.dismiss();
            return false;
        }
        return true;
    }

    private void bindViews() {
        rememberme = findViewById(R.id.cbRemember);
        ibEye = findViewById(R.id.ib_eye);
       // binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        SharedPreferences sp = getSharedPreferences("userLogin", MODE_PRIVATE);
        editor = sp.edit();


        chk = sp.getBoolean("Remembermeto", false);

        if (chk) {

            binding.etEmail.setText(sp.getString("userEmail", ""));
            binding.etPassword.setText(sp.getString("userPassword", ""));
            rememberme.setChecked(true);
        } else {
            rememberme.setChecked(false);
        }
    }

    public void callUser() {
        if(usersList.isEmpty()){
         //   kProgressHud();
            singleton.apiInterface.getUsers().enqueue(new Callback<List<Login>>() {
                @Override
                public void onResponse(Call<List<Login>> call, Response<List<Login>> response) {
                    if (response.isSuccessful()) {
                        usersList.addAll(response.body());
                        checkUser();
                    }
                    else {
                        hud.dismiss();
                        Toast.makeText(SignInActivity.this, "error :" + response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<Login>> call, Throwable t) {
                    hud.dismiss();
                    Toast.makeText(SignInActivity.this, "Network Error try later!" , Toast.LENGTH_LONG).show();
                }
            });
        }else{
          checkUser();
        }

    }

    private void checkUser() {
        for (Login user : usersList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                userFound = true;
                break;
            }
        }
        if (userFound == true) {
            hud.dismiss();
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            Toast.makeText(SignInActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            hud.dismiss();
            Toast.makeText(SignInActivity.this, "Invalid UserName/Password!", Toast.LENGTH_LONG).show();
        }
    }


    private void kProgressHud() {
        hud = KProgressHUD.create(SignInActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setWindowColor(getResources().getColor(R.color.purple_500))
                .setLabel("Please wait")
                .setAnimationSpeed(2)
                .setCancellable(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface
                                                 dialogInterface) {
                        Toast.makeText(SignInActivity.this, "You " + "cancelled manually!", Toast.LENGTH_SHORT).show();
                    }
                });
        hud.show();
    }

    public void togglePassword(View view) {
        if(isEye){
            binding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
          //  binding.ibEye.setImageDrawable(SignInActivity.this.R.drawable.view);
            binding.ibEye.setImageDrawable(getDrawable(R.drawable.view));
            binding.etPassword.setSelection(binding.etPassword.length());
            isEye = false;

        } else{
            isEye = true;
            binding.ibEye.setImageDrawable(getDrawable(R.drawable.invisible));

            binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.etPassword.setSelection(binding.etPassword.length());
        }


    }
}


