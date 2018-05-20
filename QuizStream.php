<html> <meta charset="utf-8">
	<style>																					<!-- Legt das Design fest (CSS) -->														
		table{
			width:60%;
		}
		td{
			border:1px solid;
			overflow:hidden;
			vertical-align:top;
		}
		.spieler{
			background-color: Blue; color: white;text-align: center;
		}
		head{																								
			background-color: Blue; text-align: center;
		}
		tr:first-child{																																																			
			background: blue; color: white;
		}
		tr:nth-child(2n+3){																	
			background-color: white; color: black;
		}
		.tabellehintergrund{																
			background-color: lightgrey; color: white;
		}
		.richtig{																			
			color: green;text-align: center;
		}
		.falsch{																				
			color: red; text-align: center;
		}
		.normal{																				
			text-align: center;
		}
    </style>
	<head> <title>Quiz Stream</title>
		<?php																			//PHP Teil
			$quizID = $array1[$zeile-1][0];
			$zeile3 = 0;
			$array3 = array();															//array3 = Fragen.csv							
			$lesen3 = fopen("Fragen.csv", "r");											//Einlesen der Fragen																							
			while(($csvLesen3 = fgetcsv($lesen3, 0, ";")) !== FALSE){ 																				
				$array3[$zeile3] = $csvLesen3; 																													
				$zeile3++;
			}
			
			echo "<div class=spieler><h1>Spieler1 vs Spieler2 <h1></div>";
			echo "<div class=tabellehintergrund>";
			echo "<table border=1 width= 100% align=center>";
			
			$zeile4 = 0;
			$array4 = array();																//array4 = Spieler1.csv	
			$lesen4 = fopen("Spieler1.csv", "r");											//Einlesen der Spieler1.csv, beinhaltet answerId, isCorrect und $totalPoints																								
			while(($csvLesen4 = fgetcsv($lesen4, 0, ";")) !== FALSE){ 																				
				$array4[$zeile4] = $csvLesen4; 																																			
				$zeile4++;
			}

			$zeile5 = 0;
			$array5 = array();																//array5 = Spieler2.csv	
			$lesen5 = fopen("Spieler2.csv", "r");																														
			while(($csvLesen5 = fgetcsv($lesen5, 0, ";")) !== FALSE){ 																				
				$array5[$zeile5] = $csvLesen5; 																										
				$zeile5++;
			}

			if($zeile4 == 0){																//If-Abfrage damit am Anfang die Punkte = 0 sind und nicht leer
				$punkte1="0";
			}else{
				$punkte1=$array4[$zeile4-1][2];												//Ansonsten werden die Punkte übermittelt die der Spieler1 erreicht hat
			} 
			if($zeile5 == 0){
				$punkte2="0";
			}else{
				$punkte2=$array5[$zeile5-1][2];
			}

			echo " <div class=spieler><h1>".$punkte1.":".$punkte2."</h1></div>";			//Punktestand zwischen Spieler1 und Spieler2
			
			$i=1;
			$z=0; 
			$ii=1;																
			echo "<tr class=normal> <th>Frage</th> <th>Spieler1 Antwort</th> 				
			<th>Spieler2 Antwort</th> <th>Richtig Antwort</th> </tr>";						//Tabelle 
			while($i<=264){
				$richtig = $array3[0][$i+1];										
				$rAntwort = $richtig+1+$i;
				$s1Antwort = $array4[$z][0]+1+$i;											//Setzt die Antwort von Spieler1
				$s2Antwort = $array5[$z][0]+1+$i;											//Setzt die Antwort von Spieler2
				if($zeile4 >= $ii){
					if($s1Antwort == $rAntwort){											//Vergleich der Antwort von Spieler1 mit der richtigen Antwort
						$antwort3 = $array3[0][$s1Antwort];
						$s1 = "richtig";													//Highlighted die Antwort in grün, wenn sie richtig ist
					}else{
						$s1 = "falsch";  													//Highlighted die Antwort in rot, wenn sie falsch ist
						$antwort3=$array3[0][$s1Antwort];
					}
				}else{  
					$s1 = "normal"; 														//Highlight in schwarz für "Warte auf Antwort"
					$antwort3 ="Warte auf Antwort";
				}
				if($zeile5 >= $ii){
					if($s2Antwort == $rAntwort){
						$s2 = "richtig";  													
						$antwort4=$array3[0][$s2Antwort];
					}else{
						$s2 = "falsch";  														
						$antwort4=$array3[0][$s2Antwort];
					}
				}else{
					$s2 = "normal";
					$antwort4 = "Warte auf Antwort";
				}
				if($zeile5 == "0"){
					$s2 = "normal";
					$antwort4 = "Warte auf Antwort";
				}
				if($zeile4 == "0"){
					$s1 = "normal";
					$antwort3 = "Warte auf Antwort";
				}
				echo "<tr> <th align=\"left\">".$array3[0][$i].									//Ausgabe der Frage in der Tabelle folgende Reihenfolge: Frage, Antwort Spieler1, Antwort Spieler2 sowie der Richtigen Antwort
				"</th> <th class = ".$s1.">".$antwort3."</th> <th class =  ".$s2.				//s1 und s2 sind die Highlights
				">".$antwort4."</th> <th>".$array3[0][$rAntwort]."</th> </tr>";
				$i = $i+6;
				$z++;
				$ii++;
			}
			echo "</table>";
			echo "</div>";
		?>
	</head>
	<body >
		<script language="JavaScript">										
			window.setTimeout("location.reload()",3000);								//Läd die Seite alle 3 Sekunden neu
		</script>
	</body>
</html>
