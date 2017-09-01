var obj="";
var text="";   //why test is a global variable
var url="";
var details="";
var backtext="";
var storage;
var pic="";

if (storageAvailable('localStorage')) {
	storage = window['localStorage'];
}else {
	// Too bad, no localStorage for us
	alert("LocalStorage is not Available.Please check.");
}

function npage(){
	//alert("fucntion nextpage");
	
	document.getElementById("back").style.display="none";	
	document.getElementById("acol").style.display="none";	
	document.getElementById("pcol").style.display="none";	
	url="";
	//url="homework8.php";  
	//url=url+"?search=page&paging=";
	url += obj.paging.next;
	//alert("function npage: obj.paging.next:"+obj.paging.next);

	$.ajax({
	    url: url,
	    type: 'GET',
	    async: true,
	    success: function(data, textStatus, jqXHR){
	    //  alert("created url:"+data);
	    
	      console.log(textStatus);
	      console.log(jqXHR);
	      console.log("data=:"+ data);
	      //data = "("+data+")";
	      obj = eval(data);
	      console.log(obj);

	      var len = obj.data.length;
	      text = "";
	      if(len>0){
	      	text += "<br /><table class='table'>";
	        text+="<thead><tr><th>#</th><th>Profile photo</th><th>Name</th><th>Favorite</th><th>Details</th></tr></thead>";
	        for(var i =0; i < len; i++){
	            text+="<tr><td>"+(i+1)+"</td><td><img src='"+ obj.data[i].picture.data.url+
	            "'height='30' width='30' onclick='window.open(this.src)' style='cursor:pointer;border-radius:55%;'></td>";
	            text+="<td>"+obj.data[i].name+"</td>";
	            text=text+"<td><button type='button' class='' id='starbutton' onclick='star("+(i+1)+");'><span id='star"+(i+1)+"' ";
		         // class='glyphicon glyphicon-star-empty'";
		    //    if (storageAvailable('localStorage')) {
			//		storage = window['localStorage'];
					if(storage.getItem(obj.data[i].id)!=null)
						text=text+"class='glyphicon glyphicon-star'";
					else
						text=text+"class='glyphicon glyphicon-star-empty'";
			//	}else
			//		text=text+"class='glyphicon glyphicon-star-empty'";

			      text=text+"></span></button></td><td><button type='button' onclick='detail("+obj.data[i].id+");''><span class='glyphicon glyphicon-chevron-right'></span></button></td>";

		        } 
		        text += "</table>";  
	      	}
	      document.getElementById("result").innerHTML=text; 
		  
		  if(typeof(obj.paging)!="undefined"){
		  	if(typeof(obj.paging.previous)!="undefined"){
		  		document.getElementById("pre").style.visibility="visible";
		  	}else{
		  		document.getElementById("pre").style.visibility="hidden";
		  	}
		  	var next = obj.paging.next;
			if(typeof(next)!="undefined"&&len==25) {
			  document.getElementById("next").style.visibility="visible";
			}else{
			  document.getElementById("next").style.visibility="hidden";   
			}
		  }  
		    
	    },
	    error: function(xhr, textStatus){
	      console.log("error");
	      console.log(xhr);
	      console.log(textStatus);
	    }
    })	
}

