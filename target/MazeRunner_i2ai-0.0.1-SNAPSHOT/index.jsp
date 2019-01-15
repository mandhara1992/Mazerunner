<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>

		
$(document).ready(function(){
	$("#submit").click(function() {
		$.ajax({
			  method: "GET",
			  url: "generateMap.html",
			  data: { dim: $("#dim").val(), prob: $("#prob").val()}
			})
			  .done(function( msg ) {
			    alert( "Data Saved: " + msg );
			  });
		}); 
		var number_of_rows = 5;
        var number_of_cols = 5;
          var table_body = '<table border="1">';
          for(var i=0;i<number_of_rows;i++){
            table_body+='<tr>';
            for(var j=0;j<number_of_cols;j++){
                table_body +='<td>';
                table_body +='Table data';
                table_body +='</td>';
            }
            table_body+='</tr>';
          }
            table_body+='</table>';
           $('#tableDiv1').html(table_body);
    });
</script>
</head>
<body>
<p> Yay! </p>
<div id = "tableDiv" />
<input type = "text" id = "dim" />
<input type = "text" id = "prob" />
<input type = "submit" value= "Submit" id="submit" /> 
</body>
</html>
