<?php
session_start();
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wanderer";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$place = $_GET["place"];
$lat = $_GET["lat"];
$lon = $_GET["lon"];

if($lat!=0.0){



$sql = "INSERT INTO details (place, lat, lon)
VALUES ($place, $lat, $lon)";

if ($conn->query($sql) === TRUE) {
	$sql = "SELECT id, place, lat, lon FROM details";
$result = $conn->query($sql);
$emparray = array();
if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
    	$emparray[] = $row;
        

    }
} else {
    echo "0 results";
}
   print( json_encode($emparray));

    
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
}
else
{
	echo "Error: " . $sql . "<br>" . $conn->error;
}
$conn->close();
?>