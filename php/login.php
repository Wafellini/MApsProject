<?php

require "init.php";

$u_name=$_POST["name"];
$u_password=$_POST["password"];

$sql_query="select login, password from users where login like '$u_name';";
$result = mysqli_query($connection,$sql_query);
$row=mysqli_fetch_array($result);

if(mysqli_num_rows($result)>0){
    if($u_password == $row[1]){
        echo "Success";
    } else {
        echo "Error";
    }
} else {
    echo "Error";
}

mysqli_close($connection);
?>