function ppage(){
	
	document.getElementById("back").style.display="none";	
	document.getElementById("acol").style.display="none";	
	document.getElementById("pcol").style.display="none";	
	url="";
	//url="homework8.php";  
	//url=url+"?search=page&paging=";
	url += obj.paging.previous;
	//alert(url);

	$.ajax({
	    url: url,
	    type: 'GET',
	    async: true,
	    success: function(data, textStatus, jqXHR){
	      console.log(data);
	      console.log(textStatus);
	      console.log(jqXHR);
	      //data = "("+data+")";
	      obj = eval(data);
	    // console.log(obj);

	      var len = obj.data.length;
	      text = "";
	      if(len>0){
	      	text += "<br /><table class='table'>";
	        text+="<thead><tr><th>#</th><th>Profile photo</th><th>Name</th><th>Favorite</th><th>Details</th></tr></thead>";
	        for(var i =0; i < len; i++){
	          	text+="<tr><td>"+(i+1)+"</td><td><img src='"+ obj.data[i].picture.data.url+
	          	"'height='30' width='30' onclick='window.open(this.src)' style='cursor:pointer;border-radius:55%;'></td>";
	          	text+="<td>"+obj.data[i].name+"</td>";
	            text=text+"<td><button type='button' class='' id='starbutton' onclick='star("+(i+1)+");'><span id='star"+(i+1)+"' ";
	         	// class='glyphicon glyphicon-star-empty'";
		     //   if (storageAvailable('localStorage')) {
			 //		storage = window['localStorage'];
					if(storage.getItem(obj.data[i].id)!=null)
						text=text+"class='glyphicon glyphicon-star'";
					else
						text=text+"class='glyphicon glyphicon-star-empty'";
			//	}else
			//		text=text+"class='glyphicon glyphicon-star-empty'";

			    text=text+"></span></button></td><td><button type='button' onclick='detail("+obj.data[i].id+");''><span class='glyphicon glyphicon-chevron-right'></span></button></td>";

		    }
		        text += "</table>";  
		 }
	      document.getElementById("result").innerHTML=text; 
		   
		  

		  if(typeof(obj.paging)!="undefined"){
		  	if(typeof(obj.paging.next)!="undefined"&&len==25){
		  		document.getElementById("next").style.visibility="visible";
		  	}else{
		  		document.getElementById("next").style.visibility="hidden";
		  	}
		  	var pre = obj.paging.previous;
			//alert(pre);
			if(typeof(pre)=="undefined") {
			  document.getElementById("pre").style.visibility="hidden";
			}else{
			  document.getElementById("pre").style.visibility="visible";
			}
		  }
		  
		  	
	    },
	    error: function(xhr, textStatus){
	      console.log("error");
	      console.log(xhr);
	      console.log(textStatus);
	    }
    })	
}

function changepro(){
	console.log(angular.element(document.getElementById('app')).scope().pro);
    angular.element(document.getElementById('app')).scope().pro = false;
    angular.element(document.getElementById('app')).scope().$apply();
    console.log(angular.element(document.getElementById('app')).scope().pro);
}

function changeap(){
	console.log(angular.element(document.getElementById('app')).scope().alb);
    angular.element(document.getElementById('app')).scope().alb = false;
    angular.element(document.getElementById('app')).scope().$apply();
    console.log(angular.element(document.getElementById('app')).scope().alb);
    console.log(angular.element(document.getElementById('app')).scope().pos);
    angular.element(document.getElementById('app')).scope().pos = false;
    angular.element(document.getElementById('app')).scope().$apply();
    console.log(angular.element(document.getElementById('app')).scope().pos);
}

