<?php

require "init.php";

$login=$_POST["login"];
$score=(int)$_POST["score"];


$sql_query1="select score from users where login like '$login';";

$res = mysqli_query($connection,$sql_query1);
$row = mysqli_fetch_array($res);

if(!$res){
    if($score > $row[0]){
        $sql_query2="UPDATE scores
                        SET score = '$score'
                    WHERE login like '$login';";
    }
} else {
    $sql_query2="insert into scores values('$login','$score');";
}

if(mysqli_query($connection,$sql_query2)){
    echo "Data inserted";
}
else{
    echo "Insertion error";
}
mysqli_close($connection);

?>