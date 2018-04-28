package com.example.ghwlc.text;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_ENABLE_BT = 10;
    static final int RESULT_CANCELDE = -1;
    int Devices_count;

    BluetoothAdapter bluetooth_adapter = BluetoothAdapter.getDefaultAdapter();
    Set<BluetoothDevice> pairedDevices;

    byte mDelimiter = '$';
    BluetoothSocket b_socket;
    OutputStream b_OutputStream;
    InputStream b_InputStream;
    Thread b_workerThread;
    int readBufferPositon = 0;        //   버퍼 내 수신 문자 저장 위치
    byte[] b_data;
    String data = null;

    int readBufferPosition;
    byte[] readBuffer;
    String received_data;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        backPressCloseHandler = new BackPressCloseHandler(this);

        Button search_Btn= (Button) findViewById(R.id.search_Btn);
        search_Btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //블루투스 검사
                check_bluetooth();
            }
        });


        Button btn1_1= (Button) findViewById(R.id.btn1_1);
        btn1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"1to1",Toast.LENGTH_SHORT).show();
                data ="1\n";
                b_data = data.getBytes();
                try {
                    b_OutputStream.write(b_data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Button btn1_2= (Button) findViewById(R.id.btn1_2);
        btn1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"1to2",Toast.LENGTH_SHORT).show();
                data ="2\n";
                b_data = data.getBytes();
                try {
                    b_OutputStream.write(b_data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Button btn1_3= (Button) findViewById(R.id.btn1_3);
        btn1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getBaseContext(),"1to3",Toast.LENGTH_SHORT).show();
                data ="3\n";
                b_data = data.getBytes();
                try {
                    b_OutputStream.write(b_data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Button btn1_4= (Button) findViewById(R.id.btn1_4);
        btn1_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"1to4",Toast.LENGTH_SHORT).show();
                data ="4\n";
                b_data = data.getBytes();
                try {
                    b_OutputStream.write(b_data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Button btn1_5= (Button) findViewById(R.id.btn1_5);
        btn1_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"1to5",Toast.LENGTH_SHORT).show();
                data ="5\n";
                b_data = data.getBytes();
                try {
                    b_OutputStream.write(b_data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btn1_6= (Button) findViewById(R.id.btn1_6);
        btn1_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"1to5",Toast.LENGTH_SHORT).show();
                data ="6\n";
                b_data = data.getBytes();
                try {
                    b_OutputStream.write(b_data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btn1_7= (Button) findViewById(R.id.btn1_7);
        btn1_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"1to5",Toast.LENGTH_SHORT).show();
                data ="7\n";
                b_data = data.getBytes();
                try {
                    b_OutputStream.write(b_data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    void check_bluetooth()
    {

        if(bluetooth_adapter == null) {
            //장치가 블루투스를 지원하지 않는 경우.
            Toast.makeText(this, "device not bluetooth ", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            // 장치가 블루투스를 지원하는 경우.
            if(!bluetooth_adapter.isEnabled()) {
                //장치가 지원가능하지만 비활성화 일 경우  활성화 여부를 묻는 메세지창이 뜸
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            else
            {   //장치가 지원가능하고 활성화일경우 장치 검색
                Toast.makeText(this, "search device ", Toast.LENGTH_SHORT).show();
                //디바이스 리스트 출력
                select_device();
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK) {
                    // 블루투스가 활성 상태로 변경되고 디바이스 list 출력
                    select_device();
                }
                else if(resultCode == RESULT_CANCELDE) {
                    //블루투스 활성 취소시
                    Toast.makeText(getBaseContext(),"bluetooth enable",Toast.LENGTH_SHORT).show();
                    finish();  //  어플리케이션 종료

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    void select_device() {
        pairedDevices =bluetooth_adapter.getBondedDevices();
        Devices_count =  pairedDevices.size();

        if (Devices_count ==0) {
            //  페어링 된 장치가 없는 경우
            Toast.makeText(getBaseContext(),"pair device not!",Toast.LENGTH_SHORT).show();
           // finish();    // 어플리케이션 종료
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("블루투스 장치 선택");

        // 페어링 된 블루투스 장치의 이름 목록 작성
        List<String> listItems = new ArrayList<String>();
        for (BluetoothDevice device : pairedDevices) {
            listItems.add(device.getName()+'\n'+device.getAddress());
        }
            listItems.add("취소");    // 취소 항목 추가

            final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);

            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if (item == Devices_count) {
                        // 연결할 장치를 선택하지 않고 '취소'를 누른 경우
                        finish();
                    } else {
                        // 연결할 장치를 선택한 경우
                        // 선택한 장치와 연결을 시도함
                        Toast.makeText(MainActivity.this, items[item].toString(), Toast.LENGTH_SHORT).show();
                        //connectToSelectedDevices(items[item].toString());
                        String address = items[item].toString().split("\n")[1];
                        BluetoothDevice device = bluetooth_adapter.getRemoteDevice(address);
                        connect(device);
                    }
                }
            });
            builder.setCancelable(false);    // 뒤로 가기 버튼 사용 금지
            AlertDialog alert = builder.create();
            alert.show();

    }
    //해당 블르투스 객체 연결
    private void connect(BluetoothDevice device) {
          UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        try {
            // 소켓 생성
              b_socket = device.createRfcommSocketToServiceRecord(uuid);
            // RFCOMM 채널을 통한 연결
              b_socket.connect();
            // 데이터 송수신을 위한 스트림 열기
              b_OutputStream = b_socket.getOutputStream();
               b_InputStream = b_socket.getInputStream();
            // 데이터 수신 준비
             beginListenForData();
        }catch(Exception e) {
            // 블루투스 연결 중 오류 발생
             finish();   // 어플 종료
        }

    }

    //해당 블르투스 객체 연결 x
    private void connectToSelectedDevices(String selectedDevice) {
        //BluetoothDevice mRemoteDevice = getDeviceFromBondedList(selectedDevice);
      //  UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        
        try {
            // 소켓 생성
          //  b_socket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);
            // RFCOMM 채널을 통한 연결
          //  b_socket.connect();
            // 데이터 송수신을 위한 스트림 열기
          //  b_OutputStream = b_socket.getOutputStream();
         //   b_InputStream = b_socket.getInputStream();
            // 데이터 수신 준비
           // beginListenForData();
        }catch(Exception e) {
            // 블루투스 연결 중 오류 발생
           // finish();   // 어플 종료
        }

    }

    private void beginListenForData() {
        final Handler handler = new Handler();

        readBufferPosition = 0;                 // 버퍼 내 수신 문자 저장 위치.
        readBuffer = new byte[1024];            // 수신 버퍼.

        // 문자열 수신 쓰레드.
        b_workerThread = new Thread(new Runnable()
        {
            @Override
            public void run() {
                // interrupt() 메소드를 이용 스레드를 종료시키는 예제이다.
                // interrupt() 메소드는 하던 일을 멈추는 메소드이다.
                // isInterrupted() 메소드를 사용하여 멈추었을 경우 반복문을 나가서 스레드가 종료하게 된다.
                while(!Thread.currentThread().isInterrupted()) {
                    try {
                        // InputStream.available() : 다른 스레드에서 blocking 하기 전까지 읽은 수 있는 문자열 개수를 반환함.
                        int byteAvailable = b_InputStream.available();   // 수신 데이터 확인
                        if(byteAvailable > 0) {                        // 데이터가 수신된 경우.
                            byte[] packetBytes = new byte[byteAvailable];
                            // read(buf[]) : 입력스트림에서 buf[] 크기만큼 읽어서 저장 없을 경우에 -1 리턴.
                            b_InputStream.read(packetBytes);
                            for(int i=0; i<byteAvailable; i++) {
                                byte b = packetBytes[i];

                                if(b ==mDelimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    //  System.arraycopy(복사할 배열, 복사시작점, 복사된 배열, 붙이기 시작점, 복사할 개수)
                                    //  readBuffer 배열을 처음 부터 끝까지 encodedBytes 배열로 복사.
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);

                                    received_data = new String(encodedBytes, "EUC-KR");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable(){
                                        // 수신된 문자열 데이터에 대한 처리.

                                        @Override
                                        public void run() {
                                            // mStrDelimiter = '\n';

                                            Toast.makeText(getBaseContext(),received_data.split("\n")[0],Toast.LENGTH_SHORT).show();
                                        }

                                    });
                                }
                                else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }

                    } catch (Exception e) {    // 데이터 수신 중 오류 발생.
                        Toast.makeText(getApplicationContext(), "데이터 수신 중 오류가 발생 했습니다.", Toast.LENGTH_LONG).show();
                        finish();            // App 종료.
                    }
                }
            }
        });
        b_workerThread.start();
    }

    //클릭한 블루투스객체가 페어링 리스트에 있는지 확인
    BluetoothDevice getDeviceFromBondedList(String name) {
        BluetoothDevice selectedDevice = null;
        for(BluetoothDevice device : pairedDevices) {
            if(name.equals(device.getName())) {
                selectedDevice = device;
                break;
            }
        }
        Toast.makeText(this, "selected deivce"+selectedDevice.getName(), Toast.LENGTH_SHORT).show();
        return selectedDevice;
    }
    //어플이 종료될때 실행되는 메소드  종료될때 블루투스 스트림 소켓을 닫아줘야한다.
    //데이터 송신시 쓰레드를 종료해줘야한다.
    protected void onDestroy() {
        try {
            b_workerThread.interrupt();   // 데이터 수신 쓰레드 종료
            b_InputStream.close();
            b_OutputStream.close();
            b_socket.close();
        } catch(Exception e) { }

        super.onDestroy();
    }
    void sendData(String msg)
    {
        msg += mDelimiter;    // 문자열 종료 표시

        try {
            b_OutputStream.write(msg.getBytes());    // 문자열 전송
        } catch(Exception e) {
            // 문자열 전송 도중 오류가 발생한 경우.
            Toast.makeText(getBaseContext(),"send data error!",Toast.LENGTH_SHORT).show();
            finish();    //  APP 종료
        }
    }

    @Override
    public void onBackPressed()
    {
        backPressCloseHandler.onBackPressed();
    }
}