function query(){
	//alert("function search");

	if(document.getElementById("type").value=="fav"){
		favtable();
		return;
	}
	document.getElementById("all").style.display="block";

	var key = document.getElementById("keywd").value;
	//var ty = document.getElementById("ty").value;
	var type = document.getElementById("type").value;
	
	if(key==""){
		//alert("null");
		$('[data-toggle="tooltip"]').tooltip('show');
		return;
	}else{
		$("[data-toggle='tooltip']").tooltip('destroy');
	}
/*
	console.log(angular.element(document.getElementById('app')).scope().all);
    angular.element(document.getElementById('app')).scope().all = true;
    angular.element(document.getElementById('app')).scope().$apply();
    console.log(angular.element(document.getElementById('app')).scope().all);  */

	
   // angular.element(document.getElementById('app')).scope().test();
	console.log(angular.element(document.getElementById('app')).scope().pro);
    angular.element(document.getElementById('app')).scope().pro = true;
    angular.element(document.getElementById('app')).scope().$apply();
    console.log(angular.element(document.getElementById('app')).scope().pro);
	//var element=angular.element(document.getElementById('app'));
	//var scope=element.scope();
	//scope.pro=true;


	document.getElementById("back").style.display="none";	
	document.getElementById("acol").style.display="none";	
	document.getElementById("pcol").style.display="none";	

	
	//alert(key+" "+type);
	if(typeof(type)=="undefined"){
		type = "user";
	}
	text="";
	document.getElementById("result").innerHTML=text;
	document.getElementById("pre").style.visibility="hidden";
	document.getElementById("next").style.visibility="hidden";
	//alert(type);
	//alert(key);
	//alert(ty);

  	url="";
	url="homework8.php";  

	if(type=="place"){
		if(!navigator.geolocation){
			alert("geolocation is not supported by your browser!");
			return;
		}

		function success(pos) {
			var crd = pos.coords;
		    var lat = pos.coords.latitude;
		    var lon = pos.coords.longitude;

		    url = url+"?search=first&key="+key+"&type=place&lat="+lat+"&lon="+lon;
		    //alert(url);

		    $.ajax({
			    url: url,
			    type: 'GET',
			    async: true,
			    contentType: 'json',
			    success: function(data, textStatus, jqXHR){
					console.log(data);
					console.log(textStatus);
					console.log(jqXHR);
					data = "("+data+")";
					obj = eval(data);
					// console.log(obj);

					var len = obj.data.length;
					text = "";
					if(len>0){
						text += "<br /><table class='table'>";
						text+="<thead><tr><th>#</th><th>Profile photo</th><th>Name</th><th>Favorite</th><th>Details</th></tr></thead>";
						for(var i =0; i < len; i++){
						    text+="<tr><td>"+(i+1)+"</td><td><img src='"+ obj.data[i].picture.data.url+
						          "'height='30' width='30' onclick='window.open(this.src)' style='cursor:pointer;border-radius:55%;'></td>";
						    text+="<td>"+obj.data[i].name+"</td>";
	            			text=text+"<td><button type='button' class='' id='starbutton' onclick='star("+(i+1)+");'><span id='star"+(i+1)+"' ";
					         // class='glyphicon glyphicon-star-empty'";
					    //    if (storageAvailable('localStorage')) {
						//		storage = window['localStorage'];
								if(storage.getItem(obj.data[i].id)!=null)
									text=text+"class='glyphicon glyphicon-star'";
								else
									text=text+"class='glyphicon glyphicon-star-empty'";
						//	}else
						//		text=text+"class='glyphicon glyphicon-star-empty'";

							text=text+"></span></button></td><td><button type='button' onclick='detail("+obj.data[i].id+");''><span class='glyphicon glyphicon-chevron-right'></span></button></td>";
						}
						text += "</table>";  
					}

					changepro();

					document.getElementById("result").innerHTML=text;
					document.getElementById("pre").style.visibility="hidden";
					document.getElementById("next").style.visibility="hidden";
					if(typeof(obj.paging)!="undefined"&&len==25){
						var next = obj.paging.next;
						//alert("obj.paging.next in search function place:"+next);
						if(typeof(next)!="undefined") {
							document.getElementById("next").style.visibility="visible";
						}else{
							document.getElementById("next").style.visibility="hidden";
						} 
					}
			      
			    },
			    error: function(xhr, textStatus){
					console.log("error");
					console.log(xhr);
					console.log(textStatus);
			    }
			}); 
		};

		function error(err) {
		  alert(`ERROR(${err.code}): ${err.message}`);
		};

		navigator.geolocation.getCurrentPosition(success, error);

	}else{
	
		url=url+"?search=first&key="+key;  
		url=url+"&type="+type;  
    	//alert(url);

    	$.ajax({
		    url: url,
		    type: 'GET',
		    async: true,
		    success: function(data, textStatus, jqXHR){
		      console.log(data);
		      console.log(textStatus);
		      console.log(jqXHR);
		      data = "("+data+")";
		      obj = eval(data);
		    // console.log(obj);

		      var len = obj.data.length;
		      var text = "";
		      if(len>0){
		      	text += "<br /><table class='table' >";
		        text+="<thead><tr><th>#</th><th>Profile photo</th><th>Name</th><th>Favorite</th><th>Details</th></tr></thead>";
		        for(var i =0; i < len; i++){
		            text+="<tr><td>"+(i+1)+"</td><td><img src='"+ obj.data[i].picture.data.url+
		          "'height='30' width='30' onclick='window.open(this.src)' style='cursor:pointer;border-radius:55%;'></td>";
		            text+="<td>"+obj.data[i].name+"</td>";
	            	text=text+"<td><button type='button' class='' id='starbutton' onclick='star("+(i+1)+");'><span id='star"+(i+1)+"' ";
					
					         // class='glyphicon glyphicon-star-empty'";
				    //    if (storageAvailable('localStorage')) {
					//		storage = window['localStorage'];
							if(storage.getItem(obj.data[i].id)!=null)
								text=text+"class='glyphicon glyphicon-star'";
							else
								text=text+"class='glyphicon glyphicon-star-empty'";
					//	}else
					//		text=text+"class='glyphicon glyphicon-star-empty'";

					text=text+"></span></button></td><td><button type='button' onclick='detail("+obj.data[i].id+");''><span class='glyphicon glyphicon-chevron-right'></span></button></td>";

		        }
		        text += "</table>";  
		      }

		      changepro();
		      document.getElementById('result').innerHTML=text;
		      document.getElementById("pre").style.visibility="hidden";
		      document.getElementById("next").style.visibility="hidden";
		      if(typeof(obj.paging)!="undefined"&&len==25){
		      	var next = obj.paging.next;
		      	//alert(next);
			  	if(typeof(next)!="undefined") {
			  		document.getElementById("next").style.visibility="visible";
			  	}else{
			  		document.getElementById("next").style.visibility="hidden";
			  	} 
		      }
		      
		    },
		    error: function(xhr, textStatus){
		      console.log("error");
		      console.log(xhr);
		      console.log(textStatus);
		    }
		})  
	}	 
} 

