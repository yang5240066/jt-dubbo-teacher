	//创建map类
		function Map()
		{
			var myArray=new Array();
			this.put=function(key,value)
			{
				myArray[key]=value;
			}
			this.get=function(key)
			{
				return myArray[key];
			}
			
		}
		//test.html?callback=www&id=1
		//把参数转成map
			function searchToMap()
			{
		           var map=new Map();
		           var param=location.search;
		          // console.log(param);
		           if (param.indexOf("?")>=0)
		        	   {
		        	   param=param.substr(1);
		        	   //callback=www&id=1
		        	  // console.log(param);
		        	   //[0]= callaback=www
		        	   //[1]= id=1
		        	   var nameValues=param.split("&");
		        	   for (var i=0;i<nameValues.length;i++)
		        		   {
		        		   var strArray=nameValues[i].split("=");
		        		   var key=strArray[0];
		        		   var value=strArray[1];
		        		   map.put(key,value);
		        		   }
		        	   console.log("end");
		        	   }
		           return map;
			}