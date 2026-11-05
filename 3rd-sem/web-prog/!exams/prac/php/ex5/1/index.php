<?php
$activities = [
    1 => [
        "name" => "sleeping",
        "difficulty" => 0.05
    ],
    2 => [
        "name" => "mining",
        "difficulty" => 0.6
    ],
    3 => [
        "name" => "family time",
        "difficulty" => 0.4
    ],
    4 => [
        "name" => "programming",
        "difficulty" => 0.95
    ],
    5 => [
        "name" => "poaching",
        "difficulty" => 0.7
    ],
    6 => [
        "name" => "hunting",
        "difficulty" => 0.6
    ],
    7 => [
        "name" => "playing",
        "difficulty" => 0.0
    ],
    8 => [
        "name" => "cooking",
        "difficulty" => 0.6
    ]
];
$goblins = [
    "WEB'LIN" => [
        [
            "startHour" => 0,
            "activityKey" => 3
        ],
        [
            "startHour" => 1,
            "activityKey" => 3
        ],
        [
            "startHour" => 3,
            "activityKey" => 5
        ],
        [
            "startHour" => 4,
            "activityKey" => 4
        ],
        [
            "startHour" => 5,
            "activityKey" => 4
        ],
        [
            "startHour" => 7,
            "activityKey" => 1
        ]
    ],
    "HUN'TER" => [
        [
            "startHour" => 0,
            "activityKey" => 1
        ],
        [
            "startHour" => 1,
            "activityKey" => 6
        ],
        [
            "startHour" => 3,
            "activityKey" => 3
        ],
        [
            "startHour" => 4,
            "activityKey" => 3
        ],
        [
            "startHour" => 5,
            "activityKey" => 6
        ],
        [
            "startHour" => 7,
            "activityKey" => 6
        ]
    ],
    "MOT'HER" => [
        [
            "startHour" => 0,
            "activityKey" => 3
        ],
        [
            "startHour" => 1,
            "activityKey" => 3
        ],
        [
            "startHour" => 3,
            "activityKey" => 6
        ],
        [
            "startHour" => 4,
            "activityKey" => 8
        ],
        [
            "startHour" => 5,
            "activityKey" => 8
        ],
        [
            "startHour" => 7,
            "activityKey" => 3
        ]
    ],
    "GOB'KID" => [
        [
            "startHour" => 0,
            "activityKey" => 7
        ],
        [
            "startHour" => 1,
            "activityKey" => 7
        ],
        [
            "startHour" => 3,
            "activityKey" => 7
        ],
        [
            "startHour" => 4,
            "activityKey" => 7
        ],
        [
            "startHour" => 5,
            "activityKey" => 7
        ],
        [
            "startHour" => 7,
            "activityKey" => 7
        ]
    ]
];

?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task 1</title>
    <style>
        table, td, th {
            border: 1px black solid;
            border-collapse: collapse;
        }
        td { text-align: center; }
    </style>
</head>

<body>
    <h1>Task 1</h1>
    <h2>Timetable</h2>
    <table>

    </table>
</body>

</html>