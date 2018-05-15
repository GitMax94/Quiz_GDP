<?php

$spieler1 = $_GET['Spieler1'];
$spieler2 = $_GET['Spieler2'];

$quizID = $_GET['quizID'];
$userID = $_GET['userID'];

$pointsSpieler1 = $_GET['$pointsSpieler1'];
$pointsSpieler2 = $_GET['$pointsSpieler2'];

$question = $_GET['question'];

if(isset($spieler1)){
	choose_quiz($quizID);
}

if(isset($spieler1)){
	if(file_exists("spieler1.csv")){
		$delete = "spieler1.csv";
		unlink($delete);
	}
	$spieler1CSV = fopen("spieler1.csv", "a");
	$write = $userID.";".$pointsSpieler1.";".$question.";".$spieler1;
	fwrite($spieler1CSV, $write);
	fclose($spieler1CSV);
}

if(isset($spieler2)){
	if(file_exists("spieler2.csv")){
		$delete = "spieler2.csv";
		unlink($delete);
	}
	$spieler2CSV = fopen("spieler2.csv", "a");
	$write = $userID.";".$pointsSpieler2.";".$question.";".$spieler2;
	fwrite($spieler2CSV, $write);
	fclose($spieler2CSV);
}


if(isset($spieler1)){
	heartbeat($spieler1, $spieler2);
}elseif($spieler2){
	heartbeat($spieler1, $spieler2);
}


function choose_quiz($quizID){
	if(file_exists("quizID.csv")){
		$delete = "quizID.csv";
		unlink($delete);
	}else{
		$quizCsv = fopen("quizID.csv", "a");
		fwrite($quizCsv, $quizID);
		fclose($quizCsv);
	}
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
