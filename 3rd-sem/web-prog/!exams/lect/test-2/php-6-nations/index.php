<?php
require_once "nations.php";
require_once "videos.php";
?>

<!DOCTYPE html>
<html lang="hu">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Task 6.</title>
    <link rel="stylesheet" href="index.css" />
</head>

<body>
    <h1>6. Nations</h1>
    <div id="main">
        <div id="form">
            <form>
                <select name="nation">
                    <?php foreach($nations as $nation): ?>
                        <option value="<?=$nation?>"><?=$nation?></option>
                    <?php endforeach ?>
                </select>
                <select name="id">
                    <?php foreach($videos as $video): ?>
                        <option value="<?=$video->id?>"><?=$video->title?></option>
                    <?php endforeach ?>
                </select>
                <input type="submit">
            </form>
        </div>
        <div id="videos">
            <div id="left">
                <h2>No nation</h2>
                <div>Joost Klein - Europapa</div>
                <div>Little Big - Uno</div>
            </div>
            <div id="right">
                <h2>Has nation</h2>
                <div><a href="">🚯</a> AWS - Viszlát Nyár | Hungary</div>
                <div><a href="">🚯</a> Baby Lasagna - Rim Tim Tagi Dim | Croatia</div>
            </div>
        </div>
    </div>
</body>

</html>