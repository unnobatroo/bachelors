<?php
require_once 'nations.php';
?>

<!DOCTYPE html>
<html lang="hu">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Task 5.</title>
    <link rel="stylesheet" href="index.css" />
</head>

<body>
    <h1>5. New music</h1>
    <div id="main">
        <form>
            <label>
                Title
                <input name="title">
            </label>
            <label>
                Release year
                <input name="year">
            </label>
            <label>
                Views
                <input name="views">
            </label>
            <div>
                Manual ID
                <label><input type="radio" name="manualid" value="yes"> Igen</label>
                <label><input type="radio" name="manualid" value="no"> Nem</label>
            </div>
            <label>
                ID
                <input name="id">
            </label>
            <input type="submit">
        </form>

        <div id="success">New song added!</div>
        <div id="errors">
            Error!
            <ul>
                <li>Example error.</li>
            </ul>
        </div>
    </div>
</body>

</html>