function star(i){
	
	//alert("function:star! ");
	var id="#star"+i;
	if($(id).hasClass("glyphicon glyphicon-star-empty")){
		$(id).attr("class","glyphicon glyphicon-star");
		//$(id).attr("color","yellow");
		//add some info to local storage
		//var str=name+"+"+purl+"+"+document.getElementById("type");
		var str="";
		var type = document.getElementById("type").value;;
		if(typeof(type)=="undefined")
			type = "user";
		var tid, tname, turl;
		if(i!=30){
			tid=obj.data[i-1].id;
			tname=obj.data[i-1].name;
			turl = obj.data[i-1].picture.data.url;
			
		}else{
			tid = details.id;
			tname = details.name;
			turl = details.picture.data.url;
		}
		str = tname+"+"+turl+"+"+type;
		
		storage.setItem(tid,str);
	
	}else{
		$(id).attr("class","glyphicon glyphicon-star-empty");

		if(i!=30){
			tid=obj.data[i-1].id;
			
		}else{
			tid = details.id;
		}
		if(storage.getItem(tid)!=null){
			storage.removeItem(tid);
			//alert("Delete success!");
		}	
	}
}

function storageAvailable(type) {
	try {
		var storage = window[type],
			x = '__storage_test__';
		storage.setItem(x, x);
		storage.removeItem(x);
		return true;
	}
	catch(e) {
		return false;
	}
}

