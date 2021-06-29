<?php

require "init.php";

$login=$_POST["login"];

$sql_query1="select score from scores where login like '$login';";

$res = mysqli_query($connection,$sql_query1);
$row = mysqli_fetch_array($res);

if(mysqli_num_rows($res)>0){
    echo $row[0];
} else {
    echo "None";
}

mysqli_close($connection);

?>