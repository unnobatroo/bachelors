<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Reuse advertisments</title>
  <link rel="stylesheet" href="index.css">
</head>
<body>
  <h1>Reuse advertisments</h1>
  <form action="?" method="post">
    <fieldset>
      <legend><strong>NEW AD</strong></legend>
      Title: <br>
      <input type="text" name="title" required> <br>
      Description: <br>
      <textarea name="description"cols="30" rows="3" required></textarea> <br>
      Contact email: <br>
      <input type="email" name="email" required> <br>
      Image URL: <br>
      <input type="url" name="url"> <br>
      <button type="submit">Add</button>
    </fieldset>
  </form>
  <h2>Active ads</h2>
  <form action="" method="get">
    <fieldset>
      <legend>Filter</legend>
      Filter text: <br>
      <input type="text" name="filter-text"> <br>
      <button type="submit">Search</button>
    </fieldset>
  </form>
  <div id="ads">
    <div class="item">
      <img src="https://www.prospectpizzabk.com/wp-content/uploads/2020/11/Placeholder-1.png">
      <div>
        <h3>Title</h3>
        <p>Description</p>
        <p><small>2022.01.14. 16:23:34</small></p>
      </div>
    </div>
  </div>
</body>
</html>