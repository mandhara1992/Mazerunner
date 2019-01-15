<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<style>
table, th, td {
	max-width:80%;
	max-height:90%;
	min-height:90%;
	min-width:80%;
    border:1px solid black;
    border-style: thin;
    cell-spacing: 0; 
    cell-padding: 0;
    margin:0px; 
    border-spacing: 0px;
    border-collapse: collapse;
}
img {
height: auto;
width: 100%;
}
</style>
<script>	

$(document).ready(function(){
	
	function sleep(delay) {
        var start = new Date().getTime();
        while (new Date().getTime() < start + delay);
      }
	
	$("#generateMaze").click(function() {
		$.ajax({
			  method: "POST",
			  url: "mazeGenerate.html",
			  data: { dim: $("#dim").val(), prob: $("#prob").val()}
			})
			  .done(function( json ) { 
				  alert(json);
			    var rows = $("#dim").val();
		        var cols = $("#dim").val();
		        $('#maze').val(json);
		        var cells = json.split(",");
		        var k=0;
		          var table_body = '<table>';		 
		          for(var i=0;i<rows;i++){
		            table_body+='<tr>';
		            for(var j=0;j<cols;j++){
		                		   
			        	if(cells[k]==1)
		                	{
		                	table_body +="<td id=\'"+i+","+j+"\' bgcolor=\'#000000\'>";
		                	}
		                else
		                	{
		                	table_body +="<td id=\'"+i+","+j+"\' bgcolor=\'#ffffff\'>";
		                	}
		            	
		                table_body +='</td>';
		                k++;
		            }
		            table_body+='</tr>';
		          }
		            table_body+='</table>';
		           $('#tableDiv').html(table_body);  
			  });
		});
	
	$("#submitBFS").click(function() {
		$.ajax({
			  method: "POST",
			  url: "performBFS.html",
			  data: { generated_maze:$("#maze").val(), dim: $("#dim").val(), prob: $("#prob").val()}
			})
			  .done(function( json ) { 
				  alert(json);
			    var rows = $("#dim").val();
		        var cols = $("#dim").val();
		        var data_from_json = json.split("#");
		        alert(data_from_json[0]);
		        var cells = data_from_json[0].split(",");
		        var k=0;
		          var table_body = '<table>';		 
		          for(var i=0;i<rows;i++){
			            table_body+='<tr>';
			            for(var j=0;j<cols;j++){
			                		   
				        	if(cells[k]==1)
			                	{
			                	table_body +="<td id=\'"+i+","+j+"\' bgcolor=\'#000000\'>";
			                	}
			                else
			                	{
			                	table_body +="<td id=\'"+i+","+j+"\' bgcolor=\'#ffffff\'>";
			                	}
			            	
			                table_body +='</td>';
			                k++;
			            }
			            table_body+='</tr>';
			          }
		            table_body+='</table>';
		           $('#tableDiv').html(table_body);  
		           alert(data_from_json[1]);
		           var path = data_from_json[1];
		           var path_cells = path.split(";");
		           animateMario(path_cells,path_cells.length,0);
		           $("#totalTime").text(data_from_json[2])
		           $("#exploredNodes").text(data_from_json[3]);
		           $("#algo").text("BFS");
		           $("#pathLength").text(path_cells.length);
		           $("#blah").text(data_from_json[4]);
			  });
		});
	
	$("#submitAstar").click(function() {
		$.ajax({
			  method: "POST",
			  url: "performAstar.html",
			  data: { generated_maze:$("#maze").val(), dim: $("#dim").val(), prob: $("#prob").val()}
			})
			  .done(function( json ) { 
				  alert(json);
			    var rows = $("#dim").val();
		        var cols = $("#dim").val();
		        var data_from_json = json.split("#");
		        alert(data_from_json[0]);
		        var cells = data_from_json[0].split(",");
		        var k=0;
		          var table_body = '<table>';		 
		          for(var i=0;i<rows;i++){
			            table_body+='<tr>';
			            for(var j=0;j<cols;j++){
			                		   
				        	if(cells[k]==1)
			                	{
			                	table_body +="<td id=\'"+i+","+j+"\' bgcolor=\'#000000\'>";
			                	}
			                else
			                	{
			                	table_body +="<td id=\'"+i+","+j+"\' bgcolor=\'#ffffff\'>";
			                	}
			            	
			                table_body +='</td>';
			                k++;
			            }
			            table_body+='</tr>';
			          }
		            table_body+='</table>';
		           $('#tableDiv').html(table_body);  
		           alert(data_from_json[1]);
		           var path = data_from_json[1];
		           var path_cells = path.split(";");
		           animateMario(path_cells,path_cells.length,0);
		           $("#totalTime").text(data_from_json[2])
		           $("#exploredNodes").text(data_from_json[3]);
		           $("#algo").text("A Star");
		           $("#pathLength").text(path_cells.length);
		           $("#blah").text(data_from_json[4]);
			  });
		});
	
	$("#submitDFS").click(function() {
		$.ajax({
			  method: "POST",
			  url: "performDFS.html",
			  data: { generated_maze:$("#maze").val(), dim: $("#dim").val(), prob: $("#prob").val()}
			})
			  .done(function( json ) { 
				  alert(json);
			    var rows = $("#dim").val();
		        var cols = $("#dim").val();
		        var data_from_json = json.split("#");
		        alert(data_from_json[0]);
		        var cells = data_from_json[0].split(",");
		        var k=0;
		          var table_body = '<table>';		 
		          for(var i=0;i<rows;i++){
			            table_body+='<tr>';
			            for(var j=0;j<cols;j++){
			                		   
				        	if(cells[k]==1)
			                	{
			                	table_body +="<td id=\'"+i+","+j+"\' bgcolor=\'#000000\'>";
			                	}
			                else
			                	{
			                	table_body +="<td id=\'"+i+","+j+"\' bgcolor=\'#ffffff\'>";
			                	}
			            	
			                table_body +='</td>';
			                k++;
			            }
			            table_body+='</tr>';
			          }
		            table_body+='</table>';
		           $('#tableDiv').html(table_body);  
		           alert(data_from_json[1]);
		           var path = data_from_json[1];
		           var path_cells = path.split(";");
		           animateMario(path_cells,path_cells.length,0);
		           $("#totalTime").text(data_from_json[2])
		           $("#exploredNodes").text(data_from_json[3]);
		           $("#algo").text("DFS");
		           $("#pathLength").text(path_cells.length);
		           $("#blah").text(data_from_json[4]);
		           
			  });
		}); 
    });
    
    

function animateMario(path_cells,size,m) {
	  setTimeout(function () {
		var goal_node=($("#dim").val()-1)+","+($("#dim").val()-1);
		document.getElementById(path_cells[m]).style.backgroundColor = "#24baa3";
	    if (--size) {     
	      m++;
	      animateMario(path_cells, size,m);
	    }
	  }, 1);
	}
</script>
</head>
<body>
<input type = "text" id = "dim" placeholder="dimension"/>
<input type = "text" id = "prob" placeholder="probability"/>

<input type = "submit" value= "Generate Maze" id="generateMaze" /> 
<input type = "hidden" id="maze" /> 
<input type = "submit" value= "BFS" id="submitBFS" /> 
<input type = "submit" value= "DFS" id="submitDFS" /> 
<input type = "submit" value= "A Star" id="submitAstar" /> 
<p></p>
<p></p>
<div id = "tableDiv"></div>
<nobr>
<label id= "algo"></label>
Total time taken in seconds : <label id = "totalTime"></label>
Total number of explored nodes : <label id ="exploredNodes"></label>
Path length : <label id = "pathLength"></label>
max fringe : <label id = "blah"></label>
</nobr>
</body>
</html>
