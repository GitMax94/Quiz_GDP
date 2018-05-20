<?php

$func = $_GET['func'];																			//Einlesen der Variable
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

function add_user($userName){																	//Erstellt die Csv für Spieler1 und Spieler2 bzw. löscht sie falls schon vorhanden von der vorherigen Nutzung																	
	if(file_exists("Spieler1.csv")){															
		if($userName == "Spieler1"){	
			unlink("Spieler1.csv");}															//Falls Spieler1.csv vorhanden ist wird sie gelöscht
		}
	if(file_exists("Spieler2.csv")){
		if($userName == "Spieler2"){
			unlink("Spieler2.csv");
		}
	}
	$saveRow = " ";																				
	$save = fopen("Spieler1.csv", "a");															//Erstellung der Spieler1.csv 
	fwrite($save, $saveRow);
	fclose($save);
	$saveRow = " ";
	$save = fopen("Spieler2.csv", "a");
	fwrite($save, $saveRow);
	fclose($save);
}

function answer($userName,$answerId,$isCorrect,$totalPoints){									//Speichert die Fragen ID, korrekte Antwort (true/false) und die gesamten Punkte von Spieler1 und Spieler2
	if($userName == "Spieler1"){
		$saveRow = $answerId.';'.$isCorrect.';'.$totalPoints."\r\n";							
		$save = fopen("Spieler1.csv", "a");
		fwrite($save, $saveRow);																//Schreibt die ID der Frage, ob die Frage korrekt war und die Punkte in die Spieler1.csv
		fclose($save);
		$zeile2 = 0;
		$array2 = array();
		$lesen2 = fopen("Spieler2.csv", "r");													//Einlesen der Spieler2.csv 
		while(($csvLesen2 = fgetcsv($lesen2, 1000, ";")) !== FALSE){
			$array2[$zeile2] = $csvLesen2;														//Doppel-Array: [Zeile][0=answerId][1=isCorrect][2=totalPoints]
			$zeile2++;
		}
	$points=$array2[$zeile2-1][2];																//Speichern der Punkte 

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

	}
	echo $points;																				//Ausgabe der Punkte 
}

function heartbeat($userName){																	//Übergabe der gegner Punkte und Frage
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

function FragenSenden(){																		//Einlesen der Fragen und senden an die App
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
