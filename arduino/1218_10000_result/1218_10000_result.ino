#include <SoftwareSerial.h>
//소주:맥주 3:7(황금비율) 2:8 1:9 1:1 1:0 0:1 2:1
  //bluetooth
  SoftwareSerial BTSerial(12,13); //softwareSerial (RX,TX) bluetooth pin
  byte buffer[1];  //데이터를 수신받을 버퍼
  int bufferPosition; //버퍼에 데이터를 저장할때 기록할 위치

  boolean isFirst;
  float first_soju_volume;
  float first_macju_volume;
  
  //motor  soju
  int soju_enablePin = 3;   //pmw pin
  int soju_in1Pin = 4;
  int soju_in2Pin = 2;
  int soju_nSwitch = 0;
  boolean soju_bReverse = true;
  int soju_nSpeed = 0;
  
  //motor  macju
  int macju_enablePin = 6;  //pwm pin
  int macju_in1Pin = 5;
  int macju_in2Pin = 7;
  int macju_nSwitch = 0;
  boolean macju_bReverse = true;
  int macju_nSpeed = 0;
  
  //distance sensor soju
  int soju_trigPin = 8;  //trigpin output
  int soju_echoPin = 9;  //echopin input
  float soju_duration, soju_distance;  //초음파 시간과, 거리 

  //distance sensor macju
 // int macju_trigPin = 10;  //trigpin output
  //int macju_echoPin = 11;  //echopin input
  //float macju_duration, macju_distance;  //초음파 시간과, 거리 

//sound 
int speakerPin = 9;
  
void setup() {
  // put your setup code here, to run once:
  //bluetooth settting 
  BTSerial.begin(9600);
  Serial.begin(9600);
  Serial.println("ATcommand");
  bufferPosition =0 ; //버퍼위치 초기화

  //motor setting
  pinMode(soju_enablePin, OUTPUT);
  pinMode(soju_in1Pin, OUTPUT);
  pinMode(soju_in2Pin, OUTPUT);
  pinMode(macju_enablePin, OUTPUT);
  pinMode(macju_in1Pin, OUTPUT);
  pinMode(macju_in2Pin, OUTPUT);
  

  //sensor_distance setting
   pinMode(soju_trigPin, OUTPUT);
   pinMode(soju_echoPin, INPUT);
//   pinMode(macju_trigPin, OUTPUT);
 //  pinMode(macju_echoPin, INPUT);

  isFirst = true;
  
//sound pin
pinMode(speakerPin,OUTPUT);
}


void loop() {
  // put your main code here, to run repeatedly:
  
  
  if (BTSerial.available()){ //블루투스로 데이터 수신
    byte data =BTSerial.read(); //수신받은 데이터 저장
    Serial.write(data);  //수신된 데이터 시리얼 모니터로 출력
    
    buffer[bufferPosition++] = data;  //수신받은 데이터를 버퍼에 저장

    if(data=='1')  //3:7황금 비율
    {
      Serial.write("1 success\n");
                
       soju_nSpeed=255;
      macju_nSpeed=255;
      soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
      delay(4000);
      macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on
      delay(3000);
      soju_nSpeed=0;
      macju_nSpeed=0;
      soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
      macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on
    }
    else if(data=='2') //2:8 
    {
      Serial.write("2success\n");

     soju_nSpeed=255;
      macju_nSpeed=255;
      soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
      delay(6000);
      macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on
      delay(2000);
      soju_nSpeed=0;
      macju_nSpeed=0;
      soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
      macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on
        
    }
     else if(data=='3') //1:9
    {
      Serial.write("3 success");

       soju_nSpeed=255;
      macju_nSpeed=255;
      soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
      delay(8000);
      macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on
      delay(1000);
      soju_nSpeed=0;
      macju_nSpeed=0;
      soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
      macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on
    }
     else if(data=='4') //1:1
    {
      Serial.write("4 success");

      soju_nSpeed=255;
      macju_nSpeed=255;
      soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
      macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on
      delay(5000);
      soju_nSpeed=0;
      macju_nSpeed=0;
      soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
      macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on

      
    }
     else if(data=='5')// 1:0
    {
      Serial.write("5 success");

       soju_nSpeed=255;
      macju_nSpeed=255;
   //   soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
   
      macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on
      delay(10000);
      soju_nSpeed=0;
      macju_nSpeed=0;
      soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
      macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on
    }
     else if(data=='6')// 0:1
    {
      Serial.write("6 success");

       soju_nSpeed=255;
      macju_nSpeed=255;
      soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
      delay(10000);
      //macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on
     
      soju_nSpeed=0;
      macju_nSpeed=0;
      soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
      macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on
    }
       else if(data=='7')// 2:1
    {
     soju_nSpeed=255;
      macju_nSpeed=255;
      macju_setMotor(macju_nSpeed,macju_bReverse);
        //soju motor on
      delay(3333);
      soju_setMotor(soju_nSpeed,soju_bReverse);
            //macju motor on
      delay(3333);
      soju_nSpeed=0;
      macju_nSpeed=0;
      soju_setMotor(soju_nSpeed,soju_bReverse);  //soju motor on
      macju_setMotor(macju_nSpeed,macju_bReverse); //macju motor on
    }
    if(data=='\n'){ //문자열 종료 표시
      //buffer[bufferPosition] = '$';

      //스마트폰으로 문자열 전송
      BTSerial.write(buffer,bufferPosition);

      bufferPosition = 0;
      BTSerial.write('$');
      tone(speakerPin,500,1000);
    }
   
  }
}

