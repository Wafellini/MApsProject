<?php

$db_name="mapp";
$mysql_user="jurek";
$pass="jurek2021";
$server_name="localhost";

$connection= mysqli_connect($server_name,$mysql_user,$pass,$db_name);

if(!$connection){
    //echo "Connection error";
}
else{
    //echo "Connection success";
}

?>