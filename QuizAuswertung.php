<?php

$Nutzer_ID = $_GET['Nutzer_ID'];
$Name = $_GET['Name'];
$Zeitstempel = time();
$play= "false";	

$anwendung  = $_GET["anwendung"];

if($anwendung == "F"){
	$array = FragenEinlesen();
	FragenSenden($array);
}

if($anwendung == "S"){
	PlayerOnline();
}

if(isset($Nutzer_ID)){
	save($Nutzer_ID,$Name,$Zeitstempel,$play);	
}

function save($Nutzer_ID,$Name,$Zeitstempel,$play){
	$saveUserOnlineRow = $Nutzer_ID.';'.$Name.';'.$Zeitstempel.';'.$play."\r\n";	
	$saveUserOnline = fopen("Spieler.csv", "a");																							//In Anlehnung an Folien-HTML-und-PHP-komplett Von Prof.Dr. Brell S.15
	fwrite($saveUserOnline, $saveUserOnlineRow);
	fclose($saveUserOnline); 
}

function PlayerOnline(){																															//https://www.php-kurs.com/arrays-mehrdimensional.htm
	if(file_exists("Spieler.csv")){// prueft ob datei da ist
		$zeile = 0; 
		$array = array();
		$lesen = fopen("Spieler.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und prüft diese auf Semikolon-Separierte-Werte (CSV)
		while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
			$array[$zeile] = $csvLesen; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
			$zeile++;
			}
			$i=0;
			$LaengeArray = count($array); 
			while($i<$array){
				$Zeitstempel2 = time();
			$Differenz = $Zeitstempel2 - $array[$i][2]; 
					if($Differenz < 5){
					if($array[$i][3]=="false"){
						echo $array[$i][1];
						}
			}$i++;}																											
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
			
		
																																//Wenn einzelne Werte abgefragt werden sollen
	}																						//Distanz = 0, Index = 1
echo $text;
}

function FragenEinlesen(){																															//https://www.php-kurs.com/arrays-mehrdimensional.htm
	if(file_exists("Fragen.csv")){// prueft ob datei da ist
		$zeile = 0; 
		$array = array();
		$lesen = fopen("Fragen.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und prüft diese auf Semikolon-Separierte-Werte (CSV)
		while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
			$array[$zeile] = $csvLesen; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
			$zeile++;
			}
	return $array; 																																//Wenn einzelne Werte abgefragt werden sollen
	}
}


function SpielEinlesen(){																															//https://www.php-kurs.com/arrays-mehrdimensional.htm
	if(file_exists("Fragen.csv")){// prueft ob datei da ist
		$zeile = 0; 
		$array = array();
		$lesen = fopen("Fragen.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und prüft diese auf Semikolon-Separierte-Werte (CSV)
		while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
			$array[$zeile] = $csvLesen; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
			$zeile++;
			}
	return $array; 																																//Wenn einzelne Werte abgefragt werden sollen
	}
}



	/*
	if($Laengengrad != "0.0" && $Breitengrad != "0.0" && $Laengengrad != "" && $Breitengrad != ""){													//Prüft ob GPS Daten vorhanden sind
	$DateiSchreiben = fopen("Standortdaten.csv", "a");																							//In Anlehnung an Folien-HTML-und-PHP-komplett Von Prof.Dr. Brell S.15
	fwrite($DateiSchreiben, $MeetMeCsvTextZeile);
	fclose($DateiSchreiben); 
	} 		
	
	
	$NutzerSchreiben = fopen("Nutzer.csv", "a");																							//In Anlehnung an Folien-HTML-und-PHP-komplett Von Prof.Dr. Brell S.15
	fwrite($NutzerSchreiben, $NutzerTextZeile);
	fclose($NutzerSchreiben); */
	
	//umstruckturien 
	function DatenSenden($CsvArray, $arrayPersonenSortiert, $Breitengrad, $Laengengrad){
	$CsvArray = $CsvArray; 																														//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
	$arrayPersonenSortiert = $arrayPersonenSortiert; 																							//Distanz = 0, Index = 1
	$max = count($arrayPersonenSortiert);																										//Prüft die laenge des Arrays
	$i = 0;
	echo "test";
	while($i < $max){																															//Nutzer_ID, Name, Längengrad, Breitengrad,Entfernung, Entfernung laengengrad, Entfernung breitengrad
		echo $CsvArray[$arrayPersonenSortiert[$i][1]][0].';'
		.$CsvArray[$arrayPersonenSortiert[$i][1]][1].';'.$CsvArray[$arrayPersonenSortiert[$i][1]][3]
		.';'.$CsvArray[$arrayPersonenSortiert[$i][1]][4].';'.$arrayPersonenSortiert[$i][0]
		.';'.EntfernungBerechnenMeter($Laengengrad,$CsvArray[$arrayPersonenSortiert[$i][1]][3],$Breitengrad, $Breitengrad)					
		.';'.EntfernungBerechnenMeter($Laengengrad, $Laengengrad, $Breitengrad, $CsvArray[$arrayPersonenSortiert[$i][1]][4])."\r\n";		
		$i++;	
	}
}
?>