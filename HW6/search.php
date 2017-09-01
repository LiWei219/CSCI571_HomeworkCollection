
<!doctype html>
<html>
	<head>

		<meta charset="UTF-8">
		<title>
			Homework6
		</title>
		
		<script type="text/javascript">
			function getvalue(){
			//   alert(document.getElementById("ty").value);
				var type = document.getElementById("ty").value;
				if(type == "place"){
					document.getElementById("hidden").style.visibility="visible";
				}else{
					document.getElementById("hidden").style.visibility="hidden";
				}				
			}

			function changeatable(){
				if(document.getElementById("atable").style.display=="none"){
					document.getElementById("atable").style.display="table";
					document.getElementById("ptable").style.display="none";
				}else{
					document.getElementById("atable").style.display="none";
				}
			}

			function changeptable(){
				if(document.getElementById("ptable").style.display=="none"){
					document.getElementById("ptable").style.display="table";
					document.getElementById("atable").style.display="none";
				}else{
					document.getElementById("ptable").style.display="none";
				}
			}

			function changevi(num){
				var item = "row"+num;
				if(document.getElementById(item).style.display=="none"){
					document.getElementById(item).style.display="table-row";
				}else{
					document.getElementById(item).style.display="none";
				}
			}


		</script>

		<style type="text/css" media="screen">
			table, th,td {
				border: 1px solid black;
				border-collapse: collapse;
				background-color:#f4f4f4; 
				border:solid #d6d8d8 thin;

			}
		</style>		
	</head>

	<body>

		<?php 

			//global:  $bodytext, $dblen, $initext, $i

			if($_POST['submit']){
				$_SESSION['submit'] = $_POST['submit'];
				$_SESSION['key'] = $_POST['key'];
				$_SESSION['type'] = $_POST['type'];
			//	echo $_SESSION['submit']." ".$_SESSION['key']." ".$_SESSION['type'];
				if($_POST['type']=="place"){
					$_SESSION['loc'] = $_POST['loc'];
					$_SESSION['dis'] = $_POST['dis'];
					if($_SESSION['loc']!=""){
						getloc();
					}
					rawquery();

				}else{
					rawquery();
				}
			}

			if(isset($_GET['content'])&&$_GET['content']=="details"){
				$id = $_GET['id'];
				$_SESSION['submit']="Submit";
				$_SESSION['key']=$_GET['key'];
				$_SESSION['type']=$_GET['type'];
			//	echo $_SESSION['submit']." ".$_SESSION['key']." ".$_SESSION['type'];
				if($_GET['type']=="place"){
					$_SESSION['loc'] = $_GET['loc'];
					$_SESSION['dis'] = $_GET['dis'];
				}
				albpost($id);
			}


			function rawquery(){
				require_once __DIR__ . '/php-graph-sdk-5.0.0/src/Facebook/autoload.php';

				$fb = new Facebook\Facebook([
  				'app_id' => '591946817676747',
  				'app_secret' => '18b86724580109c56e09bff0b50ffbbc',
  				'default_graph_version' => 'v2.8',
				]);

				$fb->setDefaultAccessToken('EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD');

				
  			    // Returns a `Facebook\FacebookResponse` object
  			    try{
  			    	$key = $_SESSION['key'];
  			    	$type = $_SESSION['type'];
  			    //	echo $key." ".$type;
  			    	if($type=="place"){
  			    		if($_SESSION['loc']==""||($_SESSION['loc']!=""&&$_SESSION['lat']==0&&$_SESSION['lng']==0)){
  			    		//	echo "no loc no dis";
  			    			$response = $fb->get('/search?q='.$key.'&type='.$type.'&fields=id,name,picture.width(700).height(700)','EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD');
  			    		}else{
  			    			
  			    			if($_SESSION['dis']==""){
  			    		//		echo "no dis ".(float)$_SESSION["lat"]." ".(float)$_SESSION["lng"];

  			    				$response = $fb->get('/search?q='.$key.'&type='.$type.'&center='.(float)$_SESSION["lat"].','.(float)$_SESSION["lng"].'&fields=id,name,picture.width(700).height(700)','EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD');

  			    			}else{
  			    		//		echo (float)$_SESSION["lat"]." ".(float)$_SESSION["lng"]." ".$_SESSION["dis"];
  			    				$response = $fb->get('/search?q='.$key.'&type='.$type.'&center='.(float)$_SESSION["lat"].','.(float)$_SESSION["lng"].'&distance='.$_SESSION["dis"].'&fields=id,name,picture.width(700).height(700)','EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD');
  			    			}
  			    		}
  			    		
  			    	}else if($type=="event"){
  			    		$response = $fb->get('/search?q='.$key.'&type='.$type.'&fields=id,name,picture.width(700).height(700),place','EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD');
  			    	}else{
  			    		$response = $fb->get('/search?q='.$key.'&type='.$type.'&fields=id,name,picture.width(700).height(700)','EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD');
  			    	}
  					
					global $bodytext;
					$bodytext = $response->getDecodedBody();
  			//		var_dump($bodytext);
				} catch(Facebook\Exceptions\FacebookResponseException $e) {
  					echo 'Graph returned an error: ' . $e->getMessage();
  					exit;
				} catch(Facebook\Exceptions\FacebookSDKException $e) {
  					echo 'Facebook SDK returned an error: ' . $e->getMessage();
  					exit;
				}

			//	echo "OK!";
				$data = $bodytext['data']; 

				global $dblen;
				$dblen = count($data);
				global $initext;
				global $i;

				if($_SESSION['type']=="event"){
					if($dblen!=0){
						$initext = "<table style='position:absolute; top:275px; left:350px; width:600px;'><thead align='left'> <tr> <th> Profile Photo</th><th>Name</th><th>Place</th></tr></thead><tbody>";
						for($i=0; $i<$dblen; $i++){
							$initext = $initext."<tr><td>";

							$initext = $initext.'<img src="'.$data[$i]['picture']['data']['url'].'" height="30" width="40" onclick="window.open(this.src)" style="cursor:pointer"></td>';
                        	$initext = $initext.'<td>'.$data[$i]['name'].'</td>';
                        	$initext = $initext.'<td>'.$data[$i]['place']['name'].'</td> </tr>';                 
						}	
						$initext = $initext."</tbody></table>";
					}else{
						$initext = "<table style='position:absolute; top:275px; left:350px; width:600px;'><thead> </thead><tbody align='center'>
						<tr><td>No Records has been found. </td></tr></tbody></table>";
					}

				}else{
					if($dblen!=0){
						$initext = "<table style='position:absolute; top:275px; left:350px; width:600px;'><thead align='left'> <tr> <th> Profile Photo</th><th>Name</th><th>Details</th></tr></thead><tbody>";
						for($i=0; $i<$dblen; $i++){
							$initext = $initext."<tr><td>";
							$initext = $initext.'<img src="'.$data[$i]['picture']['data']['url'].'" height="30" width="40" onclick="window.open(this.src)" style="cursor:pointer"></td>';
                        	$initext = $initext.'<td>'.$data[$i]['name'].'</td>';
                        	$initext = $initext.'<td><a href="search.php?content=details&id='.$data[$i]['id'].'&key='.$_SESSION['key'].'&type='.$_SESSION['type'].'">Details</a></td> </tr>';                 
						}	
						$initext = $initext."</tbody></table>";
					}else{
						$initext = "<table style='position:absolute; top:275px; left:350px; width:600px;'><thead> </thead><tbody align='center'>
						<tr><td>No Records has been found. </td></tr></tbody></table>";
					}
				}

				
				echo $initext;
			}   


			function albpost($id){
				require_once __DIR__ . '/php-graph-sdk-5.0.0/src/Facebook/autoload.php';

				$fb = new Facebook\Facebook([
  				'app_id' => '591946817676747',
  				'app_secret' => '18b86724580109c56e09bff0b50ffbbc',
  				'default_graph_version' => 'v2.8',
				]);

				$fb->setDefaultAccessToken('EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD');

				try{ 			    	
  					$response = $fb->get('/'.$id.'?fields=id,name,picture.width(700).height(700),albums.limit(5){name,photos.limit(2){name, picture}},posts.limit(5)','EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD');

					$details = $response->getDecodedBody();
  				//	var_dump($details);
				} catch(Facebook\Exceptions\FacebookResponseException $e) {
  					echo 'Graph returned an error: ' . $e->getMessage();
  					exit;
				} catch(Facebook\Exceptions\FacebookSDKException $e) {
  					echo 'Facebook SDK returned an error: ' . $e->getMessage();
  					exit;
				}

				$alb = $details['albums'];
				$pos = $details['posts'];
				$al = count($alb);
				$pl = count($pos);

				$atext = '<div style="position:absolute; top:275px; left:350px;">';

				if($al!=0){
					$atext = $atext.'<table style="width:600px;"><thead> </thead><tbody align="center">
						<tr><td><a href="javascript:void(0)" onclick="changeatable();" style="color:blue; text-decoration:underline;">Albums</a></td></tr></tbody></table><br />';
					$albums = $details['albums']['data'];
			//	var_dump($albums);
					$alen = count($albums);
				
					$atext = $atext.'<table id="atable" style="width:600px; display:none;"><thead></thead><tbody>';
					for($j=0; $j<$alen; $j++){
						$atext = $atext."<tr>";
						if(count($albums[$j]['photos']['data'])==0){
							$atext = $atext.'<td>'.$albums[$j]['name'].'</td><tr">';
						}else{
							$atext = $atext.'<td><a href="javascript:void(0)" onclick="changevi('.$j.')" style="color:blue; text-decoration:underline;">'.$albums[$j]['name'].'</a></td></tr><tr style="border:white;"><td id="row'.$j.'" style="display:none; border:white;">';
							for($k=0; $k<2; $k++){
								if($albums[$j]['photos']['data'][$k]!=null){
									$atext = $atext.'<img src="'.getphoto($albums,$j,$k).'" height="80" width="80" onclick="window.open(this.src)" style="cursor:pointer"> &nbsp;';
								}								
							}
							$atext = $atext."</td></tr>";
						}                
					}
					$atext = $atext."</tbody></table><br />";
				}else{
					$atext = $atext.'<table style="width:600px;"><thead> </thead><tbody align="center">
						<tr><td>No Albums has been found. </td></tr></tbody></table><br />';
				}

				if($pl!=0){
					$atext = $atext.'<table style="width:600px;"><thead></thead><tbody align="center">
						<tr><td><a href="javascript:void(0)" onclick="changeptable();" style="color:blue; text-decoration:underline;">Posts</a></td></tr></tbody></table><br />';

					$posts = $details['posts']['data'];
					$plen = count($posts);
					$atext = $atext.'<table id="ptable" style="width:600px; display:none;"><thead align="left"><tr><th>Message</th></tr></thead><tbody>';
					$cnt=0;
					for($j=0; $j<$plen; $j++){	
						if($posts[$j]['message']!=""){
							$atext = $atext.'<tr><td>'.$posts[$j]['message'].'</td></tr>';
							$cnt = $cnt+1;
						}										   
					}
					if($cnt==0){
						$atext = $atext."<tr><td>No Massage found.</td></tr>";
					}
					$atext = $atext."</tbody></table>";
				}else{
					$atext = $atext.'<table style="width:600px;"><thead> </thead><tbody align="center">
						<tr><td>No Posts has been found. </td></tr></tbody></table>';
				}
				$atext = $atext."</div>";
				echo $atext;
			}

			function getphoto($albums,$j,$k){

				require_once __DIR__ . '/php-graph-sdk-5.0.0/src/Facebook/autoload.php';

				$pid = $albums[$j]['photos']['data'][$k]['id'];
			//	echo $pid;

				$fb = new Facebook\Facebook([
  				'app_id' => '591946817676747',
  				'app_secret' => '18b86724580109c56e09bff0b50ffbbc',
  				'default_graph_version' => 'v2.8',
				]);

				$fb->setDefaultAccessToken('EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD');

				try{ 			    	
  					$response = $fb->get('/'.$pid.'/picture?redirect=false','EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD');

					$details = $response->getDecodedBody();
  			//		var_dump($details);
				} catch(Facebook\Exceptions\FacebookResponseException $e) {
  					echo 'Graph returned an error: ' . $e->getMessage();
  					exit;
				} catch(Facebook\Exceptions\FacebookSDKException $e) {
  					echo 'Facebook SDK returned an error: ' . $e->getMessage();
  					exit;
				}

				$data = $details['data'];
				return $data['url'];				
			}

			function getloc(){
				$loc = $_SESSION['loc'];
				//google api to get lat and lng
				$url = "https://maps.googleapis.com/maps/api/geocode/json?address=".$loc."&key=AIzaSyAYyyPLC5s-y_yPn5M-bXBm1TWWVqF6Wis";
				$handle = fopen($url,"rb");
				$content = "";
				while(!feof($handle)){
					$content .= fread($handle,10000);
				}
				fclose($handle);
				$raw = json_decode($content);
			//	var_dump($raw);
				if($raw->results[0]->status!="ZERO_RESULTS"){
					$_SESSION['lat']=$raw->results[0]->geometry->location->lat;
					$_SESSION['lng']=$raw->results[0]->geometry->location->lng;
				}else{
					$_SESSION['lat']=0;
					$_SESSION['lng']=0;
				}
			}
	
		?>  

		<div style="position:absolute; top:80px; left:400px; width:500px; height:165px; background-color:#f4f4f4; border:solid #d6d8d8 thin;">

			<p style="margin:10px;  font-family:monospace; font-size:24px; font-weight:300; text-align:center;"><i>Facebook Search</i></p>
 
			<hr style="width:480px; color:#cccccc;">
			
			<form id="info" action="search.php" method="post" accept-charset="utf-8">
				<div style="position:absolute; left:8px; top:50px;">
					Keyword<input style="position: absolute;left:60px;" type="text" name="key" id="keywd" required value="<?php if($_SESSION["submit"]): echo $_SESSION['key']; else: echo ''; endif;?>" ><br />
				</div>
		
				<div style="position: absolute;left:8px; top:75px;">
					Type:<select onchange="getvalue();"  
					style="position: absolute;left:60px;" name="type" id="ty" required >
					<option value="user" <?php if(($_SESSION['submit'])&&($_SESSION['type']=="user")): echo "selected='selected'"; endif;?> >Users</option>
					<option value="page" <?php if(($_SESSION['submit'])&&($_SESSION['type']=="page")): echo "selected='selected'"; endif;?>>Pages</option>
					<option value="event" <?php if(($_SESSION['submit'])&&($_SESSION['type']=="event")): echo "selected='selected'"; endif;?>>Events</option>
					<option value="place" <?php if(($_SESSION['submit'])&&($_SESSION['type']=="place")): echo "selected='selected'"; endif;?> >Places</option>
					<option value="group" <?php if(($_SESSION['submit'])&&($_SESSION['type']=="group")): echo "selected='selected'"; endif;?> >Groups</option>
					</select>
				</div>
				<div id="hidden" style="visibility: 
				    <?php 
				    	if($_SESSION['submit']){
				    		if($_SESSION['type']=="place")
				    			echo "visible"; 
				    		else 
				    			echo "hidden";
				    	}else
				    		echo "hidden";	
				    ?>">

					<div style="position: absolute;left:8px; top:100px;">
						Location<input style="position: absolute; left:60px;" type="text" name="loc" <?php if($_SESSION["submit"]&&($_SESSION['type']=="place")): echo "value=".$_SESSION['loc']; endif;?>>	
					</div>
				
					<div style="position:absolute; left:214px; top:100px;">
						Distance(meters) <input style="position: absolute;" type="text" name="dis" <?php if($_SESSION["submit"]&&($_SESSION['type']=="place")): echo "value=".$_SESSION['dis']; endif;?>><br />
					</div>
				</div>
				
				<div style="position:absolute; left:18px; top:130px;">
					<input style="position:absolute;left:50px;" type="submit" name="submit" value="Search">
					<input style="position: absolute;left:120px;" type="button" onclick="{location.href='search.php'}" value="Clear">
				</div>
			</form>
		</div>
		
	<noscript></noscript>
	</body>

</html>