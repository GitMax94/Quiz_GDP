<?php
$userId = $_GET['userId'];
$name = $_GET['name'];
$func = $_GET['func'];
$userName = $_GET['userName'];
$answerId = $_GET['answerId'];
$isCorrect = $_GET['isCorrect'];
$totalPoints = $_GET['totalPoints'];
$userName =  $_GET['userName'];
$quizID = $_GET['quizID'];

if(isset($func)){
	if ($func=="add_user"){
		add_user($userName,$userId,$name);
	}
	if ($func=="answer"){
		answer($userName,$answerId,$isCorrect,$totalPoints);
	}
	if ($func=="ende"){
		ende($userId, $userName);
	}
	if($func=="heartbeat"){
		heartbeat($userName);
	}
	if($func=="choose_quiz"){
		choose_quiz($quizID);
	}
	if($func== "get_quizzes"){
		$array = FragenEinlesen();
		FragenSenden($array);
	}
}

function add_user($userName,$userId,$name){
	if(file_exists("Spieler1.csv")){
		if($userName=="Spieler1"){
			unlink("Spieler1.csv");}
		}
	if(file_exists("Spieler2.csv")){
		if($userName=="Spieler2"){
			unlink("Spieler2.csv");
		}
	}
	$saveRow ="";
	$save = fopen("Spieler1.csv", "a");
	fwrite($save, $saveRow);
	fclose($save);
	$saveRow ="";
	$save = fopen("Spieler2.csv", "a");
	fwrite($save, $saveRow);
	fclose($save);
	if(file_exists("SpielerListe.csv")){
		$zeitstempel = time();
		if($userName=="Spieler1"){
			$userName="Spieler1";
			$saveRow =$userId.';'.$name.';'.$zeitstempel.';'.$userName."\r\n";
			$save = fopen("SpielerListe.csv", "a");
			fwrite($save, $saveRow);
			fclose($save);
			echo "Spieler1";
		}
		if($userName=="Spieler2"){
			$userName="Spieler2";
			$saveRow =$userId.';'.$name.';'.$zeitstempel.';'.$userName."\r\n";
			$save = fopen("SpielerListe.csv", "a");
			fwrite($save, $saveRow);
			fclose($save);
			if(file_exists("quizID.csv")){
				$zeile = 0;
				$array1 = array();
				$lesen = fopen("quizID.csv", "r");																								
				while(($csvLesen = fgetcsv($lesen, 1000000, ";")) !== FALSE){ 																				
					$array1[$zeile] = $csvLesen; 																										
					$zeile++;
				}
				$quizID=$array1[$zeile-1][0];
				echo "Spieler2";
			}
		}
		if($count==2){
			echo "error";
		}
	}else{
		$zeitstempel = time();
		$userName="Spieler1";
		$saveRow =$userId.';'.$name.';'.$zeitstempel.';'.$userName."\r\n";
		$save = fopen("SpielerListe.csv", "a");
		fwrite($save, $saveRow);
		fclose($save);
		echo "Spieler1";
	}
}

function answer($userName,$answerId,$isCorrect,$totalPoints){
	if($userName=="Spieler1"){
		$saveRow =$answerId.';'.$isCorrect.';'.$totalPoints."\r\n";
		$save = fopen("Spieler1.csv", "a");
		fwrite($save, $saveRow);
		fclose($save);
	}elseif($userName=="Spieler2"){
		$saveRow =$answerId.';'.$isCorrect.';'.$totalPoints."\r\n";
		$save = fopen("Spieler2.csv", "a");
		fwrite($save, $saveRow);
		fclose($save);
	}
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
	return $quizCsv;
}

function heartbeat($userName){
	if($userName=="Spieler1"){
		$zeile2 = 0;
		$array2 = array();
		$lesen2= fopen("Spieler2.csv", "r");																								
		while(($csvLesen2 = fgetcsv($lesen2, 1000, ";")) !== FALSE){ 																				
			$array2[$zeile2] = $csvLesen2; 																										
			$zeile2++;
		}		
	$points=$array2[$zeile2-1][2];
	$questions=$zeile2;
	}else{
		$zeile2 = 0;
		$array2 = array();
		$lesen2= fopen("Spieler1.csv", "r");																								
		while(($csvLesen2 = fgetcsv($lesen2, 1000, ";")) !== FALSE){ 																				
			$array2[$zeile2] = $csvLesen2; 																										
			$zeile2++;
		}
		$points=$array2[$zeile2-1][2];
		$questions=$zeile2;
	}
	echo $points.';'.$questions;
}

function FragenSenden($array){
	if(file_exists("Fragen.csv")){
		$zeile = 0;
		$array = array();
		$lesen = fopen("Fragen.csv", "r");
		while(($csvLesen = fgetcsv($lesen, 100000, ";")) !== FALSE){
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
		while(($csvLesen = fgetcsv($lesen, 1000000, ";")) !== FALSE){
			$array[$zeile] = $csvLesen;
			$zeile++;
		}
		return $array;
	}
}

?>
