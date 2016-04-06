<div id="tablePagination" class="tablePagination">
	<input id="pageNumber" type="number" value="${currentPage}" min="1" max="1">
	${tablePages}
	<!--  <label> de ${tablePages} paginas.</label>-->
	<button class="button" onclick="${IrAction}">Ir</button> 
	<!--  button class="button" onclick="BtnFetchInfo(pageNumber.value,'Ir')">Ir</button>-->
</div>
${HTMLTable}
<style>
	#TableData{
		font-size: 12px !important;
	}
	
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