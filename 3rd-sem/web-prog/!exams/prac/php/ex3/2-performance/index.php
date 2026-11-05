<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="index.css">
    <title>Performance</title>
</head>
<body>
    <h1>Performance</h1>
    <form action="index.php" method="get" novalidate>
        <label for="i1">Number of children:</label> <input type="text" name="num_children" id="i1"> <br>
        <label for="i2">Children's names:</label> <input type="text" name="children_names" id="i2"> <br>
        <label for="i3">URL of music to be played:</label> <input type="text" name="music_url" id="i3"> <br>
        <label for="i4">Performance type:</label> <input type="text" name="performance_type" id="i4"> <br>
        <input type="checkbox" name="ready" id="i5"><label for="i5">Everyone is ready</label> <br>
        <button type="submit">Submit</button>
    </form>

    <div class="merry">🎄 MERRY CHRISTMAS AND HAPPY NEW YEAR! 🎄</div>

    <h2>Test cases</h2>
        <a href="index.php?num_children=&children_names=&music_url=&performance_type=">num_children=&children_names=&music_url=&performance_type=</a><br>
        <a href="index.php?num_children=n&children_names=&music_url=&performance_type=">num_children=n&children_names=&music_url=&performance_type=</a><br>
        <a href="index.php?num_children=6.7&children_names=&music_url=&performance_type=">num_children=6.7&children_names=&music_url=&performance_type=</a><br>
        <a href="index.php?num_children=0&children_names=&music_url=&performance_type=">num_children=0&children_names=&music_url=&performance_type=</a><br>
        <a href="index.php?num_children=3&children_names=Adam%2CBarbara&music_url=&performance_type=">num_children=3&children_names=Adam%2CBarbara&music_url=&performance_type=</a><br>
        <a href="index.php?num_children=3&children_names=Adam%2CBarbara%2CChloe&music_url=&performance_type=">num_children=3&children_names=Adam%2CBarbara%2CChloe&music_url=&performance_type=</a><br>
        <a href="index.php?num_children=3&children_names=Adam%2CBarbara%2CChloe&music_url=5c3ezwen&performance_type=">num_children=3&children_names=Adam%2CBarbara%2CChloe&music_url=5c3ezwen&performance_type=</a><br>
        <a href="index.php?num_children=3&children_names=Adam%2CBarbara%2CChloe&music_url=http%3A%2F%2Ftinyurl.com%2F5c3ezwen&performance_type=">num_children=3&children_names=Adam%2CBarbara%2CChloe&music_url=http%3A%2F%2Ftinyurl.com%2F5c3ezwen&performance_type=</a><br>
        <a href="index.php?num_children=3&children_names=Adam%2CBarbara%2CChloe&music_url=http%3A%2F%2Ftinyurl.com%2F5c3ezwen&performance_type=good">num_children=3&children_names=Adam%2CBarbara%2CChloe&music_url=http%3A%2F%2Ftinyurl.com%2F5c3ezwen&performance_type=good</a><br>
        <a href="index.php?num_children=3&children_names=Adam%2CBarbara%2CChloe&music_url=http%3A%2F%2Ftinyurl.com%2F5c3ezwen&performance_type=song">num_children=3&children_names=Adam%2CBarbara%2CChloe&music_url=http%3A%2F%2Ftinyurl.com%2F5c3ezwen&performance_type=song</a><br>
        <a href="index.php?num_children=3&children_names=Adam%2CBarbara%2CChloe&music_url=http%3A%2F%2Ftinyurl.com%2F5c3ezwen&performance_type=song&ready=on">num_children=3&children_names=Adam%2CBarbara%2CChloe&music_url=http%3A%2F%2Ftinyurl.com%2F5c3ezwen&performance_type=song&ready=on</a><br>
</body>
</html>
