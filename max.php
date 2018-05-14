<?php
$Nutzer_ID = $_GET['Nutzer_ID'];
$Name = $_GET['Name'];
$func = $_GET['func'];

$userName = $_GET['userName'];
$answerId = $_GET['answerId'];
$isCorrect = $_GET['isCorrect'];
$totalPoints = $_GET['totalPoints'];

// Start app:		func = "add_user"	userId = uuid	name = "Alec"

if(isset($func)){

  if ($func=="add_user")
  {
	add_user($Nutzer_ID,$Name);
  }

  if ($func=="answer")
  {
    answer($UserName,$answerId,$isCorrect,totalPoints);
  }



}


function add_user($userId,$questionId,$answerId,$isCorrect,$totalPoints)
{
  $Zeitstempel = time();
  // noch nicht fertig
}

function answer($userName,$answerId,$isCorrect,$totalPoints){
  if($UserName=="Spieler1"){
  $saveRow =$answerId.';'.$isCorrect.';'.$totalPoints."\r\n";
  $save = fopen("Spieler1.csv", "a");
  fwrite($save, $saveRow);
  fclose($save);}

  else{
    $saveRow =$answerId.';'.$isCorrect.';'.$totalPoints."\r\n";
    $save = fopen(Spiler2.csv, "a");
    fwrite($save, $saveRow);
    fclose($save);}
  }
}

 ?>