function detail(id){
//	alert("function details! "+id);
	


	document.getElementById("result").innerHTML=""; 
	document.getElementById("pre").style.visibility="hidden";
	document.getElementById("next").style.visibility="hidden";
	document.getElementById("all").style.display="none";
	document.getElementById("back").style.display="block";
	document.getElementById("acol").style.display="block";
	document.getElementById("pcol").style.display="block";
	document.getElementById("albums").innerHTML="";
	document.getElementById("posts").innerHTML="";


	console.log(angular.element(document.getElementById('app')).scope().alb);
	angular.element(document.getElementById('app')).scope().alb = true;
	angular.element(document.getElementById('app')).scope().$apply();
	console.log(angular.element(document.getElementById('app')).scope().alb);

	console.log(angular.element(document.getElementById('app')).scope().pos);
    angular.element(document.getElementById('app')).scope().pos = true;
    angular.element(document.getElementById('app')).scope().$apply();
    console.log(angular.element(document.getElementById('app')).scope().pos);


	var durl="homework8.php";  
	durl=durl+"?search=detail&id=";
	durl += id;
	//alert(url);
	$.ajax({
	    url: durl,
	    type: 'GET',
	    async: true,
	    success: function(data, textStatus, jqXHR){
			console.log(data);
			console.log(textStatus);
			console.log(jqXHR);
		    data = "("+data+")";
			details="";
			details = eval(data);

			//console.log("details above: "+details.id + +" "+details.name+" "+details.picture.data.url);

			//text="";
			//text=text+"<button type='button' onclick='back();' style='position:relative; border-radius:1px; top:10px;left:2%; height:30px;'>";
			//text=text+"<span class='glyphicon glyphicon-chevron-left'></span>Back</button>";
			//text=text+"<button type='button' onclick='star(30);' style='position:relative; border-radius:1px; left:84%; top:10px; width:37px; height:30px;'>";
			//text=text+"<span id='star30' ";

			//console.log("details below: "+details.id + +" "+details.name+" "+details.picture.data.url);

			//judge star type		
			if(storage.getItem(details.id)!=null){
				document.getElementById('star30').className="glyphicon glyphicon-star";
				//text=text+"class='glyphicon glyphicon-star'>";
				console.log("id: "+details.id+"\ncontent: "+storage.getItem(details.id));
			}
			else{
				document.getElementById('star30').className="glyphicon glyphicon-star-empty";
				//text=text+"class='glyphicon glyphicon-star-empty'>";
			}

			//text=text+"</span></button><button type='button' onclick='share();' style='border:1px solid #D2D2D2; border-radius:1px;  position: relative; top:5px; left:85%; height:30px; width:30px; background-image:url(facebook.png); background-size:100% 100%;'></button> <br /><br />";
			//console.log("backtext= "+text);
			
			text="";
			var atext="";
			if(details.albums!=null){
				var alen = details.albums.data.length;
				if(alen==0){
					atext+="<ul class='list-group'><li class='list-group-item list-group-item-warning'>No data found.</li></ul>";
				}else{
					var albums = details.albums.data;
					for(var i = 0; i < alen; i++){
						atext+="<div class='panel panel-default'><div class='panel-heading'>";
						if(typeof(albums[i].photos)=="undefined"){
							atext=atext+albums[i].name+"</div></div>";
						}else{
							atext=atext+"<a href='javascript:void(0)' onclick='showpic("+i+")'>"+albums[i].name+"</a></div>";
							atext=atext+"<ul class='list-group' id='row"+i+"' style='"
							if(i==0)
								atext=atext+"display:block;";
							else
								atext=atext+"display:none;";
							atext=atext+"'><li class='list-group-item'>";
							for(var k=0; k<2; k++){
								if(albums[i].photos.data[k]!=null){
								//getphoto
							    	getphoto(albums,i,k);
									atext=atext+'<img src="'+pic.data.url+'" class="img-responsive" onclick="window.open(this.src)" style="cursor:pointer; border-radius:3px"><br /><br />';									
									//console.log("aaa need picurl");
								}
							}
							atext+="</li></ul></div>";
						}
					}
				}
			}else{
				atext+="<ul class='list-group'><li class='list-group-item list-group-item-warning'>No data found.</li></ul>";
			}
			var ptext="";
			if(details.posts!=null){
				
				var plen = details.posts.data.length;
				if(plen==0){
					ptext+="<ul class='list-group'><li class='list-group-item list-group-item-warning'>No data found.</li></ul>";
				}else{
					var posts = details.posts.data;
					
					for(var i=0; i < plen; i++){
						ptext+="<ul class='list-group'><li class='list-group-item'><img style='display:inline-block;' height='50' width='50' src='"+details.picture.data.url+"'><p style='display:inline-block; position:relative; top:10px; left:10px;'><b>"+details.name+"</b><br /><span style='color:#9C9C98;'>"+
						posts[i].created_time.substr(0,10)+"&nbsp;"+posts[i].created_time.substr(11,8)+"</span><br /></p><br /><p></p>";
						if(posts[i].message!=null){
							ptext+=posts[i].message;
						}
						else if(post[i].story!=null){
							ptext+=posts[i].story;
						}else{
							ptext+="No message included.";
						}
						ptext+="</li></ul>";
					}
				}
			}else{
				ptext+="<ul class='list-group'><li class='list-group-item list-group-item-warning'>No data found.</li></ul>";
			}
		
			changeap();

			document.getElementById("albums").innerHTML=atext;
			document.getElementById("posts").innerHTML=ptext;
	      
	    },
	    error: function(xhr, textStatus){
			console.log("error");
			console.log(xhr);
			console.log(textStatus);
	    }
    });

	console.log(angular.element(document.getElementById('app')).scope().det);
	angular.element(document.getElementById('app')).scope().det = false;
	angular.element(document.getElementById('app')).scope().$apply();
	console.log(angular.element(document.getElementById('app')).scope().det);

	console.log(angular.element(document.getElementById('app')).scope().det);
	angular.element(document.getElementById('app')).scope().det = true;
	angular.element(document.getElementById('app')).scope().$apply();
	console.log(angular.element(document.getElementById('app')).scope().det);
      
}

