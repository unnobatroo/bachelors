<?php
$machines = ["Budapest-Nyugati", "Budapest-Keleti", "Budapest-Déli", "Budapest-Kelenföld", "Debrecen", "Szeged", "Miskolc", "Pécs", "Győr", "Nyíregyháza", "Kecskemét", "Székesfehérvár", "Szombathely", "Szolnok", "Cegléd", "Békéscsaba"];
?>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="index.css">
  <title>CO₂ Emission</title>
</head>

<body>
  <h1>CO<sub>2</sub> Emission</h1>
  <form action="index.php" method="get" novalidate>
    <label for="i1">Station of departure:</label> <input type="text" name="journey_from" id="i1"> <br>
    <label for="i2">Destination station:</label> <input type="text" name="journey_to" id="i2"> <br>
    <label for="i3">Ticket price (HUF):</label> <input type="text" name="price" id="i3"> <br>
    <label for="i4">Distance (km):</label> <input type="text" name="distance" id="i4"> <br>
    <input type="checkbox" name="paid" id="i5"><label for="i5">Ticket paid</label> <br>
    <button type="submit">Submit</button>
  </form>

  <div class="big">Your CO<sub>2</sub> reduction is <span style="color: green">42.1</span> kg!</div>
  
  <h2>Test cases</h2>
  <a href="index.php?journey_from=&journey_to=&price=&distance=">index.php?journey_from=&journey_to=&price=&distance=</a><br>
  <a href="index.php?journey_from=Budafok&journey_to=&price=&distance=">index.php?journey_from=Budafok&journey_to=&price=&distance=</a><br>
  <a href="index.php?journey_from=Debrecen&journey_to=&price=&distance=">index.php?journey_from=Debrecen&journey_to=&price=&distance=</a><br>
  <a href="index.php?journey_from=Debrecen&journey_to=Eger&price=&distance=">index.php?journey_from=Debrecen&journey_to=Eger&price=&distance=</a><br>
  <a href="index.php?journey_from=Debrecen&journey_to=Eger&price=-3000&distance=">index.php?journey_from=Debrecen&journey_to=Eger&price=-200&distance=</a><br>
  <a href="index.php?journey_from=Debrecen&journey_to=Eger&price=3289.99&distance=">index.php?journey_from=Debrecen&journey_to=Eger&price=3289.99&distance=</a><br>
  <a href="index.php?journey_from=Debrecen&journey_to=Eger&price=3290&distance=">index.php?journey_from=Debrecen&journey_to=Eger&price=3290&distance=</a><br>
  <a href="index.php?journey_from=Debrecen&journey_to=Eger&price=3290&distance=long">index.php?journey_from=Debrecen&journey_to=Eger&price=3290&distance=long</a><br>
  <a href="index.php?journey_from=Debrecen&journey_to=Eger&price=3290&distance=-99.5">index.php?journey_from=Debrecen&journey_to=Eger&price=3290&distance=-99.5</a><br>
  <a href="index.php?journey_from=Debrecen&journey_to=Eger&price=3290&distance=210.8">index.php?journey_from=Debrecen&journey_to=Eger&price=3290&distance=210.8</a><br>
  <a href="index.php?journey_from=Debrecen&journey_to=Eger&price=3290&distance=210.8&paid=on">index.php?journey_from=Debrecen&journey_to=Eger&price=3290&distance=210.8&paid=on</a><br>
</body>

</html>