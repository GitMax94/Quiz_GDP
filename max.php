<?php
$userId = $_GET['userId'];
$name = $_GET['name'];
$func = $_GET['func'];

$questionId =  $_GET['questionId'];
$userName = $_GET['userName'];
$answerId = $_GET['answerId'];
$isCorrect = $_GET['isCorrect'];
$totalPoints = $_GET['totalPoints'];

// test.php

$spieler1 = $_GET['Spieler1'];
$spieler2 = $_GET['Spieler2'];

$quizID = $_GET['quizID'];

$pointsSpieler1 = $_GET['$pointsSpieler1'];
$pointsSpieler2 = $_GET['$pointsSpieler2'];

$question = $_GET['question'];

// Start app:		func = "add_user"	userId = uuid	name = "Alec"

if(isset($func)){

  if ($func=="add_user")
  {

	add_user($userId,$name);
  }

  if ($func=="answer")
  {
    answer($userName,$answerId,$isCorrect,$totalPoints);
  }
}



//Test.php
if(isset($spieler1)){
	choose_quiz($quizID);
}

if(isset($spieler1)){
	if(file_exists("spieler1.csv")){
		$delete = "spieler1.csv";
		unlink($delete);
	}
	$spieler1CSV = fopen("spieler1.csv", "a");
	$write = $userId.";".$pointsSpieler1.";".$question.";".$spieler1;
	fwrite($spieler1CSV, $write);
	fclose($spieler1CSV);
}

if(isset($spieler2)){
	if(file_exists("spieler2.csv")){
		$delete = "spieler2.csv";
		unlink($delete);
	}
	$spieler2CSV = fopen("spieler2.csv", "a");
	$write = $userId.";".$pointsSpieler2.";".$question.";".$spieler2;
	fwrite($spieler2CSV, $write);
	fclose($spieler2CSV);
}


if(isset($spieler1)){
	heartbeat($spieler1, $spieler2);
}elseif($spieler2){
	heartbeat($spieler1, $spieler2);
}







function add_user($userId,$name)
{

    // noch nicht fertig

    //einlesen welche zeilen aktuell sind und Spiele Nummern

    if(file_exists("SpielerListe.csv")){// prueft ob datei da ist

  		$zeile = 0;
  		$array = array();
  		$lesen = fopen("SpielerListe.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und pr�ft diese auf Semikolon-Separierte-Werte (CSV)
  		while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
  			$array[$zeile] = $csvLesen; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
  			$zeile++;
  			}
  	// return $array;

    $i=0; $count=0;
    while($i<$zeile){
      $zeitstempel = time();
    $Differenz = $zeitstempel - $array[$i][2]; $i++;
    if($Differenz < 10){$count++;  } 	}

 if($count==0){
   $userName="Spieler1";
   $saveRow =$userId.';'.$name.';'.$zeitstempel.';'.$userName."\r\n";
   $save = fopen("SpielerListe.csv", "a");
   fwrite($save, $saveRow);
   fclose($save);
   echo "Spieler1".';'."null";
 }

 if($count==1){
   $userName="Spieler2";
   $saveRow =$userId.';'.$name.';'.$zeitstempel.';'.$userName."\r\n";
   $save = fopen("SpielerListe.csv", "a");
   fwrite($save, $saveRow);
   fclose($save);

   
   if(file_exists("quizID.csv")){// prueft ob datei da ist
		$zeile = 0; 
		$array1 = array();
		$lesen = fopen("quizID.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und prüft diese auf Semikolon-Separierte-Werte (CSV)
		while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
			$array1[$zeile] = $csvLesen; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
			$zeile++;
			}
    $quizID=$array1[$zeile-1][0];
   echo "Spieler2".';'.$quizID;}
 }
 if($count==2){
   echo "error";}
}

else{
$zeitstempel = time();
$userName="Spieler1";
$saveRow =$userId.';'.$name.';'.$zeitstempel.';'.$userName."\r\n";
$save = fopen("SpielerListe.csv", "a");
fwrite($save, $saveRow);
fclose($save);
echo "Spieler1";}
}

function answer($userName,$answerId,$isCorrect,$totalPoints){
  if($userName=="Spieler1"){
  $saveRow =$answerId.';'.$isCorrect.';'.$totalPoints."\r\n";
  $save = fopen("Spieler1.csv", "a");
  fwrite($save, $saveRow);
  fclose($save);}

  else{
    $saveRow =$answerId.';'.$isCorrect.';'.$totalPoints."\r\n";
    $save = fopen("Spieler2.csv", "a");
    fwrite($save, $saveRow);
    fclose($save);}

}
//Test.php


function choose_quiz($quizID){
	if(file_exists("quizID.csv")){
		$delete = "quizID.csv";
		unlink($delete);
	}else{
		$quizCsv = fopen("quizID.csv", "a");
		fwrite($quizCsv, $quizID);
		fclose($quizCsv);
	}
	return $quizCsv;
}

function heartbeat($spieler1, $spieler2){
	if($spieler1){
		if(file_exists("spieler2.csv")){
			$isOnline = "true";
			$zeile = 0;
			$lesen = fopen("spieler2.csv", "r");
			while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){
			$array[$zeile] = $csvLesen;
			$zeile++;
		}
	}else {
		$isOnline = "false";
	}
	echo $isOnline.";".$array[0][3].";".$array[0][2].";".$array[0][1];
	if(file_exists("spieler2.csv")){
		$delete = "spieler2.csv";
		unlink($delete);
	}
	}elseif($spieler2) {
		if(file_exists("spieler1.csv")){
			$isOnline = "true";
			$zeile = 0;
			$lesen = fopen("spieler1.csv", "r");
			while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){
				$array[$zeile] = $csvLesen;
				$zeile++;
			}
		}else{
			$isOnline = "false";
		}
		echo $isOnline.";".$array[0][3].";".$array[0][2].";".$array[0][1];
		if(file_exists("spieler1.csv")){
			$delete = "spieler1.csv";
			unlink($delete);
		}
	}
}


 ?>
