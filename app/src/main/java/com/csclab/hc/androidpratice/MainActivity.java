package com.csclab.hc.androidpratice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    /** connect variable **/
    private int serverPort=8000;
    private InetAddress serverIp;
    private Socket clientSocket;
    private BufferedReader br;
    private PrintWriter writer;
    Thread thread;

    /** Init Variable for Page 0 **/
    public EditText IPAddr0, IPAddr1, IPAddr2, IPAddr3;
    Button btnConnect;
    TextView msg;
    String IPAddr;
    int curtext;

    /** Init Variable for Page 1 **/
    EditText inputNumTxt1;
    EditText inputNumTxt2;

    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;

    /** Init Variable for Page 2 **/
    TextView textResult;
    Button return_button;

    /** Init Variable **/
    String oper = "";

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Func() for setup page 1 **/
        jumpToConnectLayout();
    }

    public boolean IPCheck(){
        if(IPAddr0.length() == 0 || IPAddr1.length() == 0 || IPAddr2.length() == 0 || IPAddr3.length() == 0){
            return false;
        }
        return true;
    }

    @Override
    public void afterTextChanged(Editable e){
        if(e.length() == 3){
            if(curtext == 0) {
                IPAddr1.requestFocus();
                curtext = 1;
            }
            else if(curtext == 1){
                IPAddr2.requestFocus();
                curtext = 2;
            }
            else if(curtext == 2){
                IPAddr3.requestFocus();
                curtext = 3;
            }
        }
        else if(e.length() == 0){
            if(curtext == 0) {
                IPAddr0.requestFocus();
                curtext = 1;
            }
            else if(curtext == 1){
                IPAddr1.requestFocus();
                curtext = 2;
            }
            else if(curtext == 2){
                IPAddr2.requestFocus();
                curtext = 3;
            }
            else if(curtext == 3){
                IPAddr3.requestFocus();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count){
    }



    public void jumpToConnectLayout(){
        setContentView(R.layout.connect_page);

        IPAddr0 = (EditText) findViewById(R.id.editText);
        IPAddr1 = (EditText) findViewById(R.id.editText2);
        IPAddr2 = (EditText) findViewById(R.id.editText3);
        IPAddr3 = (EditText) findViewById(R.id.editText4);
        IPAddr0.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        IPAddr1.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        IPAddr2.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        IPAddr3.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        curtext = 0;
        /** Text change animation **/
        IPAddr0.addTextChangedListener(this);
        IPAddr1.addTextChangedListener(this);
        IPAddr2.addTextChangedListener(this);
        IPAddr3.addTextChangedListener(this);

        btnConnect = (Button) findViewById(R.id.button);
        msg = (TextView) findViewById(R.id.textView);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (IPCheck()) {
                    IPAddr = IPAddr0.getText().toString() + "." + IPAddr1.getText().toString()
                                + "." + IPAddr2.getText().toString() + "." + IPAddr3.getText().toString();
                    thread = new Thread(Connection);
                    thread.start();
                    jumpToMainLayout();
                } else {
                    msg.setText("Please enter IP");
                }
            }

        });
    }


    private Runnable Connection=new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
            try{
                serverIp = InetAddress.getByName(IPAddr);
                System.out.println(serverIp);
                clientSocket = new Socket();
                clientSocket.bind(null);
                clientSocket.connect(new InetSocketAddress(serverIp, serverPort), 100000);

                writer = new PrintWriter( new OutputStreamWriter(clientSocket.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while(true){
                    try {
                        String line=br.readLine();
                    }catch (IOException e){

                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                Log.e("text","Socket連線="+e.toString());
                finish();
            }
        }
    };

    /** Function for page 1 setup */
    public void jumpToMainLayout() {
        //TODO: Change layout to activity_main
        // HINT: setContentView()
        setContentView(R.layout.activity_main);

        //TODO: Find and bind all elements(4 buttons 2 EditTexts)
        // inputNumTxt1, inputNumTxt2
        // btnAdd, btnSub, btnMult, btnDiv
        inputNumTxt1 = (EditText) findViewById(R.id.etNum1);
        inputNumTxt2 = (EditText) findViewById(R.id.etNum2);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);

        //TODO: Set 4 buttons' listener
        // HINT: myButton.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
    }

    /** Function for onclick() implement */
    @Override
    public void onClick(View v) {
        float num1 = 0; // Store input num 1
        float num2 = 0; // Store input num 2
        float result = 0; // Store result after calculating

        // check if the fields are empty
        if (TextUtils.isEmpty(inputNumTxt1.getText().toString())
                || TextUtils.isEmpty(inputNumTxt2.getText().toString())) {
            return;
        }

        // read EditText and fill variables with numbers
        num1 = Float.parseFloat(inputNumTxt1.getText().toString());
        num2 = Float.parseFloat(inputNumTxt2.getText().toString());

        // defines the button that has been clicked and performs the corresponding operation
        // write operation into oper, we will use it later for output
        //TODO: caculate result
        switch (v.getId()) {
            case R.id.btnAdd:
                oper = "+";
                result = num1 + num2;
                break;
            case R.id.btnSub:
                oper = "-";
                result = num1 - num2;
                break;
            case R.id.btnMult:
                oper = "*";
                result = num1 * num2;
                break;
            case R.id.btnDiv:
                oper = "/";
                result = num1 / num2;
                break;
            default:
                break;
        }
        // HINT:Using log.d to check your answer is correct before implement page turning
        Log.d("debug","ANS "+result);
        //TODO: Pass the result String to jumpToResultLayout() and show the result at Result view

        String line = num1 + " " + oper + " " + num2 + " = " + result;
        this.writer.println(line);
        this.writer.flush();
        jumpToResultLayout(line);
    }

    public void jumpToResultLayout(String resultStr){
        setContentView(R.layout.result_page);

        //TODO: Bind return_button and textResult form result view
        // HINT: findViewById()
        // HINT: Remember to give type
        return_button = (Button) findViewById(R.id.return_button);
        textResult = (TextView) findViewById(R.id.textResult);

        if (textResult != null) {
            //TODO: Set the result text
            textResult.setText(resultStr);
        }

        if (return_button != null) {
            //TODO: prepare button listener for return button
            // HINT:
            // mybutton.setOnClickListener(new View.OnClickListener(){
            //      public void onClick(View v) {
            //          // Something to do..
            //      }
            // }
            return_button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    // TODO
                    jumpToMainLayout();
                }

            });
        }
    }
}
