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
    <h1>5. Press</h1>
    <div id="main">
        <form>
            <label>
                Company name
                <input name="company">
            </label>
            <label>
                Nation
                <select name="nation">
                    <?php foreach ($nations as $nation): ?>
                        <!-- It will send through a nation code, eg. HUN -->
                        <option value="<?= $nation['id'] ?>">
                            <?= $nation['flag'] ?> <?= $nation['name'] ?>
                        </option>
                    <?php endforeach ?>
                </select>
            </label>
            <label>
                Number of reportes
                <input name="reporters">
            </label>
            <div>
                Contract
                <label><input type="radio" name="contract" value="subsidiary"> EBU Subcontractor</label>
                <label><input type="radio" name="contract" value="independent"> Independent</label>
            </div>
            <input type="submit">
        </form>

        <div id="success">Successful registration!</div>
        <div id="errors">
            Error!
            <ul>
                <li>Example error.</li>
            </ul>
        </div>
    </div>



    <hr>

    <div>
        If you are using GET method through a checks.php file, you can test the values that are inconvenient to input by hand via these links:
        <ul><a href="check.php?nation=XXX">Non existent country (check.php?nation=XXX)</a></ul>
        <ul><a href="check.php?nation=DEU&reporters=25">Too many reporters for Big5 (check.php?nation=DEU&reporters=25)</a></ul>
        <ul><a href="check.php?contract=taxevasion">Incorrect contract (check.php?contract=taxevasion)</a></ul>
        <ul><a href="check.php?nation=DEU&contract=independent">Incorrect Big5 contract (check.php?nation=DEU&contract=independent)</a></ul>
    </div>
    <div>
        If you are using GET method through this same index.php file, you can test the values that are inconvenient to input by hand via these links:
        <ul><a href="index.php?nation=XXX">Non existent country (index.php?nation=XXX)</a></ul>
        <ul><a href="index.php?nation=DEU&reporters=25">Too many reporters for Big5 (index.php?nation=DEU&reporters=25)</a></ul>
        <ul><a href="index.php?contract=taxevasion">Incorrect contract (index.php?contract=taxevasion)</a></ul>
        <ul><a href="index.php?nation=DEU&contract=independent">Incorrect Big5 contract (index.php?nation=DEU&contract=independent)</a></ul>
    </div>
</body>

</html>