<html>
<head> <meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
<body>
<?php

error_reporting(E_ALL); 
ini_set('display_errors',1); 

$link=mysqli_connect("localhost","root","root","exam"); 
if (!$link)  
{ 
   echo "MySQL 접속 에러 : ";
   echo mysqli_connect_error();
   exit();
}  


mysqli_set_charset($link,"utf8");  

//POST 값을 읽어온다.
$soju = isset($_POST['soju'])? $_POST['soju']:'';
$macju = isset($_POST['macju']) ? $_POST['macju'] :'';
$time=isset($_POST['time']) ? $_POST['time'] : '';  

if ($soju !="" and $macju!=""){   
    $sql="insert into alcohol(soju,macju,time) values('$soju','$macju',now())"; 
    $result=mysqli_query($link,$sql);  
    mysqli_query($link,"ALTER TABLE alcohol AUTO_INCREMENT=1");
    mysqli_query($link,'SET @CNT=0');
    mysqli_query($link,'UPDATE Person SET alcohol.num = @CNT:=@CNT+1');
    if($result){
       echo "success, ";  
    }  
    else{  
       echo "fail, "; 
       echo mysqli_error($link);
    } 
}
else 
{
echo "fail, ";
echo mysqli_error($link);
}


mysqli_close($link);
?>
</body>
</html>
<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
   <body>
   
      <form action="<?php $_PHP_SELF ?>" method="POST">
         soju: <input type = "text" name = "soju" />
         macju: <input type = "text" name = "macju" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}
?>
