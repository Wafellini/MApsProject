<?php

require "init.php";

$sql_query1="select login, score from scores";

$res = mysqli_query($connection,$sql_query1);
$response = "";

if(mysqli_num_rows($res)>0){
    while ($row = mysqli_fetch_array($res)) {
        $response = $response . $row[0] . ',' . $row[1] . ';';
    }
}

echo $response;

mysqli_close($connection);
?>