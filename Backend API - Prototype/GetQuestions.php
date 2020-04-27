<?php
if(isset($_POST['PostData'])){
	$PostData = $_POST['PostData'];
	$jsonArray = json_decode($PostData,true);
	$user_id = $jsonArray['userid'];
	$subject_id = $jsonArray['subjectid'];
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
$sql_surveyid = "SELECT survey_id FROM relationships WHERE user_id =".$user_id." AND subject_id =".$subject_id." ;";
$result = array();
$result =mysqli_query($con,$sql_surveyid);
$row = mysqli_fetch_array($result,MYSQLI_ASSOC);

//verify if there are result to the sent new query
if($row){
	//valid suvey_id received
	$data = array();
	$data["survey_id"] = $row["survey_id"];
		
	//sql request
	$sql_questions = "SELECT questions.id, questions.text FROM questions INNER JOIN survey_questions ON survey_questions.question_id = questions.id WHERE survey_questions.survey_id =".$data["survey_id"].";";
	$result = array();
	$result = mysqli_query($con,$sql_questions);
	
	if (is_bool($result)){
		//print_r("no questions available");
	} else {
		//print_r("questions available");
		$i=-1;
		while($row = mysqli_fetch_array($result,MYSQLI_NUM)){
			$i++;
			$data[$i]['question_id'] = $row[0];
			$data[$i]['question_text'] = $row[1];
			array_push($response["user_related_data"], $data[$i]); 
		}
	}
	
		
	
} else{
	$data = array();
	$data["survey_id"] ='none';
	array_push($response["user_related_data"], $data); 
}
echo json_encode($response);

?>