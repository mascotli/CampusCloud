/* 计算已用空间所占百分比 */
function getUsedPercentage(bytes) {
    var percentage = Math.round(bytes / ((1 << 20) * (1 << 20) * 2) * 100);  // 2TB
    // var percentage = Math.round(bytes / (1024 * 1024 * 1024 * 1024 * 2) * 100);  // 2TB
    return percentage + "%";
}
/* 把字节数转为易读的单位 */
function getReadableSize(bytes) {
	if (Number(bytes) == 0){
		return "0.0 B"
	}
    var s = ['B', 'K', 'M', 'G', 'T', 'P'];
    var e = Math.floor(Math.log(bytes)/Math.log(1024));
    return (bytes/Math.pow(1024, Math.floor(e))).toFixed(1)+" "+s[e];
}

function getFilenameWithoutSuffix(filename) {
	var pos = filename.lastIndexOf(".");
	if (pos > 0 && pos < filename.length - 1) {
		return filename.substring(0, pos);
	}
	return filename;
}

function getSuffix(filename) {
	var suffix = "";
	var pos = filename.lastIndexOf(".");
	if (pos > 0 && pos < filename.length - 1) {
		suffix = filename.substring(pos + 1);
	}
	return suffix;
}

function getFileIcon(suffix) {
	suffix = suffix.toLowerCase();
	var src = "img/icon/";
	switch(suffix) {
		case "c":case "cpp":case "java":case "py":case "php":case "js":case "css": src += "code";break;
        case "jpg": case "png": case "gif": case "jpeg":src += "picture";break;
        case "zip":case "rar":case "7z":case "xz":case "gz": src += "zip";break;
        case "doc":case "docx": src += "word";break;
        case "ppt":case "pptx": src += "ppt";break;
        case "xls":case "xlsx": src += "xls";break;        
        case "pdf": src += "pdf";break;
        case "txt": src += "txt";break;
    	case "mp4": src += "video";break;
    	case "mp3": src += "music";break;
        default: src += "file";
	}
	src += ".png";
	return src;
}

function getFormattedDateTime(date) {
	function addZero(num) {
		if (num < 10) {
			num = "0" + num;
		}
		return num;
	}

	var dateObj = new Date(date);

	var year = dateObj.getFullYear();
	var month = addZero(dateObj.getMonth() + 1);
	var day = addZero(dateObj.getDate());
	var hour = addZero(dateObj.getHours());
	var minute = addZero(dateObj.getMinutes());

	var formattedString = year+"-"+month+"-"+day+" "+hour+":"+minute; 
	return formattedString;
}
/* 获取今天比输入的date晚了几天 */
function getGapDays(date) {
	var today = new Date();
	var dateObj = new Date(date);
	var gapDaysTime = today.getTime() - dateObj.getTime();
	var gapDays = Math.floor(gapDaysTime/(24*3600*1000));
	return gapDays;
}

function isPicture(localType){
	if ("JPG" == localType.toUpperCase() || "PNG" == localType.toUpperCase() || "GIF" == localType.toUpperCase() || "JPEG" == localType.toUpperCase()) {
		return true;
	} else {
		return false;
	}
}

/*
 * 测试函数 打印js对象
 */
function printObj(obj){
    var output = "";
    for(var i in obj){  
        var property = obj[i];  
        output += i + " = "+ property + "\n" ; 
    }  
    alert(output);
}

/*
 * 数据运算基本
 */
// console.log(bigMut("567", "1234")); // 699678
function bigMut(big, common) {
	big += "";
	common += "";
	if (big.length < common.length) {
		big = [common, common = big][0];
	}
	big = big.split("").reverse();
	var oneMutManyRes = [];
	var i = 0,
	len = big.length;
	for (; i < len; i++) {
		oneMutManyRes[oneMutManyRes.length] = oneMutMany(big[i], common) + getLenZero(i);
	}
	var result = oneMutManyRes[0];
	for (i = 1, len = oneMutManyRes.length; i < len; i++) {
		result = bigNumAdd(result, oneMutManyRes[i]);
	}
	return result;
}
function getLenZero(len) {
	len += 1;
	var ary = [];
	ary.length = len;
	return ary.join("0");
}
function oneMutMany(one, many) {
	one += "";
	many += "";
	if (one.length != 1) {
		one = [many, many = one][0];
	}
	one = parseInt(one, 10);
	var i = 0,
	len = many.length,
	resAry = [],
	addTo = 0,
	curItem,
	curRes,
	toSave;
	many = many.split("").reverse();
	for (; i <= len; i++) {
		curItem = parseInt(many[i] || 0, 10);
		curRes = curItem * one + addTo;
		toSave = curRes % 10;
		addTo = (curRes - curRes % 10) / 10;
		resAry.unshift(toSave);
	}
	if (resAry[0] == 0) {
		resAry.splice(0, 1);
	}
	return resAry.join("");
}
function bigNumAdd(big, common) {
	big += "";
	common += "";
	var maxLen = Math.max(big.length, common.length),
	bAry = big.split("").reverse(),
	cAry = common.split("").reverse(),
	i = 0,
	addToNext = 0,
	resAry = [],
	fn,
	sn,
	sum;
	for (; i <= maxLen; i++) {
		fn = parseInt(bAry[i] || 0);
		sn = parseInt(cAry[i] || 0);
		sum = fn + sn + addToNext;
		addToNext = (sum - sum % 10) / 10;
		resAry.unshift(sum % 10);
	}
	if (resAry[0] == 0) {
		resAry.splice(0, 1);
	}
	return resAry.join("");
}