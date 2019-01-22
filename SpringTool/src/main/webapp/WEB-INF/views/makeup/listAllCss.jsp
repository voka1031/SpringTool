<style>
.pagination {
	display: inline-block;
}

.pagination a {
	color: black;
	float: left;
	padding: 8px 16px;
	text-decoration: none;
}

.pagination a.active {
	background-color: #4CAF50;
	color: white;
	border-radius: 5px;
}

.pagination a:hover:not (.active ) {
	background-color: #ddd;
	border-radius: 5px;
}

#test {
	width: 100%;
	height: 100%;
}
/*

#pagingTable{
    text-align: center;
    margin: 0px auto;
    border: 1px solid #000;
    width: 800px;
    border-radius: 2px;
}



table, th, tr, td{
    border: 1px solid #000;
    border-radius: 2px;
}

*/

#inner {
	margin: 0 auto;
	margin-top: 10px;
	display: inline-block;
}

#wrapper{
	text-align: center;
	display: table; width:100%;
}

#paginationSelector{
	border: 1px solid #ccc;
	border-radius: 6px;
    box-sizing: border-box;
    position:absolute;
	height:30px; 
	margin-top: 13px;
}

</style>