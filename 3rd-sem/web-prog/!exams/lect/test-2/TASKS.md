# Exam

## Information

### Starter package
You can download it from this link: [https://webprogramozas.inf.elte.hu:8081/attachments/113](https://webprogramozas.inf.elte.hu:8081/attachments/113)

### Until when can I write it?
- The midterm exam can be written **until 19:30**, which **includes** not only solving the tasks but also: downloading the initial files; filling out the `README` file (which is **mandatory** – without it, the solution is **incomplete**); compressing and uploading the final solution. **No additional time** will be granted afterward.
- Tasks must be submitted via the [Exam System](https://webprogramozas.inf.elte.hu/zh). The system closes exactly at 19:30. In the **last 10 minutes** of the exam, focus **only on compressing and uploading** your work! A failed last-minute upload will result in a failing grade just the same.

### What can I use?
- The materials you pre-uploaded to the [Exam System](https://webprogramozas.inf.elte.hu/zh).
- [JavaScript documentation](https://developer.mozilla.org/en-US/).
- [PHP documentation](https://www.php.net/).
- The [lecture slides](https://webprogramozas.inf.elte.hu/webprog/lectures-pdf-en/).

### What can’t I use?
Among others:
- Human assistance (synchronous, asynchronous, chat, forum, etc.), except for asking help from supervising teachers.
- Artificial intelligence (ChatGPT, Bing AI, GitHub Copilot, AI-based code completion/extensions in VS Code, etc.). **❗ Not knowing the features of your installed extensions does not exempt you from consequences**.
If you are unsure whether something is allowed, **ask us instead**.

You must confirm your acceptance of all the above in the `README` file included in the starter package.

### What else should I know?
- The tasks (1, 2, 3…) are **independent** and can be solved **in any order**. Subtasks (a, b, c...) within a task may sometimes build on each other, or an earlier subtask might help in solving a later one – however, it's worth reading through all parts: even if you’re stuck on *a* or *b*, you might figure out *c-e*. Also, if you're stuck on a subtask, **don't spend an hour on it** – move on! The first few subtasks of the next task might be worth more points than the one you're stuck on.
- To begin, download the framework we’ve provided. Each task is in a separate folder. In each folder, we’ve prepared the HTML, CSS, JavaScript, and PHP files. Work in these! For client-side tasks, you'll usually only need to edit the `.js` file, but if necessary, you can modify the HTML as well, or even split your solution into multiple `.js` files – though this is **not required**.
- In the downloaded framework’s `README` file, enter your Neptun code and name! **We will not evaluate any solution that does not have a properly completed `README` file!**
- In each task folder, you'll find a `TASKS.md` file. Replace the space between `[ ]` with an `x` for each subtask you have (even partially) completed! This helps us know what to look at during evaluation.
- Be sure to install PHP on your machine: [https://webprogramozas.inf.elte.hu:8081/attachments/106](https://webprogramozas.inf.elte.hu:8081/attachments/106)

### Data
❗ In this exam, all tasks will work with the same data so you don’t have to understand different schemas repeatedly. You can find the data in various formats in the `data` folder. You can use any of them for any task, feel free to copy the preferred file into your task folder or copy its contents directly into your code. You may add any helper attributes to the data if needed.

Each item represents data about concert videos (live performance recordings):
- `id`: A unique identifier consisting only of lowercase English letters and digits.
- `yt`: YouTube ID pointing to the live performance. May contain uppercase/lowercase letters, digits, dashes, underscores (and may start with any of those).
- `title`: The video title in the format `Artist Name - Song Title`. Contains exactly one dash (`-`) separating the artist and the song title.
- `year`: The year of the performance (i.e. when the song was featured in Eurovision).
- `views`: The number of views (in millions) on the official Eurovision YouTube channel. Not necessarily a whole number.
- `nation`: Only relevant in the final PHP task. Indicates the country the artist represented with this song.

## Lore
After Eurovision contests, performances can be rewatched online. Since there aren’t enough video/music sharing platforms, we’re making our own.

## JavaScript tasks

### 1. Video data (js-1-videodata, 10 points)
First, we want to know some information about our favorite Eurovision songs. **Do not hardcode the answers**, solve them programmatically since more songs might catch our interest later!

- a. (1 point) Into `taskA` element: Write the title of a video from before the year 2000!
    - You can assume such a video exists.
- b. (2 points) Into `taskB` element: List all videos that have more than 100 million views!
- c. (2 points) Into `taskC` element: Write how many video titles contain the word `Love` (with capital `L` and lowercase `ove`)!
- d. (3 points) Into `taskD` element: Write the **average** number of views of videos from 2024! Round the result to two decimal places!
    - You can assume there is at least one such video.
    - Partial points for the unrounded value.
- e. (2 points) Into `taskE` element: Write whether there is any song title (not full video title!) that contains a digit!
    - The check should be for the part **after the dash** in the title.
    - The result can be `Yes / No`, `true / false`, `0 / 1` or something similarly logical.
    - Don’t get stuck here — move on if you’re stuck.

![JavaScript task1](https://webprogramozas.inf.elte.hu:8081/attachments/107)

### 2. Videos (js-2-videolist, 15 points)
Since we don’t yet know how to fetch current YouTube view counts, we’ll have to update them manually.

- a. (2 points) List the videos into the `#video-table` following the pattern (year, title, views in millions)!
- b. (2 points) Allow selecting table rows (`tr`).
    - When a row is clicked, apply the `selected` class to it!
    - If already selected, remove the class on click!
- c. (1 point) Display the total number of views of all videos in the `#sum` element.
- d. (2 points) If any video is selected, `#sum` should only show the sum of selected videos’ views.
    - If none are selected, show the overall sum!
- e. (4 points) Buttons in the `#controls` section should work accordingly: `#btn-add` should increase the views of all selected rows by the value in `#amount`; `#btn-sub` should decrease it, but never below zero.
    - Partial: update the first two rows instead of selection.
    - Partial: use a fixed increment/decrement of 1 instead of input field.
    - If nothing is selected, do nothing!
    - Keep `#sum` up to date!
- f. (4 points) Clicking header cells should sort the table in descending order based on the clicked column!
    - You don’t need to preserve selection after sorting.
    - Partial: sort by views descending at load without interactivity.
    - Sorting alphabetically thos way will result in a W to A order. That is fine, don’t complicate this further.
    - No need to implement toggling sort direction on repeated clicks.

![JavaScript task2](https://webprogramozas.inf.elte.hu:8081/attachments/108)

### 3. Popularity (js-3-popularity 11 points)
It’s worth visualizing the popularity of videos using a chart, since these displays tend to be popular.

*You don’t need to write the internal logic — the base code handles that. Your task is to complete the `drawColumn` and `drawCircleSector` functions to render the data.*

```JS
/**
 * Draws a column on the chart.
 * @param video Video object with attributes like id, views.
 * @param index The index of the column.
 * @param columnWidth The width of each column.
 */
function drawColumn(video, index, columnWidth)
```

```JS
/**
 * Draws a sector (pie slice) on the chart.
 * @param video Video object with attributes like id, views.
 * @param totalViews Total combined view count of all videos.
 * @param centerX X coordinate of the circle center.
 * @param centerY Y coordinate of the circle center.
 * @param radius Radius of the circle.
 */
function drawCircleSector(video, totalViews, centerX, centerY, radius)
```

- a. (3 points) In the `drawColumn` function, draw a column for the video.
    - Use `fillRect`.
    - The top-left of the rect is `x: index × columnWidth`, `y: 400 - views`.
    - Width is `columnWidth`, height is views.
- b. (1 point) The rectangle color should be the first 6 characters of the video `id`.
    - Use `substring`.
    - Add a `#` to the beginning to form a valid hex color.
- c. (2 points) Display the view count above the column.
    - Suggested styles: `fillStyle: black`, `font: 12px Arial`, `textAlign: center`
    - Position: `x: index × columnWidth + columnWidth/2`, `y: 400 - views - 5`
- d. (3 points) In the `drawCircleSector` function, draw the slice (like a pie slice).
    - Outline starts at the center (`centerX`, `centerY`).
    - Then draw an arc from start angle to start angle + slice angle.
    - Finally, close the path back to the center.
- e. (1 point) The pie slice color should also be the first 6 chars of `id`.
    - Use `substring`.
    - Add a `#` to the beginning.

![JavaScript task3](https://webprogramozas.inf.elte.hu:8081/attachments/109)

## PHP tasks
Do not use JavaScript in the PHP tasks.

### 4. Video data, now with PHP (php-4-videodata, 10 points)

- a. (2 points) Render one `a` element with the `card` class into `#main` per video. Links should go to `https://www.youtube.com/watch?v=abc123` where `abc123` is the video’s `yt` value. Links must open in a new tab.
- b. (1 point) Inside each card, place an `h2` with the video title.
- c. (1 point) Below the link, place a `span` with class `year` for the year; and a `span` with class `views` for the views and the word "million".
- d. (2 points) Above the link, place an image from the `img` folder, named after the video’s `id` and with `.jpg` extension.
- e. (2 points) Apply extra style classes based on views:
    - 100M+ → `very-popular` (adds `⭐` around the title)
    - 10M–100M → `slightly-popular`
    - Under 10M → `partially-popular`
- f. (2 points) Sort the cards by video title (alphabetically).
    - *Use `usort` with `strcmp` for example.*

![PHP task4](https://webprogramozas.inf.elte.hu:8081/attachments/110)

### 5. Add a new song (php-5-newmusic, 14 points)

*Validate the form on the server side after submission! (No built-in HTML/JS validation allowed.)* **Full points only if error messages are displayed; otherwise only half points.** *Using the `#errors` div is optional; you can show errors directly after inputs too. Don’t display errors on first page load. It’s smart to implement state + errors first!*

- a. (2 points) All fields are required.
- b. (1 point) Title must be at least 5 characters long.
- c. (1 point) Title must contain a "space dash space" trio (` - `).
- d. (1 point) Year must be an integer ≥ `1956`, the first Eurovision year.
    - Filter text and floats.
- e. (1 point) Views must be a number ≥ `0.01`.
    - Filter text inputs.
- f. (1 point) Manual ID value must be either `yes` or `no`.
- g. (1 point) If manual ID is `yes`, ID is required.
- h. (1 point) ID must not already exist.
    - Use any data source file you want.
- i. (2 points) ID must only contain digits and lowercase `a–f` (hex).
- j. (2 points) The form should retain previous values.
- k. (1 point) Only show `success` div if form is valid; otherwise show `errors`.

Suggested error messages (not mandatory):
```
Title is required!
Title must be at least 5 characters!
Title must contain a dash with spaces around it! ( - )
Year must be an integer!
Earliest possible year is 1956!
Views must be a number!
Views must be at least 0.01 million (10,000)!
Unknown "Manual ID" value!
Manual ID requires an ID to be entered!
ID already exists!
ID must only contain digits and lowercase "a"–"f".
```

### 6. Countries (php-6-nations, 12 points)
Oops, we forgot to include the nation info. We’ll make an interface to fix that.

*You can use one of the provided JSON files or create your own. The dropdown form uses `videos.php` initially, but you can modify or replace it.*

- a. (3 points) List videos **without** a nation on the left; with one on the right.
- b. (2 points) Add a new video-nation link via form.
    - The form submits the nation via `nation` and video ID via `id`.
    - Once added, the video should move to the right list.
- c. (3 points) Allow removing a nation assignment.
    - Once removed, the video should move to the left list.
- d. (2 points) Videos with an assigned nation should not appear in the form dropdown.
    - You’ll need to modify the form code for this.
- e. (2 points) Videos in both columns and the dropdown should be sorted by title.
    - *Use `usort` to help you.*

![PHP rask6](https://webprogramozas.inf.elte.hu:8081/attachments/111)
