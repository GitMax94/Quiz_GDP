<?php

$Nutzer_ID = $_GET['Nutzer_ID'];
$spielId = $_GET['spielId'];


$Name = $_GET['Name'];
$Zeitstempel = time();
$play= "false";	

$nutzerEingabe = $_GET["nutzerEingabe"];
$nutzerFrage = $_GET["nutzerFrage"];
$Quiz_ID = $_GET["Quiz_ID"];

$anwendung  = $_GET["anwendung"];

if($anwendung == "F"){
	$array = FragenEinlesen();
	FragenSenden($array);
}

if($anwendung == "S"){
	PlayerOnline();
}

//punkte($Nutzer_ID, $Name, $Zeitstempel, $nutzerEingabe, $nutzerFrage, $Quiz_ID);

function punkte($Nutzer_ID, $Name, $Zeitstempel, $nutzerEingabe, $nutzerFrage, $Quiz_ID){
	if(file_exists("Fragen.csv")){
		$zeile = 0; 
		$array = array();
		$lesen = fopen("Fragen.csv", "r");																								
		while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){ 																				
			$array[$zeile] = $csvLesen; 																										
			$zeile++;
		}
		$a=0;
		while($a<$zeile){
			if($Quiz_ID == $array[$a][0]){
				$b=1;
				while($nutzerFrage != $array[$a][$b]){
					$c=3;
					while($nutzerEingabe != $array[$a][$c]){
						$punkte = $Nutzer_ID.';'.$Name.';'.$Zeitstempel.';'.$play."\r\n";	
						$punkteCSV = fopen("Punkte.csv", "a");																							
						fwrite($punkteCSV, $punkte);
						fclose($punkteCSV);
					}
					$c++;
				}
				$b+=6;
			}
			$a++;
		}
	}	
}

if(isset($Nutzer_ID)){
	save($Nutzer_ID,$Name,$Zeitstempel,$play);	
}

if(isset($spielId)){
	echo $spielId;
}

function save($Nutzer_ID,$Name,$Zeitstempel,$play){
	$saveUserOnlineRow = $Nutzer_ID.';'.$Name.';'.$Zeitstempel.';'.$play."\r\n";	
	$saveUserOnline = fopen("Spieler.csv", "a");																							
	fwrite($saveUserOnline, $saveUserOnlineRow);
	fclose($saveUserOnline); 
}

function PlayerOnline(){																															
	if(file_exists("Spieler.csv")){
		$zeile = 0; 
		$array = array();
		$lesen = fopen("Spieler.csv", "r");																								
		while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){ 																				
			$array[$zeile] = $csvLesen; 																										
			$zeile++;
		}
		$i=0;
		$LaengeArray = count($array); 
		while($i<$LaengeArray){
			$Zeitstempel2 = time();
			$Differenz = $Zeitstempel2 - $array[$i][2]; 
			if($Differenz < 10){
				if($array[$i][3]=="false"){
					echo $array[$i][0].";".$array[$i][1];
				}
			}
		$i++;}																											
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

function SpielEinlesen(){																															
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