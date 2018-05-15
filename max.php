<?php
$userId = $_GET['userId'];
$name = $_GET['name'];
$func = $_GET['func'];

$questionId =  $_GET['questionId'];
$userName = $_GET['userName'];
$answerId = $_GET['answerId'];
$isCorrect = $_GET['isCorrect'];
$totalPoints = $_GET['totalPoints'];

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
    while(i<$zeile){
      $zeitstempel = time();
    $Differenz = $zeitstempel - $array[$i][2]; $i++;
    if($Differenz < 10){$count++;  } 	}

 if($count==0){
   $userName="Spieler1";
   $saveRow =$UserId.';'.$name.';'.$zeitstempel.';'.$userName."\r\n";
   $save = fopen("SpielerListe.csv", "a");
   fwrite($save, $saveRow);
   fclose($save); $aktiv="false";
   echo "Spieler1";
 }

 if($count==1){
   $userName="Spieler1";
   $saveRow =$UserId.';'.$name.';'.$zeitstempel.';'.$userName."\r\n";
   $save = fopen("SpielerListe.csv", "a");
   fwrite($save, $saveRow);
   fclose($save);
   echo "Spieler2"; $aktiv="false";
 }
 if($count==2){

   echo "error";}
// }



    																													//Wenn einzelne Werte abgefragt werden sollen



}

else{
$zeitstempel = time();
$userName="Spieler1";
$saveRow =$UserId.';'.$name.';'.$zeitstempel.';'.$userName."\r\n";
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

 ?>
