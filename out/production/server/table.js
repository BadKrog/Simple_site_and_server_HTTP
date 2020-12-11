/* Данная функция создаёт кроссбраузерный объект XMLHTTP */
function getXmlHttp() {
    var xmlhttp;
    try {
        xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e) {
        try {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        } catch (E) {
            xmlhttp = false;
        }
    }
    if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
        xmlhttp = new XMLHttpRequest();
    }
    return xmlhttp;
};

function add(){
   	
	PopUpShow();
};


function post(message, name) {

    // Создаём объект XMLHTTP
    var xmlhttp = getXmlHttp();

    // Открываем асинхронное соединение
    xmlhttp.open('POST', name, true);

    // Отправляем кодировку
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // Отправляем POST-запрос
    //xmlhttp.send("a=" +  + "&b=" + encodeURIComponent(b));
    xmlhttp.send(message+"\n");

    // Ждём ответа от сервера
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) { // Ответ пришёл
            if(xmlhttp.status == 200) { // Сервер вернул код 200 (что хорошо)

                return xmlhttp.responseText;
            }
        }
    };
}

// Удаление строки в таблице
$("#table").on('click', '.delete', function () {
	str = this.closest('tr').cells[0].innerHTML + this.closest('tr').cells[1].innerHTML;
	post(str, "delete");
   $(this).closest('tr').remove();
});
console.log("Удалил");

function tabWalker() {
	var tbls = document.getElementById("table");
	var arr = new Array(tbls.rows.length);
	
	for (var r=0; r<tbls.rows.length; r++) {	
		arr[r] = new Array(tbls.rows[r].cells.length);
		for (var c=0; c<tbls.rows[r].cells.length; c++) {
			arr[r][c] = tbls.rows[r].cells[c].innerHTML;					
		}
	}	
	return arr;
}

//------ Popup ---------
$(document).ready(function(){
        //Скрыть PopUp при загрузке страницы    
        PopUpHide();
    });
	
document.getElementById("add").addEventListener("click", ()=>{var table = document.getElementById('table');
    var tr = document.createElement("tr");
    tr.innerHTML = "<td>1</td><td>Гарри Поттер и филосовский камень</td><td>9,8</td><td>158мин</td>"+
                    "<td> <button class=\"redak\">Ред.</button> <button class=\"delete\">X</button>  </td>";
    table.appendChild(tr);
	PopUpHide()});
	
document.getElementById("exit").addEventListener("click", ()=>{PopUpHide()});

    //Функция отображения PopUp
    function PopUpShow(){
        $("#popup1").show();
    }
    //Функция скрытия PopUp
    function PopUpHide(){
        $("#popup1").hide();
    }
