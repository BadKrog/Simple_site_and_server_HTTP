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

// Добавление
function add(){
   	document.getElementById("add").value = "Добавить";
	
   	document.getElementById("nameIn").value = "";
   	document.getElementById("ratingIn").value = "";
   	document.getElementById("minIn").value = "";
	
	PopUpShow();
};

var numStrInTab;
// Редактирование
$("#table").on('click', '.redak', function () {
	document.getElementById("add").value = "Изменить";
	
	var table = document.getElementById('table');
	
	var id = this.closest('tr').cells[0].innerHTML;
	for (var r=0; r<table.rows.length; r++) {	
		if(table.rows[r].cells[0].innerHTML==id){
			numStrInTab = r;
		}
	}
	
	console.log("Редактирую:"+this.closest('tr').value);
	document.getElementById("nameIn").value = this.closest('tr').cells[1].innerHTML;
	document.getElementById("ratingIn").value = this.closest('tr').cells[2].innerHTML;
	document.getElementById("minIn").value = this.closest('tr').cells[3].innerHTML;
	
	PopUpShow();
});

// Удаление строки в таблице
$("#table").on('click', '.delete', function () {
	str = this.closest('tr').cells[0].innerHTML;
	post(str, "delete");
   $(this).closest('tr').remove();
});
console.log("Удалил");


// Отправка запроса Post
function post(message, name) {

	var request = "Ошибка получения!";
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

                request = xmlhttp.responseText;
			  console.log("Получили вот это:"+request);
			  if(name=="load"){
				  load(request);
			  }
			  
            }
        }
    };
	
}




// Перевод таблицы в массив
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

// Ввод имени
const inName = document.getElementById("nameIn");
inName.oninput =function(ev){
	var myRe = /^[A-Za-zА-Яа-яЁё\s-:\.,0-9]+$/;
	
	var str = inName.value;
	if(!myRe.test(str.slice(-1))){
		inName.value = str.slice(0,-1);
	}
	console.log(inName.value);
};

// Ввод рейтинга
const inRat = document.getElementById("ratingIn");
inRat.oninput =function(ev){
	
	var num = inRat.value;
	if(num>10){
		inRat.value = 10;
	}
	else if(num < 0){
		inRat.value = 0;
	}
	else{
		inRat.value = num;
	}
	console.log(inRat.value);
};

// Ввод имени
const inMin = document.getElementById("minIn");
inMin.oninput =function(ev){
	var myRe = /^[0-9]{1,3}$/;
	
	var str = inMin.value;
	if(!myRe.test(str)){
		inMin.value = str.slice(0,-1);
	}
	console.log(inMin.value);
};

function load(request){
	var table = document.getElementById('table');
	
	console.log("Ответ с сервера:"+request.toString);
	var arr = request.split('\n');
	
	var id;
	var name;
	var rating;
	var minutes;
	
	
	
	var num = Number(arr[0]);
	for(var i=1; i<=num; i++){
		id     = arr[i].split('+')[0];
		name   = arr[i].split('+')[1];
		rating = arr[i].split('+')[2];
		minutes = arr[i].split('+')[3];
		
		var tr = document.createElement("tr");
		tr.innerHTML = "<td>"+id+"</td><td>"+name+"</td><td>"+rating+"</td><td>"+minutes+"</td>"+
						"<td> <button class=\"redak\">Ред.</button> <button class=\"delete\">X</button>  </td>";
		table.appendChild(tr);
	}
}

//------ При запуске окна ---------
$(document).ready(function(){
        //Скрыть PopUp при загрузке страницы    
		PopUpHide();
	
		post("need all film", "load");
		
    });
	
	

	

// Кнопка подтверждения
const butAdd = document.getElementById("add");
butAdd.addEventListener("click", ()=>{
	var table = document.getElementById('table');
	
	var id = table.rows[table.rows.length-1].cells[0].innerHTML;
	if(id == "id") id = 1;
	else id  = Number(id)+1;
	
   	const name = document.getElementById("nameIn");
   	const rating = document.getElementById("ratingIn");
   	const minuts = document.getElementById("minIn");
	
	var strForPost;
	
	if((name.value!="")&&(rating.value!="")&&(minuts.value!="")){
		if(butAdd.value == "Добавить"){
			var tr = document.createElement("tr");
			tr.innerHTML = "<td>"+id+"</td><td>"+name.value+"</td><td>"+rating.value+"</td><td>"+minuts.value+"</td>"+
							"<td> <button class=\"redak\">Ред.</button> <button class=\"delete\">X</button>  </td>";
			table.appendChild(tr);
			
			strForPost = id+"\n"+name.value+"\n"+rating.value+"\n"+minuts.value;
			post(strForPost, "add");
		}
		else{
			table.rows[numStrInTab].cells[1].innerHTML = name.value;
			table.rows[numStrInTab].cells[2].innerHTML = rating.value;
			table.rows[numStrInTab].cells[3].innerHTML = minuts.value;
			
			idEdit = table.rows[numStrInTab].cells[0].innerHTML;
			strForPost = idEdit+"\n"+name.value+"\n"+rating.value+"\n"+minuts.value;
			post(strForPost, "edit");
		}
		PopUpHide()
	}
});
	
document.getElementById("exit").addEventListener("click", ()=>{PopUpHide()});

    //Функция отображения PopUp
    function PopUpShow(){
        $("#popup1").show();
    }
    //Функция скрытия PopUp
    function PopUpHide(){
        $("#popup1").hide();
    }
