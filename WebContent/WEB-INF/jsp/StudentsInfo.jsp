<div class="tablePagination">
	<input id="pageNumber" type="number" value="${currentPage}" min="1" max="${totalPages}" onkeypress="keyPressed()">
	<label> de ${totalPages} paginas.</label> 
	<button class="button" onclick="selectPage(pageNumber.value)">Ir</button>
</div>
${HTMLTable}
<style>
	.tablePagination {
		padding-bottom:10px;
		float:right;
	}
	
	#pageNumber {
		width: 65px;
	}
	
	.button {
		padding: 0 15px 0 15px;
	}
</style>
<script>
		function keyPressed(e)  {
			var key_code = e.keyCode;
			if(key_code == 13){ 
				selectPage(pageNumber.value) 
			}
		}
</script>