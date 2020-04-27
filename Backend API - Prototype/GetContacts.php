<?php
/**
if(isset($_POST['PostData'])){
	$PostData = $_POST['PostData'];
	$jsonArray = json_decode($PostData,true);
	$subject_id = $jsonArray['subjectid'];
	$user_id = $jsonArray['userid'];
}
*/

$subject_id ="1";
$user_id = "2";

//server connection 
define('DB_USER', "app_user"); // db user with select and insert privileges
define('DB_PASSWORD', "pIbSD4Z5W9ra5Y3y");
define('DB_DATABASE', "applicationdata");
define('DB_SERVER', "localhost"); 
$con = mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD,DB_DATABASE) or die(mysql_error());
if (mysqli_connect_errno()){
	echo "Connection failed".die(mysqli_connect_error());
}

$response["contact_related_data"] = array();

//sql request to DB
$sql_contacts = "SELECT users.id, users.first_name,users.last_name,users.mobile,users.email,users.phone_number,relationships.relationship_description FROM users INNER JOIN relationships ON users.id = relationships.user_id WHERE relationships.subject_id =".$subject_id." AND relationships.user_id<>".$user_id.";";     
//$sql_contacts = "SELECT users.id, users.first_name,users.last_name,users.mobile,users.email,users.phone_number,relationships.relationship_description FROM users INNER JOIN relationships ON users.id = relationships.user_id WHERE relationships.subject_id =1 AND relationships.user_id<>2";     

$result = array();
$result =mysqli_query($con,$sql_contacts);

if (is_bool($result)){
		//print_r("no contacts available");
		$data = array();
		$data["contacts"] ='none';
		array_push($response["contact_related_data"], $data); 
}else {
		//print_r("contacts available");
		$i=-1;
		while($row = mysqli_fetch_array($result,MYSQLI_NUM)){
			$i++;
			$data[$i]['user_id'] = $row[0];
			$data[$i]['first_name'] = $row[1];
			$data[$i]['last_name'] = $row[2];
			$data[$i]['mobile'] = $row[3];
			$data[$i]['email'] = $row[4];
			$data[$i]['phone'] = $row[5];
			$data[$i]['description']=$row[6];
			array_push($response["contact_related_data"], $data[$i]); 
		}
}

echo json_encode($response);

?>
