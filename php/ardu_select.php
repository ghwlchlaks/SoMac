<html> 
<head> <meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
<body>

<?php

$link =mysqli_connect('localhost','root','root','exam');

if(!$link)
{
	echo "MYSQL connect error :";
	echo mysqli_connect_error();
	exit();
}

mysqli_set_charset($link,"utf8");
#$email = isset($_POST['email'])? $_POST['email']:'';
#$email_sql = "select id from Person where address='$email'";
#$email_result =mysqli_query($link,$email_sql);
#$count = mysqli_num_rows($email_result);

$day_sql = "select * from alcohol where date(time) = date(now())";
$day_result = mysqli_query($link,$day_sql);
$count = mysqli_num_rows($day_result);

if($count==0)
{
	echo "fail, ";
	echo mysqli_error($link);
}
else 
{
	$day_soju = 0;
	$day_macju = 0;

	echo "success,";
	while($row = mysqli_fetch_row($day_result))
	{	
		$day_soju += $row[2];
		$day_macju+= $row[3];
	}
	echo $day_soju.",".$day_macju.",".$count;
}
mysqli_close($link);
?>

</body>
</html>
<?php

#$android =strpos($_SERVER['HTTP_USER_AGENT'],"Android");

#if (!$android)
{
?>

<html>
<body>

	<form action="<?php $_PHP_SELF ?>" method="POST">
		Email : <input type = "text" name = "email" />
	<input type="submit"/>
	</form>
</body>
</html>
<?php
}
?>