void soju_get_distance()
{
      digitalWrite(soju_trigPin, HIGH);
      delay(10);
      digitalWrite(soju_trigPin, LOW);

      // echoPin 이 HIGH를 유지한 시간을 저장 한다.
      soju_duration = pulseIn(soju_echoPin, HIGH);
      // HIGH 였을 때 시간(초음파가 보냈다가 다시 들어온 시간)을 가지고 거리를 계산 한다.
      // 340은 초당 초음파(소리)의 속도, 10000은 밀리세컨드를 세컨드로, 왕복거리이므로 2로 나눠준다.
      soju_distance = ((float)(340 * soju_duration) / 10000) / 2;
}
//void macju_get_distance()
//{
//      digitalWrite(macju_trigPin, HIGH);
//      delay(10);
//      digitalWrite(macju_trigPin, LOW);

      // echoPin 이 HIGH를 유지한 시간을 저장 한다.
   //   macju_duration = pulseIn(macju_echoPin, HIGH);
      // HIGH 였을 때 시간(초음파가 보냈다가 다시 들어온 시간)을 가지고 거리를 계산 한다.
      // 340은 초당 초음파(소리)의 속도, 10000은 밀리세컨드를 세컨드로, 왕복거리이므로 2로 나눠준다.
      //macju_distance = ((float)(340 * macju_duration) / 10000) / 2;
//}

void print_distace()
{
      Serial.print("soju_Duration: ");
      Serial.print(soju_duration);
      Serial.print("   ");
      Serial.print("macju_Duration: ");
//      Serial.print(macju_duration);
      
      Serial.print("\nsoju_DIstance: ");
      Serial.print(soju_distance);
      Serial.print("cm");
      Serial.print("   ");
      Serial.print("macju_DIstance: ");
//      Serial.print(macju_distance);
      Serial.println("cm\n ");
}
void soju_setMotor(int soju_nSpeed, boolean soju_bReverse)
{
  analogWrite(soju_enablePin, soju_nSpeed);
  digitalWrite(soju_in1Pin, ! soju_bReverse);
  digitalWrite(soju_in2Pin, soju_bReverse);
}
void macju_setMotor(int macju_nSpeed, boolean macju_bReverse)
{
  analogWrite(macju_enablePin, macju_nSpeed);
  digitalWrite(macju_in1Pin, ! macju_bReverse);
  digitalWrite(macju_in2Pin, macju_bReverse);
}
void first_distance()
{
if(isFirst)
  {
//     macju_get_distance();  //초음파로 거리 측정
     soju_get_distance();  

     first_soju_volume = soju_distance;
//     first_macju_volume = macju_distance;

    Serial.print("soju :");
    Serial.print(first_soju_volume);
    Serial.print(" macju : ");
    Serial.println(first_macju_volume);

    //isFirst= false;
  }


}


