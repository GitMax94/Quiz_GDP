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
  if ($func=="ende")
  {
    ende($userId, $userName);
  }
  if($func=="heartbeat"){
	heartbeat($userId); 
  }
  if($func=="choose_quiz"){
	choose_quiz($quizID); 
  }
  
  if($func== "get_quizzes"){
	$array = FragenEinlesen();
	FragenSenden($array);
}
}

function ende($userId, $userName){
	unlink("SpielerListe.csv");
}

function add_user($userId,$name){
	if(file_exists("Spieler1.csv")){
		unlink("Spieler1.csv");
	}
	if(file_exists("Spieler2.csv")){
		unlink("Spieler2.csv");
	}
  $saveRow ="";
  $save = fopen("Spieler1.csv", "a");
  fwrite($save, $saveRow);
  fclose($save);

    $saveRow ="";
    $save = fopen("Spieler2.csv", "a");
    fwrite($save, $saveRow);
    fclose($save);
	
	
	
    if(file_exists("SpielerListe.csv")){// prueft ob datei da ist

  		$zeile = 0;
  		$array = array();
  		$lesen = fopen("SpielerListe.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und pr�ft diese auf Semikolon-Separierte-Werte (CSV)
  		while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
  			$array[$zeile] = $csvLesen; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
  			$zeile++;
  			}

    $i=0; $count=0;
    while($i<$zeile){
      $zeitstempel = time();
    $Differenz = $zeitstempel - $array[$i][2]; $i++;
    if($Differenz < 10){
		$count++;  
		} 	
		}

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

  elseif($userName=="Spieler2"){
    $saveRow =$answerId.';'.$isCorrect.';'.$totalPoints."\r\n";
    $save = fopen("Spieler2.csv", "a");
    fwrite($save, $saveRow);
    fclose($save);
	}
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

function heartbeat($userId){
	$saveRow = $userId."\r\n";
	$save = fopen("SpielerListe.csv", "a");
	fwrite($save, $saveRow);
	fclose($save);

	$zeile = 0;
	$array = array();
	$lesen = fopen("SpielerListe.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und pr�ft diese auf Semikolon-Separierte-Werte (CSV)
	while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
		$array[$zeile] = $csvLesen; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
		$zeile++;
	}

	$i=0; $count=0;
	while($i<$zeile){
		$zeitstempel = time();
		$Differenz = $zeitstempel - $array[$i][2]; $i++;
		if($Differenz < 10){
			$count++;  
		} 	
	}

	if($count==2){
		if($userId==$array[0][0]){
			$opponentName=$array[1][1];
			$zeile2 = 0;
			$array2 = array();
			$lesen2= fopen("Spieler2.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und pr�ft diese auf Semikolon-Separierte-Werte (CSV)
			while(($csvLesen2 = fgetcsv($lesen2, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
				$array2[$zeile2] = $csvLesen2; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
				$zeile2++;
			}
		$points=$array2[$zeile2-1][2];
		$questions=$zeile2-1;
		}
		else{
			$opponentName=$array[0][1];
			$zeile2 = 0;
			$array2 = array();
			$lesen2= fopen("Spieler1.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und pr�ft diese auf Semikolon-Separierte-Werte (CSV)
			while(($csvLesen2 = fgetcsv($lesen2, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
				$array2[$zeile2] = $csvLesen2; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
				$zeile2++;
			}
		$points=$array2[$zeile2-1][2];
		$questions=$zeile2-1;
		}
		echo 'true'.';'.$opponentName.';'.$points.';'.question;
	}
	else{
		echo 'false';
	}
}
function FragenSenden($array){
	if(file_exists("Fragen.csv")){
		$zeile = 0;
		$array = array();
		$lesen = fopen("Fragen.csv", "r");
		while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){
			$LaengeArray = count($csvLesen);
			$i=0;
			while($i<$LaengeArray){
				if($LaengeArray-1 == $i){
					echo $csvLesen[$i].'<br>'; $i++;
				 }else{
					echo $csvLesen[$i].';'; $i++;
				}
			}
		}
	}
	echo $text;
}
function FragenEinlesen(){
	if(file_exists("Fragen.csv")){
		$zeile = 0;
		$array = array();
		$lesen = fopen("Fragen.csv", "r");
		while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){
			$array[$zeile] = $csvLesen;
			$zeile++;
		}
	return $array;
	}
}

 ?>
