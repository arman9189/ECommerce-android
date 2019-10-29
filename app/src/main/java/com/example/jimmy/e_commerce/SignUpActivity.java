package com.example.jimmy.e_commerce;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jimmy.e_commerce.Models.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class SignUpActivity extends Activity {

    private EditText textname,textusername,texttpassword,textjob,textemail;
    private Button signupbb,alreadyhave ;
    private TextView birthdatepick;
    private RadioButton maleradio,femaleradio;
private ProgressBar progressBar;
private FirebaseAuth auth;
private  String date;
private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();



        progressBar = (ProgressBar) findViewById(R.id.progressBar);
            textname= (EditText)findViewById(R.id.inputnametxt);
          textusername= (EditText)findViewById(R.id.txtUnam);
          texttpassword= (EditText)findViewById(R.id.txtpass);
          textjob= (EditText)findViewById(R.id.txtjop);
          textemail=(EditText)findViewById(R.id.inputemail);
          maleradio=(RadioButton)findViewById(R.id.malerad);
          femaleradio=(RadioButton)findViewById(R.id.femalerad);
         signupbb=(Button)findViewById(R.id.SigunUPbtn);
        alreadyhave=(Button)findViewById(R.id.haveAccountbtn);
      birthdatepick=(TextView)findViewById(R.id.birthdate);

        birthdatepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(SignUpActivity.this,
                        android.R.style.Theme_Material_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                 date=dayOfMonth+"/"+month+"/"+year;
                birthdatepick.setText(date);

            }
        };

        alreadyhave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });



        signupbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textemail.getText().toString().trim();
                String password = texttpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                final Customer cust = new Customer();
                                cust.setCus_Username(textusername.getText().toString());
                                cust.setCus_Job(textjob.getText().toString());

                                cust.setCus_name(textname.getText().toString());
                                cust.setCus_Email(textemail.getText().toString());
                                cust.setCus_Birthdate(date);

                                if(femaleradio.isChecked())
                                {
                                    cust.setCus_Gender("Female");
                                }
                                else if(maleradio.isChecked())
                                {
                                    cust.setCus_Gender("Male");
                                }

                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(cust).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUpActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                                    }
                                });




                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {

                                    Toast.makeText(SignUpActivity.this, "Registration failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                }
                                else{
                                    Toast.makeText(SignUpActivity.this, "User Created Successfully:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(SignUpActivity.this,Account.class));
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