function showpic(i){
	var item = "row"+i;
	var len = details.albums.data.length;

	if(document.getElementById(item).style.display=="none"){
		document.getElementById(item).style.display="block";
	}else{
		document.getElementById(item).style.display="none";
	}
	for(var k = 0; k <len; k++){
		if(k!=i){
			var arg = "row"+k;
			document.getElementById(arg).style.display="none";
		}
	}
}

function getphoto(albums,i,k){
	var link="https://graph.facebook.com/v2.8/"+albums[i].photos.data[k].id+
	"/picture?redirect=false&access_token=EAAIaX1vLJcsBAGDfiTaZBetKXKm59YVsPxQuyCDBAzplumzuaHuT4ghZCbhickIIyin8jZBTk27caqIop1z6zqY09erKQgyo6RATMBNq97WDFeJMfBsdm20oB1ib6aD0oSUmgJLVDSoWjOzx43i1RTQSBQCidMZD";
	//var value=albums[i].photos.data[k].picture;
	var value="";
	$.ajax({
	    url: link,
	    type: 'GET',
	    async: false,
	    success:function(data, textStatus, jqXHR){
	      console.log(data);
	      console.log(textStatus);
	      console.log(jqXHR);
	      //data = "("+data+")";
	      pic = eval(data);
	      //picurl="";
	      //picurl=pic.data.url;
	      //value=pic.data.url;
	      console.log("return photo:" +pic.data.url); 
		  
	    },
	    error: function(xhr, textStatus){
	      console.log("error");
	      console.log(xhr);
	      console.log(textStatus);
	    }
    });
    //console.log("get value:"+value);
    //return value;
}

function share(){
	//alert("pic url:"+details.picture.data.url);
	//alert("name:"+details.name);

	FB.ui({
		app_id: '1300301163391461',
		method:'share',
		display: 'popup',
		//link: window.location.href,
		href: 'http://LowCost-env.hszknzdtin.us-west-2.elasticbeanstalk.com/test8.html',
		picture: details.picture.data.url,
		title: details.name,
		caption:'FB SEARCH FROM USC CSCI571'
	},function(response){
		if(response&&!response.error_message)
			alert("Posted Successfully!")
		else{
			alert("Not Posted.")
		}
	});
}

