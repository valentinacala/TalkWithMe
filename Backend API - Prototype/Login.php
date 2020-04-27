<?php


//server receives userid and userpassword from post request
if(isset($_POST['PostData'])){
	$PostData = $_POST['PostData'];
	$jsonArray = json_decode($PostData,true);
	$username = $jsonArray['userid'];
	$password = $jsonArray['password'];
}



//server connection 
define('DB_USER', "app_user"); // db user with select and insert privileges
define('DB_PASSWORD', "pIbSD4Z5W9ra5Y3y");
define('DB_DATABASE', "applicationdata");
define('DB_SERVER', "localhost"); 
$con = mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD,DB_DATABASE) or die(mysql_error());
if (mysqli_connect_errno()){
	echo "Connection failed".die(mysqli_connect_error());
}

$response["user_related_data"] = array();

//sql request to DB
$sql_userid="SELECT id FROM users WHERE username ='".$username."' AND password= '".$password."';";
$result = array();
$result = mysqli_query($con,$sql_userid);
$row = mysqli_fetch_array($result,MYSQLI_ASSOC);

//verify if there are result to the sent new query
if($row){
	//valid user credentials received
	$data = array();
	$data["user_id"] = $row["id"];
	array_push($response["user_related_data"], $data);  
	
	//sql request to DB
	$sql_subject = "SELECT subjects.id, subjects.first_name, subjects.last_name  FROM subjects INNER JOIN relationships ON subjects.id = relationships.subject_id WHERE relationships.user_id =".$data["user_id"]; 
	$result = array();
	$result = mysqli_query($con,$sql_subject);
	

	$i=-1;
	while($row = mysqli_fetch_array($result,MYSQLI_NUM)){
		$i++;
		$data[$i]['subject_id'] = $row[0];
		$data[$i]['subject_name'] = $row[1]." ".$row[2];
		array_push($response["user_related_data"], $data[$i]); 
	}
	
}else{
	//user doesn't exist
	$user ["user_id"]= 'none';
	$response["user_related_data"] = array();
	array_push($response["user_related_data"], $user);
}
echo json_encode($response);

//{"user_related_data":[{"user_id":"none"}]} IF USER DOESN'T EXIST
//or
//{"user_related_data":[{"user_id":"5"}]} IF USER EXIST BUT NO SUBJECT IS ASSOCIATED TO IT
//OR
//{"user_related_data":[{"user_id":"3"},{"subject_id":"1","subject_name":"Giulio"},{"subject_id":"3","subject_name":"Gianpaolo"}]}



?>