<html> <meta charset="utf-8">
<style>

      table {
        width:100%;
      }
      td {
        border:1px solid;
        overflow:hidden;
        vertical-align:top;
      }
	/* body{background-color: lightblue} */
  .spieler
  {
    background-color: Blue; color: white;text-align: center;
  }
  head
  {
    background-color: Blue; text-align: center;
  }
  tr:first-child
  {
    background: blue; color: white;
  }
  tr:nth-child(2n+3)
  {
    background-color: white; color: black;
  }
.tabellehintergrund
{

  background-color: lightgrey; color: white;
}

.richtig
{
  color: green;text-align: center;
}
.falsch
{
 color: red; text-align: center;
}

.normal
{
  text-align: center;
}
    </style>


<head> <title>Watch</title>

<?php

if(file_exists("quizID.csv")){// prueft ob datei da ist
 $zeile = 0;
 $array1 = array();
 $lesen = fopen("quizID.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und prüft diese auf Semikolon-Separierte-Werte (CSV)
 while(($csvLesen = fgetcsv($lesen, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
   $array1[$zeile] = $csvLesen; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
   $zeile++;
   }
 $quizID=$array1[$zeile-1][0];

$zeile3 = 0;
$array3 = array();
$lesen3 = fopen("Fragen.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und prüft diese auf Semikolon-Separierte-Werte (CSV)
while(($csvLesen3 = fgetcsv($lesen3, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
$array3[$zeile3] = $csvLesen3; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
$zeile3++;
}

echo "<h1>Es wurde Folgendes Quiz ausgewählt: ".$array3[$quizID-1][0]."<h1>";}

 $zeile2 = 0;
 $array2 = array();
 $lesen2 = fopen("SpielerListe.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und prüft diese auf Semikolon-Separierte-Werte (CSV)
 while(($csvLesen2 = fgetcsv($lesen2, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
   $array2[$zeile2] = $csvLesen2; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
   $zeile2++;
   }
echo"  <div class=spieler><h1>Quiz Spiel Stream ".$array2[$zeile2-2][1]." vs ".$array2[$zeile2-1][1]."<h1></div>";

echo "<div class=tabellehintergrund>";
echo "<table border=1 width= 100% align=center>";


$zeile4 = 0;
$array4 = array();
$lesen4 = fopen("Spieler1.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und prüft diese auf Semikolon-Separierte-Werte (CSV)
while(($csvLesen4 = fgetcsv($lesen4, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
$array4[$zeile4] = $csvLesen4; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
$zeile4++;
}

$zeile5 = 0;
$array5 = array();
$lesen5 = fopen("Spieler2.csv", "r");																								//fgetcsv: Liest eine Zeile von der Position des Dateizeigers und prüft diese auf Semikolon-Separierte-Werte (CSV)
while(($csvLesen5 = fgetcsv($lesen5, 1000, ";")) !== FALSE){ 																				//Datei die gelesen wird(Standortdaten.csv), max. Zeichen (1000), Trennzeichen (;)
$array5[$zeile5] = $csvLesen5; 																										//Doppel Array, [Zeile][0=Nutzer_ID, 1=Name, 2=Zeit, 3=Laengengrad, 4=Breitengrad, 5=Aktualisierungsintervall] NUR NR.
$zeile5++;
}

$i=1;
$z=0;
echo "<tr class=normal> <th>Frage</th> <th>Spieler1 Antwort</th> <th>Spieler2 Antwort</th> <th>Richtig Antwort</th> </tr>";
while($i<=270){
$richtig = $array3[$quizID-1][$i+1];
$rAntwort = $richtig+1+$i;

$s1Antwort = $array4[$z][0]+1+$i;
$s2Antwort = $array5[$z][0]+1+$i;
if($s1Antwort==$rAntwort){
$s1 = "richtig";
}else{
$s1 = "falsch";
}

if($s2Antwort==$rAntwort){
$s2 = "richtig";
}else{
$s2 = "falsch";
}
echo "<tr class=normal> <th>".$array3[$quizID-1][$i]."</th> <th class = ".$s1.">".$array3[$quizID-1][$s1Antwort]."</th> <th class =  ".$s2.">".$array3[$quizID-1][$s2Antwort]."</th> <th>".$array3[$quizID-1][$rAntwort]."</th> </tr>";
$i= $i+6;
$z++;
}

echo "</table>";
echo "</div>";

echo  "<p align=center>".$array2[$zeile2-2][1]." vs ".$array2[$zeile2-1][1]."</p>";
echo "<p align=center>".$array4[$zeile4-1][2].":".$array5[$zeile5-1][2]."</p>";
?>
<br> </br>
</head>
<body >
  <div align="center"><button  onclick="window.location.href='http://cbrell.de/bwi50207/181/op995204/QuizStream.php'">Aktualisiern</button></div>
<colgroup>
  <!-- tabelen Weite -->
<col width="37%">
<col width="21%">
<col width="21%">
<col width="21%">
</colgroup>


<img src="F.png"> <img src="F.png" align="center">
</body>
</html>