function back(){

	document.getElementById("back").style.display="none";	
	document.getElementById("acol").style.display="none";	
	document.getElementById("pcol").style.display="none";	
	document.getElementById("all").style.display="block";

	//alert("back!");
	//alert("back! type="+document.getElementById("type").value);
	if(document.getElementById("type").value=="fav"){
	//	backtext="";
		favtable();
	}else{
	//	backtext="";
	//	document.getElementById("back").innerHTML="";
		var cururl= url;

		$.ajax({
		    url: url,
		    type: 'GET',
		    async: true,
		    success: function(data, textStatus, jqXHR){
		    //  alert("created url:"+data);
		    
		      console.log(textStatus);
		      console.log(jqXHR);
		      try{	
		      	obj = eval(data);
		      }catch(err){
		      	data = "("+data+")";
		      	obj = eval(data);
		      }
		      
		     // console.log(obj);

		      var len = obj.data.length;
		      text = "";
		      if(len>0){
		      	text += "<br /><table class='table'>";
		        text+="<thead><tr><th>#</th><th>Profile photo</th><th>Name</th><th>Favorite</th><th>Details</th></tr></thead>";
		        for(var i =0; i < len; i++){
		          text+="<tr><td>"+(i+1)+"</td><td><img src='"+ obj.data[i].picture.data.url+
		          "'height='30' width='30' onclick='window.open(this.src)' style='cursor:pointer;border-radius:55%;'></td>";
		          text+="<td>"+obj.data[i].name+"</td>";
		          text=text+"<td><button type='button' class='' id='starbutton' onclick='star("+(i+1)+");'><span id='star"+(i+1)+"' ";
		          "class='glyphicon glyphicon-star-empty'>";
		          if(storage.getItem(obj.data[i].id)!=null)
						text=text+"class='glyphicon glyphicon-star'";
				  else
						text=text+"class='glyphicon glyphicon-star-empty'";
		          
			      text=text+"></span></button></td><td><button type='button' onclick='detail("+obj.data[i].id+");''><span class='glyphicon glyphicon-chevron-right'></span></button></td>";

		        } 
		        text += "</table>";  
		      }
		      document.getElementById("result").innerHTML=text; 
			  
			  if(typeof(obj.paging)!="undefined"){
			  	if(typeof(obj.paging.previous)!="undefined"){
			  		document.getElementById("pre").style.visibility="visible";
			  	}else{
			  		document.getElementById("pre").style.visibility="hidden";
			  	}
			  	var next = obj.paging.next;
				if(typeof(next)!="undefined") {
				  document.getElementById("next").style.visibility="visible";
				}else{
				  document.getElementById("next").style.visibility="hidden";   
				}
			  }  
			    
		    },
		    error: function(xhr, textStatus){
		      console.log("error");
		      console.log(xhr);
		      console.log(textStatus);
		    }
	    });
	}

	console.log(angular.element(document.getElementById('app')).scope().all);
	angular.element(document.getElementById('app')).scope().all = false;
	angular.element(document.getElementById('app')).scope().$apply();
	console.log(angular.element(document.getElementById('app')).scope().all);

	console.log(angular.element(document.getElementById('app')).scope().all);
	angular.element(document.getElementById('app')).scope().all = true;
	angular.element(document.getElementById('app')).scope().$apply();
	console.log(angular.element(document.getElementById('app')).scope().all);	  
}

function user(){
	$("#user").css({background:"#285F90", color:"#ffffff"});
	$("#page").css({color:"#000000", background:"#ffffff"});
	$("#events").css({color:"#000000",background:"#ffffff"});
	$("#place").css({background:"#ffffff", color:"#000000"});
	$("#group").css({background:"#ffffff", color:"#000000"});
	$("#fav").css({background:"#ffffff", color:"#000000"});
	document.getElementById("type").value="user";
	query();
}

