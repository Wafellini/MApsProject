<?php

require "init.php";

$u_name=$_POST["name"];
$u_password=$_POST["password"];

$sql_query="insert into users(login,password) values('$u_name','$u_password');";

if(mysqli_query($connection,$sql_query)){
    echo "Success";
}
else{
    echo "Error";
}
mysqli_close($connection);
?>