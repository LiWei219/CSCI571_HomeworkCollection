<?php  

  $filter = $_GET["search"];

  if($filter=="first"){
    header("Access-Control-Allow-Origin:https://graph.facebook.com");
    $key=$_GET["key"]; 
    $type = $_GET["type"];
    if($type=="place"){
      $lat = $_GET['lat'];
      $lon = $_GET['lon'];
      $url = "https://graph.facebook.com/v2.8/search?center=".$lat.",".$lon."&q=".$key."&type=".$type."&fields=id,name,picture.width(700).height(700)&access_token=EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD";
    }else{
      $url="https://graph.facebook.com/v2.8/search?q=".$key."&type=".$type."&fields=id,name,picture.width(700).height(700)&access_token=EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD";
    }
   // var_dump($url);
    
    $curl = curl_init();
    curl_setopt($curl, CURLOPT_URL, $url);
    curl_setopt($curl, CURLOPT_HEADER, 1);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);//这个是重点。
    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, true);
    $data = curl_exec($curl);
    curl_close($curl);
    //var_dump($data);
    //echo $data;
    //output the response  
    //$b = $data->getDecodedBody();
    $sta = strpos($data,"{");
    $end = strrpos($data,"}");
    $b = substr($data,$sta, $end-$sta+1);
    //var_dump($b);
    echo $b;
    
    //echo "{$_GET['jsoncallback']}({$b})";

  }else  if($filter=="page"){
    header("Access-Control-Allow-Origin:https://graph.facebook.com");
    $type = $_GET['type'];
    $q = $_GET['q'];
    //$access = $_GET['access_token'];
    $limit = $_GET['limit'];
    
    

    if($type=="place"){
      $after = $_GET['after'];

      $center = $_GET['center'];
      $url = "https://graph.facebook.com/v2.8/search?center=".$center."&fields=id,name,picture.width(700).height(700)&type=place&q=".$q."&access_token=EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD&limit=".$limit."&after=".$after;
    }else{
      $afterid = $_GET['__after_id'];
      $offset = $_GET['offset'];
      $url = "https://graph.facebook.com/v2.8/search?fields=id,name,picture.width(700).height(700)&type=".$type."&q=".$q."&access_token=EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD&limit=".$limit."&offset=".$offset."&__after_id=".$afterid;
    }
    //$re = "hhh";
    //echo $url;
    
    $curl = curl_init();
    curl_setopt($curl, CURLOPT_URL, $url);
    curl_setopt($curl, CURLOPT_HEADER, 1);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);//这个是重点。
    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, true);
    $data = curl_exec($curl);
    curl_close($curl);
    $sta = strpos($data,"{");
    $end = strrpos($data,"}");
    $b = substr($data,$sta, $end-$sta+1);

    echo $b;     
  
  }else if($filter=="detail"){
    header("Access-Control-Allow-Origin:https://graph.facebook.com");
    $id = $_GET['id'];
    //var_dump($id);
    $url = "https://graph.facebook.com/v2.8/".$id."?fields=id,name,picture.width(700).height(700),albums.limit(5){name,photos.limit(2){name,picture,id}},posts.limit(5){created_time,message,story}&access_token=EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD";
    //var_dump($url);
    $curl = curl_init();
    curl_setopt($curl, CURLOPT_URL, $url);
    curl_setopt($curl, CURLOPT_HEADER, 1);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);//这个是重点。
    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, true);
    $data = curl_exec($curl);
    //var_dump($data);
    curl_close($curl);
    $sta = strpos($data,"{");
    $end = strrpos($data,"}");
    $b = substr($data,$sta, $end-$sta+1);
    //echo "{$_GET['jsoncallback']}({$b})"; 
    echo $b;

  }

?>  