function page(){
	$("#page").css({background:"#285F90", color:"#ffffff"});
	$("#user").css({color:"#000000", background:"#ffffff"});
	$("#events").css({color:"#000000",background:"#ffffff"});
	$("#place").css({background:"#ffffff", color:"#000000"});
	$("#group").css({background:"#ffffff", color:"#000000"});
	$("#fav").css({background:"#ffffff", color:"#000000"});
	document.getElementById("type").value="page";
	query();
}

function events(){
	$("#events").css({background:"#285F90", color:"#ffffff"});
	$("#user").css({color:"#000000", background:"#ffffff"});
	$("#page").css({color:"#000000",background:"#ffffff"});
	$("#place").css({background:"#ffffff", color:"#000000"});
	$("#group").css({background:"#ffffff", color:"#000000"});
	$("#fav").css({background:"#ffffff", color:"#000000"});
	document.getElementById("type").value="event";
	query();
}

function place(){
	$("#place").css({background:"#285F90", color:"#ffffff"});
	$("#user").css({color:"#000000", background:"#ffffff"});
	$("#page").css({color:"#000000",background:"#ffffff"});
	$("#events").css({background:"#ffffff", color:"#000000"});
	$("#group").css({background:"#ffffff", color:"#000000"});
	$("#fav").css({background:"#ffffff", color:"#000000"});
	document.getElementById("type").value="place";
	query();
}

function group(){
	$("#group").css({background:"#285F90", color:"#ffffff"});
	$("#user").css({color:"#000000", background:"#ffffff"});
	$("#page").css({color:"#000000",background:"#ffffff"});
	$("#events").css({background:"#ffffff", color:"#000000"});
	$("#place").css({background:"#ffffff", color:"#000000"});
	$("#fav").css({background:"#ffffff", color:"#000000"});
	document.getElementById("type").value="group";
	query();
}

function fav(){
	$("#fav").css({background:"#285F90", color:"#ffffff"});
	$("#user").css({color:"#000000", background:"#ffffff"});
	$("#page").css({color:"#000000",background:"#ffffff"});
	$("#events").css({background:"#ffffff", color:"#000000"});
	$("#place").css({background:"#ffffff", color:"#000000"});
	$("#group").css({background:"#ffffff", color:"#000000"});
	document.getElementById("type").value="fav";
	favtable();
}

function favtable(){
	document.getElementById("back").style.display="none";	
	document.getElementById("acol").style.display="none";	
	document.getElementById("pcol").style.display="none";
	document.getElementById("all").style.display="block";	

	changepro();
	var len = storage.length;
	text="";
	text += "<br /><table class='table'>";
	text+="<thead><tr><th>#</th><th>Profile photo</th><th>Name</th><th>Type</th><th>Favorite</th><th>Details</th></tr></thead>";
	for(var i=0; i <len; i++){
		var id = storage.key(i);
		var str = storage.getItem(id);
		if(str!=null){
			var info = str.split("+");
			var turl = info[1];
			var tname = info[0];
			var type = info[2];
			text=text+"<tr><td>"+(i+1)+"</td><td><img src='"+turl+
		            "'height='30' width='30' onclick='window.open(this.src)' style='cursor:pointer;border-radius:55%;'></td>";
		    text=text+"<td>"+tname+"</td><td>"+type+"</td><td><button  type='button' onclick='del("+id+");'><span class='glyphicon glyphicon-trash'></span></button></td>";
		    text=text+"<td><button type='button' onclick='detail("+id+");''><span class='glyphicon glyphicon-chevron-right'></span></button></td>";
		}
	}
	text+="</table>";
	document.getElementById("result").innerHTML=text; 

	document.getElementById("next").style.visibility="hidden";   
	document.getElementById("pre").style.visibility="hidden";   
}

function del(id){
	if(storage.getItem(id)!=null){
		storage.removeItem(id);
		//alert("del Delete success!"+ " "+storage.length);
		favtable();
		//alert("update!");
	}	
}

