<?php

$func = $_GET['func'];
$userName = $_GET['userName'];
$answerId = $_GET['answerId'];
$isCorrect = $_GET['isCorrect'];
$totalPoints = $_GET['totalPoints'];

if(isset($func)){
	if ($func == "add_user"){
		add_user($userName);
	}
	if ($func == "answer"){
		answer($userName,$answerId,$isCorrect,$totalPoints);
	}
	if($func == "heartbeat"){
		heartbeat($userName);
	}
	if($func == "get_quiz"){
		FragenSenden();
	}
}

function add_user($userName){
	if(file_exists("Spieler1.csv")){
		if($userName == "Spieler1"){
			unlink("Spieler1.csv");}
		}
	if(file_exists("Spieler2.csv")){
		if($userName == "Spieler2"){
			unlink("Spieler2.csv");
		}
	}
	$saveRow = " ";
	$save = fopen("Spieler1.csv", "a");
	fwrite($save, $saveRow);
	fclose($save);
	$saveRow = " ";
	$save = fopen("Spieler2.csv", "a");
	fwrite($save, $saveRow);
	fclose($save);
	$zeitstempel = time();
}

function answer($userName,$answerId,$isCorrect,$totalPoints){
	if($userName == "Spieler1"){
		$saveRow = $answerId.';'.$isCorrect.';'.$totalPoints."\r\n";
		$save = fopen("Spieler1.csv", "a");
		fwrite($save, $saveRow);
		fclose($save);
		$zeile2 = 0;
		$array2 = array();
		$lesen2 = fopen("Spieler2.csv", "r");
		while(($csvLesen2 = fgetcsv($lesen2, 1000, ";")) !== FALSE){
			$array2[$zeile2] = $csvLesen2;
			$zeile2++;
		}
	$points=$array2[$zeile2-1][2];
	$questions=$zeile2;
	}elseif($userName == "Spieler2"){
		$saveRow = $answerId.';'.$isCorrect.';'.$totalPoints."\r\n";
		$save = fopen("Spieler2.csv", "a");
		fwrite($save, $saveRow);
		fclose($save);
		$zeile2 = 0;
		$array2 = array();
		$lesen2 = fopen("Spieler1.csv", "r");
		while(($csvLesen2 = fgetcsv($lesen2, 1000, ";")) !== FALSE){
			$array2[$zeile2] = $csvLesen2;
			$zeile2++;
		}
		$points = $array2[$zeile2-1][2];
		$questions = $zeile2;
	}
	$points.';'.$questions;
}

function heartbeat($userName){
	if($userName == "Spieler1"){
		$zeile2 = 0;
		$array2 = array();
		$lesen2 = fopen("Spieler2.csv", "r");
		while(($csvLesen2 = fgetcsv($lesen2, 1000, ";")) !== FALSE){
			$array2[$zeile2] = $csvLesen2;
			$zeile2++;
		}
	$points=$array2[$zeile2-1][2];
	$questions=$zeile2;
	}else{
		$zeile2 = 0;
		$array2 = array();
		$lesen2 = fopen("Spieler1.csv", "r");
		while(($csvLesen2 = fgetcsv($lesen2, 1000, ";")) !== FALSE){
			$array2[$zeile2] = $csvLesen2;
			$zeile2++;
		}
		$points = $array2[$zeile2-1][2];
		$questions = $zeile2;
	}
	echo $points.';'.$questions;
}

function FragenSenden(){
	if(file_exists("Fragen.csv")){
		$zeile = 0;
		$array = array();
		$lesen = fopen("Fragen.csv", "r");
		while(($csvLesen = fgetcsv($lesen, 100000, ";")) !== FALSE){
			$LaengeArray = count($csvLesen);
			$i=0;
			while($i<$LaengeArray){
				if($LaengeArray-1 == $i){
					echo $csvLesen[$i]; 
					$i++;
				}else{
					echo $csvLesen[$i].';'; 
					$i++;
				}
			}
		}
	}
	echo $text;
}

?>
