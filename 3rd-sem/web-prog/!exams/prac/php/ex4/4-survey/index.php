<?php
$questions = [
  1 => "How many kilograms of communal waste do you produce in a week?",
  2 => "How many kilograms of recyclable paper waste do you produce in a week?",
  3 => "How many kilograms of recyclable plastic waste do you produce in a week?",
  4 => "How many kilograms of recyclable glass waste do you produce in a week?",
  5 => "How many kilograms of recyclable metal waste do you produce in a week?",
];
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Survey on recycle</title>
  <link rel="stylesheet" href="index.css">
</head>
<body>
  <h1>Survey</h1>
  <h2>Page 3</h2>
  <p><strong>Question:</strong> The actual question here</p>
  <form action="" method="post">
    <input type="number" name="answer" required>
    <button type="submit">Save</button>
  </form>
  <h3>Navigation</h3>
  <ul>
    <li><a href="?page=2">Previous page</a></li>
    <li><a href="?page=4">Next page</a></li>
    <li><a href="eval.php">Results</a></li>
  </ul>

  <h2>Test cases:</h2>
  <ul>
    <li><a href="index.php">index.php</a></li>
    <li><a href="index.php?page=1">index.php?page=1</a></li>
    <li><a href="index.php?page=2">index.php?page=2</a></li>
    <li><a href="index.php?page=3">index.php?page=3</a></li>
    <li><a href="index.php?page=4">index.php?page=4</a></li>
    <li><a href="index.php?page=5">index.php?page=5</a></li>
    <li><a href="eval.php">eval.php</a></li>
  </ul>
</body>
</html>