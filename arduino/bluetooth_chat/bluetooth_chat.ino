#include <SoftwareSerial.h>

  SoftwareSerial BTSerial(4,5); //softwareSerial (RX,TX)
  byte buffer[1024];  //데이터를 수신받을 버퍼
  int bufferPosition; //버퍼에 데이터를 저장할때 기록할 위치
  
void setup() {
  // put your setup code here, to run once:
  BTSerial.begin(9600);
  Serial.begin(9600);
  Serial.println("ATcommand");
  bufferPosition =0 ; //버퍼위치 초기화
}

void loop() {
  // put your main code here, to run repeatedly:
  if (BTSerial.available()){ //블루투스로 데이터 수신
    byte data =BTSerial.read(); //수신받은 데이터 저장
    Serial.write(data);  //수신된 데이터 시리얼 모니터로 출력
    
    buffer[bufferPosition++] = data;  //수신받은 데이터를 버퍼에 저장
    
    if(data=='1')
    {
      Serial.write("1 success");
    }
    else if(data=='2')
    {
      Serial.write("2 success");
    }
     else if(data=='3')
    {
      Serial.write("3 success");
    }
     else if(data=='4')
    {
      Serial.write("4 success");
    }
     else if(data=='5')
    {
      Serial.write("5 success");
    }
    
    if(data=='\n'){ //문자열 종료 표시
      //buffer[bufferPosition] = '$';

      //스마트폰으로 문자열 전송
      BTSerial.write(buffer,bufferPosition);
      
      bufferPosition = 0;
      BTSerial.write('$');
    }
   
  }
  
  
}
