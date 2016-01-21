
<?php
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


$sql = "SELECT DISTINCT place FROM details ORDER BY place" ;
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
   print( json_encode($emparray) );



$conn->close();
?>
